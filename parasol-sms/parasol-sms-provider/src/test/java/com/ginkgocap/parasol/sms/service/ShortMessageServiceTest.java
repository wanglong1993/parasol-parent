package com.ginkgocap.parasol.sms.service;

import javax.annotation.Resource;

import org.junit.Test;

public class ShortMessageServiceTest extends TestBase{
	
	@Resource
	private ShortMessageService shortMessageService;
	
	@Test
	public void TestSendMessage() {
		
		String msg = "您的短信验证码为" + 669902 + "，有效期30分钟请及时验证";
		int flag = shortMessageService.sendMessage("15011307812", msg, 1473l, 1);
		
		System.out.println("send messsage flag =" + flag);
		
	}
}
