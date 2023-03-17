/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 文件内容DTO
 * @author Lewis
 * @version 2022-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcT2FileContentDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 内容
     */
	private String content;

}
