package com.ginkgocap.parasol.message.service.impl;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.message.model.MessageRelation;
import com.ginkgocap.parasol.message.service.MessageRelationService;

@Service("messageRelationService")
public class MessageRelationServiceImpl extends BaseService<MessageRelation> implements MessageRelationService {

	@Override
	public MessageRelation insertMessageRelation (MessageRelation relation) {
		
		MessageRelation rel = null;
		try {
			rel = (MessageRelation)saveEntity(relation);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public int insertBatchMessageRelation() {
		// TODO Auto-generated method stub
		return 0;
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
