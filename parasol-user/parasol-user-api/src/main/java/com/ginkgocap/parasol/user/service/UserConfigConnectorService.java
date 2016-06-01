package com.ginkgocap.parasol.user.service;

import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.exception.UserConfigConnectorServiceException;
import com.ginkgocap.parasol.user.exception.UserConfigServiceException;
import com.ginkgocap.parasol.user.model.UserConfigConnector;

import java.util.List;

/**
 * Created by xutlong on 2016/5/31.
 * 用户设置部分好友可见的关联表
 */
public interface UserConfigConnectorService {

    /**
     * 保存可见的好友
     * @param ids
     * @return
     * @author wfl
     */
    public List<UserConfigConnector> saveOrUpdateEntitys(Long userId, int type, Long appId, String ids) throws UserConfigConnectorServiceException;

    /**
     * 删除可见的好友
     * @param Entitys
     * @return
     * @author wfl
     */
    public Boolean deletes(List<UserConfigConnector> Entitys) throws UserBlackListServiceException, UserConfigConnectorServiceException;

    /**
     * 获取判断当前用户是否可见
     * @param userId
     * @param type
     * @return
     * @author wfl
     */
    public List<UserConfigConnector> getUserConfigConnectors(Long userId, int type) throws UserConfigServiceException, UserConfigConnectorServiceException;

    /**
     * 根据用户Id和类型删除保存的部分好友
     * @param userId
     * @param type
     * @param appId
     * @return
     */
    public Boolean deletes(Long userId, int type, Long appId);
}
