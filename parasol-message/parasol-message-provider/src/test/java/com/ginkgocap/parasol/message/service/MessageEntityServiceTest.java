package com.ginkgocap.parasol.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Test
	public void TestGetMessageEntity() {
		long id = 3911302733103109l;
		MessageEntity entity = messageEntityService.getMessageEntityById(id);
		System.out.println("entity = "+entity.getContent());
	}
	
	@Test
	public void TestInsertMessageByParams() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> result = null;
		
		params.put("type", "1");
		params.put("createrId", "111111");
		params.put("content", "TestInsertMessageByParams");
		params.put("sourceId", "112233");
		params.put("sourceType", "1");
		params.put("sourceTitle", "中华人名共和国");
		params.put("appid","Test");
		params.put("receiverIds", "1,2,3,4,5,6");
		result = messageEntityService.insertMessageByParams(params);
		
		System.out.println("result=" + result.toString());
	}
	
	@Test
	public void TestGetMessagesByUserId() {
		List<MessageEntity> entities = messageEntityService.getMessagesByUserId(1, 0);
		System.out.println("entities="+entities);
	}
}
