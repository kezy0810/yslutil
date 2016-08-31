package com.qkl.util.help;

/**
 * 判断浏览器类型
 * <p>Description：
 * String agent=request.getHeader("User-Agent").toLowerCase();
 * 从request中取出传入参数，判断浏览器类型
 * </p>
 * @project_Name yc_util
 * @class_Name BrowserTypeUtil.java
 * @author lian
 * @date 2015年10月21日
 * @version v1.0
 */
public class BrowserTypeUtil {
	public static String getBrowserName(String agent) {
		  if(agent.indexOf("msie 7")>0){
		   return "ie7";
		  }else if(agent.indexOf("msie 8")>0){
		   return "ie8";
		  }else if(agent.indexOf("msie 9")>0){
		   return "ie9";
		  }else if(agent.indexOf("msie 10")>0){
		   return "ie10";
		  }else if(agent.indexOf("msie")>0){
		   return "ie";
		  }else if(agent.indexOf("opera")>0){
		   return "opera";
		  }else if(agent.indexOf("firefox")>0){
		   return "firefox";
		  }else if(agent.indexOf("webkit")>0){
		   return "webkit";
		  }else if(agent.indexOf("gecko")>0 && agent.indexOf("rv:11")>0){
		   return "ie11";
		  }else{
		   return "Others";
		  }
	}
}
