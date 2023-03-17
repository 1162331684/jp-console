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
import com.jeeplus.qc.optlog.service.dto.QcTrfItemSpecimenTestingOptlogDTO;
import com.jeeplus.qc.optlog.service.mapstruct.QcTrfItemSpecimenTestingOptlogWrapper;
import com.jeeplus.qc.optlog.service.QcTrfItemSpecimenTestingOptlogService;

/**
 * logController
 * @author max teng
 * @version 2022-11-25
 */

@Api(tags ="log")
@RestController
@RequestMapping(value = "/qc/optlog/qcTrfItemSpecimenTestingOptlog")
public class QcTrfItemSpecimenTestingOptlogController {

	@Autowired
	private QcTrfItemSpecimenTestingOptlogService qcTrfItemSpecimenTestingOptlogService;

	@Autowired
	private QcTrfItemSpecimenTestingOptlogWrapper qcTrfItemSpecimenTestingOptlogWrapper;

	/**
	 * log列表数据
	 */
	@ApiLog("查询log列表数据")
	@ApiOperation(value = "查询log列表数据")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfItemSpecimenTestingOptlog:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfItemSpecimenTestingOptlogDTO>> list(QcTrfItemSpecimenTestingOptlogDTO qcTrfItemSpecimenTestingOptlogDTO, Page<QcTrfItemSpecimenTestingOptlogDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfItemSpecimenTestingOptlogDTO, QcTrfItemSpecimenTestingOptlogDTO.class);
		IPage<QcTrfItemSpecimenTestingOptlogDTO> result = qcTrfItemSpecimenTestingOptlogService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取log数据
	 */
	@ApiLog("根据Id获取log数据")
	@ApiOperation(value = "根据Id获取log数据")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfItemSpecimenTestingOptlog:view','qc:optlog:qcTrfItemSpecimenTestingOptlog:add','qc:optlog:qcTrfItemSpecimenTestingOptlog:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfItemSpecimenTestingOptlogDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfItemSpecimenTestingOptlogService.findById ( id ) );
	}

	/**
	 * 保存log
	 */
	@ApiLog("保存log")
	@ApiOperation(value = "保存log")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfItemSpecimenTestingOptlog:add','qc:optlog:qcTrfItemSpecimenTestingOptlog:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfItemSpecimenTestingOptlogDTO qcTrfItemSpecimenTestingOptlogDTO) {
		//新增或编辑表单保存
		qcTrfItemSpecimenTestingOptlogService.saveOrUpdate (qcTrfItemSpecimenTestingOptlogWrapper.toEntity (qcTrfItemSpecimenTestingOptlogDTO));
        return ResponseEntity.ok ( "保存log成功" );
	}


	/**
	 * 删除log
	 */
	@ApiLog("删除log")
	@ApiOperation(value = "删除log")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfItemSpecimenTestingOptlog:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcTrfItemSpecimenTestingOptlogService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除log成功" );
	}

}
