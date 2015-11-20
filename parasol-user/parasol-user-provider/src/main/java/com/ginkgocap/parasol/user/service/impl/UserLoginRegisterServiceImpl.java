package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userLoginRegisterService")
public class UserLoginRegisterServiceImpl extends BaseService<UserLoginRegister> implements UserLoginRegisterService {
	private static int error_passport_blank = 1000;
	private static int error_passport_is_exist=1001;
	
	//dao const
	private static final String USER_LOGIN_REGISTER_MAP_PASSPORT = "UserLoginRegister_Map_Passport"; // 用户id列表
	private static Logger logger = Logger.getLogger(UserLoginRegisterServiceImpl.class);

	public Long createUserLoginRegister(UserLoginRegister userLoginRegister) throws UserLoginRegisterServiceException {
		Long id = null;
		try {
			// 检查通行证是否为空
			if (userLoginRegister != null && StringUtils.isBlank(userLoginRegister.getPassport())) {
				throw new UserLoginRegisterServiceException(error_passport_blank,"Passport property  must have a value");
			}
			//检查通行证是否存在
			boolean bl = passportIsExist(USER_LOGIN_REGISTER_MAP_PASSPORT, userLoginRegister.getPassport());
			
			//用户已经存在
			if(bl){
				throw new UserLoginRegisterServiceException(error_passport_is_exist, "passport already exist");
			}
			//用户不存在
			if(!bl){  
				id=(Long) saveEntity(userLoginRegister);
			}
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
		return id;
	}

	public UserLoginRegister getUserLoginRegister(String passport,String value) throws UserLoginRegisterServiceException {
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
			throws UserLoginRegisterServiceException {
		return false;
	}

	public boolean passportIsExist(String passport,String value) throws UserLoginRegisterServiceException {
		try {
			boolean bl=true;
			Long userId =(Long)getMapId(passport,value);
			if(userId==null)
				bl=false;
			return bl;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

}
