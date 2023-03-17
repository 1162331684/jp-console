package com.jeeplus.qc.configuration.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.configuration.domain.QcLineConfiguration;
import com.jeeplus.qc.configuration.service.dto.QcLineConfigurationDTO;
import org.apache.ibatis.annotations.Param;


public interface QcLineConfigurationMapper extends BaseMapper<QcLineConfiguration> {

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    QcLineConfigurationDTO findById(String id);

    /**
     * 根据id
     *
     * @param id
     * @return
     */
    int updateByIds(@Param("id") String id, @Param("line_no") String line_no);
    /**
     * 获取测试项配置列表
     * @param queryWrapper
     * @return
     */
    IPage <QcLineConfigurationDTO> findList(Page <QcLineConfigurationDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
