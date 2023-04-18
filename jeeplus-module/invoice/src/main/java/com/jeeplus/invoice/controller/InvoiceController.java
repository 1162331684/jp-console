package com.jeeplus.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;

import com.jeeplus.invoice.domain.Invoice;
import com.jeeplus.invoice.domain.InvoiceSequenceSetup;
import com.jeeplus.invoice.service.InvoiceSetupService;
import com.jeeplus.invoice.service.dto.InvoiceDTO;
import com.jeeplus.invoice.service.InvoiceService;
import com.jeeplus.invoice.service.mapstruct.InvoiceWrapper;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceWrapper invoiceWrapper;
    @Autowired
    private InvoiceSetupService invoiceSetupService;


    @ApiLog("查询Invoice列表数据")
    @ApiOperation(value = "查询Invoice列表数据")
    @PreAuthorize("hasAuthority('invoice:InvoiceList:list')")
    @GetMapping("list")
    public ResponseEntity<IPage<InvoiceDTO>> list(InvoiceDTO invoiceDTO, Page<InvoiceDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(invoiceDTO, InvoiceDTO.class);
        IPage<InvoiceDTO> result = invoiceService.findPage(page, queryWrapper);
        return ResponseEntity.ok(result);
    }
    @ApiLog("查询InvoiceSetup列表数据")
    @ApiOperation(value = "查询InvoiceSetup列表数据")
    @PreAuthorize("hasAuthority('invoiceSetup:InvoiceSetupList:list')")
    @GetMapping("invoiceSequenceSetup")
    public ResponseEntity<IPage<InvoiceDTO>> invoiceSequenceSetup(InvoiceDTO invoiceDTO, Page<InvoiceDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(invoiceDTO, InvoiceDTO.class);
        IPage<InvoiceDTO> invoiceSequenceSetup = invoiceService.findInvoiceSequenceSetup(page, queryWrapper);
        return ResponseEntity.ok(invoiceSequenceSetup);
    }

    /**
     * 保存Invoice
     */
    @ApiLog("保存Invoice")
    @ApiOperation(value = "保存Invoice")
    @PreAuthorize("hasAnyAuthority('invoice:InvoiceList:generate','invoice:InvoiceList:edit','invoice:InvoiceList:add','invoiceSetup:InvoiceSetupList:reset')")
    @PostMapping("save")
    public  ResponseEntity <String> save(@Valid @RequestBody InvoiceDTO invoiceDTO) throws Exception {
        //新增或编辑表单保存
        System.out.println("*****invoiceDTO.invoiceNumber****)"+hasInvoiceNumber(invoiceDTO));
        if(hasInvoiceNumber(invoiceDTO)){
            return ResponseEntity.badRequest().body("已存在invoice_number："+invoiceDTO.invoiceNumber);
        }
//        System.out.println("第一条\n"+invoiceDTO+"第二条\n"+invoiceDTO.getInvoiceNumber()+"第三条\n"+invoiceDTO.invoiceNumber);
//        System.out.println("***************************************************************"+invoiceWrapper.toEntity(invoiceDTO));
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
    @ApiLog("保存Invoice配置")
    @ApiOperation(value = "保存Invoice配置")
    @PreAuthorize("hasAnyAuthority('invoiceSetup:InvoiceSetupList:add','invoiceSetup:InvoiceSetupList:edit','invoiceSetup:InvoiceSetupList:reset')")
    @PostMapping("saveSetup")
    public  ResponseEntity <String> saveSetup(@Valid @RequestBody InvoiceSequenceSetup invoiceSequenceSetup) throws Exception {
        //新增或编辑表单保存
        if(!hasInvoiceSetup(invoiceSequenceSetup)){
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
    @ApiLog("删除Invoice")
    @ApiOperation(value = "删除Invoice")
    @PreAuthorize("hasAnyAuthority('invoice:InvoiceList:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String idArray[] = ids.split(",");
        invoiceService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok( "删除Invoice成功" );
    }
    @ApiLog("删除Invoice配置")
    @ApiOperation(value = "删除Invoice配置")
    @PreAuthorize("hasAnyAuthority('invoiceSetup:InvoiceSetupList:del')")
    @DeleteMapping("deleteSetup")
    public ResponseEntity <String> deleteSetup(String ids) {
        String idArray[] = ids.split(",");
        invoiceSetupService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok( "删除InvoiceSetup成功" );
    }
    @ApiLog("根据ID查询Invoice")
    @ApiOperation(value = "根据ID查询Invoice")
    @PreAuthorize("hasAnyAuthority('invoiceSetup:InvoiceSetupList:view','invoiceSetup:InvoiceSetupList:add','invoiceSetup:InvoiceSetupList:edit')")
    @GetMapping("queryById")
    public ResponseEntity<InvoiceDTO> queryById(String id) {
        return ResponseEntity.ok ( invoiceService.findById ( id ) );
    }
    @ApiLog("根据ID查询Invoice配置")
    @ApiOperation(value = "根据ID查询Invoice配置")
    @PreAuthorize("hasAnyAuthority('invoiceSetup:InvoiceSetupList:view','invoiceSetup:InvoiceSetupList:add','invoiceSetup:InvoiceSetupList:edit')")
    @GetMapping("querySetupById")
    public ResponseEntity<InvoiceDTO> querySetupById(String id) {
        return ResponseEntity.ok ( invoiceSetupService.findById ( id ) );
    }
    @ApiLog("根据SetupID更新nextnumber")
    @ApiOperation(value = "根据SetupID更新nextnumber")
    @PreAuthorize("hasAnyAuthority('invoiceSetup:InvoiceSetupList:add','invoiceSetup:InvoiceSetupList:edit','invoiceSetup:InvoiceSetupList:reset')")
    @PostMapping("updateBySetupId")
    public  ResponseEntity <String> updateBySetupId(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        UpdateWrapper<Invoice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("invoice_sequence_setup_id",invoiceDTO.invoiceSequenceSetupId).eq("had_generated",0).set("next_number",invoiceDTO.nextNumber);
        invoiceService.update (null,updateWrapper ) ;
        return ResponseEntity.ok ( "已更新nextnumber" );
    }

}