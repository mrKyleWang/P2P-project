package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.ProductAccount;

public interface IProductAccountDao {

	void addProductAccount(ProductAccount productAccount) throws SQLException;

	List<Object[]> findAllInfo(Integer cid) throws SQLException;

}
