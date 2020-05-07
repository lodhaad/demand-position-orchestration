package com.tradeai.positionprocess.externalapi;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter 

public class DemandFullfillAPIInput {
	

	private String clientId;

	private String securityId;

	private Long quantity;

	private String dateOfDemand;

	private String settlementDate;

	private Double clientDemandConversionPercentage;

}
