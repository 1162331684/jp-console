/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.exception.bom.service.dto.ExceptionBomDTO;
import com.jeeplus.exception.bom.domain.ExceptionBom;

import java.util.List;
import java.util.Map;

/**
 * BomMAPPER接口
 * @author Young
 * @version 2022-11-12
 */
public interface ExceptionBomMapper extends BaseMapper<ExceptionBom> {

    /**
     * 根据id获取Bom
     * @param id
     * @return
     */
    ExceptionBomDTO findById(String id);

    /**
     * 获取Bom列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <ExceptionBomDTO> findList(Page <ExceptionBomDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    List<ExceptionBomDTO> findListByMap(@Param("map") Map<String,Object> map);
    List<ExceptionBomDTO> findDtoList(ExceptionBomDTO dto);

    int updateList(@Param("list") List<ExceptionBom> list);
    int saveList(List<ExceptionBom> list);
}
