package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.itheima.dao.IProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.JdbcUtils;
@Repository
public class ProductDaoImpl implements IProductDao {

	@Override
	public Long getTotal() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select count(*) from product";
		Object[] result = queryRunner.query(sql, new ArrayHandler());
		Long total = (Long) result[0];
		return total;
	}

	@Override
	public List<Product> pageQuery(Integer currentPage, Integer pageSize) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from product limit ?,?";
		Integer start = (currentPage-1)*pageSize;
		List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class),start,pageSize);
		return list;
	}

	@Override
	public void add(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "insert into product values(null,?,?,?,?,null)";
		queryRunner.update(sql, product.getProNum(),product.getProName(),product.getProLimit(),product.getAnnualized());
	}

	@Override
	public Product findById(Integer id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from product where id = ?";
		Product product = queryRunner.query(sql,new BeanHandler<Product>(Product.class),id);
		return product;
	}

	@Override
	public void edit(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "update product set proNum =?,proName=?,proLimit=?,annualized=? where id=?";
		queryRunner.update(sql, product.getProNum(),product.getProName(),product.getProLimit(),product.getAnnualized(),product.getId());
		
	}

	@Override
	public List<Product> getAll() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from product";
		List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
		return list;
	}


}
