package com.shsxt.dao;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.CustomerServeQuery;
import com.shsxt.model.CustomerServe;

/**
 * Created by Tony on 2016/8/24.
 */

@Repository
public interface CustomerServeDao {

	void update(CustomerServe customerServe);

	void insert(CustomerServe customerServe);

	PageList<CustomerServe> selectForPage(CustomerServeQuery query, 
			PageBounds pageBounds);

}
