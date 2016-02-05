package com.oil.utd.util;

public class Login {
	
  private String userID;
  private String pwd;
  private int role_type;
  
  
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userid) {
		this.userID = userid;
	}
	public String getPassword() {
		return pwd;
	}
	public void setPassword(String password) {
		this.pwd = password;
	}
	public int getRoleType() {
		return role_type;
	}
	public void setRoleType(int string) {
		this.role_type = string;
	}
	@Override
	public String toString() {
		return "Login [userid=" + userID + ", password=" + pwd
				+ ", role=" + role_type + "]";
	}
  
  
}