package com.ginkgocap.parasol.user.service.impl;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserConfigConnectorServiceException;
import com.ginkgocap.parasol.user.model.UserConfigConnector;
import com.ginkgocap.parasol.user.service.UserConfigConnectorService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xutlong on 2016/5/31.
 */
@Service("userConfigConnectorService")
public class UserConfigConnectorServiceImpl extends BaseService<UserConfigConnector> implements UserConfigConnectorService{

    private static Logger logger = Logger.getLogger(UserConfigConnectorServiceImpl.class);

    @Override
    public List<UserConfigConnector> saveOrUpdateEntitys(Long userId, int type, Long appId, String ids) throws UserConfigConnectorServiceException {

        List<UserConfigConnector> userConfigConnectorList = new ArrayList<UserConfigConnector>();
        List<UserConfigConnector> userConfigConnectorListCount = null;

        try {
            // 首先需要确定当前设置是否存在部分好友可见的记录
            userConfigConnectorListCount = this.getUserConfigConnectors(userId,type,appId);
            // 如果存在 则全部删除 重新记录
            if (null != userConfigConnectorListCount || userConfigConnectorListCount.size() > 0) {
                this.deletes(userConfigConnectorListCount);
            }
            String[] idList = ids.split(",");
            for (int i = 0; i < idList.length; i++) {
                UserConfigConnector u = new UserConfigConnector();
                u.setUserId(userId);
                u.setFriendId(Long.parseLong(idList[i]));
                u.setAppId(appId);
                u.setType(type);
                u.setCtime(System.currentTimeMillis());
                userConfigConnectorList.add(u);
            }
            userConfigConnectorList = this.saveEntitys(userConfigConnectorList);
        } catch (BaseServiceException e) {
            logger.error("add userConfigConnector firends failed!");
            e.printStackTrace();
            throw new UserConfigConnectorServiceException("add userConfigConnector firends failed!");
        }
        return userConfigConnectorList;
    }

    @Override
    public Boolean deletes(List<UserConfigConnector> entitys) throws UserConfigConnectorServiceException {
        try {
            List<Long> ids = new ArrayList<Long>();
            for (UserConfigConnector u : entitys) {
                ids.add(u.getId());
            }
            return this.deleteEntityByIds(ids);
        } catch (BaseServiceException e) {
            logger.error("deletes userConfigConnector for friends failed");
            e.printStackTrace();
            throw new UserConfigConnectorServiceException("deletes userConfigConnector for friends failed");
        }
    }

    @Override
    public List<UserConfigConnector> getUserConfigConnectors(Long userId, int type, Long appId) throws UserConfigConnectorServiceException {
        List<UserConfigConnector> userConfigConnectors = null;
        try {
            userConfigConnectors = this.getEntitys("UserConfigConnector_List_UserId_type", userId, type);
        } catch (BaseServiceException e) {
            logger.error("getUserConfigConnectors failed!");
            e.printStackTrace();
            throw new UserConfigConnectorServiceException("getUserConfigConnectors failed!");
        }
        return userConfigConnectors;
    }

    @Override
    public int deletes(Long userId, int type, Long appId) throws UserConfigConnectorServiceException {
        try {
            return this.deleteList("UserConfigConnector_Delete_UserId_Type",userId, type, appId);
        } catch (BaseServiceException e) {
            logger.error("deletes friend failed!");
            e.printStackTrace();
            throw new UserConfigConnectorServiceException("deletes friend failed!");
        }
    }
}
