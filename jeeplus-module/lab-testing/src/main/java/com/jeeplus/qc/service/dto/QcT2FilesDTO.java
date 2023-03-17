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
 * T2 导入文件DTO
 * @author zhimi
 * @version 2021-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcT2FilesDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 文件名
     */
    @Query(type = QueryType.EQ)
	private String fileName;
	/**
     * 文件路径
     */
	private String fileUrl;
	/**
     * 公司编码
     */
    @Query(type = QueryType.EQ)
	private String companyCode;
	/**
     * 组合色
     */
    @Query(type = QueryType.EQ)
	private String article;

	private String remark;

}
