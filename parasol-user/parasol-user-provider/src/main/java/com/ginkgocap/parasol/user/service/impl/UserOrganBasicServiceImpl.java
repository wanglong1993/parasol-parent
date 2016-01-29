package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.exception.UserOrganBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserOrganExtServiceException;
import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;
import com.ginkgocap.parasol.user.service.UserOrganExtService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("userOrganBasicService")
public class UserOrganBasicServiceImpl extends BaseService<UserOrganBasic> implements UserOrganBasicService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private FileIndexService fileIndexService;
	@Resource
	private UserOrganExtService userOrganExtService;
	private static Logger logger = Logger.getLogger(UserOrganBasicServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param userOrganBasic
	 * @return
	 * @throws UserOrganBasicServiceException
	 */
	private UserOrganBasic checkValidity(UserOrganBasic userOrganBasic,int type)throws UserOrganBasicServiceException,UserLoginRegisterServiceException {
		if(userOrganBasic==null) throw new UserOrganBasicServiceException("userOrganBasic can not be null.");
		if(userOrganBasic.getUserId()<=0l) throw new UserOrganBasicServiceException("The value of userId is null or empty.");
		if(userLoginRegisterService.getUserLoginRegister(userOrganBasic.getUserId())==null) throw new UserLoginRegisterServiceException("userId not exists in userLoginRegister.");
		if(type!=0)
		if(getUserOrganBasic(userOrganBasic.getUserId())==null)throw new UserOrganBasicServiceException("userId not exists in userOrganBasic");
//		if(StringUtils.isEmpty(userOrganBasic.getName()))throw new UserOrganBasicServiceException("The value of  name is null or empty.");
		if(userOrganBasic.getStatus().intValue() != 0 && userOrganBasic.getStatus().intValue() != 1 && userOrganBasic.getStatus().intValue() != -1 && userOrganBasic.getStatus() !=2)throw new UserOrganBasicServiceException("The value of status is null or empty.");
		if(userOrganBasic.getCtime()==null) userOrganBasic.setCtime(System.currentTimeMillis());
		if(userOrganBasic.getUtime()==null) userOrganBasic.setUtime(System.currentTimeMillis());
		if(!StringUtils.isEmpty(userOrganBasic.getName()))userOrganBasic.setNameIndex(PinyinUtils.stringToHeads(userOrganBasic.getName()));
		return userOrganBasic;
	}
	
	@Override
	public Long createUserOrganBasic(UserOrganBasic userOrganBasic)throws UserOrganBasicServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long) saveEntity(checkValidity(userOrganBasic,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserOrganBasicServiceException("createUserOrganBasic failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}

	@Override
	public boolean updateUserOrganBasic(UserOrganBasic userOrganBasic)throws UserOrganBasicServiceException,UserLoginRegisterServiceException {
		try {
			return updateEntity(checkValidity(userOrganBasic,1));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}

	@Override
	public UserOrganBasic getUserOrganBasic(Long userId) throws UserOrganBasicServiceException {
		try {
			if(userId==null || userId<=0l)throw new UserOrganBasicServiceException("userId is null or empty.");
			UserOrganBasic userOrganBasic=getEntity(userId);
			if(userOrganBasic==null)return null;;
			return userOrganBasic;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}

	@Override
	public List<UserOrganBasic> getUserOrganBasecList(List<Long> userIds)throws UserOrganBasicServiceException {
		try {
			if(userIds==null || userIds.size()==0)throw new UserOrganBasicServiceException("userIds is null or empty");
			return getEntityByIds(userIds);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}

	@Override
	public boolean updateAuth(Long userId, Byte auth)throws UserOrganBasicServiceException,UserLoginRegisterServiceException {
		try {
			if(auth.intValue()!=-1 && auth.intValue()!=0 && auth.intValue()!=1 && auth.intValue()!=2)throw new UserOrganBasicServiceException("auth  is error, must be -1 or 0 or 1 or 2.");
			UserOrganBasic userOrganBasic=getUserOrganBasic(userId);
			if(userOrganBasic==null)throw new UserOrganBasicServiceException("userId not exists in userOrganBasic");
			userOrganBasic.setAuth(auth);
			return updateEntity(userOrganBasic);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}

	@Override
	public boolean updateStatus(Long userId, Byte status)throws UserOrganBasicServiceException,UserLoginRegisterServiceException {
		try {
			if(status.intValue()!=-1 && status.intValue()!=0 && status.intValue()!=1 && status.intValue()!=2)throw new UserOrganBasicServiceException("status is error, must be -1 or 0 or 1 or 2.");
			UserOrganBasic userOrganBasic=getUserOrganBasic(userId);
			if(userOrganBasic==null)throw new UserOrganBasicServiceException("userId not exists in userOrganBasic.");
			userOrganBasic.setStatus(status);
			return updateEntity(userOrganBasic);
		}catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserOrganBasic(Long id)throws UserOrganBasicServiceException {
		try {
			if(id==null || id<=0l) throw new UserOrganBasicServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}
}
