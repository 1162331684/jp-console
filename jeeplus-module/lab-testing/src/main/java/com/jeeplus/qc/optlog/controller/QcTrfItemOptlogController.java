/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.optlog.controller;

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
import com.jeeplus.qc.optlog.service.dto.QcTrfItemOptlogDTO;
import com.jeeplus.qc.optlog.service.mapstruct.QcTrfItemOptlogWrapper;
import com.jeeplus.qc.optlog.service.QcTrfItemOptlogService;

/**
 * logController
 * @author max teng
 * @version 2022-11-25
 */

@Api(tags ="log")
@RestController
@RequestMapping(value = "/qc/optlog/qcTrfItemOptlog")
public class QcTrfItemOptlogController {

	@Autowired
	private QcTrfItemOptlogService qcTrfItemOptlogService;

	@Autowired
	private QcTrfItemOptlogWrapper qcTrfItemOptlogWrapper;

	/**
	 * log列表数据
	 */
	@ApiLog("查询log列表数据")
	@ApiOperation(value = "查询log列表数据")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfItemOptlog:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfItemOptlogDTO>> list(QcTrfItemOptlogDTO qcTrfItemOptlogDTO, Page<QcTrfItemOptlogDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfItemOptlogDTO, QcTrfItemOptlogDTO.class);
		IPage<QcTrfItemOptlogDTO> result = qcTrfItemOptlogService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取log数据
	 */
	@ApiLog("根据Id获取log数据")
	@ApiOperation(value = "根据Id获取log数据")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfItemOptlog:view','qc:optlog:qcTrfItemOptlog:add','qc:optlog:qcTrfItemOptlog:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfItemOptlogDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfItemOptlogService.findById ( id ) );
	}

	/**
	 * 保存log
	 */
	@ApiLog("保存log")
	@ApiOperation(value = "保存log")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfItemOptlog:add','qc:optlog:qcTrfItemOptlog:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfItemOptlogDTO qcTrfItemOptlogDTO) {
		//新增或编辑表单保存
		qcTrfItemOptlogService.saveOrUpdate (qcTrfItemOptlogWrapper.toEntity (qcTrfItemOptlogDTO));
        return ResponseEntity.ok ( "保存log成功" );
	}


	/**
	 * 删除log
	 */
	@ApiLog("删除log")
	@ApiOperation(value = "删除log")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfItemOptlog:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcTrfItemOptlogService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除log成功" );
	}

}
