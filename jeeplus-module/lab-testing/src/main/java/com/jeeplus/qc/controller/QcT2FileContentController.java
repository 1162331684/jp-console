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
import com.jeeplus.qc.domain.QcT2FileContent;
import com.jeeplus.qc.service.dto.QcT2FileContentDTO;
import com.jeeplus.qc.service.mapstruct.QcT2FileContentWrapper;
import com.jeeplus.qc.service.QcT2FileContentService;

/**
 * 文件内容Controller
 * @author Lewis
 * @version 2022-01-04
 */

@Api(tags ="文件内容")
@RestController
@RequestMapping(value = "/qc/qcT2FileContent")
public class QcT2FileContentController {

	@Autowired
	private QcT2FileContentService qcT2FileContentService;

	@Autowired
	private QcT2FileContentWrapper qcT2FileContentWrapper;

	/**
	 * 文件内容列表数据
	 */
	@ApiLog("查询文件内容列表数据")
	@ApiOperation(value = "查询文件内容列表数据")
	@PreAuthorize("hasAuthority('qc:qcT2FileContent:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcT2FileContent>> list(QcT2FileContentDTO qcT2FileContentDTO, Page<QcT2FileContent> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcT2FileContentDTO, QcT2FileContentDTO.class);
		IPage<QcT2FileContent> result = qcT2FileContentService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取文件内容数据
	 */
	@ApiLog("根据Id获取文件内容数据")
	@ApiOperation(value = "根据Id获取文件内容数据")
	@PreAuthorize("hasAnyAuthority('qc:qcT2FileContent:view','qc:qcT2FileContent:add','qc:qcT2FileContent:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcT2FileContentDTO> queryById(String id) {
		return ResponseEntity.ok ( qcT2FileContentWrapper.toDTO ( qcT2FileContentService.getById ( id ) ) );
	}

	/**
	 * 保存文件内容
	 */
	@ApiLog("保存文件内容")
	@ApiOperation(value = "保存文件内容")
	@PreAuthorize("hasAnyAuthority('qc:qcT2FileContent:add','qc:qcT2FileContent:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcT2FileContentDTO qcT2FileContentDTO) {
		//新增或编辑表单保存
		qcT2FileContentService.saveOrUpdate (qcT2FileContentWrapper.toEntity (qcT2FileContentDTO));
        return ResponseEntity.ok ( "保存文件内容成功" );
	}


	/**
	 * 删除文件内容
	 */
	@ApiLog("删除文件内容")
	@ApiOperation(value = "删除文件内容")
	@PreAuthorize("hasAuthority('qc:qcT2FileContent:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcT2FileContentService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除文件内容成功" );
	}

}
