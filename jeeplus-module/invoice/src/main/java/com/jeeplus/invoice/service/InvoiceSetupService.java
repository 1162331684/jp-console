package com.jeeplus.invoice.service;

import com.jeeplus.invoice.mapper.InvoiceSetupMapper;
import com.jeeplus.invoice.domain.InvoiceSequenceSetup;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jeeplus.invoice.service.dto.InvoiceDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceSetupService extends  ServiceImpl<InvoiceSetupMapper, InvoiceSequenceSetup> {

    public InvoiceDTO findById(String id) {
        return baseMapper.findById ( id );
    }
}
