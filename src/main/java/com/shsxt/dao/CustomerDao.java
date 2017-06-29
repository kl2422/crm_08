package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.shsxt.vo.CustomerVO;

public interface CustomerDao {
	
	@Select("select id, name from t_customer where is_valid = 1 and state = 0")
	@ResultType(value = CustomerVO.class)
	List<CustomerVO> findAll();

}
