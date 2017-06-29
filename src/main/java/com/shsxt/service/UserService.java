package com.shsxt.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.dao.UserDao;
import com.shsxt.exception.ParamException;
import com.shsxt.model.User;
import com.shsxt.util.MD5Util;
import com.shsxt.util.UserIDBase64;
import com.shsxt.vo.UserLoginIdentity;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param password 密码
	 */
	public UserLoginIdentity login(String userName, String password) {
		
		// 非空验证
		if (StringUtils.isBlank(userName)) {
			throw new ParamException(100, "请输入用户名");
		}
		if (StringUtils.isBlank(password)) {
			throw new ParamException(101, "请输入密码");
		}
		// 根据用户名查询用户在验证
		User user = userDao.findByUserName(userName.trim());
		if (user == null) {
			throw new ParamException(102, "用户名密码错误，请重新输入");
		}
		// 密码验证：需要MD5加密
		if (!MD5Util.md5Method(password).equals(user.getPassword())) {
			throw new ParamException(103, "用户名密码错误，请重新输入");
		}
		// 构建登录实体
//		UserLoginIdentity userLoginIdentity = new UserLoginIdentity();
//		userLoginIdentity.setUserIdString(UserIDBase64.encoderUserID(user.getId()));
//		userLoginIdentity.setRealName(user.getTrueName());
//		userLoginIdentity.setUserName(userName);
		UserLoginIdentity userLoginIdentity = buildLoginIdentity(user);
		return userLoginIdentity;
	}
	
	/**
	 * 获取登录用户信息
	 * @param userId
	 * @return
	 */
	public UserLoginIdentity findLoginUser(Integer userId) {
		if (userId == null || userId < 1) {
			throw new ParamException(100, "请登录");
		}
		User user = userDao.findById(userId);
		UserLoginIdentity userLoginIdentity = buildLoginIdentity(user);
		return userLoginIdentity;
	}
	
	/**
	 * 查询客户经理
	 * @return
	 */
	public List<User> findCustomerManager() {
		return userDao.findByRoleName("客户经理");
	}

	/**
	 * 构建登录信息
	 * @param user
	 * @return
	 */
	private static UserLoginIdentity buildLoginIdentity(User user) {
		UserLoginIdentity userLoginIdentity = new UserLoginIdentity();
		userLoginIdentity.setUserIdString(UserIDBase64.encoderUserID(user.getId()));
		userLoginIdentity.setRealName(user.getTrueName());
		userLoginIdentity.setUserName(user.getUserName());
		return userLoginIdentity;
	}
	
}
