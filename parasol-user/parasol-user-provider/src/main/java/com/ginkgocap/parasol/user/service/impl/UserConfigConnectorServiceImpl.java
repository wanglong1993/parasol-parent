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

        try {
            String[] idList = ids.split(",");
            for (int i = 0; i < idList.length; i++) {
                UserConfigConnector u = new UserConfigConnector();
                u.setUserId(userId);
                u.setFriendId(Long.parseLong(idList[i]));
                u.setAppId(appId);
                u.setCtime(System.currentTimeMillis());
                userConfigConnectorList.add(u);
            }
            this.saveEntitys(userConfigConnectorList);
        } catch (BaseServiceException e) {
            logger.error("add userConfigConnector firends failed!");
            e.printStackTrace();
            throw new UserConfigConnectorServiceException("add userConfigConnector firends failed!");
        }
        return userConfigConnectorList;
    }

    @Override
    public Boolean deletes(List<UserConfigConnector> Entitys) {
        return null;
    }

    @Override
    public List<UserConfigConnector> getUserConfigConnectors(Long userId, int type) {
        return null;
    }
}
