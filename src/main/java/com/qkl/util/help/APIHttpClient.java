package com.qkl.util.help;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class APIHttpClient {
    // 接口地址  
    private  String URL = "http://test.tfccwallet.com";  
    private  String API = "/api/tx/admin_make";  
    private HttpClient httpClient = null;  
    private HttpPost method = null;  
    private long startTime = 0L;  
    private long endTime = 0L;  
    private int status = 0; //0.成功 1.执行方法失败 2.协议错误 3.网络错误 
    //转账相关参数
    private static String pri = "693369e4bd1ce20bab88b461e0d47d5ae69bd1b7b3a33ffcd3fab801ba04a424";
    private static String salt = "c3810b35aaee551728f847f857ef78ca33f6ec4e7fdee034264e2919e471b606";
    private static String admin_user = "sanapi";
    private static String sign = "";
    private static String data = "";
    private static String ts = String.valueOf(System.currentTimeMillis());
    /** 
     * 接口地址 
     *  
     * @param url 
     */  
    public APIHttpClient(String url,String api) {  
        try {
            if(!StringUtil.isEmpty(url)) {  
                this.URL = url;  
            }  
            if(!StringUtil.isEmpty(api)){
                this.API = api;
            }
            httpClient = new DefaultHttpClient();  
            method = new HttpPost(new URI(URL+API));
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }  
    /** 
     * 调用 API 
     *  
     * @param parameters 
     * @return 
     */  
    public String post(String parameters) {  
        String body = null;  
        System.out.println("parameters:" + parameters);  
        if (method != null & parameters != null  
                && !"".equals(parameters.trim())) {  
            try {  
  
                // 建立一个NameValuePair数组，用于存储欲传送的参数  
                method.addHeader("Content-type","application/json; charset=utf-8");  
                method.setHeader("Accept", "application/json");
                
               /* List<NameValuePair> params=new ArrayList<NameValuePair>();  
                //建立一个NameValuePair数组，用于存储欲传送的参数  
                params.add(new BasicNameValuePair("data",parameters));  
                //添加参数  
                method.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));  */
                
                method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));  
                startTime = System.currentTimeMillis();  
  
                HttpResponse response = httpClient.execute(method);  
                  
                endTime = System.currentTimeMillis();  
                int statusCode = response.getStatusLine().getStatusCode();  
                  
                System.out.print("-------------statusCode:" + statusCode);  
                System.out.print("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));  
                if (statusCode != HttpStatus.SC_OK) {  
                    System.err.println("-------------Method failed:" + response.getStatusLine());  
                    status = 1;  
                }  
  
                // Read the response body  
                body = EntityUtils.toString(response.getEntity());  
  
            } catch (IOException e) {  
                // 网络错误  
                e.printStackTrace();
                status = 3;  
            } finally {  
                System.out.println("------------------调用接口状态：" + status);  
            }  
  
        }  
        return body;  
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
            APIHttpClient.pri = pri;  
        } 
        if(!StringUtil.isEmpty(salt)){
            APIHttpClient.salt = salt;
        }
        if(!StringUtil.isEmpty(admin_user)){
            APIHttpClient.admin_user = admin_user;
        }
        APIHttpClient.data = APIHttpClient.salt+APIHttpClient.admin_user+APIHttpClient.ts;  
        String sign = SHA256Util.sign(APIHttpClient.pri, APIHttpClient.data);
        System.out.println("--------------sign:"+sign);
        return sign;
    }  
    public static String getAdminUser(String admin_user) {  
        if(!StringUtil.isEmpty(admin_user)) {  
            APIHttpClient.admin_user = admin_user;  
        } 
        return APIHttpClient.admin_user;
    }  
    public static void main(String[] args) {  
        APIHttpClient httpClient = new APIHttpClient(null,null);  
        JSONObject json = new JSONObject();  
        JSONObject data = new JSONObject();  
        data.put("amount", "10");
        data.put("recipient", "recipient");
        data.put("sender", "sender");
        json.put("data", data);
        json.put("admin_user", admin_user);
        json.put("ts", ts);
        json.put("sign", getSign(pri,salt,admin_user));
        System.out.println(httpClient.post(json.toJSONString()));  
    } 
    /**
     * @describe:
     * @author: zhangchunming
     * @date: 2016年10月9日下午4:06:21
     * @param url 请求的url
     * @param api 请求的方法
     * @param sender
     * @param recipient
     * @param amount
     * @param pri
     * @param salt
     * @param admin_user
     * @return: String
     */
    public static String turnOut(String url,String api,String sender,String recipient,String amount,String pri,String salt,String admin_user){
        if(StringUtil.isEmpty(sender)||StringUtil.isEmpty(recipient)||StringUtil.isEmpty(amount)||
                StringUtil.isEmpty(pri)||StringUtil.isEmpty(salt)||StringUtil.isEmpty(admin_user)){
            return null;
        }
        APIHttpClient httpClient = new APIHttpClient(url,api);  
        JSONObject json = new JSONObject();  
        JSONObject data = new JSONObject();  
        data.put("amount", amount);
        data.put("recipient", recipient);
        data.put("sender", sender);
        json.put("data", data);
        json.put("admin_user", getAdminUser(admin_user));
        json.put("ts", ts);
        json.put("sign", getSign(pri,salt,admin_user));
        String resStr = httpClient.post(json.toJSONString());
        System.out.println("resStr="+resStr);  
        return resStr;
    }
}
