package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Base64;

/**
 * 用户登录注册
 */
@RestController
public class UserLoginRegisterController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserLoginRegisterController.class);
	@Autowired
	private UserLoginThirdService userLoginThirdService;
	@Autowired
	private UserLoginRegisterService userLoginRegisterService;
	@Autowired
	private UserBasicService userBasicService;
	@Autowired
	private UserOrganBasicService userOrganBasicService;
	/**
	 * 用户注册
	 * 
	 * @param type 1.邮箱注册,2.手机注册
	 * @param passport 为邮箱和手机号
	 * @param userType 1.个人用户,2.组织用户
	 * @source Auth认正的appkey
	 * @param 用户昵称
	 * @param industryId 用户感兴趣的行业
	 * @throws UserLoginRegisterServiceException
	 */
	@RequestMapping(path = { "/userLoginRegister/register" }, method = { RequestMethod.GET })
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "type",required = true) int type
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "userType",required = true) String userType
			,@RequestParam(name = "source",required = true) String source
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "industryId",required = true) String industryId
			)throws UserLoginRegisterServiceException {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= new UserLoginRegister();
		UserOrganBasic userOrganBasic= new UserOrganBasic();
		UserBasic userBasic= new UserBasic();
		UserLoginThird userLoginThird= new UserLoginThird();
		MappingJacksonValue mappingJacksonValue=null;
		String ip=getIpAddr(request);
		Long userId=0l;
		Long id=0l;
		Long id2=0l;
		try {
				boolean exists=userLoginRegisterService.passportIsExist(passport);
				if(exists){
					if(type==1)reusltMap.put( "error", "email already exists.");
					if(type==2)reusltMap.put( "error", "mobile already exists.");
					reusltMap.put( "status", 0);
					return new MappingJacksonValue(reusltMap);
				}
				//个人用户邮箱注册
				if(type==1 && userType=="1"){
					userLoginRegister.setPassport(passport);
					byte[] bt = Base64.decode(password);
					String salt=userLoginRegisterService.setSalt();
					password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
					userLoginRegister.setSalt(salt);
					userLoginRegister.setPassword(password);
					userLoginRegister.setVirtual(new Byte(userType));
					userLoginRegister.setIp(ip);
					userLoginRegister.setSource(source);
					id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
			        Map<String, Object> map = new HashMap<String, Object>();
			        map.put("email", "http://www.gintong.com/userLoginRegister/verification?eamil="+Base64.decode(passport.getBytes()));
			        map.put("acceptor",passport);
			        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
					if(userLoginRegisterService.sendEmail(passport, type, map)){
						reusltMap.put( "message", "send mail success");
						reusltMap.put( "status", 1);
					}else{
						reusltMap.put( "error", "send email failed.");
						reusltMap.put( "status", 1);
					}
					return new MappingJacksonValue(reusltMap);
				}
				//组织用户邮箱注册
				if(type==1 && userType=="2"){
					userLoginRegister.setPassport(passport);
					byte[] bt = Base64.decode(password);
					String salt=userLoginRegisterService.setSalt();
					password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
					userLoginRegister.setSalt(salt);
					userLoginRegister.setPassword(password);
					userLoginRegister.setVirtual(new Byte(userType));
					userLoginRegister.setIp(ip);
					userLoginRegister.setSource(source);
					id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
					userOrganBasic.setUserId(id);
					userOrganBasic.setName(name);
					userId=userOrganBasicService.createUserOrganBasic(userOrganBasic);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("email", "http://www.gintong.com/userLoginRegister/verification?eamil="+Base64.decode(passport.getBytes()));
					map.put("acceptor",passport);
					map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
					if(userLoginRegisterService.sendEmail(passport, type, map)){
						reusltMap.put( "message", "send mail success");
						reusltMap.put( "status", 1);
					}else{
						reusltMap.put( "error", "send email failed.");
						reusltMap.put( "status", 1);
					}
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
				
				reusltMap.put("userLoginRegister",userLoginRegister);
				reusltMap.put("userBasic",userBasic);
				reusltMap.put("userLoginThird",userLoginThird);
				reusltMap.put("status",1);
				logger.info("第三注册绑定新用户成功,用户id:"+id);
				return new MappingJacksonValue(reusltMap);
				}
				

				reusltMap.put("status", 1);
				logger.info("注册成功:"+passport);
			return new MappingJacksonValue(reusltMap);
		}catch (UserLoginRegisterServiceException e ){
			logger.info("注册失败:"+passport);
			throw e;
		}
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
