/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jeeplus.qc.domain.QcTrfTestCondition;
import com.jeeplus.qc.domain.QcTrfTestContent;
import com.jeeplus.qc.service.dto.QcTrfTestConditionDTO;
import com.jeeplus.qc.service.dto.QcTrfTestContentDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfTestConditionWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfTestContentWrapper;
import com.jeeplus.sys.constant.CommonConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.service.dto.QcTrfTestCodeDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfTestCodeWrapper;
import com.jeeplus.qc.domain.QcTrfTestCode;
import com.jeeplus.qc.mapper.QcTrfTestCodeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试项条件配置Service
 * @author max teng
 * @version 2022-11-01
 */
@Service
@Transactional
public class QcTrfTestCodeService extends ServiceImpl<QcTrfTestCodeMapper, QcTrfTestCode> {
	/**
	* 子表service
	*/
	@Autowired
	private QcTrfTestContentService qcTrfTestContentService;
	@Autowired
	private QcTrfTestConditionService qcTrfTestConditionService;
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfTestCodeDTO findById(String id) {
		QcTrfTestCodeDTO qcTrfTestCodeDTO = baseMapper.findById ( id );
		List<QcTrfTestContentDTO> qcTrfTestContentDTOList = qcTrfTestContentService.findList(id);
		List<QcTrfTestCondition> qcTrfTestConditionList = qcTrfTestConditionService.lambdaQuery().eq(QcTrfTestCondition::getTestCodeId,id).list();
		List<QcTrfTestConditionDTO> qcTrfTestConditionDTOListByTestCode = QcTrfTestConditionWrapper.INSTANCE.toDTO(qcTrfTestConditionList);
		qcTrfTestCodeDTO.setQcTrfTestConditionDTOList(qcTrfTestConditionDTOListByTestCode);
		qcTrfTestCodeDTO.setQcTrfTestContentDTOList(qcTrfTestContentDTOList);
		return qcTrfTestCodeDTO;
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <QcTrfTestCodeDTO> findPage(Page <QcTrfTestCodeDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	/**
	* 保存或者更新
	* @param  qcTrfTestCodeDTO
	* @return
	*/
	public void saveOrUpdate(QcTrfTestCodeDTO qcTrfTestCodeDTO) {
		QcTrfTestCode qcTrfTestCode =  QcTrfTestCodeWrapper.INSTANCE.toEntity ( qcTrfTestCodeDTO );
		super.saveOrUpdate (qcTrfTestCode);
		for (QcTrfTestContentDTO qcTrfTestContentDTO : qcTrfTestCodeDTO.getQcTrfTestContentDTOList ()){
			if ( CommonConstants.DELETED.equals ( qcTrfTestContentDTO.getDelFlag()) ){
				qcTrfTestContentService.removeById ( qcTrfTestContentDTO.getId () );
			}else{
				QcTrfTestCodeDTO testCode = new QcTrfTestCodeDTO();
				testCode.setId(qcTrfTestCode.getId () );
				qcTrfTestContentDTO.setTestCode(testCode);
				qcTrfTestContentService.saveOrUpdate(qcTrfTestContentDTO);
			}
		}
		for (QcTrfTestConditionDTO qcTrfTestConditionDTO : qcTrfTestCodeDTO.getQcTrfTestConditionDTOList()){
			if ( CommonConstants.DELETED.equals ( qcTrfTestConditionDTO.getDelFlag()) ){
				qcTrfTestContentService.removeById ( qcTrfTestConditionDTO.getId () );
			}else{
				QcTrfTestCondition qcTrfTestCondition = QcTrfTestConditionWrapper.INSTANCE.toEntity ( qcTrfTestConditionDTO );
				qcTrfTestCondition.setTestCodeId(qcTrfTestCode.getId () );
				qcTrfTestConditionService.saveOrUpdate(qcTrfTestCondition);
			}
		}
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		super.removeById ( id );
		qcTrfTestContentService.lambdaUpdate ().eq ( QcTrfTestContent::getTestCodeId, id ).remove ();
	}

	public IPage<QcTrfTestCodeDTO> findPageByPackage(Page<QcTrfTestCodeDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		queryWrapper.groupBy("a.test_group");
		return  baseMapper.findListByPackage (page, queryWrapper);
	}

	public void removeByLogical(String testGroup) {
		baseMapper.update(null,new LambdaUpdateWrapper<QcTrfTestCode>()
				.set(QcTrfTestCode::getDelFlag,"1").eq(QcTrfTestCode::getTestGroup, testGroup));
	}
}
