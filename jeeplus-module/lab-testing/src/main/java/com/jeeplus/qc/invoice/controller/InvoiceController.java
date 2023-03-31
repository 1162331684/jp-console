package com.jeeplus.qc.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;

import com.jeeplus.qc.invoice.domain.Invoice;
import com.jeeplus.qc.invoice.domain.InvoiceSequenceSetup;
import com.jeeplus.qc.invoice.service.InvoiceService;
import com.jeeplus.qc.invoice.service.InvoiceSetupService;
import com.jeeplus.qc.invoice.service.dto.InvoiceDTO;
import com.jeeplus.qc.invoice.service.mapstruct.InvoiceWrapper;

import javafx.print.Printer;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Console;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceWrapper invoiceWrapper;
    @Autowired
    private InvoiceSetupService invoiceSetupService;


//    @ApiLog("查询Invoice列表数据")
//    @ApiOperation(value = "查询Invoice列表数据")
    //@PreAuthorize("hasAuthority('invoice:InvoiceList:list')")
    @GetMapping("list")
    public ResponseEntity<IPage<InvoiceDTO>> list(InvoiceDTO invoiceDTO, Page<InvoiceDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(invoiceDTO, InvoiceDTO.class);
        IPage<InvoiceDTO> result = invoiceService.findPage(page, queryWrapper);
        return ResponseEntity.ok(result);
    }

    @GetMapping("invoiceSequenceSetup")
    public ResponseEntity<IPage<InvoiceDTO>> invoiceSequenceSetup(InvoiceDTO invoiceDTO, Page<InvoiceDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(invoiceDTO, InvoiceDTO.class);
        IPage<InvoiceDTO> invoiceSequenceSetup = invoiceService.findInvoiceSequenceSetup(page, queryWrapper);
        return ResponseEntity.ok(invoiceSequenceSetup);
    }

    /**
     * 保存Invoice
     */
//    @ApiLog("保存Invoice")
//    @ApiOperation(value = "保存Invoice")
    //@PreAuthorize("hasAnyAuthority('invoice:InvoiceList:generate','invoice:InvoiceList:edit')")
    @PostMapping("save")
    public  ResponseEntity <String> save(@Valid @RequestBody InvoiceDTO invoiceDTO) throws Exception {
        //新增或编辑表单保存
        System.out.println("*****invoiceDTO.invoiceNumber****)"+hasInvoiceNumber(invoiceDTO));
        if(hasInvoiceNumber(invoiceDTO)){
            return ResponseEntity.badRequest().body("已存在invoice_number："+invoiceDTO.invoiceNumber);
        }
        System.out.println("第一条\n"+invoiceDTO+"第二条\n"+invoiceDTO.getInvoiceNumber()+"第三条\n"+invoiceDTO.invoiceNumber);
        System.out.println("***************************************************************"+invoiceWrapper.toEntity(invoiceDTO));
        invoiceService.saveOrUpdate (invoiceWrapper.toEntity(invoiceDTO));
        UpdateWrapper<Invoice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("invoice_number",invoiceDTO.lastInvoice).eq("had_generated",0).set("had_generated",1);
        invoiceService.update(null,updateWrapper);
        return ResponseEntity.ok ( "保存invoice成功" );
    }
    
    public boolean hasInvoiceNumber(InvoiceDTO invoiceDTO) throws Exception {
        QueryWrapper<Invoice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invoice_number",invoiceDTO.invoiceNumber).ne("id",invoiceDTO.getId());
        return invoiceService.getOne(queryWrapper)!=null;
    }

    @PostMapping("saveSetup")
    public  ResponseEntity <String> saveSetup(@Valid @RequestBody InvoiceSequenceSetup invoiceSequenceSetup) throws Exception {
        //新增或编辑表单保存
        if(!hasInvoiceSetup(invoiceSequenceSetup)){
            System.out.println("第一条"+invoiceSequenceSetup+"第二条"+invoiceSequenceSetup.getCreateBy());
            invoiceSetupService.saveOrUpdate (invoiceSequenceSetup);
            return ResponseEntity.ok ( "保存invoiceSequenceSetup成功" );
        }
        else {
            return ResponseEntity.badRequest().body("已存在numberSequenceCode:" + invoiceSequenceSetup.numberSequenceCode + "设置,请编辑修改");
        }
    }


    public boolean hasInvoiceSetup(InvoiceSequenceSetup invoiceSequenceSetup) throws Exception {
        QueryWrapper<InvoiceSequenceSetup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number_sequence_code",invoiceSequenceSetup.numberSequenceCode).ne("id",invoiceSequenceSetup.id);
        return invoiceSetupService.getOne(queryWrapper)!=null;
    }

    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String idArray[] = ids.split(",");
        invoiceService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok( "删除Invoice成功" );
    }

    @DeleteMapping("deleteSetup")
    public ResponseEntity <String> deleteSetup(String ids) {
        String idArray[] = ids.split(",");
        invoiceSetupService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok( "删除InvoiceSetup成功" );
    }

    @GetMapping("queryById")
    public ResponseEntity<InvoiceDTO> queryById(String id) {
        return ResponseEntity.ok ( invoiceService.findById ( id ) );
    }
    @GetMapping("querySetupById")
    public ResponseEntity<InvoiceDTO> querySetupById(String id) {
        return ResponseEntity.ok ( invoiceSetupService.findById ( id ) );
    }
    @PostMapping("generate")
    public  ResponseEntity <String> generate(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        //新增或编辑表单保存
        invoiceService.saveOrUpdate (invoiceWrapper.toEntity (invoiceDTO));
        return ResponseEntity.ok ( "generate invoiceSequenceSetup成功" );
    }
}