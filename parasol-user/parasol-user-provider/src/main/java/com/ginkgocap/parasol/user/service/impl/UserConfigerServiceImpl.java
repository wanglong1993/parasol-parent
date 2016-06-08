package com.ginkgocap.parasol.user.service.impl;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserConfigServiceException;
import com.ginkgocap.parasol.user.exception.UserFriendlyServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserConfig;
import com.ginkgocap.parasol.user.model.UserFriendly;
import com.ginkgocap.parasol.user.service.UserConfigConnectorService;
import com.ginkgocap.parasol.user.service.UserConfigerService;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
@Service("userConfigerService")
public class UserConfigerServiceImpl extends BaseService<UserConfig> implements UserConfigerService  {

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserFriendlyService userFriendlyService;
	@Resource
	private UserConfigConnectorService userConfigConnectorService;

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

	@Override
	public Boolean judgeConfig(Long userId, Long toUserId, int type, Long appId) throws UserConfigServiceException {
		// 1、被查看用户是否设置了不被查看的权限
		UserConfig userConfig = this.getUserConfig(toUserId);
		Byte u = 1;
		// 查看主页权限
		if (type == 1) {
			u = userConfig.getHomePageVisible();
			// 是否可以评论
		} else if (type == 2) {
			u = userConfig.getEvaluateVisible();
		}
		if (u == 1) {
			return false;
		} else if (u == 2) {
			return true;
		} else if (u == 3) {
			// 查询当前操作人是否被包含在可查看好友行列
			return userConfigConnectorService.isVisible(userId,toUserId,type,appId);
		} else {
			// 查询当前操作人是否好友
			UserFriendly userFriendly = null;
			try {
				userFriendly = userFriendlyService.getFriendly(toUserId,userId);
				if (userFriendly == null) {
					return false;
				} else {
					return true;
				}
			} catch (UserFriendlyServiceException e) {
				e.printStackTrace();
			}
		}


		// 2、能够查看返回true，不被查看返回false
		return false;
	}
}
