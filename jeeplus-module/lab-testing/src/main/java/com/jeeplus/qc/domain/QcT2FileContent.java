/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 文件内容Entity
 * @author Lewis
 * @version 2022-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_t2_file_content")
public class QcT2FileContent extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 内容
     */
	private String content;

}
