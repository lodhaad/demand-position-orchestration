package com.tradeai.positionprocess.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.tradeai.positionprocess.dto.ProcessPositionDTO;
import com.tradeai.positionprocess.externalapi.DemandFullfillAPIOutput;
import com.tradeai.positionprocess.externalapi.DemandContractAPIInput;
import com.tradeai.positionprocess.externalapi.DemandContractAPIOutput;
import com.tradeai.positionprocess.externalapi.PositionAPIInput;
import com.tradeai.positionprocess.externalapi.PositionAPIOutput;
import com.tradeai.positionprocess.externalapi.SupplyContractAPIInput;
import com.tradeai.positionprocess.externalapi.SupplyContractAPIOutput;
import com.tradeai.positionprocess.externalapi.SupplyOutputAPI;
import com.tradeai.positionprocess.input.ProcessPositionInput;
import com.tradeai.positionprocess.output.ProcessPositionOutput;
import com.tradeai.positionprocess.service.ProcessPositionService;




@RestController
@RequestMapping("/process-position")
public class PositionProcessController {

	@Autowired
	private ProcessPositionService service;

	@Autowired
	private RestTemplate template;

	@GetMapping("/health")
	public String health() {
		return "working fine process demand ";
	}

	@PostMapping
	public ResponseEntity<List<ProcessPositionOutput>> processPosition(@RequestBody List<ProcessPositionInput> input) throws ParseException {

		List<ProcessPositionDTO> dto = new ArrayList<>();
		
		List<ProcessPositionOutput> output = new ArrayList<>();

		for (ProcessPositionInput singleInput : input) {

			ProcessPositionDTO dtoElement = new ProcessPositionDTO();
			dtoElement.setClientId(singleInput.getClientId());
			dtoElement.setSecurityId(singleInput.getSecurityId());
			dtoElement.setQuantity(singleInput.getQuantity());
			dtoElement.setSettlementDate(singleInput.getSettlementDate());
			dtoElement.setTradeDate(singleInput.getTradeDate());
			dtoElement.setShortCoverIndicator(singleInput.getShortCoverIndicator());
			dto.add(dtoElement);

		}

		// save the process position as a batch

		dto = service.processPosition(dto);

		List<PositionAPIInput> postionAPIList = new ArrayList<>();

		String clientId = null;
		String date = null;

		for (ProcessPositionDTO processPosition : dto) {

			PositionAPIInput positionInput = new PositionAPIInput();
			clientId = processPosition.getClientId();
			positionInput.setClientId(clientId);
			positionInput.setSecurityId(processPosition.getSecurityId());
			positionInput.setQuantity(processPosition.getQuantity());
			positionInput.setShortCoverIndicator(processPosition.getShortCoverIndicator());
			positionInput.setTradeDate(processPosition.getTradeDate());
			date = processPosition.getSettlementDate();
			positionInput.setSettlementDate(date);
			postionAPIList.add(positionInput);

		}
		
		/// send it to positions service to save it 

		List<PositionAPIOutput> positionOutput = savePositionViaAPI(postionAPIList, clientId, date);

		/// get response
		
		/// for each postions

		for (PositionAPIOutput positionStored : positionOutput) {
			
			
			/// check the demand by date , client , security
			
			DemandFullfillAPIOutput demandAPIOutput  = getDemandFullfillForSecurityAndDateForClient(positionStored.getClientId(), positionStored.getSettlementDate()
					, positionStored.getSecurityId());

			// if exists get the source ( internal or external)
			
			if ( demandAPIOutput != null ) {
				
				///check if the source is from supply 
				
				SupplyOutputAPI supplyAPI = null;
				
				if (demandAPIOutput.getSourceType().equals("E")) {
					
					supplyAPI = getSupply(demandAPIOutput.getSourceId());
					
				}
				else {
					System.out.println("Intenal Inventory ");
				}
				
				
				/// create demand contract
				
				DemandContractAPIInput demandContract = new DemandContractAPIInput();
				
				demandContract.setClientId(clientId);
				demandContract.setSecurityCode(demandAPIOutput.getSecurityId());
				demandContract.setAskedQuantity(demandAPIOutput.getQuantity());
				demandContract.setAskedRate(demandAPIOutput.getRate());
				
				
				DemandContractAPIOutput demandFullfillId = saveDemandContract(demandContract);
				
				
				
				if (supplyAPI != null ) {
					////create supply contract 
					

					SupplyContractAPIInput supplyContract = new SupplyContractAPIInput();
					supplyContract.setSupplierId(supplyAPI.getSupplierId());
					supplyContract.setSecurityCode(supplyAPI.getSecurityCode());
					supplyContract.setOriginalQuantity(demandAPIOutput.getQuantity());
					supplyContract.setActivityType("N");
					supplyContract.setOriginalRate(supplyAPI.getRate());
					supplyContract.setCurrentPrice(0.0d);
					saveSupplyContract(supplyContract);
					
					
					
				}
				
				else {
					
					///create internal contract in supply
				}
			
				
				
				


				
		
				
				
			}else {
				///naked short 
				
				
				
				
				
			}
			
			
			ProcessPositionOutput outputEachPosition = new ProcessPositionOutput();
			
			////fill the object with the details needed 
			outputEachPosition.setClientId(clientId);
			outputEachPosition.setDemandPositionId(demandAPIOutput.getDemandId());
			///outputEachPosition.setDemandPositionBatchId(demandAPIOutput.get);
			outputEachPosition.setSecurityId(demandAPIOutput.getSecurityId());
			outputEachPosition.setQuantity(demandAPIOutput.getQuantity());
			//outputEachPosition.setShortCoverIndicator(demandAPIOutput.);
			
			output.add(outputEachPosition);
			



		}
		
		
		

		

		
		return new ResponseEntity<List<ProcessPositionOutput>>(output, HttpStatus.OK);
		


	}
	
	


	

	private List<PositionAPIOutput> savePositionViaAPI(List<PositionAPIInput> list, String clientId, String date) {

		String positionAPI = "http://localhost:83/client-position/" + clientId + "/date/" + date;

		HttpEntity<List<PositionAPIInput>> requestEntity = new HttpEntity<>(list);

		ResponseEntity<List<PositionAPIOutput>> response = template.exchange(positionAPI, HttpMethod.POST,
				requestEntity, new ParameterizedTypeReference<List<PositionAPIOutput>>() {
				});

		List<PositionAPIOutput> supplies = response.getBody();

		return supplies;
	}

	private DemandContractAPIOutput saveDemandContract(DemandContractAPIInput apiInput) {

		String positionAPI = "http://localhost:84/demand-contract";

		HttpEntity<DemandContractAPIInput> requestEntity = new HttpEntity<>(apiInput);

		ResponseEntity<DemandContractAPIOutput> response = template.exchange(positionAPI, HttpMethod.POST,
				requestEntity, new ParameterizedTypeReference<DemandContractAPIOutput>() {
				});

		DemandContractAPIOutput supplies = response.getBody();

		return supplies;
	}

	private SupplyContractAPIOutput saveSupplyContract(SupplyContractAPIInput list) {

		String positionAPI = "http://localhost:105/supply-contract";

		HttpEntity<SupplyContractAPIInput> requestEntity = new HttpEntity<>(list);

		ResponseEntity<SupplyContractAPIOutput> response = template.exchange(positionAPI, HttpMethod.POST,
				requestEntity, new ParameterizedTypeReference<SupplyContractAPIOutput>() {
				});

		SupplyContractAPIOutput supplies = response.getBody();

		return supplies;
	}

	private DemandFullfillAPIOutput getDemandFullfillForSecurityAndDateForClient(String clientId, String date, String security) {

		String demandURL = "http://localhost:87/demand-fullfillment/client/"+clientId+"/security/"+security+"/date/"+date;
		
		ResponseEntity<DemandFullfillAPIOutput> supplyResponse = template.exchange(demandURL, HttpMethod.GET, null,
				new ParameterizedTypeReference<DemandFullfillAPIOutput>() {
				});

		System.out.println();

		DemandFullfillAPIOutput demand = supplyResponse.getBody();

		return demand;

	}

	
	private SupplyOutputAPI getSupply(Integer supplyId) {

		String demandURL = "http://localhost:93/supply/"+supplyId;
		
		ResponseEntity<SupplyOutputAPI> supplyResponse = template.exchange(demandURL, HttpMethod.GET, null,
				new ParameterizedTypeReference<SupplyOutputAPI>() {
				});

		System.out.println();

		SupplyOutputAPI demand = supplyResponse.getBody();

		return demand;

	}
}
