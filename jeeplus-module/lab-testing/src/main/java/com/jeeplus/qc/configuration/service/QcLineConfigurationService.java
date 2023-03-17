package com.jeeplus.qc.configuration.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.configuration.domain.QcLineConfiguration;
import com.jeeplus.qc.configuration.mapper.QcLineConfigurationMapper;
import com.jeeplus.qc.configuration.service.dto.QcLineConfigurationDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



    @Service
    @Transactional
    public class QcLineConfigurationService extends ServiceImpl<QcLineConfigurationMapper, QcLineConfiguration> {

        /**
         * 根据id查询
         * @param id
         * @return
         */
        public QcLineConfigurationDTO findById(String id) {
            return baseMapper.findById ( id );
        }

        public int updateByIds( String id,String line_no){
           return baseMapper.updateByIds ( id ,line_no);
        }
        /**
         * 自定义分页检索
         * @param page
         * @param queryWrapper
         * @return
         */
        public IPage<QcLineConfigurationDTO> findPage(Page<QcLineConfigurationDTO> page, QueryWrapper queryWrapper) {
            queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
            return  baseMapper.findList (page, queryWrapper);
        }


    }


