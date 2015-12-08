package com.ginkgocap.parasol.message.service;

import java.util.List;

import com.ginkgocap.parasol.message.model.MessageRelation;

/**
 * 
* <p>Title: MessageRelation.java<／p> 
* <p>Description: message relation <／p> 

* @author fuliwen 
* @date 2015-11-18 
* @version 1.0
 */
public interface MessageRelationService {
	
	/**
	 * 插入消息关系
	 * @param relation
	 * @return
	 */
	public MessageRelation insertMessageRelation(MessageRelation relation);
	
	/**
	 * 批量插入消息关系
	 * @return
	 */
	public int insertBatchMessageRelation(List<MessageRelation> relations);
	
	/**
	 * 获取用户未读消息数
	 * @param userId 用户id
	 ** @return 消息总数
	 */
	public int countMessageRelationByUserId(long userId);
	
	/**
	 * 获取用户未读消息数
	 * @param userId
	 * @param type
	 * @return 消息总数
	 */
	public int countMessageRelationByUserIdAndType(long userId, int type);
	
	/**
	 * 获取用户未读消息列表
	 * @param userId
	 * @return
	 */
	public List<MessageRelation> getMessageRelationsByUserId(long userId);
	
	/**
	 * 获取用户某种类型的消息
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<MessageRelation> getMessageRelationsByUserIdAndType(long userId, int type);
	
	/**
	 * 根据entity id列表批量删除消息关系
	 * @param entityIds
	 * @param userId
	 * @return
	 */
	public boolean delBatchMessageRelation(List<Long> entityIds, long userId);
	
}
