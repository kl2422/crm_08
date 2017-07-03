package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.Customer;
import com.shsxt.vo.CustomerVO;

public interface CustomerDao {
	
	@Select("select id, name from t_customer where is_valid = 1 and state = 0")
	@ResultType(value = CustomerVO.class)
	List<CustomerVO> findAll();

	PageList<Customer> selectForPage(CustomerQuery query, PageBounds pageBounds);
	
	@Select("select id, name from t_customer where is_valid = 1 and name = #{name}")
	Customer findByName(@Param(value="name") String name);
	
	void insert(Customer customer);
	
	@Select("select id, name, khno from t_customer where is_valid = 1 and id = #{id}")
	Customer findById(@Param(value="id")Integer id);
	
	void update(Customer customer);
	
	@Update("update t_customer set is_valid = 0 where id in (${ids})")
	void delete(@Param(value="ids")String ids);

}
