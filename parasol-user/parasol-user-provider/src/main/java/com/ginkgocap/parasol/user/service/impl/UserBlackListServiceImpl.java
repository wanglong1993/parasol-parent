package com.ginkgocap.parasol.user.service.impl;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.exception.UserConfigServiceException;
import com.ginkgocap.parasol.user.model.UserBlackList;
import com.ginkgocap.parasol.user.service.UserBlackListService;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * Created by xutlong on 2016/5/24.
 */
public class UserBlackListServiceImpl extends BaseService implements UserBlackListService {

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
        if (null == userBlack) {
            logger.error("userBlackList can not be null");
            throw new UserBlackListServiceException("userBlackList is can not be null");
        } else {
            try {
                Long id = (Long) saveEntity(checkValidity(userBlack, 0));
                if(!ObjectUtils.isEmpty(id) && id > 0l)
                    return id;
            } catch (BaseServiceException e) {
                if (logger.isDebugEnabled()) {
                    e.printStackTrace(System.err);
                }
                throw new UserBlackListServiceException(e);
            }
        }

        return 0;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Map<String, Object> getBlackListPageUtilByUserId(long userId, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public boolean isBlackUser(long blackUserId, long userId) {
        return false;
    }

    @Override
    public boolean isBlackRelation(long userId, long toUserId) {
        return false;
    }

    @Override
    public void deleteUserBlack(long userId, String blackUserId) {

    }

    @Override
    public void saveUserBlack(long userId, String blackUserId) {

    }

    @Override
    public UserBlackList getUserBlackList(Long id) {
        return null;
    }
}
