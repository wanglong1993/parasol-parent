package com.ginkgocap.parasol.user.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import jersey.repackaged.com.google.common.collect.Maps;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import weibo4j.model.WeiboException;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginThirdServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
import com.ginkgocap.parasol.user.thirdlogin.autho.Autho2;
import com.ginkgocap.parasol.user.thirdlogin.exception.QQException;
import com.ginkgocap.parasol.user.thirdlogin.user.OpenID;
import com.ginkgocap.parasol.user.thirdlogin.user.UserInfo;
import com.ginkgocap.parasol.user.thirdlogin.utils.ThirdLoginConstantCode;
@Service("userLoginThirdService")
public class UserLoginThirdServiceImpl extends BaseService<UserLoginThird>  implements UserLoginThirdService {
	
	private static Logger logger = Logger.getLogger(UserLoginThirdServiceImpl.class);
	@Resource
	UserBasicService userBasicService;
	@Resource
	UserLoginRegisterService userLoginRegisterService;
	public static final String USER_DEFAULT_PIC_PATH_FAMALE = "/web/pic/user/default.jpeg";//个人默认头像：女
	private static final String UserLoginThird_Map_OpenId_LoginType = "UserLoginThird_Map_OpenId_LoginType"; 
	private static final String UserLoginThird_Map_OpenId = "UserLoginThird_Map_OpenId"; 
	private static final String UserLoginThird_Map_UserId_LoginType = "UserLoginThird_Map_UserId_LoginType"; 

	@Override
	public String getLoginThirdUrl(int type)throws UserLoginThirdServiceException {
		try {
			String url=null;
			if(type!=1 && type !=2 ) throw new UserLoginThirdServiceException("value of type is must be 1 or 2.");
			if(type==1)url = new weibo4j.Oauth().authorize("code", "", "");
			else if(type==2) url = new Autho2().getRedirect();
			return  url;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public String saveUserLoginThird(int login_type, String access_token,String nikeName, String headpic)throws UserLoginThirdServiceException {
		if(login_type!=100 && login_type!=200)  throw new UserLoginThirdServiceException("login_type mus be 100 or 200.");
		if(StringUtils.isEmpty(access_token)) throw  new UserLoginThirdServiceException("access_token is null or empty.");
		if(StringUtils.isEmpty(nikeName)) throw  new UserLoginThirdServiceException("nickName is null or empty.");
		if(StringUtils.isEmpty(headpic)) throw  new UserLoginThirdServiceException("headpic is null or empty.");
		
		return null;
	}	
	private UserLoginThird checkValidity(UserLoginThird userLoginThird,int type) throws UserLoginThirdServiceException, UserLoginRegisterServiceException{
		if(userLoginThird==null)throw new UserLoginThirdServiceException("userLoginThird is null.");
		if(userLoginThird.getUserId()==null || userLoginThird.getUserId()<=0l) throw new UserLoginThirdServiceException("userId is null or less than zero.");
		if(userLoginRegisterService.getUserLoginRegister(userLoginThird.getUserId())==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
		if(StringUtils.isEmpty(userLoginThird.getOpenId())) throw new UserLoginThirdServiceException("openId is null or empty.");
		if(type==0)
		if(getUserLoginThirdByOpenId(userLoginThird.getOpenId())!=null) throw new UserLoginRegisterServiceException("openId is exists in UserLoginThird.");
		if(type==1)
		if(getUserLoginThirdByOpenId(userLoginThird.getOpenId())==null) throw new UserLoginRegisterServiceException("openId is not exists in UserLoginThird.");
		if(userLoginThird.getLoginType()!=100 && userLoginThird.getLoginType()!=200) throw new UserLoginThirdServiceException("loginType is Must be equal to 100 or 200.");
		if(StringUtils.isEmpty(userLoginThird.getAccesstoken()))throw new UserLoginThirdServiceException("accesstoken is null or empty.");
		if(StringUtils.isEmpty(userLoginThird.getIp()))throw new UserLoginThirdServiceException("ip is null or empty.");
		if(StringUtils.isEmpty(userLoginThird.getHeadPic()))throw new UserLoginThirdServiceException("headPic is null or empty.");
		if(userLoginThird.getCtime()==null || userLoginThird.getCtime()<=0l)userLoginThird.setCtime(System.currentTimeMillis());
		if(userLoginThird.getUtime()==null || userLoginThird.getUtime()<=0l)userLoginThird.setUtime(System.currentTimeMillis());	
		if(type==1)userLoginThird.setUtime(System.currentTimeMillis());	
		return userLoginThird;
	}
	
	public boolean isBinding(String openId,Byte loginType) throws UserLoginThirdServiceException{
		try {
			UserLoginThird userLoginThird = selectUserByOpenId(openId, loginType);
			if(userLoginThird!=null)
				return true;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
		return false;		
	}
	@Override
	public String isBingByUid(String openId,int loginType) throws UserLoginThirdServiceException{
		String url = "";
		try{
				UserLoginThird userLoginThird = selectUserByOpenId(openId, loginType);
				if(userLoginThird!=null ) {   
					if(ThirdLoginConstantCode.LOGIN_QQ.getKey() == loginType){
					   logger.info(ThirdLoginConstantCode.LOGIN_QQ_BIND.getDescription());
					   url = ThirdLoginConstantCode.LOGIN_QQ_BIND.getValue();
					}else{
					   logger.info(ThirdLoginConstantCode.LOGIN_WEIBO_BIND.getDescription());
					   url = ThirdLoginConstantCode.LOGIN_WEIBO_BIND.getValue();
					}
				}
				else {
					if(ThirdLoginConstantCode.LOGIN_QQ.getKey() == loginType){
					     logger.info(ThirdLoginConstantCode.LOGIN_QQ_NO_BIND.getDescription());
					     url = ThirdLoginConstantCode.LOGIN_QQ_NO_BIND.getValue();
					}
					else{
						 logger.info(ThirdLoginConstantCode.LOGIN_WEIBO_NO_BIND.getDescription());
					     url = ThirdLoginConstantCode.LOGIN_WEIBO_NO_BIND.getValue();
					}
				}
		} catch (Exception e) {
			logger.error(ThirdLoginConstantCode.LOGIN_WEIBO_ERROR.getDescription() +"或"+ThirdLoginConstantCode.LOGIN_QQ_ERROR.getDescription() +e.getMessage(),e);
			url = ThirdLoginConstantCode.LOGIN_WEIBO_ERROR.getValue();
		}
		return url;
	}


	@Override
	public Map<String, Object> binding(Long userId, UserLoginThird userLoginThird) throws UserLoginThirdServiceException {
		try {
		boolean binding_status = false;
		Map<String, Object> responseDataMap = Maps.newHashMap();
		UserLoginThird users = selectUserByOpenId(userLoginThird.getOpenId(), userLoginThird.getLoginType());
		responseDataMap.put(ThirdLoginConstantCode.LOGIN_TYPE.getValue(), userLoginThird.getLoginType()) ;
		if(null == users){
			Long id=saveUserLoginThird(userLoginThird);
			if(id!=null && id>0l )	
			binding_status = true;
		   UserBasic userBasic =userBasicService.getUserBasic(userId);
		   if(null != userBasic){
			  String default_user_image = USER_DEFAULT_PIC_PATH_FAMALE;
//			  if(null==userBasic.getPicPath() || userBasic.getPicPath().equals("") || default_user_image.equals(userBasic.getPicPath())){
//				 userBasic.setPicPath(userLoginThird.getFigureurl());
//			  }
			  if(null == userBasic.getName() || userBasic.getName().equals("")){
//				 userBasic.setName(userLoginThird.getNikeName());
			  }
			  if(binding_status) userBasicService.updateUserBasic(userBasic);
		   }
	    }
		responseDataMap.put("binding_status", binding_status);
		return responseDataMap;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public Map<String, Object> unBinding(UserLoginThird userLoginThird) throws UserLoginThirdServiceException{
		try {
	    boolean unBinding_status = true;
	    Map<String, Object> responseDataMap = Maps.newHashMap();
	    responseDataMap.put(ThirdLoginConstantCode.LOGIN_TYPE.getValue(), userLoginThird.getLoginType()) ;
	    UserLoginThird ult = selectUserByOpenId(userLoginThird.getOpenId(), userLoginThird.getLoginType());
	    if(null != ult)	ult.setOpenId("");
	    updateUserLoginThird(ult);
		responseDataMap.put("unBinding_status", unBinding_status);
		return responseDataMap;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public Map<String, Object> getThirdUserInfoByAccessToken(String access_token, int login_type) throws UserLoginThirdServiceException{
		Map<String, Object> responseDataMap = Maps.newHashMap();
		Map<String, Object> notificationMap = Maps.newHashMap();
		Map<String, Object> model = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(access_token) && login_type>0){
		   UserLoginThird userLoginThird = new UserLoginThird();	
		   userLoginThird.setAccesstoken(access_token);
		   if(new Integer(login_type).equals(String.valueOf(ThirdLoginConstantCode.LOGIN_QQ.getKey()))){ //QQ
		      try{
			     String open_id = new OpenID(access_token).getJson();
				 String openid_json = StringUtils.substringBetween(open_id, "(", ")");
				 String openId = JSONObject.fromObject(openid_json).get("openid").toString();
				 userLoginThird.setOpenId(openId);
				 userLoginThird.setLoginType(new Byte("100"));
				 UserInfo userInfo = new UserInfo(access_token, openId); 
//				 userLoginThird.setNikeName(userInfo.getNikeName());
//				 userLoginThird.setFigureurl(userInfo.getFigureurl());
				 responseDataMap.put("qqUserInfo", userInfo);
			  }
			  catch(QQException e){
				 notificationMap.put("get_qq_openid_error", e.getMessage());
				 model.put("notification", notificationMap);
				 return model;
			  }
		   } 	
		   else{	
			   try {
				   weibo4j.http.AccessToken accessTokenObj = (new weibo4j.Oauth()).getAccessTokentInfo(access_token);
				   String uid = accessTokenObj.getUid();				   
				   userLoginThird.setOpenId(uid);
				   userLoginThird.setLoginType(new Byte("100"));
				   weibo4j.Users weiboUsers = new weibo4j.Users();
				   weiboUsers.client.setToken(access_token);
				   weibo4j.model.User weibouser = weiboUsers.showUserById(uid);
//				   userLoginThird.setNikeName(weibouser.getScreenName());
//				   userLoginThird.setFigureurl(weibouser.getavatarLarge());
				   responseDataMap.put("weiboUserInfo", weibouser);
				} catch (WeiboException e) {
				   logger.error(e.getMessage(),e);
				   notificationMap.put("get_weibo_openid_error", e.getMessage());
				   model.put("notification", notificationMap);
				   return model;
				}
		   }
		   responseDataMap.put("userLoginThird", userLoginThird);
		}
		return responseDataMap;
	}


	@Override
	public Map<String, Object> registerBinding(int login_type,String access_token,String nikeName) throws UserLoginThirdServiceException{
		try{
			Map<String, Object> parameterMap = Maps.newHashMap();
			Map<String,Object> responseDataMap = getThirdUserInfoByAccessToken(access_token,login_type);
			if(null == responseDataMap.get("notification")){
			   UserLoginThird userLoginThird = (UserLoginThird) responseDataMap.get("userLoginThird");
			   parameterMap.put("userStatus", 2);
			   parameterMap.put("login_type", login_type);
			   parameterMap.put("access_token", access_token);
			   parameterMap.put("uid", userLoginThird.getOpenId());
			   if(login_type== ThirdLoginConstantCode.LOGIN_QQ.getKey()){
				  UserInfo qqUserInfo = (UserInfo)responseDataMap.get("qqUserInfo");
			      parameterMap.put("name", null != nikeName ?nikeName:qqUserInfo.getNikeName());
				  parameterMap.put("picPath", qqUserInfo.getFigureurl());
			   	  parameterMap.put("sex", qqUserInfo.getGender().equals("男")?1:2);
			   }
			   else{
			      weibo4j.model.User weiboUserInfo = (weibo4j.model.User)responseDataMap.get("weiboUserInfo");
				  parameterMap.put("name",weiboUserInfo.getScreenName());
				  parameterMap.put("picPath", weiboUserInfo.getavatarLarge());
				  parameterMap.put("sex", weiboUserInfo.getGender().equals("男")?1:2);
			   }
			}else{
				return responseDataMap;
			}
			return parameterMap;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}
	@Override
	public UserLoginThird selectUserByOpenId(String openId, int loginType) throws UserLoginThirdServiceException{
		try {
			if(StringUtils.isEmpty(openId))throw new UserLoginThirdServiceException("openId is null or empty.");
			if(loginType!=100 && loginType!=200) throw new UserLoginThirdServiceException("loginType is Must be equal to 100 or 200.");
			return getEntity((Long)getMapId(UserLoginThird_Map_OpenId_LoginType, openId,loginType));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}


	@Override
	public Long saveUserLoginThird(UserLoginThird userLoginThird) throws UserLoginThirdServiceException{
		try {
			Long id=(Long) saveEntity(checkValidity(userLoginThird,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserLoginThirdServiceException("saveUserLoginThird failed.");
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public UserLoginThird getUserLoginThirdByOpenId(String openId) throws UserLoginThirdServiceException{
		try{
			if(StringUtils.isEmpty(openId)) throw new UserLoginThirdServiceException("openId is null or empty.");
			Long id=(Long)getMapId(UserLoginThird_Map_OpenId, openId);
			if(id==null || id<=0l) return null;
			UserLoginThird userLoginThird=getEntity(id);
			if(userLoginThird==null) return null;
			try {
				if(userLoginRegisterService.getUserLoginRegister(userLoginThird.getUserId())==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
			} catch (UserLoginRegisterServiceException e) {
				e.printStackTrace();
			}
			return userLoginThird;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new UserLoginThirdServiceException(e);
		}
	}
	public UserLoginThird getUserLoginThirdByUserId(Long userId) throws UserLoginThirdServiceException{
		try{
			if(userId==null || userId<=0l) throw new UserLoginThirdServiceException("userId is null or less than zero.");
			if(userLoginRegisterService.getUserLoginRegister(userId)==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
			return getEntity((Long)getMapId(UserLoginThird_Map_UserId_LoginType, userId));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}
	public UserLoginThird getUserLoginThirdById(Long id) throws UserLoginThirdServiceException{
		try{
			if(id==null || id<=0l) throw new UserLoginThirdServiceException("id is null or less than zero.");
			UserLoginThird userLoginThird=getEntity(id);
			if(userLoginRegisterService.getUserLoginRegister(userLoginThird.getUserId())==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
			return userLoginThird; 
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}
	@Override
	public Boolean updateAccesstoken(String openId, String accesstoken) throws UserLoginThirdServiceException{
		try{
			UserLoginThird userLoginThird=getUserLoginThirdByOpenId(openId);
			userLoginThird.setAccesstoken(accesstoken);
			return updateEntity(userLoginThird);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}
	@Override
	public Boolean updateUserLoginThird(UserLoginThird userLoginThird) throws UserLoginThirdServiceException{
		try {
			if(updateEntity((checkValidity(userLoginThird,1))))return  true;
			else throw new UserLoginThirdServiceException("updateUserLoginThird failed.");
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserLoginThird(Long id)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l) throw new UserBasicServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	/**
     * @param 下载图片
     * @throws Exception
     */
    private static boolean getImages(String urlPath,String fileName) {
        URL url;
        boolean flag=true;
		try {
			url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(6 * 10000);
			if (conn.getResponseCode() < 10000) {
				InputStream inputStream = conn.getInputStream();
				byte[] data = readStream(inputStream);
				if (data.length > 0) {
					FileOutputStream outputStream = new FileOutputStream(fileName);
					outputStream.write(data);
//					log.debug("图片下载成功");
					outputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
    }
    /**
	 * 读取url中数据，并以字节的形式返回
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
    private static byte[] readStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len = inputStream.read(buffer)) !=-1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }    
}