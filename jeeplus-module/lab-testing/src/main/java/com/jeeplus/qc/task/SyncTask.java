package com.jeeplus.qc.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.net.HttpUtil;
import com.jeeplus.common.utils.translation.MD5;
import com.jeeplus.qc.domain.QcTrf;
import com.jeeplus.qc.domain.QcTrfItem;
import com.jeeplus.qc.service.QcTrfItemService;
import com.jeeplus.qc.service.QcTrfService;
import com.jeeplus.quartz.domain.ScheduleJob;
import com.jeeplus.quartz.domain.Task;
import com.jeeplus.quartz.util.CommonServer;
import com.jeeplus.sys.domain.DictType;
import com.jeeplus.sys.domain.DictValue;
import com.jeeplus.sys.service.DictTypeService;
import com.jeeplus.sys.service.DictValueService;
import com.jeeplus.sys.utils.DictUtils;

import cn.hutool.extra.spring.SpringUtil;

public class SyncTask  extends Task  {

	private QcTrfService qcTrfService=null;
	private QcTrfItemService qcTrfItemService=null;
	private DictValueService dictValueService=null;
	private DictTypeService  dictTypeService=null;
	
	private  void init() {
		if(qcTrfService==null || qcTrfItemService ==null || dictValueService ==null || dictTypeService == null){
			init2();
		}
	}
	private synchronized  void init2() {
		if(qcTrfItemService==null || qcTrfService ==null || dictValueService ==null || dictTypeService == null){
			qcTrfService = SpringUtil.getBean(QcTrfService.class);
			qcTrfItemService =SpringUtil.getBean(QcTrfItemService.class);
			dictValueService =SpringUtil.getBean(DictValueService.class);
			dictTypeService =SpringUtil.getBean(DictTypeService.class);
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Object scheduleJob) {
		init();
		try {
			String id =scheduleJob.getClass().getMethod("getId").invoke(scheduleJob).toString();
			ScheduleJob schedule= CommonServer.getScheduleJob(id);
			String param =schedule.getParam() ==null?"": schedule.getParam();
			 String token= getToken ();
			 String getTrfListUrl="https://dev7113aeb62110517415aos.cloudax.dynamics.com/api/services/GAR_GetDevelopmentServiceGroup/DevelopmentService/getDevelopmentList";
			 getTrfListUrl = DictUtils.getDictValue("getTrfListUrl", "sys_config_d365", getTrfListUrl);
			 
			 Map<String,String> headers=new HashMap<>();
			 headers.put("Content-Type", "application/json");
			 headers.put("Authorization", "Bearer "+token);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String startTiemString=null;
			 DictType dictType = dictTypeService.lambdaQuery().eq(DictType::getType,"sys_config_d365").one();
			 DictValue startTiemDictValue= dictValueService.lambdaQuery().eq(DictValue::getDictTypeId, dictType.getId()).eq(DictValue::getLabel, "lastSyncTime").one(); // 直接拿数据库数据，解决缓存问题;
			 startTiemString = startTiemDictValue.getValue();
			 if(StringUtils.isBlank(startTiemString)){
				 SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				 startTiemString = sdf2.format(new Date());
			 }
			 else{
				 Date startTime = sdf.parse(startTiemString);
				 startTime = new Date(startTime.getTime() - 10000);
				 startTiemString = sdf.format(startTime);
			 }
			 Date endTime=new Date(new Date().getTime() ); // 8小时  + 8*3600000
			 String endTimeString = sdf.format(endTime);
			 
			 // startTiemString = "2021-12-13 07:00:00" ;endTimeString =  "2022-01-04 08:00:00" ;
			 String paramString=String.format("{\"_request\":{\"LastModifiedDateTime\":\"%s\",\"ToLastModifiedDateTime\":\"%s\"}}",
					 startTiemString, endTimeString);
			 if(!StringUtils.isBlank(param)){
				 paramString = param;
			 }
			 String resText=HttpUtil.post(getTrfListUrl, paramString, headers);
			 JSONObject res=JSON.parseObject(resText);
			 JSONArray list = res.getJSONArray("DevelopmentList");
			 List<QcTrf> trfList=new ArrayList<>();
			 List<QcTrfItem> trfItemList=new ArrayList<>();
			 for(int i=0;list.size() > i ;i++){
				 QcTrf trf=new QcTrf();
				 d365DataToQcTrf(list.getJSONObject(i),trf);
				 if(!StringUtils.isBlank(trf.getId())){
					 long count= trfList.stream().filter(t -> t.getId().equals(trf.getId())).count();
					 if(count ==0){
						 trfList.add(trf);
					 }
				 }
				 QcTrfItem qcTrfItem=new QcTrfItem();
				 d365DataToQcTrfItem(list.getJSONObject(i), qcTrfItem, trf);
				 if(!StringUtils.isBlank(qcTrfItem.getId())){
					 long count= trfItemList.stream().filter(t -> t.getId().equals(qcTrfItem.getId())).count();
					 if(count ==0){
						 trfItemList.add(qcTrfItem);
					 }
				 }
			 }
			 if(trfList.size()>0||trfItemList.size()>0){
				 qcTrfService.syncD365Data(trfList, trfItemList);
			 }
			 if(!StringUtils.isBlank(param)){
				 startTiemDictValue.setValue(endTimeString);
				 dictValueService.saveOrUpdate(startTiemDictValue);
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private  void d365DataToQcTrfItem(JSONObject json,QcTrfItem qcTrfItem,QcTrf trf) {
		 if(StringUtils.isBlank(json.getString("ProductType"))||StringUtils.isBlank(json.getString("ReferenceCode"))||
			StringUtils.isBlank(json.getString("VendorName"))||StringUtils.isBlank(json.getString("VendorCode"))||StringUtils.isBlank(json.getString("ColorName"))){
				 System.out.println(json);
				 return ;
		 }
		 qcTrfItem.setMaterialType(json.getString("ProductType"));
		 qcTrfItem.setMaterialItemCode(json.getString("ReferenceCode"));
		 qcTrfItem.setT2Name(json.getString("VendorName"));
		 qcTrfItem.setT2Code(json.getString("VendorCode"));
		 qcTrfItem.setColorCode(json.getString("ColorName")); 
		 qcTrfItem.setQcTrfId(trf.getId());
		 qcTrfItem.setId(MD5.md5( qcTrfItem.getMaterialType() + qcTrfItem.getMaterialItemCode() 
		 	+ qcTrfItem.getT2Name() + qcTrfItem.getT2Code() 
		 	+ qcTrfItem.getColorCode()+  qcTrfItem.getQcTrfId() ));
	 }
	 private void  d365DataToQcTrf(JSONObject json,QcTrf trf) {
		 if(StringUtils.isBlank(json.getString("BNumber"))||StringUtils.isBlank(json.getString("WorkingNo"))||
			StringUtils.isBlank(json.getString("Article"))||StringUtils.isBlank(json.getString("Season"))){
			 System.out.println(json);
			 return ;
		 }
		 trf.setBno(json.getString("BNumber"));
		 trf.setWorkingNo(json.getString("WorkingNo"));
		 trf.setArticle(json.getString("Article"));
		 trf.setSeason(json.getString("Season"));
		 trf.setId(MD5.md5(trf.getBno()+trf.getWorkingNo()+trf.getArticle()+trf.getSeason()));		 
	}
	 
	 private  JSONObject getServerToken() throws Exception {
		 Date d=new Date();
		 String url="https://login.microsoftonline.com/winhanverky.onmicrosoft.com/oauth2/token";
		 url = DictUtils.getDictValue("tokenUrl", "sys_config_d365", url);
		 Map<String,String> headers=new HashMap<>();
		 headers.put("Content-Type", "application/x-www-form-urlencoded");
		 Map<String,String> params=new HashMap<>();
		 params.put("grant_type", "client_credentials");
		 String clientId = "cdafdad2-a6e7-4898-87aa-1091d270b3c7" ; 
		 clientId = DictUtils.getDictValue("clientId", "sys_config_d365", clientId);
		 params.put("client_id",clientId );
		 
		 String clientSecret ="7duwO6nE2AVFIf/8UvvngnjzCgv0r9rgmAmKrX/dXMI="; 
		 clientSecret = DictUtils.getDictValue("clientSecret", "sys_config_d365", clientSecret);
		 params.put("client_secret",clientSecret );
		 
		 String resource ="https://dev7113aeb62110517415aos.cloudax.dynamics.com"; 
		 resource = DictUtils.getDictValue("resource", "sys_config_d365", resource);
		 params.put("resource",resource );
		 
		 String resText=HttpUtil.post(url, params, headers);
		 JSONObject resObj=JSON.parseObject(resText);
		 JSONObject token=new JSONObject();
		 token.put("token", resObj.getString("access_token"));
		 token.put("expiresDate", new Date(d.getTime()+resObj.getLongValue("expires_in")));
		 
		 return token;
	 }
	 
	 private  String getToken () throws Exception {
		 JSONObject token =new JSONObject();
		 if(token.getDate("expiresDate")==null || token.getDate("expiresDate").getTime() < new Date().getTime()){
			 token = getServerToken();
		 }
		 return token.getString("token");
	}
}
