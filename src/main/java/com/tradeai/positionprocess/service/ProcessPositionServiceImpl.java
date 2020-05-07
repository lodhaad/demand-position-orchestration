package com.tradeai.positionprocess.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;


import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeai.positionprocess.datamodel.ProcessPosition;
import com.tradeai.positionprocess.dto.ProcessPositionDTO;
import com.tradeai.positionprocess.repository.PositionProcessRepo;


@Service

public class ProcessPositionServiceImpl implements ProcessPositionService {
	
	@Autowired
	private PositionProcessRepo repo;
	

	@Override
	public List<ProcessPositionDTO> processPosition(List<ProcessPositionDTO> dto) throws ParseException {
		
		List<ProcessPosition> processPosList = new ArrayList<>();
		
		Integer positionId = repo.getMaxPositionProcessId();
		
		if (positionId == null) {
			positionId = 0;
		}
		
		Integer batchId = repo.getMaxPositionProcessBatchId();
		
		if (batchId == null) {
			batchId = 1;
		}
		
		for (ProcessPositionDTO eachelement : dto) {
			
			positionId++;
			
			ProcessPosition processPos = new ProcessPosition();
			processPos.setDemandPositionId(positionId);
			
			processPos.setDemandPositionBatchId(batchId);
			processPos.setSecurityId(eachelement.getSecurityId());
			processPos.setClientId(eachelement.getClientId());
			processPos.setQuantity(eachelement.getQuantity());
			processPos.setSettlementDate(Date.valueOf(eachelement.getSettlementDate()));
			processPos.setTradeDate(Date.valueOf(eachelement.getTradeDate()));
			processPos.setShortCoverIndicator(eachelement.getShortCoverIndicator());
			processPosList.add(processPos);
		}
		
		Iterable<ProcessPosition> iterable =  repo.saveAll(processPosList);
		
		Iterator<ProcessPosition> iterator = iterable.iterator();
		
		List<ProcessPositionDTO> returnDTO = new ArrayList<>();
		
		while  (iterator.hasNext()) {
			
			ProcessPosition position = iterator.next();
			ProcessPositionDTO elementDTO = new ProcessPositionDTO();
			
			elementDTO.setClientId(position.getClientId());
			elementDTO.setQuantity(position.getQuantity());
			elementDTO.setSettlementDate(position.getSettlementDate());
			elementDTO.setTradeDate(position.getTradeDate());
			elementDTO.setSecurityId(position.getSecurityId());
			
			returnDTO.add(elementDTO);
			
		}
		
		return returnDTO;
	}

}
