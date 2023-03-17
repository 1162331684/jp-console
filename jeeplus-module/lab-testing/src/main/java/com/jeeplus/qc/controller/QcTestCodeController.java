/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.controller;

import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.qc.domain.QcTestCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.service.dto.QcTestCodeDTO;
import com.jeeplus.qc.service.mapstruct.QcTestCodeWrapper;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.qc.service.QcTestCodeService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试项配置Controller
 * @author zhimi
 * @version 2021-12-08
 */

@Api(tags ="测试项配置")
@RestController
@RequestMapping(value = "/qc/qcTestCode")
public class QcTestCodeController {

	@Autowired
	private QcTestCodeService qcTestCodeService;

	@Autowired
	private QcTestCodeWrapper qcTestCodeWrapper;

	/**
	 * 测试项配置列表数据
	 */
	@ApiLog("查询测试项配置列表数据")
	@ApiOperation(value = "查询测试项配置列表数据")
	@PreAuthorize("hasAuthority('qc:qcTestCode:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTestCodeDTO>> list(QcTestCodeDTO qcTestCodeDTO, Page<QcTestCodeDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTestCodeDTO, QcTestCodeDTO.class);
		IPage<QcTestCodeDTO> result = qcTestCodeService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	@ApiLog("查询测试项配置列表数据")
	@ApiOperation(value = "查询测试项配置列表数据")
	@GetMapping("data")
	public ResponseEntity<List<QcTestCode>> data(QcTestCodeDTO qcTestCodeDTO) throws Exception {
		List<QcTestCode> list = qcTestCodeService.list();
		List<QcTestCode> rows = new ArrayList<>();
		if(StringUtils.isNotBlank(qcTestCodeDTO.getFabricType())){
			for(QcTestCode qcTestCode : list){
				if(StringUtils.isNotBlank(qcTestCodeDTO.getFabricType()) && qcTestCode.getFabricType().equals(qcTestCodeDTO.getFabricType())){
					rows.add(qcTestCode);
				}
			}
		}
		for(QcTestCode qcTestCode : list){
			if(StringUtils.isBlank(qcTestCode.getFabricType())){
				rows.add(qcTestCode);
			}
		}
		return ResponseEntity.ok(rows);
	}


	/**
	 * 根据Id复制测试项配置数据
	 */
	@ApiLog("根据Id复制测试项配置数据")
	@ApiOperation(value = "根据Id复制测试项配置数据")
	@PreAuthorize("hasAnyAuthority('qc:qcTestCode:view','qc:qcTestCode:add','qc:qcTestCode:edit')")
	@GetMapping("copyById")
	public ResponseEntity<String> copyById(String id) {
		QcTestCodeDTO qcTestCodeDTO = qcTestCodeService.findById ( id );
		if(null != qcTestCodeDTO){
			qcTestCodeDTO.setId(null);
			qcTestCodeDTO.setStatus("0");
			qcTestCodeDTO.setTestContent(qcTestCodeDTO.getTestContent()+"_copy"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
			qcTestCodeService.saveOrUpdate (qcTestCodeWrapper.toEntity (qcTestCodeDTO));
			return ResponseEntity.ok ( "复制测试项配置数据成功");
		}
		return ResponseEntity.ok ( "没有可以复制的测试项配置数据");
	}

	/**
	 * 根据Id获取测试项配置数据
	 */
	@ApiLog("根据Id获取测试项配置数据")
	@ApiOperation(value = "根据Id获取测试项配置数据")
	@PreAuthorize("hasAnyAuthority('qc:qcTestCode:view','qc:qcTestCode:add','qc:qcTestCode:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTestCodeDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTestCodeService.findById ( id ) );
	}

	/**
	 * 保存测试项配置
	 */
	@ApiLog("保存测试项配置")
	@ApiOperation(value = "保存测试项配置")
	@PreAuthorize("hasAnyAuthority('qc:qcTestCode:add','qc:qcTestCode:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTestCodeDTO qcTestCodeDTO) {
		//新增或编辑表单保存
		qcTestCodeService.saveOrUpdate (qcTestCodeWrapper.toEntity (qcTestCodeDTO));
        return ResponseEntity.ok ( "保存测试项配置成功" );
	}


	@ApiLog("导入测试项配置")
	@ApiOperation(value = "导入测试项配置")
	@PostMapping("import/save")
	public  ResponseEntity <String> importExcelSave(@Valid @RequestBody QcTestCodeDTO qcTestCodeDTO) {
		qcTestCodeService.saveOrUpdate (qcTestCodeWrapper.toEntity (qcTestCodeDTO));
		return ResponseEntity.ok ( "保存测试项配置成功" );
	}

	private void checkTestCodeCanChange(String [] idArray ) {
		List<QcTestCode> testCodeList = qcTestCodeService.lambdaQuery().in(QcTestCode::getId, idArray).list();
		Long limit=new Date().getTime() - 1000*60*60*8L;
		for(QcTestCode t:testCodeList ){
			if(t.getCreateDate().getTime()  < limit ){
				throw new RuntimeException(t.getTestCode() + ": "+ DictUtils.getLanguageLabel("无法删除,请设置成失效状态", ""));
			}
		}
	}

	/**
	 * 删除测试项配置
	 */
	@ApiLog("删除测试项配置")
	@ApiOperation(value = "删除测试项配置")
	@PreAuthorize("hasAuthority('qc:qcTestCode:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		this.checkTestCodeCanChange(idArray);
        qcTestCodeService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除测试项配置成功" );
	}

}
