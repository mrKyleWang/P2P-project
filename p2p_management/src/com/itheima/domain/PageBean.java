package com.itheima.domain;

import java.util.List;

/**
 * 封装分页查询JavaBean
 * @author wking
 *
 */
public class PageBean {
	private Long total;
	private List rows;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}

}
