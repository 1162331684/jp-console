/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 操作记录Entity
 * @author Lewis
 * @version 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_data_trigger")
public class QcDataTrigger extends BaseEntity {
	
	public static final String DELETE="DELETE";
	public static final String INSERT="INSERT";
	public static final String UPDATE="UPDATE";

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
