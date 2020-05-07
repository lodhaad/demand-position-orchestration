package com.tradeai.positionprocess.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tradeai.positionprocess.datamodel.ProcessPosition;

public interface PositionProcessRepo extends CrudRepository<ProcessPosition, Integer> {
	
	@Query ("select max(demandPositionId) from ProcessPosition")
	public Integer getMaxPositionProcessId();
	
	
	@Query ("select max(demandPositionBatchId) from ProcessPosition")
	public Integer getMaxPositionProcessBatchId();
	

}
