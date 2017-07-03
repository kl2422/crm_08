package com.shsxt.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.base.ResultInfo;
import com.shsxt.dto.UserQuery;
import com.shsxt.model.User;
import com.shsxt.service.UserService;
import com.shsxt.vo.UserLoginIdentity;

@RequestMapping("user")
@Controller
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
//	@RequestMapping("login")
//	@GetMapping("login")
	@PostMapping("login")
//	@PutMapping
	@ResponseBody
	public ResultInfo login(String userName, String password) {
		logger.info("這是一個參數：userName={}, password={}", userName, password);
		UserLoginIdentity userLoginIdentity = userService.login(userName, password);
		return success(userLoginIdentity);
	}
	
	@RequestMapping("find_customer_manager")
	@ResponseBody
	public List<User> findCustomerManager () {
		List<User> users = userService.findCustomerManager();
		return users;
	}
	
	
	@RequestMapping("index")
	public String index() {
		return "user";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object>selectForPage(UserQuery query) {
		Map<String, Object> result = userService.selectForPage(query);
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(User user) {
		userService.add(user);
		return success("添加成功");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(User user) {
		userService.update(user);
		return success("修改成功");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(String ids) {
		userService.deleteBatch(ids);
		return success("删除成功");
	}
}
