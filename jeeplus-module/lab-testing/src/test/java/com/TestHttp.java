package com;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.translation.MD5;
import com.jeeplus.database.datamodel.utils.DataSourceUtils;
import com.jeeplus.qc.domain.QcTrf;
import com.jeeplus.qc.domain.QcTrfItem;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestHttp {
	 public static void main(String args[]) throws Exception { 
/*			OkHttpClient client = new OkHttpClient().newBuilder()
					  .build();
					MediaType mediaType = MediaType.parse("application/json");
					JSONObject test1= new JSONObject();
					JSONObject test2= new JSONObject();
					test2.put("@microsoft.graph.conflictBehavior", "rename");
					test1.put("name", "test1638858737802.xlsx");
					test1.put("item", test2);
					RequestBody body = RequestBody.create(mediaType,test1.toJSONString() );
					Request request = new Request.Builder()
					  .url("https://graph.microsoft.com/v1.0/me/drive/root:/Allocation Report:/createUploadSession")
					  .method("POST", body)
					  .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJub25jZSI6IkRzZTQ2LWVLa1JVVktVVDQ5LVRxUUJ4bmdXUnlFMGdzcHlHZE1NYWFHc1UiLCJhbGciOiJSUzI1NiIsIng1dCI6Imwzc1EtNTBjQ0g0eEJWWkxIVEd3blNSNzY4MCIsImtpZCI6Imwzc1EtNTBjQ0g0eEJWWkxIVEd3blNSNzY4MCJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDAiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9hYTlkODg3MC1lOWZiLTQ3NGItOTBiMi0yOTdmZDc4ODc0ODQvIiwiaWF0IjoxNjM4ODU4MDEyLCJuYmYiOjE2Mzg4NTgwMTIsImV4cCI6MTYzODg2MzQ0NCwiYWNjdCI6MCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhUQUFBQXZ0L0pXcTd0WmEvOURkay9NTFg5Qmt2VTlGcEJZdk14TkNHd3lvZWs1cXM9IiwiYW1yIjpbInB3ZCJdLCJhcHBfZGlzcGxheW5hbWUiOiJEMzY1UExNIiwiYXBwaWQiOiI4NmE5YjdiMC05MGY1LTQwZGQtOTQ5Ni0wMDhlOTZhMWZjZDAiLCJhcHBpZGFjciI6IjAiLCJpZHR5cCI6InVzZXIiLCJpcGFkZHIiOiIxMTYuNi4yNy42NyIsIm5hbWUiOiJBbGxvY2F0aW9uIFBvcnRhbCBTdXBwb3J0Iiwib2lkIjoiYzM5NTljZDYtZGFmYS00ZjBjLTlmOTctN2U4ODUyYmM0YmEyIiwicGxhdGYiOiIxNCIsInB1aWQiOiIxMDAzMjAwMUIzMjE0Mzk1IiwicmgiOiIwLkFWVUFjSWlkcXZ2cFMwZVFzaWxfMTRoMGhMQzNxWWIxa04xQWxKWUFqcGFoX05CVkFQay4iLCJzY3AiOiJlbWFpbCBGaWxlcy5SZWFkV3JpdGUuQWxsIG9wZW5pZCBwcm9maWxlIFVzZXIuUmVhZCBVc2VyLlJlYWQuQWxsIiwic2lnbmluX3N0YXRlIjpbImlua25vd25udHdrIl0sInN1YiI6ImRPVUlUM0N1V0JqUGZTdzYzMVV3Nko1NkgteXQ3dXVaR2RleDV5V3lfQlUiLCJ0ZW5hbnRfcmVnaW9uX3Njb3BlIjoiQVMiLCJ0aWQiOiJhYTlkODg3MC1lOWZiLTQ3NGItOTBiMi0yOTdmZDc4ODc0ODQiLCJ1bmlxdWVfbmFtZSI6IkFsbG9jYXRpb24uUG9ydGFsLlN1cHBvcnRAd2luaGFudmVya3kub25taWNyb3NvZnQuY29tIiwidXBuIjoiQWxsb2NhdGlvbi5Qb3J0YWwuU3VwcG9ydEB3aW5oYW52ZXJreS5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJUN2JIaWtRenZFZUdMd3haTUhtZEFBIiwidmVyIjoiMS4wIiwid2lkcyI6WyJiNzlmYmY0ZC0zZWY5LTQ2ODktODE0My03NmIxOTRlODU1MDkiXSwieG1zX3N0Ijp7InN1YiI6ImtCcURVZVU1N3hrSy13NWI1bHRhMFZrSFpoeFlUeDFsQ1I4anRfTS1LUlEifSwieG1zX3RjZHQiOjE0NzY3Nzk3MjN9.R5C27FCCQCwnx7rnms-G2mrc7TlveV4_3kwRhDWO_Z6-mw5l_yzNHuf0Mz93P7N9vBCW0vX3CYF0793hUjF90dnDms72T5V7RVzkaGrC_lV1iMb68_M0SBW00_kEFPi3YZtJ2OZXmLbgPUoTqqV7oiEoxFoEA5Y1wi0y-WfAGsdGGzkIOukR-xdSjJ4ayXMIdK0TAtTzp6DwFocFw8xXvKKLa_sEZo0bOC4FWRzucwVcy8-qUtGoO5w5YAzM_HjocsmnZJudEq8biu31kxiDP7mBGagI1v8Y4hvffbJcKZzcX57Fr-6FY1qj4lsQs-H4KuyGJxzEgidGmRiCZO3Oxw")
					  .addHeader("Content-Type", "application/json")
					  .build();
					Response response = client.newCall(request).execute();
					System.out.println(response.body().string());*/
					
/*		OkHttpClient client = new OkHttpClient().newBuilder()
					  .build();
					MediaType mediaType = MediaType.parse("application/json");
					RequestBody body = RequestBody.create(mediaType, "{\"item\": {\"@microsoft.graph.conflictBehavior\": \"rename\"},\"name\": \"test1638858737802.xlsx\"}");
					Request request = new Request.Builder()
					  .url("https://graph.microsoft.com/v1.0/me/drive/root:/Allocation Report:/createUploadSession")
					  .method("POST", body)
					  .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJub25jZSI6IkZiRlFGXzYyNE45ZEZjcVRYTnoxM3JPMURzQkNPYnFjYlhYakpNTFg3emsiLCJhbGciOiJSUzI1NiIsIng1dCI6Imwzc1EtNTBjQ0g0eEJWWkxIVEd3blNSNzY4MCIsImtpZCI6Imwzc1EtNTBjQ0g0eEJWWkxIVEd3blNSNzY4MCJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDAiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9hYTlkODg3MC1lOWZiLTQ3NGItOTBiMi0yOTdmZDc4ODc0ODQvIiwiaWF0IjoxNjM4ODY0MTEzLCJuYmYiOjE2Mzg4NjQxMTMsImV4cCI6MTYzODg2OTI0MywiYWNjdCI6MCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhUQUFBQVJPTzY3TUhXdVNBVDdrR3gyVStWNiswSXdxVTJxc3NKS1VqM216eEc5NFk9IiwiYW1yIjpbInB3ZCJdLCJhcHBfZGlzcGxheW5hbWUiOiJEMzY1UExNIiwiYXBwaWQiOiI4NmE5YjdiMC05MGY1LTQwZGQtOTQ5Ni0wMDhlOTZhMWZjZDAiLCJhcHBpZGFjciI6IjAiLCJpZHR5cCI6InVzZXIiLCJpcGFkZHIiOiIxMTYuNi4yNy42NyIsIm5hbWUiOiJBbGxvY2F0aW9uIFBvcnRhbCBTdXBwb3J0Iiwib2lkIjoiYzM5NTljZDYtZGFmYS00ZjBjLTlmOTctN2U4ODUyYmM0YmEyIiwicGxhdGYiOiIxNCIsInB1aWQiOiIxMDAzMjAwMUIzMjE0Mzk1IiwicmgiOiIwLkFWVUFjSWlkcXZ2cFMwZVFzaWxfMTRoMGhMQzNxWWIxa04xQWxKWUFqcGFoX05CVkFQay4iLCJzY3AiOiJlbWFpbCBGaWxlcy5SZWFkV3JpdGUuQWxsIG9wZW5pZCBwcm9maWxlIFVzZXIuUmVhZCBVc2VyLlJlYWQuQWxsIiwic2lnbmluX3N0YXRlIjpbImlua25vd25udHdrIl0sInN1YiI6ImRPVUlUM0N1V0JqUGZTdzYzMVV3Nko1NkgteXQ3dXVaR2RleDV5V3lfQlUiLCJ0ZW5hbnRfcmVnaW9uX3Njb3BlIjoiQVMiLCJ0aWQiOiJhYTlkODg3MC1lOWZiLTQ3NGItOTBiMi0yOTdmZDc4ODc0ODQiLCJ1bmlxdWVfbmFtZSI6IkFsbG9jYXRpb24uUG9ydGFsLlN1cHBvcnRAd2luaGFudmVya3kub25taWNyb3NvZnQuY29tIiwidXBuIjoiQWxsb2NhdGlvbi5Qb3J0YWwuU3VwcG9ydEB3aW5oYW52ZXJreS5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJmV1hyeldsdjQwRzdsZTBXdUQ4bUFBIiwidmVyIjoiMS4wIiwid2lkcyI6WyJiNzlmYmY0ZC0zZWY5LTQ2ODktODE0My03NmIxOTRlODU1MDkiXSwieG1zX3N0Ijp7InN1YiI6ImtCcURVZVU1N3hrSy13NWI1bHRhMFZrSFpoeFlUeDFsQ1I4anRfTS1LUlEifSwieG1zX3RjZHQiOjE0NzY3Nzk3MjN9.pHBDXIkp-5pmSGs7VGAtq0QAeAJATeMv_pfunMwK5eahBaW73kmBjk4EZvkWaE7cHZ1cc5MZA7Mp2jM4eIFm9g9QzxJHXOKldIvD9UFE1JhmOiAcdqyb05kEQtwOjchjtKp3akmuQtAiA8SyexWiRa2soAISOWuj4Fy5VwEhhv_GJ1-Gxi6b9AG30VTuI8lvwnanXOc05PM2Vx4wWNW5JmAMrGZ2lDsRZlNlgnN_YHVlUCBCp65OwAeBFmICuSENnRPF6Mo3_n7iTkND_GF2p9KmUYaLUkEIHwx2r4oMPKb58gVNMFqEl4haqDH5VI1NfMt1801DGb2nNI2nlVMLVg")
					  .addHeader("Content-Type", "application/json")
		 			  .build();
					System.out.println(request.headers().names());
					// System.out.println(request.headers().values(name));
		  Response response = client.newCall(request).execute();
		  System.out.println(response.body().string());*/
		  	
/*		  HttpUrl url=HttpUrl.get("https://graph.microsoft.com/v1.0/me/drive/root:/Allocation Report:/createUploadSession") ;
			String host=url.toString();
			//host = host.replace(" ", "%20");
			JSONObject params = new JSONObject();
			JSONObject item = new JSONObject();
			item.put("@microsoft.graph.conflictBehavior","rename");
			params.put("item",item);
//	        params.put("deferCommit",true);
			params.put("name","test"+new Date().getTime()+".xlsx");
			Map<String,String> headers = new HashMap<>();
			headers.put("Authorization", "eyJ0eXAiOiJKV1QiLCJub25jZSI6IlZraFhCeTBJcFJLVGN6aFo5RTdxbUxZTDJoRHpMeWxJeUtiUmNvM1hxemsiLCJhbGciOiJSUzI1NiIsIng1dCI6Imwzc1EtNTBjQ0g0eEJWWkxIVEd3blNSNzY4MCIsImtpZCI6Imwzc1EtNTBjQ0g0eEJWWkxIVEd3blNSNzY4MCJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDAiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9hYTlkODg3MC1lOWZiLTQ3NGItOTBiMi0yOTdmZDc4ODc0ODQvIiwiaWF0IjoxNjM4ODYzNzQyLCJuYmYiOjE2Mzg4NjM3NDIsImV4cCI6MTYzODg2ODc1MSwiYWNjdCI6MCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhUQUFBQXRlTTlHeTJiNXFpOXo4aFkyMEp1djh5TXhGUGNxUU9lN1pOdUFtMmdJenc9IiwiYW1yIjpbInB3ZCJdLCJhcHBfZGlzcGxheW5hbWUiOiJEMzY1UExNIiwiYXBwaWQiOiI4NmE5YjdiMC05MGY1LTQwZGQtOTQ5Ni0wMDhlOTZhMWZjZDAiLCJhcHBpZGFjciI6IjAiLCJpZHR5cCI6InVzZXIiLCJpcGFkZHIiOiIxMTYuNi4yNy42NyIsIm5hbWUiOiJBbGxvY2F0aW9uIFBvcnRhbCBTdXBwb3J0Iiwib2lkIjoiYzM5NTljZDYtZGFmYS00ZjBjLTlmOTctN2U4ODUyYmM0YmEyIiwicGxhdGYiOiIxNCIsInB1aWQiOiIxMDAzMjAwMUIzMjE0Mzk1IiwicmgiOiIwLkFWVUFjSWlkcXZ2cFMwZVFzaWxfMTRoMGhMQzNxWWIxa04xQWxKWUFqcGFoX05CVkFQay4iLCJzY3AiOiJlbWFpbCBGaWxlcy5SZWFkV3JpdGUuQWxsIG9wZW5pZCBwcm9maWxlIFVzZXIuUmVhZCBVc2VyLlJlYWQuQWxsIiwic2lnbmluX3N0YXRlIjpbImlua25vd25udHdrIl0sInN1YiI6ImRPVUlUM0N1V0JqUGZTdzYzMVV3Nko1NkgteXQ3dXVaR2RleDV5V3lfQlUiLCJ0ZW5hbnRfcmVnaW9uX3Njb3BlIjoiQVMiLCJ0aWQiOiJhYTlkODg3MC1lOWZiLTQ3NGItOTBiMi0yOTdmZDc4ODc0ODQiLCJ1bmlxdWVfbmFtZSI6IkFsbG9jYXRpb24uUG9ydGFsLlN1cHBvcnRAd2luaGFudmVya3kub25taWNyb3NvZnQuY29tIiwidXBuIjoiQWxsb2NhdGlvbi5Qb3J0YWwuU3VwcG9ydEB3aW5oYW52ZXJreS5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJ1N0Y1ZmJ2cTgwLXowVEpVTjhBY0FBIiwidmVyIjoiMS4wIiwid2lkcyI6WyJiNzlmYmY0ZC0zZWY5LTQ2ODktODE0My03NmIxOTRlODU1MDkiXSwieG1zX3N0Ijp7InN1YiI6ImtCcURVZVU1N3hrSy13NWI1bHRhMFZrSFpoeFlUeDFsQ1I4anRfTS1LUlEifSwieG1zX3RjZHQiOjE0NzY3Nzk3MjN9.QSaV-rfz1oGaEIeHtlcnfUj9h87aTl0ojZ50M4daF-BUWoxRCJfwEWgBMM1woxiDNNPfMMx3gBcTYTQoKgnnanaHybWDGuJh9l1EG4cZYMx5bXyXSIkE1LHHfe4vV7dpjClTK-TJveSzyHeQuKFptMTZsdEHVd3710dLH0FyqyvsHEjL0xyci3RnKALoIbNh3BfGL_d6QPeXerwOJ9qRp2DHfHr16dw6VidWgyXE23EiImTk1-X22mzYViSCwJ3SL2d3Es4gUzVoKwmJUO4QS_BivyBshx9pKP_rrwKnLb03clUVGsocNGWScPuVGu7FU47G7kpaEI8nkk8CT7MvYA");
			headers.put("Content-Type", "application/json");
			//headers.put("User-Agent", null);
			
			
			String src= HttpUtil.post(host, "{\"item\": {\"@microsoft.graph.conflictBehavior\": \"rename\"},\"name\": \"test1638858737802.xlsx\"}", headers);
			System.out.println(src);*/
		 

		 
		 // https://mes-uat.bowkerasia.com   https://eins.bowkerasia.com/
		 
/*		 OkHttpClient client = new OkHttpClient().newBuilder()
		          //.sslSocketFactory(RxUtils.createSSLSocketFactory())
		          //.hostnameVerifier(new RxUtils.TrustAllHostnameVerifier())
				 .build();
		 
		 
		 Request request = new Request.Builder()
		  .url("https://mes-uat.bowkerasia.com/eins/a/qc/productionStatus/getHomeStatistics?type=productLine&beginSearchDate=2021-12-08&endSearchDate=2021-12-15&factoryIDs='2','3','4','5','445b39d139ae4fea811d665378bee984','1c2ca8ca56334083bd81d593fe6f0d67','pga_1c2ca8ca56334083bd81d593fe6f0d67'")
		  .method("GET", null)
		  .addHeader("Cookie", "jeeplus.session.id=5e2b60c730b8416ab3b980c8b702c2b0")
		  .build();
		 Response response = client.newCall(request).execute();*/
		 
		 String token= getToken ();

		 String url="https://dev7113aeb62110517415aos.cloudax.dynamics.com/api/services/GAR_GetDevelopmentServiceGroup/DevelopmentService/getDevelopmentList";
		 Map<String,String> headers=new HashMap<>();
		 headers.put("Content-Type", "application/json");
		 headers.put("Authorization", "Bearer "+token);
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String startTiemString=null;
		 if(startTiemString==null){
			 SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			 startTiemString = sdf2.format(new Date());
		 }
		 else{
			 Date startTime = sdf.parse(startTiemString);
			 startTime = new Date(startTime.getTime() - 10000);
			 startTiemString = sdf.format(startTime);
		 }
		 Date endTime=new Date(new Date().getTime() ); // 8小时  + 8*3600000
		 String endTimeString = sdf.format(endTime);
		 
		 startTiemString = "2021-12-13 07:00:00" ;endTimeString =  "2022-01-04 08:00:00" ;
		 String paramString=String.format("{\"_request\":{\"LastModifiedDateTime\":\"%s\",\"ToLastModifiedDateTime\":\"%s\"}}",
				 startTiemString, endTimeString);

		 String resText=HttpUtil.post(url, paramString, headers);
		 
		 System.out.println(resText);
		 JSONObject res=JSON.parseObject(resText);
		 JSONArray list = res.getJSONArray("DevelopmentList");
		 List<QcTrf> trflist=new ArrayList<>();
		 List<QcTrfItem> trfItemlist=new ArrayList<>();
		 for(int i=0;list.size() > i ;i++){
			 QcTrf trf=new QcTrf();
			 boolean hasSaveDate= d365DataToQcTrf(list.getJSONObject(i),trf);
			 if(hasSaveDate)trflist.add(trf);
			 
			 QcTrfItem qcTrfItem=new QcTrfItem();
			 hasSaveDate=  d365DataToQcTrfItem(list.getJSONObject(i), qcTrfItem, trf);
			 if(hasSaveDate)trfItemlist.add(qcTrfItem);
		 }
		 
	 }
	 private static boolean d365DataToQcTrfItem(JSONObject json,QcTrfItem qcTrfItem,QcTrf trf) {
		 qcTrfItem.setMaterialType(json.getString("ProductType"));
		 qcTrfItem.setMaterialItemCode(json.getString("ReferenceCode"));
		 qcTrfItem.setT2Name(json.getString("VendorName"));
		 qcTrfItem.setT2Code(json.getString("VendorCode"));
		 qcTrfItem.setColorCode(json.getString("ColorName")); 
		 qcTrfItem.setQcTrfId(trf.getId());
		 qcTrfItem.setId(MD5.md5( qcTrfItem.getMaterialType() + qcTrfItem.getMaterialItemCode() 
		 	+ qcTrfItem.getT2Name() + qcTrfItem.getT2Code() 
		 	+ qcTrfItem.getColorCode()+  qcTrfItem.getQcTrfId() ));
		// 查询db是否有数据 
		 
		 return true;
	 }
	 private static boolean d365DataToQcTrf(JSONObject json,QcTrf trf) {
		 trf.setBno(json.getString("Bnumber"));
		 trf.setWorkingNo(json.getString("WorkingNo"));
		 trf.setArticle(json.getString("Article"));
		 trf.setSeason(json.getString("Season"));
		 trf.setId(MD5.md5(trf.getBno()+trf.getWorkingNo()+trf.getArticle()+trf.getSeason()));
		 // 查询db是否有数据 
		 
		 /* JSONArray tmp=DataSourceUtils.getDataBySql("GET_SERIAL_NUMBER", new HashMap<>());
		 if(tmp.size()!=0){
			qcTrfDTO.setTrfId(tmp.getJSONObject(0).getString("serialNumber"));
		 }*/
		 
		 return true;
	}
	 
	 private static JSONObject getServerToken() throws Exception {
		 Date d=new Date();
		 String url="https://login.microsoftonline.com/winhanverky.onmicrosoft.com/oauth2/token";
		 Map<String,String> headers=new HashMap<>();
		 headers.put("Content-Type", "application/x-www-form-urlencoded");
		 Map<String,String> params=new HashMap<>();
		 params.put("grant_type", "client_credentials");
		 params.put("client_id", "cdafdad2-a6e7-4898-87aa-1091d270b3c7");
		 params.put("client_secret", "7duwO6nE2AVFIf/8UvvngnjzCgv0r9rgmAmKrX/dXMI=");
		 params.put("resource", "https://dev7113aeb62110517415aos.cloudax.dynamics.com");
		 String resText=HttpUtil.post(url, params, headers);
		 JSONObject resObj=JSON.parseObject(resText);
		 JSONObject token=new JSONObject();
		 token.put("token", resObj.getString("access_token"));
		 token.put("expiresDate", new Date(d.getTime()+resObj.getLongValue("expires_in")));
		 
		 return token;
	 }
	 
	 private static String getToken () throws Exception {
		 JSONObject token =new JSONObject();
		 if(token.getDate("expiresDate")==null || token.getDate("expiresDate").getTime() < new Date().getTime()){
			 token = getServerToken();
		 }
		 return token.getString("token");
	}
	 
	 

}
