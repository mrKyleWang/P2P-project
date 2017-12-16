package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.itheima.dao.IProductAccountDao;
import com.itheima.domain.ProductAccount;
import com.itheima.utils.JdbcUtils;

public class ProductAccountDaoImpl implements IProductAccountDao {

	@Override
	public void addProductAccount(ProductAccount productAccount) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into product_account values(null,?,null,?,?,?,?)";
		queryRunner.update(JdbcUtils.getConnection(),sql,productAccount.getPa_num(),productAccount.getC().getId(),productAccount.getP().getId(),productAccount.getMoney(),productAccount.getInterest());
		
	}

	@Override
	public List<Object[]> findAllInfo(Integer cid) throws SQLException {
		//pa表
		//pa_num投资编号
		//pa_date投资时间
		//pa_money本金
		//pa_interest预期收益
		
		//product表
		//proName">产品名称
		//proLimit">期限
		//annualized">年化利率
		QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select pa.pa_num,pa.pa_date,pa.money,pa.interest,p.proName,p.proLimit,p.annualized from product p,product_account pa where pa.c_id = ? and p.id = pa.p_id";
		List<Object[]> list = queryRunner.query(sql, new ArrayListHandler(),cid);
		return list;
	}
}
