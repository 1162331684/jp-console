/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.jeeplus.qc.service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.config.swagger.IgnoreSwaggerParameter;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * TRFEntity
 * @author Lewis
 * @version 2021-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * TRF ID
     */
    @Query(tableColumn = "trf_id", javaField = "trfId", type = QueryType.LIKE)
	private String trfId;
	/**
     * 宝嘉款号
     */
    @Query(tableColumn = "bno", javaField = "bno", type = QueryType.EQ)
	private String bno;

	@Query(tableColumn = "mo", javaField = "mo", type = QueryType.EQ)
	private String mo;
	/**
     * 客款号
     */
    @Query(tableColumn = "working_no", javaField = "workingNo", type = QueryType.EQ)
	private String workingNo;
	/**
     * 组合色
     */
	@Query(tableColumn = "article", javaField = "article", type = QueryType.LIKE)
	private String article;
	/**
     * 季节
     */
	@Query(tableColumn = "season", javaField = "season", type = QueryType.EQ)
	private String season;
	/**
     * 状态
     */
//	@Query(tableColumn = "status", javaField = "status", type = QueryType.EQ)
	private String status;
	/**
     * 用户录入时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeTime;
	/**
     * 备注
     */
	private String remarks;

	@Query(tableColumn = "a.del_flag", javaField = "delFlag", type = QueryType.EQ)
	private Integer delFlag;

	@Query(tableColumn = "a.create_date", javaField = "createDate", type = QueryType.EQ)
	private Date createDate;

	@Query(tableColumn = "a.update_date", javaField = "updateDate", type = QueryType.EQ)
	private Date updateDate;


	private String createDateStart;
	private String createDateEnd;

	private String cpo;
	private String crd;
	private String psdd;

	private String updateDateStart;
	private String updateDateEnd;

	private String taskStatus;
	private String resultStatus;

	@Query(tableColumn = "factory", javaField = "factory", type = QueryType.EQ)
	private String factory;
	private String barcode;
	private String sampleCode;
	private String sampleName;
	private String merchandiser;
	private String sampleType;

	private String atpStatus;

	private String labLevel;
	private String lab1stLevel;
	private String lab2ndLevel;
	private String lab3rdLevel;
	private String lab4thLevel;
	private String lab1stLevelStatus;
	private String lab2ndLevelStatus;
	private String lab3rdLevelStatus;
	private String lab4thLevelStatus;
	private UserDTO lab1stLevelUser;
	private UserDTO lab2ndLevelUser;
	private UserDTO lab3rdLevelUser;
	private UserDTO lab4thLevelUser;

	private UserDTO submitter;
	private Date testResultsDate;
	private String source;

	@Query(tableColumn = "type", javaField = "type", type = QueryType.EQ)
	private String type;

    /**
     *子表列表
     */
	private List<QcTrfItemDTO> qcTrfItemDTOList = Lists.newArrayList();

}
