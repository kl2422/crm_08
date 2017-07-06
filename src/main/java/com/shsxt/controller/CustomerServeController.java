package com.shsxt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseController;
import com.shsxt.dto.CustomerServeQuery;
import com.shsxt.model.CustomerServe;
import com.shsxt.service.CustomerServeService;

/**
 * Created by Tony on 2016/8/25.
 */
@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Autowired
    private CustomerServeService customerServeService;

    @RequestMapping("index/{status}")
    public String index(@PathVariable int status, ModelMap model) {
        model.addAttribute("status", status);
        switch (status) {
            default:
                return "customer_serve_create";
            case 2:
                return "customer_serve_assign";
            case 3:
                return "customer_serve_handle";
            case 4:
                return "customer_serve_feedback";
            case 5:
                return "customer_serve_archive";

        }

    }

    @RequestMapping("list")
    public @ResponseBody
    Map<String, Object> selectForPage(CustomerServeQuery customerServeQuery) {
        return customerServeService.selectForPage(customerServeQuery);
    }

    @RequestMapping("add_update")
    public @ResponseBody Object addOrUpdate(CustomerServe customerServe) {
    	  customerServeService.addOrUpdate(customerServe);
          return success("操作成功");
    }
}
