package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.utils.JdbcUtils;

public class AccountDaoImpl implements IAccountDao {

	
	@Override
	public void addAccount(Account account) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql="insert into account values(null,?,?,?,?)";
		queryRunner.update(JdbcUtils.getConnection(), sql, account.getTotal(),account.getBalance(),account.getInterest(),account.getC_id());
	}

	@Override
	public Account findAccountByCid(Integer c_id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql="select * from account where c_id = ?";
		Account account = queryRunner.query(sql, new BeanHandler<Account>(Account.class), c_id);
		return account;
	}

	@Override
	public void updateBalance(Integer id, Double newBalance) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql="update account set balance =? where id = ?";
		queryRunner.update(JdbcUtils.getConnection(), sql,newBalance,id);
		
		
	}

}
