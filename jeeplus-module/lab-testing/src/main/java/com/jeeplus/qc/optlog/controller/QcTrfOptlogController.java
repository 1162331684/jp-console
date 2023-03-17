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
import com.jeeplus.qc.optlog.service.dto.QcTrfOptlogDTO;
import com.jeeplus.qc.optlog.service.mapstruct.QcTrfOptlogWrapper;
import com.jeeplus.qc.optlog.service.QcTrfOptlogService;

/**
 * optlogController
 * @author max
 * @version 2022-11-25
 */

@Api(tags ="optlog")
@RestController
@RequestMapping(value = "/qc/optlog/qcTrfOptlog")
public class QcTrfOptlogController {

	@Autowired
	private QcTrfOptlogService qcTrfOptlogService;

	@Autowired
	private QcTrfOptlogWrapper qcTrfOptlogWrapper;

	/**
	 * optlog列表数据
	 */
	@ApiLog("查询optlog列表数据")
	@ApiOperation(value = "查询optlog列表数据")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfOptlog:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfOptlogDTO>> list(QcTrfOptlogDTO qcTrfOptlogDTO, Page<QcTrfOptlogDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfOptlogDTO, QcTrfOptlogDTO.class);
		IPage<QcTrfOptlogDTO> result = qcTrfOptlogService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取optlog数据
	 */
	@ApiLog("根据Id获取optlog数据")
	@ApiOperation(value = "根据Id获取optlog数据")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfOptlog:view','qc:optlog:qcTrfOptlog:add','qc:optlog:qcTrfOptlog:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfOptlogDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfOptlogService.findById ( id ) );
	}

	/**
	 * 保存optlog
	 */
	@ApiLog("保存optlog")
	@ApiOperation(value = "保存optlog")
	@PreAuthorize("hasAnyAuthority('qc:optlog:qcTrfOptlog:add','qc:optlog:qcTrfOptlog:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfOptlogDTO qcTrfOptlogDTO) {
		//新增或编辑表单保存
		qcTrfOptlogService.saveOrUpdate (qcTrfOptlogWrapper.toEntity (qcTrfOptlogDTO));
        return ResponseEntity.ok ( "保存optlog成功" );
	}


	/**
	 * 删除optlog
	 */
	@ApiLog("删除optlog")
	@ApiOperation(value = "删除optlog")
	@PreAuthorize("hasAuthority('qc:optlog:qcTrfOptlog:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcTrfOptlogService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除optlog成功" );
	}

}
