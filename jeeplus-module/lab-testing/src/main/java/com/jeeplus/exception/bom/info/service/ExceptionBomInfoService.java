/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.info.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.exception.bom.info.service.dto.ExceptionBomInfoDTO;
import com.jeeplus.exception.bom.info.domain.ExceptionBomInfo;
import com.jeeplus.exception.bom.info.mapper.ExceptionBomInfoMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Bom InfoService
 * @author Young
 * @version 2022-11-12
 */
@Service
@Transactional
public class ExceptionBomInfoService extends ServiceImpl<ExceptionBomInfoMapper, ExceptionBomInfo> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ExceptionBomInfoDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <ExceptionBomInfoDTO> findPage(Page <ExceptionBomInfoDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	public void removeByBomIds(List<String> ids){
		if (CollectionUtils.isNotEmpty(ids)){
			baseMapper.removeByBomIds(ids);
		}
	}

	@Transactional(readOnly = false)
	public void saveList(List<ExceptionBomInfo> list) {
		List<ExceptionBomInfo> saveList = new ArrayList<>();
		for(ExceptionBomInfo info:list){
			saveList.add(info);
			if(saveList.size()>199){
				baseMapper.saveList(saveList);
				saveList.clear();
			}
		}
		if(saveList.size()>0){
			baseMapper.saveList(saveList);
		}

	}
	@Transactional(readOnly = false)
	public void updateList(List<ExceptionBomInfo> list) {
		List<ExceptionBomInfo> updateList = new ArrayList<>();
		for(ExceptionBomInfo info:list){
			updateList.add(info);
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
