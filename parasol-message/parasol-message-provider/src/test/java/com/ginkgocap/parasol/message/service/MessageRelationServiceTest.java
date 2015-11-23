package com.ginkgocap.parasol.message.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ginkgocap.parasol.message.model.MessageRelation;

public class MessageRelationServiceTest extends TestBase{
	
	@Resource
	private MessageRelationService messageRelationService;
	
	@Test
	public void TestInsertMessageRelation () {
		
		MessageRelation rel = new MessageRelation();
		rel.setEntityId(3911302733103109l);
		rel.setIsRead(0);
		rel.setReceiverId(0l);
		rel.setStatus(0);
		rel.setType(1);
		messageRelationService.insertMessageRelation(rel);
	}
	
	@Test
	public void TestGetMessageRelationsByUserId() {
		List<MessageRelation> rels = messageRelationService.getMessageRelationsByUserId(1);
		System.out.println("rels="+rels.toString());
	}
}
