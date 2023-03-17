/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * TRF日志
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qc_trf_log")
public class QcTrfLog extends BaseEntity {

	
	private static final long serialVersionUID = 1L;

	private String interfaces;
	private String trfId;
	private String specimenId;
	private String status;
	private String remarks;

}
