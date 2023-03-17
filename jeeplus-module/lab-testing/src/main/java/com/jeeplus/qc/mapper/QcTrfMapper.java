/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.domain.QcTrf;
import com.jeeplus.qc.service.dto.QcTrfDTO;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;
import org.apache.ibatis.annotations.Param;

/**
 * TRFMAPPER接口
 * @author Lewis
 * @version 2021-11-18
 */
public interface QcTrfMapper extends BaseMapper<QcTrf> {

    Integer hasSpecimenById(String id);
    
    List<String> getIdsByItemIds(String trfItemIds);
    
    List<String> getIdsBySpecimenIds(String specimenIds);

    IPage<QcTrf> findList(Page<QcTrf> page,@Param(Constants.WRAPPER)  QueryWrapper queryWrapper);
    IPage<QcTrfDTO> findDTOPage(Page<QcTrf> page,@Param(Constants.WRAPPER)  QueryWrapper queryWrapper);
    List<QcTrfDTO> findDTOList(QcTrfDTO qcTrfDTO);
    List<QcTrf> findListByAppSync(QcTrfDTO qcTrfDTO);

}
