package com.ginkgocap.parasol.message.service.impl;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.message.model.MessageEntity;
import com.ginkgocap.parasol.message.service.MessageEntityService;

@Service("messageEntityService")
public class MessageEntityServiceImpl extends BaseService<MessageEntity> implements MessageEntityService{

	@Override
	public int insertMessageEntity(MessageEntity entity) {
		try {
			saveEntity(entity);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateMessageEntity(MessageEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMessageEntity(long entityId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
