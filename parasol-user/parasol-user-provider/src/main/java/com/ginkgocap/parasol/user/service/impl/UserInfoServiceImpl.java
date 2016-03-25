package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserInfoServiceException;
import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.service.UserInfoService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static Logger logger = Logger.getLogger(UserInfoServiceImpl.class);
	/**
	 * 检查数据
	 * @param userInfo
	 * @return
	 * @throws UserInfoServiceException
	 */
	private UserInfo checkValidity(UserInfo userInfo,int type)throws UserInfoServiceException,UserLoginRegisterServiceException {
		if(userInfo==null) throw new UserInfoServiceException("userInfo can not be null.");
		if(userInfo.getUserId()<=0l) throw new UserInfoServiceException("The value of userId is null or empty.");
		if(type!=0)
		if(getUserInfo(userInfo.getUserId())==null)throw new UserInfoServiceException("userId not exists in userInfo");
		if(userInfo.getCtime()==null) userInfo.setCtime(System.currentTimeMillis());
		if(userInfo.getUtime()==null) userInfo.setUtime(System.currentTimeMillis());
		if(type==1)userInfo.setUtime(System.currentTimeMillis());
		return userInfo;
	}
	
	@Override
	public Long createUserInfo(UserInfo userInfo)throws UserInfoServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(userInfo,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserInfoServiceException("createUserInfo failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInfoServiceException(e);
		}
	}

	@Override
	public boolean updateUserInfo(UserInfo userInfo)throws UserInfoServiceException,UserLoginRegisterServiceException {
		try {
			if(updateEntity(checkValidity(userInfo,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInfoServiceException(e);
		}
	}

	@Override
	public UserInfo getUserInfo(Long id) throws UserInfoServiceException {
		try {
			if(id==null || id<=0l)throw new UserInfoServiceException("id is null or empty");
			UserInfo userInfo =getEntity(id);
			return userInfo;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInfoServiceException(e);
		}
	}

	@Override
	public List<UserInfo> getUserInfo(List<Long> ids)throws UserInfoServiceException {
		try {
			if(ids==null || ids.size()==0)throw new UserInfoServiceException("userIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInfoServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserInfo(Long id)throws UserInfoServiceException {
		try {
			if(id==null || id<=0l) throw new UserInfoServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInfoServiceException(e);
		}
	}
}
