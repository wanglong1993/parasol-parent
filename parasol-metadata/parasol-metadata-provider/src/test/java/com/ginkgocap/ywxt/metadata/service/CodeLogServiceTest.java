package com.ginkgocap.ywxt.metadata.service;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ginkgocap.parasol.metadata.service.code.CodeLogService;
import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.model.CodeLog;
/**
 * 分词操作日志的service测试
 * @author 窦友
 */
@Configuration
public class CodeLogServiceTest  extends TestBase{
    @Autowired
    private CodeLogService codeLogService;

    @Test
    public void testCodeDao() {
        CodeLog codeLog = new CodeLog();
        codeLog.setSysItemId(-10000023);
        codeLog.setCreateBy("测试");
        codeLog.setCreateById(-23423);
        codeLog.setContent("修改内容23222");
        codeLog = codeLogService.insert(codeLog);
        assertNotNull(codeLog);
        assertTrue(codeLog.getId()>0);
        codeLog = codeLogService.selectByPrimarKey(codeLog.getId());
        assertNotNull(codeLog);
        assertTrue(codeLog.getId()>0);
        List<CodeLog> lt = codeLogService.selectByCodeId(-10000023, 0, 20);
        assertTrue(lt.size()>0);
        long count = codeLogService.countByCodeId(-10000023);
        assertTrue(count>0);
        int c = codeLogService.deleteByCodeId(-10000023);
        assertTrue(c>0);
    }
    
}
