package com.ginkgocap.parasol.message.service;

import com.ginkgocap.parasol.message.model.MessageEntity;


/**
 * 
* <p>Title: MessageEntity.java<／p> 
* <p>Description: message entity <／p> 

* @author fuliwen 
* @date 2015-11-18 
* @version 1.0
 */
public interface MessageEntityService {
	
	public MessageEntity getMessageEntityById(long id);
	
	public int insertMessageEntity(MessageEntity entity);
	
	public int updateMessageEntity(MessageEntity entity);
	
	public int deleteMessageEntity(long entityId);
	
}
