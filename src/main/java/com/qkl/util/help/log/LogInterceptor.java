package com.qkl.util.help.log;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.qkl.util.help.BeanUtil;

/**
 * 日志拦截器
 * 
 * @author whl
 * 
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>(
			"StopWatch-StartTime");

	/**
	 * 在controller后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在controller前拦截日志，记录开始时间
	 * <p> (描述)  </p>
	 * @Title: logInfo 
	 * @param @param HttpServletRequest 
	 * @param @param HttpServletResponse 
	 * @param @param handler 
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年10月21日 下午2:47:40
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		// Map map = new HashMap();
		// Enumeration paramNames = request.getParameterNames();
		// while (paramNames.hasMoreElements()) {
		// String paramName = (String) paramNames.nextElement();
		//
		// String[] paramValues = request.getParameterValues(paramName);
		// if (paramValues.length == 1) {
		// String paramValue = paramValues[0];
		// if (paramValue.length() != 0) {
		// System.out.println("参数：" + paramName + "=" + paramValue);
		// map.put(paramName, paramValue);
		// }
		// }
		// }
		// logger.info("%s preHandle"+
		// request.getRequestURI()+"参数：" + JSON.toJSONString(map));

		Logger logger = getLogger(handler);
		Map<String, Object> map = getUserName(request);
		if (map != null) {
			logInfo(logger, "preHandle", map.get("userName").toString(),
					request.getRequestURI(), request.getParameterMap());
		} else {
			String ip = getIp(request);
			logger.info("#%s preHandle ip：" + ip + "访问系统！");
		}
		return true;// 继续流程
	}

	/**
	 * 日志
	 * <p> (描述)  </p>
	 * @Title: logInfo 
	 * @param @param HttpServletRequest 
	 * @param @param HttpServletResponse 
	 * @param @param Object 
	 * @param @param Exception 
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年10月21日 下午2:47:40
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Logger logger = getLogger(handler);
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		Map<String, Object> map = getUserName(request);

		if (map != null) {
			if (consumeTime > 500) {// 此处认为处理时间超过500毫秒的请求为慢请求
				// 记录到日志文件
				logWarn(logger, "afterCompletion", map.get("userName")
						.toString(), request.getRequestURI(), consumeTime);
			} else {
				logInfo(logger, "afterCompletion", map.get("userName")
						.toString(), request.getRequestURI(), consumeTime);
			}
		}
	}

	public Map<String, Object> getUserName(HttpServletRequest request) {
		Map<String, Object> map = null;
		Object o = request.getSession().getAttribute("loginUser");
		if (o != null) {
			map = BeanUtil.bean2Map(o);
		}
		return map;
	}

	public Logger getLogger(Object handler) {
		HandlerMethod temp = (HandlerMethod) handler;
		Logger logger = Logger.getLogger(temp.getBean().getClass());
		return logger;
	}

	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
		return ip;
	}

	/**
	 * 参数日志
	 * <p> (描述)  </p>
	 * @Title: logInfo 
	 * @param @param logger 
	 * @param @param ks 自定义内容
	 * @param @param userName 用户
	 * @param @param uri 操作
	 * @param @param parameterMap 参数
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年10月21日 下午2:47:40
	 */
	public static void logInfo(Logger logger, String ks, String userName,
			String uri, Map parameterMap) {
		logger.info("#%s "+ks+" 用户：" + userName + "， 操作：" + uri + "，参数："
				+ JSON.toJSONString(parameterMap));
	}

	/**
	 * 返回日志
	 * <p> (描述)  </p>
	 * @Title: logInfo 
	 * @param @param logger
	 * @param @param ks 自定义内容
	 * @param @param userName 用户
	 * @param @param uri 操作
	 * @param @param returnMap 返回
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年10月21日 下午2:51:28
	 */
	public static void logInfo(Logger logger,String ks, String userName,
			String uri, Object returnMap) {
		logger.info("#%s "+ks+" 用户：" + userName + "， 操作：" + uri + "，返回："
				+ JSON.toJSONString(returnMap));
	}
	
	
	public static void logInfo(Logger logger, String ks, String userName,
			String uri, long consumeTime) {
		logger.info("#%s " + ks + " 用户：" + userName + "， 操作：" + uri
				+ ",耗时millis :" + consumeTime);
	}

	public static void logWarn(Logger logger, String ks, String userName,
			String uri, long consumeTime) {
		logger.warn("#%s " + ks + " 用户：" + userName + "， 操作：" + uri
				+ ",耗时millis :" + consumeTime);
	}
}
