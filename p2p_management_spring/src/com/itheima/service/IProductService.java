package com.itheima.service;

import java.util.List;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

public interface IProductService {

	//商品分页查询
	PageBean pageQuery(Integer currentPage, Integer pageSize);

	void add(Product product);

	Product findById(Integer id);

	void edit(Product product);

	List<Product> getAll();

}
