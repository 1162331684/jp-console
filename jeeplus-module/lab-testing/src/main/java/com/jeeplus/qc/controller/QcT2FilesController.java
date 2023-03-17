/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.controller;

import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.domain.QcT2FileContent;
import com.jeeplus.qc.domain.QcT2Files;
import com.jeeplus.qc.service.dto.QcT2FilesDTO;
import com.jeeplus.qc.service.mapstruct.QcT2FilesWrapper;
import com.jeeplus.qc.utils.GetHttpFileContent;
import com.jeeplus.qc.service.QcT2FileContentService;
import com.jeeplus.qc.service.QcT2FilesService;


/**
 * T2 导入文件Controller
 * @author zhimi
 * @version 2021-12-21
 */

@Api(tags ="T2 导入文件")
@RestController
@RequestMapping(value = "/qc/qcT2Files")
public class QcT2FilesController {

	@Autowired
	private QcT2FilesService qcT2FilesService;

	@Autowired
	private QcT2FilesWrapper qcT2FilesWrapper;

	@Autowired
	private QcT2FileContentService qcT2FileContentService;

	
	/**
	 * T2 导入文件列表数据
	 */
	@ApiLog("查询T2 导入文件列表数据")
	@ApiOperation(value = "查询T2 导入文件列表数据")
	@PreAuthorize("hasAuthority('qc:qcT2Files:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcT2FilesDTO>> list(QcT2FilesDTO qcT2FilesDTO, String contentKey, Page<QcT2FilesDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcT2FilesDTO, QcT2FilesDTO.class);
		if(StringUtils.isNoneBlank(contentKey)){
			queryWrapper.like("c.content", contentKey);
		}
		IPage<QcT2FilesDTO> pageList = qcT2FilesService.findPage(page, queryWrapper);
		for(QcT2FilesDTO qcT2Files : pageList.getRecords()){
			qcT2Files.setCreateBy(UserUtils.get(qcT2Files.getCreateBy().getId()));
		}
		return ResponseEntity.ok (pageList);
	}


	/**
	 * 根据Id获取T2 导入文件数据
	 */
	@ApiLog("根据Id获取T2 导入文件数据")
	@ApiOperation(value = "根据Id获取T2 导入文件数据")
	@PreAuthorize("hasAnyAuthority('qc:qcT2Files:view','qc:qcT2Files:add','qc:qcT2Files:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcT2FilesDTO> queryById(String id) {
		return ResponseEntity.ok ( qcT2FilesWrapper.toDTO ( qcT2FilesService.getById ( id ) ) );
	}

	/**
	 * 保存T2 导入文件
	 */
	@SuppressWarnings("unchecked")
	@ApiLog("保存T2 导入文件")
	@ApiOperation(value = "保存T2 导入文件")
	@PreAuthorize("hasAnyAuthority('qc:qcT2Files:add','qc:qcT2Files:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcT2FilesDTO qcT2FilesDTO) {
		if(StringUtils.isBlank( qcT2FilesDTO.getFileUrl())){
			ResponseUtil responseUtil = new ResponseUtil ( ); 
			return responseUtil.error(DictUtils.getLanguageLabel("文件路径不能为空", ""));
		}
		//新增或编辑表单保存
		QcT2Files dbData =null;
		if(StringUtils.isNoneBlank( qcT2FilesDTO.getId())){
			dbData=qcT2FilesService.getById(qcT2FilesDTO.getId());
		}
		QcT2Files entity = qcT2FilesWrapper.toEntity (qcT2FilesDTO);
		qcT2FilesService.saveOrUpdate (entity);
		if(dbData ==null ||!entity.getFileUrl().equals(dbData.getFileUrl())){
			QcT2FileContent qcT2FileContent =new QcT2FileContent();
			String content = GetHttpFileContent.getFile(entity.getFileUrl());
			qcT2FileContent.setContent(content);
			qcT2FileContent.setId(entity.getId());
			qcT2FileContentService.save(qcT2FileContent);
		}
        return ResponseEntity.ok ( "保存T2 导入文件成功" );
	}


	/**
	 * 删除T2 导入文件
	 */
	@ApiLog("删除T2 导入文件")
	@ApiOperation(value = "删除T2 导入文件")
	@PreAuthorize("hasAuthority('qc:qcT2Files:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcT2FilesService.removeByIds ( Lists.newArrayList ( idArray ) );
        qcT2FileContentService.removeByIds(Lists.newArrayList ( idArray ));
		return ResponseEntity.ok( "删除T2 导入文件成功" );
	}

}
