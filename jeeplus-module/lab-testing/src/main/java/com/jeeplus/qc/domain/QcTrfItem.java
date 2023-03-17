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
 * 物料信息Entity
 * @author Lewis
 * @version 2021-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_item")
public class QcTrfItem extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final String RESULT_PASS = "PASS";
	public static final String RESULT_FAIL= "FAIL";
	/**
	 * 测试类型
	 */
	private String type;
	private String stage;
	private String model;
	/**
     * qc_trf_id
     */
	private String qcTrfId;
	/**
     * 类别
     */
	private String category;
	/**
     * 物料类型
     */
	private String materialType;
	/**
     * 布料类型
     */
	private String fabricType;
	/**
     * 物料代码
     */
	private String materialItemCode;
	/**
     * 供应商名称
     */
	private String t2Name;
	/**
     * 供应商代码
     */
	private String t2Code;
	/**
     * 颜色代号
     */
	private String colorCode;
	/**
     * 用户更变时间 目前app 不会改这个表的数据，所以不会导致异步问题的发生，所以暂时无用
     */
	private Date changeTime;
	/**
     * 备注
     */
	private String remarks;

	private String result;

	private String pressingCondition;
	private String materialDescription;
	private String lotNo;
	private String sampleType;
	
	private String partNo;
}
