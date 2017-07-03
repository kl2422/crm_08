package com.shsxt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shsxt.annotation.SystemLog;
import com.shsxt.base.BaseController;
import com.shsxt.service.UserService;
import com.shsxt.util.LoginUserUtil;
import com.shsxt.vo.UserLoginIdentity;

@Controller
public class IndexController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("index")
	@SystemLog(description = "登录页面")
	public String index() {
		return "index";
	}
	
	
	@RequestMapping("main")
	@SystemLog(description = "首页")
	public String main(Model model, HttpServletRequest request) {
		// 获取登录用户的信息
		Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
		UserLoginIdentity userLoginIdentity = userService.findLoginUser(userId);
		model.addAttribute("currentUser", userLoginIdentity);
		return "main";
	}
}
