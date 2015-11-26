package com.ginkgocap.parasol.message.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.message.model.MessageEntity;
import com.ginkgocap.parasol.message.model.MessageRelation;
import com.ginkgocap.parasol.message.service.MessageEntityService;
import com.ginkgocap.parasol.message.service.MessageRelationService;

public class MessageFacadeServiceImpl {
	@Autowired
	private MessageEntityService messageEntityService;
	
	@Autowired
	private MessageRelationService messageRelationService;
	
	
	
	public List<MessageEntity> getMessageEntity(Long userId) {
		List<MessageRelation> messageRelations = messageRelationService.getMessageRelationsByUserId(userId);
		
		List<Long> messageIds = new ArrayList<Long>();
		//List<Long> messageIds_unread = new ArrayList<Long>();

		
		for (MessageRelation relation : messageRelations) {
//				messageIds_read.add(relation.getEntityId());
			}
		return null;
		}
		
//		List<MessageEntity> messageEntitys = messageEntityService.getMessageEntityById(messageIds_read);
//	}
	
}
