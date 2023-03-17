package com.test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest2 {

	
	public static void main(String args[]) throws Exception { 
		Map<String,String>map =  new HashMap<>();
		// 测试
		String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Njg4NDYzMjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.59bme-xdGFQ_nKbn3LkwXJnZ9aeYTxYXfsdZXmuVwRo";
		map.put("token", token);
		Map<String, String> params=new HashMap<>();
		params.put("processDefinitionId", "test_QKPI_EXCEPTION");
		
		//{"processDefinitionId":"test_QKPI_EXCEPTION","datas":"[{\"article\":\"HI0991\",\"brand\":\"ADIDAS\",\"colorwayName\":\"ALMOST BLUE F22\",\"colorwayStatus\":\"IN RANGE\",\"coo\":\"CHINA\",\"createBy\":\"b32fc3d6aae34e9590575ea00f22972d\",\"createDate\":1668371508000,\"delFlag\":0,\"devRegion\":\"CCP\",\"developer\":\"Jimenez, Ariel\",\"directDevelopment\":\"ACCEPT\",\"earliestBuyReady\":\"6/27/2022\",\"ftyCode\":\"APU002\",\"ftyName\":\"Bowker Yee Sing Garment Factor\",\"ftyPriority\":\"H\",\"groupCode\":\"06X004\",\"id\":\"1591992295707123715\",\"level\":\"1\",\"lo\":\"VIETNAM\",\"loForFty\":\"LO Guangzhou\",\"material\":\"60033416 Mesh\",\"partName\":\"FULL BODY  LINING,\",\"partNo\":\"30\",\"productName\":\"Y TRAE SHORT\",\"ref\":\"60033416\",\"shortName\":\"FU HSUN (CHINA)\",\"sportsCat\":\"BASKETBALL\",\"styleBrStatus\":\"Buy Ready\",\"styleStatus\":\"In Range\",\"suppRef\":\"60614-ARE\",\"suppUom\":\"YARD\",\"sustainability\":\"100%\",\"typename\":\"adidas\",\"workingNo\":\"F22BYTRAR100\",\"yield\":\"0.352\"}]"}

		//params.put("datas", "[{\"workingNo\":\"F22BYTRAR100\",\"brand\":\"ADIDAS\",\"productName\":\"Y TRAE SHORT\",\"article\":\"HI0991\",\"ftyCode\":\"APU002\",\"ftyName\":\"Bowker Yee Sing Garment Factor\",\"ref\":\"60033416\",\"material\":\"60033416 Mesh\",\"groupCode\":\"06X004\",\"shortName\":\"FU HSUN (CHINA)\",\"partNo\":\"30\",\"partName\":\"FULL BODY  LINING,\",\"level\":\"1\",\"yield\":\"0.352\",\"suppUom\":\"YARD\",\"comments\":\"\",\"season\":\"adidas Spring/Summer 2023\",\"file\":[{\"key\":\"1668682056884_20062\",\"url\":\"https://bowkerstorageprod.blob.core.windows.net/eins/File-83281e7e-bb68-4632-8a1a-ad214b749e72.jpg\",\"name\":\"File-83281e7e-bb68-4632-8a1a-ad214b749e72.jpg\"}]}]");
		
		params.put("datas", "[{\"article\":\"HI0991\",\"brand\":\"ADIDAS\",\"colorwayName\":\"ALMOST BLUE F22\",\"colorwayStatus\":\"IN RANGE\",\"coo\":\"CHINA\",\"createBy\":\"b32fc3d6aae34e9590575ea00f22972d\",\"createDate\":1668371508000,\"delFlag\":0,\"devRegion\":\"CCP\",\"developer\":\"Jimenez, Ariel\",\"directDevelopment\":\"ACCEPT\",\"earliestBuyReady\":\"6/27/2022\",\"ftyCode\":\"APU002\",\"ftyName\":\"Bowker Yee Sing Garment Factor\",\"ftyPriority\":\"H\",\"groupCode\":\"06X004\",\"id\":\"1591992295707123715\",\"level\":\"1\",\"lo\":\"VIETNAM\",\"loForFty\":\"LO Guangzhou\",\"material\":\"60033416 Mesh\",\"partName\":\"FULL BODY  LINING,\",\"partNo\":\"30\",\"productName\":\"Y TRAE SHORT\",\"ref\":\"60033416\",\"shortName\":\"FU HSUN (CHINA)\",\"sportsCat\":\"BASKETBALL\",\"styleBrStatus\":\"Buy Ready\",\"styleStatus\":\"In Range\",\"suppRef\":\"60614-ARE\",\"suppUom\":\"YARD\",\"sustainability\":\"100%\",\"typename\":\"adidas\",\"workingNo\":\"F22BYTRAR100\",\"yield\":\"0.352\"}]");
		params.put("datas",URLEncoder.encode(params.get("datas"),"UTF-8"));//,null
		String res=HttpUtil.post("http://127.0.0.1:8083/approval/flowable/form/submitStartFormDatas", params,map);
		System.out.println(res);
	}
}
