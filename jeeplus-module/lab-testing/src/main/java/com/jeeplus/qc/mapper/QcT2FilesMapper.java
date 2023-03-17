/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jeeplus.qc.domain.QcT2Files;
import com.jeeplus.qc.service.dto.QcT2FilesDTO;

/**
 * T2 导入文件MAPPER接口
 * @author zhimi
 * @version 2021-12-21
 */
public interface QcT2FilesMapper extends BaseMapper<QcT2Files> {

    IPage <QcT2FilesDTO> findList(Page <QcT2FilesDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
