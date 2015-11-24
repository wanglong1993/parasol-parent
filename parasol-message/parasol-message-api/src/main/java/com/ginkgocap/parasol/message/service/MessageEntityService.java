package com.ginkgocap.parasol.message.service;

import java.util.List;
import java.util.Map;

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
	public MessageEntity getMessageEntityById(long id);
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public int insertMessageEntity(MessageEntity entity);
	
	/**
	 * 插入消息实体及消息关系
	 * @param params
	 * @return
	 */
	public Map<String, Object> insertMessageByParams(Map<String,String> params);
	
	/**
	 * 获取用户消息列表
	 * @param userId
	 * @param type
	 * @return entity列表
	 */
	public List<MessageEntity> getMessagesByUserIdAndType(long userId, int type);
	
	/**
	 * 通过消息实体列表获取消息
	 * @param ids
	 * @return
	 */
	public List<MessageEntity> getMessagesByIds(List<Long> ids);
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public int updateMessageEntity(MessageEntity entity);
	
	public int deleteMessageEntity(long entityId);
	
}
