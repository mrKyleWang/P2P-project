package com.itheima.service;

import com.itheima.domain.Account;
import com.itheima.domain.Customer;

public interface IAccountService {

	Account findAccountByCid(Integer c_id);

	String sendMail(String emailAddress);

	void changeStatusById(Integer id);

	Customer findCustomerById(Integer c_id);



}
