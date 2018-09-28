package com.org.ums.model;

import java.util.List;

public class UserRoleMappingBean {
	
	private String emailId;
	private boolean active;
	private List<String> roleList;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
