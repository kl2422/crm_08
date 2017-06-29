package com.shsxt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.exception.ParamException;
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
	public Map<String, Object> login(String userName, String password) {
		logger.info("這是一個參數：userName={}, password={}", userName, password);
		Map<String, Object> map = new HashMap<>();
		try {
			UserLoginIdentity userLoginIdentity = userService.login(userName, password);
			map.put("resultCode", 1);
			map.put("message", "Success");
			map.put("result", userLoginIdentity);
		} catch (ParamException e) {
			map.put("resultCode", e.getErrorCode());
			map.put("message", e.getMessage());
			map.put("result", e.getMessage());
		}
		
		return map;
	}
	
	@RequestMapping("find_customer_manager")
	@ResponseBody
	public List<User> findCustomerManager () {
		List<User> users = userService.findCustomerManager();
		return users;
	}
	
}
