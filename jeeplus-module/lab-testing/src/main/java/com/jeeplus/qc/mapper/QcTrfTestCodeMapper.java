/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.service.dto.QcTrfTestCodeDTO;
import com.jeeplus.qc.domain.QcTrfTestCode;

/**
 * 测试项条件配置MAPPER接口
 * @author max teng
 * @version 2022-11-01
 */
public interface QcTrfTestCodeMapper extends BaseMapper<QcTrfTestCode> {

    /**
     * 根据id获取测试项条件配置
     * @param id
     * @return
     */
    QcTrfTestCodeDTO findById(String id);

    /**
     * 获取测试项条件配置列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <QcTrfTestCodeDTO> findList(Page <QcTrfTestCodeDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    IPage<QcTrfTestCodeDTO> findListByPackage(Page<QcTrfTestCodeDTO> page,@Param(Constants.WRAPPER)  QueryWrapper queryWrapper);
}
