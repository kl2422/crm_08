package com.shsxt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.base.ResultInfo;
import com.shsxt.dto.ReportGxQuery;
import com.shsxt.service.ReportSerivce;
import com.shsxt.vo.CustomerGc;

@RequestMapping("report")
@Controller
public class ReportController extends BaseController {
	
	@Autowired
	private ReportSerivce reportService;

	@RequestMapping("{type}")
	public String index(@PathVariable Integer type) {
		switch (type) {
		default:
			return "khgxfx";
		case 1:
			return "khgcfx";
		case 2:
			return "khfwfx";
		case 3:
			return "khlsfx";
		}
	}
	
	@RequestMapping("khgxfx")
	@ResponseBody
	public Map<String, Object> khgxfx(ReportGxQuery query) {
		Map<String, Object> result = reportService.findKhgx(query);
		return result;
	}
	
	@RequestMapping("khgcfx")
	@ResponseBody
	public List<CustomerGc> khgcfx() {
		List<CustomerGc> result = reportService.findKhgc();
		return result;
	}
	
	
}
