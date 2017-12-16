package com.itheima.service.impl;

import java.sql.SQLException;
import com.itheima.dao.IAccountDao;
import com.itheima.dao.ICustomerDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.dao.impl.CustomerDaoImpl;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.service.ICustomerService;
import com.itheima.utils.JdbcUtils;

public class CustomerServiceImpl implements ICustomerService {

	private ICustomerDao customerDao = new CustomerDaoImpl();
	
	private IAccountDao accountDao = new AccountDaoImpl();
	
	@Override
	public void register(Customer customer) {
		try {
			JdbcUtils.startTransaction();
			//添加用户
			customerDao.addCustomer(customer);
			Customer cus = customerDao.findByName(customer.getC_name());
			//添加账户
			Account account = new Account();
			account.setC_id(cus.getId());
			accountDao.addAccount(account);
		} catch (SQLException e) {
			
			try {
				JdbcUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				JdbcUtils.commit();
				JdbcUtils.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Customer findByName(String c_name) {
		try {
			return customerDao.findByName(c_name);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Customer findByEmail(String email) {
		try {
			return customerDao.findByEmail(email);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Customer login(String nameOrEmail, String password) {
		try {
			return customerDao.findByNameAndPassword(nameOrEmail,password);
		} catch (SQLException e) {
			return null;
		}
	}

}
