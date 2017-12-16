package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.Customer;

public interface ICustomerDao {

	void addCustomer(Customer customer) throws SQLException;

	Customer findByName(String c_name) throws SQLException;

	Customer findByEmail(String email) throws SQLException;

	Customer findByNameAndPassword(String nameOrEmail, String password) throws SQLException;

	void changeStatusById(Integer id) throws SQLException;

	Customer findById(Integer id) throws SQLException;


}
