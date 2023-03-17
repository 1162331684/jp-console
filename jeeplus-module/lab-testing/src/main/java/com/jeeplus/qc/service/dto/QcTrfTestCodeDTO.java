/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.jeeplus.qc.service.dto;

import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试项条件配置Entity
 * @author max teng
 * @version 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfTestCodeDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 测试项组
     */
    @Query(tableColumn = "a.test_group", javaField = "testGroup", type = QueryType.EQ)
	private String testGroup;
	/**
     * 测试项
     */
    @Query(tableColumn = "a.test_code", javaField = "testCode", type = QueryType.EQ)
	private String testCode;
	/**
     * 测试项名称
     */
    @Query(tableColumn = "a.test_name", javaField = "testName", type = QueryType.EQ)
	private String testName;
	/**
     * 测试项内容
     */
    @Query(tableColumn = "a.test_content", javaField = "testContent", type = QueryType.EQ)
	private String testContent;
	/**
     * 版样
     */
    @Query(tableColumn = "a.model", javaField = "model", type = QueryType.EQ)
	private String model;
	/**
     * 布种类型
     */
    @Query(tableColumn = "a.fabric_type", javaField = "fabricType", type = QueryType.EQ)
	private String fabricType;
	/**
     * 样本类型
     */
    @Query(tableColumn = "a.sample_type", javaField = "sampleType", type = QueryType.EQ)
	private String sampleType;
	/**
     * 辅料类型
     */
    @Query(tableColumn = "a.acc_type", javaField = "accType", type = QueryType.EQ)
	private String accType;
	/**
     * 是否有效
     */
	private String status;
    /**
     *子表列表
     */
	private List<QcTrfTestContentDTO> qcTrfTestContentDTOList = Lists.newArrayList();
	private List<QcTrfTestConditionDTO> qcTrfTestConditionDTOList = Lists.newArrayList();

}
