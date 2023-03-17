/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.access.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.common.utils.net.HttpUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.exception.access.domain.ExceptionList;
import com.jeeplus.exception.access.service.ExceptionListService;
import com.jeeplus.exception.access.service.dto.ExceptionListDTO;
import com.jeeplus.exception.access.service.mapstruct.ExceptionListWrapper;
import com.jeeplus.exception.bom.domain.ExceptionBom;
import com.jeeplus.exception.bom.info.domain.ExceptionBomInfo;
import com.jeeplus.exception.bom.info.service.ExceptionBomInfoService;
import com.jeeplus.exception.bom.service.ExceptionBomService;
import com.jeeplus.exception.bom.service.dto.ExceptionBomDTO;
import com.jeeplus.exception.bom.service.mapstruct.ExceptionBomWrapper;
import com.jeeplus.qc.service.QcTrfService;
import com.jeeplus.qc.service.dto.QcTrfDTO;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;
import com.jeeplus.sys.service.dto.DictValueDTO;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Exception AccessController 
 * @author Young
 * @version 2022-11-12
 */

@Api(tags ="Exception Access")
@RestController
@RequestMapping(value = "/exception/access/exceptionList")
public class ExceptionListController {

	@Autowired
	private ExceptionBomInfoService exceptionBomInfoService;
	
	@Autowired
	private ExceptionBomService exceptionBomService;
	
	@Autowired
	private ExceptionBomWrapper exceptionBomWrapper;
	
	
	@Autowired
	private ExceptionListService exceptionListService;

	
	@Autowired
	private QcTrfService qcTrfService;

	@Autowired
	private ExceptionListWrapper exceptionListWrapper;

	/**
	 * Exception Access列表数据
	 */
	@ApiLog("查询Exception Access列表数据")
	@ApiOperation(value = "查询Exception Access列表数据")
	@PreAuthorize("hasAuthority('exception:access:exceptionList:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<ExceptionListDTO>> list(ExceptionListDTO exceptionListDTO, Page<ExceptionListDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (exceptionListDTO, ExceptionListDTO.class);
		IPage<ExceptionListDTO> result = exceptionListService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取Exception Access数据 
	 */
	@ApiLog("根据Id获取Exception Access数据")
	@ApiOperation(value = "根据Id获取Exception Access数据")
	@PreAuthorize("hasAnyAuthority('exception:access:exceptionList:view','exception:access:exceptionList:add','exception:access:exceptionList:edit')")
	@GetMapping("queryById")
	public ResponseEntity<ExceptionListDTO> queryById(String id) {
		return ResponseEntity.ok ( exceptionListService.findById ( id ) );
	}

	/**
	 * 保存Exception Access
	 */
	@ApiLog("保存Exception Access")
	@ApiOperation(value = "保存Exception Access")
	@PreAuthorize("hasAnyAuthority('exception:access:exceptionList:add','exception:access:exceptionList:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody ExceptionListDTO exceptionListDTO) {
		//新增或编辑表单保存
		exceptionListService.saveOrUpdate (exceptionListWrapper.toEntity (exceptionListDTO));
        return ResponseEntity.ok ( "保存Exception Access成功" );
	}
	
	@ApiLog("保存Exception Access")
	@ApiOperation(value = "保存Exception Access")
	@PostMapping("saveByAppNo")
	public  ResponseEntity <String> saveByAppNo(@Valid @RequestBody ExceptionListDTO exceptionListDTO) {
		//新增或编辑表单保存
		exceptionListService.saveOrUpdate (exceptionListWrapper.toEntity (exceptionListDTO));
        return ResponseEntity.ok ( "保存Exception Access成功" );
	}
	private String getCookie(String name,HttpServletRequest request) {
		for(Cookie c: request.getCookies()){
			if(name.equals(c.getName())){
				return c.getValue();
			}
		}
		return null;
	}
	@ApiLog("保存Exception Access")
	@ApiOperation(value = "保存Exception Access")
	@PostMapping("createPR")
	@ResponseBody
	public  ResponseEntity <String> createPR(@Valid 
			@RequestBody Map<String,String> param,HttpServletRequest request) {
		//新增或编辑表单保存
		try {
			List<String> ids=Arrays.asList(param.get("ids").split(","));
			List<ExceptionList> tmpList= exceptionListService.lambdaQuery()
			.in(ExceptionList::getBomId,ids).list();
			List<String> ids2= tmpList.stream().map(t->t.getBomId()).collect(Collectors.toList());
			List<String> newList = new ArrayList<String>();
			for(String select:ids){
				boolean hasPR = false;
				for(String str:ids2){
					if(select.equals(str)){
						hasPR=true;
					}
				}
				if(hasPR){
					newList.add(select);
				}
			}
//			Iterator<String> it = ids2.iterator();
//			while (it.hasNext()) {
//				if(newList.contains(it.next())){
//					newList.remove(it.next());
//				}
//			}
			JSONObject res1=new JSONObject();
			if(newList.size()>0){
				res1.put("msg", DictUtils.getLanguageLabel("物料已发起风险评估，请勿重复操作", ""));
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(res1.toJSONString());
			}
			List<ExceptionBom> list = exceptionBomService.lambdaQuery()
					.in(ExceptionBom::getId,ids).list();
			List<ExceptionBomInfo> tmpList2= exceptionBomInfoService.lambdaQuery()
			.in(ExceptionBomInfo::getBomId,ids).list();
			if(list.size()==0){
				res1.put("msg", DictUtils.getLanguageLabel("物料已发起风险评估，请勿重复操作", ""));
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(res1.toJSONString());
			}
			JSONArray apParamList=(JSONArray) JSON.toJSON(list);
			for(int i=0;i<apParamList.size();i++){
				JSONObject json= apParamList.getJSONObject(i);
				List<ExceptionBomInfo> tmpList3 =tmpList2.stream().filter(a->a.getBomId().equals(json.getString("id"))).collect(Collectors.toList());
				if(tmpList3.size()>0){
					json.put("season", tmpList3.get(0).getSeason());
				}
			}
			String apParams =apParamList.toJSONString();
			String token = getCookie("bowker_baseportal_token",request);// 
			Map<String,String>map =  new HashMap<>();
			// 测试
			map.put("token", token);
			map.put("tokenType", "bowker_baseportal");		
			String url= DictUtils.getDictValue("submitStartForm", "ap_config", "https://mes-uat.bowkerasia.com/approval/flowable/form/submitStartFormDatas");
			//url=  "http://127.0.0.1:8083/approval/flowable/form/submitStartFormDatas";
			Map<String, String> params=new HashMap<>();
			params.put("processDefinitionId", DictUtils.getDictValue("processDefinitionId", "ap_config", "test_QKPI_EXCEPTION"));
			//params.put("processDefinitionId", "QKPI_EXCEPTION");
			params.put("datas", URLEncoder.encode( apParams,"UTF-8"));
			String res=HttpUtil.post(url, params,map);
			JSONObject resJson=JSON.parseObject(res);
			List<ExceptionList> exceptionList =new ArrayList<>();
			if(resJson.getIntValue("code")==200&&resJson.getBooleanValue("success")){
				JSONArray procInfos= resJson.getJSONArray("procInfos");
				for(int i=0;i<procInfos.size();i++){
					ExceptionList e=new ExceptionList();
					JSONObject procInfo=procInfos.getJSONObject(i);
					e.setBomId(list.get(i).getId()); 
					e.setApplicationNo(procInfo.getString("applicationNo"));
					e.setProcessInstanceId(procInfo.getString("processInstanceId"));
					e.setAssignee(procInfo.getString("assignee"));
					e.setStatus(procInfo.getString("names"));
					exceptionList.add(e);
				}
				exceptionListService.saveBatch(exceptionList);
				res1.put("success", true);
				res1.put("msg", DictUtils.getLanguageLabel("Create WorkFlow Success", ""));
				return ResponseEntity.status(HttpStatus.OK).body(res1.toJSONString());
			}
			else{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(resJson.getString("msg"));
			}			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject res=new JSONObject();
			res.put("msg", DictUtils.getLanguageLabel("无法连接到审批系统", "createPR"));
			res.put("exception", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(res.toJSONString());
		}
	}
	
	@ApiLog("保存Exception Access")
	@ApiOperation(value = "保存Exception Access")
	@GetMapping("createLabTesting")
	public  ResponseEntity <ExceptionBomDTO> createLabTesting( String ids,String source) {
		List<DictValueDTO> listDictValue = DictUtils.getDictDTOList("ap_code");
//		List<String> list = listDto.getIds();
		List<String> list  = Arrays.asList(ids.split(","));
		//新增或编辑表单保存
		ExceptionBomDTO bomBean = new ExceptionBomDTO();
		bomBean.setIds(list);
		List<ExceptionBomDTO>  listBoms = exceptionBomService.findDtoList(bomBean);
		String workingNo = "";
		//校验同一次选择bom必须同workingno
		for(ExceptionBomDTO bomDto:listBoms){
			if(!"".equals(workingNo)&&!workingNo.equals(bomDto.getWorkingNo())){
				throw new RuntimeException(DictUtils.getLanguageLabel("Please select boms of one workingno", ""));
			}
			workingNo = bomDto.getWorkingNo();
		}
		ExceptionBomDTO bomDto =  listBoms.get(0);
		QcTrfDTO trfBean = new QcTrfDTO();
		trfBean.setWorkingNo(bomDto.getWorkingNo());
		trfBean.setSeason(bomDto.getSeason());
		trfBean.setArticle(bomDto.getArticle());
		trfBean.setSource(source);
		//trfBean.setType("Fabric");
		for(DictValueDTO dictValue:listDictValue){
			if(bomDto.getFtyCode()!=null&&bomDto.getFtyCode().equals(dictValue.getValue()))
				trfBean.setFactory(dictValue.getLabel());
		}
		List<QcTrfItemDTO> qcTrfItemDTOList = new ArrayList<QcTrfItemDTO>();
		for(ExceptionBomDTO bom:listBoms){
			QcTrfItemDTO itemDto = new QcTrfItemDTO();
			itemDto.setMaterialItemCode(bom.getRef());
			itemDto.setT2Code(bom.getGroupCode());
			itemDto.setT2Name(bom.getShortName());
			itemDto.setColorCode(bom.getColor());
			itemDto.setPartNo(bom.getPartNo());
			qcTrfItemDTOList.add(itemDto);
		}
		trfBean.setQcTrfItemDTOList(qcTrfItemDTOList);
		//调用labtesting
		qcTrfService.saveOrUpdate (trfBean);
		
		return ResponseEntity.ok (bomDto );
	}

	@ApiLog("提交审批")
	@ApiOperation(value = "提交审批")
	@PostMapping("submitApprovals")
	@ResponseBody
	public  ResponseEntity <String> submitApprovals(@Valid 
			@RequestBody Map<String,String> param,HttpServletRequest request) {
		//新增或编辑表单保存
		String token = getCookie("bowker_baseportal_token",request);// 
		Map<String,String>map =  new HashMap<>();
		map.put("token", token);
		map.put("tokenType", "bowker_baseportal");	
		map.put("Content-Type", "application/json");
		String url= DictUtils.getDictValue("submitTaskFormDatas", "ap_config", "https://mes-uat.bowkerasia.com/approval/flowable/form/submitTaskFormDatas");
		//url=  "http://127.0.0.1:8083/approval/flowable/form/submitTaskFormDatas";
		try {
			String res=HttpUtil.post(url, param.get("jsonParam"),map);
			JSONObject resJson=JSON.parseObject(res);
			if(resJson.getIntValue("code")==200&&resJson.getBooleanValue("success")){
				List<ExceptionList> tmpList= exceptionListService.lambdaQuery()
						.in(ExceptionList::getId,Arrays.asList(param.get("selectIds").split(","))).list();
				JSONArray procInfos= resJson.getJSONArray("procInfos");
				for(int i=0;i<procInfos.size();i++){
					ExceptionList e=tmpList.get(i);
					JSONObject procInfo=procInfos.getJSONObject(i);
					if(StringUtils.isBlank(procInfo.getString("names"))){
						e.setAssignee("");
						e.setStatus("complete");
					}
					else{
						e.setAssignee(procInfo.getString("assignee"));
						e.setStatus(procInfo.getString("names"));
					}
				}
				exceptionListService.saveOrUpdateBatch(tmpList);
				return ResponseEntity.ok (DictUtils.getLanguageLabel("提交成功", "submitApprovals"));
			}
			else{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(resJson.getString("msg"));
			}
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject res=new JSONObject();
			res.put("msg", DictUtils.getLanguageLabel("无法连接到审批系统 ", "createPR"));
			res.put("exception", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(res.toJSONString());
		}
		
	}
	
	public ResponseEntity <Map<String,String>> getMyTaskSummay() {
		List<ExceptionList> list=  exceptionListService.lambdaQuery()
				.like(ExceptionList::getAssignee, String.format(",%s,", UserUtils.getCurrentUserDTO().getId()))
				.list();
		Map<String,String>map=new HashMap<>();
		
		return ResponseEntity.ok().body(map);
	}
	


	/**
	 * 删除Exception Access
	 */
	@ApiLog("删除Exception Access")
	@ApiOperation(value = "删除Exception Access")
	@PreAuthorize("hasAuthority('exception:access:exceptionList:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        exceptionListService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除Exception Access成功" );
	}

}
