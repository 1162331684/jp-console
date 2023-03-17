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
import com.jeeplus.qc.optlog.service.dto.QcTrfItemSpecimenOptlogDTO;
import com.jeeplus.qc.optlog.service.mapstruct.QcTrfItemSpecimenOptlogWrapper;
import com.jeeplus.qc.optlog.service.QcTrfItemSpecimenOptlogService;

/**
 * logController
 * @author max teng
 * @version 2022-11-25
 */

@Api(tags ="log")
@RestController
@RequestMapping(value = "/qc/optlog/qcTrfItemSpecimenOptlog")
public class QcTrfItemSpecimenOptlogController {

	@Autowired
	private QcTrfItemSpecimenOptlogService qcTrfItemSpecimenOptlogService;

	@Autowired
	private QcTrfItemSpecimenOptlogWrapper qcTrfItemSpecimenOptlogWrapper;

	/**
	 * log列表数据
	 */
	@ApiLog("查询log列表数据")
	@ApiOperation(value = "查询log列表数据")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfItemSpecimenOptlog:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfItemSpecimenOptlogDTO>> list(QcTrfItemSpecimenOptlogDTO qcTrfItemSpecimenOptlogDTO, Page<QcTrfItemSpecimenOptlogDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfItemSpecimenOptlogDTO, QcTrfItemSpecimenOptlogDTO.class);
		IPage<QcTrfItemSpecimenOptlogDTO> result = qcTrfItemSpecimenOptlogService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取log数据
	 */
	@ApiLog("根据Id获取log数据")
	@ApiOperation(value = "根据Id获取log数据")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfItemSpecimenOptlog:view','qc:optlog:qcTrfItemSpecimenOptlog:add','qc:optlog:qcTrfItemSpecimenOptlog:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfItemSpecimenOptlogDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfItemSpecimenOptlogService.findById ( id ) );
	}

	/**
	 * 保存log
	 */
	@ApiLog("保存log")
	@ApiOperation(value = "保存log")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfItemSpecimenOptlog:add','qc:optlog:qcTrfItemSpecimenOptlog:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfItemSpecimenOptlogDTO qcTrfItemSpecimenOptlogDTO) {
		//新增或编辑表单保存
		qcTrfItemSpecimenOptlogService.saveOrUpdate (qcTrfItemSpecimenOptlogWrapper.toEntity (qcTrfItemSpecimenOptlogDTO));
        return ResponseEntity.ok ( "保存log成功" );
	}


	/**
	 * 删除log
	 */
	@ApiLog("删除log")
	@ApiOperation(value = "删除log")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfItemSpecimenOptlog:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcTrfItemSpecimenOptlogService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除log成功" );
	}

}
