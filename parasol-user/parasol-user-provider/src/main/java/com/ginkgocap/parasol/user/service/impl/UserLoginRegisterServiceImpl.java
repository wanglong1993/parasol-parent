
package com.ginkgocap.parasol.user.service.impl;

import java.util.List;
import java.util.Map;
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
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.cache.Cache;
import com.ginkgocap.parasol.cache.CacheModule;
import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.email.service.EmailService;
import com.ginkgocap.parasol.sms.service.ShortMessageService;
import com.ginkgocap.parasol.user.exception.UserFriendlyServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserFriendly;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userLoginRegisterService")
public class UserLoginRegisterServiceImpl extends BaseService<UserLoginRegister> implements UserLoginRegisterService {
	private final  static String findPasswordTitle = "【金桐】找回密码";
	private final  static String findPasswordCoopertTitle = "【coopert】找回密码";
	private final  static String registerTitle = "【金桐】邮箱注册";
	private final  static String registerCoopertTitle = "【coopert】邮箱注册";
	private final  static String bindTitle = "【金桐】绑定邮箱";
	private final  static String changeOldTitle = "【金桐】更改邮箱-验证旧邮箱";
	private final  static String editPasswordTitle = "【金桐】修改密码";
	
	private static SecureRandomNumberGenerator ecureRandomNumberGenerator;
	private static Random random;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private EmailService emailService;
	@Resource
	private Cache cache;
	
	private static final String USER_LOGIN_REGISTER_MAP_PASSPORT = "UserLoginRegister_Map_Passport"; 
	private static final String USER_LOGIN_REGISTER_MAP_GID = "UserLoginRegister_Map_Gid"; 
	private static final String USER_LOGIN_REGISTER_MAP_MOBILE = "UserLoginRegister_Map_Mobile"; 
	private static final String USER_LOGIN_REGISTER_MAP_EMAIL = "UserLoginRegister_Map_Email"; 
	private static final String USER_LOGIN_REGISTER_MAP_USER_NAME = "UserLoginRegister_Map_User_Name"; 
	private static final String UserLoginRegister_List_By_Sapc = "UserLoginRegister_List_By_Sapc"; 
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
			if(userLoginRegister.getUsetType().intValue()!=0 && userLoginRegister.getUsetType().intValue()!=1)throw new UserLoginRegisterServiceException("userType must be 0 or 1");
			if(StringUtils.isBlank(userLoginRegister.getSource()))throw new UserLoginRegisterServiceException("source is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getIp()))throw new UserLoginRegisterServiceException("ip is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getSalt()))throw new UserLoginRegisterServiceException("salt is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getPassword()))throw new UserLoginRegisterServiceException("password is null or empty");
			if(userLoginRegister.getCtime()==null || userLoginRegister.getCtime()<=0l) userLoginRegister.setCtime(System.currentTimeMillis());
			if(userLoginRegister.getUtime()==null || userLoginRegister.getUtime()<=0l) userLoginRegister.setUtime(System.currentTimeMillis());
			Object obj=saveEntity(userLoginRegister);
			if(!ObjectUtils.isEmpty(obj)){
				Long id =(Long)obj;
				if(id>0l)return id;
			}throw new UserLoginRegisterServiceException("createUserLoginRegister failed.");
		} catch (BaseServiceException e) {
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
			System.out.println("passport="+passport+",id1111="+id);
			if(id==null || id<0l){
				if(isMobileNo(passport))id =(Long)getMapId(USER_LOGIN_REGISTER_MAP_MOBILE,passport);
				System.out.println("passport="+passport+",id22222="+id);
			}
			if(id==null || id<0l){
				if(isEmail(passport))id =(Long)getMapId(USER_LOGIN_REGISTER_MAP_EMAIL,passport);
				System.out.println("passport="+passport+",id33333="+id);
			}
			if(id==null || id<0l){
				if(!isMobileNo(passport) && !isEmail(passport))id =(Long)getMapId(USER_LOGIN_REGISTER_MAP_USER_NAME,passport);
				System.out.println("passport="+passport+",id44444="+id);
			}
			//根据id查找实体
			if(id!=null && id>0l){	
				userLoginRegister=getEntity(id);
				return userLoginRegister;
			}
			System.out.println("passport="+passport+",id=========="+id);
			return null;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}

	public UserLoginRegister getUserLoginRegister(Long id) throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l)throw new UserLoginRegisterServiceException("id is must grater than zero.");
			UserLoginRegister userLoginRegister=getEntity(id);
			if(!ObjectUtils.isEmpty(userLoginRegister)) return userLoginRegister;
			else return null;
		}catch (BaseServiceException e) {
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
			userLoginRegister.setPassword(password);
			if(updateEntity(userLoginRegister))return true;
			throw new UserLoginRegisterServiceException("updatePassword failed.");
		}catch (BaseServiceException e) {
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
			if(userId==null || userId<0l){
				if(isMobileNo(passport))userId =(Long)getMapId(USER_LOGIN_REGISTER_MAP_MOBILE,passport);
			}
			if(userId==null || userId<0l){
				if(isEmail(passport))userId =(Long)getMapId(USER_LOGIN_REGISTER_MAP_EMAIL,passport);
			}
			if(userId==null || userId<0l){
				if(!isMobileNo(passport) && !isEmail(passport))userId =(Long)getMapId(USER_LOGIN_REGISTER_MAP_USER_NAME,passport);
			}
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
			if(id==null || id<=0l)throw new UserLoginRegisterServiceException("id is must grater than zero.");
			if(StringUtils.isEmpty(ip)) throw new UserLoginRegisterServiceException("ip is null or empty.");
			// 根据id查找实体
			UserLoginRegister userLoginRegister = getEntity(id);
			if(userLoginRegister==null) throw new UserLoginRegisterServiceException("id is not exists in UserLoginRegister.");
			userLoginRegister.setIp(ip);
			userLoginRegister.setUtime(System.currentTimeMillis());
			if(updateEntity(userLoginRegister))return true;
			throw new UserLoginRegisterServiceException("updateIpAndLoginTime failed.");
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
			if(id==null || id<=0l) throw new UserLoginRegisterServiceException("id is must grater than zero.");
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
			if(id==null || id<=0l) throw new UserLoginRegisterServiceException("id is must grater than zero.");
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
			if(StringUtils.isEmpty(passport))throw new UserLoginRegisterServiceException("passport is null or empty.");
			Object id=getMapId(USER_LOGIN_REGISTER_MAP_PASSPORT,passport);
			return id!=null?(Long)id:0l;
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
		}catch (BaseServiceException e) {
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
		}catch (BaseServiceException e) {
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
     * 验证是否是正常的邮箱
     * @param email
     * @return
     */
    private boolean isEmail(String email){     
        String str="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
           Pattern p = Pattern.compile(str);     
           Matcher m = p.matcher(email);     
           logger.info(m.matches()+"---");     
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
//		Object object =cache.get(key);
//		String value=null;
//		if(object!=null)value=object.toString();
//		if(StringUtils.isEmpty(value))
		bl = cache.set(key, 1 * 60 * 30, identifyingCode);
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
	public String sendIdentifyingCode(String passport,int type)throws UserLoginRegisterServiceException {
		try {
			if(isMobileNo(passport)){ 
//				Object value=cache.get(cache.getCacheHelper().buildKey(CacheModule.REGISTER, passport));
				String identifyingCode=null;
//				if(value!=null)identifyingCode=value.toString();
//				if(StringUtils.isEmpty(identifyingCode)){
					identifyingCode=generationIdentifyingCode();
					if(setCache(passport,identifyingCode)){
						int back=shortMessageService.sendMessage(passport, new StringBuffer().append(type==2?"【coopert】":"").append("您的短信验证码为").append(identifyingCode).append("，有效期30分钟，请及时验证").toString(), getId(passport), type);
						if(back==1)return identifyingCode;
						else return "";
					}
//				}else{
//					return identifyingCode;
//				}
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
	public String getIdentifyingCode(String passport)throws UserLoginRegisterServiceException {
		Object value=cache.get(cache.getCacheHelper().buildKey(CacheModule.REGISTER, passport));
		return value!=null?value.toString():null;
	}
	@Override
	public boolean sendEmail(String mailTo,int type,Map<String, Object> map)throws UserLoginRegisterServiceException {
		try {
			boolean bl=false;
			if(StringUtils.isEmpty(mailTo))throw new UserLoginRegisterServiceException("email is null or empty.");
			if(type!=0 && type!=1 && type!=2 && type !=3 && type !=4 && type!=5 && type!=6 && type!=7 && type!=8) throw new UserLoginRegisterServiceException("type must be 0 or 1 or 2 or 3 or 4.");
			if(type==0) bl=emailService.sendEmailSync(mailTo, null, registerTitle, null, map, "reg-code-emai.ftl");
			if(type==1) bl=emailService.sendEmailSync(mailTo, null, registerCoopertTitle, null, map, "reg-activate-emai-coopert.ftl");
			if(type==2) bl= emailService.sendEmailSync(mailTo, null, findPasswordTitle, null, map, "findpwd-email.ftl");
			if(type==3) bl= emailService.sendEmailSync(mailTo, null, bindTitle, null, map, "bindemail.ftl");
			if(type==7) bl= emailService.sendEmailSync(mailTo, null, changeOldTitle, null, map, "bindemail.ftl");
			if(type==4) bl= emailService.sendEmailSync(mailTo, null, findPasswordCoopertTitle, null, map, "findpwd-email-coopert.ftl");
			if(type==5) bl=emailService.sendEmailSync(mailTo, null, registerTitle, null, map, "reg-activate-emai-coopert.ftl");
			if(type==6) bl=emailService.sendEmailSync(mailTo, null, findPasswordTitle, null, map, "bindemail.ftl");
			if(type==8) bl=emailService.sendEmailSync(mailTo, null, findPasswordTitle, null, map, "reg-code-emai.ftl");
			return bl;
		}catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	@Override
	public Boolean updataUserLoginRegister(UserLoginRegister userLoginRegister)throws UserLoginRegisterServiceException {
		try {
			if (userLoginRegister != null && StringUtils.isBlank(userLoginRegister.getPassport())) throw new UserLoginRegisterServiceException("Field passport must be a value");
			if(userLoginRegister.getUsetType().intValue()!=0 && userLoginRegister.getUsetType().intValue()!=1)throw new UserLoginRegisterServiceException("userType must be 0 or 1");
			if(StringUtils.isBlank(userLoginRegister.getSource()))throw new UserLoginRegisterServiceException("source is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getIp()))throw new UserLoginRegisterServiceException("ip is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getSalt()))throw new UserLoginRegisterServiceException("salt is null or empty");
			if(StringUtils.isBlank(userLoginRegister.getPassword()))throw new UserLoginRegisterServiceException("password is null or empty");
			if(userLoginRegister.getCtime()==null || userLoginRegister.getCtime()<=0l) userLoginRegister.setCtime(System.currentTimeMillis());
			if(userLoginRegister.getUtime()==null || userLoginRegister.getUtime()<=0l) userLoginRegister.setUtime(System.currentTimeMillis());
			return updateEntity(userLoginRegister);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	@Override
	public boolean getEmail(String email)throws UserLoginRegisterServiceException {
		 Object value=cache.get(cache.getCacheHelper().buildKey(CacheModule.REGISTER, email));
		 if(value!=null)return true;
		 return false;
	}
	@Override
	public boolean deleteIdentifyingCode(String passport)throws UserLoginRegisterServiceException {
		return cache.remove(passport);
	}
	@Override
	public UserLoginRegister getUserLoginRegisterByGid(String gid)throws UserLoginRegisterServiceException {
		try {
			if(StringUtils.isEmpty(gid)) throw new UserLoginRegisterServiceException("gid is null or empty.");
			UserLoginRegister userLoginRegister=null;
			//根据passport查找id
			Long id =(Long)getMapId(USER_LOGIN_REGISTER_MAP_GID,gid);
			//根据id查找实体
			if(id!=null && id>0l){	
				userLoginRegister=getEntity(id);
				return userLoginRegister;
			}
			return null;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	@Override
	public boolean setCache(String key, Object value, int expireTime)throws UserLoginRegisterServiceException {
		boolean bl =false;
		String key2=cache.getCacheHelper().buildKey(CacheModule.REGISTER, key);
		bl = cache.set(key2, expireTime, value);
		return bl;
	}
	@Override
	public Object getCache(String key) throws UserLoginRegisterServiceException {
		Object value=cache.get(cache.getCacheHelper().buildKey(CacheModule.REGISTER, key));
		return value!=null?value:null;
	}
	@Override
	public List<UserLoginRegister> getUserList(int statu, int auth,String passport, long from, long to)throws UserLoginRegisterServiceException {
		List<UserLoginRegister> list =null;
		List<Long> ids =null;
		try {
			if((statu==0 || statu==1) && (auth==0 || auth==1 || auth==2) && !StringUtils.isEmpty(passport) && from>=0 && to>=0){
			ids =getIds(UserLoginRegister_List_By_Sapc, new Object[]{statu,auth,passport,from,to});
			}
			if((statu==0 || statu==1) && (auth==0 || auth==1 || auth==2) && !StringUtils.isEmpty(passport) && (from<=0 || to<=0)){
			ids =getIds(UserLoginRegister_List_By_Sap, new Object[]{statu,auth,passport});
			}
			if((statu==0 || statu==1) && (auth==0 || auth==1 || auth==2) && StringUtils.isEmpty(passport) && (from>=0 || to>=0)){
			ids =getIds(UserLoginRegister_List_By_Sac, new Object[]{statu,auth,from,to});
			}
			if((statu==0 || statu==1) && (auth!=0 && auth!=1 && auth!=2) && !StringUtils.isEmpty(passport) && (from>=0 || to>=0)){
			ids =getIds(UserLoginRegister_List_By_Spc, new Object[]{statu,passport,from,to});
			}
			if((statu!=0 && statu!=1) && (auth==0 || auth==1 || auth==2) && !StringUtils.isEmpty(passport) && (from>=0 || to>=0)){
			ids =getIds(UserLoginRegister_List_By_Apc, new Object[]{auth,passport,from,to});
			}
			if((statu==0 || statu==1) && (auth!=0 && auth!=1 && auth!=2) && !StringUtils.isEmpty(passport) && (from<=0 || to<=0)){
			ids =getIds(UserLoginRegister_List_By_Sp, new Object[]{statu,passport});
			}
			if((statu==0 || statu==1) && (auth==0 || auth==1 || auth==2) && StringUtils.isEmpty(passport) && (from<=0 || to<=0)){
			ids =getIds(UserLoginRegister_List_By_Sa, new Object[]{statu,auth});
			}
			if((statu==0 || statu==1) && (auth!=0 && auth!=1 && auth!=2) && StringUtils.isEmpty(passport) && (from>=0 || to>=0)){
			ids =getIds(UserLoginRegister_List_By_Sc, new Object[]{statu,from,to});
			}
			if((statu==0 || statu==1) && (auth!=0 && auth!=1 && auth!=2) && StringUtils.isEmpty(passport) && (from<=0 || to<=0)){
			ids =getIds(UserLoginRegister_List_By_S, new Object[]{statu});
			}
			if((statu!=0 && statu!=1) && (auth==0 || auth==1 || auth==2) && StringUtils.isEmpty(passport) && (from<=0 || to<=0)){
			ids =getIds(UserLoginRegister_List_By_A, new Object[]{auth});
			}
			if((statu!=0 && statu!=1) && (auth!=0 && auth!=1 && auth!=2) && !StringUtils.isEmpty(passport) && (from<=0 || to<=0)){
			ids =getIds(UserLoginRegister_List_By_P, new Object[]{passport});
			}
			if((statu!=0 && statu!=1) && (auth!=0 && auth!=1 && auth!=2) && StringUtils.isEmpty(passport) && (from>=0 || to>=0)){
			ids =getIds(UserLoginRegister_List_By_C, new Object[]{passport});
			}
			if(ids==null || ids.size()==0 )return null;
			list=getEntityByIds(ids);
			return list;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	private static final String UserLoginRegister_List_By_Sap = "UserLoginRegister_List_By_Sap"; 
	private static final String UserLoginRegister_List_By_Sac = "UserLoginRegister_List_By_Sac"; 
	private static final String UserLoginRegister_List_By_Spc = "UserLoginRegister_List_By_Spc"; 
	private static final String UserLoginRegister_List_By_Apc = "UserLoginRegister_List_By_Apc"; 
	private static final String UserLoginRegister_List_By_Sp = "UserLoginRegister_List_By_Sp"; 
	private static final String UserLoginRegister_List_By_Sa = "UserLoginRegister_List_By_Sa"; 
	private static final String UserLoginRegister_List_By_Sc = "UserLoginRegister_List_By_Sc"; 
	private static final String UserLoginRegister_List_By_S = "UserLoginRegister_List_By_S"; 
	private static final String UserLoginRegister_List_By_A = "UserLoginRegister_List_By_A"; 
	private static final String UserLoginRegister_List_By_P = "UserLoginRegister_List_By_P"; 
	private static final String UserLoginRegister_List_By_C = "UserLoginRegister_List_By_C"; 
}
