package com.itheima.service;

import java.util.List;

import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.domain.ProductAccount;

public interface IProductAccountService {

	boolean purchase(Customer customer, Account account, Integer productId, Double money);

	List<ProductAccount> findAllInfo(Integer cid);

}
