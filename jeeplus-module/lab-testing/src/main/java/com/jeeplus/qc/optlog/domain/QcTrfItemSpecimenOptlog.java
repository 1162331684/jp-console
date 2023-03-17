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
@TableName("qc_trf_item_specimen_optlog")
public class QcTrfItemSpecimenOptlog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * qc_trf_item_id
     */
	private String qcTrfItemSpecimenId;
	/**
     * qc_trf_item_id
     */
	private String qcTrfItemId;
	/**
     * 标本编号
     */
	private String specimenId;
	/**
     * 阶段
     */
	private String stage;
	/**
     * 类型
     */
	private String type;
	/**
     * 服务
     */
	private String service;
	/**
     * 状态
     */
	private String status;
	/**
     * 备注
     */
	private String remarks;
	/**
     * 用户修改时间
     */
	private Date changeTime;
	/**
     * 版样
     */
	private String model;

}
