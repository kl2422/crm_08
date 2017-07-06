package com.shsxt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.dao.CustomerDao;
import com.shsxt.dto.ReportGxQuery;
import com.shsxt.vo.CustomerGc;
import com.shsxt.vo.CustomerGx;

@Service
public class ReportSerivce {
	
	@Autowired
	private CustomerDao customerDao;

	public Map<String, Object> findKhgx(ReportGxQuery query) {
		
		PageList<CustomerGx> customerGxs = customerDao.khgxReport(query, query.buildPageBounds());
		Paginator paginator = customerGxs.getPaginator(); // 分页对象
		Map<String, Object> result = new HashMap<>();
		result.put("paginator", paginator);
		result.put("rows", customerGxs);
		result.put("total", paginator.getTotalCount());
		return result;
	}

	public List<CustomerGc> findKhgc() {
		return customerDao.khgcReport();
	}

}
