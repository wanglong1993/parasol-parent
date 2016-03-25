package com.ginkgocap.parasol.knowledge.dao;

import com.ginkgocap.parasol.knowledge.model.Knowledge;

import java.util.List;

/**
 * Created by Admin on 2016/3/24.
 */
public interface IKnowledgeMongoV1 {
    /**
     * 默认数据库表名，一般用作数据表的前缀
     */
    public final static String KNOWLEDGE_COLLECTION_NAME = "knowledge";

    /**
     * 用户自行录入的数据，表名后缀
     */
    public final static String KNOWLEDGE_COLLECTION_USERSELF_NAME = "UserSelf";

    /**
     * 插入
     * @date 2016年1月13日 上午10:54:20
     * @param knowledge
     * @param userId
     * @return
     * @throws Exception
     */
    public Knowledge insert(Knowledge knowledge, long userId) throws Exception;

    /**
     * 批量插入
     * @date 2016年1月13日 下午4:24:56
     * @param knowledgeList
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Knowledge> insertList(List<Knowledge> knowledgeList, long userId) throws Exception;

    /**
     * 更新
     * @date 2016年1月13日 上午10:54:29
     * @param knowledge
     * @param userId
     * @return
     * @throws Exception
     */
    public Knowledge update(Knowledge knowledge, long userId) throws Exception;

    /**
     * 先删除后插入
     * @date 2016年1月13日 上午10:54:44
     * @param knowledge
     * @param knowledgeId
     * @param userId
     * @return
     * @throws Exception
     */
    public Knowledge insertAfterDelete(Knowledge knowledge,long knowledgeId, long userId) throws Exception;

    /**
     * 根据主键及栏目删除数据
     * @date 2016年1月13日 上午10:54:47
     * @param id
     * @param columnId
     * @return
     * @throws Exception
     */
    public int deleteByIdAndColumnId(long id, short columnId) throws Exception;

    /**
     * 根据主键list以及栏目批量删除数据
     * @date 2016年1月13日 上午10:54:50
     * @param ids
     * @param columnId
     * @return
     * @throws Exception
     */
    public int deleteByIdsAndColumnId(List<Long> ids, short columnId) throws Exception;

    /**
     * 根据用户Id以及栏目删除数据
     * @date 2016年1月13日 上午10:54:53
     * @param createUserId
     * @param columnId
     * @return
     * @throws Exception
     */
    public int deleteByCreateUserIdAndColumnId(long createUserId, short columnId) throws Exception;

    /**
     * 根据主键以及栏目提取数据
     * @date 2016年1月13日 上午10:54:56
     * @param id
     * @param columnId
     * @return
     * @throws Exception
     */
    public Knowledge getByIdAndColumnId(long id, short columnId) throws Exception;

    /**
     * 根据主键list以及栏目提取数据
     * @date 2016年1月13日 上午10:54:58
     * @param ids
     * @param columnId
     * @return
     * @throws Exception
     */
    public List<? extends Knowledge> getByIdsAndColumnId(List<Long> ids, short columnId) throws Exception;

}
