package com.tradeai.positionprocess.externalapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 

public class PositionAPIOutput {
	
private Integer clientPositionId;
	
	private Integer batchPositionId;

	private String clientId;

	private String tradeDate;

	private String settlementDate;

	///private String transactionReceivingDate;

	private String securityId;

	private Integer quantity;

	private Double priceOfSecurity;

	private String shortCoverIndicator;

}
