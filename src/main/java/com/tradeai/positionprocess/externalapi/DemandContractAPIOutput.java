package com.tradeai.positionprocess.externalapi;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DemandContractAPIOutput {
	
	private Integer demandContractId;

	private String clientId;

	private String securityCode;

	private Integer originalQuantity;

	private Integer currentQuantity;

	private Double originalRate;

	private Double currentRate;

	private Double originalPrice;

	private Double currentPrice;

	private String contractBookingDate;

	 ///private LocalDate timestamp;

	private String contractStatus;

}
