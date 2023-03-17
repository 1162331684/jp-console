/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * TRFEntity
 * @author Lewis
 * @version 2021-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf")
public class QcTrf extends BaseEntity {
	
	public static String STATUS_TODO="todo";
	public static String STATUS_DOING="doing";
	
	public static String STATUS_FINISH="finish"; // app 上的汇总状态
	
	public static String STATUS_PASS="pass";
	public static String STATUS_NOPASS="nopass";

	public static String TASK_RESULT_PASS="PASS";
	public static String TASK_RESULT_FAIL="FAIL";

	public static String LAB1ST_LEVEL="1st";
	public static String LAB2ND_LEVEL="2nd";
	public static String LAB3RD_LEVEL="3rd";
	public static String LAB4TH_LEVEL="4th";
	
	private static final long serialVersionUID = 1L;

	/**
     * TRF ID
     */
	private String trfId;
	/**
     * 宝嘉款号
     */
	private String bno;

	private String mo;
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
     * 用户录入时间  目前app 不会改这个表的数据，所以不会导致异步问题的发生，所以暂时无用
     */
	private Date changeTime;
	/**
     * 备注
     */
	private String remarks;

	private String factory;
	private String barcode;
	private String sampleCode;
	private String sampleName;
	private String merchandiser;
	private String sampleType;

	private String atpStatus;

	private String lab1stLevel;
	private String lab2ndLevel;
	private String lab3rdLevel;
	private String lab4thLevel;
	private String lab1stLevelStatus;
	private String lab2ndLevelStatus;
	private String lab3rdLevelStatus;
	private String lab4thLevelStatus;

	private String submitterId;
	private Date testResultsDate;

	private String type;
	
	private String source;
}
