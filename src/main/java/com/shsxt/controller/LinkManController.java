package com.shsxt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.base.ResultInfo;
import com.shsxt.dto.ContactQuery;
import com.shsxt.model.LinkMan;
import com.shsxt.model.Customer;
import com.shsxt.service.LinkManService;
import com.shsxt.service.CustomerService;

@Controller
@RequestMapping("linkman")
public class LinkManController extends BaseController {
	
	@Autowired
	private LinkManService linkManService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("index")
	public String index(Model model, Integer customerId) {
		model.addAttribute("customerId", customerId);
		Customer customer = customerService.findById(customerId);
		model.addAttribute("customer", customer);
		return "linkman";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> list(ContactQuery query) {
		Map<String, Object> result = linkManService.selectForPage(query);
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(LinkMan linkman) {
		linkManService.save(linkman);
		return success("添加成功");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(LinkMan linkman) {
		linkManService.update(linkman);
		return success("修改成功");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(Integer id) {
		linkManService.delete(id);
		return success("删除成功");
	}
	
}
