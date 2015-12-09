package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public MappingJacksonValue getLoginThirdUrl(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "type",required = true) int type
			)throws Exception {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		try {
				String url= userLoginThirdService.getLoginThirdUrl(type);
				if(!StringUtils.isEmpty(url)){
					setMap(reusltMap,"url", url);
					setMap(reusltMap,"status",1);
				}
				logger.info("getLoginThirdUrl:"+url);
			return new MappingJacksonValue(reusltMap);
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
	public MappingJacksonValue getIdentifyingCode(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			)throws Exception {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		try {
				if(StringUtils.isEmpty(passport)){
					setMap(reusltMap, "error", "passport is null or empty.");
					setMap(reusltMap, "status", 0);
					return new MappingJacksonValue(reusltMap);
				}
				if(!isMobileNo(passport)){
					setMap(reusltMap, "error", "passport is not right phone number.");
					setMap(reusltMap, "status", 0);
					return new MappingJacksonValue(reusltMap);
				}
				if(userLoginRegisterService.passportIsExist(passport)){
					setMap(reusltMap, "error", "mobile already exists.");
					setMap(reusltMap, "status", 0);
					return new MappingJacksonValue(reusltMap);
				}
				String verificationCode=userLoginRegisterService.sendIdentifyingCode(passport);
				if(StringUtils.isEmpty(verificationCode)){
					setMap(reusltMap, "error", "failed to get the verfication code.");
					setMap(reusltMap, "status", 0);
					return new MappingJacksonValue(reusltMap);	
				}else{
					setMap(reusltMap, "verification_code", verificationCode);
					setMap(reusltMap, "status", 1);
				}
				logger.info(new StringBuffer().append("手机号:").append(passport).append(",的短信验证码为:").append(verificationCode).append(",有效期为30分钟!").toString());
			return new MappingJacksonValue(reusltMap);
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
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "loginType",required = true) String loginType
			,@RequestParam(name = "accessToken",required = true) String accessToken
			,@RequestParam(name = "openId",required = true) String openId
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "code",required = true) String code
			,@RequestParam(name = "picPath",required = true) String picPath
			,@RequestParam(name = "userType",required = true) String userType
			,@RequestParam(name = "source",required = true) String source
			,@RequestParam(name = "sex",required = true) String sex
			)throws Exception {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= new UserLoginRegister();
		UserBasic userBasic= new UserBasic();
		UserLoginThird userLoginThird= new UserLoginThird();
		String ip=getIpAddr(request);
		Long userId=0l;
		Long id=0l;
		Long id2=0l;
		try {
				//获取短信验证码开始
				if(!code.equals(userLoginRegisterService.getIdentifyingCode(passport))){
					setMap(reusltMap, "error", "code is not right");
					setMap(reusltMap, "status", 0);
					return new MappingJacksonValue(reusltMap);
				}
				//通行证是否存在开始
				if(userLoginRegisterService.passportIsExist(passport)){
					setMap(reusltMap, "error", "mobile already exists.");
					setMap(reusltMap, "status", 0);
					return new MappingJacksonValue(reusltMap);
				}
				//设置userLoginRegister开始
				userLoginRegister.setPassport(passport);
				byte[] bt = Base64.decode(password);
				String salt=userLoginRegisterService.setSalt();
				password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
				userLoginRegister.setSalt(salt);
				userLoginRegister.setPassword(password);
				userLoginRegister.setVirtual(new Byte(userType));
				userLoginRegister.setIp(ip);
				userLoginRegister.setSource(source);
				//设置userBasic开始
				userBasic.setPicPath(picPath);
				userBasic.setName(name);
				userBasic.setSex(new Byte(sex));
				userBasic.setIp(ip);
				userBasic.setStatus(new Byte("1"));
				//设置userLoginThird开始
				userLoginThird=new UserLoginThird();
				userLoginThird.setAccesstoken(accessToken);
				userLoginThird.setOpenId(openId);
				userLoginThird.setLoginType(Integer.parseInt(loginType));
				userLoginThird.setIp(ip);
				//保存userLoginRegister开始
				id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
				if(id==null || id<0L){
					setMap(reusltMap, "error", "createUserLoginRegister failed.");
					setMap(reusltMap, "status", 0);
					return new MappingJacksonValue(reusltMap);
				}
				//保存userBasic开始
				userBasic.setUserId(id);
				userId=userBasicService.createUserBasic(userBasic);
				if(userId==null || userId<0L){
					setMap(reusltMap, "error", "createUserBasic failed.");
					setMap(reusltMap, "status", 0);
					userLoginRegisterService.realDeleteUserLoginRegister(userId);
					return new MappingJacksonValue(reusltMap);
				}
				//保存userLoginThird开始
				userLoginThird.setUserId(id);
				id2=userLoginThirdService.saveUserLoginThird(userLoginThird);
				if(id2==null || id2<=0l ){
					setMap(reusltMap, "error", "updateUserLoginThird failed.");
					setMap(reusltMap, "status", 0);
					userLoginRegisterService.realDeleteUserLoginRegister(id);
					userBasicService.realDeleteUserBasic(id2);
					return new MappingJacksonValue(reusltMap);
				}
				setMap(reusltMap,"userLoginRegister",userLoginRegister);
				setMap(reusltMap,"userBasic",userBasic);
				setMap(reusltMap,"userLoginThird",userLoginThird);
				setMap(reusltMap,"status",1);
				logger.info("注册成功,用户id:"+id);
				return new MappingJacksonValue(reusltMap);
//			}
		}catch (Exception e ){
			//异常失败回滚
			if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
			if(userId!=null && userId>0l)userBasicService.realDeleteUserBasic(userId);
			if(id2!=null && id2>0l)userLoginThirdService.realDeleteUserLoginThird(id2);
			logger.info("注册失败,用户id:"+passport);
			throw e;
		}
	}
	private Map<String, Object> setMap(Map<String, Object> map,String key,Object value){
		if(!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)){
			map.put(key, value);
		}
		return map;
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
