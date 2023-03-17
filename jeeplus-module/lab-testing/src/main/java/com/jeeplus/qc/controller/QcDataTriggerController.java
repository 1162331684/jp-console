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
import com.jeeplus.qc.domain.QcDataTrigger;
import com.jeeplus.qc.service.dto.QcDataTriggerDTO;
import com.jeeplus.qc.service.mapstruct.QcDataTriggerWrapper;
import com.jeeplus.qc.service.QcDataTriggerService;

/**
 * 操作记录Controller
 * @author Lewis
 * @version 2021-12-08
 */

@Api(tags ="操作记录")
@RestController
@RequestMapping(value = "/qc/qcDataTrigger")
public class QcDataTriggerController {

	@Autowired
	private QcDataTriggerService qcDataTriggerService;

	@Autowired
	private QcDataTriggerWrapper qcDataTriggerWrapper;

	/**
	 * 操作记录列表数据
	 */
	@ApiLog("查询操作记录列表数据")
	@ApiOperation(value = "查询操作记录列表数据")
	@PreAuthorize("hasAuthority('qc:qcDataTrigger:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcDataTrigger>> list(QcDataTriggerDTO qcDataTriggerDTO, Page<QcDataTrigger> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcDataTriggerDTO, QcDataTriggerDTO.class);
		IPage<QcDataTrigger> result = qcDataTriggerService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取操作记录数据
	 */
	@ApiLog("根据Id获取操作记录数据")
	@ApiOperation(value = "根据Id获取操作记录数据")
	@PreAuthorize("hasAnyAuthority('qc:qcDataTrigger:view','qc:qcDataTrigger:add','qc:qcDataTrigger:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcDataTriggerDTO> queryById(String id) {
		return ResponseEntity.ok ( qcDataTriggerWrapper.toDTO ( qcDataTriggerService.getById ( id ) ) );
	}

	/**
	 * 保存操作记录
	 */
	@ApiLog("保存操作记录")
	@ApiOperation(value = "保存操作记录")
	@PreAuthorize("hasAnyAuthority('qc:qcDataTrigger:add','qc:qcDataTrigger:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcDataTriggerDTO qcDataTriggerDTO) {
		//新增或编辑表单保存
		qcDataTriggerService.saveOrUpdate (qcDataTriggerWrapper.toEntity (qcDataTriggerDTO));
        return ResponseEntity.ok ( "保存操作记录成功" );
	}


	/**
	 * 删除操作记录
	 */
	@ApiLog("删除操作记录")
	@ApiOperation(value = "删除操作记录")
	@PreAuthorize("hasAuthority('qc:qcDataTrigger:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcDataTriggerService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除操作记录成功" );
	}

}
