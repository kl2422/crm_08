package com.shsxt.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.CustomerServeQuery;
import com.shsxt.model.CustomerServe;
import com.shsxt.vo.CustomerFw;

/**
 * Created by Tony on 2016/8/24.
 */

@Repository
public interface CustomerServeDao {

	void update(CustomerServe customerServe);

	void insert(CustomerServe customerServe);

	PageList<CustomerServe> selectForPage(CustomerServeQuery query, 
			PageBounds pageBounds);

	List<CustomerFw> findCustomerFw();

}
