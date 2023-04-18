package com.jeeplus.invoice.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.invoice.domain.Invoice;
import com.jeeplus.invoice.service.dto.InvoiceDTO;
import org.apache.ibatis.annotations.Param;

public interface InvoiceMapper extends BaseMapper<Invoice> {
    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    InvoiceDTO findById(String id);

    /**
     * 根据id
     *
     * @param id
     * @return
     */

    /**
     * 获取测试项配置列表
     * @param queryWrapper
     * @return
     */
    IPage <InvoiceDTO> findList(Page <InvoiceDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    IPage<InvoiceDTO> findInvoiceSequenceSetup(Page<InvoiceDTO> page,@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
