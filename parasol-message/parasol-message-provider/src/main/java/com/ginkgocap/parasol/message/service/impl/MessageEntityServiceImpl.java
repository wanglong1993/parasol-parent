package com.ginkgocap.parasol.message.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.message.model.MessageEntity;
import com.ginkgocap.parasol.message.model.MessageRelation;
import com.ginkgocap.parasol.message.service.MessageEntityService;
import com.ginkgocap.parasol.message.service.MessageRelationService;

@Service("messageEntityService")
public class MessageEntityServiceImpl extends BaseService<MessageEntity> implements MessageEntityService{

	@Resource
	private MessageRelationService messageRelationService;
	
    private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public long insertMessageEntity(MessageEntity entity) {
		logger.info("进入保存消息提醒实体，entity.id：{},entity.content:{}", entity.getCreaterId(), entity.getContent());
		long id = 0;
		try {
			id = (Long)saveEntity(entity);
		} catch (BaseServiceException e) {
			logger.error("保存消息提醒实体失败，entity.id：{},entity.content:{}", entity.getCreaterId(), entity.getContent());
			e.printStackTrace();
		}
		return id;
	}


	@Override
	public Map<String, Object> insertMessageByParams(Map<String, String> params) {
		
		logger.info("进入插入消息队列：参数params：{}", params.toString());
		Map<String, Object> result = new HashMap<String, Object>();
		String type = params.get("type");
		String createrId = params.get("createrId");
		String content = params.get("content");
		String sourceId = params.get("sourceId");
		String sourceType = params.get("sourceType");
		String sourceTitle = params.get("sourceTitle");
		String appid = params.get("appid");
		String receiverIds = params.get("receiverIds");
		
		MessageEntity entity = new MessageEntity();
		entity.setCreaterId(Long.valueOf(createrId));
		entity.setSourceId(Long.valueOf(sourceId));
		entity.setSourceTitle(sourceTitle);
		entity.setSourceType(sourceType);
		entity.setContent(content);
		entity.setType(Integer.valueOf(type));
		entity.setAppid(appid);
		Long entityId = null;
		List<MessageRelation> relations = new ArrayList<MessageRelation>();
		
		try {
			entityId = (Long) saveEntity(entity);
		} catch (BaseServiceException e) {
			logger.error("插入消息提醒出错：参数createrId：{}, sourceId:{}", createrId,sourceId);
			result.put("result", "false");
			result.put("message", "保存消息实体失败，"+e.getMessage());
			return result;
		}
		
		List<String> ids = Arrays.asList(receiverIds.split(","));
		for(String id : ids) {
			MessageRelation rel = new MessageRelation();
			rel.setEntityId(entityId);
			rel.setIsRead(0);
			rel.setReceiverId(Long.valueOf(id));
			rel.setStatus(0);
			rel.setType(Integer.valueOf(type));
			rel.setDealTime(System.currentTimeMillis());
			relations.add(rel);
		}
		int i = messageRelationService.insertBatchMessageRelation(relations);
		if( i==0 ) {
			logger.error("插入消息关系出错：参数createrId：{}, sourceId:{}", createrId,sourceId);
			result.put("result", "false");
			result.put("message", "保存消息关系失败！");
		} else {
			result.put("result", "true");
			result.put("message", "保存消息成功！");
		}
		return result;
	}


	@Override
	public List<MessageEntity> getMessagesByUserIdAndType(long userId, int type) {
		logger.info("进入通过用户名称，消息类型获取消息列表：参数userId：{}, type:{}", userId,type);
		
		List<MessageEntity> entities = new ArrayList<MessageEntity>();
		
		// 通过userId获取消息关系列表
		List<MessageRelation> relations = messageRelationService.getMessageRelationsByUserId(userId);
		
		// 新建map，用于存放消息关系
		Map<Long, MessageRelation> mapRel = new HashMap<Long, MessageRelation>();
		
		// 消息实体id列表
		List<Long> ids = new ArrayList<Long>();
		// 
		for (MessageRelation rel : relations) {
			ids.add(rel.getEntityId());
			mapRel.put(rel.getEntityId(), rel);
		}
		
		try {
			// 通过id列表获取实体列表
			entities = getEntityByIds(ids);
		} catch (BaseServiceException e1) {
			logger.error("进入通过用户名称，消息类型获取消息列表出错：参数userId：{}, type:{}, exception:{}", userId,type,e1.getMessage());
			return entities;
		}
		for(MessageEntity entity : entities) {
			mapRel.get(entity.getId());
			entity.setIsRead(mapRel.get(entity.getId()).getIsRead());
		}
		
		return entities;
	}
	
	@Override
	public List<MessageEntity> getMessagesByIds(List<Long> ids) {
		logger.info("进入根据id列表获取消息提醒实体方法，ids：{}", ids);
		List<MessageEntity> entities = null;
		try {
			entities = getEntityByIds(ids);
		} catch (BaseServiceException e) {
			logger.error("根据id列表获取消息提醒实体失败，ids：{}", ids);
			e.printStackTrace();
		}
			
		return entities;
	}
	
	@Override
	public MessageEntity getMessageEntityById(long id) {
		logger.info("进入根据id获取消息提醒实体方法，id：{}", id);
		try {
			return getEntity(id);
		} catch (BaseServiceException e) {
			logger.error("根据id获取消息提醒实体失败，id：{}", id);
			e.printStackTrace();
		}
		return null;
	}	
	
	@Override
	public boolean updateMessageEntity(MessageEntity entity) {
		logger.info("进入更新消息实体，entityId：{}", entity.getId());
		boolean flag = false;
		try {
			flag = updateEntity(entity);
		} catch (BaseServiceException e) {
			logger.error("更新消息实体失败，entityId：{}", entity.getId());
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteMessageEntity(long entityId) {
		logger.info("进入通过id，删除消息：参数entityId：{}", entityId);
		boolean flag = false;
		try {
			flag = deleteEntity(entityId);
		} catch (BaseServiceException e) {
			logger.error("通过id，删除消息失败：参数entityId：{}", entityId);
			e.printStackTrace();
		}
		return flag;
	}

}
