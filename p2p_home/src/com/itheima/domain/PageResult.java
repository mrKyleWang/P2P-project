package com.itheima.domain;

/**
 * 封装ajax请求结果
 * @author wking
 *
 */
public class PageResult {
	private Integer type;
	private String msg;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
