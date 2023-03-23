package com.jeeplus.qc.invoice.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.qc.invoice.domain.InvoiceSequenceSetup;
import com.jeeplus.qc.invoice.service.dto.InvoiceDTO;

public interface InvoiceSetupMapper extends BaseMapper<InvoiceSequenceSetup> {

    InvoiceDTO findById(String id);
}
