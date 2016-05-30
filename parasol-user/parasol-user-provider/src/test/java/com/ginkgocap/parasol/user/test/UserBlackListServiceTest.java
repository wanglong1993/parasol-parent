package com.ginkgocap.parasol.user.test;

import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.model.UserBlackList;
import com.ginkgocap.parasol.user.service.UserBlackListService;
import junit.framework.Test;
import junit.framework.TestResult;

import javax.annotation.Resource;
import java.util.List;

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
        userBlackList.setBlackUserId(3121626L);
        userBlackList.setCtime(System.currentTimeMillis());
        Long id = userBlackListService.save(userBlackList);
        System.out.println("创建的黑名单Id------------------------:" + id);
    }

    @org.junit.Test
    public void testDelete() throws Exception {
        Boolean b = userBlackListService.delete(3979405743620102L);
        System.out.println(b);
    }

    @org.junit.Test
    public void testGetBlackListPageUtilByUserId() throws UserBlackListServiceException {
        List<UserBlackList> l = userBlackListService.getBlackListPageUtilByUserId(123L,1,3,1L);
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i).getId());
        }
    }

    @org.junit.Test
    public void testIsBlackUser() throws UserBlackListServiceException {
        System.out.println(userBlackListService.isBlackUser(326676L,123L,1L));
    }

    @org.junit.Test
    public void testDeleteUserBlack() throws UserBlackListServiceException {
        userBlackListService.deleteUserBlack("3979413565997066,3979413637300232,3979438903787525");
    }

    @org.junit.Test
    public void testGetUserBlackList() throws UserBlackListServiceException {
        UserBlackList blackList = userBlackListService.getUserBlackList(3979438585020424L);
        System.out.println("id:" + blackList.getId());
    }

    @org.junit.Test
    public void testGetUserBlackListCount() throws UserBlackListServiceException {
        System.out.println(userBlackListService.getUserBlackListCount(123L));
    }
}
