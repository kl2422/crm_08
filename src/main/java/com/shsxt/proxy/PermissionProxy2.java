package com.shsxt.proxy;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shsxt.annotation.RequirePermissions;
import com.shsxt.base.AssertUtil;
import com.shsxt.base.Constant;
import com.shsxt.exception.UnAuthPermissionException;
import com.shsxt.model.UserRole;
import com.shsxt.service.PermissionService;
import com.shsxt.service.UserRoleService;
import com.shsxt.util.LoginUserUtil;

@Component
@Aspect
public class PermissionProxy2 {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PermissionService permissionService;
	
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
		if ("/index".equals(uri) || "/user/login".equals(uri)) { // 放行
			return pjp.proceed();
		}
		AssertUtil.intIsNotEmpty(userId, "请登录");
		
		// 获取用户权限--》先获取角色 --》获取权限
		List<UserRole> userRoles = userRoleService.findUserRoles(userId);
		AssertUtil.isTrue(userRoles == null || userRoles.isEmpty(), "您无权访问此系统");
		String roleIds = "";
		for (UserRole userRole : userRoles) {
			roleIds += userRole.getRoleId() + ",";
		}
		List<String> permissions = permissionService.findRolePermissions(roleIds.substring(0, roleIds.lastIndexOf(",")));
		
		
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method method = ms.getMethod();
		RequirePermissions requirePermissions = method.getAnnotation(RequirePermissions.class);
		if (requirePermissions != null) {
			String permission = requirePermissions.permission(); // 后台权限认证
			throw new UnAuthPermissionException(permission, "您无权操作此模块");
		}
		
		
		
		// 将权限存入session
		request.getSession().setAttribute(Constant.USER_PERMISSION_SESSION_KEY, permissions);
		// 执行代码
		Object result = pjp.proceed();
		return result;
	}
}
