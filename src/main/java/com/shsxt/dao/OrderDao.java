package com.shsxt.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.ContactQuery;
import com.shsxt.model.Order;

public interface OrderDao {
	
	@Select("SELECT t.id, t.cus_id, t.order_no, t.order_date, t.address, " +
			" t.state, t.create_date,t.update_date, t.is_valid FROM t_customer_order t "
			+ "WHERE cus_id = #{customerId} and is_valid = 1")
	PageList<Order> selectForPage(ContactQuery query, PageBounds buildPageBounds);
	
	@Select("SELECT t.id, t.cus_id, t.order_no, t.order_date, t.address, " +
			" t.state, t.create_date,t.update_date, t.is_valid FROM t_customer_order t "
			+ "WHERE id = #{orderId} and is_valid = 1")
	Order findById(@Param(value="orderId")Integer orderId);
	
	@Select("select order_date from t_customer_order where "
			+ " cus_id = #{cusId} order by order_date desc limit 1")
	Date findCustomerOrderDate(@Param(value="cusId")Integer cusId);
	
}
