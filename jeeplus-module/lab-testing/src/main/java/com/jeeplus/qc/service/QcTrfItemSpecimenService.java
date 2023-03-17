/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenDTO;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenTestingDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfItemSpecimenWrapper;
import com.jeeplus.qc.utils.TriggerSave;
import com.jeeplus.qc.service.mapstruct.QcTrfItemSpecimenTestingWrapper;
import com.jeeplus.qc.domain.QcTrfItem;
import com.jeeplus.qc.domain.QcTrfItemSpecimen;
import com.jeeplus.qc.domain.QcTrfItemSpecimenTesting;
import com.jeeplus.qc.mapper.QcTrfItemSpecimenMapper;

/**
 * 测试样本信息Service
 * @author Lewis
 * @version 2021-11-19
 */
@Service
@Transactional
public class QcTrfItemSpecimenService extends ServiceImpl<QcTrfItemSpecimenMapper, QcTrfItemSpecimen> {
	/**
	* 子表service
	*/
	@Autowired
	private QcTrfItemSpecimenTestingService qcTrfItemSpecimenTestingService;
	@Autowired
	private QcTrfLogService qcTrfLogService;
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfItemSpecimenDTO findById(String id) {
		QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO = QcTrfItemSpecimenWrapper.INSTANCE.toDTO ( super.getById ( id ) );
		qcTrfItemSpecimenDTO.setQcTrfItemSpecimenTestingDTOList(qcTrfItemSpecimenTestingService.findList(id));
		return qcTrfItemSpecimenDTO;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<QcTrfItemSpecimenDTO> listAll(QueryWrapper queryWrapper) {
		List<QcTrfItemSpecimen> list= super.list(queryWrapper);
		List<QcTrfItemSpecimenDTO> result=new ArrayList<>();
		for(QcTrfItemSpecimen qcTrfItemSpecimen: list){
			QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO = QcTrfItemSpecimenWrapper.INSTANCE.toDTO (qcTrfItemSpecimen);
			qcTrfItemSpecimenDTO.setQcTrfItemSpecimenTestingDTOList(qcTrfItemSpecimenTestingService.findList(qcTrfItemSpecimen.getId()));
			
			for(QcTrfItemSpecimenTestingDTO test:qcTrfItemSpecimenDTO.getQcTrfItemSpecimenTestingDTOList() ){
				if(test.getCreateBy()!=null&&StringUtils.isNoneBlank(test.getCreateBy().getId())){
					test.setCreateBy(UserUtils.get(test.getCreateBy().getId()));
				}
				if(test.getUpdateBy()!=null&&StringUtils.isNoneBlank(test.getUpdateBy().getId())){
					test.setUpdateBy(UserUtils.get(test.getUpdateBy().getId()));
				}
			}
			
			if(qcTrfItemSpecimenDTO.getCreateBy()!=null&&StringUtils.isNoneBlank(qcTrfItemSpecimenDTO.getCreateBy().getId())){
				qcTrfItemSpecimenDTO.setCreateBy(UserUtils.get(qcTrfItemSpecimenDTO.getCreateBy().getId()));
			}
			if(qcTrfItemSpecimenDTO.getUpdateBy()!=null&&StringUtils.isNoneBlank(qcTrfItemSpecimenDTO.getUpdateBy().getId())){
				qcTrfItemSpecimenDTO.setUpdateBy(UserUtils.get(qcTrfItemSpecimenDTO.getUpdateBy().getId()));
			}
			result.add(qcTrfItemSpecimenDTO);
		}
		return result;
	}

	/**
	 * 保存或者更新
	 * @param  qcTrfItemSpecimenDTO
	 * @return
	 */
	public void saveOrUpdate(QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO) {
		QcTrfItemSpecimen qcTrfItemSpecimen =  QcTrfItemSpecimenWrapper.INSTANCE.toEntity ( qcTrfItemSpecimenDTO );
		super.saveOrUpdate (qcTrfItemSpecimen);
		qcTrfItemSpecimenDTO.setId(qcTrfItemSpecimen.getId());
		List<String> itemIds=new ArrayList<>();
		Set <String> permissions =UserUtils.getPermissions();
		for (QcTrfItemSpecimenTestingDTO qcTrfItemSpecimenTestingDTO : qcTrfItemSpecimenDTO.getQcTrfItemSpecimenTestingDTOList ()){
			if ( CommonConstants.DELETED.equals ( qcTrfItemSpecimenTestingDTO.getDelFlag()) ){
				qcTrfItemSpecimenTestingService.removeById ( qcTrfItemSpecimenTestingDTO.getId () );
				itemIds.add(qcTrfItemSpecimenTestingDTO.getId ());
			}else{
				QcTrfItemSpecimenTesting qcTrfItemSpecimenTesting = QcTrfItemSpecimenTestingWrapper.INSTANCE.toEntity ( qcTrfItemSpecimenTestingDTO );
				qcTrfItemSpecimenTesting.setQcTrfItemSpecimenId ( qcTrfItemSpecimen.getId () );
				if(StringUtils.isBlank(qcTrfItemSpecimenTesting.getIsRequire())){
					if(permissions.contains("qc:qcTrf:requirement")){
						qcTrfItemSpecimenTesting.setIsRequire("1");
					}
					else{
						qcTrfItemSpecimenTesting.setIsRequire("0");
					}
				}
				qcTrfItemSpecimenTestingService.saveOrUpdate ( qcTrfItemSpecimenTesting );
			}
		}
		if(itemIds.size()>0){
			TriggerSave.saveDeleteLog(itemIds,"qc_trf_item_specimen_testing");
		}
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		TriggerSave.saveDeleteLog(id,"qc_trf_item_specimen");
		super.removeById ( id );
		List<String> itemIds=qcTrfItemSpecimenTestingService.lambdaQuery().eq(QcTrfItemSpecimenTesting::getQcTrfItemSpecimenId, id).list()
				.stream().map(QcTrfItemSpecimenTesting::getId).collect(Collectors.toList());
		TriggerSave.saveDeleteLog(itemIds,"qc_trf_item_specimen_testing");
		qcTrfItemSpecimenTestingService.lambdaUpdate ().eq ( QcTrfItemSpecimenTesting::getQcTrfItemSpecimenId, id ).remove ();
	}

	
	public void  setOpenSpecimen(String id) {
		QcTrfItemSpecimen specimen=  getById(id);
		if(specimen!=null && StringUtils.isNoneBlank(specimen.getId()) &&  StringUtils.isBlank(specimen.getStatus()) ){
			specimen.setStatus(QcTrfItemSpecimen.STATUS_OPEN);
			qcTrfLogService.saveQcTrfLog("/app/qc/qcTrf/setOpenSpecimen",null,specimen.getSpecimenId(),specimen.getStatus());
			this.updateById(specimen);
		}
	}

    public List<QcTrfItemSpecimen> findListByItemId(String qcTrfItemId) {
		return  baseMapper.findListByItemId(qcTrfItemId);
    }
}
