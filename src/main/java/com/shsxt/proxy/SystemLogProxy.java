package com.shsxt.proxy;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.shsxt.annotation.SystemLog;
import com.shsxt.model.Log;
import com.shsxt.service.LogService;
import com.shsxt.util.LoginUserUtil;

@Component
@Aspect
public class SystemLogProxy {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private LogService logService;
	
	/**
	 * 定义切入点
	 */
	@Pointcut("@annotation(com.shsxt.annotation.SystemLog)")
	public void pointcut() {
		
	}
	
	@Around(value="pointcut()&&@annotation(systemLog)")
	public Object aroundMethod(ProceedingJoinPoint pjp, SystemLog systemLog) throws Throwable {
		
		// 收集日志
		Log log = new Log();
		log.setCreateDate(new Date());
		log.setDescription(systemLog.description());
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		log.setMethod(method.getName());
		
		Map<String, String[]> params = request.getParameterMap();
		
		log.setParams(JSON.toJSONString(params));
		String ip = request.getRemoteAddr();
		log.setRequestIp(ip);
		log.setType(0);
		
		Long startTime = new Date().getTime();
		Object result = pjp.proceed();
		Long endTime = new Date().getTime();
		Long executeTime = endTime - startTime;
		log.setExecuteTime(executeTime);
		log.setResult(JSON.toJSONString(result));
		String loginUserName = LoginUserUtil.releaseUserNameFromCookie(request);
		log.setCreateMan(loginUserName);
		
		logService.addLog(log);
		
		return result;
		
	}
}
