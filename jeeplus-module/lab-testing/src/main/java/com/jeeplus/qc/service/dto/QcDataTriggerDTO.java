/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 操作记录DTO
 * @author Lewis
 * @version 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcDataTriggerDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 表名称
     */
	private String tableName;
	/**
     * 主键
     */
	private String tableKeyId;
	/**
     * 操作
     */
	private String opcode;
	/**
     * 操作时间
     */
	private String optime;
	/**
     * 内容
     */
	private String content;

}
