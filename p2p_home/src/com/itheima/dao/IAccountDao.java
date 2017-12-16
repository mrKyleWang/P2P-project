package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.Account;

public interface IAccountDao {

	void addAccount(Account account) throws SQLException;

	Account findAccountByCid(Integer c_id) throws SQLException;

	void updateBalance(Integer id, Double newBalance) throws SQLException;

}
