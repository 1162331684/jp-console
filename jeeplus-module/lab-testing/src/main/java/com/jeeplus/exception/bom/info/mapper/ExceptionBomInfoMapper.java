/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.exception.bom.info.service.dto.ExceptionBomInfoDTO;
import com.jeeplus.exception.bom.info.domain.ExceptionBomInfo;

import java.util.List;

/**
 * Bom InfoMAPPER接口
 * @author Young
 * @version 2022-11-12
 */
public interface ExceptionBomInfoMapper extends BaseMapper<ExceptionBomInfo> {

    /**
     * 根据id获取Bom Info
     * @param id
     * @return
     */
    ExceptionBomInfoDTO findById(String id);

    /**
     * 获取Bom Info列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <ExceptionBomInfoDTO> findList(Page <ExceptionBomInfoDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    void removeByBomIds(@Param("list") List<String> ids);
    int saveList(@Param("list") List<ExceptionBomInfo> list);
    int updateList(@Param("list") List<ExceptionBomInfo> list);
}
