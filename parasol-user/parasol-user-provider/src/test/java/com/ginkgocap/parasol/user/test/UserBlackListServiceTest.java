package com.ginkgocap.parasol.user.test;

import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.model.UserBlackList;
import com.ginkgocap.parasol.user.service.UserBlackListService;
import junit.framework.Test;
import junit.framework.TestResult;

import javax.annotation.Resource;

/**
 * Created by xutlong on 2016/5/26.
 */
public class UserBlackListServiceTest extends TestBase implements Test {

    @Resource
    public UserBlackListService userBlackListService;

    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult result) {

    }

    @org.junit.Test
    public void testSave() throws UserBlackListServiceException {
        UserBlackList userBlackList = new UserBlackList();
        userBlackList.setAppId(1L);
        userBlackList.setUserId(123L);
        userBlackList.setBlackUserId(321L);
        userBlackList.setCtime(System.currentTimeMillis());
        Long id = userBlackListService.save(userBlackList);
        System.out.println("创建的黑名单Id------------------------:" + id);
    }
}
