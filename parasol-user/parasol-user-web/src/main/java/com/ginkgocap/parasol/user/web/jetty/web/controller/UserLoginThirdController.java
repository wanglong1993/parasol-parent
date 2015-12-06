package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginThirdServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;

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
	 * @throws UserLoginRegisterServiceException
	 */
	@RequestMapping(path = { "/userLoginThird/getLoginThirdUrl" }, method = { RequestMethod.GET })
	public MappingJacksonValue getLoginThirdUrl(HttpServletRequest request,HttpServletResponse response)throws UserLoginThirdServiceException {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		try {
			String requestJson=request.getParameter("requestJson").toString();
			if(StringUtils.isEmpty(requestJson)){
				notificationMap.put("error", "requestJson is null or empty.");
				notificationMap.put("status", 0);
			}else{
				JSONObject json = JSONObject.fromObject(requestJson);
				String url= userLoginThirdService.getLoginThirdUrl(json.getInt("type"));
				if(!StringUtils.isEmpty(url)){
					responseDataMap.put("url", url);
					notificationMap.put("status", 1);
				}else {
					notificationMap.put("error", "return is null or empty.");
					notificationMap.put("status", 0);
				}
				logger.info("getLoginThirdUrl:"+url);
			}
			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
		}catch (UserLoginThirdServiceException e ){
			throw e;
//			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,e));
		}
	}
	/**
	 * 第三方注册
	 * 
	 * @param request
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	@RequestMapping(path = { "/userLoginThird/register" }, method = { RequestMethod.GET})
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response)throws UserLoginThirdServiceException,UserLoginRegisterServiceException {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= new UserLoginRegister();
		UserBasic userBasic= new UserBasic();
		UserLoginThird userLoginThird= new UserLoginThird();
		
		String login_type=null;
		String access_token=null;
		String nikeName=null;
		String password=null;
		String passport=null;
		String code=null;
		
		try {
			String requestJson=request.getParameter("requestJson").toString();
			if(StringUtils.isEmpty(requestJson)){
				notificationMap.put("error", "requestJson is null or empty.");
				notificationMap.put("status", 0);
			}else{
				JSONObject json = JSONObject.fromObject(requestJson);
				if(!StringUtils.isEmpty((userLoginRegisterService.getIdentifyingCode(passport)))){
					
				}else{
					
				}
				
				;
				if(json.has("login_type"))login_type=json.getString("login_type");
				if(json.has("access_token"))access_token=json.getString("login_type");
				if(json.has("access_token"))nikeName=json.getString("login_type");
				
				String url= userLoginThirdService.getLoginThirdUrl(json.getInt("type"));
				if(!StringUtils.isEmpty(url)){
					responseDataMap.put("url", url);
					notificationMap.put("status", 1);
				}else {
					notificationMap.put("error", "return is null or empty.");
					notificationMap.put("status", 0);
				}
				logger.info("getLoginThirdUrl:"+url);
			}
			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,null));
		}catch (UserLoginRegisterServiceException e ){
			throw e;
		}catch (UserLoginThirdServiceException e ){
			throw e;
//			return new MappingJacksonValue(setResultMap(reusltMap,responseDataMap,notificationMap,e));
		}
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
}
