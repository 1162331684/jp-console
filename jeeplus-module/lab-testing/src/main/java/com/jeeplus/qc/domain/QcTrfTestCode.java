/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试项条件配置Entity
 * @author max teng
 * @version 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_test_code")
public class QcTrfTestCode extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 测试项组
     */
	private String testGroup;
	/**
     * 测试项
     */
	private String testCode;
	/**
     * 测试项名称
     */
	private String testName;
	/**
     * 测试项内容
     */
	private String testContent;
	/**
     * 版样
     */
	private String model;
	/**
     * 布种类型
     */
	private String fabricType;
	/**
     * 样本类型
     */
	private String sampleType;
	/**
     * 辅料类型
     */
	private String accType;
	/**
     * 是否有效
     */
	private String status;

}
