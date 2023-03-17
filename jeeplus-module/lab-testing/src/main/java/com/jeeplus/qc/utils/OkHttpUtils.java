package com.jeeplus.qc.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class OkHttpUtils {
    public static String post(String url, Map<String,String> headers, Object bodyObject) throws IOException{
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Content-Type", "application/json; charset=utf-8");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        Headers header = headersBuilder.build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(bodyObject));
        Request requestByCall = new Request.Builder().url(url).headers(header).post(body).build();
        Response responseByCall = client.newCall(requestByCall).execute();
        if(responseByCall.code() == 200) {
            //注：response.body().string()在Debugger下watch不到值会报错，必须赋值给一个String对象才能看到。
            String result = responseByCall.body().string();
            System.out.println(result);//此处才可以看到请求返回值
            return result;
        }
        return "";
    }
}
