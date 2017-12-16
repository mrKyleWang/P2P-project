package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.Random;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.ICustomerDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.dao.impl.CustomerDaoImpl;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.service.IAccountService;
import com.itheima.utils.MailUtils;

public class AccountServiceImpl implements IAccountService {

	private IAccountDao accountDao = new AccountDaoImpl();
	private ICustomerDao customerDao = new CustomerDaoImpl();
	
	@Override
	public Account findAccountByCid(Integer c_id) {
		try {
			return accountDao.findAccountByCid(c_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String sendMail(String emailAddress) {
		try {
			Random random = new Random();
			String randomCode = "";
			for(int i=0;i<6;i++) {
				Integer randomNum = random.nextInt(10);
				randomCode+= randomNum;
			}
			MailUtils.sendMail(emailAddress, "邮箱认证验证码 : "+randomCode);
			return randomCode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	@Override
	public void changeStatusById(Integer id) {
		try {
			customerDao.changeStatusById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Customer findCustomerById(Integer id) {
		try {
			return customerDao.findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


}
