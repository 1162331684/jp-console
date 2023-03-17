/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.access.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.exception.access.service.dto.ExceptionListDTO;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.exception.access.domain.ExceptionList;
import com.jeeplus.exception.access.mapper.ExceptionListMapper;

/**
 * Exception AccessService
 * @author Young
 * @version 2022-11-12
 */
@Service
@Transactional
public class ExceptionListService extends ServiceImpl<ExceptionListMapper, ExceptionList> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ExceptionListDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <ExceptionListDTO> findPage(Page <ExceptionListDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		IPage<ExceptionListDTO> pageException = baseMapper.findList (page, queryWrapper);
		for(ExceptionListDTO exception:pageException.getRecords()){
			String statusContentArr="";
			String status = exception.getStatus();
			if(status.startsWith(",")){
				status = status.substring(1);
			}
			String[] strArr = status.split(",");
			for(String str:strArr){
				String statusContent = DictUtils.getLanguageLabel(str, "");
				statusContentArr+=","+statusContent;
			}
			exception.setStatus(statusContentArr.startsWith(",")?statusContentArr.substring(1):statusContentArr);
		}
		return  pageException;
	}

}
