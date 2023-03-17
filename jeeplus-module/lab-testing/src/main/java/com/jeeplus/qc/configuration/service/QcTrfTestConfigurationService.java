/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.configuration.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.configuration.service.dto.QcTrfTestConfigurationDTO;
import com.jeeplus.qc.configuration.domain.QcTrfTestConfiguration;
import com.jeeplus.qc.configuration.mapper.QcTrfTestConfigurationMapper;

/**
 * qc_trf_test_configurationService
 * @author maxteng
 * @version 2022-11-25
 */
@Service
@Transactional
public class QcTrfTestConfigurationService extends ServiceImpl<QcTrfTestConfigurationMapper, QcTrfTestConfiguration> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfTestConfigurationDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <QcTrfTestConfigurationDTO> findPage(Page <QcTrfTestConfigurationDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

}
