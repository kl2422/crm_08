package com.shsxt.proxy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shsxt.base.AssertUtil;
import com.shsxt.base.Constant;
import com.shsxt.exception.LoginException;
import com.shsxt.model.UserRole;
import com.shsxt.service.PermissionService;
import com.shsxt.service.UserRoleService;
import com.shsxt.util.LoginUserUtil;

@Component
@Aspect
public class PermissionProxy {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PermissionService permissionService;
	
	private static Logger logger = LoggerFactory.getLogger(PermissionProxy.class);
	
	/**
	 * 定义切入点
	 */
	@Pointcut("execution(* com.shsxt.controller.*.*(..))")
	public void pointcut() {
		
	}
	
	@Around(value="pointcut()")
//	@Around(value="execution(* com.shsxt.controller.*.*(..))")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable { // pjp连接点对象
		// 用户是否登录
		Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		logger.info("uri = {}, ctx = {}", uri, ctx);
		String indexUri = "/index";
		String loginUri = "/user/login";
		logger.info("indexUri = {}, loginUri = {}", indexUri, loginUri);
		if (indexUri.equals(uri) || loginUri.equals(uri)) { // 放行
			return pjp.proceed();
		}
		if (userId == null || userId < 1) {
			throw new LoginException(201, "请先登录");
		}
		
		
		// 先从session查询是否存在用户的权限，如果存在就通过
//		List<String> permissions = (List<String>) request.getSession()
//				.getAttribute(Constant.USER_PERMISSION_SESSION_KEY);
//		if (permissions != null && !permissions.isEmpty()) {
//			return pjp.proceed();
//		}
		// 获取用户权限--》先获取角色 --》获取权限
		List<UserRole> userRoles = userRoleService.findUserRoles(userId);
		AssertUtil.isTrue(userRoles == null || userRoles.isEmpty(), "您无权访问此系统");
		String roleIds = "";
		for (UserRole userRole : userRoles) {
			roleIds += userRole.getRoleId() + ",";
		}
		List<String> permissions = permissionService.findRolePermissions(roleIds.substring(0, roleIds.lastIndexOf(",")));
//		String permissioFront = request.getParameter("permission"); // 后台权限认证
//		AssertUtil.isTrue(!permissions.contains(permissioFront), "您无权操作此模块");
		
		// 将权限存入session
		request.getSession().setAttribute(Constant.USER_PERMISSION_SESSION_KEY, permissions);
		// 执行代码
		Object result = pjp.proceed();
		return result;
	}
}
