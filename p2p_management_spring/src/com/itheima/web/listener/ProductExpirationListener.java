package com.itheima.web.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 监听到期投资产品
 * @author wking
 *
 */
public class ProductExpirationListener implements ServletContextListener{


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//获取下一天的0时
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),0,0,0);
		//设置计时器,每24小时执行一次run
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//查询product_account表,判断如果到期,将收益累加到当前客户account的interest上
				//不想写了
			}
		}, c.getTime(), 24*60*60*1000);
		
	}

}
