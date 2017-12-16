package com.itheima.service.impl;

import com.itheima.dao.IUserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.IUserService;
/**
 * 用户Service
 * @author wking
 *
 */
public class UserServiceImpl implements IUserService {
	
	private IUserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		try {
			User existUser = userDao.findUserByUsernameAndPassword(username,password);
			return existUser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
