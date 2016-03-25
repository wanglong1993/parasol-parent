package com.ginkgocap.parasol.knowledge.service;

import com.ginkgocap.parasol.knowledge.model.KnowledgeData;
import com.ginkgocap.parasol.knowledge.model.common.InterfaceResult;

import java.util.List;

/**
 * Created by Admin on 2016/3/24.
 */
public interface IKnowledgeServiceV1 {
    /**
     * 插入，承担以下任务：
     * <p>1、知识详细表插入</P>
     * <p>2、知识基础表插入</P>
     * <p>3、知识来源表插入</P>
     * <p>4、大数据MQ推送</P>
     * <p>5、动态推送</P>
     * @date 2016年1月15日 上午9:41:03
     * @param KnowledgeData
     * @param userId
     * @return
     * @throws Exception
     */
    public InterfaceResult<KnowledgeData> insert(KnowledgeData KnowledgeData, long userId) throws Exception;

    /**
     * 更新，承担以下任务：
     * <p>1、知识详细表更新</P>
     * <p>2、知识基础表更新</P>
     * <p>3、知识来源表更新</P>
     * <p>4、大数据MQ推送更新</P>
     * <p>5、动态推送更新</P>
     * @date 2016年1月15日 上午9:41:16
     * @param KnowledgeData
     * @return
     * @throws Exception
     */
    public InterfaceResult<KnowledgeData> update(KnowledgeData KnowledgeData, long userId) throws Exception;

    /**
     * 删除，承担以下任务：
     * <p>1、知识详细表删除</P>
     * <p>2、知识基础表删除</P>
     * <p>3、知识来源表删除</P>
     * <p>4、大数据MQ推送删除</P>
     * <p>5、动态推送删除</P>
     * @date 2016年1月15日 上午9:41:20
     * @param knowledgeId
     * @param columnId
     * @param userId
     * @return
     * @throws Exception
     */
    public InterfaceResult<KnowledgeData> deleteByKnowledgeId(long knowledgeId, short columnId, long userId) throws Exception;

    /**
     * 批量删除，承担以下任务：
     * <p>1、知识详细表批量删除</P>
     * <p>2、知识基础表批量删除</P>
     * <p>3、知识来源表批量删除</P>
     * <p>4、大数据MQ推送批量删除</P>
     * <p>5、动态推送批量删除</P>
     * @date 2016年1月15日 上午9:41:23
     * @param knowledgeIds
     * @param columnId
     * @param userId
     * @return
     * @throws Exception
     */
    public InterfaceResult deleteByKnowledgeIds(List<Long> knowledgeIds, short columnId, long userId) throws Exception;

    /**
     * 提取详细信息（一般用在知识详细信息查看界面、知识编辑界面的数据提取中），具体提取以下信息：
     * <p>1、知识详细表信息</P>
     * <p>2、知识来源表信息</P>
     * @date 2016年1月15日 上午9:41:26
     * @param knowledgeId
     * @param columnId 由于知识详细表信息为分库存储，则columnId为必须字段
     * @param userId
     * @return
     * @throws Exception
     */
    public KnowledgeData getDetailById(long knowledgeId, short columnId, long userId) throws Exception;

    /**
     * 提取简要信息（一般用在知识简要信息界面的数据提取中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * <p>2、知识来源表信息</P>
     * @date 2016年1月15日 上午9:41:39
     * @param knowledgeId
     * @param userId
     * @return
     * @throws Exception
     */
    public KnowledgeData getBaseById(long knowledgeId, long userId) throws Exception;

    /**
     * 提取简要信息列表（一般用在知识简要信息界面的数据提取中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * <p>2、知识来源表信息</P>
     * @date 2016年1月15日 上午9:41:37
     * @param knowledgeIds
     * @param userId
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByIds(List<Long> knowledgeIds, long userId) throws Exception;

    /**
     * 提取所有数据（一般用在首页数据展示中）
     * <p>1、知识基础表信息</P>
     * @author 周仕奇
     * @date 2016年1月15日 下午5:43:26
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseAll(int start, int size) throws Exception;

    /**
     * 根据用户ID提取简要信息列表（一般用在用户个人中心知识信息列表查询的数据提取中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * @date 2016年1月15日 上午9:41:33
     * @param userId
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByCreateUserId(long userId, int start, int size) throws Exception;

    /**
     * 根据用户ID与栏目ID提取简要信息列表（一般用在用户个人中心知识信息列表查询的数据提取中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * @date 2016年1月15日 上午9:41:32
     * @param userId
     * @param columnId
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByCreateUserIdAndColumnId(long userId, short columnId, int start,int size) throws Exception;

    /**
     * 根据用户ID与类型提取简要信息列表（一般用个人中心在根据类型区分的知识信息列表查询的数据提取中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * @date 2016年1月15日 上午9:41:30
     * @param userId
     * @param type
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByCreateUserIdAndType(long userId, String type, int start, int size) throws Exception;

    /**
     * 根据用户ID与类型提取简要信息列表（一般用在个人中心根据类型、栏目区分的知识信息列表查询的数据提取中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * @date 2016年1月15日 上午9:41:29
     * @param userId
     * @param columnId
     * @param type
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByCreateUserIdAndColumnIdAndType(long userId, short columnId, String type, int start, int size) throws Exception;

    /**
     * 根据类型提取简要信息列表（一般用在游客、或者首页等不区分用户的界面中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * @date 2016年1月15日 上午10:14:51
     * @param type
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByType(String type,int start,int size) throws Exception;

    /**
     * 根据栏目提取简要信息列表（一般用在游客、或者首页等不区分用户的界面中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * @date 2016年1月15日 上午10:14:55
     * @param columnId
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByColumnId(short columnId,int start,int size) throws Exception;

    /**
     * 根据栏目、类型提取简要信息列表（一般用在游客、或者首页等不区分用户的界面中），具体提取以下信息：
     * <p>1、知识基础表信息</P>
     * @date 2016年1月15日 上午10:14:59
     * @param columnId
     * @param type
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<KnowledgeData> getBaseByColumnIdAndType(short columnId, String type, int start,int size) throws Exception;
}