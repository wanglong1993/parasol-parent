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
	 * @param userId
	 * @return
	 */
	public long countMessageRelationByUserId(long userId);
	
	/**
	 * 获取用户未读消息列表
	 * @param userId
	 * @return
	 */
	public List<MessageRelation> getMessageRelationsByUserId(long userId);
	
	public int delMessageRelation();
	
	public int delBatchMessageRelation();
	
}
