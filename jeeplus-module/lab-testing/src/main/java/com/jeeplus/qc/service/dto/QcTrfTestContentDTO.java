/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.dto;

import com.google.common.collect.Lists;
import com.jeeplus.qc.domain.QcTrfTestCode;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 测试项条件配置表Entity
 * @author max teng
 * @version 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfTestContentDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 测试项ID
     */
	private QcTrfTestCodeDTO testCode;

	private String testContent;
	private String supplement;

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
     * 允许录入不用测试
     */
	private String status;
	/**
	 *子表列表
	 */
	private List<QcTrfTestConditionDTO> qcTrfTestConditionDTOList = Lists.newArrayList();
}
