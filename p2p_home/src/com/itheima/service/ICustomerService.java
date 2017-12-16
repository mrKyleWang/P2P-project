package com.itheima.service;

import com.itheima.domain.Customer;

public interface ICustomerService {

	void register(Customer customer);

	Customer findByName(String c_name);

	Customer findByEmail(String email);

	Customer login(String nameOrEmail, String password);

}
