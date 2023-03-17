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
 * 测试条件配置DTO
 * @author max teng
 * @version 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfTestConditionDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 测试项ID
	 */
	private QcTrfTestCodeDTO testCode;
	private QcTrfTestContentDTO testContent;
	/**
     * 测试条件
     */
    @Query(tableColumn = "a.test_condition", javaField = "testCondition", type = QueryType.EQ)
	private String testCondition;

    private String supplement;

	private String status;
}
