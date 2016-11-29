package com.qkl.util.help;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
public class APIHttpClient {
    // 接口地址  
    private  static String URL = "http://test.tfccwallet.com";  
//    private  String https_url = "https://test.tfccwallet.com";  
    private  static String API = "/api/tx/admin_make";  
    private HttpClient httpClient = null;  
    private PostMethod method = null;  
    private long startTime = 0L;  
    private long endTime = 0L;  
    private int status = 0; //1.成功 0.执行方法失败 2.网络异常 
    //转账相关参数
    private static String pri = "693369e4bd1ce20bab88b461e0d47d5ae69bd1b7b3a33ffcd3fab801ba04a424";
    private static String salt = "c3810b35aaee551728f847f857ef78ca33f6ec4e7fdee034264e2919e471b606";
    private static String admin_user = "sanapi";
    private static String sign = "";
    private static String data = "";
    private static String ts = String.valueOf(System.currentTimeMillis());
    
    public APIHttpClient(){
        
    }
    /** 
     * 接口地址 
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
            httpClient = new HttpClient();  
            httpClient.getParams().setContentCharset("UTF-8");
            method = new PostMethod(URL+API);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    } 
 // 封装参数
    public static NameValuePair[] PackDataParas(String sign, String ts,String admin_user,String data){
        System.out.println("发送参数PackDataParas：sign="+sign+"---ts="+ts+"---admin_user="+admin_user+"---data="+data);
        NameValuePair[] dataPair = {
                new NameValuePair("sign", sign), 
                new NameValuePair("ts", ts),
                new NameValuePair("admin_user", admin_user), 
                new NameValuePair("data", data),
        };
        return dataPair;
    }
    /** 
     * 调用 API 
     *  
     * @param parameters 
     * @return 
     */  
    public String post(NameValuePair[] PackDataParas) {  
        String body = null;  
        System.out.println("PackDataParas:" + PackDataParas.toString());  
        if (method != null & PackDataParas != null  && !"".equals(PackDataParas.toString())) {  
            try {  
  
                // 建立一个NameValuePair数组，用于存储欲传送的参数  
               /* method.addHeader("Content-type","application/json; charset=utf-8");  
                method.setHeader("Accept", "application/json");*/
                method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
                
                /*List<NameValuePair> params=new ArrayList<NameValuePair>();  
                //建立一个NameValuePair数组，用于存储欲传送的参数  
                params.add(new BasicNameValuePair("data",parameters));  
                //添加参数  
                method.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));  */
                
//                method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));  
                
                method.setRequestBody(PackDataParas);        
                
                /*HttpResponse response = httpClient.execute(method);  
                  
                endTime = System.currentTimeMillis();  
                int statusCode = response.getStatusLine().getStatusCode();  
                  
                System.out.print("-------------statusCode:" + statusCode);  
                System.out.print("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));  
                if (statusCode != HttpStatus.SC_OK) {  
                    System.err.println("-------------Method failed:" + response.getStatusLine());  
                    status = 1;  
                }  
  
                // Read the response body  
                body = EntityUtils.toString(response.getEntity());  */
                startTime = System.currentTimeMillis();  
                httpClient.executeMethod(method);  
                int statusCode = method.getStatusLine().getStatusCode();
                endTime = System.currentTimeMillis();
                System.out.println("调用API 花费时间(单位：毫秒)：" + (endTime - startTime)); 
                
                if (statusCode != HttpStatus.SC_OK) {  
                    System.err.println("-------------Method failed:" + method.getStatusLine());  
                    status = 0;  
                }  
                if(statusCode == HttpStatus.SC_OK){
                    status = 1;  
                }
                body =method.getResponseBodyAsString();
                System.out.println("转账接口调用------------------返回结果body="+body);
            } catch (Exception e) {  
                e.printStackTrace();
                status = 2;  
            }finally{
                System.out.println("转账接口调用------------------返回状态码status="+status);
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
    /**
     * @describe:签名校验
     * @author: zhangchunming
     * @date: 2016年10月10日上午10:32:44
     * @param orderId
     * @param status
     * @param value
     * @param ts
     * @param sign
     * @param pri
     * @return: boolean
     */
    public static boolean validSign(String orderId,String status,String value,String ts,String sign,String pri) {  
        if(StringUtil.isEmpty(orderId)||StringUtil.isEmpty(status)||StringUtil.isEmpty(value)||StringUtil.isEmpty(ts)||StringUtil.isEmpty(sign)) {  
            return false;
        }
        if(!StringUtil.isEmpty(pri)) {  
            APIHttpClient.pri = pri;  
        } 
        String data = orderId+status+value+ts;
        String resSign = SHA256Util.sign(APIHttpClient.pri, data);
        if(sign.equals(resSign)){
           return true;
        }
        return false;
    }
       
    public static String getAdminUser(String admin_user) {  
        if(!StringUtil.isEmpty(admin_user)) {  
            APIHttpClient.admin_user = admin_user;  
        } 
        return APIHttpClient.admin_user;
    }  
    public static void main(String[] args) {  
        APIHttpClient httpClient = new APIHttpClient(null,null);  
        /*JSONObject json = new JSONObject();  
        JSONObject data = new JSONObject();  
        data.put("amount", "10");
        data.put("recipient", "recipient");
        data.put("sender", "sender");
        json.put("data", data);
        json.put("admin_user", admin_user);
        json.put("ts", ts);
        json.put("sign", getSign(pri,salt,admin_user));
        System.out.println(httpClient.post(json.toJSONString()));  */
//        String str = "sign=c91e45447ddcece3118eeb72c3f55d4628d24f349b53489b55fd265d4fbd4322&ts=1476008251959&admin_user=sanapi&data={\"sender\":\"test02\",\"amount\":\"10\",\"recipient\":\"test01\"}";
        String sign = "c91e45447ddcece3118eeb72c3f55d4628d24f349b53489b55fd265d4fbd4322";
        String data = "{\"sender\":\"test02\",\"amount\":\"10\",\"recipient\":\"test01\"}";
        NameValuePair[] packDataParas = PackDataParas(sign, ts, admin_user, data);
        System.out.println(httpClient.post(packDataParas));
        /*String data = "1234561101476090950801";
        String sign = SHA256Util.sign(APIHttpClient.pri, data);
        System.out.println("currentTimeMillis="+System.currentTimeMillis());  */
        
        //签名认证
//        validSign("23335","1" ,"10","1476187837548","8c12c33958fd8ca926cd40ab59ce32b422bae496cdb31dbfb8c497eb7c798e44","693369e4bd1ce20bab88b461e0d47d5ae69bd1b7b3a33ffcd3fab801ba04a424");
//        httpsPost(sign, ts, admin_user, data,URL);
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
            System.out.println("转账接口-------参数有误");
            return null;
        }
        APIHttpClient httpClient = new APIHttpClient(url,api);  
//        String sign = "c91e45447ddcece3118eeb72c3f55d4628d24f349b53489b55fd265d4fbd4322";
//        String data = "{\"sender\":\"test02\",\"amount\":\"10\",\"recipient\":\"test01\"}";
        String sign = getSign(pri, salt, admin_user);
        String data = "{\"sender\":\""+sender+"\",\"amount\":\""+amount+"\",\"recipient\":\""+recipient+"\"}";
        String ts = String.valueOf(System.currentTimeMillis());
        NameValuePair[] packDataParas = PackDataParas(sign, ts, admin_user, data);
        String resStr = httpClient.post(packDataParas);
        return resStr;
    }
    /**
     * @describe:https请求，转出三姐宝至钱包
     * @author: zhangchunming
     * @date: 2016年11月19日下午3:43:24
     * @param url
     * @param api
     * @param sender
     * @param recipient
     * @param amount
     * @param pri
     * @param salt
     * @param admin_user
     * @return: String
     */
    public static String httpsTurnOut(String url,String api,String sender,String recipient,String amount,String pri,String salt,String admin_user,String txnType){
        if(StringUtil.isEmpty(sender)||StringUtil.isEmpty(recipient)||StringUtil.isEmpty(amount)||
                StringUtil.isEmpty(pri)||StringUtil.isEmpty(salt)||StringUtil.isEmpty(admin_user)||StringUtil.isEmpty(txnType)){
            System.out.println("转账接口-------参数有误");
            return null;
        }
        String sign = getSign(pri, salt, admin_user);
        String data = "{\"sender\":\""+sender+"\",\"amount\":\""+amount+"\",\"recipient\":\""+recipient+"\",\"txnType\":\""+txnType+"\"}";
        if(StringUtil.isEmpty(url)){
            url = URL+API;
        }
        String ts = String.valueOf(System.currentTimeMillis());
        String resStr = httpsPost(sign, ts, admin_user, data,url);
        return resStr;
    }
    
    public static String httpsPost(String sign, String ts,String admin_user,String data,String url) {  
        try {  
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            };
            sign = URLEncoder.encode(sign, "UTF-8");
            ts = URLEncoder.encode(ts, "UTF-8");
            admin_user = URLEncoder.encode(admin_user, "UTF-8");
            data = URLEncoder.encode(data, "UTF-8");
            String content = "sign="+sign+"&ts="+ts+"&admin_user="+admin_user+"&data="+data;
            System.out.println("------------httpsPost----请求内容content："+content);
            javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
            javax.net.ssl.TrustManager tm = new MyM();
            trustAllCerts[0] = tm;
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, null);
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            HttpURLConnection  httpsConn  = (HttpURLConnection) new URL(url).openConnection();
            //设置是否向httpsConn输出
            httpsConn.setDoOutput(true);
            //设置是否向httpsConn读入
            httpsConn.setDoInput(true);
            httpsConn.setRequestMethod("POST");
            // Post 请求不能使用缓存
            httpsConn.setUseCaches(false);
            httpsConn.setInstanceFollowRedirects(true);
            httpsConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpsConn.connect();
            //输出内容
            DataOutputStream out = new DataOutputStream(httpsConn.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close(); 
            //获取响应流
            InputStream response = httpsConn.getInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] dataArray = new byte[10240];
            int count = -1;
            while (-1 != (count = response.read(dataArray, 0, dataArray.length))) {
                result.write(dataArray, 0, count);
            }
            httpsConn.disconnect();
            String reponseStr = new String(result.toByteArray(), "utf-8");
            System.out.println("-----------httpsPost------------返回reponseStr："+reponseStr);
            return reponseStr;
        } catch (Exception e) {  
            e.printStackTrace();
        }finally{
//            System.out.println("转账接口调用------------------返回状态码status="+status);
        } 
        return null;  
    }  
    static class MyM implements TrustManager, X509TrustManager {
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {}
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {}
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
