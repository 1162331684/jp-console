/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.jeeplus.qc.service.dto;


import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfLogDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * TRF ID
     */
    @Query(tableColumn = "trf_id", javaField = "trfId", type = QueryType.LIKE)
	private String trfId;
	@Query(tableColumn = "interfaces", javaField = "interfaces", type = QueryType.LIKE)
	private String interfaces;
	@Query(tableColumn = "specimen_id", javaField = "specimenId", type = QueryType.LIKE)
	private String specimenId;
	@Query(tableColumn = "status", javaField = "status", type = QueryType.LIKE)
	private String status;
	@Query(tableColumn = "remarks", javaField = "remarks", type = QueryType.LIKE)
	private String remarks;
}
