package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserFriendlyServiceException;
import com.ginkgocap.parasol.user.exception.UserOrgPerCusRelServiceException;
import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrgPerCusRelService;
@Service("userOrgPerCusRelService")
public class UserOrgPerCusRelServiceImpl extends BaseService<UserOrgPerCusRel> implements UserOrgPerCusRelService {
	private static int error_friendId_is_null=1001;
	private static int error_userId_is_null=1000;
	private static int error_releationType_is_error=1002;
	private static int error_friendId_is_not_exists=1003;
	private static int error_start=1004;
	private static int error_count=1005;
	private static int error_uesrId_is_not_exists = 1006;	
	private static int error_FriendId_is_not_exists = 1007;	
	@Resource
	private UserBasicService userBasicService;
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	//dao const
	private static final String UserOrgPerCusRel_UserAndOrg_Friendly_UserId = "UserOrgPerCusRel_UserAndOrg_Friendly_UserId"; 
	private static final String UserOrgPerCusRel_Org_Friendly_UserId = "UserOrgPerCusRel_Org_Friendly_UserId"; 
	private static final String UserOrgPerCusRel_User_Friendly_UserId = "UserOrgPerCusRel_User_Friendly_UserId"; 
	private static final String UserOrgPerCusRel_Map_FriendId = "UserOrgPerCusRel_Map_FriendId"; 
	private static Logger logger = Logger.getLogger(UserOrgPerCusRelServiceImpl.class);
	
	public Long createUserOrgPerCusRel(UserOrgPerCusRel UserOrgPerCusRel) throws UserOrgPerCusRelServiceException {
		try {
			if (UserOrgPerCusRel.getUserId()==null ||UserOrgPerCusRel.getUserId()<=0l) throw new UserOrgPerCusRelServiceException(error_userId_is_null,"userId is null or empty.");
			if(UserOrgPerCusRel.getFriendId()==null ||UserOrgPerCusRel.getFriendId()<=0l)throw new UserOrgPerCusRelServiceException(error_friendId_is_null, "friendId is null or empty.");
			int rt=UserOrgPerCusRel.getReleationType().intValue();
			if(rt!=1 && rt!=2  && rt!=3 && rt!=4 && rt!=5 && rt!=6 && rt!=7 && rt!=8)throw new UserOrgPerCusRelServiceException(error_releationType_is_error, "releationType is null or empty.");
			if(UserOrgPerCusRel.getCtime()==null || UserOrgPerCusRel.getCtime()<=0l) UserOrgPerCusRel.setCtime(System.currentTimeMillis());
			if(UserOrgPerCusRel.getUtime()==null || UserOrgPerCusRel.getUtime()<=0l) UserOrgPerCusRel.setUtime(System.currentTimeMillis());
			//如果备注名为空将其设置为用户名字
			if(StringUtils.isEmpty(UserOrgPerCusRel.getName())) UserOrgPerCusRel.setName(userBasicService.getUserBasic(UserOrgPerCusRel.getUserId()).getName());
			return (Long) saveEntity(UserOrgPerCusRel);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrgPerCusRelServiceException(e);
		}
	}

	public boolean updateName(Long userId,Long friendId,String name) throws UserOrgPerCusRelServiceException {
		try {
			if(friendId==null || friendId<=0l)throw new UserOrgPerCusRelServiceException(error_friendId_is_null,"friendId is null or empty.");
			if(userId==null || userId<=0l)throw new UserOrgPerCusRelServiceException(error_userId_is_null,"userId is null or empty.");
			if(StringUtils.isEmpty(name)) throw new UserOrgPerCusRelServiceException(error_friendId_is_null,"friendId is null or empty.");
			if(userLoginRegisterService.getUserLoginRegister(userId)==null)throw new UserFriendlyServiceException(error_uesrId_is_not_exists,"userId is not exists in UserLogniRegister");
			if(userLoginRegisterService.getUserLoginRegister(friendId)==null)throw new UserFriendlyServiceException(error_FriendId_is_not_exists,"friendId is not exists in UserLogniRegister");
			Long id =(Long) getMapId(UserOrgPerCusRel_Map_FriendId, new Object[]{friendId,userId});
			UserOrgPerCusRel userOrgPerCusRel= getEntity(id);
			if(userOrgPerCusRel==null)throw new UserOrgPerCusRelServiceException(error_friendId_is_not_exists,"friendId is not exists.");
			if(userOrgPerCusRel!=null){
				userOrgPerCusRel.setName(name);
			}
			return updateEntity(userOrgPerCusRel);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrgPerCusRelServiceException(e);
		}
	}
	@Override
	public List<UserOrgPerCusRel> getUserAndOrgFriendlyList(int start,int count,Long userId)throws UserOrgPerCusRelServiceException {
		try {
			if(start <0)throw new UserOrgPerCusRelServiceException(error_start,"start must be greater than zero.");
			if(count <=0)throw new UserOrgPerCusRelServiceException(error_count,"count must be greater than zero.");
			return getEntityByIds(getIds(UserOrgPerCusRel_UserAndOrg_Friendly_UserId, start, count, new Object[]{userId}));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrgPerCusRelServiceException(e.getMessage());
		}
	}
	@Override
	public List<UserOrgPerCusRel> getOrgFriendlylList(int start,int count,Long userId)throws UserOrgPerCusRelServiceException {
		try {
			return getEntityByIds(getIds(UserOrgPerCusRel_Org_Friendly_UserId, start, count, new Object[]{userId}));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrgPerCusRelServiceException(e);
		}
	}
	@Override
	public List<UserOrgPerCusRel> getUserFriendlyList(int start,int count,Long userId)throws UserOrgPerCusRelServiceException {
		try {
			return getEntityByIds(getIds(UserOrgPerCusRel_User_Friendly_UserId, start, count, new Object[]{userId}));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrgPerCusRelServiceException(e);
		}
	}

	@Override
	public Boolean deleteFriendly(Long friendId)throws UserOrgPerCusRelServiceException {
		try {
			Long id =(Long) getMapId(UserOrgPerCusRel_Map_FriendId, friendId);
			deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrgPerCusRelServiceException(e);
		}
		return null;
	}

	@Override
	public UserOrgPerCusRel getUserOrgPerCusRel(Long userId, Long friendId)throws UserOrgPerCusRelServiceException {
		try {
			if(friendId==null || friendId<=0l)throw new UserOrgPerCusRelServiceException(error_friendId_is_null,"friendId is null or empty.");
			if(userId==null || userId<=0l)throw new UserOrgPerCusRelServiceException(error_userId_is_null,"userId is null or empty.");
			if(userLoginRegisterService.getUserLoginRegister(userId)==null)throw new UserFriendlyServiceException(error_uesrId_is_not_exists,"userId is not exists in UserLogniRegister");
			if(userLoginRegisterService.getUserLoginRegister(friendId)==null)throw new UserFriendlyServiceException(error_FriendId_is_not_exists,"friendId is not exists in UserLogniRegister");
			Long id =(Long) getMapId(UserOrgPerCusRel_Map_FriendId, new Object[]{friendId,userId});
			return getEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrgPerCusRelServiceException(e);
		}
	}
	
}
