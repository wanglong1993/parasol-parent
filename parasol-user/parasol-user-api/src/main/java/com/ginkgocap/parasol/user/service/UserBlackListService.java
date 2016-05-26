package com.ginkgocap.parasol.user.service;

import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.model.UserBlackList;

import java.util.List;

/**
 * Created by xutlong on 2016/5/24.
 * 用户黑名单表
 */
public interface UserBlackListService {
    public long save(UserBlackList userBlack) throws UserBlackListServiceException;

    public Boolean delete(Long id) throws Exception;

    /**得到自己的黑名单列表
     * @param userId
     * @return
     * @author wfl
     */
    public List<UserBlackList> getBlackListPageUtilByUserId(long userId, int pageIndex, int pageSize, Long appId) throws UserBlackListServiceException;

    /**
     * blackUserId 是否在userId的拉黑列表中
     * @param blackUserId 被拉黑用户
     * @param userId 用户
     * @return
     * @author wfl
     */
    public boolean isBlackUser(long blackUserId ,long userId, long appId) throws UserBlackListServiceException;

    /**
     * 判断用户userId和toUserId是否存在黑名单关系
     * @param userId
     * @param toUserId
     * @return
     * @author wfl
     */
    /*
    public boolean isBlackRelation(long userId ,long toUserId);*/

    /**
     * 把用户blackUserId 从 用户为userId的黑名单中移出
     * @param ids
     * @author wfl
     */
    public Boolean deleteUserBlack(String ids) throws UserBlackListServiceException;

    /**
     * 根据用户Id获取黑名单
     * @date 2016-05-24
     * @author xutlong
     * @param id
     * @return
     */
    public UserBlackList getUserBlackList(Long id) throws UserBlackListServiceException;
}
