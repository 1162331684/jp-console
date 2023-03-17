/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.domain.QcDataTrigger;
import com.jeeplus.qc.mapper.QcDataTriggerMapper;

/**
 * 操作记录Service
 * @author Lewis
 * @version 2021-12-08
 */
@Service
@Transactional
public class QcDataTriggerService extends ServiceImpl<QcDataTriggerMapper, QcDataTrigger> {

}
