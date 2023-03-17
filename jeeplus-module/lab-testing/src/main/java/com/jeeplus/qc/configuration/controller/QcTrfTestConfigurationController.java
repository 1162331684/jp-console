/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.configuration.controller;

import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.qc.configuration.service.dto.QcTrfTestConfigurationDTO;
import com.jeeplus.qc.configuration.service.mapstruct.QcTrfTestConfigurationWrapper;
import com.jeeplus.qc.configuration.service.QcTrfTestConfigurationService;

import java.util.HashSet;
import java.util.Set;

/**
 * qc_trf_test_configurationController
 * @author maxteng
 * @version 2022-11-25
 */

@Api(tags ="qc_trf_test_configuration")
@RestController
@RequestMapping(value = "/qc/configuration/qcTrfTestConfiguration")
public class QcTrfTestConfigurationController {

	@Autowired
	private QcTrfTestConfigurationService qcTrfTestConfigurationService;

	@Autowired
	private QcTrfTestConfigurationWrapper qcTrfTestConfigurationWrapper;

	/**
	 * qc_trf_test_configuration列表数据
	 */
	@ApiLog("查询qc_trf_test_configuration列表数据")
	@ApiOperation(value = "查询qc_trf_test_configuration列表数据")
	@PreAuthorize("hasAuthority('qc:configuration:qcTrfTestConfiguration:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<QcTrfTestConfigurationDTO>> list(QcTrfTestConfigurationDTO qcTrfTestConfigurationDTO, Page<QcTrfTestConfigurationDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (qcTrfTestConfigurationDTO, QcTrfTestConfigurationDTO.class);
		IPage<QcTrfTestConfigurationDTO> result = qcTrfTestConfigurationService.findPage (page, queryWrapper);
		for(QcTrfTestConfigurationDTO config : result.getRecords()){
			if(config.getType().compareTo("approvalEmail") == 0 && StringUtils.isNotBlank(config.getSupplement())){
				String[] userIds = config.getSupplement().split(",");
				Set<String> loginNames = new HashSet<>();
				for(int i=0;i<userIds.length;i++){
					UserDTO userDTO = UserUtils.get(userIds[i]);
					if(null != userDTO){
						loginNames.add(userDTO.getLoginName());
					}
				}
				config.setLoginName(StringUtils.join(loginNames,","));
			}
		}
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取qc_trf_test_configuration数据
	 */
	@ApiLog("根据Id获取qc_trf_test_configuration数据")
	@ApiOperation(value = "根据Id获取qc_trf_test_configuration数据")
	@PreAuthorize("hasAnyAuthority('qc:configuration:qcTrfTestConfiguration:view','qc:configuration:qcTrfTestConfiguration:add','qc:configuration:qcTrfTestConfiguration:edit')")
	@GetMapping("queryById")
	public ResponseEntity<QcTrfTestConfigurationDTO> queryById(String id) {
		return ResponseEntity.ok ( qcTrfTestConfigurationService.findById ( id ) );
	}

	/**
	 * 保存qc_trf_test_configuration
	 */
	@ApiLog("保存qc_trf_test_configuration")
	@ApiOperation(value = "保存qc_trf_test_configuration")
	@PreAuthorize("hasAnyAuthority('qc:configuration:qcTrfTestConfiguration:add','qc:configuration:qcTrfTestConfiguration:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody QcTrfTestConfigurationDTO qcTrfTestConfigurationDTO) {
		//新增或编辑表单保存
		qcTrfTestConfigurationService.saveOrUpdate (qcTrfTestConfigurationWrapper.toEntity (qcTrfTestConfigurationDTO));
        return ResponseEntity.ok ( "保存qc_trf_test_configuration成功" );
	}


	/**
	 * 删除qc_trf_test_configuration
	 */
	@ApiLog("删除qc_trf_test_configuration")
	@ApiOperation(value = "删除qc_trf_test_configuration")
	@PreAuthorize("hasAuthority('qc:configuration:qcTrfTestConfiguration:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        qcTrfTestConfigurationService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除qc_trf_test_configuration成功" );
	}

}
