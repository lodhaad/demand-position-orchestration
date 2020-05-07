package com.tradeai.positionprocess.externalapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyInputAPI {
	
	private String supplierId;
	private String dateOfSupply;
	private String securityId;
	private String quantity;

}
