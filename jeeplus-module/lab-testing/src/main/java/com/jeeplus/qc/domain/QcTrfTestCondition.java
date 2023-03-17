/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试条件配置Entity
 * @author max teng
 * @version 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_test_condition")
public class QcTrfTestCondition extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String testCodeId;
	private String testContentId;
	/**
     * 测试条件
     */
	private String testCondition;

	private String supplement;


	private String status;
}
