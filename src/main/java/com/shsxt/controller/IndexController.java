package com.shsxt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shsxt.service.UserService;
import com.shsxt.util.LoginUserUtil;
import com.shsxt.vo.UserLoginIdentity;

@Controller
public class IndexController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	
	
	@RequestMapping("main")
	public String main(Model model, HttpServletRequest request) {
		// 获取登录用户的信息
		Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
		UserLoginIdentity userLoginIdentity = userService.findLoginUser(userId);
		model.addAttribute("currentUser", userLoginIdentity);
		return "main";
	}
}
