package com.tradeai.positionprocess.externalapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyOutputAPI {
	
	private Integer supplyId;

	private Integer supplyGroupId;
	
	private String supplierId;
	
	private String supplyDate;
	
	private String securityCode;
	
	private Integer quantity;
	
	private Double rate;


}
