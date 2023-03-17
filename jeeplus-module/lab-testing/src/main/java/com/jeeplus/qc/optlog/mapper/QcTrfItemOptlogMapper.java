/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.optlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.optlog.service.dto.QcTrfItemOptlogDTO;
import com.jeeplus.qc.optlog.domain.QcTrfItemOptlog;

/**
 * logMAPPER接口
 * @author max teng
 * @version 2022-11-25
 */
public interface QcTrfItemOptlogMapper extends BaseMapper<QcTrfItemOptlog> {

    /**
     * 根据id获取log
     * @param id
     * @return
     */
    QcTrfItemOptlogDTO findById(String id);

    /**
     * 获取log列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <QcTrfItemOptlogDTO> findList(Page <QcTrfItemOptlogDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
