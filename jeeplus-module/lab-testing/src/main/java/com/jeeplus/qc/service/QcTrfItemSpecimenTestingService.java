/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenTestingDTO;
import com.jeeplus.qc.utils.TriggerSave;
import com.jeeplus.qc.domain.QcTrfItemSpecimenTesting;
import com.jeeplus.qc.mapper.QcTrfItemSpecimenTestingMapper;

/**
 * 测试内容Service
 * @author Lewis
 * @version 2021-11-19
 */
@Service
@Transactional
public class QcTrfItemSpecimenTestingService extends ServiceImpl<QcTrfItemSpecimenTestingMapper, QcTrfItemSpecimenTesting> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfItemSpecimenTestingDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param qcTrfItemSpecimenId
	 * @return
	 */
	public List <QcTrfItemSpecimenTestingDTO> findList(String qcTrfItemSpecimenId) {
		return  baseMapper.findList (qcTrfItemSpecimenId);
	}
    boolean removeByIds(List<String> idList) {
    	TriggerSave.saveDeleteLog(idList,"qc_trf_item_specimen_testing");
    	return super.removeByIds(idList);
    }
}
