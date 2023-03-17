package com.jeeplus.qc.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetHttpFileContent {
	private static String readExcel(InputStream io) throws EncryptedDocumentException, InvalidFormatException, IOException {
		// 工作表
		Workbook workbook = WorkbookFactory.create(io);
		// 表个数。
		int numberOfSheets = workbook.getNumberOfSheets();
		// 遍历表。
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			// 行数。
			int rowNumbers = sheet.getLastRowNum() + 1;
			// Excel第一行。
			Row temp = sheet.getRow(0);
			if (temp == null)continue;	
			int cells = temp.getPhysicalNumberOfCells();
			// 读数据。
			for (int row = 0; row < rowNumbers; row++) {
				Row r = sheet.getRow(row);
				for (int col = 0; col < cells; col++) {
					 result.append("").append(r.getCell(col).toString());
				}
			}
		}
		return result.toString();
	}
	private static String readWorkDoc(InputStream io) throws IOException {
        WordExtractor ex = new WordExtractor(io);
        String content = ex.getText();
        ex.close();
        return content;
	}
	private static String readWorkDocx(InputStream io) throws IOException {
		//OPCPackage opcPackage = POIXMLDocument.openPackage(io);
		XWPFDocument doc=new  XWPFDocument(io);
        POIXMLTextExtractor extractor = new XWPFWordExtractor(doc);
        String content = extractor.getText();
        extractor.close();
        return content;
	}
	public static String getFile(String url) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder()
			  .url(url)
			  .method("GET", null)
			  .build();
			Response response = client.newCall(request).execute();			 
			InputStream io=  response.body().byteStream();
			if(url.lastIndexOf(".xlsx") == (url.length()-5)){
				return readExcel(io);
			}
			else if(url.lastIndexOf(".docx") == (url.length()-5)){
				return readWorkDocx(io);
			}
			else if(url.lastIndexOf(".doc") == (url.length()-5)){
				return readWorkDoc(io);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
