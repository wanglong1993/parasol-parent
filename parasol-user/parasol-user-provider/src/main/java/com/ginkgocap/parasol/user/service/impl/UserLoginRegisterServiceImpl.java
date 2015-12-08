package com.ginkgocap.parasol.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.cache.Cache;
import com.ginkgocap.parasol.cache.CacheModule;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.sms.service.ShortMessageService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userLoginRegisterService")
public class UserLoginRegisterServiceImpl extends BaseService<UserLoginRegister> implements UserLoginRegisterService {
	private static SecureRandomNumberGenerator ecureRandomNumberGenerator;
	private static Random random;
	private static StringBuffer sfb;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private Cache cache;
	
	private static final String USER_LOGIN_REGISTER_MAP_PASSPORT = "UserLoginRegister_Map_Passport"; 
	private static Logger logger = Logger.getLogger(UserLoginRegisterServiceImpl.class);
	
	private static synchronized SecureRandomNumberGenerator getSecureRandomNumberGeneratorInstance(){
		if (ecureRandomNumberGenerator==null)ecureRandomNumberGenerator=new SecureRandomNumberGenerator();
			return ecureRandomNumberGenerator;
	}
	private static synchronized Random getRandom(){
		if (random==null)random=new Random();
			return random;
	}
	public Long createUserLoginRegister(UserLoginRegister userLoginRegister) throws UserLoginRegisterServiceException {
		try {
			if (userLoginRegister != null && StringUtils.isBlank(userLoginRegister.getPassport())) throw new UserLoginRegisterServiceException("Field passport must be a value");
			if(passportIsExist(userLoginRegister.getPassport()))throw new UserLoginRegisterServiceException("passport already exists");
			if(userLoginRegister.getVirtual().intValue()!=0 && userLoginRegister.getVirtual().intValue()!=1)throw new UserLoginRegisterServiceException("virtual must be 0");
			if(StringUtils.isBlank(userLoginRegister.getSource()))throw new UserLoginRegisterServiceException("source is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getIp()))throw new UserLoginRegisterServiceException("ip is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getSalt()))throw new UserLoginRegisterServiceException("salt is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getPassword()))throw new UserLoginRegisterServiceException("password is null or empty");
			if(userLoginRegister.getCtime()==null || userLoginRegister.getCtime()<=0l) userLoginRegister.setCtime(System.currentTimeMillis());
			if(userLoginRegister.getUtime()==null || userLoginRegister.getUtime()<=0l) userLoginRegister.setUtime(System.currentTimeMillis());
			//用户不存在
			return (Long) saveEntity(userLoginRegister);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public UserLoginRegister getUserLoginRegister(String passport) throws UserLoginRegisterServiceException {
		try {
			if(StringUtils.isEmpty(passport)) throw new UserLoginRegisterServiceException("passport is null or empty.");
			UserLoginRegister userLoginRegister=null;
			//根据passport查找id
			Long id =(Long)getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
			//根据id查找实体
			if(id!=null && id>0l)	userLoginRegister=getEntity(id);
			return userLoginRegister;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public UserLoginRegister getUserLoginRegister(Long id) throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l)throw new UserLoginRegisterServiceException("id is must grater than zero.");
			// 根据id查找实体
			return getEntity(id);
		}catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	public boolean updatePassword(Long id, String password) throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l)throw new UserLoginRegisterServiceException("id is must grater than zero.");
			if(StringUtils.isEmpty(password)) throw new UserLoginRegisterServiceException("password is null or empty.");
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			if(userLoginRegister==null) throw new UserLoginRegisterServiceException("id is not exists in UserLoginRegister.");
			userLoginRegister.setSalt(setSalt());
			userLoginRegister.setPassword(setSha256Hash(userLoginRegister.getSalt(),password));
			return updateEntity(userLoginRegister);
		}catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public boolean passportIsExist(String passport) throws UserLoginRegisterServiceException {
		try {
			if(StringUtils.isEmpty(passport)) throw new UserLoginRegisterServiceException("passport is null or empty.");
			Long userId =(Long)getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
			return userId==null?false:true;
					
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public boolean updateIpAndLoginTime(Long id, String ip)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l)throw new UserLoginRegisterServiceException("id is must grater than zero.");
			if(StringUtils.isEmpty(ip)) throw new UserLoginRegisterServiceException("ip is null or empty.");
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			if(userLoginRegister==null) throw new UserLoginRegisterServiceException("id is not exists in UserLoginRegister.");
			userLoginRegister.setIp(ip);
			userLoginRegister.setUtime(new Date().getTime());
			return updateEntity(userLoginRegister);
		}catch (Exception e) {
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
			if(id==null || id<=0l) throw new UserLoginRegisterServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	
	@Override
	public Boolean fakeDeleteUserLoginRegister(Long id)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l) throw new UserLoginRegisterServiceException("id is must grater than zero.");
			return fakeDeleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public Long getId(String passport) throws UserLoginRegisterServiceException {
		try {
			if(StringUtils.isEmpty(passport))throw new UserLoginRegisterServiceException("passport is null or empty.");
			Object id=getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
			return id!=null?(Long)id:0l;
		} catch (Exception e) {
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
		} catch (Exception e) {
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

	@Override
	public Boolean setMobile(String mobile, Long id)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l)throw new UserLoginRegisterServiceException("id is null or empty.");
			if(StringUtils.isEmpty(mobile)) throw new UserLoginRegisterServiceException("mobile is null or empty.");
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			if(userLoginRegister==null)throw new UserLoginRegisterServiceException("id is not exists in UserLoginRegister.");
			userLoginRegister.setMobile(mobile);
			return updateEntity(userLoginRegister);
		}catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	@Override
	public Boolean setEmail(String email, Long id)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l)throw new UserLoginRegisterServiceException("id is null or empty.");
			if(StringUtils.isEmpty(email)) throw new UserLoginRegisterServiceException("email is null or empty.");
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			if(userLoginRegister==null)throw new UserLoginRegisterServiceException("id is not exists.");
			{
				userLoginRegister.setMobile(email);
				return updateEntity(userLoginRegister);
			}
		}catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	/**
	 * 验证是否是手机号码
	 * 验证号段截止2015.11.26,如今后有新的号段进来,会在正则表达式中添加
	 * @param mobile
	 * @return
	 */
	public  boolean isMobileNo(String mobile){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]|(17[6,7,8])|14[5,7]))\\d{8}$");   
		Matcher m = p.matcher(mobile);  
		return m.matches();  
	}
	/**
	 * 根据手机号设置缓存,时间为30分钟
	 * @param mobile
	 * @return boolean
	 */
	private  boolean setCache(String mobile,String identifyingCode){
		boolean bl =false;
		String key=cache.getCacheHelper().buildKey(CacheModule.REGISTER, mobile);
		Object object =cache.get(key);
		String value=null;
		if(object!=null)value=object.toString();
		if(StringUtils.isEmpty(value))
		bl = cache.set(key, 1 * 60 * 1, identifyingCode);
		return bl;
	}	
	/**
	 * 生成验证码
	 * @return String
	 */
	private  String generationIdentifyingCode(){
		Random random = getRandom();
		StringBuffer sfb=new StringBuffer();
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sfb.append(rand);
		}
		return sfb.toString();
	}

	@Override
	public String sendIdentifyingCode(String mobile)throws UserLoginRegisterServiceException {
		try {
			if(isMobileNo(mobile)){ 
				Object value=cache.get(cache.getCacheHelper().buildKey(CacheModule.REGISTER, mobile));
				String identifyingCode=null;
				if(value!=null)identifyingCode=value.toString();
				if(StringUtils.isEmpty(identifyingCode)){
					identifyingCode=generationIdentifyingCode();
					if(setCache(mobile,identifyingCode)){
						int back=shortMessageService.sendMessage(mobile, new StringBuffer().append("您的短信验证码为").append(identifyingCode).append("，有效期30分钟，请及时验证").toString(), getId(mobile), 1);
						if(back==1)return identifyingCode;
						else return "";
					}
				}else{
					return identifyingCode;
				}
			}else return "";
		}catch (Exception e){
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
		return "";
	}
	@Override
	public String getIdentifyingCode(String mobile)throws UserLoginRegisterServiceException {
		Object value=cache.get(cache.getCacheHelper().buildKey(CacheModule.REGISTER, mobile));
		return value!=null?value.toString():null;
	}
}
