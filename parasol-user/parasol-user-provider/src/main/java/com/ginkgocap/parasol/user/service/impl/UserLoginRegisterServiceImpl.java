package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userLoginRegisterService")
public class UserLoginRegisterServiceImpl extends BaseService<UserLoginRegister> implements UserLoginRegisterService {
	private static int error_passport_blank = 1000;
	private static int error_user_is_exist=1001;
	private static Logger logger = Logger.getLogger(UserLoginRegisterServiceImpl.class);

	public Long createUserLoginRegister(UserLoginRegister userLoginRegister) throws UserServiceException {
		try {
			// 检查通行证是否为空
			if (userLoginRegister != null && StringUtils.isBlank(userLoginRegister.getPassport())) {
				throw new UserServiceException(error_passport_blank,"Passport property of code must have a value");
			}
			//检查通行证是否存在
			boolean bl = passportIsExist("passport", userLoginRegister.getPassport());
			
			//用户已经存在
			if(bl){
				throw new UserServiceException(error_user_is_exist, "User already exist");
			}
			//用户不存在
			if(!bl){
				return (Long) saveEntity(userLoginRegister);
			}
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserServiceException(e);
		}
		return null;
	}

	public UserLoginRegister getUserLoginRegister(String passport,String value) throws UserServiceException {
			try {
				List<UserLoginRegister> list=getEntitys(passport,value);
				if (list.size()==0){
					
				}
			} catch (BaseServiceException e) {
				e.printStackTrace();
			}
		return null;
	}

	public boolean updatePassword(Integer id, String password)
			throws UserServiceException {
		return false;
	}

	public boolean passportIsExist(String passport,String value) throws UserServiceException {
		try {
			boolean bl=true;
			List<UserLoginRegister> list =getEntitys(passport,value);
			if (list.size()==1){
				bl=true;
			}else if (list.size()==0){
				bl=false;
			}
			return bl;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserServiceException(e);
		}
	}

}
