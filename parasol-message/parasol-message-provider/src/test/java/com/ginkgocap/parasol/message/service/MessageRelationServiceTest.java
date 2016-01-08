package com.ginkgocap.parasol.message.service;

import java.util.ArrayList;
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
		rel.setEntityId(3928965047910405l);
		rel.setIsRead(0);
		rel.setReceiverId(0l);
		rel.setStatus(0);
		rel.setType(1);
		messageRelationService.insertMessageRelation(rel);
	}
	
	@Test
	public void TestGetMessageRelationsByUserId() {
		List<MessageRelation> rels = messageRelationService.getMessageRelationsByUserId(1,111111);
		System.out.println("rels="+rels.toString());
	}
	
	@Test
	public void TestCountMessageRelationByUserId() {
		
		int count = messageRelationService.countMessageRelationByUserId(1l,111111);
		System.out.println("count===="+count);
		int count2 = messageRelationService.countMessageRelationByUserIdAndType(1l,0,111111);
		System.out.println("count2====="+count2);
	}
	
	@Test
	public void TestDeleteMessageRelationByIds() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(3913017205194757l);
		long userId = 1;
		boolean flag = messageRelationService.delBatchMessageRelation(ids, userId);
		
		System.out.println("flag : ===="+flag);
	}
}
