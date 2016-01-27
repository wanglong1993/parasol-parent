package com.ginkgocap.parasol.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.message.service.MessageEntityService;
import com.ginkgocap.parasol.user.exception.UserFriendlyServiceException;
import com.ginkgocap.parasol.user.model.UserFriendly;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrgPerCusRelService;
@Service("userFriendlyService")
public class UserFriendlyServiceImpl extends BaseService<UserFriendly> implements UserFriendlyService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserOrgPerCusRelService userOrgPerCusRelService;
	@Resource
	private MessageEntityService messageEntityService;
	private static int error_friendId_is_null = 1000;	
	private static int error_uesrId_is_null = 1001;	
	private static int error_status_is_error = 1002;
	private static int error_friendId_is_not_exists = 1003;
	private static int error_uesrId_is_not_exists = 1004;	
	private static int error_FriendId_is_not_exists = 1005;	
	private static final String UserFriendly_Map_FriendId = "UserFriendly_Map_FriendId"; 
	private static Logger logger = Logger.getLogger(UserFriendlyServiceImpl.class);
	@Override
	public Long createUserFriendly(UserFriendly userFriendly,boolean isSendMessage)throws UserFriendlyServiceException {
		Long id=0l;  
		try {
			if (userFriendly.getUserId()==null ||userFriendly.getUserId()<=0l) throw new UserFriendlyServiceException(error_uesrId_is_null,"userId is null or empty.");
			if(userFriendly.getFriendId()==null ||userFriendly.getFriendId()<=0l)throw new UserFriendlyServiceException(error_friendId_is_null, "friendId is null or empty.");
			if(userLoginRegisterService.getUserLoginRegister(userFriendly.getUserId())==null)throw new UserFriendlyServiceException(error_uesrId_is_not_exists,"userId is not exists in UserLogniRegister");
			if(userLoginRegisterService.getUserLoginRegister(userFriendly.getFriendId())==null)throw new UserFriendlyServiceException(error_FriendId_is_not_exists,"friendId is not exists in UserLogniRegister");
			int status=userFriendly.getStatus().intValue();
			if(status!=0 && status!=1)throw new UserFriendlyServiceException(error_status_is_error, "status must be 0 or 1.");
			if(userFriendly.getCtime()==null || userFriendly.getCtime()<=0l) userFriendly.setCtime(System.currentTimeMillis());
			if(userFriendly.getUtime()==null || userFriendly.getUtime()<=0l) userFriendly.setUtime(System.currentTimeMillis());
			id=(Long) saveEntity(userFriendly);
			if(id==null) return null;
			if(isSendMessage){
				Map<String,String> map =new HashMap<String,String>();
				map.put("type", "1");
				map.put("createrId", userFriendly.getUserId().toString());
				map.put("content", userFriendly.getContent());
				map.put("sourceId", id.toString());
				map.put("appId", userFriendly.getAppId().toString());
				map.put("sourceType", "");
				map.put("sourceTitle", "");
				map.put("receiverIds", userFriendly.getFriendId().toString());
				Map<String, Object> result=messageEntityService.insertMessageByParams(map);
				if(!result.get("result").equals("true")){
					realDeleteUserFriendly(id);
					return null;
				}
			}
			return id;
		} catch (Exception e) {
			if(id!=null || id>0l)realDeleteUserFriendly(id);
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
			if(status.intValue()!=1) throw new UserFriendlyServiceException(error_status_is_error,"status must be equal to 1.");
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
			if(id!=null)list.add(id);
			if(id2!=null)list.add(id2);
			if(list.size()>0){
				return deleteEntityByIds(list)==true?true:false;
			}else{
				return true;
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserFriendlyServiceException(e);
		}
	}
	@Override
	public boolean realDeleteUserFriendly(Long id)throws UserFriendlyServiceException {
		try {
			if(id==null || id<=0l) return false;
			return deleteEntity(id);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserFriendlyServiceException(e);
		}
	}
	@Override
	public UserFriendly getFriendly(Long userId, Long friendId)throws UserFriendlyServiceException {
		try {
			Long id =(Long) getMapId(UserFriendly_Map_FriendId, new Object[]{userId,friendId});
			if(id!=null) return getEntity(id);
			else return null;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserFriendlyServiceException(e);
		}
	}
}
