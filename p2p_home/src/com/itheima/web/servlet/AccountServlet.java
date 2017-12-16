package com.itheima.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.domain.PageResult;
import com.itheima.service.IAccountService;
import com.itheima.service.impl.AccountServiceImpl;

/**
 * Servlet implementation class AccountServlet
 */
public class AccountServlet extends BaseServlet {

	private IAccountService accountService = new AccountServiceImpl();


	protected void findCustomerAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		if (customer != null) {
			Integer c_id = customer.getId();
			Account account = accountService.findAccountByCid(c_id);
			Customer currentCustomer = accountService.findCustomerById(c_id);
			account.setC(currentCustomer);
			String jsonString = JSONObject.toJSONString(account);
			response.getWriter().write(jsonString);
			return;
		} else {
			PageResult result = new PageResult();
			result.setType(0);
			result.setMsg("用户未登录!");
			String jsonString = JSONObject.toJSONString(result);
			response.getWriter().write(jsonString);
			return;
		}
	}

	protected void sendMail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String emailAddress = request.getParameter("emailAddress");

		String emailCheckCode = accountService.sendMail(emailAddress);
		PageResult result = new PageResult();
		if (emailCheckCode != null) {
			request.getSession().setAttribute("emailCheckCode", emailCheckCode);
			result.setType(1);
			result.setMsg("邮件发送成功,请查收!");
			String jsonString = JSONObject.toJSONString(result);
			response.getWriter().write(jsonString);
			return;
		} else {
			result.setType(0);
			result.setMsg("邮件发送失败!");
			String jsonString = JSONObject.toJSONString(result);
			response.getWriter().write(jsonString);
			return;
		}
	}

	protected void checkMail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String emailCode = request.getParameter("emailCode");
		String emailCheckCode = (String) request.getSession().getAttribute("emailCheckCode");
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		PageResult result = new PageResult();
		if(emailCode.equals(emailCheckCode)) {
			request.getSession().removeAttribute("emailCheckCode");
			accountService.changeStatusById(customer.getId());
			
			result.setType(1);
			result.setMsg("认证成功!");
			String jsonString = JSONObject.toJSONString(result);
			response.getWriter().write(jsonString);
			return;
		}else {
			result.setType(0);
			result.setMsg("认证失败!");
			String jsonString = JSONObject.toJSONString(result);
			response.getWriter().write(jsonString);
			return;
		}
	}

}
