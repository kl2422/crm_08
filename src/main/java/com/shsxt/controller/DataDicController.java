package com.shsxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.model.DataDic;
import com.shsxt.service.DataDicService;

@Controller
@RequestMapping("datadic")
public class DataDicController extends BaseController {
	
	@Autowired
	private DataDicService dataDicService;
	
	@RequestMapping("dataDicComboList")
	@ResponseBody
	public List<DataDic> dataDicComboList(String dataDicName) {
		List<DataDic> dataDics = dataDicService.findByName(dataDicName);
		return dataDics;
		
	}
}
