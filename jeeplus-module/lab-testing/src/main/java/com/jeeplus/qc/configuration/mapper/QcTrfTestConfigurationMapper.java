/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.configuration.service.dto.QcTrfTestConfigurationDTO;
import com.jeeplus.qc.configuration.domain.QcTrfTestConfiguration;

/**
 * qc_trf_test_configurationMAPPER接口
 * @author maxteng
 * @version 2022-11-25
 */
public interface QcTrfTestConfigurationMapper extends BaseMapper<QcTrfTestConfiguration> {

    /**
     * 根据id获取qc_trf_test_configuration
     * @param id
     * @return
     */
    QcTrfTestConfigurationDTO findById(String id);
    /**
     * 获取qc_trf_test_configuration列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <QcTrfTestConfigurationDTO> findList(Page <QcTrfTestConfigurationDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
