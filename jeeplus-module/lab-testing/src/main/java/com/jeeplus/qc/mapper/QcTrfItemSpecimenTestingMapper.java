/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenTestingDTO;
import com.jeeplus.qc.domain.QcTrfItemSpecimenTesting;

/**
 * 测试内容MAPPER接口
 * @author Lewis
 * @version 2021-12-17
 */
public interface QcTrfItemSpecimenTestingMapper extends BaseMapper<QcTrfItemSpecimenTesting> {

    /**
     * 根据id获取测试内容
     * @param id
     * @return
     */
    QcTrfItemSpecimenTestingDTO findById(String id);

    /**
     * 获取测试内容列表
     *
     * @param QcTrfItemSpecimenId
     * @return
     */
    List <QcTrfItemSpecimenTestingDTO> findList(String QcTrfItemSpecimenId);

}
