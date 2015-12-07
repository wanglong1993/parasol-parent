package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ginkgocap.parasol.user.exception.UserLoginThirdServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Base64;

/**
 * 第三方注册和登录
 */
@RestController
public class UserLoginThirdController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserLoginThirdController.class);
	@Autowired
	private UserLoginThirdService userLoginThirdService;
	@Autowired
	private UserLoginRegisterService userLoginRegisterService;
	@Autowired
	private UserBasicService userBasicService;
	/**
	 * 获取第三方登录url地址
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginThird/getLoginThirdUrl" }, method = { RequestMethod.GET })
	public MappingJacksonValue getLoginThirdUrl(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		try {
			String requestJson= checkRequestJson(notificationMap,request);
			if(!StringUtils.isEmpty(requestJson)){
				JSONObject json = JSONObject.fromObject(requestJson);
				String url= userLoginThirdService.getLoginThirdUrl(json.has("type")?json.getInt("type"):0);
				if(!StringUtils.isEmpty(url)){
					setMap(responseDataMap,"url", url);
					setMap(notificationMap,"status",1);
				}
				logger.info("getLoginThirdUrl:"+url);
			}
			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
		}catch (Exception e ){
			throw e;
		}
	}

	/**
	 * 注册获取手机验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginThird/getIdentifyingCode" }, method = { RequestMethod.GET})
	public MappingJacksonValue getIdentifyingCode(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		
		try {
			String requestJson= checkRequestJson(notificationMap,request);
			if(!StringUtils.isEmpty(requestJson)){
				JSONObject json = JSONObject.fromObject(requestJson);
				String passport=json.has("passport")?json.getString("passport"):null;
				if(StringUtils.isEmpty(passport)){
					setMap(notificationMap, "error", "passport is null or empty.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				if(!isMobileNo(passport)){
					setMap(notificationMap, "error", "passport is not right phone number.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				if(userLoginRegisterService.passportIsExist(passport)){
					setMap(notificationMap, "error", "mobile already exists.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				String verificationCode=userLoginRegisterService.sendIdentifyingCode(passport);
				if(StringUtils.isEmpty(verificationCode)){
					setMap(notificationMap, "error", "failed to get the verfication code.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));	
				}else{
					setMap(responseDataMap, "verification_code", verificationCode);
					setMap(responseDataMap, "status", 1);
				}
				logger.info(new StringBuffer().append("手机号:").append(passport).append(",的短信验证码为:").append(verificationCode).append(",有效期为30分钟!").toString());
			}
			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
		}catch (Exception e ){
			throw e;
		}
	}
	/**
	 * 第三方注册
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginThird/register" }, method = { RequestMethod.GET})
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= new UserLoginRegister();
		UserBasic userBasic= new UserBasic();
		UserLoginThird userLoginThird= new UserLoginThird();
		MappingJacksonValue mappingJacksonValue=null;
		String login_type=null;
		String access_token=null;
		String openid=null;
		String nikeName=null;
		String password=null;
		String passport=null;
		String verificationCode=null;
		String thumbnailPic=null;
		String userType=null;
		try {
			String requestJson= checkRequestJson(notificationMap,request);
			if(!StringUtils.isEmpty(requestJson)){
				JSONObject json = JSONObject.fromObject(requestJson);
				mappingJacksonValue=checkParameter("verificationCode",verificationCode,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("passport",passport,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("login_type",login_type,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("access_token",access_token,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("nikeName",nikeName,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("password",password,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("thumbnailPic",thumbnailPic,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("openid",openid,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("userType",userType,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				
				if(verificationCode!=(userLoginRegisterService.getIdentifyingCode(passport))){
					setMap(notificationMap, "error", "verificationCode is not right");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				if(userLoginRegisterService.passportIsExist(passport)){
					setMap(notificationMap, "error", "mobile already exists.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				if(Integer.parseInt(login_type)!=100 && Integer.parseInt(login_type)!=200){
					setMap(notificationMap, "error", "login_type is must be 100 or 200.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				userLoginRegister.setPassport(passport);
				byte[] bt = Base64.decode(password);
				String salt=userLoginRegisterService.setSalt();
				password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
				userLoginRegister.setSalt(salt);
				userLoginRegister.setPassword(password);
				userLoginRegister.setVirtual(new Byte(userType));
				
				userBasic.setPicPath(thumbnailPic);
				userBasic.setName(nikeName);
				
				userLoginThird.setAccesstoken(access_token);
				userLoginThird.setOpenId(openid);
				userLoginThird.setLoginType(Integer.parseInt(login_type));
				
				String url= userLoginThirdService.getLoginThirdUrl(json.getInt("type"));
				
				if(!StringUtils.isEmpty(url)){
					responseDataMap.put("url", url);
					notificationMap.put("status", 1);
				}else {
//					setNotificationMap(notificationMap,0,"return is null or empty.");
				}
//				if(ObjectUtils.isEmpty(userLoginThirdService.getUserLoginThirdByOpenId(openid))){
//				setMap(notificationMap, "error", "passport already exists.");
//				setMap(notificationMap, "status", 0);
//				return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
//			}				
				logger.info("getLoginThirdUrl:"+url);
			}
			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
		}catch (Exception e ){
			throw e;
		}
	}
	
	private MappingJacksonValue checkParameter(String key,String value,JSONObject json,Map<String, Object> reusltMap,Map<String, Object> notificationMap,Map<String, Object> responseDataMap){
		String va=json.has(key)?json.getString(key):null;
		if(StringUtils.isEmpty(va)){
			setMap(notificationMap, "error", key+ "is null or empty.");
			setMap(notificationMap, "status", 0);
			value=va;
			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
		}
		return null;
	}
	private Map<String, Object>  setResultMap(Map<String, Object> reusltMap,Map<String, Object> responseDataMap,Map<String, Object> notificationMap,Exception e){
		if(e!=null){
			e.printStackTrace();
			if(e instanceof  UserLoginThirdServiceException){
				notificationMap.put("error", e.getMessage());	
			}else {
				notificationMap.put("error", "System internal error");
			}
			notificationMap.put("status", 0);
		}
		reusltMap.put("responseData", responseDataMap);
		reusltMap.put("notification", notificationMap);
		return reusltMap;
	}
	private Map<String, Object> setMap(Map<String, Object> map,String key,Object value){
		if(!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)){
			map.put(key, value);
		}
		return map;
	}
	private String  checkRequestJson(Map<String, Object> notificationMap,HttpServletRequest request){
		String requestJson=request.getParameter("requestJson").toString();
		if(StringUtils.isEmpty(requestJson)){
			notificationMap.put("error", "requestJson is null or empty.");
			notificationMap.put("status", 0);
		}	
		return requestJson;
	}
	/**
	 * 验证是否是手机号码
	 * 验证号段截止2015.11.26,如今后有新的号段进来,会在正则表达式中添加
	 * @param mobile
	 * @return
	 */
	private  boolean isMobileNo(String mobile){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]|(17[6,7,8])|14[5,7]))\\d{8}$");   
		Matcher m = p.matcher(mobile);  
		return m.matches();  
	}
}
