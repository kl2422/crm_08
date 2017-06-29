package com.shsxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.service.CustomerService;
import com.shsxt.vo.CustomerVO;


@RequestMapping("customer")
@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("find_all")
	@ResponseBody
	public List<CustomerVO> findAll() {
		List<CustomerVO> customerVOs = customerService.findAll();
		return customerVOs;
	}
	
}
