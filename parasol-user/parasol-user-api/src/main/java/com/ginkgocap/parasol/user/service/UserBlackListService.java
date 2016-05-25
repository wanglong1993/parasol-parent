package com.ginkgocap.parasol.user.service;

import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.exception.UserConfigServiceException;
import com.ginkgocap.parasol.user.model.UserBlackList;

import java.util.Map;

/**
 * Created by xutlong on 2016/5/24.
 * 用户黑名单表
 */
public interface UserBlackListService {
    public long save(UserBlackList userBlack) throws UserBlackListServiceException, UserConfigServiceException;

    public void delete(long id);

    /**得到自己的黑名单列表
     * @param userId
     * @return
     * @author wfl
     */
    public Map<String,Object> getBlackListPageUtilByUserId(long userId, int pageIndex, int pageSize);

    /**
     * blackUserId 是否在userId的拉黑列表中
     * @param blackUserId 被拉黑用户
     * @param userId 用户
     * @return
     * @author wfl
     */
    public boolean isBlackUser(long blackUserId ,long userId);

    /**
     * 判断用户userId和toUserId是否存在黑名单关系
     * @param userId
     * @param toUserId
     * @return
     * @author wfl
     */
    public boolean isBlackRelation(long userId ,long toUserId);

    /**
     * 把用户blackUserId 从 用户为userId的黑名单中移出
     * @param userId
     * @param blackUserId 多个id，以','隔开
     * @author wfl
     */
    public void deleteUserBlack(long userId ,String blackUserId);

    /**
     * 把用户blackUserId 加入用户为userId的黑名单中
     * @param userId
     * @param blackUserId 多个id，以','隔开
     * @author wfl
     */
    public void saveUserBlack(long userId ,String blackUserId);

    /**
     * 根据用户Id获取黑名单
     * @date 2016-05-24
     * @author xutlong
     * @param id
     * @return
     */
    public UserBlackList getUserBlackList(Long id);
}
