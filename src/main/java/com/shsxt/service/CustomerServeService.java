package com.shsxt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dao.CustomerServeDao;
import com.shsxt.dto.CustomerServeQuery;
import com.shsxt.model.CustomerServe;

/**
 * Created by Tony on 2016/8/24.
 */
@Service
public class CustomerServeService {

    @Autowired
    private CustomerServeDao customerServeDao;

    /**
     * 添加或者修改
     * @param customerServe
     */
    public void addOrUpdate(CustomerServe customerServe) {
        if (customerServe.getId() != null) {
            if ("已分配".equals(customerServe.getState())) {
                customerServe.setAssignTime(new Date());
            } else if ("已处理".equals(customerServe.getState())) {
                customerServe.setServiceProceTime(new Date());
            }
            customerServeDao.update(customerServe);
        } else {
        	customerServeDao.insert(customerServe);
        }
    }

	public Map<String, Object> selectForPage(CustomerServeQuery query) {
		 PageList<CustomerServe> pageDatas = customerServeDao.selectForPage(query, 
				 query.buildPageBounds());
        Map<String, Object> result = new HashMap<>();
        result.put("rows", pageDatas);
        result.put("total", pageDatas.getPaginator().getTotalCount());
        return result;
	}
}