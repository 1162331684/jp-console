/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.domain.QcT2Files;
import com.jeeplus.qc.mapper.QcT2FilesMapper;
import com.jeeplus.qc.service.dto.QcT2FilesDTO;

/**
 * T2 导入文件Service
 * @author zhimi
 * @version 2021-12-21
 */
@Service
@Transactional
public class QcT2FilesService extends ServiceImpl<QcT2FilesMapper, QcT2Files> {
	
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(QcT2Files entity) {
    	return super.saveOrUpdate(entity);
    }
    
	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <QcT2FilesDTO> findPage(Page <QcT2FilesDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}
    
}
