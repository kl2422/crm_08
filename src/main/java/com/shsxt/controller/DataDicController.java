package com.shsxt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.base.Constant;
import com.shsxt.dto.DataDicQuery;
import com.shsxt.exception.ParamException;
import com.shsxt.model.DataDic;
import com.shsxt.service.DataDicService;

@Controller
@RequestMapping("datadic")
public class DataDicController extends BaseController {
	
	@Autowired
	private DataDicService dataDicService;
	
	@RequestMapping("index")
	public String index() {
		return "data_dic";
	}
	
	@RequestMapping("dataDicComboList")
	@ResponseBody
	public List<DataDic> dataDicComboList(String dataDicName) {
		List<DataDic> dataDics = dataDicService.findByName(dataDicName);
		return dataDics;
		
	}
	
	@RequestMapping("list")
	public @ResponseBody Map<String, Object> selectForPage(DataDicQuery dataDicQuery) {
		Map<String, Object> result = dataDicService.selectForPage(dataDicQuery);
		return result;
	}

	@RequestMapping("find_all")
	public @ResponseBody List<DataDic> findDataDicName() {
		List<DataDic> result = dataDicService.findAll();
		return result;
	}

	@RequestMapping("add_update")
	public @ResponseBody Object addOrUpdate (DataDic dataDic) {
		try {
			dataDicService.addOrUpdate(dataDic);
			return success(Constant.SUCCESS_MSG);
		} catch (ParamException e) {
			return failure(e.getErrorCode(), e.getMessage());
		}
	}

	@RequestMapping("delete")
	public @ResponseBody Object delete (String ids) {
		try {
			dataDicService.deleteBatch(ids);
			return success(Constant.SUCCESS_MSG);
		} catch (ParamException e) {
			return failure(e.getErrorCode(), e.getMessage());
		}
	}
}
