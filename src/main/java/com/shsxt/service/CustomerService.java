package com.shsxt.service;

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
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.Customer;
import com.shsxt.util.DateUtil;
import com.shsxt.vo.CustomerVO;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	public List<CustomerVO> findAll() {
		return customerDao.findAll();
	}
	
	/**
	 * 分页查询
	 * @param query
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
}
