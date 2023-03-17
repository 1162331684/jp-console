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
import com.jeeplus.qc.optlog.service.dto.QcTrfOptlogDTO;
import com.jeeplus.qc.optlog.domain.QcTrfOptlog;
import com.jeeplus.qc.optlog.mapper.QcTrfOptlogMapper;

/**
 * optlogService
 * @author max
 * @version 2022-11-25
 */
@Service
@Transactional
public class QcTrfOptlogService extends ServiceImpl<QcTrfOptlogMapper, QcTrfOptlog> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfOptlogDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <QcTrfOptlogDTO> findPage(Page <QcTrfOptlogDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

}
