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
 * 测试样本信息Entity
 * @author Lewis
 * @version 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_item_specimen")
public class QcTrfItemSpecimen extends BaseEntity {

	public static String STATUS_OPEN="open";
	public static String STATUS_TODO="todo";
	public static String STATUS_FINISH="finish";
	public static String STATUS_NO_FINISH="nofinish";
	
	private static final long serialVersionUID = 1L;

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
     * 版样
     */
	private String model;
	/**
     * 备注
     */
	private String remarks;
	/**
     * 用户修改时间 目前app 不会改这个表的数据，所以不会导致异步问题的发生，所以暂时无用
     */
	private Date changeTime;

}
