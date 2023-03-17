/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;
import com.jeeplus.qc.domain.QcTrfItem;

/**
 * 物料信息MAPPER接口
 * @author Lewis
 * @version 2021-11-18
 */
public interface QcTrfItemMapper extends BaseMapper<QcTrfItem> {

    /**
     * 根据id获取物料信息
     * @param id
     * @return
     */
    QcTrfItemDTO findById(String id);

    /**
     * 获取物料信息列表
     *
     * @param QcTrfId
     * @return
     */
    List <QcTrfItemDTO> findList(String QcTrfId);
    
   
    Integer hasSpecimenById(String id);

}
