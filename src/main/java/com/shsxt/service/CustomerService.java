package com.shsxt.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.base.AssertUtil;
import com.shsxt.dao.CustomerDao;
import com.shsxt.dao.CustomerLossDao;
import com.shsxt.dao.OrderDao;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.Customer;
import com.shsxt.model.CustomerLoss;
import com.shsxt.util.DateUtil;
import com.shsxt.vo.CustomerVO;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerLossDao customerLossDao;
	@Autowired
	private OrderDao orderDao;
	
	public List<CustomerVO> findAll() {
		return customerDao.findAll();
	}
	
	/**
	 * 分页查询
	 * @param querya
	 * @return
	 */
	public Map<String, Object> selectForPage(CustomerQuery query) {
		
		PageList<Customer> customers = customerDao.selectForPage(query, query.buildPageBounds());
		
		Paginator paginator = customers.getPaginator(); // 分页对象
		Map<String, Object> result = new HashMap<>();
		result.put("paginator", paginator);
		result.put("rows", customers);
		result.put("total", paginator.getTotalCount());
		return result;
	}

	public void add(Customer customer) {
		// 基本参数验证
		// TODO
		// 验证客户名称是否存在
		Customer customerByName = customerDao.findByName(customer.getName().trim());
		AssertUtil.isTrue(customerByName != null, "该客户已存在");
		customer.setCreateDate(new Date());
		customer.setUpdateDate(new Date());
		customer.setIsValid(1);
		customer.setState(0);
		// 生成一个客户编号：KH + 日期时间 + 随机数
		String customerNo = "KH" + DateUtil.formatOtherDatetime(new Date()) 
							+ RandomUtils.nextInt(100, 999);
		customer.setKhno(customerNo);
		// 插入数据库
		customerDao.insert(customer);
	}

	public void update(Customer customer) {
		AssertUtil.intIsNotEmpty(customer.getId(), "请选择一条记录进行更新");
		// 基本参数验证 TODO
		Customer customerFromDb = customerDao.findById(customer.getId());
		AssertUtil.notNull(customerFromDb, "该记录不存在，请确认后更新");
		// 客户名称判断
		if (!customer.getName().equals(customerFromDb.getName())) {
			// 需要验证客户名称唯一
			Customer customerByName = customerDao.findByName(customer.getName().trim());
			AssertUtil.isTrue(customerByName != null, "该客户已存在");
		}
		customer.setUpdateDate(new Date());
		customerDao.update(customer);
		
	}

	public void delete(String ids) {
		AssertUtil.isNotEmpty(ids, "请选择记录进行删除");
		customerDao.delete(ids);
	}
	
	/**
	 * 根据ID查询
	 * @param customerId
	 * @return
	 */
	public Customer findById(Integer customerId) {
		AssertUtil.intIsNotEmpty(customerId, "请选择客户信息");
		Customer customer = customerDao.findById(customerId);
		return customer;
	}
	
	/**
	 * 定时抓取流失客户
	 */
	public void runCustomerLoss() {
		
		// 六个月没有下个单的客户
		List<CustomerLoss> customerLosses = new ArrayList<>();
		List<Customer> customers = customerDao.findLossCustomer();
		List<Integer> customerIds = buildCustomerLoss(customers, customerLosses, 0);
		
		// 六个月前可能下过单
		List<Customer> customerNoOrderLongTimes = customerDao.findLossCustomerNoOrderLongTime();
		List<Integer> customerMoreIds = buildCustomerLoss(customerNoOrderLongTimes, customerLosses, 1);
		customerIds.addAll(customerMoreIds);
		
		// 插入数据
		if (customerLosses != null && !customerLosses.isEmpty()) {
			customerLossDao.insertBatch(customerLosses);
		}
		
		// 更新客户的状态 update t_customer set state = 2 where id in (1,2,3)
		if (customerIds != null && !customerIds.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (Integer customerId : customerMoreIds) {
				sb.append(customerId).append(",");
			}
			customerDao.updateStates(sb.substring(0, sb.length() - 1));
		}
		
	}
	
	/**
	 * 构建
	 * @param customers
	 * @param customerLosses
	 */
	private List<Integer> buildCustomerLoss(List<Customer> customers, 
			List<CustomerLoss> customerLosses, int type) {
		if (customers == null || customers.isEmpty()) {
			return Collections.emptyList();
		}
		List<Integer> customerIds = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerLoss customerLoss = new CustomerLoss();
			customerLoss.setCreateDate(new Date());
			customerLoss.setCusManager(customer.getCusManager());
			customerLoss.setCusName(customer.getName());
			customerLoss.setCusNo(customer.getKhno());
			customerLoss.setIsValid(1);
			if (type == 1) { // 需要查询最后一次订单时间
				Date lastOrderTime = orderDao.findCustomerOrderDate(customer.getId());
				customerLoss.setLastOrderTime(lastOrderTime);
			}
			
			customerLosses.add(customerLoss);
			customerIds.add(customer.getId());
		}
		return customerIds;
	}
}
