package com.ginkgocap.parasol.message.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.ginkgocap.parasol.message.model.MessageEntity;

public class MessageEntityServiceTest extends TestBase{
	
	@Resource
	private MessageEntityService messageEntityService;
	
	@Test
	public void TestInsertMessageEntity() {
		MessageEntity entity = new MessageEntity();
		entity.setId(111111111111l);
		entity.setAppid("test");
		entity.setContent("what a fucking day!");
		entity.setSourceId(111l);
		messageEntityService.insertMessageEntity(entity);
	}
}
