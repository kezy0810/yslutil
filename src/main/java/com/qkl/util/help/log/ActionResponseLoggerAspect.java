package com.qkl.util.help.log;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.Pagination;

/**
 * 日志记录
 *
 * <p>
 * Description：
 * </p>
 * 
 * @project_Name yc-udrs-web
 * @class_Name ActionResponseLoggerAspect.java
 * @author wanghaolong
 * @date 2015年9月6日 上午10:19:06
 * @version v1.0
 */
@Aspect
@Component
public class ActionResponseLoggerAspect {

	@AfterReturning(pointcut = "execution(* com.yc.dc.*.controller..*.*(..)))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
		String json = "";
		if (result instanceof AjaxResponse) {
			AjaxResponse m = (AjaxResponse) result;
			if (m.getData() instanceof Pagination) {
				Pagination<?> pagination = (Pagination<?>) m.getData();
				json = JSON.toJSONString(pagination != null ? pagination
						.getContent() : "");
			}
			if (m.getData() instanceof ArrayList) {
				json = JSON.toJSONString(m.getData());
			}
			logger.info("# returned:" + json);
			LogInterceptor.logInfo(logger, "afterReturning", "", joinPoint.getSignature().getName(), json);
		}
	}

/*	@Before("execution(* com.yc.dc.*.controller..*.*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
		logger.info("#%s Before:" + joinPoint.getTarget().getClass().getName()
				+ "." + joinPoint.getSignature().getName() + "(),参数："
				+ Arrays.toString(joinPoint.getArgs()));
	}*/
}