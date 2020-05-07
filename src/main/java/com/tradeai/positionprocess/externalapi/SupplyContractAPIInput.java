package com.tradeai.positionprocess.externalapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SupplyContractAPIInput {
	


	private String securityCode;

	private Integer originalQuantity;
	
	private String supplierId;


	private Double originalRate;


	private Double currentPrice;


	private String contractStatus;
	
	private String activityType;


	
	
	

}
