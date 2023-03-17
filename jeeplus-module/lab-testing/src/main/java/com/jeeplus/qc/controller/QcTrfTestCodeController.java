/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.controller;

import javax.validation.Valid;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.qc.domain.*;
import com.jeeplus.qc.service.QcTrfTestContentService;
import com.jeeplus.qc.service.QcTrfTestConditionService;
import com.jeeplus.qc.service.dto.QcTrfTestContentDTO;
import com.jeeplus.qc.service.dto.QcTrfTestConditionDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfTestContentWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfTestCodeWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfTestConditionWrapper;
import com.jeeplus.sys.constant.CommonConstants;
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
import com.jeeplus.qc.service.dto.QcTrfTestCodeDTO;
import com.jeeplus.qc.service.QcTrfTestCodeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试项条件配置Controller
 * @author max teng
 * @version 2022-11-01
 */
@Api(tags ="测试项条件配置")
@RestController
@RequestMapping(value = "/qc/qcTrfTestCode")
public class QcTrfTestCodeController {

	@Autowired
	private QcTrfTestCodeService qcTrfTestCodeService;
	@Autowired
	private QcTrfTestContentService qcTrfTestContentService;
	@Autowired
	private QcTrfTestConditionService qcTrfTestConditionService;
	/**
	 * 测试项条件配置列表数据
	 */
	@ApiLog("查询测试项条件配置列表数据")
	@ApiOperation(value = "查询测试项条件配置列表数据")
	@PreAuthorize("hasAuthority('qc:qcTrfTestCode:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfTestCodeDTO>> list(QcTrfTestCodeDTO qcTrfTestCodeDTO, Page<QcTrfTestCodeDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfTestCodeDTO, QcTrfTestCodeDTO.class);
		IPage<QcTrfTestCodeDTO> result = qcTrfTestCodeService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}
	@ApiLog("查询测试项组列表数据")
	@ApiOperation(value = "查询测试项组列表数据")
	@PreAuthorize("hasAuthority('qc:qcTrfTestCode:list')")
	@GetMapping("package/list")
	public ResponseEntity<IPage<QcTrfTestCodeDTO>> listByPackage(QcTrfTestCodeDTO qcTrfTestCodeDTO, Page<QcTrfTestCodeDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfTestCodeDTO, QcTrfTestCodeDTO.class);
		IPage<QcTrfTestCodeDTO> result = qcTrfTestCodeService.findPageByPackage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}
	@ApiLog("删除测试项条件配置")
	@ApiOperation(value = "删除测试项条件配置")
	@PreAuthorize("hasAuthority('qc:qcTrfTestCode:del')")
	@DeleteMapping("package/delete")
	public ResponseEntity <String> deleteByPackage(String testGroups) throws Exception {
		String arr[] = testGroups.split(",");
		if(arr.length > 0){
			for(String testGroup : arr){
				qcTrfTestCodeService.removeByLogical(testGroup);
			}
		}
		return ResponseEntity.ok( "删除测试项条件配置成功" );
	}
	@ApiLog("查询测试项配置列表数据")
	@ApiOperation(value = "查询测试项配置列表数据")
	@GetMapping("data")
	public ResponseEntity<List<QcTrfTestCodeDTO>> data(QcTrfTestCode qcTrfTestCode) throws Exception {
		List<QcTrfTestCode> list = new ArrayList<>();
		if(StringUtils.isNotBlank(qcTrfTestCode.getFabricType())){
			list = qcTrfTestCodeService.lambdaQuery().isNotNull(QcTrfTestCode::getFabricType).in(QcTrfTestCode::getFabricType,qcTrfTestCode.getFabricType()).list();
		}else{
			list = qcTrfTestCodeService.lambdaQuery().list();
		}
		List<QcTrfTestCodeDTO> dataList = QcTrfTestCodeWrapper.INSTANCE.toDTO(list);
		List<String> testCodeIds = list.stream().map(QcTrfTestCode::getId).distinct().collect(Collectors.toList());
		if(testCodeIds.size() > 0){
			List<QcTrfTestContent> testContentList = qcTrfTestContentService.lambdaQuery().in(QcTrfTestContent::getTestCodeId,testCodeIds).list();
			List<QcTrfTestCondition> testConditionList = qcTrfTestConditionService.lambdaQuery().in(QcTrfTestCondition::getTestCodeId,testCodeIds).list();
			for(QcTrfTestCodeDTO tmp : dataList){
				List<QcTrfTestContent> list1 = testContentList.stream().filter(t -> t.getTestCodeId().equals(tmp.getId())).collect(Collectors.toList());
				List<QcTrfTestContentDTO> listDTO1 = QcTrfTestContentWrapper.INSTANCE.toDTO(list1);
				tmp.setQcTrfTestContentDTOList(listDTO1);

				List<QcTrfTestCondition> list2 = testConditionList.stream().filter(t -> t.getTestCodeId().equals(tmp.getId())).collect(Collectors.toList());
				List<QcTrfTestConditionDTO> listDTO2 = QcTrfTestConditionWrapper.INSTANCE.toDTO(list2);
				tmp.setQcTrfTestConditionDTOList(listDTO2);
			}
		}
		dataList = dataList.stream().filter(t -> t.getQcTrfTestContentDTOList().size() > 0).collect(Collectors.toList());
		return ResponseEntity.ok(dataList);
	}

	/**
	 * 根据Id获取测试项条件配置数据
	 */
	@ApiLog("根据Id获取测试项条件配置数据")
	@ApiOperation(value = "根据Id获取测试项条件配置数据")
	@PreAuthorize("hasAnyAuthority('qc:qcTrfTestCode:view','qc:qcTrfTestCode:add','qc:qcTrfTestCode:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfTestCodeDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfTestCodeService.findById ( id ) );
	}

	/**
	 * 保存测试项条件配置
	 */
	@ApiLog("保存测试项条件配置")
	@ApiOperation(value = "保存测试项条件配置")
	@PreAuthorize("hasAnyAuthority('qc:qcTrfTestCode:add','qc:qcTrfTestCode:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfTestCodeDTO qcTrfTestCodeDTO) {
		//新增或编辑表单保存
		qcTrfTestCodeService.saveOrUpdate (qcTrfTestCodeDTO);
        return ResponseEntity.ok ( "保存测试项条件配置成功" );
	}
	@ApiLog("保存测试项条件配置")
	@ApiOperation(value = "保存测试项条件配置")
	@PreAuthorize("hasAnyAuthority('qc:qcTrfTestCode:add','qc:qcTrfTestCode:edit')")
	@PostMapping("save/list")
	public  ResponseEntity <String> saveList(@Valid @RequestBody List<QcTrfTestCodeDTO> qcTrfTestCodeDTOList) {
		//新增或编辑表单保存
		for(QcTrfTestCodeDTO qcTrfTestCodeDTO : qcTrfTestCodeDTOList){
			if ( CommonConstants.DELETED.equals ( qcTrfTestCodeDTO.getDelFlag()) ){
				qcTrfTestCodeService.removeById ( qcTrfTestCodeDTO.getId() );
			}else{
				qcTrfTestCodeService.saveOrUpdate (qcTrfTestCodeDTO);
			}

		}
		return ResponseEntity.ok ( "保存测试项条件配置成功" );
	}

	/**
	 * 删除测试项条件配置
	 */
	@ApiLog("删除测试项条件配置")
	@ApiOperation(value = "删除测试项条件配置")
	@PreAuthorize("hasAuthority('qc:qcTrfTestCode:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			qcTrfTestCodeService.removeById ( id );
		}
		return ResponseEntity.ok( "删除测试项条件配置成功" );
	}

}
