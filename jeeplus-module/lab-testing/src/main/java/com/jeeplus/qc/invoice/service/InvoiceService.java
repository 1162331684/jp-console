package com.jeeplus.qc.invoice.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.configuration.service.dto.QcLineConfigurationDTO;
import com.jeeplus.qc.invoice.domain.Invoice;
import com.jeeplus.qc.invoice.mapper.InvoiceMapper;
import com.jeeplus.qc.invoice.service.dto.InvoiceDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceService extends ServiceImpl<InvoiceMapper, Invoice> {

    public IPage<InvoiceDTO> findPage(Page<InvoiceDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
        return  baseMapper.findList (page, queryWrapper);
    }

    public IPage<InvoiceDTO> findInvoiceSequenceSetup(Page<InvoiceDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq ("a.del_flag", 0 ); // 排
        return  baseMapper.findInvoiceSequenceSetup (page, queryWrapper);
    }


    public InvoiceDTO findById(String id) {
        return baseMapper.findById ( id );
    }


}
