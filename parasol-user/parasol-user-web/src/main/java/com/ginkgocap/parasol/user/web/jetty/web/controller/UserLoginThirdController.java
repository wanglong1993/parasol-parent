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
		String loginType=null;
		String accessToken=null;
		String openId=null;
		String nikeName=null;
		String password=null;
		String passport=null;
		String verificationCode=null;
		String thumbnailPic=null;
		String userType=null;
		String gender=null;
		Long userId=0l;
		try {
			String requestJson= checkRequestJson(notificationMap,request);
			if(!StringUtils.isEmpty(requestJson)){
				//验证参数开始
				JSONObject json = JSONObject.fromObject(requestJson);
				mappingJacksonValue=checkParameter("verificationCode",verificationCode,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("passport",passport,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("loginType",loginType,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("accessToken",accessToken,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("nikeName",nikeName,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("password",password,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("thumbnailPic",thumbnailPic,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("openId",openId,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("userType",userType,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				mappingJacksonValue=checkParameter("gender",gender,json,reusltMap, notificationMap,responseDataMap);
				if(ObjectUtils.isEmpty(mappingJacksonValue))return mappingJacksonValue;
				//验证参数结束
				
				//获取短信验证码开始
				if(verificationCode!=(userLoginRegisterService.getIdentifyingCode(passport))){
					setMap(notificationMap, "error", "verificationCode is not right");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				//获取短信验证码结束
				
				//通行证是否存在开始
				if(userLoginRegisterService.passportIsExist(passport)){
					setMap(notificationMap, "error", "mobile already exists.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				//通行证是否存在结束
				
				//验证login_type取值开始
				if(Integer.parseInt(loginType)!=100 && Integer.parseInt(loginType)!=200){
					setMap(notificationMap, "error", "login_type is must be 100 or 200.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				//验证login_type取值结束
				
				//保存userLoginRegister开始
				userLoginRegister.setPassport(passport);
				byte[] bt = Base64.decode(password);
				String salt=userLoginRegisterService.setSalt();
				password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
				userLoginRegister.setSalt(salt);
				userLoginRegister.setPassword(password);
				userLoginRegister.setVirtual(new Byte(userType));
				userLoginRegister.setIp(getIpAddr(request));
				userId=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
				if(userId==null || userId<0L){
					setMap(notificationMap, "error", "createUserLoginRegister failed.");
					setMap(notificationMap, "status", 0);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				//保存userLoginRegister结束
				
				//保存userBasic开始
				userBasic.setPicPath(thumbnailPic);
				userBasic.setName(nikeName);
				userBasic.setUserId(userId);
				userBasic.setSex(new Byte(gender));
				
				userId=userBasicService.createUserBasic(userBasic);
				if(userId==null || userId<0L){
					setMap(notificationMap, "error", "createUserBasic failed.");
					setMap(notificationMap, "status", 0);
					userLoginRegisterService.realDeleteUserLoginRegister(userId);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				//保存userBasic结束
				
				//更新userLoginThird开始
				if(userId!=null && userId>0L)userLoginThird=userLoginThirdService.getUserLoginThirdByOpenId(openId);
				if(!ObjectUtils.isEmpty(userLoginThird)){
					userLoginThird.setAccesstoken(accessToken);
					userLoginThird.setOpenId(openId);
					userLoginThird.setLoginType(Integer.parseInt(loginType));
					if(!userLoginThirdService.updateUserLoginThird(userLoginThird)){
						setMap(notificationMap, "error", "updateUserLoginThird failed.");
						setMap(notificationMap, "status", 0);
						userLoginRegisterService.realDeleteUserLoginRegister(userId);
						userBasicService.realDeleteUserBasic(userId);
						return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
					}
				}
				//更新userLoginThird结束
				
				//保存userLoginThird开始
				userLoginThird.setAccesstoken(accessToken);
				userLoginThird.setOpenId(openId);
				userLoginThird.setLoginType(Integer.parseInt(loginType));
				userId=userLoginThirdService.saveUserLoginThird(userLoginThird);
				if(userId==null || userId<=0l ){
					setMap(notificationMap, "error", "updateUserLoginThird failed.");
					setMap(notificationMap, "status", 0);
					userLoginRegisterService.realDeleteUserLoginRegister(userId);
					userBasicService.realDeleteUserBasic(userId);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}else{
					setMap(responseDataMap,"userLoginRegister",userLoginRegister);
					setMap(responseDataMap,"userBasic",userBasic);
					setMap(responseDataMap,"userLoginThird",userLoginThird);
					setMap(responseDataMap,"status",1);
					logger.info("注册成功,用户id:"+userId);
					return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
				}
				//保存userLoginThird结束
				
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
	/**
	 * 获取真实IP的方法
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}	
}
