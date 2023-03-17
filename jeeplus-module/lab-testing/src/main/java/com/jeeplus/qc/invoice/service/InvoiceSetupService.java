package com.jeeplus.qc.invoice.service;

import com.jeeplus.qc.invoice.domain.Invoice;
import com.jeeplus.qc.invoice.domain.InvoiceSequenceSetup;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jeeplus.qc.invoice.mapper.InvoiceMapper;
import com.jeeplus.qc.invoice.mapper.InvoiceSetupMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceSetupService extends  ServiceImpl<InvoiceSetupMapper, InvoiceSequenceSetup> {

}
