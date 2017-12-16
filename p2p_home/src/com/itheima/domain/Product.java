package com.itheima.domain;

import java.util.Date;

/**
 * 封装产品javaBean
 * @author wking
 *
 */
public class Product {
	
	private Integer id;
	private String proNum;		//编号
	private String proName;		//名称
	private Integer proLimit;	//期限
	private Double annualized;	//年化利率 
	private Date releaseData;	//
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProNum() {
		return proNum;
	}
	public void setProNum(String proNum) {
		this.proNum = proNum;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Integer getProLimit() {
		return proLimit;
	}
	public void setProLimit(Integer proLimit) {
		this.proLimit = proLimit;
	}
	public Double getAnnualized() {
		return annualized;
	}
	public void setAnnualized(Double annualized) {
		this.annualized = annualized;
	}
	public Date getReleaseData() {
		return releaseData;
	}
	public void setReleaseData(Date releaseData) {
		this.releaseData = releaseData;
	}
	
	

}
