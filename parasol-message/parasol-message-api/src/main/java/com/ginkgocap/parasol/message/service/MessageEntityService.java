package com.ginkgocap.parasol.message.service;

import java.util.List;
import java.util.Map;

import com.ginkgocap.parasol.message.exception.MessageEntityServiceException;
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
	
	/**
	 * 通过Id获取消息实体
	 * @param id
	 * @return
	 */
	public MessageEntity getMessageEntityById(long id) throws MessageEntityServiceException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public long insertMessageEntity(MessageEntity entity) throws MessageEntityServiceException;
	
	/**
	 * 插入消息实体及消息关系
	 * @param params
	 * @return
	 */
	public Map<String, Object> insertMessageByParams(Map<String,String> params) throws MessageEntityServiceException;
	
	/**
	 * 获取用户消息列表
	 * @param userId
	 * @param type
	 * @return entity列表
	 */
	public List<MessageEntity> getMessagesByUserIdAndType(long userId, int type, long appId) throws MessageEntityServiceException;
	
	/**
	 * 通过消息实体列表获取消息
	 * @param ids
	 * @return
	 */
	public List<MessageEntity> getMessagesByIds(List<Long> ids) throws MessageEntityServiceException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws MessageEntityServiceException
	 */
	public boolean updateMessageEntity(MessageEntity entity) throws MessageEntityServiceException;
	
	/**
	 * 
	 * @param entityId
	 * @return
	 * @throws MessageEntityServiceException
	 */
	public boolean deleteMessageEntity(long entityId) throws MessageEntityServiceException;
	
}
