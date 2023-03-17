package com.jeeplus.qc.utils;


import java.util.ArrayList;
import java.util.List;

import com.jeeplus.qc.domain.QcDataTrigger;
import com.jeeplus.qc.service.QcDataTriggerService;

import cn.hutool.extra.spring.SpringUtil;

public class TriggerSave {
	private  static QcDataTriggerService qcDataTriggerService;
	private static void init(){
		if(qcDataTriggerService==null){
			init2();
		}
	}
	private synchronized  static void  init2(){
		qcDataTriggerService = SpringUtil.getBean(QcDataTriggerService.class);
	}
	
	public static void saveDeleteLog(List<String> idList,String tableName) {
		init();
		List<QcDataTrigger> list=new ArrayList<>();
    	for(String id:idList){
    		QcDataTrigger qcDataTrigger=new QcDataTrigger();
    		qcDataTrigger.setOpcode(QcDataTrigger.DELETE);
    		qcDataTrigger.setTableName(tableName);
    		qcDataTrigger.setTableKeyId(id);
    		list.add(qcDataTrigger);
    	}
    	qcDataTriggerService.saveBatch(list);
	}
	
	public static void saveDeleteLog(String id,String tableName) {
		init();
		QcDataTrigger qcDataTrigger=new QcDataTrigger();
		qcDataTrigger.setOpcode(QcDataTrigger.DELETE);
		qcDataTrigger.setTableName(tableName);
		qcDataTrigger.setTableKeyId(id);
    	qcDataTriggerService.save(qcDataTrigger);
	}
}
