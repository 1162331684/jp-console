package com.jeeplus.invoice.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.invoice.domain.InvoiceSequenceSetup;
import com.jeeplus.invoice.service.dto.InvoiceDTO;

public interface InvoiceSetupMapper extends BaseMapper<InvoiceSequenceSetup> {

    InvoiceDTO findById(String id);
}
