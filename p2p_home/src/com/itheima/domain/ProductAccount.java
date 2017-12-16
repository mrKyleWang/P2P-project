package com.itheima.domain;

import java.util.Date;

/**
 * 封装商品账户对应 JavaBean
 * @author wking
 *
 */
public class ProductAccount {
	private Integer id;
	private String pa_num;
	private Date pa_date;
	private Customer c;
	private Product p;
	private Double money;
	private Double interest;
	private Integer status = 0;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPa_num() {
		return pa_num;
	}
	public void setPa_num(String pa_num) {
		this.pa_num = pa_num;
	}
	public Date getPa_date() {
		return pa_date;
	}
	public void setPa_date(Date pa_date) {
		this.pa_date = pa_date;
	}
	public Customer getC() {
		return c;
	}
	public void setC(Customer c) {
		this.c = c;
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
