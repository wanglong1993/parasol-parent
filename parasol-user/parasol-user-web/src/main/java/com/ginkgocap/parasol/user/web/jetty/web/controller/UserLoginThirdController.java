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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
//				Cookie[] cookies = request.getCookies();
//				for (Cookie cookie : cookies) {
//					if(cookie.getName().equals("lastTime"))logger.info("lastTime:"+cookie.getValue());
//				}
				String url= userLoginThirdService.getLoginThirdUrl(type);
				if(!StringUtils.isEmpty(url)){
					resultMap.put("url", url);
					resultMap.put("status",1);
				}
				logger.info("getLoginThirdUrl:"+url);
//				Cookie ck=new Cookie("lastTime",Long.toString(System.currentTimeMillis()/1000));
//				ck.setMaxAge(60*60*24*365);
//				response.addCookie(ck);
			return new MappingJacksonValue(resultMap);
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
				if(StringUtils.isEmpty(passport)){
					resultMap.put( "error", "passport is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!isMobileNo(passport)){
					resultMap.put( "error", "passport is not right phone number.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(userLoginRegisterService.passportIsExist(passport)){
					resultMap.put( "error", "mobile already exists.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				String code=userLoginRegisterService.sendIdentifyingCode(passport);
				if(StringUtils.isEmpty(code)){
					resultMap.put( "error", "failed to get the verfication code.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);	
				}else{
					resultMap.put( "code", code);
					resultMap.put( "status", 1);
				}
				logger.info(new StringBuffer().append("手机号:").append(passport).append(",的短信验证码为:").append(code).append(",有效期为30分钟!").toString());
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			throw e;
		}
	}
	/**
	 * 第三方注册绑定
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginThird/bind" }, method = { RequestMethod.GET})
	public MappingJacksonValue bind(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "loginType",required = true) String loginType
		,@RequestParam(name = "accessToken",required = true) String accessToken
		,@RequestParam(name = "openId",required = true) String openId
		,@RequestParam(name = "name",required = true) String name
		,@RequestParam(name = "password",required = true) String password
		,@RequestParam(name = "passport",required = true) String passport
		,@RequestParam(name = "code",required = true) String code
		,@RequestParam(name = "headPic",required = true) String headPic
		,@RequestParam(name = "userType",required = true) String userType
		,@RequestParam(name = "source",required = true) String source
		,@RequestParam(name = "sex",required = true) String sex
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= new UserLoginRegister();
		UserBasic userBasic= new UserBasic();
		UserLoginThird userLoginThird= new UserLoginThird();
		MappingJacksonValue mappingJacksonValue=null;
		String ip=getIpAddr(request);
		Long userId=0l;
		Long id=0l;
		Long id2=0l;
		try {   
				userLoginThird=userLoginThirdService.getUserLoginThirdByOpenId(openId);
				boolean exists=userLoginRegisterService.passportIsExist(passport);
				//没有绑定过且是新注册帐号
				if(userLoginThird==null && exists){
					//检查短信验证码
					if(!code.equals(userLoginRegisterService.getIdentifyingCode(passport))){
						resultMap.put( "error", "code is not right");
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
					//设置userLoginRegister开始
					userLoginRegister.setPassport(passport);
					byte[] bt = Base64.decode(password);
					String salt=userLoginRegisterService.setSalt();
					password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
					userLoginRegister.setSalt(salt);
					userLoginRegister.setPassword(password);
					userLoginRegister.setUsetType(new Byte(userType));
					userLoginRegister.setIp(ip);
					userLoginRegister.setSource(source);
					//设置userBasic开始
					userBasic.setName(name);
					userBasic.setSex(new Byte(sex));
					userBasic.setIp(ip);
					userBasic.setStatus(new Byte("1"));
					//设置userLoginThird开始
					userLoginThird= new UserLoginThird();
					userLoginThird.setAccesstoken(accessToken);
					userLoginThird.setOpenId(openId);
					userLoginThird.setLoginType(Integer.parseInt(loginType));
					userLoginThird.setIp(ip);
					userLoginThird.setHeadPic(headPic);
					//保存userLoginRegister开始
					id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
					//保存userBasic开始
					userBasic.setUserId(id);
					userId=userBasicService.createUserBasic(userBasic);
					//保存userLoginThird开始
					userLoginThird.setUserId(id);
					id2=userLoginThirdService.saveUserLoginThird(userLoginThird);
					
					resultMap.put("userLoginRegister",userLoginRegister);
					resultMap.put("userBasic",userBasic);
					resultMap.put("userLoginThird",userLoginThird);
					resultMap.put("status",1);
					logger.info("第三注册绑定新用户成功,用户id:"+id);
					return new MappingJacksonValue(resultMap);
				}
				//没有绑定过绑定一个已经存在的帐号
				if(userLoginThird==null && !exists){
					mappingJacksonValue=bindExistsPassport(request,response,loginType, accessToken,openId,password,passport,headPic);
				}
				//绑定过就直接登录
				if(userLoginThird!=null){
					mappingJacksonValue=login(request,response,loginType, accessToken,openId,headPic,name);
				}
				return mappingJacksonValue;
		}catch (Exception e ){
			//异常失败回滚
			if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
			if(userId!=null && userId>0l)userBasicService.realDeleteUserBasic(userId);
			if(id2!=null && id2>0l)userLoginThirdService.realDeleteUserLoginThird(id2);
			logger.info("第三方注册失败:"+passport);
			throw e;
		}
	}
	/**
	 * 注册时绑定已经存在的用户
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginThird/bindExistsPassport" }, method = { RequestMethod.GET})
	public MappingJacksonValue bindExistsPassport(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "loginType",required = true) String loginType
		,@RequestParam(name = "accessToken",required = true) String accessToken
		,@RequestParam(name = "openId",required = true) String openId
		,@RequestParam(name = "password",required = true) String password
		,@RequestParam(name = "passport",required = true) String passport
		,@RequestParam(name = "headPic",required = true) String headPic
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister=null;
		UserBasic userBasic= null;
		UserLoginThird userLoginThird= null;
		String ip=getIpAddr(request);
		Long id=0l;
		try {
				//判断用户密码是否正确
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
				userLoginRegister.setPassport(passport);
				byte[] bt = Base64.decode(password);
				String salt=userLoginRegister.getSalt();
				String tablePssword=userLoginRegisterService.setSha256Hash(salt, new String(bt));
				if(!userLoginRegister.getPassword().equals(tablePssword)){
					resultMap.put( "error", "password is error");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//判断用户的状态是否正常
				userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
				int status=userBasic.getStatus().intValue();
				if(status==0 || status==-1 || status==2  ){
					resultMap.put( "error", "user have been logic deleted or locked or canceled");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//查找此passport之前是否绑定过
				userLoginThird=userLoginThirdService.getUserLoginThirdByOpenId(openId);
				//已经绑定
				if(userLoginThird!=null && userLoginThird.getUserId().equals(userLoginRegister.getId())){
					resultMap.put("status",1);
					logger.info("第三方注册绑定已经存在的用户成功,用户passport:"+passport);
					return new MappingJacksonValue(resultMap);
				}
				//存在openid但是没有绑定过
				if(userLoginThird!=null && !userLoginThird.getUserId().equals(userLoginRegister.getId())){
					userLoginThird.setUserId(userLoginRegister.getId());
					resultMap.put("status",1);
					userLoginThirdService.updateUserLoginThird(userLoginThird);
					logger.info("第三方注册绑定已经存在的用户成功,用户passport:"+passport);
					return new MappingJacksonValue(resultMap);
				}
				//用户从来没有绑定过,设置userLoginThird开始
				userLoginThird=new UserLoginThird();
				userLoginThird.setAccesstoken(accessToken);
				userLoginThird.setOpenId(openId);
				userLoginThird.setLoginType(Integer.parseInt(loginType));
				userLoginThird.setIp(ip);
				userLoginThird.setHeadPic(headPic);
				userLoginThird.setUserId(userLoginRegister.getId());
				//更新userLoginRegister开始
				userLoginRegisterService.updateIpAndLoginTime(userLoginRegister.getId(), ip);
				//更新userBasic开始
				userBasic.setIp(ip);
				userBasicService.updateUserBasic(userBasic);
				//创建userLoginThird开始
				id=userLoginThirdService.saveUserLoginThird(userLoginThird);
				resultMap.put("userLoginRegister",userLoginRegister);
				resultMap.put("userBasic",userBasic);
				resultMap.put("userLoginThird",userLoginThird);
				resultMap.put("status",1);
				logger.info("第三方注册绑定已经存在的用户成功,用户passport:"+passport);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//异常失败回滚
			if(id!=null && id>0l)userLoginThirdService.realDeleteUserLoginThird(id);
			logger.info("第三方注册绑定已经存在的用户失败,用户passport:"+passport);
			throw e;
		}
	}
	/**
	 * 设置里面解绑第三方登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginThird/unbindExistPassport" }, method = { RequestMethod.GET})
	public MappingJacksonValue unbindExistPassport(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "passport",required = true) String passport
		,@RequestParam(name = "openId",required = true) String openId
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister=null;
		UserLoginThird userLoginThird= null;
		try {
				//通行证获取已经存在的登录注册用户
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
				//查找此passport之前是否绑定过
				userLoginThird=userLoginThirdService.getUserLoginThirdByOpenId(openId);
				if(userLoginThird==null){
					resultMap.put( "error", "openId never bind in userLoginThird.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!userLoginThird.getUserId().equals(userLoginRegister.getId())){
					resultMap.put( "error", "the userId in the userLoginRegister is different from the userId in the userLoginThird.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//删除解绑
				userLoginThirdService.realDeleteUserLoginThird(userLoginThird.getId());
				resultMap.put("status",1);
				logger.info("解绑成功,用户passport:"+passport);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//异常失败回滚
			logger.info("解绑失败,用户passport:"+passport);
			throw e;
		}
	}	
	/**
	 * 第三方登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginThird/login" }, method = { RequestMethod.GET })
	public MappingJacksonValue login(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "loginType",required = true) String loginType
			,@RequestParam(name = "accessToken",required = true) String accessToken
			,@RequestParam(name = "openId",required = true) String openId
			,@RequestParam(name = "headPic",required = true) String headPic
			,@RequestParam(name = "name",required = true) String name
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UserLoginThird userLoginThird= userLoginThirdService.getUserLoginThirdByOpenId(openId);
			UserLoginRegister userLoginRegister=userLoginRegisterService.getUserLoginRegister(userLoginThird.getUserId());
			UserBasic userBasic=userBasicService.getUserBasic(userLoginThird.getUserId());
			String ip=getIpAddr(request);
			//每次第三方登录,检查是否需要更新昵称和头像
			if(!StringUtils.isEmpty(headPic) && userLoginThird.getHeadPic()!=headPic)userLoginThird.setHeadPic(headPic);
			if(!StringUtils.isEmpty(accessToken) && userLoginThird.getAccesstoken()!=accessToken){
				userLoginThird.setAccesstoken(accessToken);
				userLoginThird.setIp(ip);
				userLoginThirdService.updateUserLoginThird(userLoginThird);	
			}
			if(!StringUtils.isEmpty(headPic) && userLoginThird.getHeadPic()!=headPic){
				userBasic.setName(name);
				userBasic.setIp(ip);
				userBasicService.updateUserBasic(userBasic);
			}
			userLoginRegisterService.updateIpAndLoginTime(userLoginRegister.getId(), ip);
			resultMap.put("userLoginRegister",userLoginRegister);
			resultMap.put("userBasic",userBasic);
			resultMap.put("userLoginThird",userLoginThird);
			resultMap.put("status",1);
			logger.info("/userLoginThird/login:success");
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("/userLoginThird/login:error");
			throw e;
		}
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
