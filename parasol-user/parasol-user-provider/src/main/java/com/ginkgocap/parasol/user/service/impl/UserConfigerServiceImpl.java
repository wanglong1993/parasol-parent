package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserConfigServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserConfig;
import com.ginkgocap.parasol.user.service.UserConfigerService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userConfigerService")
public class UserConfigerServiceImpl extends BaseService<UserConfig> implements UserConfigerService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static Logger logger = Logger.getLogger(UserConfigerServiceImpl.class);
	/**
	 * 检查数据
	 * @param userConfig
	 * @return
	 * @throws UserConfigServiceException
	 */
	private UserConfig checkValidity(UserConfig userConfig,int type)throws UserConfigServiceException,UserLoginRegisterServiceException {
		if(userConfig==null) throw new UserConfigServiceException("userConfig can not be null.");
		if(userConfig.getUserId()<=0l) throw new UserConfigServiceException("The value of userId is null or empty.");
		if(type!=0)
		if(getUserConfig(userConfig.getUserId())==null)throw new UserConfigServiceException("id not exists in userConfig");
		if(userConfig.getCtime()==null) userConfig.setCtime(System.currentTimeMillis());
		if(userConfig.getUtime()==null) userConfig.setUtime(System.currentTimeMillis());
		if(type==1)userConfig.setUtime(System.currentTimeMillis());
		return userConfig;
	}
	
	@Override
	public Long createUserConfig(UserConfig userConfig)throws UserConfigServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(userConfig,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserConfigServiceException("createUserConfig failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserConfigServiceException(e);
		}
	}

	@Override
	public boolean updateUserConfig(UserConfig userConfig)throws UserConfigServiceException,UserLoginRegisterServiceException {
		try {
			if(updateEntity(checkValidity(userConfig,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserConfigServiceException(e);
		}
	}

	@Override
	public UserConfig getUserConfig(Long id) throws UserConfigServiceException {
		try {
			if(id==null || id<=0l)throw new UserConfigServiceException("id is null or empty");
			UserConfig userConfig =getEntity(id);
			return userConfig;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserConfigServiceException(e);
		}
	}

	@Override
	public List<UserConfig> getUserConfig(List<Long> ids)throws UserConfigServiceException {
		try {
			if(ids==null || ids.size()==0)throw new UserConfigServiceException("userIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserConfigServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserConfig(Long id)throws UserConfigServiceException {
		try {
			if(id==null || id<=0l) throw new UserConfigServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserConfigServiceException(e);
		}
	}
}
