/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.exception.bom.service.dto.ExceptionBomDTO;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.exception.access.service.dto.ExceptionListDTO;
import com.jeeplus.exception.bom.domain.ExceptionBom;
import com.jeeplus.exception.bom.mapper.ExceptionBomMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * BomService
 * @author Young
 * @version 2022-11-12
 */
@Service
@Transactional
public class ExceptionBomService extends ServiceImpl<ExceptionBomMapper, ExceptionBom> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ExceptionBomDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <ExceptionBomDTO> findPage(Page <ExceptionBomDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		IPage<ExceptionBomDTO> pageException = baseMapper.findList (page, queryWrapper);
		for(ExceptionBomDTO exception:pageException.getRecords()){
			String statusContentArr="";
			String status = exception.getPrTaskStatus();
			if(status!=null){
				if(status.startsWith(",")){
					status = status.substring(1);
				}
				String[] strArr = status.split(",");
				for(String str:strArr){
					String statusContent = DictUtils.getLanguageLabel(str, "");
					statusContentArr+=","+statusContent;
				}
			}
			exception.setPrTaskStatus(statusContentArr.startsWith(",")?statusContentArr.substring(1):statusContentArr);
			
			if(exception.getLabCount()!=null&&Integer.parseInt(exception.getLabCount())>0){
				exception.setLabTestingTaskStatus(DictUtils.getLanguageLabel("已进行 ","")+exception.getLabCount()+DictUtils.getLanguageLabel(" 次Testing",""));
			}else{
				exception.setLabTestingTaskStatus(DictUtils.getLanguageLabel("未LabTesting", ""));
			}
		}
		return  pageException;
	}
	public List<ExceptionBomDTO> findDtoList(ExceptionBomDTO dto){
		return baseMapper.findDtoList(dto);
	}

	public List<ExceptionBomDTO> findListByMap(Map<String,Object> map){
		List<ExceptionBomDTO> resultList = new ArrayList<>();
		//需要拆分，IN 语句中的数组不能>1000
		if(map!=null &&map.get("keyList")!=null){
			Object valueObj = map.get("keyList");
			if(valueObj instanceof ArrayList<?>){
				List<String> queryKesList = new ArrayList<>();
				for(String item :(List<String>) valueObj){
					queryKesList.add(item);
					if(queryKesList.size()>499){
						map.put("keyList",queryKesList);
						List<ExceptionBomDTO> listByMap = baseMapper.findListByMap(map);
						if (listByMap!=null&&listByMap.size()>0){
							resultList.addAll(listByMap);
						}
						queryKesList.clear();
					}
				}
				if (CollectionUtils.isNotEmpty(queryKesList)){
					map.put("keyList",queryKesList);
					List<ExceptionBomDTO> listByMap = baseMapper.findListByMap(map);
					if (listByMap!=null){
						resultList.addAll(listByMap);
					}
				}
			}
		}else {
			resultList = baseMapper.findListByMap(map);
		}
		return resultList;
	}
	@Transactional(readOnly = false)
	public void saveList(List<ExceptionBom> list) {
		List<ExceptionBom> addList = new ArrayList<>();
		for(ExceptionBom ex:list){
			addList.add(ex);
			if(addList.size()>499){
				baseMapper.saveList(addList);
				addList.clear();
			}
		}
		if(addList.size()>0){
			baseMapper.saveList(addList);
		}
	}
	@Transactional(readOnly = false)
	public void updateList(List<ExceptionBom> list) {
		List<ExceptionBom> updateList = new ArrayList<>();
		for(ExceptionBom ex:list){
			updateList.add(ex);
			if(updateList.size()>199){
				baseMapper.updateList(updateList);
				updateList.clear();
			}
		}
		if(updateList.size()>0){
			baseMapper.updateList(updateList);
		}
	}
}
