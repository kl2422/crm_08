package com.shsxt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.dao.CustomerDao;
import com.shsxt.vo.CustomerVO;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	public List<CustomerVO> findAll() {
		return customerDao.findAll();
	}

}
