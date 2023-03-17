/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试项配置Entity
 * @author zhimi
 * @version 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_test_code")
public class QcTestCode extends BaseEntity {

	private static final long serialVersionUID = 1L;


	private String details;
	private String state;
	private String testCondition;
	private String dry;
	private String comp;
	private String sampleType;
	/**
     * 版样
     */
	private String model;
	/**
     * 布种
     */
	private String fabricType;
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
     * 参考值
     */
	private String refStd;
	/**
     * 比较符
     */
	private String op;
	/**
     * 标准值
     */
	private String stdValue;
	/**
     * 单位
     */
	private String unit;
	/**
     * 参考组
     */
	private String opGroup;
	/**
     * 取值来源类型
     */
	private String resultSrcType;
	/**
     * 取值来源
     */
	private String resultSrcDictCode;
	/**
     * 是否有效
     */
	private String status;

}
