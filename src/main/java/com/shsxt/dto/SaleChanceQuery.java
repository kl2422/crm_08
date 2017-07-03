package com.shsxt.dto;

import com.shsxt.base.BaseQuery;

public class SaleChanceQuery extends BaseQuery {
	
//	private Integer page;
//	private Integer rows; // 获取前端传入的分页多少条的参数名称
	private String customerName;
	private String overview; 
	private String createMan;
	private Integer state; // 0=未分配 1=已分配
	
	private Integer devResult; // 开发状态 0=未开发 1=开发中 2=开发完成 3=开发失败
	
	
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
	public Integer getDevResult() {
		return devResult;
	}
	public void setDevResult(Integer devResult) {
		this.devResult = devResult;
	}
	
}
