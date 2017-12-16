package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.IProductService;
import com.mysql.jdbc.StringUtils;

public class ProductServlet extends BaseServlet {

	@Autowired
	private IProductService productService;

	protected void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String callback = request.getParameter("callback");
		if(StringUtils.isNullOrEmpty(callback)) {
			List<Product> list = productService.getAll();
			String jsonString = JSONObject.toJSONString(list);
			response.getWriter().write(jsonString);
			
		}else {
			List<Product> list = productService.getAll();
			String jsonString = JSONObject.toJSONString(list);
			response.getWriter().write(callback+"("+jsonString+")");
		}
	}
	
	// 商品分页查询
	protected void pageQuery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageBean pageBean = productService.pageQuery(currentPage, pageSize);
		String jsonString = JSONObject.toJSONString(pageBean);
		response.getWriter().write(jsonString);
	}

	// 商品添加
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Product product = new Product();
			BeanUtils.populate(product,request.getParameterMap());
			productService.add(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	protected void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("productId"));
		String callback = request.getParameter("callback");
		if(StringUtils.isNullOrEmpty(callback)) {
			Product product = productService.findById(id);
			String jsonString = JSONObject.toJSONString(product);
			response.getWriter().write(jsonString);
		}else {
			Product product = productService.findById(id);
			String jsonString = JSONObject.toJSONString(product);
			response.getWriter().write(callback+"("+jsonString+")");
		}
	}
	
	
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Product product = new Product();
			BeanUtils.populate(product,request.getParameterMap());
			productService.edit(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
