package com.tradeai.positionprocess.externalapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 

public class DemandContractAPIInput {
	
	private String clientId;
	private String securityCode;
	private Integer askedQuantity;
	private Double askedRate;
	private Double askedPrice;

}
