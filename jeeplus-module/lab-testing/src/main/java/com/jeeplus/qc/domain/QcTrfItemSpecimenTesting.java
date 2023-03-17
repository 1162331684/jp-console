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
 * 测试内容Entity
 * @author Lewis
 * @version 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_item_specimen_testing")
public class QcTrfItemSpecimenTesting extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * qc_trf_item_specimen_id
     */
	private String qcTrfItemSpecimenId;
	/**
	 * 测点项ID
	 */
	private String qcTestCodeId;
	/**
	 * 测点项
	 */
	private String testCode;
	/**
	 * 测试项名称
	 */
	private String testName;
	/**
	 * 参考值
	 */
	private String refStd;

	private String comp;
	/**
     * 测试结果
     */
	private String testResult;
	/**
     * 测试前图片
     */
	private String imgBefore;
	/**
     * 测试后图片
     */
	private String imgAfter;
	/**
     * 用户录入时间
     */
	private Date changeTime;
	/**
     * 备注
     */
	private String remarks;
	/**
     * 是否是需求
     */
	private String isRequire;

	private String tester;
	private String testTime;

}
