/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jeeplus.qc.domain.QcTrf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;
import com.jeeplus.qc.utils.TriggerSave;
import com.jeeplus.qc.domain.QcTrfItem;
import com.jeeplus.qc.mapper.QcTrfItemMapper;

/**
 * 物料信息Service
 * @author Lewis
 * @version 2021-11-18
 */
@Service
@Transactional
public class QcTrfItemService extends ServiceImpl<QcTrfItemMapper, QcTrfItem> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfItemDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param qcTrfId
	 * @return
	 */
	public List <QcTrfItemDTO> findList(String qcTrfId) {
		return  baseMapper.findList (qcTrfId);
	}

	public Integer hasSpecimenById(String id) {
		return getBaseMapper().hasSpecimenById(id);
	}
	
    boolean removeByIds(List<String> idList) {
    	TriggerSave.saveDeleteLog(idList,"qc_trf_item");
    	return super.removeByIds(idList);
    }

    public void updateResult(QcTrfItem qcTrfItem){
		String result = "";
		if(QcTrf.STATUS_NOPASS.equals(qcTrfItem.getResult())){
			result = QcTrfItem.RESULT_FAIL;
		}
		if(QcTrf.STATUS_PASS.equals(qcTrfItem.getResult())){
			result = QcTrfItem.RESULT_PASS;
		}
		baseMapper.update(null,new LambdaUpdateWrapper<QcTrfItem>()
				.set(QcTrfItem::getResult,result).eq(QcTrfItem::getId,qcTrfItem.getId()));
	}

}
