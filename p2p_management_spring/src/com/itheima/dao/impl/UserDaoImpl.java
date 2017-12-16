package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import com.itheima.utils.JdbcUtils;
@Repository
public class UserDaoImpl implements IUserDao {

	@Override
	public User findUserByUsernameAndPassword(String username, String password) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from user where username =? and password =?";
		User existUser = queryRunner.query(sql, new BeanHandler<User>(User.class), username,password);
		return existUser;
	}

}
