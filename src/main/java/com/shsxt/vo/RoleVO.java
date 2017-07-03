package com.shsxt.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RoleVO implements Serializable {
	
	private Integer id;
	private String roleName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
