/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.dto;

import com.jeeplus.qc.service.dto.QcTrfDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物料信息Entity
 * @author Lewis
 * @version 2021-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfItemDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 测试类型
	 */
	private String type;
	private String stage;
	private String model;
	/**
     * qc_trf_id
     */
	private QcTrfDTO qcTrf;
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
     * 用户更变时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeTime;
	/**
     * 备注
     */
	private String remarks;
	
	private String partNo;

	private String result;

	private String pressingCondition;
	private String materialDescription;
	private String lotNo;
	private String sampleType;
}
