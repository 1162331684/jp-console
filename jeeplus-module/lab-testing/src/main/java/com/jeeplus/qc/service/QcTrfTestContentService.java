/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import java.util.List;

import com.jeeplus.qc.domain.QcTrfTestCode;
import com.jeeplus.qc.domain.QcTrfTestCondition;
import com.jeeplus.qc.domain.QcTrfTestContent;
import com.jeeplus.qc.service.dto.QcTrfTestCodeDTO;
import com.jeeplus.qc.service.dto.QcTrfTestConditionDTO;
import com.jeeplus.qc.service.dto.QcTrfTestContentDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfTestCodeWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfTestConditionWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfTestContentWrapper;
import com.jeeplus.sys.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.mapper.QcTrfTestContentMapper;

/**
 * 测试项条件配置表Service
 * @author max teng
 * @version 2022-11-01
 */
@Service
@Transactional
public class QcTrfTestContentService extends ServiceImpl<QcTrfTestContentMapper, QcTrfTestContent> {
	@Autowired
	private QcTrfTestConditionService qcTrfTestConditionService;
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfTestContentDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param testCodeId
	 * @return
	 */
	public List <QcTrfTestContentDTO> findList(String testCodeId) {
		return  baseMapper.findList (testCodeId);
	}
	public void saveOrUpdate(QcTrfTestContentDTO qcTrfTestContentDTO) {
		QcTrfTestContent qcTrfTestContent =  QcTrfTestContentWrapper.INSTANCE.toEntity ( qcTrfTestContentDTO );
		super.saveOrUpdate (qcTrfTestContent);
		for (QcTrfTestConditionDTO qcTrfTestConditionDTO : qcTrfTestContentDTO.getQcTrfTestConditionDTOList ()){
			if ( CommonConstants.DELETED.equals ( qcTrfTestConditionDTO.getDelFlag()) ){
				qcTrfTestConditionService.removeById ( qcTrfTestConditionDTO.getId () );
			}else{
				QcTrfTestCondition qcTrfTestCondition = QcTrfTestConditionWrapper.INSTANCE.toEntity ( qcTrfTestConditionDTO );
				qcTrfTestCondition.setTestContentId ( qcTrfTestContent.getId () );
				qcTrfTestConditionService.saveOrUpdate(qcTrfTestCondition);
			}
		}
	}

}
