package com.qkl.util.help.cookie;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.qkl.util.help.interceptor.crm.Constants;

/**cookie操作
 * @author liuyang
 *
 */
@Component
public class CookieManager {
	
	private CookieManager(){}
	private String domain;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * cookie添加信息
	 * @param response
	 * @param key cookie名称
	 * @param value cookie值
	 */
	public  void addCookie(HttpServletResponse response, String key, String value, int maxAge){
		if(null == key){
	        throw new IllegalArgumentException("key mus be not null");
		}else{
			Cookie cookie = new Cookie(key, domain+"-"+value);
			cookie.setPath("/");
//			cookie.setMaxAge(maxAge);/***cookie生命周期，以秒为单位**/
			cookie.setDomain(domain);
			response.addCookie(cookie);
		}
	}
	public  void updateCookie(HttpServletRequest request,HttpServletResponse response, String key, String value, int maxAge,String domain){
		if(null == key){
	        throw new IllegalArgumentException("key mus be not null");
		}else{
			Map<String,Object> cookieMap = readCookieMap(request,key,domain);
			Cookie cookie ;
			if(cookieMap.containsKey(key)){
				cookie = (Cookie) cookieMap.get(key);
			}else{
				cookie = new Cookie(key, domain+"-"+value);
			}
			if(response!=null){
				cookie.setPath("/");
//				cookie.setMaxAge(maxAge);/***cookie生命周期，以秒为单位**/
				cookie.setDomain(domain);
				response.addCookie(cookie);
			}
		}
	}
	/**
	 * 根据cookie获取cookie
	 * @param request
	 * @param key cookie的名称
	 * @return Cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String key,String domain){
		Map<String,Object> cookieMap = readCookieMap(request,key,domain);
		if(cookieMap.containsKey(key)){
			Cookie cookie = (Cookie) cookieMap.get(key);
			return cookie;
		}
		return null;
	}
	
	/**
	 * 讲cookie封装到Map中去
	 * @param request
	 * @return map
	 */
	private static Map<String, Object> readCookieMap(HttpServletRequest request,String key,String domain){
		Map<String, Object> cookieMap = new HashMap<String,Object>();
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for(Cookie cookie : cookies){
				if (key.equals(cookie.getName())&& StringUtils.startsWith(cookie.getValue(), domain)) {
					cookieMap.put(cookie.getName(), cookie);
				}
			}
		}
		return cookieMap;
	}
}
