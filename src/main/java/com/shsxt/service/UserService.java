package com.shsxt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.base.AssertUtil;
import com.shsxt.dao.UserDao;
import com.shsxt.dao.UserRoleDao;
import com.shsxt.dto.UserQuery;
import com.shsxt.exception.ParamException;
import com.shsxt.model.User;
import com.shsxt.model.UserRole;
import com.shsxt.util.MD5Util;
import com.shsxt.util.UserIDBase64;
import com.shsxt.vo.UserLoginIdentity;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param password 密码
	 */
	public UserLoginIdentity login(String userName, String password) {

		// 非空验证
		AssertUtil.isNotEmpty(userName, "请输入用户名");
		AssertUtil.isNotEmpty(password, 100, "请输入密码");
		//		if (StringUtils.isBlank(userName)) {
		//			throw new ParamException(100, "请输入用户名");
		//		}
		//		if (StringUtils.isBlank(password)) {
		//			throw new ParamException(101, "请输入密码");
		//		}
		// 根据用户名查询用户在验证
		User user = userDao.findByUserName(userName.trim());
		AssertUtil.notNull(user);
		//		if (user == null) {
		//			throw new ParamException(102, "用户名密码错误，请重新输入");
		//		}
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
	 * 分页查询
	 * @param query
	 * @return
	 */
	public Map<String, Object> selectForPage(UserQuery query) {

		PageList<User> users = userDao.selectForPage(query, query.buildPageBounds());
		//		for(User user : users) {
		//			//
		//		}
		Map<String, Object> result = new HashMap<>();
		result.put("rows", users);
		result.put("total", users.getPaginator().getTotalCount());
		return result;
	}

	/**
	 * 添加用户
	 * @param user
	 */
	public void add(User user) {
		// 非空验证 用户名 密码 真实姓名 电话 邮箱 角色
		checkParams(user);
		String userName = user.getUserName();
		// 用户名唯一验证
		User userByUserName = userDao.findByUserName(userName);
		AssertUtil.isTrue(userByUserName != null, "该用户名已存在");
		// 邮箱、手机号唯一验证 TODO
		String password = user.getPassword();
		// 密码加密
		password = MD5Util.md5Method(password);
		user.setPassword(password);
		// 插入数据库
		userDao.insert(user);
		// 关联角色
		saveUserRoles(user);
	}

	/**
	 * 更新
	 * @param user
	 */
	public void update(User user) {
		Integer id = user.getId();
		AssertUtil.intIsNotEmpty(id, "请选择一条记录进行修改");
		checkParams(user);
		User userFromDB = userDao.findById(user.getId());
		if (!userFromDB.getUserName().equals(user.getUserName())) {
			// 用户名唯一验证
			User userByUserName = userDao.findByUserName(user.getUserName());
			AssertUtil.isTrue(userByUserName != null, "该用户名已存在");
		}
		// TODO 手机号以及邮箱唯一验证
		// 更新
		userDao.update(user);
		// 关联角色
		// 先删除 在关联
		userRoleDao.deleteUserRoles(user.getId());
		saveUserRoles(user);
	}

	/**
	 * 删除
	 * @param ids
	 */
	public void deleteBatch(String ids) {
		AssertUtil.isNotEmpty(ids, "请选择删除的记录");
		userDao.deleteBatch(ids);
		// 删除user_role
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

	/**
	 * 基本参数验证
	 * @param user
	 */
	private static void checkParams(User user) {
		String userName = user.getUserName();
		AssertUtil.isNotEmpty(userName, "请输入用户名");
		String password = user.getPassword();
		AssertUtil.isNotEmpty(password, "请输入密码");
		String realName = user.getTrueName();
		AssertUtil.isNotEmpty(realName, "请输入真实姓名");
		String phone = user.getPhone();
		AssertUtil.isNotEmpty(phone, "请输入手机号");
		String email = user.getEmail();
		AssertUtil.isNotEmpty(email, "请输入邮箱");
		Integer[] roleIds = user.getRoleIds();
		AssertUtil.isTrue(roleIds == null || roleIds.length == 0, "请选择角色");
	}

	/**
	 * 插入角色
	 * @param user
	 */
	private void saveUserRoles(User user) {
		List<UserRole> userRoles = new ArrayList<>();
		for (Integer roleId : user.getRoleIds()) {
			UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(user.getId());
			userRoles.add(userRole);
		}
		userRoleDao.insertBatch(userRoles);
	}

	/**
	 * 修改密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param confirmPassword
	 */
	public void updatePassword(int userId, String oldPassword, String newPassword,
			String confirmPassword) {
		// 基本参数校验
		AssertUtil.isTrue(userId == 0, "请重新登陆");
		AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "请输入旧密码");
		AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输入新密码");
		AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "请输入确认密码");
		AssertUtil.isTrue(!newPassword.equals(confirmPassword), "新密码和确认密码不一致, 请重新输入");

		// 查询用户判断旧密码输入是否正确
		User user = userDao.findById(userId);
		AssertUtil.notNull(user, "该用户不存在或已被注销");
		String password = MD5Util.md5Method(oldPassword);
		AssertUtil.isTrue(!password.equals(user.getPassword()), "旧密码输入错误, 请重新输入");
		// 更新
		String newPwd = MD5Util.md5Method(newPassword);
		int mnt = userDao.updatePassword(user.getId(), newPwd);
		AssertUtil.isTrue(mnt == 0, "更新失败, 请重试");
	}

}
