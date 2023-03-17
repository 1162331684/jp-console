/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.common.utils.net.HttpUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;

import com.jeeplus.database.datamodel.utils.DataSourceUtils;
import com.jeeplus.qc.configuration.domain.QcTrfTestConfiguration;
import com.jeeplus.qc.configuration.service.QcTrfTestConfigurationService;
import com.jeeplus.qc.domain.*;
import com.jeeplus.qc.service.*;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenTestingDTO;

import com.jeeplus.qc.service.mapstruct.QcTrfItemSpecimenTestingWrapper;
import com.jeeplus.qc.service.mapstruct.QcTrfWrapper;
import com.jeeplus.qc.utils.ApprovalUtils;
import com.jeeplus.qc.utils.GetBpToken;

import com.jeeplus.qc.utils.OkHttpUtils;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.domain.QcTrfItemSpecimenTesting;
import com.jeeplus.qc.service.dto.QcTrfDTO;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenDTO;
import com.jeeplus.sys.utils.SpringThreadCacheUtils;

/**
 * TRFController
 * @author Lewis
 * @version 2021-11-18
 */
@Api(tags ="TRF")
@RestController
@RequestMapping(value = "/qc/qcTrf")
public class QcTrfController {
	@Autowired
	private QcTrfLogService qcTrfLogService;
	@Autowired
	private QcTrfService qcTrfService;

	@Autowired
	private QcTrfItemService qcTrfItemService;
	@Autowired
	private QcTrfItemSpecimenService qcTrfItemSpecimenService;
	@Autowired
	private QcTrfItemSpecimenTestingService qcTrfItemSpecimenTestingService;

	@Autowired
	private QcTrfItemSpecimenTestingWrapper qcTrfItemSpecimenTestingWrapper;
	@Autowired
	private QcTrfTestConfigurationService qcTrfTestConfigurationService;

	@ApiLog("发起TRF审批")
	@ApiOperation(value = "发起TRF审批")
	@GetMapping("trf/send/approval")
	public ResponseEntity <String> sendApproval(String ids) throws Exception {
		return ResponseEntity.ok(qcTrfService.approvalStartByTrf(ids));

	}


	@ApiLog("审批签字")
	@ApiOperation(value = "审批签字")
	@PostMapping("trf/approval")
	public ResponseEntity approval(@RequestBody QcTrfDTO qcTrfDTO){
		try {
			if(StringUtils.isNotBlank(qcTrfDTO.getId()) && StringUtils.isNotBlank(qcTrfDTO.getLabLevel())){
				qcTrfService.updateApprovalStatus(qcTrfDTO);
				QcTrfDTO qcTrfDTOById = qcTrfService.findById ( qcTrfDTO.getId());
				String result = "";
				if(StringUtils.isNotBlank(qcTrfDTOById.getStatus())) {
					if (qcTrfDTOById.getStatus().equals(QcTrf.STATUS_NOPASS)) {
						result = QcTrf.TASK_RESULT_FAIL;
					}
					if (qcTrfDTOById.getStatus().equals(QcTrf.STATUS_PASS)) {
						result = QcTrf.TASK_RESULT_PASS;
					}
				}
				if(StringUtils.isNotBlank(result)){
					ApprovalUtils.sendApprovalEmail(qcTrfDTO,qcTrfDTO.getFactory(),qcTrfDTO.getLabLevel());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			qcTrfDTO.setRemarks(e.getMessage());
		}
		qcTrfLogService.saveQcTrfLog2("trf/approval",null,null,null,JSON.toJSONString(qcTrfDTO));
		return ResponseEntity.ok( "回调成功" );
	}

	@ApiLog("ATP上传")
	@ApiOperation(value = "ATP上传")
	@GetMapping("trf/atpUploading")
	public ResponseEntity <String> atpUploading(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			QcTrf qcTrf = new QcTrf();
			qcTrf.setId(id);
			qcTrfService.updateAtpUploadingStatus(qcTrf);
		}
//		qcTrfService.updateStatusByTrfIds(Arrays.asList(idArray));
		return ResponseEntity.ok( "上传ATP成功" );
	}

	@ApiLog("通过WMS Sample创建TRF")
	@ApiOperation(value = "通过WMS Sample创建TRF")
	@PostMapping("data/wms/sample/create")
	public ResponseEntity wmsSampleCreate(@RequestBody List<QcTrfDTO> dataList){

		for (QcTrfDTO qcTrfDTO : dataList){
			qcTrfService.saveOrUpdate (qcTrfDTO);
		}
		return ResponseEntity.ok ( "创建TRF成功" );
	}

	@ApiLog("查询WMS Sample列表数据")
	@ApiOperation(value = "查询WMS Sample列表数据")
	@GetMapping("data/wms/sample")
	public ResponseEntity getWmsSample(WmsStockInfoParams wmsStockInfoParams){
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", true);
		Map<String,Object> r =new HashMap<>();
		r.put("records",new ArrayList<>());
		r.put("total",0);
		r.put("pageNo",wmsStockInfoParams.getPageNo());
		r.put("pageSize",wmsStockInfoParams.getPageSize());
		try {
			Map<String,String> headers = new HashMap<>();
			headers.put("loginName", UserUtils.getCurrentUserDTO().getLoginName());
			headers.put(GetBpToken.TOKEN, new GetBpToken().getTokenByDefaultUser());
			headers.put(GetBpToken.TOKEN_TYPE,GetBpToken.BOWKER_BASEPORTAL);
			Map params = JSON.parseObject(JSON.toJSONString(wmsStockInfoParams), Map.class);
			String result = HttpUtil.get(DictUtils.getDictValue("WmsStockInfoList", "sys_config", ""),params, headers);
			if(StringUtils.isNotBlank(result)){
				JSONObject resultObj = JSON.parseObject(result);
				JSONObject output = resultObj.getJSONObject("output");
				if(null != output){
					for(Map.Entry<String,Object> tmp : output.entrySet()){
						r.put(tmp.getKey(),tmp.getValue());
						if(tmp.getKey().equals("rows")){
							JSONArray rows = output.getJSONArray("rows");
							if(rows.size() > 0){
								JSONArray records = new JSONArray();
								for(int i=0;i<rows.size();i++){
									JSONObject jsonObject = rows.getJSONObject(i);
									JSONObject record = new JSONObject();
									for(Map.Entry<String, Object> tmp1 : jsonObject.entrySet()){
										record.put(tmp1.getKey().replaceAll(" ","").toLowerCase(),tmp1.getValue());
									}
									records.add(record);
								}
								r.put("records",records);
							}
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			responseUtil.add("success", false);
			responseUtil.add("message", e.getMessage());
		}
		responseUtil.putAll(r);
		return responseUtil.ok();
	}


	/**
	 * TRF列表数据
	 */
	@ApiLog("查询TRF列表数据")
	@ApiOperation(value = "查询TRF列表数据")
	@PreAuthorize("hasAuthority('qc:qcTrf:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfDTO>> list(QcTrfDTO qcTrfDTO, Page<QcTrf> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfDTO, QcTrfDTO.class);
		IPage<QcTrfDTO> list = qcTrfService.findDTOPage (page, queryWrapper,qcTrfDTO);

		List<QcTrfDTO> moList = list.getRecords().stream().filter(t -> StringUtils.isNotBlank(t.getMo())).collect(Collectors.toList());

		if(moList.size() > 0){
			List<String> mos =moList.stream().map(QcTrfDTO::getMo).collect(Collectors.toList());
			Map<String,String> param = new HashMap<>();
			param.put("mo", StringUtils.join(mos.toArray(),"','"));
			JSONArray jsonArray =  DataSourceUtils.getDataBySql("ADB_MO_INFO",param);
			if(jsonArray.size() > 0){
				for(QcTrfDTO trfDTO : list.getRecords()){
					if(StringUtils.isNotBlank(trfDTO.getMo())){
						for(int i=0;i<jsonArray.size();i++){
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							if(trfDTO.getMo().compareTo(jsonObject.getString("mo")) == 0){
								trfDTO.setCpo(jsonObject.getString("cpo"));
								trfDTO.setCrd(jsonObject.getString("crd"));
								trfDTO.setPsdd(jsonObject.getString("psdd"));
							}
						}
					}
				}
			}
		}
		return ResponseEntity.ok (list);
	}


	@ApiLog("查询TRF数据")
	@ApiOperation(value = "查询TRF数据")
	@GetMapping("trf/testing/info")
	public ResponseEntity testingInformationInputData(QcTrfItemDTO qcTrfItemDTO) throws Exception {
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", true);
		try {
			QcTrfItemDTO trfItemDTO = qcTrfItemService.findById(qcTrfItemDTO.getId());
			if(null != trfItemDTO){
				QcTrfDTO trfDTO = qcTrfService.findById(trfItemDTO.getQcTrf().getId());
				responseUtil.add("trf",trfDTO);
			}
			responseUtil.add("item",trfItemDTO);
		} catch (Exception e) {
			e.printStackTrace();
			responseUtil.add("success", false);
			responseUtil.add("msg", e.getMessage());
		}
		return responseUtil.ok();
	}

	/**
	 * 获取TRF所有关联记录
	 * @param qcTrfDTO
	 * @return
	 * @throws Exception
	 */
	@ApiLog("查询TRF数据")
	@ApiOperation(value = "查询TRF数据")
	@GetMapping("trf/byId")
	public ResponseEntity trfData(QcTrfDTO qcTrfDTO) throws Exception {
		if(StringUtils.isNotBlank(qcTrfDTO.getId())){
			Map<String,Object> r =new HashMap<>();
			//qc_trf的id查询Trf和TrfItem的所有记录
			List<String> ids = new ArrayList<>();
			if(StringUtils.isNotBlank(qcTrfDTO.getId())){
				String[] idArr = qcTrfDTO.getId().split(",");
				for(int i=0;i<idArr.length;i++){
					ids.add(idArr[i]);
				}
			}

			List<QcTrf> trfList= qcTrfService.lambdaQuery().in(QcTrf::getId,ids).list();
			List<QcTrfItem> qcTrfItemList= qcTrfItemService.lambdaQuery().in(QcTrfItem::getQcTrfId, ids).list();
			r.put("trfs", trfList);
			r.put("trfItemts", qcTrfItemList);
			//qc_trf_item的id查询所有的Specimen
			ids=qcTrfItemList.stream().map(QcTrfItem::getId).collect(Collectors.toList());
			if(ids.size() > 0){
				List<QcTrfItemSpecimen> qcTrfItemSpecimenList= qcTrfItemSpecimenService.lambdaQuery()
						.in(QcTrfItemSpecimen::getQcTrfItemId, ids).list();
				r.put("trfItemSpecimen", qcTrfItemSpecimenList);
				//qc_trf_item_specimen的id查询所有的Testing
				ids=qcTrfItemSpecimenList.stream().map(QcTrfItemSpecimen::getId).collect(Collectors.toList());
				if(ids.size() > 0){
					List<QcTrfItemSpecimenTesting> qcTrfItemSpecimenTestingList= qcTrfItemSpecimenTestingService.lambdaQuery()
							.in(QcTrfItemSpecimenTesting::getQcTrfItemSpecimenId, ids).list();
					List<QcTrfItemSpecimenTestingDTO> qcTrfItemSpecimenTestingDTOList = qcTrfItemSpecimenTestingWrapper.toDTO(qcTrfItemSpecimenTestingList);
					for(QcTrfItemSpecimenTestingDTO test:qcTrfItemSpecimenTestingDTOList ){
						if(test.getCreateBy()!=null&&StringUtils.isNoneBlank(test.getCreateBy().getId())){
							test.setCreateBy(UserUtils.get(test.getCreateBy().getId()));
						}
						if(test.getUpdateBy()!=null&&StringUtils.isNoneBlank(test.getUpdateBy().getId())){
							test.setUpdateBy(UserUtils.get(test.getUpdateBy().getId()));
						}
						if(test.getUpdateBy()!=null&&StringUtils.isNoneBlank(test.getUpdateBy().getId())){
							test.setTester(UserUtils.get(test.getTester().getId()));
						}
					}
					r.put("trfItemSpecimenTestingList", qcTrfItemSpecimenTestingDTOList);
				}
			}
			ResponseUtil responseUtil = new ResponseUtil ( );
			responseUtil.add("success", true);
			responseUtil.putAll(r);
			return responseUtil.ok();
		}else{
			return ResponseEntity.ok ("");
		}

	}

	/**
	 * 根据Id获取TRF数据
	 */
	@ApiLog("根据Id获取TRF数据")
	@ApiOperation(value = "根据Id获取TRF数据")
	@PreAuthorize("hasAnyAuthority('qc:qcTrf:view','qc:qcTrf:add','qc:qcTrf:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfService.findById ( id ) );
	}
	/**
	 * 保存TRF
	 */
	@ApiLog("保存TRF")
	@ApiOperation(value = "保存TRF")
	@PreAuthorize("hasAnyAuthority('qc:qcTrf:add','qc:qcTrf:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfDTO qcTrfDTO) {
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", true);
		//新增或编辑表单保存
		qcTrfService.saveOrUpdate (qcTrfDTO);
		responseUtil.add("msg", "保存TRF成功");
        return responseUtil.ok ();
	}

	/**
	 * 删除TRF
	 */
	@ApiLog("删除TRF")
	@ApiOperation(value = "删除TRF")
	@PreAuthorize("hasAuthority('qc:qcTrf:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", true);
		responseUtil.add("msg", "删除TRF成功");
		try {
			String idArray[] = ids.split(",");
			for(String id: idArray){
				qcTrfService.removeById ( id );
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseUtil.add("success", false);
			responseUtil.add("msg", e.getMessage());
		}
		return responseUtil.ok();
	}

	/**
	 * 保存TRF
	 */
	@ApiLog("保存TRF")
	@ApiOperation(value = "保存TRF")
	@PreAuthorize("hasAnyAuthority('qc:qcTrf:add','qc:qcTrf:edit')")
	@PostMapping("saveItemAll")
	public  ResponseEntity <String> saveItemAll(@Valid @RequestBody Map<String, Object> param ) {
		SpringThreadCacheUtils.setParam(JSON.toJSONString(param));
		QcTrfItemDTO qcTrfItemDTO = JSON.parseObject((String)param.get("qcTrfItemDTO"),QcTrfItemDTO.class) ;
		List<QcTrfItemSpecimenDTO> qcTrfItemSpecimens = JSON.parseArray((String)param.get("qcTrfItemSpecimens"), QcTrfItemSpecimenDTO.class);
		//新增或编辑表单保存
		qcTrfService.saveItemAll (qcTrfItemDTO,qcTrfItemSpecimens);
		qcTrfService.updateStatusByTrfIds(Arrays.asList( qcTrfItemDTO.getQcTrf().getId()));
        return ResponseEntity.ok ( "保存TRF成功" );
	}
	
	@PostMapping("updateTrfStatus")
	public  ResponseEntity <String> updateTrfStatus(@Valid @RequestBody  List<String> ids ) {
		qcTrfService.updateStatusByTrfIds(ids);
        return ResponseEntity.ok ( "保存TRF成功" );
	}
	
}
