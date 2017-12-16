package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.domain.PageResult;
import com.itheima.domain.ProductAccount;
import com.itheima.service.IAccountService;
import com.itheima.service.IProductAccountService;
import com.itheima.service.impl.AccountServiceImpl;
import com.itheima.service.impl.ProductAccountServiceImpl;

/**
 * Servlet implementation class ProductAccountServlet
 */
public class ProductAccountServlet extends BaseServlet {

	private IProductAccountService productAccountService = new ProductAccountServiceImpl();
	private IAccountService accountService = new AccountServiceImpl();


	// 购买商品
	protected void purchase(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PageResult result = new PageResult();
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		Double money = Double.parseDouble(request.getParameter("money"));
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		if(customer!=null) {
			Account account = accountService.findAccountByCid(customer.getId());
			// 检查余额是否充足
			try {
				if (account.getBalance() < money) {
					// 余额不足
					result.setType(0);
					result.setMsg("余额不足,请充值后重新操作!");
					String jsonString = JSONObject.toJSONString(result);
					response.getWriter().write(jsonString);
					return;
				} else {
					if(productAccountService.purchase(customer, account, productId, money)) {
						result.setType(1);
						result.setMsg("购买成功!");
						String jsonString = JSONObject.toJSONString(result);
						response.getWriter().write(jsonString);
						return;
					}else {
						result.setType(0);
						result.setMsg("购买失败,请稍后重试!");
						String jsonString = JSONObject.toJSONString(result);
						response.getWriter().write(jsonString);
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setType(0);
				result.setMsg("购买失败,请稍后重试!");
				String jsonString = JSONObject.toJSONString(result);
				response.getWriter().write(jsonString);
				return;
			}
		}else {
			result.setType(-1);
			result.setMsg("用户未登录,请登录!");
			String jsonString = JSONObject.toJSONString(result);
			response.getWriter().write(jsonString);
			return;
		}
	}
	
	//查询投资列表
	protected void findAllInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*<td id="pa_num">投资编号</td>
		<td id="proName">产品名称</td>
		<td id="proLimit">期限</td>
		<td id="annualized">年化利率</td>
		<td id="pa_money">本金</td>
		<td id="pa_interest">预期收益</td>
		<td id="pa_date">投资时间</td>
		<td id="isMature">是否到期</td>*/
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		if(customer!=null) {
			Integer cid = customer.getId();
			List<ProductAccount> list = productAccountService.findAllInfo(cid);
			String jsonString = JSONObject.toJSONString(list);
			response.getWriter().write(jsonString);
			return;
		}else {
			PageResult result = new PageResult();
			result.setType(-1);
			result.setMsg("用户未登录,请登录!");
			String jsonString = JSONObject.toJSONString(result);
			response.getWriter().write(jsonString);
			return;
		}
	}
}
