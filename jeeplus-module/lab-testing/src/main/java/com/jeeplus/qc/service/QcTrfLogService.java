/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.domain.QcTrfLog;
import com.jeeplus.qc.mapper.QcTrfLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QcTrfLogService extends ServiceImpl<QcTrfLogMapper, QcTrfLog> {
    public void saveQcTrfLog(String interfaces,String trfId,String specimenId,String status){
        saveQcTrfLog2(interfaces,trfId,specimenId,status,null);
    }
    public void saveQcTrfLog2(String interfaces,String trfId,String specimenId,String status,String remarks){
        QcTrfLog qcTrfLog = new QcTrfLog();
        qcTrfLog.setInterfaces(interfaces);
        qcTrfLog.setTrfId(trfId);
        qcTrfLog.setSpecimenId(specimenId);
        qcTrfLog.setStatus(status);
        qcTrfLog.setRemarks(remarks);
        this.saveOrUpdate(qcTrfLog);
    }

}
