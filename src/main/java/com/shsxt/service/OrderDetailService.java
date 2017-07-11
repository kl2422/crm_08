package com.shsxt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.base.AssertUtil;
import com.shsxt.base.BaseQuery;
import com.shsxt.dao.OrderDetailDao;
import com.shsxt.model.OrderDetails;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailDao orderDetailDao;

	public Integer getTotalPrice(Integer orderId) {
		AssertUtil.intIsNotEmpty(orderId, "请选择订单");
		Integer result = orderDetailDao.countTotal(orderId);
		return result;
	}
	
	public Map<String, Object> selectForPage(Integer orderId, BaseQuery query) {
		
		AssertUtil.intIsNotEmpty(orderId, "请选择订单");
		PageList<OrderDetails> orderDetails = orderDetailDao.selectForPage(orderId, query.buildPageBounds());
		Paginator paginator = orderDetails.getPaginator(); // 分页对象
		Map<String, Object> result = new HashMap<>();
		result.put("rows", orderDetails);
		result.put("total", paginator.getTotalCount());
		return result;
		
	}

}
