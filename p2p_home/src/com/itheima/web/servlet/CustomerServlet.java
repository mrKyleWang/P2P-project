package com.itheima.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.Customer;
import com.itheima.domain.PageResult;
import com.itheima.service.ICustomerService;
import com.itheima.service.impl.CustomerServiceImpl;

public class CustomerServlet extends BaseServlet {

	private ICustomerService customerService = new CustomerServiceImpl();

	//注册
	protected void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PageResult result = new PageResult();
		try {
			Customer customer = new Customer();
			BeanUtils.populate(customer, request.getParameterMap());
			// 判断用户名和邮箱是否唯一
			Customer byName = customerService.findByName(customer.getC_name());
			if (byName != null) {
				result.setType(0);
				result.setMsg("用户名已被占用,请重新输入!");
				response.getWriter().write(JSONObject.toJSONString(result));
				return;
			}
			Customer byEmail = customerService.findByEmail(customer.getEmail());
			if (byEmail != null) {
				result.setType(0);
				result.setMsg("邮箱已被占用,请重新输入!");
				response.getWriter().write(JSONObject.toJSONString(result));
				return;
			}
			customerService.register(customer);
			result.setType(1);
			result.setMsg("注册成功!");
			response.getWriter().write(JSONObject.toJSONString(result));
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setType(0);
			result.setMsg("注册失败,请稍后重试!");
			response.getWriter().write(JSONObject.toJSONString(result));
			return;
		}
	}

	//检查验证码
	protected void checkCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String checkCode = request.getParameter("checkCode");
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
		PageResult result = new PageResult();
		if (!checkcode_session.equalsIgnoreCase(checkCode)) {
			result.setType(0);
			result.setMsg("验证码输入错误!");
			response.getWriter().write(JSONObject.toJSONString(result));
			return;
		}
		result.setType(1);
		result.setMsg("验证通过");
		response.getWriter().write(JSONObject.toJSONString(result));
		return;
	}

	//登录
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nameOrEmail = request.getParameter("nameOrEmail");
		String password = request.getParameter("password");
		Customer customer = customerService.login(nameOrEmail, password);
		PageResult result = new PageResult();
		if (customer != null) {
			request.getSession().setAttribute("customer", customer);
			result.setType(1);
			result.setMsg("登录成功!");
			//设置记住用户名的cookie
			Integer isRemenber = Integer.parseInt(request.getParameter("isRemenber"));
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("remenberName")) {
					if (isRemenber == 1) {
						Cookie newCookie = new Cookie("remenberName",nameOrEmail);
						cookie.setPath("/p2p_home");
						cookie.setMaxAge(60*60);
						response.addCookie(newCookie);
					} else {
						cookie.setMaxAge(0);
					}
				}
			}
			response.getWriter().write(JSONObject.toJSONString(result));
			return;
		} else {
			result.setType(0);
			result.setMsg("账号或密码错误,请重新输入!");
			response.getWriter().write(JSONObject.toJSONString(result));
			return;
		}

	}
	
	//记住用户名
	protected void showRemenber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		String loginName ="";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("remenberName")) {
				loginName = cookie.getValue();
			}
		}
		response.getWriter().write(loginName);
		return;
	}
	
}
