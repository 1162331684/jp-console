/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.service.dto.QcTestCodeDTO;
import com.jeeplus.qc.utils.TriggerSave;
import com.jeeplus.qc.domain.QcTestCode;
import com.jeeplus.qc.mapper.QcTestCodeMapper;

/**
 * 测试项配置Service
 * @author zhimi
 * @version 2021-12-08
 */
@Service
@Transactional
public class QcTestCodeService extends ServiceImpl<QcTestCodeMapper, QcTestCode> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTestCodeDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <QcTestCodeDTO> findPage(Page <QcTestCodeDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}
	
    boolean removeByIds(List<String> idList) {
    	TriggerSave.saveDeleteLog(idList,"qc_test_code");
    	return super.removeByIds(idList);
    }

}
