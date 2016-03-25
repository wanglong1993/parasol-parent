package com.ginkgocap.parasol.knowledge.dao.impl;

import com.ginkgocap.parasol.knowledge.dao.IKnowledgeMongoV1;
import com.ginkgocap.parasol.knowledge.model.ColumnSys;
import com.ginkgocap.parasol.knowledge.model.Knowledge;
import com.ginkgocap.parasol.knowledge.model.KnowledgeUtil;
import com.ginkgocap.parasol.knowledge.utils.DateUtil;
import com.mongodb.WriteResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Admin on 2016/3/24.
 */
@Repository("knowledgeMongo")
public class KnowledgeMongoV1 implements IKnowledgeMongoV1 {

    private Logger logger = LoggerFactory.getLogger(KnowledgeMongoV1.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Knowledge insert(Knowledge knowledge, long userId) throws Exception {

        if (knowledge == null || knowledge.getId() <= 0)
            return null;

        String currentDate = DateUtil.formatWithYYYYMMDDHHMMSS(new Date());

        if (knowledge.getCId() <= 0) {
            knowledge.setCId(userId);
        }
        //TODO: check if the data is validate or not
        if (knowledge.getCreatetime() == null) {
            knowledge.setCreatetime(currentDate);
        }
        knowledge.setUId(userId);
        knowledge.setModifytime(currentDate);

        String currCollectionName = knowledge.getClass().getSimpleName(); //getCollectionName(knowledge.getColumnId(),collectionName);

        mongoTemplate.insert(knowledge, currCollectionName);

        return knowledge; //this.getByIdAndColumnId(knowledge.getId(),knowledge.getColumnId(),currCollectionName);
    }

    @Override
    public List<Knowledge> insertList(List<Knowledge> knowledgeList, long userId) throws Exception {

        if (knowledgeList == null || knowledgeList.size() <= 0) {
            return null;
        }

        for (Knowledge knowledge : knowledgeList) {
            if (knowledge != null && knowledge.getUId() <= 0) {
                knowledge.setUId(userId);
            }
        }
        mongoTemplate.insert(knowledgeList, knowledgeList.get(0).getClass().getSimpleName());

        return knowledgeList;
    }

    @Override
    public Knowledge update(Knowledge knowledge, long userId) throws Exception {

        if (knowledge == null)
            return null;

        long knowledgeId = knowledge.getId();

        if (knowledgeId <= 0) {
            return this.insert(knowledge, userId);
        }

        String currentDate = DateUtil.formatWithYYYYMMDDHHMMSS(new Date());

        //knowledge.setModifytime(userId);
        knowledge.setModifytime(currentDate);

        Criteria criteria = Criteria.where("_id").is(knowledgeId);
        Query query = new Query(criteria);

        String currCollectionName = knowledge.getClass().getSimpleName();//getCollectionName(Knowledge.getColumnId(),collectionName);

        WriteResult result = mongoTemplate.updateFirst(query, getUpdate(knowledge, userId), currCollectionName);

        return knowledge; // this.getByIdAndColumnId(knowledgeId,Knowledge.getColumnId(),currCollectionName);
    }

    @Override
    public Knowledge insertAfterDelete(Knowledge knowledge, long knowledgeId, long userId) throws Exception {

        String currCollectionName = knowledge.getClass().getSimpleName();//getCollectionName(knowledge.getColumnId(),collectionName);

        if (knowledge == null || knowledgeId <= 0)
            return null;

        Knowledge oldValue = this.getByIdAndColumnId(knowledgeId, knowledge.getColumnId());
        if (oldValue == null) {
            logger.error("Can't find the old knowledge. userId: " + userId + " knowledgeId: " + knowledgeId);
        }

        this.deleteByIdAndColumnId(knowledgeId, knowledge.getColumnId());

        try {
            this.insert(knowledge, userId);
        } catch (Exception e) {
            if (oldValue != null)
                this.insert(oldValue, userId);
            throw e;
        }


        return this.getByIdAndColumnId(knowledgeId, knowledge.getColumnId());
    }

    @Override
    public int deleteByIdAndColumnId(long id, short columnId) throws Exception {

        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);

        String collectionName = KnowledgeUtil.getClassByColumnId(columnId).getSimpleName();
        if (collectionName == null) {
            return -1;
        }

        mongoTemplate.remove(query, collectionName);

        return 0;
    }

    @Override
    public int deleteByIdsAndColumnId(List<Long> ids, short columnId) throws Exception {

        Criteria criteria = Criteria.where("_id").in(ids);
        Query query = new Query(criteria);

        String collectionName = getCollectionName(columnId, null);
        if (collectionName == null) {
            logger.error("Can't delete knowledges, because of invaliade");
        }

        mongoTemplate.remove(query, getCollectionName(columnId, collectionName));

        return 0;
    }

    @Override
    public int deleteByCreateUserIdAndColumnId(long createUserId, short columnId) throws Exception {

        Criteria criteria = Criteria.where("createUserId").is(createUserId);
        Query query = new Query(criteria);

        String collectionName = getCollectionName(columnId, null);
        if (collectionName == null) {
            return -1;
        }

        mongoTemplate.remove(query, collectionName);

        return 0;
    }

    @Override
    public Knowledge getByIdAndColumnId(long id, short columnId) throws Exception {

        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        Class<? extends Knowledge> knowledgeClass = KnowledgeUtil.getClassByColumnId(columnId);
        if (knowledgeClass == null) {
            return null;
        }

        return mongoTemplate.findOne(query, knowledgeClass, knowledgeClass.getSimpleName());

    }

    @Override
    public List<? extends Knowledge> getByIdsAndColumnId(List<Long> ids, short columnId) throws Exception {

        Criteria criteria = Criteria.where("_id").in(ids);
        Query query = new Query(criteria);
        Class<? extends Knowledge> knowledgeClass = KnowledgeUtil.getClassByColumnId(columnId);
        if (knowledgeClass == null) {
            return null;
        }

        return mongoTemplate.find(query, knowledgeClass, knowledgeClass.getSimpleName());

    }

    private String getCollectionName(long columnId) throws Exception {

        StringBuffer strBuf = new StringBuffer();

        strBuf.append(KNOWLEDGE_COLLECTION_NAME);

        //从缓存中获取系统栏目
        List<ColumnSys> columnSysList = new ArrayList<ColumnSys>();

        Iterator<ColumnSys> it = columnSysList.iterator();

        boolean columnCodeNotExistflag = true;

        while (it.hasNext()) {
            ColumnSys columnSys = it.next();
            if (columnId == columnSys.getId()) {
                if (StringUtils.isEmpty(columnSys.getColumnCode())) {
                    break;
                }
                columnCodeNotExistflag = false;
                strBuf.append(columnSys.getColumnCode());
                break;
            }
        }

        if (columnCodeNotExistflag) {
            strBuf.append(KNOWLEDGE_COLLECTION_USERSELF_NAME);
        }

        return strBuf.toString();

    }

//    private String getCollectionName(long columnId, String[] collectionName) throws Exception {
//        return ArrayUtils.isEmpty(collectionName) && StringUtils.isEmpty(collectionName[0]) ? getCollectionName(columnId) : collectionName[0];
//    }

    private String getCollectionName(short columnId, String collectionName) {
        if (collectionName == null || collectionName.trim().length() > 0) {
            Class<? extends Knowledge> knowledgeClass = KnowledgeUtil.getClassByColumnId(columnId);
            if (knowledgeClass == null) {
                return knowledgeClass.getSimpleName();
            }
        }
        return collectionName;
    }

    private Update getUpdate(Knowledge knowledge, Long userId) {

        //构建更新字段，目前默认是全字段更新
        Update update = new Update();

        JSONObject json = JSONObject.fromObject(knowledge);

        Iterator<String> it = json.keys();

        while (it.hasNext()) {
            String key = it.next();
            update.update(key, json.get(key));
        }

        return update;

    }

}
