/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.access.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.exception.access.service.dto.ExceptionListDTO;
import com.jeeplus.exception.access.domain.ExceptionList;

/**
 * Exception AccessMAPPER接口
 * @author Young
 * @version 2022-11-12
 */
public interface ExceptionListMapper extends BaseMapper<ExceptionList> {

    /**
     * 根据id获取Exception Access
     * @param id
     * @return
     */
    ExceptionListDTO findById(String id);

    /**
     * 获取Exception Access列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <ExceptionListDTO> findList(Page <ExceptionListDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
