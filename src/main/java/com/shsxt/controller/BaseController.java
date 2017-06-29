package com.shsxt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	
	@ModelAttribute
	public void preMethod(Model model, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		model.addAttribute("ctx", contextPath);
	}
	
	
}
