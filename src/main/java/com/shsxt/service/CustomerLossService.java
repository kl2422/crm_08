package com.shsxt.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.base.AssertUtil;
import com.shsxt.dao.CustomerDao;
import com.shsxt.dao.CustomerLossDao;
import com.shsxt.dto.CustomerLossQuery;
import com.shsxt.model.CustomerLoss;

@Service
public class CustomerLossService {

	@Autowired
	private CustomerLossDao customerLossDao;
	@Autowired
	private CustomerDao customerDao;

	public Map<String, Object> selectForPage(CustomerLossQuery 
			query) {

		PageList<CustomerLoss> customerLosses = customerLossDao.selectForPage
				(query, query.buildPageBounds());
		Paginator paginator = customerLosses.getPaginator(); // 分页对象
		Map<String, Object> result = new HashMap<>();
		result.put("paginator", paginator);
		result.put("rows", customerLosses);
		result.put("total", paginator.getTotalCount());
		return result;

	}

	public void confirmLoss(Integer lossId, String lossReason) {
		// 更新流失原因
		AssertUtil.isTrue(lossId == null || lossId < 1, "请选择记录");
		AssertUtil.isTrue(StringUtils.isEmpty(lossReason), "请输入流失原因");
		CustomerLoss customerLoss = customerLossDao.loadById(lossId);
		AssertUtil.notNull(customerLoss, "该客户并没有流失， 请确定");
		customerLoss.setLossReason(lossReason);
		customerLoss.setState(1);
		customerLossDao.update(customerLoss);
		// 更新t_customer的状态为 1:已流失
		customerDao.updateLossState(customerLoss.getCusNo());
	}

	public CustomerLoss loadById(Integer id) {
		AssertUtil.isTrue(id == null || id < 1, "请选择记录");
		return customerLossDao.loadById(id);
	}
}
