package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Product;

public interface IProductDao {

	Long getTotal() throws SQLException;

	void add(Product product) throws SQLException;

	Product findById(Integer id) throws SQLException;

	void edit(Product product) throws SQLException;

	List<Product> pageQuery(Integer currentPage, Integer pageSize) throws SQLException;

	List<Product> getAll() throws SQLException;

}
