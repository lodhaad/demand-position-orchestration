package com.tradeai.positionprocess.externalapi;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class PositionAPIInput {
	
	private String clientId;

	private String tradeDate;

	private String settlementDate;

	///private String transactionReceivingDate;

	private String securityId;

	private Integer quantity;

	private Double priceOfSecurity;

	private String shortCoverIndicator;


}
