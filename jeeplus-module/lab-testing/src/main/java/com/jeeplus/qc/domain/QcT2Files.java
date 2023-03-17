/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * T2 导入文件Entity
 * @author zhimi
 * @version 2021-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_t2_files")
public class QcT2Files extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 文件名
     */
	private String fileName;
	/**
     * 文件路径
     */
	private String fileUrl;
	/**
     * 公司编码
     */
	private String companyCode;
	/**
     * 组合色
     */
	private String article;

	private String remark;

}
