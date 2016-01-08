package com.ginkgocap.parasol.message.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.message.model.MessageRelation;
import com.ginkgocap.parasol.message.service.MessageRelationService;

@Service("messageRelationService")
public class MessageRelationServiceImpl extends BaseService<MessageRelation> implements MessageRelationService {

	
	private Logger logger = LoggerFactory.getLogger(MessageRelationServiceImpl.class);
	
	@Override
	public MessageRelation insertMessageRelation (MessageRelation relation) {
		logger.info("进入添加消息提醒：参数relation.entityId", relation.getEntityId());
		Long rel = null;
		try {
			rel = (Long)saveEntity(relation);
			relation.setId(rel);
		} catch (BaseServiceException e) {
			logger.error("添加消息提醒失败：参数relation.entityId", relation.getEntityId());
			e.printStackTrace();
		}
		return relation;
	}

	@Override
	public int insertBatchMessageRelation(List<MessageRelation> relations) {
		logger.info("进入批量添加消息提醒：参数relations.size:{}", relations.size());	
		List<MessageRelation> relationIds = null;
		try {
			relationIds = saveEntitys(relations);
		} catch (BaseServiceException e) {
			logger.error("批量添加消息提醒失败：参数relations.size:{}", relations.size());
			e.printStackTrace();
		}
		return relationIds==null ? 0 : relationIds.size();
	}

	@Override
	public int countMessageRelationByUserId(long userId, long appId) {
		logger.info("进入查询我的消息提醒数：参数userId:{}", userId);		
		int count = 0;
		try {
			count = countEntitys("MessageRelation_List_Id_ReceiverId_AppId",userId, appId);
		} catch (BaseServiceException e) {
			logger.error("查询我的消息提醒数失败：参数userId:{}", userId);
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int countMessageRelationByUserIdAndType(long userId, int type, long appId) {
		logger.info("进入查询我的消息提醒数：参数userId:{}, type:{}, appId:{}", userId, type, appId);		
		int count = 0;
		try {
			count = countEntitys("MessageRelation_List_Id_ReceiverId_Type_AppId", userId, type, appId);
		} catch (BaseServiceException e) {
			logger.error("查询我的消息提醒数失败：参数userId:{},type:{}, appId{}", userId, type, appId);
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<MessageRelation> getMessageRelationsByUserId(long userId, long appId) {
		logger.info("进入获取我的消息列表：参数userId:{}, appId:{}", userId, appId);
		List<MessageRelation> relations = null;
		try {
			relations = getSubEntitys("MessageRelation_List_Id_ReceiverId_AppId", 0,20, userId, appId);
		} catch (BaseServiceException e) {
			logger.info("获取我的消息列表失败：参数userId:{}, appId:{}", userId, appId);
			e.printStackTrace();
		}
		return relations;
	}
	

	@Override
	public List<MessageRelation> getMessageRelationsByUserIdAndType(long userId, int type, long appId) {
		logger.info("进入获取我的特定消息列表：参数userId:{}, type:{}, appId:{}", userId, type, appId);
		List<MessageRelation> relations = null;
		try {
			relations = getSubEntitys("MessageRelation_List_Id_ReceiverId_Type_AppId", 0,20, userId,type,appId);
		} catch (BaseServiceException e) {
			logger.info("获取我的特定消息列表失败：参数userId:{}, type:{}, appId{} ", userId, type, appId);
			e.printStackTrace();
		}
		return relations;
	}	

	@Override
	public boolean delBatchMessageRelation(List<Long> relIds, long userId) {
		logger.info("进入批量删除消息关系列表：参数relIds：{}, userId:{}", relIds,userId);
		boolean flag = false;
		// 通过id列表批量删除消息关系
		try {
			flag = deleteEntityByIds(relIds);
		} catch (BaseServiceException e) {
			logger.error("批量删除消息关系列表出错，请检查！参数relIds：{}, userId:{}", relIds,userId);
		}
		return flag;
	}


}
