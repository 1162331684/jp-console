/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jeeplus.qc.configuration.domain.QcTrfTestConfiguration;
import com.jeeplus.qc.configuration.service.QcTrfTestConfigurationService;
import com.jeeplus.qc.domain.*;
import com.jeeplus.qc.service.dto.QcTrfTestContentDTO;
import com.jeeplus.qc.utils.ApiCommonUtils;
import com.jeeplus.qc.utils.ApprovalUtils;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.UserUtils;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.service.dto.QcTrfDTO;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenDTO;
import com.jeeplus.qc.service.mapstruct.QcTrfWrapper;
import com.jeeplus.qc.utils.TriggerSave;
import com.jeeplus.qc.service.mapstruct.QcTrfItemSpecimenTestingWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfItemSpecimenWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfItemWrapper;
import com.jeeplus.database.datamodel.utils.DataSourceUtils;
import com.jeeplus.qc.app.domain.QcAppTrfData;
import com.jeeplus.qc.mapper.QcTrfMapper;

/**
 * TRFService
 * @author Lewis
 * @version 2021-11-18
 */
@Service
@Transactional
public class QcTrfService extends ServiceImpl<QcTrfMapper, QcTrf> {
	@Autowired
	private QcTrfLogService qcTrfLogService;
	@Autowired
	private QcTrfTestConfigurationService qcTrfTestConfigurationService;
	/**
	* 子表service
	*/
	@Autowired
	private QcTrfItemService qcTrfItemService;
	
	@Autowired
	private QcTrfItemSpecimenService qcTrfItemSpecimenService;
	
	@Autowired
	private QcTrfItemSpecimenTestingService qcTrfItemSpecimenTestingService;
	@Autowired
	private QcTrfTestConditionService qcTrfTestConditionService;
	@Autowired
	private QcTrfTestContentService qcTrfTestContentService;

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage<QcTrf> findPage(Page<QcTrf> page, QueryWrapper queryWrapper,String status) {
		return  baseMapper.findList (page, queryWrapper);
	}

	public List<QcTrf> findListByAppSync(QcTrfDTO qcTrfDTO){
		return  baseMapper.findListByAppSync(qcTrfDTO);
	}

	public IPage<QcTrfDTO> findDTOPage(Page<QcTrf> page, QueryWrapper queryWrapper,QcTrfDTO qcTrfDTO) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		queryWrapper.in("a.factory",ApiCommonUtils.getFactoryRule(UserUtils.getCurrentUserDTO()));
		if(StringUtils.isNotBlank(qcTrfDTO.getCreateDateStart() )){
			queryWrapper.ge (true,"date_format(a.create_date,'%Y-%m-%d')", qcTrfDTO.getCreateDateStart() );
		}
		if(StringUtils.isNotBlank(qcTrfDTO.getCreateDateEnd() )){
			queryWrapper.le (true,"date_format(a.create_date,'%Y-%m-%d')", qcTrfDTO.getCreateDateEnd() );
		}
		if(StringUtils.isNotBlank(qcTrfDTO.getTaskStatus())){
			if(qcTrfDTO.getTaskStatus().equals("completed")){
				List<String> completed = new ArrayList<>();
				completed.add(QcTrf.STATUS_PASS);
				completed.add(QcTrf.STATUS_NOPASS);
				queryWrapper.in("a.status",completed);
			}else{
				queryWrapper.in("a.status",qcTrfDTO.getTaskStatus());
			}
//			queryWrapper.in("if(ifnull(a.status,'') = 'pass' or ifnull(a.status,'') = 'nopass' ,'completed',a.status)",qcTrfDTO.getTaskStatus());
		}
		if(StringUtils.isNotBlank(qcTrfDTO.getResultStatus())){
			queryWrapper.in("a.status",qcTrfDTO.getResultStatus());
//			queryWrapper.in("if(ifnull(a.status,'') != 'pass' and ifnull(a.status,'') != 'nopass' ,'',a.status)",qcTrfDTO.getResultStatus());
		}
		return  baseMapper.findDTOPage (page, queryWrapper);
	}
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public QcTrfDTO findById(String id) {
		QcTrfDTO qcTrfDTO = QcTrfWrapper.INSTANCE.toDTO ( super.getById ( id ) );
		qcTrfDTO.setQcTrfItemDTOList(qcTrfItemService.findList(id));
		return qcTrfDTO;
	}
	/**
	 * 保存或者更新
	 * @param  qcTrfDTO
	 * @return
	 */
	public void saveOrUpdate(QcTrfDTO qcTrfDTO) {
		if(StringUtils.isBlank(qcTrfDTO.getTrfId())){
			JSONArray tmp=DataSourceUtils.getDataBySql("GET_SERIAL_NUMBER", new HashMap<>());
			if(tmp.size()!=0){
				qcTrfDTO.setTrfId(tmp.getJSONObject(0).getString("serialNumber"));
			}
		}
		if(StringUtils.isBlank(qcTrfDTO.getStatus())){
			qcTrfDTO.setStatus(QcTrf.STATUS_TODO);
		}
		QcTrf qcTrf =  QcTrfWrapper.INSTANCE.toEntity ( qcTrfDTO );
		super.saveOrUpdate (qcTrf);
		List<String> itemIds=new ArrayList<>();
		for (QcTrfItemDTO qcTrfItemDTO : qcTrfDTO.getQcTrfItemDTOList ()){
			if ( CommonConstants.DELETED.equals ( qcTrfItemDTO.getDelFlag()) ){
				if(qcTrfItemService.hasSpecimenById( qcTrfItemDTO.getId ())==0){
					itemIds.add( qcTrfItemDTO.getId () );
					qcTrfItemService.removeById ( qcTrfItemDTO.getId () );
				}
				else{
					throw new RuntimeException(DictUtils.getLanguageLabel("请先清理对于数据下的关联数据", ""));
				}
			}else{
				QcTrfItem qcTrfItem = QcTrfItemWrapper.INSTANCE.toEntity ( qcTrfItemDTO );
				qcTrfItem.setQcTrfId ( qcTrf.getId () );
				qcTrfItemService.saveOrUpdate ( qcTrfItem );
			}
		}
		if(itemIds.size()>0){
			TriggerSave.saveDeleteLog(itemIds,"qc_trf_item");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void saveItemAll(QcTrfItemDTO qcTrfItemDTO,List<QcTrfItemSpecimenDTO> qcTrfItemSpecimens) {
		QcTrfItem qcTrfItem = QcTrfItemWrapper.INSTANCE.toEntity ( qcTrfItemDTO );
		qcTrfItemService.saveOrUpdate ( qcTrfItem );
		for (QcTrfItemSpecimenDTO qcTrfItemSpecimenDTO : qcTrfItemSpecimens){
			qcTrfItemSpecimenDTO.setQcTrfItemId(qcTrfItemDTO.getId());
			if ( CommonConstants.DELETED.equals ( qcTrfItemSpecimenDTO.getDelFlag()) ){
				qcTrfItemSpecimenService.removeById ( qcTrfItemSpecimenDTO.getId () );
			}else{
				qcTrfLogService.saveQcTrfLog("QcTrfService/saveItemAll",null,qcTrfItemSpecimenDTO.getSpecimenId(),qcTrfItemSpecimenDTO.getStatus());
				qcTrfItemSpecimenService.saveOrUpdate ( qcTrfItemSpecimenDTO );
			}
		}
		
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		if(getBaseMapper().hasSpecimenById(id)==0){
			TriggerSave.saveDeleteLog(id,"qc_trf");
			super.removeById ( id );
			List<String> itemIds=qcTrfItemService.lambdaQuery().eq(QcTrfItem::getQcTrfId, id).list()
					.stream().map(QcTrfItem::getId).collect(Collectors.toList());
			TriggerSave.saveDeleteLog(itemIds,"qc_trf_item");
			qcTrfItemService.lambdaUpdate ().eq(QcTrfItem::getQcTrfId, id ).remove ();
		}
		else{
			throw new RuntimeException(DictUtils.getLanguageLabel("请先清理对于数据下的关联数据", ""));
		}
	
	}


	/**
	 * TODO 如一条test_code 一个用户先删除，另一个用户 修改， 这个情况暂时不处理，
	 * 如果管理和网络好的情况下，不会有这个问题。
	 * 如果要出来，许添加较多的校验，增加数据库负担，建议优先从管理上处理
	 * @param qcAppTrfData
	 */
    @Transactional(rollbackFor = Exception.class)
	public void mergeAppUploadData(QcAppTrfData qcAppTrfData) {
		//  TODO 通过 changeTime 做同步策略  
    	List<QcTrfItemSpecimenTesting> qcTrfItemSpecimenTestings = qcAppTrfData.getQcTrfItemSpecimenTestings() ;
    	List<String> trfItemSpecimenIds = qcTrfItemSpecimenTestings.stream().map(e -> e.getQcTrfItemSpecimenId()).distinct().collect(Collectors.toList());
    	List<QcTrfItemSpecimen> qcTrfItemSpecimens = qcAppTrfData.getQcTrfItemSpecimens() ;
    	List<String> tmpIds = qcTrfItemSpecimens.stream().map(e -> e.getId()).distinct().collect(Collectors.toList());
    	trfItemSpecimenIds.addAll(tmpIds);
    	// 对Specimen下对testing进行更新
    	for(QcTrfItemSpecimen specimen: qcTrfItemSpecimens){
			List<QcTrfItemSpecimenTesting> list= qcTrfItemSpecimenTestings.stream().filter(t -> t.getQcTrfItemSpecimenId().equals(specimen.getId()))
					.collect(Collectors.toList());
			QcTrfItemSpecimen specimenSave = specimen;
			if(StringUtils.isNoneBlank(specimen.getId())){
    			QcTrfItemSpecimen specimenDB = qcTrfItemSpecimenService.getById(specimen.getId());
    			if(specimenDB ==null){// 如果已经被删除，则忽略数据
    				qcTrfItemSpecimenTestings.removeAll(list);
    				continue ;
    			}
    			if(null != specimenDB.getChangeTime() && null != specimen.getChangeTime()
						&& specimenDB.getChangeTime().after(specimen.getChangeTime())){// 谁先更新，用谁的数据
    				specimenSave = specimenDB;
    			}
    		}
			QcTrfItemSpecimenDTO specimenDto =  QcTrfItemSpecimenWrapper.INSTANCE.toDTO ( specimenSave );
			specimenDto.setQcTrfItemSpecimenTestingDTOList(QcTrfItemSpecimenTestingWrapper.INSTANCE.toDTO(list));
			qcTrfLogService.saveQcTrfLog("/app/qc/qcTrf/sync",null,specimenDto.getSpecimenId(),specimenDto.getStatus());
			qcTrfItemSpecimenService.saveOrUpdate(specimenDto);
			qcTrfItemSpecimenTestings.removeAll(list);
    	}
    	Set <String> permissions =UserUtils.getPermissions();
    	if(qcTrfItemSpecimenTestings.size()>0){
    		for(QcTrfItemSpecimenTesting qcTrfItemSpecimenTesting: qcTrfItemSpecimenTestings){
    			if(StringUtils.isBlank(qcTrfItemSpecimenTesting.getIsRequire())){
    				if(permissions.contains("qc:qcTrf:requirement")){
        				qcTrfItemSpecimenTesting.setIsRequire("1");
        			}
        			else{
        				qcTrfItemSpecimenTesting.setIsRequire("0");
        			}
				}
    		}
    		
    		List<String> tmp0 = qcTrfItemSpecimenTestings.stream().map(e -> e.getQcTrfItemSpecimenId()).distinct().collect(Collectors.toList());
    		List<QcTrfItemSpecimen> specimenDBs= qcTrfItemSpecimenService.lambdaQuery().in(QcTrfItemSpecimen::getId, tmp0).list();
    		Iterator<QcTrfItemSpecimenTesting> tmps= qcTrfItemSpecimenTestings.iterator();
    		while(tmps.hasNext()){
    			QcTrfItemSpecimenTesting tmp = tmps.next();
    			long size = specimenDBs.stream().filter(t -> t.getId().equals(tmp.getQcTrfItemSpecimenId())).count();
    			if(size==0){// 已经被删除的 specimen  ， 也忽略 test 的更新
    				tmps.remove();
    			}
    		}
    		Map<Integer, List<QcTrfItemSpecimenTesting>> temMap = qcTrfItemSpecimenTestings.stream().collect(Collectors.groupingBy(QcTrfItemSpecimenTesting::getDelFlag));
    		if(temMap.get(0) !=null && temMap.get(0).size()>0){
    			//如果已经录入数据的情况下，保存测试人
				List<QcTrfItemSpecimenTesting> updateTestingList = temMap.get(0);
				for(QcTrfItemSpecimenTesting updateTesting : updateTestingList){
					if(StringUtils.isNotBlank(updateTesting.getTestResult())){
						updateTesting.setTester(UserUtils.getCurrentUserDTO().getId());
						updateTesting.setTestTime(ApiCommonUtils.getUTCTimeByString());
					}
				}
    			qcTrfItemSpecimenTestingService.saveOrUpdateBatch(updateTestingList);
    		}
    		if(temMap.get(1) !=null && temMap.get(1).size()>0){
    			List<String> ids=temMap.get(1).stream().map(QcTrfItemSpecimenTesting::getId).collect(Collectors.toList());
    			qcTrfItemSpecimenTestingService.removeByIds(ids);
    		}
    	}
    	
    	this.updateStatus(trfItemSpecimenIds);
	}

    // 更新 TRF 的状态，  todo  doing  pass  nopass 
    public void updateStatus(List<String> specimenIds) {
    	if(specimenIds.size()>0){
        	List<String> trfIds= getBaseMapper().getIdsBySpecimenIds(StringUtils.join(specimenIds,"','") );
        	this.updateStatusByTrfIds(trfIds);
    	}
	}

	/**
	 * 开启审批流程
	 * @param ids
	 * @return
	 */
	public String approvalStartByTrf(String ids){
		List<String> idArray = new ArrayList<>();
		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			for(int i=0;i<idArr.length;i++){
				idArray.add(idArr[i]);
			}
		}
		List<String> statusArr = new ArrayList<>();
		statusArr.add(QcTrf.STATUS_PASS);
		statusArr.add(QcTrf.STATUS_NOPASS);
		QcTrfDTO trf = new QcTrfDTO();
		trf.setId(StringUtils.join(idArray,"','"));
		trf.setStatus(StringUtils.join(statusArr,"','"));
		List<QcTrfDTO> trfList = baseMapper.findDTOList(trf);
		if(0 == trfList.size()){
			return  "没有TRF记录";
		}
		for(QcTrfDTO qcTrfDTO:trfList){
			Map map = JSON.parseObject(JSON.toJSONString(qcTrfDTO), Map.class);
			qcTrfLogService.saveQcTrfLog2("trf/send/approval",null,null,null,JSON.toJSONString(map));
			ApprovalUtils.approvalStart(JSONObject.parseObject(JSON.toJSONString(map)),UserUtils.getCurrentUserDTO(),"TRFApproval");
			ApprovalUtils.sendResultToWMSByTRF(qcTrfDTO,qcTrfDTO.getResultStatus());
			ApprovalUtils.sendApprovalEmail(qcTrfDTO,qcTrfDTO.getFactory(),"system");
		}
		return "发送TRF成功";
	}

	/**
	 * 更新TRF并且开启审批
	 * @param trf
	 */
	public void updateTrfAndStartApproval(QcTrf trf){
		boolean isComplete = Boolean.FALSE;
		if(StringUtils.isNotBlank(trf.getId())
				&& StringUtils.isNotBlank(trf.getStatus())
				&& (trf.getStatus().equals(QcTrf.STATUS_PASS) || trf.getStatus().equals(QcTrf.STATUS_NOPASS))){
			isComplete = Boolean.TRUE;
			trf.setSubmitterId(UserUtils.getCurrentUserDTO().getId());
			trf.setTestResultsDate(ApiCommonUtils.getUTCTime());
		}
		this.updateById(trf);
		qcTrfLogService.saveQcTrfLog("updateTrfAndStartApproval",trf.getTrfId(),null,trf.getStatus());
		if(isComplete){
			approvalStartByTrf(trf.getId());
		}
	}

	/**
	 * 更新 TRF 的状态
	 * 状态有:todo  doing  pass  nopass
	 * @param trfIds
	 * @version 2022-06-29 15:39:56
	 */
    public void updateStatusByTrfIds(List<String> trfIds) {
    	List<QcTrfTestCondition> testConditionList= qcTrfTestConditionService.lambdaQuery().list();
    	for(String id: trfIds){
    		//获取trf所关联的数据（trf->item->specimen->testing）
    		QcTrfDTO trfDto= this.findById(id);
    		QcTrf trf=QcTrfWrapper.INSTANCE.toEntity(trfDto);
    		List<QcTrfItem> qcTrfItemList = qcTrfItemService.lambdaQuery().eq(QcTrfItem::getQcTrfId, id).list();

    		for (QcTrfItem qcTrfItem : qcTrfItemList) {
				String status="";
				qcTrfItem.setResult(null);
				List<QcTrfItemSpecimen> qcTrfItemSpecimenList = new ArrayList<>();
				if (qcTrfItemList.size() != 0) {
					List<String> ids2 = new ArrayList<>();
					ids2.add(qcTrfItem.getId());
					qcTrfItemSpecimenList = qcTrfItemSpecimenService.lambdaQuery().in(QcTrfItemSpecimen::getQcTrfItemId, ids2).list();
				}
				List<QcTrfItemSpecimenTesting> qcTrfItemSpecimenTestList = new ArrayList<>();
				if (qcTrfItemSpecimenList.size() != 0) {
					List<String> ids3 = qcTrfItemSpecimenList.stream().map(e -> e.getId()).distinct().collect(Collectors.toList());
					qcTrfItemSpecimenTestList = qcTrfItemSpecimenTestingService.lambdaQuery()
							.in(QcTrfItemSpecimenTesting::getQcTrfItemSpecimenId, ids3).list();
				}
				//app点击complete，specimen状态status=finish
				for (QcTrfItemSpecimen trfItemSpecimen : qcTrfItemSpecimenList) {
					if (!QcTrfItemSpecimen.STATUS_FINISH.equals(trfItemSpecimen.getStatus())) {
						status = QcTrfItemSpecimen.STATUS_NO_FINISH;
					}
				}
				//specimen是否在APP点击complete
				if (QcTrfItemSpecimen.STATUS_NO_FINISH.equals(status) || qcTrfItemSpecimenList.size() == 0 || qcTrfItemSpecimenTestList.size() == 0) {
					//筛选TestResult不为空对记录
					List<QcTrfItemSpecimenTesting> tmp = qcTrfItemSpecimenTestList.stream()
							.filter(t -> StringUtils.isNoneBlank(t.getTestResult()))
							.collect(Collectors.toList());
					//如果全为空，则状态TODO，否则状态DOING
					if (tmp.size() == 0) {
						qcTrfItem.setResult(QcTrf.STATUS_TODO);
					} else {
						qcTrfItem.setResult(QcTrf.STATUS_DOING);
					}
				} else {
					String res = QcTrf.STATUS_PASS;
					//循环便利所有对测试项，若存在一个测试项未NOPASS，则trf未NOPASS
					for (QcTrfItemSpecimenTesting test : qcTrfItemSpecimenTestList) {
						QcTrfTestContentDTO qcTrfTestContentDTO = qcTrfTestContentService.findById(test.getQcTestCodeId());
						if (qcTrfTestContentDTO != null
								&& StringUtils.isNotBlank(qcTrfTestContentDTO.getTestContent())
							&& StringUtils.isNotBlank(qcTrfTestContentDTO.getOp())
									&& StringUtils.isNotBlank(qcTrfTestContentDTO.getStdValue())) {
								try {
									res = this.calculationResults(qcTrfTestContentDTO, test);
									if (QcTrf.STATUS_NOPASS.equals(res)) {
										break;
									}
								} catch (Exception e) {
									res = QcTrf.STATUS_NOPASS;
									break;
								}
						}
					}
					qcTrfItem.setResult(res);
				}
				qcTrfItemService.updateResult(qcTrfItem);
			}
			trf.setStatus(QcTrf.STATUS_PASS);
    		Set<String> statusSet = qcTrfItemList.stream().map(item -> item.getResult()).distinct().collect(Collectors.toSet());
			if(statusSet.contains(QcTrf.STATUS_DOING)){
				trf.setStatus(QcTrf.STATUS_DOING);
			}
    		if(statusSet.contains(QcTrf.STATUS_TODO)){
				trf.setStatus(QcTrf.STATUS_TODO);
			}
			if(statusSet.contains(QcTrf.STATUS_NOPASS)){
				trf.setStatus(QcTrf.STATUS_NOPASS);
			}
			updateTrfAndStartApproval(trf);
    	}
	}

    private String calculationResults(QcTrfTestContentDTO testCode,QcTrfItemSpecimenTesting test) {
    	String res=QcTrf.STATUS_PASS;
    	// TODO N/A不进行测试
    	if(StringUtils.isNotBlank(test.getTestResult()) && !test.getTestResult().equals("N/A")) {
			switch (testCode.getOp()) {
				case "<": {
					Double requirement = Double.valueOf(testCode.getStdValue());
					Double testResult = Double.valueOf(test.getTestResult());
					if (testResult >= requirement) {
						res = QcTrf.STATUS_NOPASS;
					}
					break;
				}
				case ">": {
					Double requirement = Double.valueOf(testCode.getStdValue());
					Double testResult = Double.valueOf(test.getTestResult());
					if (testResult <= requirement) {
						res = QcTrf.STATUS_NOPASS;
					}
					break;
				}
				case "<=": {
					Double requirement = Double.valueOf(testCode.getStdValue());
					Double testResult = Double.valueOf(test.getTestResult());
					if (testResult > requirement) {
						res = QcTrf.STATUS_NOPASS;
					}
					break;
				}
				case ">=": {
					Double requirement = Double.valueOf(testCode.getStdValue());
					Double testResult = Double.valueOf(test.getTestResult());
					if (testResult < requirement) {
						res = QcTrf.STATUS_NOPASS;
					}
					break;
				}
				case "between": {
					String[] stds = testCode.getStdValue().split(",");
					Double requirement1 = Double.valueOf(stds[0]);
					Double requirement2 = Double.valueOf(stds[1]);

					Double testResult = Double.valueOf(test.getTestResult());
					if (!(requirement1 <= testResult && testResult <= requirement2)) {
						res = QcTrf.STATUS_NOPASS;
					}
					break;
				}
				case "no_between": {
					String[] stds = testCode.getStdValue().split(",");
					Double requirement1 = Double.valueOf(stds[0]);
					Double requirement2 = Double.valueOf(stds[1]);

					Double testResult = Double.valueOf(test.getTestResult());
					if (requirement1 <= testResult && testResult <= requirement2) {
						res = QcTrf.STATUS_NOPASS;
					}
					break;
				}
				case "=":
					if (!testCode.getStdValue().equals(test.getTestResult())) {
						res = QcTrf.STATUS_NOPASS;
					}
					break;
			}
		}
		return res;
	}
	
    public void syncD365Data(List<QcTrf> trfList , List<QcTrfItem> trfItemList) {
    	if(trfList.size()>0){
        	List<String> ids=trfList.stream().map(QcTrf::getId).collect(Collectors.toList());
        	List<QcTrf> dbTrfList = this.listByIds(ids);
        	List<QcTrf> saveTrfList=new ArrayList<>();
        	for(QcTrf trf :trfList){
        		long count= dbTrfList.stream().filter(t -> t.getId().equals(trf.getId())).count();
        		if(count == 0){
        			JSONArray tmp=DataSourceUtils.getDataBySql("GET_SERIAL_NUMBER", new HashMap<>());
        			if(tmp.size()!=0){
        				trf.setTrfId(tmp.getJSONObject(0).getString("serialNumber"));
        			}
        			saveTrfList.add(trf);
        		}
        	}
        	if(saveTrfList.size()>0){
        		this.saveBatch(saveTrfList);
        	}
        	
    	}

    	if(trfItemList.size()>0){
        	List<QcTrfItem> saveTrfItemList=new ArrayList<>();
        	List<String> ids2=trfItemList.stream().map(QcTrfItem::getId).collect(Collectors.toList());
        	List<QcTrfItem> dbTrfItemList = qcTrfItemService.listByIds(ids2);
        	for(QcTrfItem trfItem :trfItemList){
        		long count= dbTrfItemList.stream().filter(t -> t.getId().equals(trfItem.getId())).count();
        		if(count == 0){
        			saveTrfItemList.add(trfItem);
        		}
        	}
        	if(saveTrfItemList.size()>0){
        		qcTrfItemService.saveBatch(saveTrfItemList);
        	}
        	
    	}

	}

	public void updateAtpUploadingStatus(QcTrf qcTrf) {
		baseMapper.update(null,new LambdaUpdateWrapper<QcTrf>()
				.set(QcTrf::getAtpStatus,"1").eq(QcTrf::getId,qcTrf.getId()));
	}

	public void updateApprovalStatus(QcTrfDTO qcTrfDTO) {
		QcTrf qcTrf = QcTrfWrapper.INSTANCE.toEntity(qcTrfDTO);
    	if(qcTrfDTO.getLabLevel().compareTo(QcTrf.LAB1ST_LEVEL) == 0 && StringUtils.isNotBlank(qcTrf.getLab1stLevel())){
			baseMapper.update(null,new LambdaUpdateWrapper<QcTrf>()
					.set(QcTrf::getLab1stLevel,qcTrf.getLab1stLevel())
					.set(QcTrf::getLab1stLevelStatus,qcTrf.getLab1stLevelStatus())
					.eq(QcTrf::getId,qcTrf.getId()));
		}
		if(qcTrfDTO.getLabLevel().compareTo(QcTrf.LAB2ND_LEVEL) == 0 && StringUtils.isNotBlank(qcTrf.getLab2ndLevel())){
			baseMapper.update(null,new LambdaUpdateWrapper<QcTrf>()
					.set(QcTrf::getLab2ndLevel,qcTrf.getLab2ndLevel())
					.set(QcTrf::getLab2ndLevelStatus,qcTrf.getLab2ndLevelStatus())
					.eq(QcTrf::getId,qcTrf.getId()));
		}
		if(qcTrfDTO.getLabLevel().compareTo(QcTrf.LAB3RD_LEVEL) == 0 && StringUtils.isNotBlank(qcTrf.getLab3rdLevel())){
			baseMapper.update(null,new LambdaUpdateWrapper<QcTrf>()
					.set(QcTrf::getLab3rdLevel,qcTrf.getLab3rdLevel())
					.set(QcTrf::getLab3rdLevelStatus,qcTrf.getLab3rdLevelStatus())
					.eq(QcTrf::getId,qcTrf.getId()));
		}
		if(qcTrfDTO.getLabLevel().compareTo(QcTrf.LAB4TH_LEVEL) == 0 && StringUtils.isNotBlank(qcTrf.getLab4thLevel())){
			baseMapper.update(null,new LambdaUpdateWrapper<QcTrf>()
					.set(QcTrf::getLab4thLevel,qcTrf.getLab4thLevel())
					.set(QcTrf::getLab4thLevelStatus,qcTrf.getLab4thLevelStatus())
					.eq(QcTrf::getId,qcTrf.getId()));
		}
	}
}
