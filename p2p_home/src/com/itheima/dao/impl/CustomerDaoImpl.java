package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.ICustomerDao;
import com.itheima.domain.Customer;
import com.itheima.utils.JdbcUtils;

public class CustomerDaoImpl implements ICustomerDao {

	@Override
	public void addCustomer(Customer customer) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into customer values(null,?,?,0,?)";
		queryRunner.update(JdbcUtils.getConnection(),sql,customer.getC_name(),customer.getEmail(),customer.getPassword() );
	}

	@Override
	public Customer findByName(String c_name) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from customer where c_name = ?";
		Customer customer = queryRunner.query(sql, new BeanHandler<Customer>(Customer.class),c_name);
		return customer;
	}

	@Override
	public Customer findByEmail(String email) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from customer where email = ?";
		Customer customer = queryRunner.query(sql, new BeanHandler<Customer>(Customer.class),email);
		return customer;
	}

	@Override
	public Customer findByNameAndPassword(String nameOrEmail, String password) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from customer where (c_name = ? or email = ?) and password = ?";
		Customer customer = queryRunner.query(sql, new BeanHandler<Customer>(Customer.class),nameOrEmail,nameOrEmail,password);
		return customer;
	}

	@Override
	public void changeStatusById(Integer id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "update customer set email_status = 1 where id = ?";
		queryRunner.update(JdbcUtils.getConnection(),sql,id);
		
	}

	@Override
	public Customer findById(Integer id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from customer where id = ?";
		Customer customer = queryRunner.query(sql, new BeanHandler<Customer>(Customer.class),id);
		return customer;
	}
	


}
