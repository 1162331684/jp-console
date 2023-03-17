/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.optlog.service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * optlogDTO
 * @author max
 * @version 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfOptlogDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * qc_trf ID
     */
	private String qcTrfId;
	/**
     * TRF ID
     */
	private String trfId;
	/**
     * 宝嘉款号
     */
	private String bno;
	/**
     * 客款号
     */
	private String workingNo;
	/**
     * 组合色
     */
	private String article;
	/**
     * 季节
     */
	private String season;
	/**
     * 状态
     */
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
	/**
     * mo
     */
	private String mo;
	/**
     * factory
     */
	private String factory;
	/**
     * barcode
     */
	private String barcode;
	/**
     * sample_code
     */
	private String sampleCode;
	/**
     * sample_name
     */
	private String sampleName;
	/**
     * sample_type
     */
	private String sampleType;
	/**
     * merchandiser
     */
	private String merchandiser;
	/**
     * lab1st_level
     */
	private String lab1stLevel;
	/**
     * lab2nd_level
     */
	private String lab2ndLevel;
	/**
     * lab3rd_level
     */
	private String lab3rdLevel;
	/**
     * lab4th_level
     */
	private String lab4thLevel;
	/**
     * atp_status
     */
	private String atpStatus;
	/**
     * submitter_id
     */
	private String submitterId;
	/**
     * test_results_date
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date testResultsDate;
	/**
     * lab1st_level_status
     */
	private String lab1stLevelStatus;
	/**
     * lab2nd_level_status
     */
	private String lab2ndLevelStatus;
	/**
     * lab3rd_level_status
     */
	private String lab3rdLevelStatus;
	/**
     * lab4th_level_status
     */
	private String lab4thLevelStatus;
	/**
     * type
     */
	private String type;
	/**
     * source
     */
	private String source;

}
