package com.shsxt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.base.Constant;
import com.shsxt.base.ResultInfo;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.Customer;
import com.shsxt.service.CustomerService;
import com.shsxt.vo.CustomerVO;


@RequestMapping("customer")
@Controller
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("find_all")
	@ResponseBody
	public List<CustomerVO> findAll() {
		List<CustomerVO> customerVOs = customerService.findAll();
		return customerVOs;
	}
	@RequestMapping("index")
	public String index () {
		return "customer";

	}

	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object>selectForPage(CustomerQuery query) {
		Map<String, Object> result = customerService.selectForPage(query);
		return result;
	}

	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(Customer customer) {
		customerService.add(customer);
		return success(Constant.SUCCESS_MSG);
	}

	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(Customer customer) {
		customerService.update(customer);
		return success(Constant.SUCCESS_MSG);
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(String ids) {
		customerService.delete(ids);
		return success(Constant.SUCCESS_MSG);

	}
}
