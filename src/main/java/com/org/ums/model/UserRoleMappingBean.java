package com.org.ums.model;

import java.util.List;

public class UserRoleMappingBean {
	
	private String userId;
	private List<String> roleList;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	
	

}
