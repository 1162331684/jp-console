/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.info.controller;

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
import com.jeeplus.exception.bom.info.service.dto.ExceptionBomInfoDTO;
import com.jeeplus.exception.bom.info.service.mapstruct.ExceptionBomInfoWrapper;
import com.jeeplus.exception.bom.info.service.ExceptionBomInfoService;

/**
 * Bom InfoController
 * @author Young
 * @version 2022-11-12
 */

@Api(tags ="Bom Info")
@RestController
@RequestMapping(value = "/exception/bom/info/exceptionBomInfo")
public class ExceptionBomInfoController {

	@Autowired
	private ExceptionBomInfoService exceptionBomInfoService;

	@Autowired
	private ExceptionBomInfoWrapper exceptionBomInfoWrapper;

	/**
	 * Bom Info列表数据
	 */
	@ApiLog("查询Bom Info列表数据")
	@ApiOperation(value = "查询Bom Info列表数据")
	@PreAuthorize("hasAuthority('exception:bom:info:exceptionBomInfo:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<ExceptionBomInfoDTO>> list(ExceptionBomInfoDTO exceptionBomInfoDTO, Page<ExceptionBomInfoDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (exceptionBomInfoDTO, ExceptionBomInfoDTO.class);
		IPage<ExceptionBomInfoDTO> result = exceptionBomInfoService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取Bom Info数据
	 */
	@ApiLog("根据Id获取Bom Info数据")
	@ApiOperation(value = "根据Id获取Bom Info数据")
	@PreAuthorize("hasAnyAuthority('exception:bom:info:exceptionBomInfo:view','exception:bom:info:exceptionBomInfo:add','exception:bom:info:exceptionBomInfo:edit')")
	@GetMapping("queryById")
	public ResponseEntity<ExceptionBomInfoDTO> queryById(String id) {
		return ResponseEntity.ok ( exceptionBomInfoService.findById ( id ) );
	}

	/**
	 * 保存Bom Info
	 */
	@ApiLog("保存Bom Info")
	@ApiOperation(value = "保存Bom Info")
	@PreAuthorize("hasAnyAuthority('exception:bom:info:exceptionBomInfo:add','exception:bom:info:exceptionBomInfo:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody ExceptionBomInfoDTO exceptionBomInfoDTO) {
		//新增或编辑表单保存
		exceptionBomInfoService.saveOrUpdate (exceptionBomInfoWrapper.toEntity (exceptionBomInfoDTO));
        return ResponseEntity.ok ( "保存Bom Info成功" );
	}


	/**
	 * 删除Bom Info
	 */
	@ApiLog("删除Bom Info")
	@ApiOperation(value = "删除Bom Info")
	@PreAuthorize("hasAuthority('exception:bom:info:exceptionBomInfo:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        exceptionBomInfoService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除Bom Info成功" );
	}

}
