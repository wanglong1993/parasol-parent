package com.ginkgocap.parasol.sms.service;

import javax.annotation.Resource;

import org.junit.Test;

public class ShortMessageServiceTest extends TestBase{
	
	@Resource
	private ShortMessageService shortMessageService;
	
	@Test
	public void TestSendMessage() {
		
		int flag = shortMessageService.sendMessage("15011307812", "what a fucking day", 1473l, 1);
		
		System.out.println("send messsage flag =" + flag);
		
	}
}
