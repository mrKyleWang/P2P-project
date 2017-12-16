package com.itheima.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.IProductAccountDao;
import com.itheima.dao.IProductDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.dao.impl.ProductAccountDaoImpl;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.domain.Product;
import com.itheima.domain.ProductAccount;
import com.itheima.service.IProductAccountService;
import com.itheima.utils.JdbcUtils;

public class ProductAccountServiceImpl implements IProductAccountService {
	
	private IAccountDao accountDao = new AccountDaoImpl();
	private IProductDao productDao = new ProductDaoImpl();
	private IProductAccountDao productAccountDao = new ProductAccountDaoImpl();

	@Override
	public boolean purchase(Customer customer, Account account, Integer productId, Double money) {
		
		//计算余额
		BigDecimal balance = new BigDecimal(account.getBalance());
		BigDecimal balanceDecimal = balance.subtract(new BigDecimal(money));
		double newBalance = balanceDecimal.doubleValue();
		//从账户中扣款
		try {
			JdbcUtils.startTransaction();
			accountDao.updateBalance(account.getId(),newBalance);
			//新增一条记录
			//1.查询商品信息
			Product product = productDao.findById(productId);
			
			//2.计算预期利润
			BigDecimal bigDecimal = new BigDecimal(money);
			BigDecimal bigDecimal2 = new BigDecimal(product.getAnnualized()).divide(new BigDecimal(100), 3,BigDecimal.ROUND_HALF_UP);
			BigDecimal bigDecimal3 = new BigDecimal(product.getProLimit()).divide(new BigDecimal(12), 3,BigDecimal.ROUND_HALF_UP);
			Double interest = bigDecimal.multiply(bigDecimal2).multiply(bigDecimal3).doubleValue();
			//封装属性
			ProductAccount productAccount = new ProductAccount();
			productAccount.setPa_num("PA"+new Date().getTime());
			productAccount.setC(customer);
			productAccount.setP(product);
			productAccount.setMoney(money);
			productAccount.setInterest(interest);
			//新增
			productAccountDao.addProductAccount(productAccount);
			JdbcUtils.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				JdbcUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				JdbcUtils.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public List<ProductAccount> findAllInfo(Integer cid) {
		try {
			List<Object[]> info = productAccountDao.findAllInfo(cid);
			List<ProductAccount> paList = new ArrayList<ProductAccount>();
			for (Object[] object : info) {
				ProductAccount pa = new ProductAccount();
				Product p = new Product();
				//pa表
				//pa_num投资编号
				//pa_date投资时间
				//pa_money本金
				//pa_interest预期收益
				
				//product表
				//proName">产品名称
				//proLimit">期限
				//annualized">年化利率
				pa.setPa_num((String)object[0]);
				pa.setPa_date((Date) object[1]);
				pa.setMoney((Double) object[2]);
				pa.setInterest((Double) object[3]);
				p.setProName((String) object[4]);
				p.setProLimit((Integer) object[5]);
				p.setAnnualized((Double) object[6]);
				
				pa.setP(p);
				pa.setStatus(checkStatus(pa.getPa_date(),p.getProLimit()));
				paList.add(pa);
			}
			return paList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Integer checkStatus(Date pa_date, Integer proLimit) {
		Calendar c = Calendar.getInstance();
		c.setTime(pa_date);
		c.add(Calendar.MONTH, proLimit);
		if(c.getTimeInMillis()-new Date().getTime()>=0) {
			//未到期
			return 0;
		}else {
			//到期
			return 1;
		}
	}
}
