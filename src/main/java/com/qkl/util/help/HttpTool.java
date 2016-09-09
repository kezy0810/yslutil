package com.qkl.util.help;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Http操作辅助工具
 */
public class HttpTool {
	/**
	 * 发送请求数据
	 * @param get_url url地址
	 * @param content 发送内容 key=value形式
	 * @return 返回结果
	 * @throws Exception
	 */
	public static boolean sendData(String url, NameValuePair[] data) {
	    HttpClient client = new HttpClient(); 
        PostMethod method = new PostMethod(url); 
        //client.getParams().setContentCharset("GBK");      
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
        method.setRequestBody(data);
        try {
            client.executeMethod(method);   
            
            String SubmitResult =method.getResponseBodyAsString();
                    
            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult); 
            Element root = doc.getRootElement();


            String code = root.elementText("code"); 
            String msg = root.elementText("msg");   
            String smsid = root.elementText("smsid");   
            
            
            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);
                        
             if("2".equals(code)){
                System.out.println("短信提交成功");
                return true;
            }else{
                return false;
            }
            
        } catch (HttpException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
	}

}