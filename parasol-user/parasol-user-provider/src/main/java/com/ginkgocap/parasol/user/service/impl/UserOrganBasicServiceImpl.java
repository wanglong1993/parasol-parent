package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserOrganBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;
@Service("userOrganBasicService")
public class UserOrganBasicServiceImpl extends BaseService<UserOrganBasic> implements UserOrganBasicService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static int error_userOrganBasic_null = 1000;	
	private static int error_userId_null = 1001;	
	private static int error_name_null = 1004;
	private static int error_ip_incorrect = 1008;
	private static int error_status_incorrect = 1009;
	private static int error_userId_not_exists_in_userlogin = 1010;	
	private static int error_userIds_null = 1011;	
	private static int error_name_first_null = 1012;	
	private static int error_name_index_null = 1013;	
	private static int error_name_index_all_null = 1014;
	private static int error_userId_not_exists_in_userOrganBasic = 1015;	
	private static Logger logger = Logger.getLogger(UserOrganBasicServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param userOrganBasic
	 * @return
	 * @throws UserOrganBasicServiceException
	 */
	private UserOrganBasic checkValidity(UserOrganBasic userOrganBasic,int type)throws UserOrganBasicServiceException,UserLoginRegisterServiceException {
		if(userOrganBasic==null) throw new UserOrganBasicServiceException(error_userOrganBasic_null,"userOrganBasic can not be null.");
		if(userOrganBasic.getUserId()<=0l) throw new UserOrganBasicServiceException(error_userId_null,"The value of userId is null or empty.");
		try {
			if(userLoginRegisterService.getUserLoginRegister(userOrganBasic.getUserId())==null) throw new UserLoginRegisterServiceException(error_userId_not_exists_in_userlogin,"userId not exists in userLoginRegister");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(type!=0)
		if(getUserOrganBasic(userOrganBasic.getUserId())==null)throw new UserOrganBasicServiceException(error_userId_not_exists_in_userOrganBasic,"userId not exists in userOrganBasic");
		if(StringUtils.isEmpty(userOrganBasic.getName()))throw new UserOrganBasicServiceException(error_name_null,"The value of  name is null or empty.");
		if(StringUtils.isEmpty(userOrganBasic.getIp()))throw new UserOrganBasicServiceException(error_ip_incorrect,"The value of ip is null or empty.");
		if(StringUtils.isEmpty(userOrganBasic.getNameFirst()))throw new UserOrganBasicServiceException(error_name_first_null,"The value of nameFirst is null or empty.");
		if(StringUtils.isEmpty(userOrganBasic.getNameIndex()))throw new UserOrganBasicServiceException(error_name_index_null,"The value of nameIndex is null or empty.");
		if(StringUtils.isEmpty(userOrganBasic.getNameIndexAll()))throw new UserOrganBasicServiceException(error_name_index_all_null,"The value of nameIndexAll is null or empty.");
		if(userOrganBasic.getStatus().intValue() != 0 && userOrganBasic.getStatus().intValue() != 1 && userOrganBasic.getStatus().intValue() != -1 && userOrganBasic.getStatus() !=2)throw new UserOrganBasicServiceException(error_status_incorrect,"The value of status is null or empty.");
		if(userOrganBasic.getCtime()==null) userOrganBasic.setCtime(System.currentTimeMillis());
		if(userOrganBasic.getUtime()==null) userOrganBasic.setUtime(System.currentTimeMillis());
		return userOrganBasic;
	}
	
	@Override
	public Long createUserOrganBasic(UserOrganBasic userOrganBasic)throws UserOrganBasicServiceException,UserLoginRegisterServiceException{
		try {
			return (Long) saveEntity(checkValidity(userOrganBasic,0));
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
			if(userId==null || userId<=0l)throw new UserOrganBasicServiceException(error_userId_null,"userId is null or empty");
			return getEntity(userId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}

	@Override
	public List<UserOrganBasic> getUserBasecList(List<Long> userIds)throws UserOrganBasicServiceException {
		try {
			if(userIds==null || userIds.size()==0)throw new UserOrganBasicServiceException(error_userIds_null,"userIds is null or empty");
			return getEntityByIds(userIds);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganBasicServiceException(e);
		}
	}
}
