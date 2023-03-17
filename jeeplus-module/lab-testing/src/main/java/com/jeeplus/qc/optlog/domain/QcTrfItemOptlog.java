/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.optlog.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * logEntity
 * @author max teng
 * @version 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_item_optlog")
public class QcTrfItemOptlog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
	private String qcTrfItemId;
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
	private Date changeTime;
	/**
     * 备注
     */
	private String remarks;
	/**
     * 布料类型
     */
	private String fabricType;
	/**
     * type
     */
	private String type;
	/**
     * stage
     */
	private String stage;
	/**
     * model
     */
	private String model;
	/**
     * result
     */
	private String result;
	/**
     * pressing_condition
     */
	private String pressingCondition;
	/**
     * material_description
     */
	private String materialDescription;
	/**
     * lot_no
     */
	private String lotNo;
	/**
     * sample_type
     */
	private String sampleType;
	/**
     * part_no
     */
	private String partNo;

}
