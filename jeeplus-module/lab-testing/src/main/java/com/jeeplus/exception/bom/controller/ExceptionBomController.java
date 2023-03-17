/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.exception.bom.domain.ExceptionBom;
import com.jeeplus.exception.bom.info.domain.ExceptionBomInfo;
import com.jeeplus.exception.bom.info.service.ExceptionBomInfoService;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.UserUtils;
import com.jeeplus.utils.excel.ExcelFieldTools;
import com.jeeplus.utils.excel.ExportExcel;
import com.monitorjbl.xlsx.StreamingReader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.exception.bom.service.dto.ExceptionBomDTO;
import com.jeeplus.exception.bom.service.mapstruct.ExceptionBomWrapper;
import com.jeeplus.exception.bom.service.ExceptionBomService;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * BomController
 * @author Young
 * @version 2022-11-12
 */

@Api(tags ="Bom")
@RestController
@RequestMapping(value = "/exception/bom/exceptionBom")
public class ExceptionBomController {

	@Autowired
	private ExceptionBomService exceptionBomService;
	@Autowired
	private ExceptionBomInfoService exceptionBomInfoService;

	@Autowired
	private ExceptionBomWrapper exceptionBomWrapper;

	/**
	 * Bom列表数据
	 */
	@ApiLog("查询Bom列表数据")
	@ApiOperation(value = "查询Bom列表数据")
	@PreAuthorize("hasAuthority('exception:bom:exceptionBom:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<ExceptionBomDTO>> list(ExceptionBomDTO exceptionBomDTO, Page<ExceptionBomDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (exceptionBomDTO, ExceptionBomDTO.class);
		IPage<ExceptionBomDTO> result = exceptionBomService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取Bom数据
	 */
	@ApiLog("根据Id获取Bom数据")
	@ApiOperation(value = "根据Id获取Bom数据")
	@PreAuthorize("hasAnyAuthority('exception:bom:exceptionBom:view','exception:bom:exceptionBom:add','exception:bom:exceptionBom:edit')")
	@GetMapping("queryById")
	public ResponseEntity<ExceptionBomDTO> queryById(String id) {
		return ResponseEntity.ok ( exceptionBomService.findById ( id ) );
	}

	/**
	 * 保存Bom
	 */
	@ApiLog("保存Bom")
	@ApiOperation(value = "保存Bom")
	@PreAuthorize("hasAnyAuthority('exception:bom:exceptionBom:add','exception:bom:exceptionBom:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody ExceptionBomDTO exceptionBomDTO) {
		//新增或编辑表单保存
		exceptionBomService.saveOrUpdate (exceptionBomWrapper.toEntity (exceptionBomDTO));
        return ResponseEntity.ok ( "保存Bom成功" );
	}


	/**
	 * 删除Bom
	 */
	@ApiLog("删除Bom")
	@ApiOperation(value = "删除Bom")
	@PreAuthorize("hasAuthority('exception:bom:exceptionBom:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		exceptionBomInfoService.removeByBomIds(Lists.newArrayList ( idArray ));
        exceptionBomService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除Bom成功" );
	}


	/**
	 * 导入Bom
	 */
	@ApiLog("导入Bom")
	@ApiOperation(value = "导入Bom")
	@PostMapping("importException")
	public ResponseEntity importException(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		try {
			int successNum = 0;
			int addNum = 0;
			int updateNum = 0;
			int failureNum = 0;
			Date now = new Date();
			StringBuilder failureMsg = new StringBuilder();
			String fileName = file.getOriginalFilename();
			Workbook wb;
			int size = 5000;
			if (org.apache.commons.lang3.StringUtils.isBlank(fileName)) {
				throw new RuntimeException(DictUtils.getLanguageLabel("导入文档为空", "") + "!");
			} else {
				if (fileName.toLowerCase().endsWith("xls")) {
					wb = new HSSFWorkbook(file.getInputStream());
				} else {
					if (!fileName.toLowerCase().endsWith("xlsx")) {
						throw new RuntimeException(DictUtils.getLanguageLabel("文档格式不正确", "") + "!");
					}
//					wb = new XSSFWorkbook(file.getInputStream());
					//流式处理：每次缓存5000行至内存，缓存4MB字节至内存
					wb = StreamingReader.builder().rowCacheSize(size).bufferSize(4024).open(file.getInputStream());
				}
			}
			//获取excel的sheet数量
//			int numberOfSheets = wb.getNumberOfSheets();
			ExceptionBom exception;
			ExceptionBomInfo exceptionInfo;
			List<ExceptionBom> needAddExceptionList;
			List<ExceptionBomInfo> needAddExceptionInfoList;
			List<ExceptionBom> needUpdateExceptionList;
			List<ExceptionBomInfo> needUpdateExceptionInfoList;
			List<String> msgs = new ArrayList<>();
			successNum = 0;
			addNum = 0;
			updateNum = 0;
			try {
				Sheet sheet = wb.getSheetAt(0);
				String sheetName = sheet.getSheetName();
				int headerRowIndex = 0;
				//获取表头行
				List<Object[]> headerInforList = ExcelFieldTools.getHeaderListByExcelField(ExceptionBomDTO.class);
				//开始读取数据
				List<ExceptionBomDTO> list = ExcelFieldTools.getDataListByHeader(sheet, ExceptionBomDTO.class, headerInforList);

				if (CollectionUtils.isNotEmpty(list)) {
					//去掉非数据的行:此处模板文件的第一行为表头
					list.remove(0);
					//查询数据表用于判重，根据key规则：workingNo + article + ref + LabDip Status + Mtl-Supp Lifecycle State
					List<String> kesList = list.stream().map(item -> item.getWorkingNo() + '-' + item.getArticle() + '-' + item.getRef() + '-' + item.getLabDipStatus() + '-' + item.getMtlSuppLifecycleState()).collect(Collectors.toList());
					Map<String, Object> queryMap = new HashMap<>();
					queryMap.put("keyList", kesList);
					List<ExceptionBomDTO> dbExceptionDtoList = exceptionBomService.findListByMap(queryMap);

					//为了防止map扩容增加性能代价；初始化长度为链表的长度
					int capacity = (int)((float)(dbExceptionDtoList.size())/0.75F+1.0F);
					Map<String, String> dbExceptionKeyMap = new HashMap<>(capacity);
					//通过key组合 Map 原有数据的id
					if (dbExceptionDtoList != null) {
						dbExceptionKeyMap = dbExceptionDtoList.stream().collect(Collectors.toMap(
								item -> item.getWorkingNo() + '-' + item.getArticle() + '-' + item.getRef() + '-' + item.getLabDipStatus() + '-' + item.getMtlSuppLifecycleState(),
								ExceptionBomDTO::getId, (v1, v2) -> v2));
					}
					needAddExceptionList = new ArrayList<>();
					needAddExceptionInfoList = new ArrayList<>();
					needUpdateExceptionList = new ArrayList<>();
					needUpdateExceptionInfoList = new ArrayList<>();
					for (ExceptionBomDTO dto : list) {
						//去除空行数据
						if (StringUtils.isBlank(dto.getWorkingNo()) && StringUtils.isBlank(dto.getArticle()) && StringUtils.isBlank(dto.getRef())
						&&StringUtils.isBlank(dto.getLabDipStatus()) && StringUtils.isBlank(dto.getMtlSuppLifecycleState())) {
							continue;
						}
						exception = new ExceptionBom();
						exceptionInfo = new ExceptionBomInfo();
						BeanUtils.copyProperties(dto, exception);
						BeanUtils.copyProperties(dto, exceptionInfo);
						String key = dto.getWorkingNo() + '-' + dto.getArticle() + '-' + dto.getRef() + '-' + dto.getLabDipStatus() + '-' + dto.getMtlSuppLifecycleState();
						//当更新数 == 数据库重复数则结束比较，不考虑后面再有相同的情况 && needUpdateExceptionInfoList.size()<dbExceptionKeyMap.size()
						if (dbExceptionKeyMap.containsKey(key) ) {
							exception.setId(dbExceptionKeyMap.get(key));
							exceptionInfo.setBomId(dbExceptionKeyMap.get(key));
							exception.setUpdateDate(now);
							exception.setUpdateBy(UserUtils.getCurrentUserDTO().getId());
							exceptionInfo.setUpdateDate(now);
							exceptionInfo.setUpdateBy(UserUtils.getCurrentUserDTO().getId());
							needUpdateExceptionList.add(exception);
							needUpdateExceptionInfoList.add(exceptionInfo);
						} else {
							exception.setCreateDate(now);
							exception.setCreateBy(UserUtils.getCurrentUserDTO().getId());
							needAddExceptionList.add(exception);
							exceptionInfo.setCreateDate(now);
							exceptionInfo.setCreateBy(UserUtils.getCurrentUserDTO().getId());
							needAddExceptionInfoList.add(exceptionInfo);
							//分批
							if(needAddExceptionList.size()>199){
								exceptionBomService.saveList(needAddExceptionList);
								for(int i=0;i<needAddExceptionInfoList.size();i++){
									//根据list的有序性，set关联的Id
									needAddExceptionInfoList.get(i).setBomId(needAddExceptionList.get(i).getId());
								}
								//批量保存后返回
								exceptionBomInfoService.saveList(needAddExceptionInfoList);
								addNum += needAddExceptionInfoList.size();
								needAddExceptionInfoList.clear();
								needAddExceptionList.clear();
							}
						}
					}
					if (CollectionUtils.isNotEmpty(needAddExceptionList)) {
						exceptionBomService.saveBatch(needAddExceptionList,199);
					}
					if (CollectionUtils.isNotEmpty(needUpdateExceptionList)) {
						exceptionBomService.updateList(needUpdateExceptionList);
					}
					if (CollectionUtils.isNotEmpty(needAddExceptionInfoList)) {
						for(int i=0;i<needAddExceptionInfoList.size();i++){
							//根据list的有序性，set关联的Id
							needAddExceptionInfoList.get(i).setBomId(needAddExceptionList.get(i).getId());
						}
						exceptionBomInfoService.saveList(needAddExceptionInfoList);
						addNum += needAddExceptionInfoList.size();
					}
					if (CollectionUtils.isNotEmpty(needUpdateExceptionInfoList)) {
						exceptionBomInfoService.updateList(needUpdateExceptionInfoList);
						updateNum += needUpdateExceptionInfoList.size();
					}
					successNum = list.size();
					String msg = sheetName + "(" + "Success：" + successNum + "；" + "Add: " + addNum + "；" + "Update: " + updateNum + ");";
					msgs.add(msg);
				}
			} catch (ConstraintViolationException ex) {
				failureNum++;
			} catch (Exception ex) {
				failureNum++;
			}
			JSONObject result = new JSONObject();
			result.put("success",200);
			result.put("msg","导入成功");
			result.put("data",msgs);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			return ResponseEntity.ok("导入Sample Exception失败！失败信息：" + e.getMessage());
		}
	}


	/**
	 * 下载导入bom数据模板
	 */
	@PreAuthorize("hasAnyAuthority('exception:bom:exceptionBom:import')")
	@GetMapping("import/template")
	public ResponseEntity importFileTemplate(HttpServletResponse response) {
		try {
			String fileName = "bom数据导入模板.xlsx";
			List<ExceptionBomDTO> list = Lists.newArrayList();
			new ExportExcel(null, ExceptionBomDTO.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			return ResponseEntity.ok( "导入模板下载失败！失败信息："+e.getMessage());
		}
	}
	/**
	 * 导出excel文件
	 */
	@PreAuthorize("hasAnyAuthority('exception:bom:exceptionBom:export')")
	@GetMapping("export")
	public ResponseEntity exportFile(ExceptionBomDTO exceptionBomDto, HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileName = "bom"+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xlsx";
			//支持勾选指定数据导出ids
			List<ExceptionBomDTO> list = exceptionBomService.findDtoList(exceptionBomDto);
			new ExportExcel(null, ExceptionBomDTO.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			return ResponseEntity.ok("导出bom记录失败！失败信息："+e.getMessage());
		}
	}
}
