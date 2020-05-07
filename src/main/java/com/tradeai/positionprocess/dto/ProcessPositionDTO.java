package com.tradeai.positionprocess.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProcessPositionDTO {
	
	
	private String clientId;
	private String securityId;
	private Date tradeDate;
	private Date settlementDate;
	private Integer quantity;
	private String shortCoverIndicator;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	


	
	public String getTradeDate() {
		return sdf.format(tradeDate);
	}
	
	
	public void setSettlementDate(String date ) throws ParseException {
		
		settlementDate = sdf.parse(date);
		
	}
	
	public void setSettlementDate(java.sql.Date date) throws ParseException {

		settlementDate =  new Date(date.getTime());

	}
	
	
	public void setTradeDate(java.sql.Date date) throws ParseException {

		tradeDate =  new Date(date.getTime());

	}
	
	public void setTradeDate(String date) throws ParseException {
		
		tradeDate = sdf.parse(date);
	}
	
	
	public String getSettlementDate() {
		return sdf.format(settlementDate);
	}

}
