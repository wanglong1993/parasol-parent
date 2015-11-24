package com.ginkgocap.parasol.message.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.message.model.MessageRelation;
import com.ginkgocap.parasol.message.service.MessageRelationService;

@Service("messageRelationService")
public class MessageRelationServiceImpl extends BaseService<MessageRelation> implements MessageRelationService {

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
	public long countMessageRelationByUserId(long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MessageRelation> getMessageRelationsByUserId(long userId) {
		
		List<MessageRelation> relations = null;
		try {
			relations = getSubEntitys("MessageRelation_List_Id_ReceiverId_Type", 0,20, userId , 0);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relations;
	}
	
	@Override
	public int delMessageRelation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delBatchMessageRelation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
