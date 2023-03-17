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
import com.jeeplus.qc.optlog.service.dto.QcTrfOptlogDTO;
import com.jeeplus.qc.optlog.domain.QcTrfOptlog;

/**
 * optlogMAPPER接口
 * @author max
 * @version 2022-11-25
 */
public interface QcTrfOptlogMapper extends BaseMapper<QcTrfOptlog> {

    /**
     * 根据id获取optlog
     * @param id
     * @return
     */
    QcTrfOptlogDTO findById(String id);

    /**
     * 获取optlog列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <QcTrfOptlogDTO> findList(Page <QcTrfOptlogDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
