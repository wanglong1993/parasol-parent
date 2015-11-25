package com.ginkgocap.parasol.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
	private static int error_usertype_is_error=1002;
	private static int error_source_is_error=1003;
	private static int error_ip_is_error=1004;
	private static int error_salt_is_error=1005;
	private static int error_password_is_error=1006;
	private static SecureRandomNumberGenerator ecureRandomNumberGenerator;
	
	//dao const
	private static final String USER_LOGIN_REGISTER_MAP_PASSPORT = "UserLoginRegister_Map_Passport"; 
	private static Logger logger = Logger.getLogger(UserLoginRegisterServiceImpl.class);
	
	private static synchronized SecureRandomNumberGenerator getSecureRandomNumberGeneratorInstance(){
		if (ecureRandomNumberGenerator==null)ecureRandomNumberGenerator=new SecureRandomNumberGenerator();
			return ecureRandomNumberGenerator;
		}

	public Long createUserLoginRegister(UserLoginRegister userLoginRegister) throws UserLoginRegisterServiceException {
		try {
			if (userLoginRegister != null && StringUtils.isBlank(userLoginRegister.getPassport())) throw new UserLoginRegisterServiceException(error_passport_blank,"Field passport must be a value");
			if(passportIsExist(userLoginRegister.getPassport()))throw new UserLoginRegisterServiceException(error_passport_is_exist, "passport already exists");
			if(userLoginRegister.getVirtual()!=0)throw new UserLoginRegisterServiceException(error_usertype_is_error, "virtual must be 0");
			if(StringUtils.isBlank(userLoginRegister.getSource()))throw new UserLoginRegisterServiceException(error_source_is_error, "source is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getIp()))throw new UserLoginRegisterServiceException(error_ip_is_error, "ip is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getSalt()))throw new UserLoginRegisterServiceException(error_salt_is_error, "salt is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getPassword()))throw new UserLoginRegisterServiceException(error_password_is_error, "password is null or empty");
			if(userLoginRegister.getCtime()==null || userLoginRegister.getCtime()<=0l) userLoginRegister.setCtime(System.currentTimeMillis());
			if(userLoginRegister.getUtime()==null || userLoginRegister.getUtime()<=0l) userLoginRegister.setUtime(System.currentTimeMillis());
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
			if(StringUtils.isEmpty(passport)) return null;
			UserLoginRegister userLoginRegister=null;
			//根据passport查找id
			Long id =(Long)getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
			//根据id查找实体
			if(id!=null && id>0l)	userLoginRegister=getEntity(id);
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
			if(id==null || id<=0l)return null;
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
			if(id==null || id<=0l)return false;
			if(StringUtils.isEmpty(password)) return false;
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			if(userLoginRegister!=null){
				userLoginRegister.setSalt(setSalt());
				userLoginRegister.setPassword(setSha256Hash(userLoginRegister.getSalt(),password));
				return updateEntity(userLoginRegister);
			}
			return  false;
		}catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public boolean passportIsExist(String passport) throws UserLoginRegisterServiceException {
		try {
			if(StringUtils.isEmpty(passport)) return false;
			Long userId =(Long)getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
			return userId==null?false:true;
					
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public boolean updateIpAndLoginTime(Long id, String ip)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l)return false;
			if(StringUtils.isEmpty(ip)) return false;
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			if(userLoginRegister!=null){
				userLoginRegister.setIp(ip);
				userLoginRegister.setUtime(new Date().getTime());
				return updateEntity(userLoginRegister);
			}
			return false;
		}catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public String setSalt() throws UserLoginRegisterServiceException {
		RandomNumberGenerator saltGenerator = UserLoginRegisterServiceImpl.getSecureRandomNumberGeneratorInstance();
		return saltGenerator.nextBytes().toHex();
	}

	@Override
	public String setSha256Hash(String salt,String password)throws UserLoginRegisterServiceException {
		String newPass=new Sha256Hash(password, salt,5000).toHex();
		return newPass;
	}

	@Override
	public Boolean realDeleteUserLoginRegister(Long id) throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l) return false;
			return deleteEntity(id);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	
	@Override
	public Boolean fakeDeleteUserLoginRegister(Long id)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l) return false;
			return fakeDeleteEntity(id);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public Long getId(String passport) throws UserLoginRegisterServiceException {
		try {
			if(StringUtils.isEmpty(passport)) return null;
			return (Long)getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public boolean realDeleteUserLoginRegisterList(List<Long> list)throws UserLoginRegisterServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public int fakeDeleteUserLoginRegister(List<Long> list)throws UserLoginRegisterServiceException {
		return 0;
	}

}
