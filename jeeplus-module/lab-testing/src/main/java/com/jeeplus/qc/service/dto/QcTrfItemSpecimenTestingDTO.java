/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试内容Entity
 * @author Lewis
 * @version 2021-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfItemSpecimenTestingDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * qc_trf_item_specimen_id
     */
	private QcTrfItemSpecimenDTO qcTrfItemSpecimen;
	/**
     * 测点项ID
     */
	private String qcTestCodeId;
	/**
	 * 测点项
	 */
	private String testCode;
	/**
	 * 测试项名称
	 */
	private String testName;
	/**
	 * 参考值
	 */
	private String refStd;

	private String comp;
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
     * 用户录入时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeTime;
	/**
     * 备注
     */
	private String remarks;
	/**
     * 是否是需求
     */
	private String isRequire;

	private UserDTO tester;
	private String testTime;

}
