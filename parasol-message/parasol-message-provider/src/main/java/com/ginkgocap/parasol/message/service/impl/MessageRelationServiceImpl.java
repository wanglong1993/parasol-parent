package com.ginkgocap.parasol.message.service.impl;

import java.io.Serializable;
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
		
		Long rel = null;
		try {
			rel = (Long)saveEntity(relation);
			relation.setId(rel);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return relation;
	}

	@Override
	public int insertBatchMessageRelation(List<MessageRelation> relations) {
		List<Serializable> relationIds = null;
		try {
			relationIds = saveEntitys(relations);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return relationIds==null ? 0 : relationIds.size();
	}

	@Override
	public int countMessageRelationByUserId(long userId) {
		logger.info("进入查询我的消息提醒数：参数userId:{}", userId);		
		int count = 0;
		try {
			count = countEntitys("MessageRelation_List_Id_ReceiverId",userId);
		} catch (BaseServiceException e) {
			logger.error("查询我的消息提醒数失败：参数userId:{}", userId);
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int countMessageRelationByUserIdAndType(long userId, int type) {
		logger.info("进入查询我的消息提醒数：参数userId:{}, type:{}", userId, type);		
		int count = 0;
		try {
			count = countEntitys("MessageRelation_List_Id_ReceiverId_Type",userId,type);
		} catch (BaseServiceException e) {
			logger.error("查询我的消息提醒数失败：参数userId:{},type:{}", userId, type);
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<MessageRelation> getMessageRelationsByUserId(long userId) {
		
		List<MessageRelation> relations = null;
		try {
			relations = getSubEntitys("MessageRelation_List_Id_ReceiverId", 0,20, userId);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relations;
	}
	
	@Override
	public int delMessageRelation(List<Serializable> entityIds, long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delBatchMessageRelation(List<Serializable> relIds, long userId) {
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
