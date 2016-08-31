package com.qkl.util.help.session;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.qkl.util.help.RC4;
import com.qkl.util.help.cache.CacheManager;
import com.qkl.util.help.cookie.CookieManager;

/**
 * session具体操作
 * 
 * @author liuyang
 *
 */
@Component
public class SessionManager {

	@Resource
	CacheManager cacheManager;

	@Resource
	CookieManager cookieManager;
	
	private String sessionIdName = "sid";
	
	private String rc4Id = "rc4Id";

	private int cacheOutTime = 30 * 60 *1;

	private int cookieOutTime = 30 * 60 *1;
	
	public Session getSession(HttpServletRequest request, HttpServletResponse response){
		String sessionId = request.getParameter("sessionId") == null  ? this.getSessionIdCookie(request) : request.getParameter("sessionId");
		if(null == sessionId){
			return this.createSession(response);
		}else{
			sessionId = RC4.decry_RC4(sessionId, rc4Id);
			Object obj = cacheManager.get(sessionId);
			if(null == obj){
				return this.createSession(response);
			}else{
				updateSession((Session) obj, request,response);
				return (Session) obj;
			}
		}
	}
	
	public boolean addRedis(Session session){
    	return cacheManager.set(session.getId().toString(), session,
				cacheOutTime);
    }
	
	@SuppressWarnings("static-access")
	public void addCookie(Session session,HttpServletResponse response){
		cookieManager.addCookie(response, sessionIdName, RC4.encry_RC4_string(session.getId().toString(),rc4Id),cookieOutTime);
	}
	public void updateCookie(Session session,HttpServletRequest request,HttpServletResponse response){
		cookieManager.updateCookie(request,response, sessionIdName, RC4.encry_RC4_string(session.getId().toString(),rc4Id),cookieOutTime,cookieManager.getDomain());
	}
	
	public void removeSession(Session session){
		String key = session.getId();
		cacheManager.del(key);
	}
	
	public void addSession(Session session,HttpServletResponse response){
		boolean isSave = addRedis(session);
		if(isSave) addCookie(session, response);
	}
	public void updateSession(Session session,HttpServletRequest request,HttpServletResponse response){
		boolean isSave = addRedis(session);
		if(isSave) updateCookie(session, request, response);
	}
	private Session createSession(HttpServletResponse response) {
		Session session = new Delegate().getSession();
		boolean isSave = this.addRedis(session);
		if (isSave) {
			addCookie(session, response);
			return session;
		}
		return null;
	}

	private String getSessionIdCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if ((cookies == null) || (cookies.length == 0)) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (sessionIdName.equals(cookies[i].getName())&& StringUtils.startsWith(cookies[i].getValue(), cookieManager.getDomain())) {
				return StringUtils.remove(cookies[i].getValue(), cookieManager.getDomain()+"-");
			}
		}
		return null;
	}
}
