package com.jeeplus.qc.configuration.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.qc.configuration.service.QcLineConfigurationService;
import com.jeeplus.qc.configuration.service.dto.QcLineConfigurationDTO;
import com.jeeplus.qc.configuration.service.dto.QcTrfTestConfigurationDTO;
import com.jeeplus.qc.configuration.service.mapstruct.QcLineConfigurationWrapper;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/qc/configuration/qcLineConfiguration")
public class QcLineConfigurationController {

    @Autowired
    private QcLineConfigurationService qcLineConfigurationService;

    @Autowired
    private QcLineConfigurationWrapper qcLineConfigurationWrapper;

//    @ApiLog("查询qc_line_configuration列表数据")
//    @ApiOperation(value = "查询qc_line_configuration列表数据")
//    @PreAuthorize("hasAuthority('qc:configuration:qcLineConfiguration:list')")
    @GetMapping("list")
    public ResponseEntity<IPage<QcLineConfigurationDTO>> list(QcLineConfigurationDTO qcLineConfigurationDTO, Page<QcLineConfigurationDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcLineConfigurationDTO, QcLineConfigurationDTO.class);
        IPage<QcLineConfigurationDTO> result = qcLineConfigurationService.findPage (page, queryWrapper);

        return ResponseEntity.ok (result);
    }


    /**
     * 根据Id获取qc_line_configuration数据
     */
//    @ApiLog("根据Id获取qc_line_configuration数据")
//    @ApiOperation(value = "根据Id获取qc_line_configuration数据")
//    @PreAuthorize("hasAnyAuthority('qc:configuration:qcLineConfiguration:view','qc:configuration:qcLineConfiguration:add','qc:configuration:qcLineConfiguration:edit')")
    @GetMapping("queryById")
    public ResponseEntity<QcLineConfigurationDTO> queryById(String id) {
        return ResponseEntity.ok ( qcLineConfigurationService.findById ( id ) );
    }

    /**
     * 删除qc_line_configuration
     */
//    @ApiLog("删除qc_line_configuration")
//    @ApiOperation(value = "删除qc_line_configuration")
//    @PreAuthorize("hasAuthority('qc:configuration:qclineConfiguration:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String idArray[] = ids.split(",");
        qcLineConfigurationService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok( "删除qc_line_configuration成功" );
    }

    /**
     * 更新qc_line_configuration
     */
//    @ApiLog("更新qc_line_configuration")
//    @ApiOperation(value = "更新qc_line_configuration")
//    @PreAuthorize("hasAuthority('qc:configuration:qclineConfiguration:edit')")
    @GetMapping("update")
    public ResponseEntity <String> update(String ids,String line_no) {
        String idArray[] = ids.split(",");
        qcLineConfigurationService.updateByIds ( ids,line_no );
        return ResponseEntity.ok( "更新qc_line_configuration成功" );
    }

    /**
     * 保存qc_line_configuration
     */
//    @ApiLog("保存qc_line_configuration")
//    @ApiOperation(value = "保存qc_line_configuration")
//    @PreAuthorize("hasAnyAuthority('qc:configuration:qcLineConfiguration:add','qc:configuration:qcLineConfiguration:edit')")
    @PostMapping("save")
    public  ResponseEntity <String> save(@Valid @RequestBody QcLineConfigurationDTO qcLineConfigurationDTO) {
        System.out.println(qcLineConfigurationDTO);
        System.out.println(qcLineConfigurationWrapper.toEntity (qcLineConfigurationDTO));
        //新增或编辑表单保存
        qcLineConfigurationService.saveOrUpdate (qcLineConfigurationWrapper.toEntity (qcLineConfigurationDTO));
        return ResponseEntity.ok ( "保存qc_line_configuration成功" );
    }

}


