package com.shsxt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.base.ResultInfo;
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
	
}
