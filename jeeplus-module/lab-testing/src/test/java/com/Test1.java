package com;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import com.jeeplus.qc.utils.ApiCommonUtils;
import com.jeeplus.qc.utils.GetHttpFileContent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.time.DateFormatUtils;

public class Test1 {

	public static void main(String args[]) throws Exception {
		System.out.println(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		Date date = ApiCommonUtils.addDayToDate(-1,new Date());
		System.out.println(DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(Double.valueOf("1%"));

	 }
}
