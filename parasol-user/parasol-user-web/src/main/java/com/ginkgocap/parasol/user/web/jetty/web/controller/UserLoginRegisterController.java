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
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "type",required = true) int type
			,@RequestParam(name = "passport",required = true) String passport
			)throws UserLoginRegisterServiceException {
		try {
			Map<String, Object> reusltMap = new HashMap<String, Object>();
//				id=userLoginRegisterService.createUserLoginRegister(setUserLoginRegister(json.getString("passport"),new Byte(json.getString("virtual"))));
//				String identifyingCode= userLoginRegisterService.sendIdentifyingCode("13677687632");
//				UserLoginRegister ulr=userLoginRegisterService.getUserLoginRegister(3913092224516104l);
//				ulr.getUserName();
//				logger.info("你的短信验证码为:"+identifyingCode+",有效时间1分钟");
		        Map<String, Object> map = new HashMap<String, Object>();
		        map.put("email", "http://www.gintong.com#/verify?from=1&type=1&e=Y2Nra2t0dEAxMjYuY29t&email=MTUxMjEwMTUxMzE5MTAxPV89Y2Nra2t0dCU0MDEyNi5jb20=");
		        map.put("acceptor",passport);
		        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
				userLoginRegisterService.sendEmail(passport, type, map);
				reusltMap.put("status", 1);
			return new MappingJacksonValue(reusltMap);
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
