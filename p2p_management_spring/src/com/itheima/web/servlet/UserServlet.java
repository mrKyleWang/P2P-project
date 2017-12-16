package com.itheima.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.domain.User;
import com.itheima.service.IUserService;

public class UserServlet extends BaseServlet {
	@Autowired
	private IUserService userService;

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User existUser = userService.login(username, password);
		if (null != existUser) {
			request.getSession().setAttribute("user", existUser);
			response.sendRedirect(request.getContextPath()+"/views/home.jsp");
		} else {
			request.setAttribute("loginError", "登录失败");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	//退出
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//销毁session
		request.getSession().invalidate();
	}
}
