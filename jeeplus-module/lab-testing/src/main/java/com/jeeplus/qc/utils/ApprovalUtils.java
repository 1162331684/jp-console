package com.jeeplus.qc.utils;


import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jeeplus.common.utils.ResponseUtil;

import com.jeeplus.common.utils.net.HttpUtil;
import com.jeeplus.qc.configuration.domain.QcTrfTestConfiguration;
import com.jeeplus.qc.configuration.service.QcTrfTestConfigurationService;
import com.jeeplus.qc.domain.WmsStockInfoParams;
import com.jeeplus.qc.service.QcTrfLogService;
import com.jeeplus.qc.service.dto.QcTrfDTO;
import com.jeeplus.sys.domain.DictValue;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.DictValueDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ApprovalUtils {

	/**
	 * 构造表单数据
	 * @param jsonObject
	 * @param user
	 * @param now
	 * @return
	 */
	public static Map<String, String> actionPlanFormData(JSONObject jsonObject, UserDTO user,String processDefinitionId, Date now){
		Map<String, String> params = new HashMap<>();
		String applicationNo = jsonObject.getString("trfId");
		params.put("processDefinitionId", processDefinitionId);
		params.put("title", applicationNo);
		params.put("assign", "");
		params.put("data", "");
		jsonObject.put("applicationNo", applicationNo);
		jsonObject.put("applicationDate", DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss"));
		if(null != user){
			jsonObject.put("applicantName", user.getName());
		}
		//TODO 申请人所在部门
		jsonObject.put("applicantDepartment", "");
		params.put("data",jsonObject.toJSONString());
		return  params;
	}
	public static ResponseEntity sendResultToWMSByTRF(QcTrfDTO qcTrfDTO,String result){
		List<WmsStockInfoParams> records = new ArrayList<>();
		WmsStockInfoParams record = new WmsStockInfoParams();
		record.setBarcode(qcTrfDTO.getBarcode());
		record.setResult(result);
		records.add(record);
		return sendResultToWMS(records);
	}
	public static ResponseEntity sendResultToWMS(List<WmsStockInfoParams> records){
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", Boolean.TRUE);
		responseUtil.add("message","发送成功");
		if(records == null || records.size() == 0){
			responseUtil.add("success", Boolean.FALSE);
			responseUtil.add("message","表单数据为空");
			return responseUtil.ok();
		}
		Map<String,String> headers = new HashMap<>();
		String token = new GetBpToken().getToken();
		if(StringUtils.isBlank(token)){
			token = new GetBpToken().getTokenByDefaultUser();
		}
		headers.put(GetBpToken.TOKEN, token);
		headers.put(GetBpToken.TOKEN_TYPE,GetBpToken.BOWKER_BASEPORTAL);
		Map params = new HashMap();
		params.clear();
		params.put("list",records);
		//TODO 告诉WMS测试结果
		String url = DictUtils.getDictValue("SendTestResultsToWMS", "sys_config", "");
		String returnResultByWMS = null;
		try {
			returnResultByWMS = OkHttpUtils.post(url, headers,params);
		} catch (IOException e) {
			e.printStackTrace();
			returnResultByWMS = e.getMessage();
		}
		params.put("returnResultByWMS",returnResultByWMS);
		((QcTrfLogService) SpringUtil.getBean(QcTrfLogService.class)).saveQcTrfLog2("SendTestResultsToWMS",null,null,null,JSON.toJSONString(params));
		return responseUtil.ok();
	}
	public static ResponseEntity sendEmailByTRF(QcTrfDTO qcTrfDTO){
		return sendUserEmailByTRF(qcTrfDTO,null);
	}
	public static ResponseEntity sendApprovalEmail(QcTrfDTO qcTrfDTO,String factory,String configuration){
		Set<String> sendBys = new HashSet<>();
		List<QcTrfTestConfiguration> list = ((QcTrfTestConfigurationService)SpringUtil.getBean(QcTrfTestConfigurationService.class)).lambdaQuery()
				.eq(QcTrfTestConfiguration::getFactory,factory)
				.eq(QcTrfTestConfiguration::getType,"approvalEmail")
				.eq(QcTrfTestConfiguration::getConfiguration,configuration).list();
		if(list.size() > 0){
			List<String> sendArr = list.stream().map(QcTrfTestConfiguration::getSupplement).distinct().collect(Collectors.toList());
			for(int i=0;i<sendArr.size();i++){
				UserDTO userDTO = UserUtils.get(sendArr.get(i));
				if(null != userDTO){
					sendBys.add(userDTO.getLoginName());
				}
			}
		}
		return sendUserEmailByTRF(qcTrfDTO,StringUtils.join(sendBys,","));
	}
	public static ResponseEntity sendUserEmailByTRF(QcTrfDTO qcTrfDTO,String sendTo){
		//TODO 邮件发送
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", Boolean.TRUE);
		responseUtil.add("message","邮件发送成功");
		if(null == qcTrfDTO || StringUtils.isBlank(qcTrfDTO.getTrfId())){
			responseUtil.add("success", Boolean.FALSE);
			responseUtil.add("message","表单数据为空");
			return responseUtil.ok();
		}
		Map<String,String> headers = new HashMap<>();
		headers.put(GetBpToken.TOKEN, new GetBpToken().getTokenByDefaultUser());
		headers.put(GetBpToken.TOKEN_TYPE,GetBpToken.BOWKER_BASEPORTAL);
		Map params = new HashMap();
		params.clear();
		params.put("id",DictUtils.getDictValue("TrfApprovalSendMsgId", "sys_config", ""));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("trfId", qcTrfDTO.getTrfId());
		params.put("param", JSON.toJSONString(jsonObject));
		if(StringUtils.isNotBlank(sendTo)){
			params.put("sendTo", sendTo);
		}
		String url = DictUtils.getDictValue("TrfApprovalSendMsg", "sys_config","");
		String returnResultByMsg = null;
		try {
			returnResultByMsg = OkHttpUtils.post(url,headers,params);
		} catch (IOException e) {
			e.printStackTrace();
			responseUtil.add("success", Boolean.FALSE);
			responseUtil.add("message",e.getMessage());
			returnResultByMsg = e.getMessage();
		}
		params.put("returnResultByMsg",returnResultByMsg);
		((QcTrfLogService) SpringUtil.getBean(QcTrfLogService.class)).saveQcTrfLog2(url,null,null,null,JSON.toJSONString(params));
		return responseUtil.ok();
	}

	/**
	 * 流程发起
	 * @param json
	 * @param user
	 * @return
	 */
	public static ResponseEntity approvalStart(JSONObject json,UserDTO user, String processDefinitionId){
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", Boolean.TRUE);
		if(null == json){
			responseUtil.add("success", Boolean.FALSE);
			responseUtil.add("message","表单数据为空");
			return responseUtil.ok();
		}
		Date now = new Date();
		String url = DictUtils.getDictValue("submitStartFormDataURL","sys_config","");
//		url = "http://localhost:8083/approval/flowable/form/submitStartFormData";
		if(StringUtils.isBlank(url)){
			responseUtil.add("success", Boolean.FALSE);
			responseUtil.add("message","流程审批启动接口路径未配置");
			return responseUtil.ok();
		}
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put(GetBpToken.TOKEN, new GetBpToken().getToken());
			headers.put(GetBpToken.TOKEN_TYPE,GetBpToken.BOWKER_BASEPORTAL);
			Map<String, String> formMap = ApprovalUtils.actionPlanFormData(json,user,processDefinitionId,now);
			String res = HttpUtil.post(url, formMap, headers);
			responseUtil.add("message","流程审批发起成功");
			responseUtil.add("body",res);
		} catch (Exception e) {
			e.printStackTrace();
			responseUtil.add("success", Boolean.FALSE);
			responseUtil.add("message",e.getMessage());
		}
		return responseUtil.ok();
	}
}
