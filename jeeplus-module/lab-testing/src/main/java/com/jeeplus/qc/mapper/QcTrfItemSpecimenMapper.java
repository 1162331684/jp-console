/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.qc.domain.QcTrfItemSpecimen;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenDTO;

import java.util.List;

/**
 * 测试样本信息MAPPER接口
 * @author Lewis
 * @version 2021-12-17
 */
public interface QcTrfItemSpecimenMapper extends BaseMapper<QcTrfItemSpecimen> {

List<QcTrfItemSpecimen> findListByItemId(String qcTrfItemId);
}
