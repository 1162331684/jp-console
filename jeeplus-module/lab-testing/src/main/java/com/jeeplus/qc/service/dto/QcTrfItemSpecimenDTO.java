/**
* Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
*/
package com.jeeplus.qc.service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试样本信息Entity
 * @author Lewis
 * @version 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcTrfItemSpecimenDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private String qcTrfId;
	/**
     * qc_trf_item_id
     */
	@Query(type = QueryType.EQ)
	private String qcTrfItemId;
	/**
     * 标本编号
     */
    @Query(type = QueryType.LIKE)
	private String specimenId;
	/**
     * 阶段
     */
    @Query(type = QueryType.EQ)
	private String stage;
	/**
     * 类型
     */
    @Query(type = QueryType.EQ)
	private String type;
	/**
     * 服务
     */
	private String service;
	/**
     * 状态
     */
	private String status;
	/**
     * 版样
     */
	private String model;
	/**
     * 备注
     */
	private String remarks;
	/**
     * 用户修改时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeTime;
    /**
     *子表列表
     */
	private List<QcTrfItemSpecimenTestingDTO> qcTrfItemSpecimenTestingDTOList = Lists.newArrayList();

}
