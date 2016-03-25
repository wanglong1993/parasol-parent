package com.ginkgocap.parasol.knowledge.service.impl;

import com.ginkgocap.parasol.knowledge.dao.IKnowledgeBaseDao;
import com.ginkgocap.parasol.knowledge.dao.IKnowledgeMongoV1;
import com.ginkgocap.parasol.knowledge.dao.IKnowledgeReferenceDao;
import com.ginkgocap.parasol.knowledge.model.*;
import com.ginkgocap.parasol.knowledge.model.common.CommonResultCode;
import com.ginkgocap.parasol.knowledge.model.common.InterfaceResult;
import com.ginkgocap.parasol.knowledge.service.IKnowledgeServiceV1;
import com.ginkgocap.parasol.knowledge.service.common.IBigDataService;
import com.ginkgocap.parasol.knowledge.service.common.IBigDataServiceV1;
import com.ginkgocap.parasol.knowledge.service.common.IKnowledgeCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/3/24.
 */
public class KnowledgeServiceV1 implements IKnowledgeServiceV1 {
    private Logger logger = LoggerFactory.getLogger(KnowledgeService.class);

    /**知识简表*/
    @Autowired
    private IKnowledgeBaseDao knowledgeBaseDao;
    /**知识详细表*/
    @Autowired
    private IKnowledgeMongoV1 knowledgeDao;
    /**知识来源表*/
    @Autowired
    private IKnowledgeReferenceDao knowledgeReferenceDao;
    /**知识公共服务*/
    @Autowired
    private IKnowledgeCommonService knowledgeCommonService;
    /**MQ大数据服务*/
    @Autowired
    private IBigDataServiceV1 bigDataService;
    /**动态推送服务*/
    //@Autowired
    //private UserFeedService userFeedService;
    /**心情日记*/
    //@Autowired
    //private DiaryService diaryService;

    //@AssoSaveAnnotation

    @Override
    public InterfaceResult<KnowledgeData> insert(KnowledgeData knowledgeData, long userId) throws Exception {

        Knowledge knowledge = (Knowledge) knowledgeData.getKnowledge();
        KnowledgeReference knowledgeReference = knowledgeData.getReference();

        long knowledgeId = this.knowledgeCommonService.getKnowledgeSeqenceId();

        short columnId = knowledge.getColumnId();

        knowledge.setId(knowledgeId);

        //knowledge.createContendDesc();

        //知识详细信息插入
        Knowledge afterSaveKnowledge = this.knowledgeDao.insert(knowledge, userId);

        //知识基础表插入
        try {
            this.knowledgeBaseDao.insert(KnowledgeUtil.convertOldKnowledge(afterSaveKnowledge), userId);
        } catch (Exception e) {
            this.insertRollBack(knowledgeId, columnId, userId, true, false, false, false, false);
            logger.error("知识基础表插入失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //知识来源表插入
        KnowledgeReference afterSaveKnowledgeReference = null;
        try {
            this.knowledgeReferenceDao.insert(knowledgeReference, knowledgeId, userId);
        } catch (Exception e) {
            this.insertRollBack(knowledgeId, columnId, userId, true, true, false, false, false);
            logger.error("知识基础表插入失败！失败原因：\n"+e.getCause().toString());
            //return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //大数据MQ推送
        try {
            //bigDataService.sendMessage(IBigDataService.KNOWLEDGE_INSERT, afterSaveKnowledge, userId);
        } catch (Exception e) {
            this.insertRollBack(knowledgeId, columnId, userId, true, true, true, false, false);
            logger.error("知识MQ推送失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //动态推送（仅推送观点）
        try {
            //userFeedService.saveOrUpdate(PackingDataUtil.packingSendFeedData(afterSaveKnowledge, userId, diaryService));
        } catch (Exception e) {
            this.insertRollBack(knowledgeId, columnId, userId, true, true, true, true, false);
            logger.error("动态推送失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        return InterfaceResult.getSuccessInterfaceResultInstance(getReturn(afterSaveKnowledge,afterSaveKnowledgeReference));
    }

    //@AssoUpdateAnnotation
    @Override
    public InterfaceResult<KnowledgeData> update(KnowledgeData knowledgeData, long userId) throws Exception {

        Knowledge knowledge = (Knowledge) knowledgeData.getKnowledge();
        KnowledgeReference knowledgeReference = knowledgeData.getReference();

        long knowledgeId = knowledge.getId();
        short columnId = knowledge.getColumnId();

        //knowledge.createContendDesc();

        Knowledge oldKnowledge = this.knowledgeDao.getByIdAndColumnId(knowledgeId, columnId);

        //知识详细表更新
        Knowledge afterSaveKnowledge = this.knowledgeDao.update(knowledge, userId);

        //知识简表更新
        KnowledgeBase oldKnowledgeBase = this.knowledgeBaseDao.getById(knowledgeId);
        try {
            this.knowledgeBaseDao.update(KnowledgeUtil.convertOldKnowledge(afterSaveKnowledge), userId);
        } catch (Exception e) {
            this.updateRollBack(knowledgeId, columnId,oldKnowledge,null,null, userId, true, false, false, false, false);
            logger.error("知识基础表更新失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //知识来源表更新
        KnowledgeReference afterSaveKnowledgeReference = null;
        KnowledgeReference oldKnowledgeReference = this.knowledgeReferenceDao.getByKnowledgeId(knowledgeId);
        try {
            this.knowledgeReferenceDao.update(knowledgeReference, userId);
        } catch (Exception e) {
            this.updateRollBack(knowledgeId, columnId,oldKnowledge,oldKnowledgeBase,null, userId, true, true, false, false, false);
            logger.error("知识来源表更新失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //大数据MQ推送更新
        try {
            //bigDataService.sendMessage(IBigDataService.KNOWLEDGE_UPDATE, afterSaveKnowledge, userId);
        } catch (Exception e) {
            this.updateRollBack(knowledgeId, columnId,oldKnowledge,oldKnowledgeBase,oldKnowledgeReference, userId, true, true, true, false, false);
            logger.error("知识MQ推送失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //动态推送更新（仅推送观点）
        try {
            //userFeedService.saveOrUpdate(PackingDataUtil.packingSendFeedData(afterSaveKnowledge, user, diaryService));
        } catch (Exception e) {
            this.updateRollBack(knowledgeId, columnId,oldKnowledge,oldKnowledgeBase,oldKnowledgeReference, userId, true, true, true, true, false);
            logger.error("动态推送失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        return InterfaceResult.getSuccessInterfaceResultInstance(getReturn(afterSaveKnowledge,afterSaveKnowledgeReference));
    }

    //@AssoDeleteAnnotation
    @Override
    public InterfaceResult deleteByKnowledgeId(long knowledgeId, short columnId, long userId) throws Exception {

        Knowledge oldKnowledge = this.knowledgeDao.getByIdAndColumnId(knowledgeId, columnId);

        //知识详细表删除
        this.knowledgeDao.deleteByIdAndColumnId(knowledgeId, columnId);

        //知识简表删除
        KnowledgeBase oldKnowledgeBase = this.knowledgeBaseDao.getById(knowledgeId);
        try {
            this.knowledgeBaseDao.deleteById(knowledgeId);
        } catch (Exception e) {
            this.deleteRollBack(knowledgeId, columnId,oldKnowledge,null,null, userId, true, false, false, false, false);
            logger.error("知识基础表删除失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //知识来源表删除
        KnowledgeReference oldKnowledgeReference = this.knowledgeReferenceDao.getByKnowledgeId(knowledgeId);
        try {
            this.knowledgeReferenceDao.deleteById(knowledgeId);
        } catch (Exception e) {
            this.deleteRollBack(knowledgeId, columnId,oldKnowledge,oldKnowledgeBase,null, userId, true, true, false, false, false);
            logger.error("知识来源表删除失败！失败原因：\n"+e.getCause().toString());
            //return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //大数据MQ推送删除
        try {
            //bigDataService.sendMessage(IBigDataService.KNOWLEDGE_DELETE, oldKnowledge, userId);
        } catch (Exception e) {
            this.deleteRollBack(knowledgeId, columnId,oldKnowledge,oldKnowledgeBase,oldKnowledgeReference, userId, true, true, true, false, false);
            logger.error("知识MQ推送失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //动态推送删除（仅推送观点）
        /*
		try {
			//userFeedService.deleteDynamicKnowledge(knowledgeId);
		} catch (Exception e) {
			this.deleteRollBack(knowledgeId, columnId,oldKnowledge,oldKnowledgeBase,oldKnowledgeReference, user, true, true, true, true, false);
			logger.error("动态推送失败！失败原因：\n"+e.getCause().toString());
			return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
		}*/

        return InterfaceResult.getSuccessInterfaceResultInstance(null);
    }

    //@AssoDeleteAnnotation
    @Override
    public InterfaceResult deleteByKnowledgeIds(List<Long> knowledgeIds, short columnId, long userId) throws Exception {

        List<Knowledge> oldKnowledgeList = (List<Knowledge>)this.knowledgeDao.getByIdsAndColumnId(knowledgeIds, columnId);

        //知识详细表删除
        this.knowledgeDao.deleteByIdsAndColumnId(knowledgeIds, columnId);

        //知识简表删除
        List<KnowledgeBase> oldKnowledgeBaseList = this.knowledgeBaseDao.getByIds(knowledgeIds);
        try {
            this.knowledgeBaseDao.deleteByIds(knowledgeIds);
        } catch (Exception e) {
            this.deleteListRollBack(oldKnowledgeList, null, null, userId, true, false, false, false, false);
            logger.error("知识基础表删除失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //知识来源表删除
        List<KnowledgeReference> oldKnowledgeReferenceList = this.knowledgeReferenceDao.getByKnowledgeIds(knowledgeIds);
        try {
            this.knowledgeReferenceDao.deleteByIds(knowledgeIds);
        } catch (Exception e) {
            this.deleteListRollBack(oldKnowledgeList,oldKnowledgeBaseList,null, userId, true, true, false, false, false);
            logger.error("知识来源表删除失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //大数据MQ推送删除
        try {
            bigDataService.sendMessage(IBigDataService.KNOWLEDGE_DELETE, oldKnowledgeList, userId);
        } catch (Exception e) {
            this.deleteListRollBack(oldKnowledgeList,oldKnowledgeBaseList,oldKnowledgeReferenceList, userId, true, true, true, false, false);
            logger.error("知识MQ推送失败！失败原因：\n"+e.getCause().toString());
            return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
        }

        //动态推送删除（仅推送观点）
        /*
		try {
			for(long knowledgeId : knowledgeIds) 
				userFeedService.deleteDynamicKnowledge(knowledgeId);
		} catch (Exception e) {
			this.deleteListRollBack(oldKnowledgeList,oldKnowledgeBaseList,oldKnowledgeReferenceList, user, true, true, true, true, false);
			logger.error("动态推送失败！失败原因：\n"+e.getCause().toString());
			return InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
		}*/

        return InterfaceResult.getSuccessInterfaceResultInstance(null);
    }

    //@AssoGetAnnotation
    @Override
    public KnowledgeData getDetailById(long knowledgeId, short columnId, long userId) throws Exception {

        Knowledge Knowledge = this.knowledgeDao.getByIdAndColumnId(knowledgeId, columnId);

        KnowledgeReference knowledgeReference = this.knowledgeReferenceDao.getById(knowledgeId);

        return getReturn(Knowledge,knowledgeReference);

    }

    @Override
    public KnowledgeData getBaseById(long knowledgeId, long userId) throws Exception {

        KnowledgeBase knowledgeBase = this.knowledgeBaseDao.getById(knowledgeId);

        KnowledgeReference knowledgeReference = this.knowledgeReferenceDao.getById(knowledgeId);

        return getReturn(knowledgeBase,knowledgeReference);
    }

    @Override
    public List<KnowledgeData> getBaseByIds(List<Long> knowledgeIds, long userId) throws Exception {

        List<KnowledgeBase> knowledgeBaseList = this.knowledgeBaseDao.getByIds(knowledgeIds);

        List<KnowledgeReference> knowledgeReferenceList = this.knowledgeReferenceDao.getByIds(knowledgeIds);

        return KnowledgeReference.putReferenceListToKnowledgeList(knowledgeBaseList,knowledgeReferenceList);
    }

    @Override
    public List<KnowledgeData> getBaseAll(int start,int size) throws Exception {

        return getReturn(this.knowledgeBaseDao.getAll(start, size));
    }

    @Override
    public List<KnowledgeData> getBaseByCreateUserId(long userId, int start, int size) throws Exception {

        return getReturn(this.knowledgeBaseDao.getByCreateUserId(userId, start, size));
    }

    @Override
    public List<KnowledgeData> getBaseByCreateUserIdAndColumnId(long userId, short columnId, int start, int size) throws Exception {

        return getReturn(this.knowledgeBaseDao.getByCreateUserIdAndColumnId(userId, columnId, start, size));
    }

    @Override
    public List<KnowledgeData> getBaseByCreateUserIdAndType(long userId, String type, int start, int size) throws Exception {
        return getReturn(this.knowledgeBaseDao.getByCreateUserIdAndType(userId, type, start, size));
    }

    @Override
    public List<KnowledgeData> getBaseByCreateUserIdAndColumnIdAndType(long userId, short columnId, String type, int start, int size) throws Exception {
        return getReturn(this.knowledgeBaseDao.getByCreateUserIdAndTypeAndColumnId(userId, type, columnId, start, size));
    }

    @Override
    public List<KnowledgeData> getBaseByType(String type, int start, int size) throws Exception {
        return getReturn(this.knowledgeBaseDao.getByType(type, start, size));
    }

    @Override
    public List<KnowledgeData> getBaseByColumnId(short columnId, int start, int size) throws Exception {
        return getReturn(this.knowledgeBaseDao.getByColumnId(columnId, start, size));
    }

    @Override
    public List<KnowledgeData> getBaseByColumnIdAndType(short columnId,String type,int start,int size) throws Exception {
        return getReturn(this.knowledgeBaseDao.getByTypeAndColumnId(type, columnId, start, size));
    }

    /**
     * 插入时异常手动回滚方法
     * @author 周仕奇
     * @date 2016年1月15日 上午11:30:18
     * @throws Exception
     */
    private void insertRollBack(long knowledgeId, short columnId, long userId,boolean isMongo,boolean isBase,boolean isReference,boolean isBigData,boolean isUserFeed) throws Exception {
        if(isMongo) this.knowledgeDao.deleteByIdAndColumnId(knowledgeId, columnId);
        if(isBase) this.knowledgeBaseDao.deleteById(knowledgeId);
        if(isReference) this.knowledgeReferenceDao.deleteByKnowledgeId(knowledgeId);
        if(isBigData) this.bigDataService.deleteMessage(knowledgeId, columnId, userId);
        //TODO: check if need or not
        //if(isUserFeed) this.userFeedService.deleteDynamicKnowledge(knowledgeId);
    }

    /**
     * 更新时异常手动回滚方法
     * @author 周仕奇
     * @date 2016年1月15日 上午11:30:54
     * @throws Exception
     */
    private void updateRollBack(long knowledgeId, short columnId,
                                Knowledge oldKnowledge,KnowledgeBase oldKnowledgeBase,KnowledgeReference oldKnowledgeReference, long userId,
                                boolean isMongo,boolean isBase,boolean isReference,boolean isBigData,boolean isUserFeed) throws Exception {
        if(isMongo) this.knowledgeDao.insertAfterDelete(oldKnowledge, knowledgeId, userId);
        if(isBase) this.knowledgeBaseDao.insertAfterDelete(oldKnowledgeBase, userId);
        if(isReference) this.knowledgeReferenceDao.insertAfterDelete(oldKnowledgeReference, knowledgeId, userId);
        if(isBigData) this.bigDataService.sendMessage(IBigDataService.KNOWLEDGE_UPDATE, oldKnowledge, userId);
        //TODO: check if need or not
        //if(isUserFeed) this.userFeedService.saveOrUpdate(PackingDataUtil.packingSendFeedData(oldKnowledge, user, diaryService));
    }

    /**
     * 单条删除时异常手动回滚方法
     * @author 周仕奇
     * @date 2016年1月15日 上午11:31:29
     * @throws Exception
     */
    private void deleteRollBack(long knowledgeId, short columnId,
                                Knowledge oldKnowledge,KnowledgeBase oldKnowledgeBase,KnowledgeReference oldKnowledgeReference, long userId,
                                boolean isMongo,boolean isBase,boolean isReference,boolean isBigData,boolean isUserFeed) throws Exception {
        if(isMongo) this.knowledgeDao.insert(oldKnowledge, userId);
        if(isBase) this.knowledgeBaseDao.insert(oldKnowledgeBase, userId);
        if(isReference) this.knowledgeReferenceDao.insert(oldKnowledgeReference, knowledgeId, userId);
        if(isBigData) this.bigDataService.sendMessage(IBigDataService.KNOWLEDGE_INSERT, oldKnowledge, userId);
        //TODO: check if need or not
        //if(isUserFeed) this.userFeedService.saveOrUpdate(PackingDataUtil.packingSendFeedData(oldKnowledge, userId, diaryService));
    }

    /**
     * 批量删除时异常手动回滚方法
     * @author 周仕奇
     * @date 2016年1月15日 上午11:32:13
     * @throws Exception
     */
    private void deleteListRollBack(List<Knowledge> oldKnowledgeList,List<KnowledgeBase> oldKnowledgeBaseList,List<KnowledgeReference> oldKnowledgeReferenceList, Long userId,
                                    boolean isMongo,boolean isBase,boolean isReference,boolean isBigData,boolean isUserFeed) throws Exception {
        if(isMongo) this.knowledgeDao.insertList(oldKnowledgeList, userId);
        if(isBase) this.knowledgeBaseDao.insertList(oldKnowledgeBaseList, userId);
        if(isReference) this.knowledgeReferenceDao.insertList(oldKnowledgeReferenceList, userId);
        if(isBigData) this.bigDataService.sendMessage(IBigDataService.KNOWLEDGE_INSERT, oldKnowledgeList, userId);
        if(isUserFeed) {
            //for (Knowledge oldKnowledge: oldKnowledgeList)
            //TODO: check if need or not
            //this.userFeedService.saveOrUpdate(PackingDataUtil.packingSendFeedData(oldKnowledge, user, diaryService));
        }

    }

    /**
     * 返回数据包装方法
     * @author 周仕奇
     * @date 2016年1月15日 上午11:32:58
     * @param knowledgeBaseList
     * @return
     */
    private List<KnowledgeData> getReturn(List<KnowledgeBase> knowledgeBaseList) {

        List<KnowledgeData> returnList = new ArrayList<KnowledgeData>();
        if(knowledgeBaseList != null && !knowledgeBaseList.isEmpty())
            for (KnowledgeBase data : knowledgeBaseList)
                returnList.add(getReturn(data,null));

        return returnList;
    }

    /**
     * 返回数据包装方法
     * @author 周仕奇
     * @date 2016年1月15日 上午11:33:16
     * @param knowledge
     * @param knowledgeReference
     * @return
     */
    private KnowledgeData getReturn(Knowledge knowledge, KnowledgeReference knowledgeReference) {

        KnowledgeData KnowledgeData = new KnowledgeData();

        KnowledgeData.setKnowledge(knowledge);

        KnowledgeData.setReference(knowledgeReference);

        return KnowledgeData;
    }

    private KnowledgeData getReturn(KnowledgeBase knowledge, KnowledgeReference knowledgeReference) {

        KnowledgeData KnowledgeData = new KnowledgeData();

        KnowledgeData.setKnowledgeBase(knowledge);

        KnowledgeData.setReference(knowledgeReference);

        return KnowledgeData;
    }
}
