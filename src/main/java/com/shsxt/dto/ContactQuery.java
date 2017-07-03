package com.shsxt.dto;

import com.shsxt.base.BaseQuery;

public class ContactQuery extends BaseQuery {
	
	private Integer customerId; // 编号

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
