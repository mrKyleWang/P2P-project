package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface IUserDao {

	
	//根据用户名和密码查询用户
	User findUserByUsernameAndPassword(String username, String password) throws SQLException;

}
