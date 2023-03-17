/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.configuration.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * qc_trf_test_configurationDTO
 * @author maxteng
 * @version 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfTestConfigurationDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * factory
     */
	@Query(tableColumn = "factory", javaField = "factory", type = QueryType.EQ)
	private String factory;
	/**
     * type
     */
	@Query(tableColumn = "type", javaField = "type", type = QueryType.EQ)
	private String type;
	/**
     * configuration
     */
	private String configuration;
	/**
     * supplement
     */
	private String supplement;
	private String loginName;
}
