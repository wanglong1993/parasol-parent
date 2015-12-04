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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

/**
 * 用户登录注册
 */
@RestController
public class UserLoginRegisterController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserLoginRegisterController.class);
	@Autowired
	private UserLoginRegisterService userLoginRegisterService;
	/**
	 * 用户注册
	 * 
	 * @param request
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	@RequestMapping(path = { "/userLoginRegister/register" }, method = { RequestMethod.GET })
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response)throws UserLoginRegisterServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			Long id=0l;
			Map<String, Object> reusltMap = new HashMap<String, Object>();
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			Map<String, Object> notificationMap = new HashMap<String, Object>();
			String requestJson=request.getParameter("requestJson").toString();
			if(StringUtils.isEmpty(requestJson)){
				notificationMap.put("json_error", "requestJson is null or empty.");
			}else{
				JSONObject json = JSONObject.fromObject(requestJson);
//				id=userLoginRegisterService.createUserLoginRegister(setUserLoginRegister(json.getString("passport"),new Byte(json.getString("virtual"))));
				userLoginRegisterService.sendIdentifyingCode("13677687632");
				UserLoginRegister ulr=userLoginRegisterService.getUserLoginRegister(3913092224516104l);
				ulr.getUserName();
				
			// 2.转成框架数据
			}
			
			reusltMap.put("responseData", responseDataMap);
			reusltMap.put("notification", notificationMap);
			reusltMap.put("id", id);
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		}catch (UserLoginRegisterServiceException e ){
			throw e;
		}
	}
	/**
	 * 初始化登录注册用户对象
	 * @return userLoginRegister
	 */
	public UserLoginRegister setUserLoginRegister(String passport,Byte virtual){
		try {
			Long ctime=System.currentTimeMillis();
			UserLoginRegister userLoginRegister = new UserLoginRegister();
			userLoginRegister.setPassport(passport);
			userLoginRegister.setVirtual(virtual);;
			String salt=userLoginRegisterService.setSalt();
			String password=userLoginRegisterService.setSha256Hash(salt, "123456");
			userLoginRegister.setSalt(salt);
			userLoginRegister.setPassword(password);
			userLoginRegister.setSource("app");
			userLoginRegister.setIp("111.111.11.11");
			userLoginRegister.setCtime(ctime);
			userLoginRegister.setUtime(ctime);
			return userLoginRegister;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}
