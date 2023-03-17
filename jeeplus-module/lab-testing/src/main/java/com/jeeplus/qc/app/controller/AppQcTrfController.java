/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.app.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.common.redis.RedisUtils;
import com.jeeplus.common.repository.AccessoryRepository;
import com.jeeplus.common.utils.RequestUtils;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.config.properties.JeePlusProperties;
import com.jeeplus.core.errors.ErrorConstants;
import com.jeeplus.modules.app.utils.AzureUpload;

import com.jeeplus.qc.domain.*;
import com.jeeplus.qc.service.*;
import com.jeeplus.qc.service.dto.QcTrfDTO;
import com.jeeplus.qc.utils.ApiCommonUtils;
import com.jeeplus.qc.utils.GetBpToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jeeplus.qc.app.domain.QcAppTrfData;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.constant.enums.LogTypeEnum;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.SpringThreadCacheUtils;
import com.jeeplus.sys.utils.UserUtils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;

import com.jeeplus.security.jwt.TokenProvider;
import com.jeeplus.security.util.SecurityUtils;

/**
 * TRFController
 * @author Lewis
 * @version 2021-11-18
 */
@Api(tags ="TRFApp")
@RestController
@RequestMapping(value = "/app/qc/qcTrf")
public class AppQcTrfController {

	@Autowired
	private QcTrfService qcTrfService;
	@Autowired
	private QcTrfItemService cTrfItemService;
	@Autowired
	private QcTrfItemSpecimenService qcTrfItemSpecimenService;
	@Autowired
	private QcTrfItemSpecimenTestingService qcTrfItemSpecimenTestingService;
	@Autowired
	private QcDataTriggerService qcDataTriggerService;
	@Autowired
	private QcTrfTestCodeService qcTrfTestCodeService;
	@Autowired
	private QcTrfTestContentService qcTrfTestContentService;
	@Autowired
	private QcTrfTestConditionService qcTrfTestConditionService;
	@Autowired
	private AccessoryRepository accessoryRepository;
	@Autowired
	private QcTestCodeService qcTestCodeService;
    @Autowired
    private RedisUtils redisUtils;	
    @Autowired
    private UserService userService;
    
	private String getImgUrl(String testId,String urlString,String path) throws FileNotFoundException {
		if(StringUtils.isNoneBlank(urlString)){
			String[] urls=urlString.split("\\|");
			for(int i =0;i<urls.length;i++){
				String url= urls[i];
				if(!url.startsWith("http")){
					String [] datas=url.split(",");
					String type=datas[0].split(";")[0].split("/")[1];
					String fileSuffix =".png";
					switch (type) {
					case "png":
						fileSuffix =".png";
						break;
					case "jpg":
					case "jepg":
						fileSuffix =".png";
						break;
					case "bmp":
						fileSuffix =".bmp";
						break;
					default:
						fileSuffix = type+".png";
						break;
					}
					ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(datas[1]));
					String orgFileName =String.format("%s_%d_%d%s", testId,i,new Date().getTime(),fileSuffix) ;
		    		accessoryRepository.save(inputStream,path , orgFileName,type);
		    		String newUrl =accessoryRepository.getPermanentURL(path, orgFileName).toString();
		    		urls[i] = newUrl;
				}
			}
			return StringUtils.join(Arrays.asList(urls) ,"|");
		}
		return urlString;
	}
	
	private void dealUploadImg(List<QcTrfItemSpecimenTesting> testList) throws FileNotFoundException{
		String uploadPath = "qc/testapp";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        String path=uploadPath+"/"+year+"/"+month+"/"+UserUtils.getCurrentUserDTO().getLoginName()+"/";
        
        for(QcTrfItemSpecimenTesting test :testList ){
        	test.setImgBefore(this.getImgUrl(test.getId(), test.getImgBefore(), path));
        	test.setImgAfter( this.getImgUrl(test.getId(), test.getImgAfter(),  path));
        }
	}

	private Map<String,Object> getReturnData(List<QcTrf> trfList,List<QcTrfItem> qcTrfItemList,
			List<QcTrfItemSpecimen> qcTrfItemSpecimenList,List<QcTrfItemSpecimenTesting> qcTrfItemSpecimenTestingList) {
		Map<String,Object> data=new HashMap<>();
		data.put("trfs", trfList);
		data.put("rfItemts", qcTrfItemList);
		data.put("trfItemSpecimen", qcTrfItemSpecimenList);
		data.put("trfItemSpecimenTestingList", qcTrfItemSpecimenTestingList);
		return data;
	}
	
	private boolean checkToken(HttpServletRequest request) { // 局部做单一登陆
		String token = TokenProvider.resolveToken(request);
		String tokenMd5=MD5.create().digestHex(token.getBytes());
		String username = SecurityUtils.getLoginName ();
		String tokenMd5Login= (String) RedisUtils.getInstance ().get("tokenMd5_"+username.toLowerCase());
		if(!tokenMd5.equals(tokenMd5Login)){
			return false;
		}
		return true;
	}




	@ApiLog("已经被打开Specimen")
	@ApiOperation(value = "已经被打开Specimen")
	@PostMapping("setOpenSpecimen")
	public  ResponseEntity <String> setOpenSpecimen(String id) {
		if(StringUtils.isNoneBlank(id)){
			qcTrfItemSpecimenService.setOpenSpecimen(id);
		}
        return ResponseEntity.ok ( "保存测试样本信息成功" );
	}


	private void setAppUpdateTime(QcTrfDTO qcTrfDTO,String syncTime,String syncDay){
		Date nowDate = ApiCommonUtils.getTodayStartTime();
		Date dateStart =  ApiCommonUtils.addDayToDate(-7,nowDate);
		if(StringUtils.isNotBlank(syncDay)){
			Integer day = Integer.parseInt(syncDay) - 1;
			dateStart =  ApiCommonUtils.addDayToDate(-day,nowDate);
			Date dateEnd =  ApiCommonUtils.addDayToDate(1-day,nowDate);
			qcTrfDTO.setUpdateDateEnd(DateFormatUtils.format(dateEnd,"yyyy-MM-dd HH:mm:ss"));
		}
		qcTrfDTO.setUpdateDateStart(DateFormatUtils.format(dateStart,"yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * TRF列表数据
	 */
	@ApiLog("查询TRF列表数据")
	@ApiOperation(value = "查询TRF列表数据")
	@PostMapping("sync")
	public ResponseEntity sync(String syncTime,String syncDay,String factory,@RequestBody(required=false) QcAppTrfData data,HttpServletRequest request) throws Exception {
		ResponseUtil responseUtil = new ResponseUtil ( );
		responseUtil.add("success", true);
		responseUtil.add("syncTime", syncTime);
		responseUtil.add("syncDay", syncDay);
		responseUtil.add("factory", factory);
		if(!this.checkToken(request)){
			return ResponseEntity.status ( HttpStatus.FORBIDDEN )
					.body (DictUtils.getLanguageLabel(ErrorConstants.LOGIN_ERROR_FORBID_LOGGED_IN_ELSEWHERE, "")  );
		}
		this.dealUploadImg(data.getQcTrfItemSpecimenTestings());
		if(data.getQcTrfItemSpecimenTestings().size() > 0){
			for(QcTrfItemSpecimenTesting updateTesting : data.getQcTrfItemSpecimenTestings()){
				if(StringUtils.isNotBlank(updateTesting.getTestResult())){
					updateTesting.setTester(UserUtils.getCurrentUserDTO().getId());
					updateTesting.setTestTime(ApiCommonUtils.getUTCTimeByString());
				}
			}
		}
		SpringThreadCacheUtils.setParam(JSON.toJSONString(data));
		if(data.getQcTrfItemSpecimens().size()!=0||data.getQcTrfItemSpecimenTestings().size()!=0){
			qcTrfService.mergeAppUploadData(data);
		}
		QcTrfDTO qcTrfDTO = new QcTrfDTO();
		qcTrfDTO.setFactory(factory);

		if(StringUtils.isBlank(syncTime) || StringUtils.isNotBlank(syncDay)){ // 首次必须尽量多拿数据，不然后面的增量会漏掉
			setAppUpdateTime(qcTrfDTO,syncTime,syncDay);
			responseUtil.add("UpdateDateStart",qcTrfDTO.getUpdateDateStart());
			responseUtil.add("UpdateDateEnd",qcTrfDTO.getUpdateDateEnd());
			List<QcTrf> trfList= qcTrfService.findListByAppSync(qcTrfDTO);
			List<QcTrfItem> qcTrfItemList= new ArrayList<QcTrfItem>();
			if(trfList.size()>0){
				List<String> ids=trfList.stream().map(QcTrf::getId).collect(Collectors.toList());
				qcTrfItemList= cTrfItemService.lambdaQuery().in(QcTrfItem::getQcTrfId, ids).list();
			}
			
			List<QcTrfItemSpecimen> qcTrfItemSpecimenList= new ArrayList<QcTrfItemSpecimen>();
			if(qcTrfItemList.size()>0){
				List<String> ids=qcTrfItemList.stream().map(QcTrfItem::getId).collect(Collectors.toList());
				qcTrfItemSpecimenList= qcTrfItemSpecimenService.lambdaQuery()
						.in(QcTrfItemSpecimen::getQcTrfItemId, ids).list();
			}
			
			List<QcTrfItemSpecimenTesting> qcTrfItemSpecimenTestingList= new ArrayList<QcTrfItemSpecimenTesting>();
			if(qcTrfItemSpecimenList.size()>0){
				List<String> ids=qcTrfItemSpecimenList.stream().map(QcTrfItemSpecimen::getId).collect(Collectors.toList());
				qcTrfItemSpecimenTestingList= qcTrfItemSpecimenTestingService.lambdaQuery()
						.in(QcTrfItemSpecimenTesting::getQcTrfItemSpecimenId, ids).list();
			}
			List<QcTrfTestCode> qcTrfTestCode = new ArrayList<>();
			List<QcTrfTestContent> qcTrfTestContent = new ArrayList<>();
			List<QcTrfTestCondition> qcTrfTestCondition = new ArrayList<>();
			if(StringUtils.isNotBlank(syncDay) && syncDay.equals("1")){
				qcTrfTestContent = qcTrfTestContentService.lambdaQuery()
						.isNotNull(QcTrfTestContent::getTestCodeId)
						.list();
				if(qcTrfTestContent.size() > 0){
					List<String> testCodeIds = qcTrfTestContent.stream().map(QcTrfTestContent::getTestCodeId).distinct().collect(Collectors.toList());
					qcTrfTestCode= qcTrfTestCodeService.lambdaQuery().in(QcTrfTestCode::getId,testCodeIds).list();
					qcTrfTestCondition= qcTrfTestConditionService.lambdaQuery().in(QcTrfTestCondition::getTestCodeId,testCodeIds).list();
				}
			}

			responseUtil.putAll(this.getReturnData(trfList, qcTrfItemList, qcTrfItemSpecimenList, qcTrfItemSpecimenTestingList));
			responseUtil.put("qcTrfTestCode", qcTrfTestCode);
			responseUtil.put("qcTrfTestContent", qcTrfTestContent);
			responseUtil.put("qcTrfTestCondition", qcTrfTestCondition);
			return responseUtil.ok();
		}
		else{ // 增量获取数据   // 缓存 删除数据的id，通过 syncTime 和 update 获取数据
			Date syncDate= DateUtils.parseIso8601DateTime(syncTime);
			qcTrfDTO.setUpdateDateStart(DateUtils.format(syncDate,"yyyy-MM-dd HH:mm:ss"));
			responseUtil.add("UpdateDateStart",qcTrfDTO.getUpdateDateStart());
			List<QcTrf> trfList= qcTrfService.findListByAppSync(qcTrfDTO);
			List<QcTrfItem> qcTrfItemList = cTrfItemService.lambdaQuery().ge(QcTrfItem::getUpdateDate,syncDate ).list();
			List<QcTrfItemSpecimen> qcTrfItemSpecimenList= qcTrfItemSpecimenService.lambdaQuery().ge(QcTrfItemSpecimen::getUpdateDate,syncDate ).list();
			List<QcTrfItemSpecimenTesting> qcTrfItemSpecimenTestingList= 
					qcTrfItemSpecimenTestingService.lambdaQuery().ge(QcTrfItemSpecimenTesting::getUpdateDate,syncDate ).list();
			
			List<QcDataTrigger> trigger =qcDataTriggerService.lambdaQuery().ge(QcDataTrigger::getUpdateDate,syncDate ).list();

			List<QcTrfTestCode> qcTrfTestCode= qcTrfTestCodeService.lambdaQuery().ge(QcTrfTestCode::getUpdateDate,syncDate ).list();
			List<QcTrfTestContent> qcTrfTestContent = qcTrfTestContentService.lambdaQuery().ge(QcTrfTestContent::getUpdateDate,syncDate ).list();
			List<QcTrfTestCondition> qcTrfTestCondition= qcTrfTestConditionService.lambdaQuery().ge(QcTrfTestCondition::getUpdateDate,syncDate ).list();

			responseUtil.putAll(this.getReturnData(trfList, qcTrfItemList, qcTrfItemSpecimenList, qcTrfItemSpecimenTestingList));
			responseUtil.put("trigger", trigger);
			responseUtil.put("qcTrfTestCode", qcTrfTestCode);
			responseUtil.put("qcTrfTestContent", qcTrfTestContent);
			responseUtil.put("qcTrfTestCondition", qcTrfTestCondition);
			return responseUtil.ok();
		}
	}

    /**
     * 用户登录
     * @param userName
     * @param session
     * @return
     */
    @PostMapping("/login")
    @ApiLog(value = "用户登录", type = LogTypeEnum.LOGIN)
    @ApiOperation("登录接口")
    @ApiModelProperty("username")
    public ResponseEntity login(@RequestParam("userName") String userName,
            @RequestParam("password") String password, HttpSession session) {
        ResponseUtil responseUtil = new ResponseUtil ( );
		try {
			String bpToken = new GetBpToken().getToken(userName,password);
			if(StringUtils.isNotBlank(bpToken)){
		        //登录成功，生成token
		        UserDTO userDTO = UserUtils.getByLoginName ( userName );
		        if(userDTO !=null){
		        	String token=  TokenProvider.createAccessToken ( userName, userDTO.getPassword () ) ;
			        responseUtil.add ( TokenProvider.TOKEN,token );
			        // responseUtil.add ( TokenProvider.REFRESH_TOKEN, TokenProvider.createRefreshToken ( username, userDTO.getPassword ( ) ) );
			        responseUtil.add("oldLoginDate", DateUtil.format (userDTO.getLoginDate () , "yyyy-MM-dd HH:mm:ss"));
			        responseUtil.add("oldLoginIp", userDTO.getLoginIp () );
			        responseUtil.add("success", true);
			        String tokenMd5=MD5.create().digestHex(token.getBytes());
			        RedisUtils.getInstance ().set("tokenMd5_"+userName.toLowerCase(),tokenMd5);
			        //更新登录日期
			        this.updateUserLoginInfo(responseUtil, userDTO, token);
					responseUtil.add("factorys", ApiCommonUtils.getFactoryRule(userDTO));
			        return responseUtil.ok ( );
		        }
		        else{
		        	return responseUtil.error(DictUtils.getLanguageLabel("登陆异常", "")+" : "+DictUtils.getLanguageLabel("不存在的用户", ""));
		        }
			}
			else{
				return responseUtil.error(DictUtils.getLanguageLabel("登陆异常", "")+" : "+DictUtils.getLanguageLabel("请确认密码和用户是否存在", ""));
			}
		} catch (Exception e) {
			return responseUtil.error(DictUtils.getLanguageLabel("登陆异常", "")+" : "+e.getMessage());
		}
    }
    private void updateUserLoginInfo(ResponseUtil responseUtil, UserDTO userDTO, String token){

        String username = userDTO.getLoginName ();
        redisUtils.set ( CacheNames.USER_CACHE_TOKEN + username + ":" + token, token  );
        redisUtils.expire ( CacheNames.USER_CACHE_TOKEN + username + ":" + token, JeePlusProperties.newInstance ( ).getEXPIRE_TIME ( ) );
        responseUtil.add ( "oldLoginDate", userDTO.getLoginDate () );
        responseUtil.add ( "oldLoginIp", userDTO.getLoginIp () );
        //更新登录日期
        userDTO.setLoginDate ( new Date (  ) );
        userDTO.setLoginIp ( RequestUtils.getRequest ().getRemoteHost () );
        userService.updateUserLoginInfo ( userDTO );

    }
	
	@RequestMapping(value = "uploadLog", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity uploadLog(@RequestParam("file") MultipartFile importFile,String loginName) { 
		ResponseUtil responseUtil = new ResponseUtil ( );
    	try {
			//Files.write(dbData, new File("C:/Users/123/Desktop/einsdb3.db"));
//			accessoryRepository.save(importFile,"db" , "labtestingdb"+loginName+new Date().getTime());
			byte [] dbData=importFile.getBytes();
			AzureUpload.uploadToBlob(dbData,"labtesting","labtestingdb"+loginName+ ApiCommonUtils.getUTCTimeByPattern("yyyyMMddHHmmss"),"sqllite");
			return responseUtil.ok();
		} catch (Exception e) {
			e.printStackTrace();
			responseUtil.add("msg", e.getMessage());
			return responseUtil.ok();
		}
    }
}
