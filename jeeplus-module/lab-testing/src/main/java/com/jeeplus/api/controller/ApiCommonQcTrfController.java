package com.jeeplus.api.controller;

import com.jeeplus.aop.logging.annotation.ApiLog;

import com.jeeplus.qc.utils.AjaxJsonUtil;
import com.jeeplus.qc.utils.ApiCommonUtils;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags ="TRFAPI")
@RestController
@RequestMapping(value = "/api/common")
public class ApiCommonQcTrfController {
    @ApiLog("查询TRF列表数据")
    @ApiOperation(value = "查询TRF列表数据")
    @PostMapping("factory/data")
    public ResponseEntity getFactoryList(HttpServletRequest request) throws Exception {
        AjaxJsonUtil ajaxJsonUtil = new AjaxJsonUtil();
        ajaxJsonUtil.setRecord(ApiCommonUtils.getFactoryRuleByDictValue(UserUtils.getCurrentUserDTO()));
        return ajaxJsonUtil.ok();
    }
}
