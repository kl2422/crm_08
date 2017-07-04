package com.shsxt.model;

import com.shsxt.base.BaseModel;

@SuppressWarnings("serial")
public class Permission extends BaseModel {
	
	private Integer roleId;
	private Integer moduleId;
	private String aclValue;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getAclValue() {
		return aclValue;
	}
	public void setAclValue(String aclValue) {
		this.aclValue = aclValue;
	}
}
