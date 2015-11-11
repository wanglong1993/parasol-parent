package com.ginkgocap.ywxt.metadata.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.model.CodeDy;


/**
 * 地域Service的测试用例
 * @author liu
 * @创建时间：2011-11-30 14:24:22
 */
@Configuration
public class CodeDyServiceTest extends TestBase {
    @Autowired
    private CodeDyService codeDyService;
    private String dyOldName = "北京市";
    private String dyNewName = "上海市";

    @Test
    public void testCodeDyService() {
        CodeDy codeDy = new CodeDy();
        //测试codeDyService的insert方法
        codeDy.setName(dyOldName);
        codeDy.setCtime("2011-11-24 10:31:57");
        CodeDy codeDy1 = codeDyService.insert(codeDy);
        assertEquals(codeDy1.getName(), dyOldName);

        //测试codeDyService的selectAll方法和selectByPrimarKey方法
        List<CodeDy> codeDyList = codeDyService.selectAll();
        assertNotNull(codeDyList);
        CodeDy codeDyempt = codeDyList.get(0);
        CodeDy codeDy0 = codeDyService.selectByPrimarKey(codeDyempt.getId());
        assertEquals(codeDy0.getName(), codeDyempt.getName());
        //测试codeDyService的selectByPrimarKey方法id在数据库中不存在的记录
        CodeDy codeDyF = codeDyService.selectByPrimarKey(111111);
        assertEquals(codeDyF.getId(), 0);

        //测试codeDyService的update方法
        codeDy.setName(dyNewName);
        codeDyService.update(codeDy);
        assertEquals(codeDy1.getName(), dyNewName);

        //测试codeDyService的selectByName方法
        CodeDy codeDyN = codeDyService.selectByName(dyNewName);
        assertEquals(codeDy.getName(), codeDyN.getName());
        //测试codeDyService的delete方法
        codeDyService.delete(codeDy1.getId());
    }
}
