package com.itheima.domain;

/**
 * 封装客户JavaBean
 * @author wking
 *
 */
public class Customer {
	private Integer id;
	private String c_name;
	private String email;
	private Integer email_status;
	private String password;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getEmail_status() {
		return email_status;
	}
	public void setEmail_status(Integer email_status) {
		this.email_status = email_status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
