package com.ginkgocap.parasol.knowledge.service.impl.common;

import com.ginkgocap.parasol.knowledge.model.Knowledge;
import com.ginkgocap.parasol.knowledge.service.common.IBigDataServiceV1;
import com.ginkgocap.parasol.knowledge.utils.PackingDataUtil;
import com.gintong.rocketmq.api.DefaultMessageService;
import com.gintong.rocketmq.api.enums.TopicType;
import com.gintong.rocketmq.api.model.RocketSendResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Admin on 2016/3/24.
 */
public class BigDataServiceV1 implements IBigDataServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(BigDataService.class);

    @Autowired(required = true)
    private DefaultMessageService defaultMessageService;

    @Override
    public void sendMessage(String optionType, Knowledge knowledge, long userId) {
        logger.info("通知大数据，发送请求 请求用户{}", userId);
        RocketSendResult result = null;
        try {
            if (StringUtils.isNotBlank(optionType)) {
                result = defaultMessageService.sendMessage(TopicType.KNOWLEDGE_TOPIC, optionType, PackingDataUtil.packingSendBigData(knowledge, userId));
                logger.info("返回参数{}", result.getSendResult());
            } else {
                defaultMessageService.sendMessage(TopicType.KNOWLEDGE_TOPIC, PackingDataUtil.packingSendBigData(knowledge,userId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("发送失败  返回参数{}", result.getSendResult());
        }
    }

    @Override
    public void sendMessage(String optionType, List<Knowledge> knowledgeList, long userId) {
        if(knowledgeList != null && !knowledgeList.isEmpty()) {

            for (Knowledge data : knowledgeList) {
                this.sendMessage(optionType, data, userId);
            }

        }
    }

    @Override
    public void deleteMessage(long knowledgeId, short columnId, long userId)
            throws Exception {

        Knowledge data = new Knowledge();

        data.setId(knowledgeId);
        data.setColumnId(columnId);

        this.sendMessage(KNOWLEDGE_UPDATE, data, userId);

    }

}
