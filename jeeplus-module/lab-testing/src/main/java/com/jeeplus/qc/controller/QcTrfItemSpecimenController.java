/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.qc.domain.QcTrf;
import com.jeeplus.qc.domain.QcTrfItem;
import com.jeeplus.qc.service.QcTrfItemService;
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
import com.jeeplus.qc.domain.QcTrfItemSpecimen;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfItemSpecimenWrapper;
import com.jeeplus.qc.service.QcTrfItemSpecimenService;
import com.jeeplus.qc.service.QcTrfService;

/**
 * 测试样本信息Controller
 * @author Lewis
 * @version 2021-11-19
 */
@Api(tags ="测试样本信息")
@RestController
@RequestMapping(value = "/qc/qcTrfItemSpecimen")
public class QcTrfItemSpecimenController {

	@Autowired
	private QcTrfItemSpecimenService qcTrfItemSpecimenService;
	@Autowired
	private QcTrfItemService qcTrfItemService;
	@Autowired
	private QcTrfService qcTrfService;

	/**
	 * 测试样本信息列表数据
	 */
	@ApiLog("查询测试样本信息列表数据")
	@ApiOperation(value = "查询测试样本信息列表数据")
	@PreAuthorize("hasAuthority('qc:qcTrfItemSpecimen:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfItemSpecimen>> list(QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO, Page<QcTrfItemSpecimen> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfItemSpecimenDTO, QcTrfItemSpecimenDTO.class);
		IPage<QcTrfItemSpecimen> result = qcTrfItemSpecimenService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	@ApiLog("查询测试样本信息列表数据(不分页)")
	@ApiOperation(value = "查询测试样本信息列表数据(不分页)")
	@PreAuthorize("hasAuthority('qc:qcTrfItemSpecimen:list')")
	@GetMapping("listAll")
	public ResponseEntity<List<QcTrfItemSpecimenDTO>> listAll(QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO) throws Exception {
		QueryWrapper<QcTrfItemSpecimenDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfItemSpecimenDTO, QcTrfItemSpecimenDTO.class);
		List<QcTrfItemSpecimenDTO> result = qcTrfItemSpecimenService.listAll (queryWrapper);
		
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据Id获取测试样本信息数据
	 */
	@ApiLog("根据Id获取测试样本信息数据")
	@ApiOperation(value = "根据Id获取测试样本信息数据")
	@PreAuthorize("hasAnyAuthority('qc:qcTrfItemSpecimen:view','qc:qcTrfItemSpecimen:add','qc:qcTrfItemSpecimen:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfItemSpecimenDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfItemSpecimenService.findById ( id ) );
	}

	/**
	 * 保存测试样本信息
	 */
	@ApiLog("保存测试样本信息")
	@ApiOperation(value = "保存测试样本信息")
	@PostMapping("save/specimenId")
	public  ResponseEntity <QcTrfItemSpecimenDTO> saveSpecimenId(@Valid @RequestBody QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO) throws Exception {
		List<QcTrfItemSpecimen> qcTrfItemSpecimenList = qcTrfItemSpecimenService.findListByItemId(qcTrfItemSpecimenDTO.getQcTrfItemId());
		if(qcTrfItemSpecimenList.size() > 0){
			QcTrfItemSpecimen specimen = qcTrfItemSpecimenList.get(0);
			if(StringUtils.isNotBlank(specimen.getSpecimenId()) && specimen.getSpecimenId().compareTo("-") > 0){
				String[] arr = specimen.getSpecimenId().split("-");
				if(3 == arr.length){
					String specimenId = arr[0]+"-"+arr[1]+"-"+getSpecimenSeq(qcTrfItemSpecimenList,2);
					qcTrfItemSpecimenDTO.setSpecimenId(specimenId);
				}
			}
		}else{
			//qc_trf的id查询Trf和TrfItem的所有记录
			List<String> ids = new ArrayList<>();
			ids.add(qcTrfItemSpecimenDTO.getQcTrfId());
			List<QcTrfItem> qcTrfItemList= qcTrfItemService.lambdaQuery().in(QcTrfItem::getQcTrfId, ids).list();
			ids=qcTrfItemList.stream().map(QcTrfItem::getId).collect(Collectors.toList());
			if(ids.size() > 0) {
				qcTrfItemSpecimenList = qcTrfItemSpecimenService.lambdaQuery()
						.in(QcTrfItemSpecimen::getQcTrfItemId, ids).list();
			}
			if(StringUtils.isNotBlank(qcTrfItemSpecimenDTO.getSpecimenId()) && qcTrfItemSpecimenDTO.getSpecimenId().compareTo("-") > 0){
				String[] arr = qcTrfItemSpecimenDTO.getSpecimenId().split("-");
				if(3 == arr.length){
					String specimenId = arr[0]+"-"+getSpecimenSeq(qcTrfItemSpecimenList,1)+"-01";
					qcTrfItemSpecimenDTO.setSpecimenId(specimenId);
				}
			}

		}
		saveOrUpdateSpecimen(qcTrfItemSpecimenDTO);
		return ResponseEntity.ok (qcTrfItemSpecimenDTO);
	}

	private String getSpecimenSeq(List<QcTrfItemSpecimen> qcTrfItemSpecimenList,int index){
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		};
		TreeSet<Integer> treeSet = new TreeSet<>(comparator);
		Set<Integer> set = new HashSet<>();
		for(QcTrfItemSpecimen specimen : qcTrfItemSpecimenList){
			if(StringUtils.isNotBlank(specimen.getSpecimenId()) && specimen.getSpecimenId().compareTo("-") > 0){
				String[] arr = specimen.getSpecimenId().split("-");
				if(3 == arr.length){
					set.add(Integer.valueOf(arr[index]));
				}
			}
		}
		treeSet.addAll(set);
		int seq = 2 == index?1:0;
		if(treeSet.size() > 0){
			seq = treeSet.first()+1;
		}
		return (seq<10 && 2 == index?"0"+seq:String.valueOf(seq));
	}

	/**
	 * 保存测试样本信息
	 */
	@ApiLog("保存测试样本信息")
	@ApiOperation(value = "保存测试样本信息")
	@PreAuthorize("hasAnyAuthority('qc:qcTrfItemSpecimen:add','qc:qcTrfItemSpecimen:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO) {
		saveOrUpdateSpecimen(qcTrfItemSpecimenDTO);
        return ResponseEntity.ok ( "保存测试样本信息成功" );
	}

	private void saveOrUpdateSpecimen(QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO){
		//新增或编辑表单保存
		qcTrfItemSpecimenService.saveOrUpdate (qcTrfItemSpecimenDTO);
		qcTrfService.updateStatus(Arrays.asList(qcTrfItemSpecimenDTO.getId()));
	}
	
	
	@GetMapping("updateStatus")
	public ResponseEntity <String>  updateStatus(String trfId){
		qcTrfService.updateStatusByTrfIds(Arrays.asList(trfId));
		return ResponseEntity.ok ( "test ok" );
	}

	/**
	 * 删除测试样本信息
	 */
	@ApiLog("删除测试样本信息")
	@ApiOperation(value = "删除测试样本信息")
	@PreAuthorize("hasAuthority('qc:qcTrfItemSpecimen:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			qcTrfItemSpecimenService.removeById ( id );
		}
		qcTrfService.updateStatus(Arrays.asList(idArray));
		return ResponseEntity.ok( "删除测试样本信息成功" );
	}

}
