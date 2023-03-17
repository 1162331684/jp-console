/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.optlog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.optlog.service.dto.QcTrfItemOptlogDTO;
import com.jeeplus.qc.optlog.domain.QcTrfItemOptlog;
import com.jeeplus.qc.optlog.mapper.QcTrfItemOptlogMapper;

/**
 * logService
 * @author max teng
 * @version 2022-11-25
 */
@Service
@Transactional
public class QcTrfItemOptlogService extends ServiceImpl<QcTrfItemOptlogMapper, QcTrfItemOptlog> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfItemOptlogDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <QcTrfItemOptlogDTO> findPage(Page <QcTrfItemOptlogDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

}
