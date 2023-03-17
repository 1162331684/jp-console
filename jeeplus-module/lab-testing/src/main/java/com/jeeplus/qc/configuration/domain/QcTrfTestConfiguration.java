/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.configuration.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * qc_trf_test_configurationEntity
 * @author maxteng
 * @version 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_test_configuration")
public class QcTrfTestConfiguration extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * factory
     */
	private String factory;
	/**
     * type
     */
	private String type;
	/**
     * configuration
     */
	private String configuration;
	/**
     * supplement
     */
	private String supplement;

}
