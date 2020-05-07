package com.tradeai.positionprocess.datamodel;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 

@Entity
@Table(name = "demand_process_orchestration", schema = "demand")

public class ProcessPosition {
	
	@Id
	@Column(name = "demand_process_orch_id")
	private Integer demandPositionId;
	
	
	@Column(name = "demand_process_orch_batch_id")
	private Integer demandPositionBatchId;
	
	@Column(name = "client_id")
	private String clientId;
	
	@Column(name = "security_id")
	private String securityId;
	
	@Column(name = "trade_date")
	private Date tradeDate;
	
	@Column(name = "settlement_date")
	private Date settlementDate;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	
	@Column(name = "short_cover_indicator")
	private String shortCoverIndicator;
	

}
