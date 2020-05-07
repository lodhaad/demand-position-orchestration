package com.tradeai.positionprocess.service;

import java.text.ParseException;
import java.util.List;

import com.tradeai.positionprocess.dto.ProcessPositionDTO;

public interface ProcessPositionService {
	
	public List<ProcessPositionDTO> processPosition(List<ProcessPositionDTO> dto) throws ParseException;

}
