package com.ginkgocap.ywxt.knowledge.service.impl;

import com.ginkgocap.parasol.knowledge.dao.IKnowledgeBaseDao;
import com.ginkgocap.parasol.knowledge.dao.impl.KnowledgeBaseDao;
import com.ginkgocap.parasol.knowledge.model.KnowledgeBase;
import com.ginkgocap.ywxt.knowledge.base.TestBase;
import com.ginkgocap.ywxt.knowledge.testData.DataUtil;
import com.mongodb.util.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chen Peifeng on 2016/3/23.
 */
public class KnowledgeBaseDaoTest extends TestBase {

    /**知识简表*/
    //@Autowired
    private IKnowledgeBaseDao knowledgeBaseDao = new KnowledgeBaseDao();

    private long userId = 123456L;

    public static void main(String[] args) throws Exception {
        new KnowledgeBaseDaoTest().testInsert();
    }

    public void testInsert() {
        KnowledgeBase knowledgeBase = DataUtil.getKnowledgeBaseObject();
        try {
            KnowledgeBase knowledgeBase2 = knowledgeBaseDao.insert(knowledgeBase, userId);
            TestCase.assertEquals(knowledgeBase, knowledgeBase2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testUpadte() {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        try {
            KnowledgeBase knowledgeBase2 = knowledgeBaseDao.update(knowledgeBase, userId);
            TestCase.assertEquals(knowledgeBase, knowledgeBase2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testDeleteByKnowledgeId() {
        try {
            long knowledgeId = 00122L;
            int ret = knowledgeBaseDao.deleteById(knowledgeId);
            TestCase.assertTrue(ret>0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testDeleteByKnowledgeIds() {
        try {
            List<Long> Ids = new ArrayList<Long>();
            int ret = knowledgeBaseDao.deleteByIds(Ids);
            TestCase.assertTrue(ret>0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void getDetailById() {
//        try {
//            List<Long> Ids = new ArrayList<Long>();
//            int ret = knowledgeBaseDao.getde(Ids);
//            TestCase.assertTrue(ret>0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void getBaseById() {

    }

    public void getBaseByIds() {

    }

    public void getBaseAll() {

    }

    public void getBaseByCreateUserId() {

    }

    public void getBaseByCreateUserIdAndColumnId() {

    }

    public void getBaseByCreateUserIdAndType() {

    }

    public void getBaseByCreateUserIdAndColumnIdAndType() {

    }

    public void getBaseByType() {

    }

    public void getBaseByColumnId() {

    }

    public void getBaseByColumnIdAndType() {

    }
}
