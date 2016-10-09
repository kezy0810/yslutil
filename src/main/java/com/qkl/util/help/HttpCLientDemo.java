package com.qkl.util.help;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpCLientDemo {
    private static HttpClient httpclient = null;  
    private  String URL = "http://test.tfccwallet.com";  
    private  String API = "/api/tx/admin_make";  
    //转账相关参数
    private static String pri = "693369e4bd1ce20bab88b461e0d47d5ae69bd1b7b3a33ffcd3fab801ba04a424";
    private static String salt = "c3810b35aaee551728f847f857ef78ca33f6ec4e7fdee034264e2919e471b606";
    private static String admin_user = "sanapi";
    private static String sign = "";
    private static String data = "";
    private static String ts = String.valueOf(System.currentTimeMillis());
    public static String post(String url, Map<String, String> params) {  
        httpclient = new DefaultHttpClient();  
        String body = null;  
          
        HttpPost post = postForm(url, params);  
          
        body = invoke(httpclient, post);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
      
    public static String get(String url) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
          
        HttpGet get = new HttpGet(url);  
        body = invoke(httpclient, get);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
          
      
    private static String invoke(HttpClient httpclient,  
            HttpUriRequest httpost) {  
          
        HttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);  
          
        return body;  
    }  
  
    private static String paseResponse(HttpResponse response) {  
        HttpEntity entity = response.getEntity();  
          
        String charset = EntityUtils.getContentCharSet(entity);  
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return body;  
    }  
  
    private static HttpResponse sendRequest(HttpClient httpclient,  
            HttpUriRequest httpost) {  
        HttpResponse response = null;  
          
        try {  
            response = httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return response;  
    }  
  
    private static HttpPost postForm(String url, Map<String, String> params){  
          
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }  
          
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
          
        return httpost;  
    }  
    /**
     * @describe:获取签名
     * @author: zhangchunming
     * @date: 2016年10月9日下午3:15:30
     * @param pri
     * @param salt
     * @param admin_user
     * @return: String
     */
    public static String getSign(String pri,String salt,String admin_user) {  
        if(!StringUtil.isEmpty(pri)) {  
            HttpCLientDemo.pri = pri;  
        } 
        if(!StringUtil.isEmpty(salt)){
            HttpCLientDemo.salt = salt;
        }
        if(!StringUtil.isEmpty(admin_user)){
            HttpCLientDemo.admin_user = admin_user;
        }
        HttpCLientDemo.data = HttpCLientDemo.salt+HttpCLientDemo.admin_user+HttpCLientDemo.ts;  
        String sign = SHA256Util.sign(HttpCLientDemo.pri, HttpCLientDemo.data);
        System.out.println("--------------sign:"+sign);
        return sign;
    }  
    public static void main(String[] args) {
        JSONObject json = new JSONObject();  
        JSONObject data = new JSONObject();  
        data.put("amount", "10");
        data.put("recipient", "recipient");
        data.put("sender", "sender");
        json.put("data", data);
        json.put("admin_user", admin_user);
        json.put("ts", ts);
        json.put("sign", getSign(pri,salt,admin_user));
//        System.out.println(HttpCLientDemo.post(json.toJSONString()));  
    }
}
