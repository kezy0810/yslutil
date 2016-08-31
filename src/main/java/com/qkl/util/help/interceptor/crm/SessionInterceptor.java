package com.qkl.util.help.interceptor.crm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qkl.util.help.filter.FilterUtil;
import com.qkl.util.help.session.Session;
import com.qkl.util.help.session.SessionManager;

/**过滤器
 * @author liuyang
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	private SessionManager sessionManager;
	

    /**
     * 验证URl的工具
     */
    private FilterUtil filterUtil = new FilterUtil();
	/**
	 * 忽略的URl表达式 优先级低于 filterList
	 * 例如一个URl既在noFilterList 又在filterList那么该URl仍需校验
	 */
	private List<String> noFilterList = new ArrayList<String>();
	
	/**
	 * 必须校验的URl  优先级高于 noFilterList
	 * 例如一个URl既在noFilterList 又在filterList那么该URl仍需校验
	 */
	private Map<String, String[]> filterMap  = new HashMap<String, String[]>();
	
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		uri = uri.substring(request.getContextPath().length());
		String webPath = request.getSession().getServletContext().getAttribute("webPath").toString();
		//过滤uri
		boolean isUnFilter = filterUtil.filterUrl(uri, noFilterList, filterMap);
		if(isUnFilter){
			return true;
		}else{ 
			if(uri.equals("/checkuser.do") || uri == "/checkuser.do"){
				return true;
			}else{
				try {
					Session session = sessionManager.getSession(httpRequest, httpResponse);
					if(null != session){
						Object obj = session.getAttribute(Constants.LOGIN_USER);
						if(null == obj){
							httpResponse.sendRedirect(webPath + "/login.do");
							return false;
						}else{
							return true;
						}
					}
					httpResponse.sendRedirect(webPath + "/login.do");
					return false;
				} catch (Exception e) {
					httpResponse.sendRedirect(webPath + "/error.do");
					return false;
				}
			}
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}





	public void setFilterUtil(FilterUtil filterUtil) {
		this.filterUtil = filterUtil;
	}



	public void setNoFilterList(List<String> noFilterList) {
		this.noFilterList = noFilterList;
	}


	public void setFilterMap(Map<String, String[]> filterMap) {
		this.filterMap = filterMap;
	}

	
}
