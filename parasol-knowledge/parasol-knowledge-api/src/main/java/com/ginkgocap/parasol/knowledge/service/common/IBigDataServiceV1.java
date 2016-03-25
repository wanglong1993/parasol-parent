package com.ginkgocap.parasol.knowledge.service.common;

import com.ginkgocap.parasol.knowledge.model.Knowledge;
import com.gintong.rocketmq.api.utils.FlagTypeUtils;

import java.util.List;

/**
 * Created by Admin on 2016/3/24.
 */
public interface IBigDataServiceV1 {


    /**知识MQ插入*/
    public final static String KNOWLEDGE_INSERT = FlagTypeUtils.createKnowledgeFlag();

    /**知识MQ更新*/
    public final static String KNOWLEDGE_UPDATE = FlagTypeUtils.createKnowledgeFlag();

    /**知识MQ删除*/
    public final static String KNOWLEDGE_DELETE = FlagTypeUtils.createKnowledgeFlag();

    /**
     * MQ数据发送
     * @author 周仕奇
     * @date 2016年1月14日 下午3:19:04
     * @param optionType 操作类型，插入（KNOWLEDGE_INSERT）、更新（KNOWLEDGE_UPDATE）、删除（KNOWLEDGE_DELETE）
     * @param knowledge 发送的数据
     * @param userId
     * @throws Exception
     */
    public void sendMessage(String optionType, Knowledge knowledge, long userId) throws Exception;

    /**
     * MQ数据发送（批量发送）
     * @author 周仕奇
     * @date 2016年1月14日 下午6:16:07
     * @param optionType 操作类型，插入（KNOWLEDGE_INSERT）、更新（KNOWLEDGE_UPDATE）、删除（KNOWLEDGE_DELETE）
     * @param knowledgeList 发送的数据
     * @param userId
     * @throws Exception
     */
    public void sendMessage(String optionType,List<Knowledge> knowledgeList, long userId) throws Exception;

    /**
     * MQ数据删除
     * @author 周仕奇
     * @date 2016年1月14日 下午6:44:34
     * @param knowledgeId
     * @param columnId
     * @param userId
     * @throws Exception
     */
    public void deleteMessage(long knowledgeId, short columnId, long userId) throws Exception;

}
