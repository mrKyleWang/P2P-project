package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.dao.IProductDao;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.IProductService;
@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private IProductDao productDao;

	
	//分页查询
	@Override
	public PageBean pageQuery(Integer currentPage, Integer pageSize) {
		try {
			//查询总数
			Long total = productDao.getTotal();
			//分页查询
			List<Product> list = productDao.pageQuery(currentPage,pageSize);
			PageBean pageBean = new PageBean();
			pageBean.setTotal(total);
			pageBean.setRows(list);
			return pageBean;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void add(Product product) {
		try {
			productDao.add(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Product findById(Integer id) {
		Product product;
		try {
			product = productDao.findById(id);
			return product;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void edit(Product product) {
		try {
		productDao.edit(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<Product> getAll() {
		try {
			return productDao.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
