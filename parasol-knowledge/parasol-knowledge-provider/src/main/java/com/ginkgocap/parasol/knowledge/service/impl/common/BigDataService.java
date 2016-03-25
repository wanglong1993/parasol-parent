package com.ginkgocap.parasol.knowledge.service.impl.common;

import com.ginkgocap.parasol.knowledge.model.KnowledgeMongo;
import com.ginkgocap.parasol.knowledge.service.common.IBigDataService;
import com.ginkgocap.parasol.knowledge.utils.PackingDataUtil;
import com.gintong.rocketmq.api.DefaultMessageService;
import com.gintong.rocketmq.api.enums.TopicType;
import com.gintong.rocketmq.api.model.RocketSendResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bigDataService")
public class BigDataService implements IBigDataService{

	private static final Logger logger = LoggerFactory.getLogger(BigDataService.class);

	@Autowired(required = true)
	private DefaultMessageService defaultMessageService;

	@Override
	public void sendMessage(String optionType, KnowledgeMongo knowledgeMongo, long userId) {
		logger.info("通知大数据，发送请求 请求用户{}", userId);
		RocketSendResult result = null;
		try {
			if (StringUtils.isNotBlank(optionType)) {
				result = defaultMessageService.sendMessage(TopicType.KNOWLEDGE_TOPIC, optionType, PackingDataUtil.packingSendBigData(knowledgeMongo,userId));
				logger.info("返回参数{}", result.getSendResult());
			} else {
				defaultMessageService.sendMessage(TopicType.KNOWLEDGE_TOPIC, PackingDataUtil.packingSendBigData(knowledgeMongo,userId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发送失败  返回参数{}", result.getSendResult());
		}
	}
	
	@Override
	public void sendMessage(String optionType, List<KnowledgeMongo> knowledgeMongoList, long userId) {
		if(knowledgeMongoList != null && !knowledgeMongoList.isEmpty()) {
			
			for (KnowledgeMongo data : knowledgeMongoList) {
				this.sendMessage(optionType, data, userId);
			}
			
		}
	}

	@Override
	public void deleteMessage(long knowledgeId, short columnId, long userId)
			throws Exception {
		
		KnowledgeMongo data = new KnowledgeMongo();
		
		data.setId(knowledgeId);
		data.setColumnId(columnId);
		
		this.sendMessage(KNOWLEDGE_UPDATE, data, userId);
		
	}

}
