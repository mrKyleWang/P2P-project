package com.itheima.service;

import com.itheima.domain.User;

public interface IUserService {

	//根据用户名和密码查询用户
	User login(String username, String password);

}
