package com.shsxt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shsxt.base.Constant;
import com.shsxt.base.ResultInfo;
import com.shsxt.dto.SaleChanceQuery;
import com.shsxt.exception.ParamException;
import com.shsxt.model.SaleChance;
import com.shsxt.service.SaleChanceService;
import com.shsxt.util.CookieUtil;

@RequestMapping("sale_chance")
@Controller
public class SaleChanceController extends BaseController {
	
	@Autowired
	private SaleChanceService saleChanceService;
	
	@RequestMapping("index")
	public String index () {
		return "sale_chance";
	}
	
	@RequestMapping("branch")
	public String branch () {
		return "branch";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object>selectForPage(SaleChanceQuery query) {
		Map<String, Object> result = saleChanceService.selectForPage(query);
		return result;
	}
	
	@RequestMapping("add_update")
	@ResponseBody
	public ResultInfo addOrUpdate(SaleChance saleChance, HttpServletRequest request) {
//		Map<String, Object> map = new HashMap<>();
//		ResultInfo resultInfo = new ResultInfo();
		String userName = CookieUtil.getCookieValue(request, "userName");
		try {
			saleChanceService.addOrUpdate(saleChance, userName);
//			resultInfo.setResult("操作成功");
//			resultInfo.setResultCode(Constant.SUCCESS_CODE);
//			resultInfo.setResultMessage("操作成功");
			return success(Constant.SUCCESS_MSG);
		} catch (ParamException e) {
//			resultInfo.setResultCode(e.getErrorCode());
//			resultInfo.setResult(e.getMessage());
//			resultInfo.setResultMessage(e.getMessage());
			return failure(e);
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(String ids) {
		Map<String, Object> map = new HashMap<>();
		try {
			saleChanceService.delete(ids);
			map.put("resultCode", 1);
			map.put("resultMessage", "删除成功");
			map.put("result", "删除成功");
		} catch (ParamException e) {
			map.put("resultCode", e.getErrorCode());
			map.put("resultMessage", e.getMessage());
			map.put("result", e.getMessage());
		}
		
		return map;
		
	}
	
}
