package com.shsxt.dto;

import com.shsxt.base.BaseQuery;

public class SaleChanceQuery extends BaseQuery {
	
//	private Integer page;
//	private Integer rows; // 获取前端传入的分页多少条的参数名称
	private String customerName;
	private String overview; 
	private String createMan;
	private Integer state;
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getCreateMan() {
		return createMan;
	}
	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
