package com.shsxt.dao;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.dto.ReportGxQuery;
import com.shsxt.model.Customer;
import com.shsxt.vo.CustomerGc;
import com.shsxt.vo.CustomerGx;
import com.shsxt.vo.CustomerVO;
import org.springframework.stereotype.Repository;

public interface CustomerDao {
	
	@Select("select id, name from t_customer where is_valid = 1 and state in (0, 2)")
	@ResultType(value = CustomerVO.class)
	List<CustomerVO> findAll();

	PageList<Customer> selectForPage(CustomerQuery query, PageBounds pageBounds);
	
	@Select("select id, name from t_customer where is_valid = 1 and name = #{name}")
	Customer findByName(@Param(value="name") String name);
	
	void insert(Customer customer);
	
	@Select("select id, name, khno from t_customer where is_valid = 1 and id = #{id}")
	Customer findById(@Param(value="id")Integer id);
	
	void update(Customer customer);
	
	@Update("update t_customer set is_valid = 0, update_date = now() where id in (${ids})")
	void delete(@Param(value="ids")String ids);
	
	
	List<Customer> findLossCustomer();
	
	List<Customer> findLossCustomerNoOrderLongTime();
	
	@Update("update t_customer set state = 2, update_date = now() where id in (${ids})")
	void updateStates(@Param(value="ids")String ids);
	
	@Update("update t_customer set state = 1, update_date = now() where khno = #{cusNo}")
	void updateLossState(@Param(value="cusNo")String cusNo);
	
	PageList<CustomerGx> khgxReport(ReportGxQuery query, PageBounds pageBounds);

	List<CustomerGc> khgcReport();

}
