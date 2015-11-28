package com.ginkgocap.parasol.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserFriendlyServiceException;
import com.ginkgocap.parasol.user.model.UserFriendly;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userFriendlyService")
public class UserFriendlyServiceImpl extends BaseService<UserFriendly> implements UserFriendlyService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static int error_friendId_is_null = 1000;	
	private static int error_uesrId_is_null = 1001;	
	private static int error_status_is_error = 1002;
	private static int error_friendId_is_not_exists = 1003;
	private static int error_uesrId_is_not_exists = 1004;	
	private static int error_FriendId_is_not_exists = 1005;	
	private static final String UserFriendly_Map_FriendId = "UserFriendly_Map_FriendId"; 
	private static Logger logger = Logger.getLogger(UserFriendlyServiceImpl.class);
	@Override
	public Long createUserFriendly(UserFriendly userFriendly)throws UserFriendlyServiceException {
		try {
			if (userFriendly.getUserId()==null ||userFriendly.getUserId()<=0l) throw new UserFriendlyServiceException(error_uesrId_is_null,"userId is null or empty.");
			if(userFriendly.getFriendId()==null ||userFriendly.getFriendId()<=0l)throw new UserFriendlyServiceException(error_friendId_is_null, "friendId is null or empty.");
			if(userLoginRegisterService.getUserLoginRegister(userFriendly.getUserId())==null)throw new UserFriendlyServiceException(error_uesrId_is_not_exists,"userId is not exists in UserLogniRegister");
			if(userLoginRegisterService.getUserLoginRegister(userFriendly.getFriendId())==null)throw new UserFriendlyServiceException(error_FriendId_is_not_exists,"friendId is not exists in UserLogniRegister");
			int status=userFriendly.getStatus().intValue();
			if(status!=0 && status!=1)throw new UserFriendlyServiceException(error_status_is_error, "status is null or empty.");
			if(userFriendly.getCtime()==null || userFriendly.getCtime()<=0l) userFriendly.setCtime(System.currentTimeMillis());
			if(userFriendly.getUtime()==null || userFriendly.getUtime()<=0l) userFriendly.setUtime(System.currentTimeMillis());
			return (Long) saveEntity(userFriendly);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserFriendlyServiceException(e);
		}
	}
	@Override
	public boolean updateStatus(Long userId,Long friendId, Byte status)throws UserFriendlyServiceException {
		try {
			if(friendId==null || friendId<=0l)throw new UserFriendlyServiceException(error_friendId_is_null,"friendId is null or empty.");
			if(status.intValue()!=1) throw new UserFriendlyServiceException(error_status_is_error,"status is error,must be equal to one.");
			if(userLoginRegisterService.getUserLoginRegister(userId)==null)throw new UserFriendlyServiceException(error_uesrId_is_not_exists,"userId is not exists in UserLogniRegister");
			if(userLoginRegisterService.getUserLoginRegister(friendId)==null)throw new UserFriendlyServiceException(error_FriendId_is_not_exists,"friendId is not exists in UserLogniRegister");
			Long id =(Long) getMapId(UserFriendly_Map_FriendId, new Object[]{friendId,userId});
			UserFriendly userFriendly= getEntity(id);
			if(userFriendly==null)throw new UserFriendlyServiceException(error_friendId_is_not_exists,"friendId is not exists.");
			if(userFriendly!=null){
				userFriendly.setStatus(status);
			}
			return updateEntity(userFriendly);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserFriendlyServiceException(e);
		}
	}
	@Override
	public Boolean deleteFriendly(Long userId,Long friendId)throws UserFriendlyServiceException {
		try {
			List<Long> list =new ArrayList<Long>();
			Long id =(Long) getMapId(UserFriendly_Map_FriendId, new Object[]{friendId,userId});
			Long id2 =(Long) getMapId(UserFriendly_Map_FriendId, new Object[]{userId,friendId});
			list.add(id);
			list.add(id2);
			deleteEntityByIds(list);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserFriendlyServiceException(e);
		}
		return null;
	}
}
