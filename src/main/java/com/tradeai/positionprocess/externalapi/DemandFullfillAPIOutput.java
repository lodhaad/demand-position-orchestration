package com.tradeai.positionprocess.externalapi;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DemandFullfillAPIOutput {

	private Integer fullfillmentId;

	private Integer sourceId;

	private String sourceType;

	/// for which demand
	private Integer demandId;

	private String clientId;
	
	private Double rate;

	private String sourceOfDemandHeld;

	private String status;

	private String securityId;

	private Integer quantity;

	private Date businessDate;

	private Double clientProbablity;

}
