/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.controller;

import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.service.dto.QcTrfTestConditionDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfTestConditionWrapper;
import com.jeeplus.qc.service.QcTrfTestConditionService;

/**
 * 测试条件配置Controller
 * @author max teng
 * @version 2022-11-01
 */

@Api(tags ="测试条件配置")
@RestController
@RequestMapping(value = "/qc/qcTrfTestCondition")
public class QcTrfTestConditionController {

	@Autowired
	private QcTrfTestConditionService qcTrfTestConditionService;

	@Autowired
	private QcTrfTestConditionWrapper qcTrfTestConditionWrapper;

	/**
	 * 测试条件配置列表数据
	 */
	@ApiLog("查询测试条件配置列表数据")
	@ApiOperation(value = "查询测试条件配置列表数据")
	@PreAuthorize("hasAuthority('qc:qcTrfTestCondition:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfTestConditionDTO>> list(QcTrfTestConditionDTO qcTrfTestConditionDTO, Page<QcTrfTestConditionDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfTestConditionDTO, QcTrfTestConditionDTO.class);
		IPage<QcTrfTestConditionDTO> result = qcTrfTestConditionService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取测试条件配置数据
	 */
	@ApiLog("根据Id获取测试条件配置数据")
	@ApiOperation(value = "根据Id获取测试条件配置数据")
	@PreAuthorize("hasAnyAuthority('qc:qcTrfTestCondition:view','qc:qcTrfTestCondition:add','qc:qcTrfTestCondition:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfTestConditionDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfTestConditionService.findById ( id ) );
	}

	/**
	 * 保存测试条件配置
	 */
	@ApiLog("保存测试条件配置")
	@ApiOperation(value = "保存测试条件配置")
	@PreAuthorize("hasAnyAuthority('qc:qcTrfTestCondition:add','qc:qcTrfTestCondition:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfTestConditionDTO qcTrfTestConditionDTO) {
		//新增或编辑表单保存
		qcTrfTestConditionService.saveOrUpdate (qcTrfTestConditionWrapper.toEntity (qcTrfTestConditionDTO));
        return ResponseEntity.ok ( "保存测试条件配置成功" );
	}


	/**
	 * 删除测试条件配置
	 */
	@ApiLog("删除测试条件配置")
	@ApiOperation(value = "删除测试条件配置")
	@PreAuthorize("hasAuthority('qc:qcTrfTestCondition:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcTrfTestConditionService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除测试条件配置成功" );
	}

}
