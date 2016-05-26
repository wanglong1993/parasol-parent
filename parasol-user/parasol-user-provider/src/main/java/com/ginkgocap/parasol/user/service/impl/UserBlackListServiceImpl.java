package com.ginkgocap.parasol.user.service.impl;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.model.UserBlackList;
import com.ginkgocap.parasol.user.service.UserBlackListService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xutlong on 2016/5/24.
 */
@Service("userBlackListService")
public class UserBlackListServiceImpl extends BaseService<UserBlackList> implements UserBlackListService {

    private static Logger logger = Logger.getLogger(UserBlackListServiceImpl.class);

    @Override
    public Class getEntityClass() {
        return super.getEntityClass();
    }

    private UserBlackList checkValidity(UserBlackList userBlackList, int type) throws UserBlackListServiceException {
        if (userBlackList == null)
            throw new UserBlackListServiceException("userBlackList can not be null.");
        if (userBlackList.getUserId() <= 0l)
            throw new UserBlackListServiceException("The value of userId is null or empty.");
        if (type != 0)
            if(getUserBlackList(userBlackList.getUserId()) == null)
                throw new UserBlackListServiceException("id not exists in userBalckList");
        if (userBlackList.getCtime() == null)
            userBlackList.setCtime(System.currentTimeMillis());
        if (userBlackList.getUtime() == null)
            userBlackList.setUtime(System.currentTimeMillis());
        if (type == 1)
            userBlackList.setUtime(System.currentTimeMillis());
        return userBlackList;
    }

    @Override
    public long save(UserBlackList userBlack) throws UserBlackListServiceException {
        try {
            Long id = (Long) saveEntity(checkValidity(userBlack, 0));
            if (!ObjectUtils.isEmpty(id) && id > 0l) {
                return id;
            } else {
                throw new UserBlackListServiceException("creat userBlackList failed!");
            }
        } catch (BaseServiceException e) {
            if (logger.isDebugEnabled()) {
                e.printStackTrace(System.err);
            }
            throw new UserBlackListServiceException(e);
        }
    }

    @Override
    public Boolean delete(Long id) throws Exception{
        if (id == null || id <= 0l)
            throw new Exception("id is null or empty");
        return this.deleteEntity(id);
    }

    @Override
    public List<UserBlackList> getBlackListPageUtilByUserId(long userId, int pageIndex, int pageSize, Long appId) throws UserBlackListServiceException {
        List<UserBlackList> userBlackListLists = null;
        try {
            userBlackListLists = this.getSubEntitys("UserBlack_List_UserId",pageIndex,pageSize,userId,appId);
        } catch (BaseServiceException e) {
            logger.error("get userBlackList failed");
            e.printStackTrace();
            throw new UserBlackListServiceException("get userBlackList failed");
        }
        return userBlackListLists;
    }

    @Override
    public boolean isBlackUser(long blackUserId, long userId, long appId) throws UserBlackListServiceException {
        try {
            List<UserBlackList> userBlackLists = this.getEntitys("UserBlack_List_UserId_BlackListId",userId,blackUserId,appId);
            return userBlackLists.size() > 0 ? false : true;
        } catch (BaseServiceException e) {
            logger.error("isBlackList is failed!");
            if (logger.isDebugEnabled()) {
                e.printStackTrace(System.err);
            }
            throw new UserBlackListServiceException(e);
        }

    }

    /*
    @Override
    public boolean isBlackRelation(long userId, long toUserId) {
        return false;
    }*/

    @Override
    public Boolean deleteUserBlack(String ids) throws UserBlackListServiceException {
        try {
            List<Long> ls = new ArrayList<Long>();
            if (!ObjectUtils.isEmpty(ids)) {
                // 传递的需要批量删除的id 用String传递，中间用逗号分隔
                String[] idList = ids.split(",");
                for (int i = 0; i < idList.length; i++) {
                    ls.add(Long.valueOf(idList[i]));
                }
            }
            return this.deleteEntityByIds(ls);
        } catch (BaseServiceException e) {
            e.printStackTrace();
            throw new UserBlackListServiceException("Batch delete BlackLists failed!");
        }
    }

    @Override
    public UserBlackList getUserBlackList(Long id) throws UserBlackListServiceException {
        try {
            UserBlackList blackLidt = (UserBlackList)this.getEntity(id);
            return blackLidt;
        } catch (BaseServiceException e) {
            e.printStackTrace();
            logger.error("get balcklist failed!");
            throw new UserBlackListServiceException("get balcklist failed!");
        }

    }
}
