/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试项配置DTO
 * @author zhimi
 * @version 2021-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTestCodeDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;


	@Query(tableColumn = "a.details", javaField = "details", type = QueryType.EQ)
	private String details;

	@Query(tableColumn = "a.state", javaField = "state", type = QueryType.EQ)
	private String state;

	@Query(tableColumn = "a.test_condition", javaField = "testCondition", type = QueryType.EQ)
	private String testCondition;

	@Query(tableColumn = "a.dry", javaField = "dry", type = QueryType.EQ)
	private String dry;

	@Query(tableColumn = "a.comp", javaField = "comp", type = QueryType.EQ)
	private String comp;

	@Query(tableColumn = "a.sample_type", javaField = "sampleType", type = QueryType.EQ)
	private String sampleType;

	/**
     * 版样
     */
    @Query(tableColumn = "a.model", javaField = "model", type = QueryType.EQ)
	private String model;
	/**
     * 布种
     */
	private String fabricType;
	/**
     * 测试项
     */
    @Query(tableColumn = "a.test_code", javaField = "testCode", type = QueryType.LIKE)
	private String testCode;
	/**
     * 测试项名称
     */
    @Query(tableColumn = "a.test_name", javaField = "testName", type = QueryType.LIKE)
	private String testName;
	/**
     * 测试项内容
     */
    @Query(tableColumn = "a.test_content", javaField = "testContent", type = QueryType.LIKE)
	private String testContent;
	/**
     * 参考值
     */
    @Query(tableColumn = "a.ref_std", javaField = "refStd", type = QueryType.LIKE)
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
    @Query(tableColumn = "a.op_group", javaField = "opGroup", type = QueryType.EQ)
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
    @Query(tableColumn = "a.status", javaField = "status", type = QueryType.EQ)
	private String status;

}
