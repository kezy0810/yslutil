package com.qkl.util.help.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 *  spring mvc异常捕获类
 * <p>Description：  </p>
 * @project_Name yc_util
 * @class_Name MyExceptionHandler.java
 * @author lihongxuan
 * @date 2015年8月31日
 * @version 1.0
 */
@Component
public class MyExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = Logger
			.getLogger("eventinfo");

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		StringBuffer msg = new StringBuffer();
		msg.append("异常拦截日志");
		msg.append("[uri＝").append(request.getRequestURI()).append("]");
		Enumeration<String> enumer = request.getParameterNames();
		while (enumer.hasMoreElements()) {
			String name = (String) enumer.nextElement();//参数名称 
			//System.out.println("name:"+name);
			String[] values = request.getParameterValues(name);
			msg.append("[").append(name).append("=");
			if(values != null){
				int i=0;
				for(String value: values){
					i++;
					msg.append(value);//   参数值
					//System.out.println("value:"+value);
					if(i < values.length){
						msg.append("｜");
					}
				}
			}
			msg.append("]");
		}
		//System.out.println("msg:"+msg);
		logger.error(msg,ex);
		if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有，x-requested-with  
            response.setHeader("sessionstatus", "404");//在响应头设置session状态  
        }
		return new ModelAndView("/404");
	}
}
