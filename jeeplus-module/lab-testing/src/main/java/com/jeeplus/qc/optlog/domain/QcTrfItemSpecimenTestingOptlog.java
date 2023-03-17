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
@TableName("qc_trf_item_specimen_testing_optlog")
public class QcTrfItemSpecimenTestingOptlog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * qc_trf_item_specimen_id
     */
	private String qcTrfItemSpecimenTestingId;
	/**
     * qc_trf_item_specimen_id
     */
	private String qcTrfItemSpecimenId;
	/**
     * qc_test_code_id
     */
	private String qcTestCodeId;
	/**
     * test_name
     */
	private String testName;
	/**
     * ref_std
     */
	private String refStd;
	/**
     * comp
     */
	private String comp;
	/**
     * 要求值
     */
	private String requirement;
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
     * 测点项
     */
	private String testCode;
	/**
     * 备注
     */
	private String remarks;
	/**
     * 是否是需求
     */
	private String isRequire;
	/**
     * tester
     */
	private String tester;
	/**
     * test_time
     */
	private String testTime;
	/**
     * 用户录入时间
     */
	private Date changeTime;

}
