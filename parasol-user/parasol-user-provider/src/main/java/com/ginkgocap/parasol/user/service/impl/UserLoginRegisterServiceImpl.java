package com.ginkgocap.parasol.user.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
		try {
			// 检查通行证是否为空
			if (userLoginRegister != null && StringUtils.isBlank(userLoginRegister.getPassport())) {
				throw new UserLoginRegisterServiceException(error_passport_blank,"Field passport must be a value");
			}
			//检查通行证是否存在
			boolean bl = passportIsExist(USER_LOGIN_REGISTER_MAP_PASSPORT, userLoginRegister.getPassport());
			
			//用户已经存在
			if(bl){
				throw new UserLoginRegisterServiceException(error_passport_is_exist, "passport already exists");
			}
			//用户不存在
			return (Long) saveEntity(userLoginRegister);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public UserLoginRegister getUserLoginRegister(String passport) throws UserLoginRegisterServiceException {
		try {
			UserLoginRegister userLoginRegister=null;
			//根据passport查找id
			Long id =(Long)getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
			if(id!=null && id>0l){
			//根据id查找实体	
			userLoginRegister=getEntity(id);
			}
			return userLoginRegister;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public UserLoginRegister getUserLoginRegister(Long id) throws UserLoginRegisterServiceException {
		try {
			// 根据id查找实体
			return getEntity(id);
		}catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	public boolean updatePassword(Long id, String password) throws UserLoginRegisterServiceException {
		try {
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			userLoginRegister.setSalt(setSalt());
			userLoginRegister.setPassword(setSha256Hash(userLoginRegister.getSalt(),password));
			return updateEntity(userLoginRegister);
		}catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public boolean passportIsExist(String mapping_region,String value) throws UserLoginRegisterServiceException {
		try {
			Long userId =(Long)getMapId(mapping_region,value);
			return userId==null?false:true;
					
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public boolean updateIpAndLoginTime(Long id, String ip, Date utime)throws UserLoginRegisterServiceException {
		try {
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			userLoginRegister.setIp(ip);
			userLoginRegister.setUtime(utime);
			saveEntity(userLoginRegister);
			return true;
		}catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public String setSalt() throws UserLoginRegisterServiceException {
		RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
		return saltGenerator.nextBytes().toHex();
	}

	@Override
	public String setSha256Hash(String salt,String password)throws UserLoginRegisterServiceException {
		String newPass=new Sha256Hash(password, salt,5000).toHex();
		return  newPass;
	}

}
