package com.itheima.utils;

import java.util.Random;

import org.junit.Test;

public class JunitTestDemo {

	@Test
	public void testName() throws Exception {

		Random random = new Random();
		String randomCode = "";
		for (int i = 0; i < 6; i++) {
			Integer randomNum = random.nextInt(10);
			randomCode += randomNum;
		}
		MailUtils.sendMail("wk52525@126.com", randomCode);
	}

}
