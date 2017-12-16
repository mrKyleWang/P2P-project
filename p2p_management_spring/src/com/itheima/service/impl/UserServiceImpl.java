package com.itheima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.dao.IUserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.IUserService;
/**
 * 用户Service
 * @author wking
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
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
