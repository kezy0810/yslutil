/**
 * 
 * 版权声明钰诚集团北京数据中心，版权所有违者必究
 *
 *<br> Copyright：Copyright (c) 2015
 *<br> Company：钰诚集团北京数据中心
 *<br> @author 作者 liubang
 *<br> @data 2015-9-23 下午7:49:27
 *<br> @version v1.0
 */
/**
 * 
 * 版权声明钰诚集团北京数据中心，版权所有违者必究
 *
 *<br> Copyright：Copyright (c) 2015
 *<br> Company：钰诚集团北京数据中心
 *<br> @author 作者 liubang
 *<br> @data 2015-9-23 下午7:49:27
 *<br> @version v1.0
 */
package com.qkl.util.help;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 *
 * <p>Description：  </p>
 * @project_Name yc_util
 * @class_Name GenerateShortUrlUtil.java
 * @author liubang
 * @date 2015-9-23 下午7:49:27
 * @version v1.0
 */

public class GenerateShortUrlUtil {
	public static DefaultHttpClient httpclient;
	static {
		httpclient = new DefaultHttpClient();
//		httpclient = (DefaultHttpClient) HttpClientConnectionManager
//				.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端
	}
	
	/**
	 * 生成端连接信息
	 * 
	 * @author: Jerri 
	 * @date: 2014年3月22日下午5:31:15
	 */
	public static String  generateShortUrl(String url) {
		try {
			HttpPost httpost = new HttpPost("http://dwz.cn/create.php");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("url", url)); // 用户名称
			httpost.setEntity(new UrlEncodedFormEntity(params,  "utf-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "utf-8");
			System.out.println(jsonStr);
			JSONObject object = JSON.parseObject(jsonStr);
			System.out.println(object.getString("tinyurl"));
			return object.getString("tinyurl");
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		
	}
	
	/**
	 * 测试生成端连接
	 * @param args
	 * @author: Jerri 
	 * @date: 2014年3月22日下午5:34:05
	 */
	public static void main(String []args){
		generateShortUrl("http://172.16.16.16:7080/write/qrCode/index.do?id=40");
	}
}