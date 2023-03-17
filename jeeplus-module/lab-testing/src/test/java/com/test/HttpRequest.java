package com.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.net.HttpUtil;
import com.jeeplus.qc.utils.GetBpToken;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;

public class HttpRequest {
	static GetBpToken getBpToken =new GetBpToken();
	
	public static void main(String args[]) throws Exception { 
/*		String url="https://bowkerstorage.blob.core.windows.net/labeltesting/labtesting/t2/2021/12/zhimi/工作时间设置数据导入模板588.xlsx";
	   // HttpRequest.downLoadFromUrl(url,"abc.xls","D:\\");
	    System.out.println("下载完成");*/
		
		Map<String, String> parameter = getBpToken.getloginParameter("CE0087", "Leny@123");
        String thhpBady = HttpUtil.post("http://8.218.91.144:8080/baseportal/api/authorize", parameter, new HashMap<>());
        System.out.println(thhpBady);
		
	}
}
