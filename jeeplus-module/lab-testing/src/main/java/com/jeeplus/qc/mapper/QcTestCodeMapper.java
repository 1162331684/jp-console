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
import com.jeeplus.qc.service.dto.QcTestCodeDTO;
import com.jeeplus.qc.domain.QcTestCode;

/**
 * 测试项配置MAPPER接口
 * @author zhimi
 * @version 2021-12-08
 */
public interface QcTestCodeMapper extends BaseMapper<QcTestCode> {

    /**
     * 根据id获取测试项配置
     * @param id
     * @return
     */
    QcTestCodeDTO findById(String id);

    /**
     * 获取测试项配置列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <QcTestCodeDTO> findList(Page <QcTestCodeDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
