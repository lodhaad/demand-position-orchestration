package com.tradeai.positionprocess.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class ProcessPositionOutput {
	
	
	private Integer demandPositionId; 
	private Integer demandPositionBatchId;
	private String clientId;
	private String securityId;
	private String tradeDate;
	private String settlementDate;
	private Integer quantity;
	private String shortCoverIndicator;


}
