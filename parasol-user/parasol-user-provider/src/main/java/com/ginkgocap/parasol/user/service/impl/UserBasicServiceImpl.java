package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userBasicService")
public class UserBasicServiceImpl extends BaseService<UserBasic> implements UserBasicService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static int error_userBasic_null = 1000;	
	private static int error_userId_null = 1001;	
	private static int error_name_null = 1004;
	private static int error_sex_incorrect = 1005;
	private static int error_provinceId_incorrect = 1005;
	private static int error_cityId_incorrect = 1006;
	private static int error_countyId_incorrect = 1007;
	private static int error_ip_incorrect = 1008;
	private static int error_status_incorrect = 1009;
	private static int error_userId_not_exists_in_userlogin = 1010;	
	private static int error_userIds_null = 1011;	
	private static int error_name_first_null = 1012;	
	private static int error_name_index_null = 1013;	
	private static int error_name_index_all_null = 1014;
	private static int error_userId_not_exists_in_userBasic = 1015;	
	private static Logger logger = Logger.getLogger(UserBasicServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param userBasic
	 * @return
	 * @throws UserBasicServiceException
	 */
	@SuppressWarnings("unused")
	private UserBasic checkValidity(UserBasic userBasic,int type)throws UserBasicServiceException,UserLoginRegisterServiceException {
		if(userBasic==null) throw new UserBasicServiceException(error_userBasic_null,"userBasic can not be null.");
		if(userBasic.getUserId()<=0l) throw new UserBasicServiceException(error_userId_null,"The value of userId is null or empty.");
		try {
			if(userLoginRegisterService.getUserLoginRegister(userBasic.getUserId())==null) throw new UserLoginRegisterServiceException(error_userId_not_exists_in_userlogin,"userId not exists in userLoginRegister");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(type!=0)
		if(getUserBasic(userBasic.getUserId())==null)throw new UserBasicServiceException(error_userId_not_exists_in_userBasic,"userId not exists in userBasic");
		if(StringUtils.isEmpty(userBasic.getName()))throw new UserBasicServiceException(error_name_null,"The value of  name is null or empty.");
		if(userBasic.getSex().intValue()!=0 && userBasic.getSex().intValue()!=1 && userBasic.getSex().intValue()!=2)throw new UserBasicServiceException(error_sex_incorrect,"The value of sex is null or empty.");
//		if(userBasic.getProvinceId()==null ||userBasic.getProvinceId() <=0l )throw new UserBasicServiceException(error_provinceId_incorrect,"The value of provinceId is null or empty.");
//		if(userBasic.getCityId()==null ||userBasic.getCityId() <=0l )throw new UserBasicServiceException(error_cityId_incorrect,"The value of cityId is null or empty.");
//		if(userBasic.getCountyId()==null ||userBasic.getCountyId() <=0l )throw new UserBasicServiceException(error_countyId_incorrect,"The value of countyId is null or empty.");
		if(StringUtils.isEmpty(userBasic.getIp()))throw new UserBasicServiceException(error_ip_incorrect,"The value of ip is null or empty.");
		if(StringUtils.isEmpty(userBasic.getNameFirst()))throw new UserBasicServiceException(error_name_first_null,"The value of nameFirst is null or empty.");
		if(StringUtils.isEmpty(userBasic.getNameIndex()))throw new UserBasicServiceException(error_name_index_null,"The value of nameIndex is null or empty.");
		if(StringUtils.isEmpty(userBasic.getNameIndexAll()))throw new UserBasicServiceException(error_name_index_all_null,"The value of nameIndexAll is null or empty.");
		if(userBasic.getStatus().intValue() != 0 && userBasic.getStatus().intValue() != 1 && userBasic.getStatus().intValue() != -1 && userBasic.getStatus() !=2)throw new UserBasicServiceException(error_status_incorrect,"The value of status is null or empty.");
		if(userBasic.getCtime()==null) userBasic.setCtime(System.currentTimeMillis());
		if(userBasic.getUtime()==null) userBasic.setUtime(System.currentTimeMillis());
		return userBasic;
	}
	
	@Override
	public Long createUserBasic(UserBasic userBasic)throws UserBasicServiceException,UserLoginRegisterServiceException{
		try {
			return (Long) saveEntity(checkValidity(userBasic,0));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public boolean updateUserBasic(UserBasic userBasic)throws UserBasicServiceException,UserLoginRegisterServiceException {
		try {
			return updateEntity(checkValidity(userBasic,1));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public UserBasic getUserBasic(Long userId) throws UserBasicServiceException {
		try {
			if(userId==null || userId<=0l)throw new UserBasicServiceException(error_userId_null,"userId is null or empty");
			return getEntity(userId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public List<UserBasic> getUserBasecList(List<Long> userIds)throws UserBasicServiceException {
		try {
			if(userIds==null || userIds.size()==0)throw new UserBasicServiceException(error_userIds_null,"userIds is null or empty");
			return getEntityByIds(userIds);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}
}