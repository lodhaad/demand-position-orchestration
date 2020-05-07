package com.tradeai.positionprocess.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProcessPositionInput {
	
	private String clientId;
	private String securityId;
	private String tradeDate;
	private String settlementDate;
	private Integer quantity;
	private String shortCoverIndicator;


}
