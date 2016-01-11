package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.model.UserExt;
import com.ginkgocap.parasol.user.model.UserFriendly;
import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;
import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.model.UserOrganExt;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserDefinedService;
import com.ginkgocap.parasol.user.service.UserExtService;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
import com.ginkgocap.parasol.user.service.UserOrgPerCusRelService;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;
import com.ginkgocap.parasol.user.service.UserOrganExtService;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Base64;

/**
 * 用户登录注册
 */
@RestController
public class UserController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UserLoginThirdService userLoginThirdService;
	@Autowired
	private UserLoginRegisterService userLoginRegisterService;
	@Autowired
	private UserBasicService userBasicService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private UserOrganExtService userOrganExtService;
	@Autowired
	private UserOrganBasicService userOrganBasicService;
	@Autowired
	private UserInterestIndustryService userInterestIndustryService;
	@Autowired
	private UserDefinedService userDefinedService;
	@Autowired
	private UserFriendlyService userFriendlyService;
	@Autowired
	private UserOrgPerCusRelService userOrgPerCusRelService;
	@Value("${user.web.url}")  
    private String userWebUrl;  

	/**
	 * 用户注册
	 * 
	 * @param type 1.邮箱注册,2.手机注册
	 * @param code 手机验证码
	 * @param passport 为邮箱和手机号
	 * @param password 用户密码
	 * @param userType 0.个人用户,1.组织用户
	 * @param firstIndustryIds 一级行业ID数组
	 * @param name 昵称,企业全称
	 * @param source  来源的appkey
	 * @param shortName 企业简称
	 * @picId 个人或组织LOGOID
	 * @param businessLicencePicId 企业执照图片ID
	 * @param stockCode 股票代码
	 * @param orgType 组织类型
	 * @param companyContactsMobile 组织联系人电话
	 * @param companyContacts   组织联系人
	 * @param idcardFrontPicId   组织联系人身份证正面照片id
	 * @param idcardBackPicId  联系人身份证背面照片id
	 * @throws Exception
	 * @return MappingJacksonValue
	 * http://www.jsjtt.com/java/Javakuangjia/67.html
	 */
	@RequestMapping(path = { "/user/register" }, method = { RequestMethod.POST })
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "type",required = true) int type
			,@RequestParam(name = "code",required = false) String code
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "userType",required = true) String userType
			,@RequestParam(name = "firstIndustryIds", required = true) Long[] firstIndustryIds
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "source",required = true) String source
			,@RequestParam(name = "shortName",required = true) String shortName
			,@RequestParam(name = "picId",required = false) Long picId
			,@RequestParam(name = "businessLicencePicId",required = true) Long businessLicencePicId
			,@RequestParam(name = "stockCode",required = false) String stockCode
			,@RequestParam(name = "orgType",required = true) String orgType
			,@RequestParam(name = "companyContactsMobile",required = true) String companyContactsMobile
			,@RequestParam(name = "companyContacts",required = true) String companyContacts
			,@RequestParam(name = "idcardFrontPicId",required = true) Long idcardFrontPicId
			,@RequestParam(name = "idcardBackPicId",required = true) Long idcardBackPicId
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserOrganBasic userOrganBasic= null;
		UserOrganExt userOrganExt= null;
		UserBasic userBasic= null;
		UserExt userExt= null;
		UserInterestIndustry userInterestIndustry= null;
		List<UserInterestIndustry> list = null;
		String ip=getIpAddr(request);
		Long userBasicId=0l;
		Long userExtId=0l;
		Long userOrganBasicId=0l;
		Long userOrganExtId=0l;
		Long id=0l;
		try {
				boolean exists=userLoginRegisterService.passportIsExist(passport);
				if(exists){
					if(type==1)resultMap.put( "error", "email already exists.");
					if(type==2)resultMap.put( "error", "mobile already exists.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//个人用户组织用户邮箱注册
				if((type==1 && userType.equals("0")) ||(type==1 && userType.equals("1"))){
					if(!isEmail(passport)){
						resultMap.put( "error", "email format is not correct.");
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				//个人用户手机注册
				if(type==2 && userType.equals("0")){
					if(!code.equals(userLoginRegisterService.getIdentifyingCode(passport))){
						resultMap.put( "error", "code is not right");
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				userLoginRegister= new UserLoginRegister();
				userLoginRegister.setPassport(passport);
				byte[] bt = Base64.decode(password);
				String salt=userLoginRegisterService.setSalt();
				password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
				userLoginRegister.setSalt(salt);
				userLoginRegister.setPassword(password);
				userLoginRegister.setUsetType(new Byte(userType));
				userLoginRegister.setIp(ip);
				userLoginRegister.setSource(source);
				userLoginRegister.setCtime(System.currentTimeMillis());
				userLoginRegister.setUtime(System.currentTimeMillis());
				if(type==1)userLoginRegister.setEmail(passport);
				if(type==2)userLoginRegister.setMobile((passport));
				id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
				//个人用户邮箱注册
				if((type==1 && userType.equals("0"))){
					userBasic= new UserBasic();
					userBasic.setName(name);
					userBasic.setMobile(passport);
					userBasic.setPicId(picId);
					userBasic.setStatus(new Byte("1"));
					userBasic.setSex(new Byte("1"));
					userBasic.setUserId(id);
					userBasic.setAuth(new Byte("0"));
					userBasicId=userBasicService.createUserBasic(userBasic);
			        Map<String, Object> map = new HashMap<String, Object>();
			        map.put("email", userWebUrl+"/user/validateEmail?eamil="+passport);
			        map.put("acceptor",passport);
			        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
					if(userLoginRegisterService.sendEmail(passport, type, map)){
						resultMap.put( "message", "send mail success");
						resultMap.put( "status", 1);
					}else{
						resultMap.put( "error", "send email failed.");
						resultMap.put( "status", 0);
					}
					return new MappingJacksonValue(resultMap);
				}
				//个人用手机注册
				if(type==2 && userType.equals("0")){
					userBasic= new UserBasic();
					userBasic.setName(name);
					userBasic.setMobile(passport);
					userBasic.setPicId(picId);
					userBasic.setStatus(new Byte("1"));
					userBasic.setSex(new Byte("1"));
					userBasic.setUserId(id);
					userBasic.setAuth(new Byte("1"));
					userBasicId=userBasicService.createUserBasic(userBasic);
					userExt=new UserExt();
					userExt.setName(name);
					userExt.setShortName(name);
					userExt.setIp(ip);
					userExt.setUserId(id);
					userExtId=userExtService.createUserExt(userExt);
					list =new ArrayList<UserInterestIndustry>();
					for (Long firstIndustryId : firstIndustryIds) {
						userInterestIndustry= new UserInterestIndustry();
						userInterestIndustry.setUserId(id);
						userInterestIndustry.setFirstIndustryId(firstIndustryId);
						userInterestIndustry.setIp(ip);
						list.add(userInterestIndustry);
						list=userInterestIndustryService.createUserInterestIndustryByList(list, id);
					}
					resultMap.put( "id", id);
					resultMap.put( "status", 1);
					return new MappingJacksonValue(resultMap);
				}
				//组织用户邮箱注册
				if(type==1 && userType.equals("1")){
					userOrganBasic= new UserOrganBasic();
					userOrganBasic.setPicId(picId);
					userOrganBasic.setUserId(id);
					userOrganBasic.setName(name);
					userOrganBasic.setAuth(new Byte("0"));
					userOrganBasic.setStatus(new Byte("1"));
					userOrganBasicId=userOrganBasicService.createUserOrganBasic(userOrganBasic);
					userOrganExt= new UserOrganExt();
					userOrganExt.setShortName(shortName);
					userOrganExt.setName(name);
					userOrganExt.setStockCode(stockCode);
					userOrganExt.setOrgType(orgType);
					userOrganExt.setBusinessLicencePicId(businessLicencePicId);
					userOrganExt.setIdcardFrontPicId(idcardFrontPicId);
					userOrganExt.setIdcardBackPicId(idcardBackPicId);
					userOrganExt.setCompanyContacts(companyContacts);
					userOrganExt.setCompanyContactsMobile(companyContactsMobile);
					userOrganExt.setIp(ip);
					userOrganExt.setUserId(id);
					userOrganExtId=userOrganExtService.createUserOrganExt(userOrganExt);
					//邮箱验证地址
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("email",  userWebUrl+"/user/validateEmail?eamil="+passport);
					map.put("acceptor",passport);
					map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
					if(userLoginRegisterService.sendEmail(passport, type, map)){
						resultMap.put( "message", "send mail success");
						resultMap.put( "status", 1);
					}else{
						resultMap.put( "error", "send email failed.");
						resultMap.put( "status", 1);
					}
					return new MappingJacksonValue(resultMap);
				}
				resultMap.put("error", "paramter type or userType error.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//异常失败回滚
			if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
			if(userExtId!=null && userExtId>0L)userExtService.realDeleteUserExt(id);
			if(userBasicId!=null && userBasicId>0l)userBasicService.realDeleteUserBasic(userBasicId);
			if(userOrganExtId!=null && userOrganExtId>0l)userOrganExtService.realDeleteUserOrganExt(userOrganExtId);
			if(userOrganBasicId!=null && userOrganBasicId>0l)userOrganBasicService.realDeleteUserOrganBasic(userOrganBasicId);
			if(list!=null && list.size()>=0){
				List<Long> listIds=new ArrayList<Long>();
				for (UserInterestIndustry userInterestIndustry2 : list) {
					listIds.add(userInterestIndustry2.getId());
				}
				userInterestIndustryService.realDeleteUserInterestIndustryList(listIds);
			}
			logger.info("注册失败:"+passport);
			throw e;
		}
	}
	/**
	 * 获取用户资料
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/getUserDetail" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserDetail(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserOrganBasic userOrganBasic= null;
		UserOrganExt userOrganExt= null;
		UserBasic userBasic= null;
		UserExt userExt= null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(userLoginRegister==null){
				resultMap.put("error", "passport is not exists.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(userLoginRegister.getUsetType().intValue()==1){
				userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
				userOrganExt=userOrganExtService.getUserOrganExt(userLoginRegister.getId());
				resultMap.put("userLoginRegister", userLoginRegister);
				resultMap.put("userOrganBasic", userOrganBasic);
				resultMap.put("userOrganExt", userOrganExt);
				resultMap.put("status",1);
			}
			if(userLoginRegister.getUsetType().intValue()==0){
				userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
				userExt=userExtService.getUserExt(userLoginRegister.getId());
				resultMap.put("userLoginRegister", userLoginRegister);
				resultMap.put("userBasic", userBasic);
				resultMap.put("userExt", userExt);
				resultMap.put("status",1);
			}
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("获取用户资料失败:"+passport);
			throw e;
		}
	}
	/**
	 *根据userId获取用户我的里面的组织好友列表
	 * 
	 * @param passport 为邮箱和手机号
	 * @param start 开始位置 0为起始位置
	 * @param count 每页多少个
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/getOrgFriendlylList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getOrgFriendlylList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserOrgPerCusRel> list=null;
		UserLoginRegister userLoginRegister=null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(userLoginRegister==null){
				resultMap.put("error", "passport is not exists in UserLoginRegister.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			list= userOrgPerCusRelService.getOrgFriendlylList(start, count, userLoginRegister.getId());
			resultMap.put("list", list);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("根据userId获取用户我的里面的组织好友列表失败:"+passport);
			throw e;
		}
	}	
	/**
	 *根据userId获取用户我的里面的个人好友列表
	 * 
	 * @param passport 为邮箱和手机号
	 * @param start 开始位置 0为起始位置
	 * @param count 每页多少个
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/getUserFriendlyList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserFriendlyList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserOrgPerCusRel> list=null;
		UserLoginRegister userLoginRegister=null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(userLoginRegister==null){
				resultMap.put("error", "passport is not exists in UserLoginRegister.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			list= userOrgPerCusRelService.getUserFriendlyList(start, count, userLoginRegister.getId());
			resultMap.put("list", list);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("根据userId获取用户我的里面的组织好友列表失败:"+passport);
			throw e;
		}
	}	
	/**
	 *根据userId获取用户通讯录个人和组织好友列表
	 * 
	 * @param passport 为邮箱和手机号
	 * @param start 开始位置 0为起始位置
	 * @param count 每页多少个
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/getUserAndOrgFriendlyList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserAndOrgFriendlyList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserOrgPerCusRel> list=null;
		UserLoginRegister userLoginRegister=null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(userLoginRegister==null){
				resultMap.put("error", "passport is not exists in UserLoginRegister.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			list= userOrgPerCusRelService.getUserFriendlyList(start, count, userLoginRegister.getId());
			resultMap.put("list", list);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("根据userId获取用户我的里面的组织好友列表失败:"+passport);
			throw e;
		}
	}	
	/**
	 * 申请添加好友
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param content 添加用户时发送的消息.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/applyToAddFriendly" }, method = { RequestMethod.GET })
	public MappingJacksonValue applyToAddFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "userId",required = true) Long userId
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "content",required = true) String content
			,@RequestParam(name = "appId",required = true) String appId
			,@RequestParam(name = "status",required = true) String status
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserFriendly userFriendly=null;
		try {
			if(!status.equals("0")){
				resultMap.put("error", "status must be 0.");
				resultMap.put("status",0);
			}
			if(userLoginRegisterService.getUserLoginRegister(userId)==null){
				resultMap.put("error", "userId is not exists in UserLoginRegister.");
				resultMap.put("status",0);
			}
			if(userLoginRegisterService.getUserLoginRegister(friendId)==null){
				resultMap.put("error", "friendId is not exists in UserLoginRegister.");
				resultMap.put("status",0);
			}
			userFriendly=new UserFriendly();
			userFriendly.setUserId(userId);
			userFriendly.setFriendId(friendId);
			userFriendly.setStatus(new Byte(status));
			userFriendly.setContent(content);
			userFriendly.setAppId(appId);
			userFriendlyService.createUserFriendly(userFriendly,true);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("添加好友"+friendId+"失败");
			throw e;
		}
	}	
	/**
	 * 同意添加好友
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param appId 添加用户时发送的消息.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/auditByAddFriendly" }, method = { RequestMethod.GET })
	public MappingJacksonValue auditByAddFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "userId",required = true) Long userId
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "appId",required = true) String appId
			,@RequestParam(name = "status",required = true) String  status
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserFriendly userFriendly=null;
		UserOrgPerCusRel userOrgPerCusRel=null;
		UserLoginRegister userLoginRegister=null;
		UserLoginRegister userLoginRegisterFriend=null;
		UserBasic userBasic=null;
		UserOrganBasic userOrganBasic=null;
		Long id=0l;
		Long userOrgPerCusRelId=0l;
		Long userOrgPerCusRelFriendlyId=0l;
		boolean bl=false;
		try {
				if(!status.equals("1")){
					resultMap.put("error", "status must be 1.");
					resultMap.put("status",0);
				}
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
				if(userLoginRegister==null){
					resultMap.put("error", "userId is not exists in UserLoginRegister.");
					resultMap.put("status",0);
				}
				userLoginRegisterFriend=userLoginRegisterService.getUserLoginRegister(friendId);
				if(userLoginRegisterFriend==null){
					resultMap.put("error", "friendId is not exists in UserLoginRegister.");
					resultMap.put("status",0);
				}
				//检查个人好友是否存在
				if(userLoginRegisterFriend.getUsetType().intValue()==0){
					userBasic=userBasicService.getUserBasic(userLoginRegisterFriend.getId());
					if(userBasic==null){
						resultMap.put("error", "friendId is not exists in UserBasic.");
						resultMap.put("status",0);
					}
				}
				//检查组织好友是否存在
				if(userLoginRegisterFriend.getUsetType().intValue()==1){
					userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegisterFriend.getId());
					if(userOrganBasic==null){
						resultMap.put("error", "friendId is not exists in UserOrganBasic.");
						resultMap.put("status",0);
					}
				}
				//更新好友关系和创建对方的好友关系
				userFriendly=new UserFriendly();
				userFriendly.setUserId(friendId);
				userFriendly.setFriendId(userId);
				userFriendly.setStatus(new Byte(status));
				userFriendly.setAppId(appId);
				bl=userFriendlyService.updateStatus(userId, friendId, new Byte(status));
				id=userFriendlyService.createUserFriendly(userFriendly,false);
				//添加个人好友
				if(userLoginRegisterFriend.getUsetType().intValue()==0){
					userOrgPerCusRel=new UserOrgPerCusRel();
					userOrgPerCusRel.setUserId(userId);
					userOrgPerCusRel.setFriendId(friendId);
					userOrgPerCusRel.setName(userBasic.getName());
					userOrgPerCusRel.setReleationType(new Byte("1"));
					userOrgPerCusRelId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					//添加对方的个人好友
					if(userLoginRegister.getUsetType().intValue()==0){
						userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setName(userBasic.getName());
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					//添加对方的组织好友
					if(userLoginRegister.getUsetType().intValue()==1){
						userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setName(userOrganBasic.getName());
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					resultMap.put("status",1);
				}
				//同意添加组织好友
				if(userLoginRegisterFriend.getUsetType().intValue()==1){
					userOrgPerCusRel=new UserOrgPerCusRel();
					userOrgPerCusRel.setUserId(userId);
					userOrgPerCusRel.setFriendId(friendId);
					userOrgPerCusRel.setName(userOrganBasic.getName());
					userOrgPerCusRel.setReleationType(new Byte("2"));
					userOrgPerCusRelId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					//添加对方的个人好友
					if(userLoginRegister.getUsetType().intValue()==0){
						userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setName(userBasic.getName());
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					//添加对方的组织好友
					if(userLoginRegister.getUsetType().intValue()==1){
						userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setName(userOrganBasic.getName());
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					resultMap.put("status",1);
				}
			
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//失败回滚
			if(bl)userFriendlyService.updateStatus(userId, friendId, new Byte("0"));
			if(id!=null || id>0l)userFriendlyService.realDeleteUserFriendly(id);
			if(userOrgPerCusRelId>0l)userOrgPerCusRelService.deleteFriendly(friendId);
			if(userOrgPerCusRelFriendlyId>0l)userOrgPerCusRelService.deleteFriendly(userId);
			logger.info("添加好友"+friendId+"失败");
			throw e;
		}
	}	
	/**
	 * 删除好友
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param appId 应用的appId
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/deleteFriendly" }, method = { RequestMethod.GET })
	public MappingJacksonValue deleteFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "userId",required = true) Long userId
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "appId",required = true) String appId
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister=null;
		UserLoginRegister userLoginRegisterFriend=null;
		UserBasic userBasic=null;
		UserFriendly userFriendly=null;
		UserFriendly userFriendlyTarget=null;
		UserOrgPerCusRel userOrgPerCusRel=null;
		UserOrgPerCusRel userOrgPerCusRelTarget=null;
		UserOrganBasic userOrganBasic=null;
		boolean bl1=false;
		boolean bl2=false;
		boolean bl3=false;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("error", "userId is not exists in UserLoginRegister.");
				resultMap.put("status",0);
			}
			userLoginRegisterFriend=userLoginRegisterService.getUserLoginRegister(friendId);
			if(userLoginRegisterFriend==null){
				resultMap.put("error", "friendId is not exists in UserLoginRegister.");
				resultMap.put("status",0);
			}
			//检查个人好友是否存在
			if(userLoginRegisterFriend.getUsetType().intValue()==0){
				userBasic=userBasicService.getUserBasic(userLoginRegisterFriend.getId());
				if(userBasic==null){
					resultMap.put("error", "friendId is not exists in UserBasic.");
					resultMap.put("status",0);
				}
			}
			//检查组织好友是否存在
			if(userLoginRegisterFriend.getUsetType().intValue()==1){
				userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegisterFriend.getId());
				if(userOrganBasic==null){
					resultMap.put("error", "friendId is not exists in UserOrganBasic.");
					resultMap.put("status",0);
				}
			}
			userFriendly=userFriendlyService.getFriendly(userId, friendId);
			userFriendlyTarget=userFriendlyService.getFriendly(friendId, userId);
			userOrgPerCusRel=userOrgPerCusRelService.getUserOrgPerCusRel(userId, friendId);
			userOrgPerCusRelTarget=userOrgPerCusRelService.getUserOrgPerCusRel(friendId, userId);
			bl1=userOrgPerCusRelService.deleteFriendly(userId);
			bl2=userOrgPerCusRelService.deleteFriendly(friendId);
			bl3=userFriendlyService.deleteFriendly(userId, friendId);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//失败回滚
			if(bl1)userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
			if(bl2)userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRelTarget);
			if(bl3)userFriendlyService.createUserFriendly(userFriendly, false);
			if(bl3)userFriendlyService.createUserFriendly(userFriendlyTarget, false);
			if(!bl3){
				if(userFriendlyService.getFriendly(userId, friendId)==null){
					userFriendlyService.createUserFriendly(userFriendly, false);
				}
				if(userFriendlyService.getFriendly(friendId, userId)==null){
					userFriendlyService.createUserFriendly(userFriendlyTarget, false);
				}
			}
			logger.info("删除好友"+friendId+"失败");
			throw e;
		}
	}	
	/**
	 * 修改资料
	 * 
	 * @param passport 为邮箱和手机号
	 * @param name 昵称,企业全称
	 * @param sex 性别
	 * @param picId 个人或组织LOGOID
	 * @param shortName 组织简称
	 * @param firstIndustryIds 一级行业ID数组
	 * @param userDefineds 用户自定义数组 userDefineds=1,自定义字段12,3&userDefineds=4,自定义字段子,好的&userDefineds=7,8,9
	 * @param email 邮箱
	 * @param phone 企业电话
	 * @param brief 企业简介
	 * @param companyName 公司名称
	 * @param companyJob 职业
	 * @param mobile 个人手机号
	 * @param provinceId 省ID
	 * @param cityId   市ID
	 * @param countyId   县ID
	 * @throws Exception
	 * @return MappingJacksonValue
	 */
	@RequestMapping(path = { "/user/updateUser" }, method = { RequestMethod.GET })
	public MappingJacksonValue updateUser(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "sex",required = false) String sex
			,@RequestParam(name = "picId",required = true) Long picId
			,@RequestParam(name = "shortName",required = false) String shortName
			,@RequestParam(name = "firstIndustryIds", required = false) Long[] firstIndustryIds
			,@RequestParam(name = "userDefineds", required = false) String[] userDefineds
			,@RequestParam(name = "email",required = false) String email
			,@RequestParam(name = "phone",required = false) String phone
			,@RequestParam(name = "brief",required = false) String brief
			,@RequestParam(name = "companyName",required = false) String companyName
			,@RequestParam(name = "companyJob",required = false) String companyJob
			,@RequestParam(name = "mobile",required = false) String mobile
			,@RequestParam(name = "provinceId",required = false) Long provinceId
			,@RequestParam(name = "cityId",required = false) Long cityId
			,@RequestParam(name = "countyId",required = false) Long countyId
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInterestIndustry userInterestIndustry= null;
		UserDefined userDefined= null;
		UserLoginRegister userLoginRegister= null;
		UserOrganBasic userOrganBasic= null;
		UserOrganExt userOrganExt= null;
		UserBasic userBasic= null;
		UserExt userExt= null;
		List<UserInterestIndustry> list = null;
		List<UserDefined> listUserDefined = null;
		String ip=getIpAddr(request);
		Long userId=0L;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(ObjectUtils.isEmpty(userLoginRegister)){
				resultMap.put("error", "passport is not exists.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userId=userLoginRegister.getId();
			//修改组织
			if(userLoginRegister.getUsetType().intValue()==1){
				userOrganBasic= userOrganBasicService.getUserOrganBasic(userId);
				if(userOrganBasic==null){
					resultMap.put( "error", "userId is not exist in UserOrganBasic.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				userLoginRegister.setEmail(email);
				userLoginRegister.setMobile(phone);
				userLoginRegisterService.updataUserLoginRegister(userLoginRegister);
				userOrganBasic.setPicId(picId);
				userOrganBasicService.updateUserOrganBasic(userOrganBasic);
				list =new ArrayList<UserInterestIndustry>();
				for (Long firstIndustryId : firstIndustryIds) {
					userInterestIndustry= new UserInterestIndustry();
					userInterestIndustry.setUserId(userId);
					userInterestIndustry.setFirstIndustryId(firstIndustryId);
					userInterestIndustry.setIp(ip);
					list.add(userInterestIndustry);
					list=userInterestIndustryService.createUserInterestIndustryByList(list, userId);
				}
				if(userDefineds!=null){
					listUserDefined=new ArrayList<UserDefined>();
					for (String strUserDefined : userDefineds) {
						String object[]=strUserDefined.split(",");
						userDefined= new UserDefined();
						userDefined.setUserId(userId);
						userDefined.setIp(ip);
						for (int i = 0; i < object.length; i++) {
							if(i==0)userDefined.setUserDefinedModel(object[i]);
							if(i==1)userDefined.setUserDefinedFiled(object[i]);
							if(i==2)userDefined.setUserDefinedValue(object[i]);
						}
						listUserDefined.add(userDefined);
						listUserDefined=userDefinedService.createUserDefinedByList(listUserDefined, userId);
					}
				}
				userOrganExt=userOrganExtService.getUserOrganExt(userId);
				userOrganExt.setPhone(phone);
				userOrganExt.setShortName(shortName);
				userOrganExt.setIp(ip);
				userOrganExt.setName(name);
				userOrganExtService.updateUserOrganExt(userOrganExt);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
			}
			//修改个人
			if(userLoginRegister.getUsetType().intValue()==2){
				userBasic= userBasicService.getUserBasic(userId);
				if(userBasic==null){
					resultMap.put( "error", "userId is not exist in UserBasic.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				userLoginRegister.setEmail(email);
				userLoginRegister.setMobile(phone);
				userLoginRegisterService.updataUserLoginRegister(userLoginRegister);
				userBasic.setPicId(picId);
				userBasic.setMobile(phone);
				userBasic.setName(name);
				userBasic.setSex(new Byte("1"));
				userBasicService.updateUserBasic(userBasic);
				if(userDefineds!=null){
					listUserDefined=new ArrayList<UserDefined>();
					for (String strUserDefined : userDefineds) {
						String object[]=strUserDefined.split(",");
						userDefined= new UserDefined();
						userDefined.setUserId(userId);
						for (int i = 0; i < object.length; i++) {
							if(i==0)userDefined.setUserDefinedModel(object[i]);
							if(i==1)userDefined.setUserDefinedFiled(object[i]);
							if(i==2)userDefined.setUserDefinedValue(object[i]);
						}
						listUserDefined.add(userDefined);
						listUserDefined=userDefinedService.createUserDefinedByList(listUserDefined, userId);
					}
				}
				userExt=userExtService.getUserExt(userId);
				userExt.setProvinceId(provinceId);
				userExt.setCityId(cityId);
				userExt.setCountyId(countyId);
				userExt.setIp(ip);
				userExtService.updateUserExt(userExt);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
			}			
			resultMap.put("userLoginRegister", userLoginRegister);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("登录失败:"+passport);
			throw e;
		}
	}	
	/**
	 * 用户登录
	 * 
	 * @param passport 为邮箱和手机号
	 * @param password 用户密码
	 * @param source  来源的appkey
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/login" }, method = { RequestMethod.GET })
	public MappingJacksonValue login(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "source",required = true) String source
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			byte[] bt = Base64.decode(password);
			String salt=userLoginRegister.getSalt();
			password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
			if(!userLoginRegister.getPassword().equals(password)){
				resultMap.put("error", "incorrect password .");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			resultMap.put("userLoginRegister", userLoginRegister);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("登录失败:"+passport);
			throw e;
		}
	}
	/**
	 * 验证passport是否已经存在
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/validatPassport" }, method = { RequestMethod.GET })
	public MappingJacksonValue validatPassport(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(userLoginRegisterService.passportIsExist(passport)){
				if(isEmail(passport))resultMap.put("error", "email already exists.");
				else resultMap.put("error", "email format is not correct.");
				if(isMobileNo(passport))resultMap.put("error", "mobile already exists.");
				else resultMap.put("error", "mobile phone number is not correct.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("登录失败:"+passport);
			throw e;
		}
	}
	/**
	 * 邮箱注册验证地址,找回密码验证地址
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/validateEmail" }, method = { RequestMethod.GET })
	public MappingJacksonValue validateEmail(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "eamil",required = true) String email
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserBasic userBasic=null;
		UserOrganBasic userOrganBasic=null;
		UserLoginRegister userLoginRegister=null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(email);
			if(userLoginRegister==null){
				resultMap.put("error", "email is not exists in UserLogniRegister.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(userLoginRegisterService.getEmail(email)){
				if(userLoginRegister.getUsetType().intValue()==0){
					userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
					if(userBasic==null){
						resultMap.put("error", "email is not exists in UserBasic.");
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
					userBasic.setAuth(new Byte("1"));
					if(userBasicService.updateUserBasic(userBasic)){
						resultMap.put("error", "email validate success.");
						resultMap.put("status",1);
						return new MappingJacksonValue(resultMap);
					}else{
						resultMap.put("error", "email validate failed.");
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
				}
				if(userLoginRegister.getUsetType().intValue()==1){
					userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
					if(userOrganBasic==null){
						resultMap.put("error", "email is not exists in UserOrganBasic.");
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
					userOrganBasic.setAuth(new Byte("1"));
					if(userOrganBasicService.updateUserOrganBasic(userOrganBasic)){
						resultMap.put("error", "email validate success.");
						resultMap.put("status",1);
						return new MappingJacksonValue(resultMap);
					}else{
						resultMap.put("error", "email validate failed.");
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
				}
				
			}
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			throw e;
		}
	}	
	/**
	 * 修改密码
	 * 
	 * @param passport 为邮箱和手机号
	 * @param oldpassword 用户密码
	 * @param newpassword 用户密码
	 * @param source  来源的appkey
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/updatepassword" }, method = { RequestMethod.GET })
	public MappingJacksonValue updatepassword(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "oldpassword",required = true) String oldpassword
			,@RequestParam(name = "newpassword",required = true) String newpassword
			,@RequestParam(name = "source",required = true) String source
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(ObjectUtils.isEmpty(userLoginRegister)){
				resultMap.put("error", "passport is not exists.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			byte[] bt = Base64.decode(oldpassword);
			String salt=userLoginRegisterService.setSalt();
			oldpassword=userLoginRegisterService.setSha256Hash(salt, new String(bt));
			if(!userLoginRegister.getPassword().equals(oldpassword)){
				resultMap.put("error", "incorrect oldpassword .");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			salt=userLoginRegisterService.setSalt();
			newpassword=userLoginRegisterService.setSha256Hash(salt, new String(bt));
			userLoginRegisterService.updatePassword(userLoginRegister.getId(), newpassword);
			resultMap.put("userLoginRegister", userLoginRegister);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("登录失败:"+passport);
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
	@RequestMapping(path = { "/user/getIdentifyingCode" }, method = { RequestMethod.GET})
//	@RequestMapping(path = { "/user/getIdentifyingCode" })
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
	 * 根据地区省ID获取用户列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/getUserListByProvinceId" }, method = { RequestMethod.GET})
	public MappingJacksonValue getUserListByProvinceId(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "passport",required = true) String passport
		,@RequestParam(name = "provinceId",required = true) Long provinceId
		,@RequestParam(name = "start",required = true) int start
		,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
				if(!userLoginRegisterService.passportIsExist(passport)){
					resultMap.put("error", "passport is not exists.");
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(provinceId)){
					resultMap.put( "error", "provinceId is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(passport)){
					resultMap.put( "error", "passport is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(start<0){
					resultMap.put( "error", "start must be than zero.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(count<=0){
					resultMap.put( "error", "count must be than zero.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				List<UserExt> list=userExtService.getUserExtListByProvinceId(start, count, provinceId);
				if(list==null || list.size()==0)  return new MappingJacksonValue(list);
				List<Long> ids=new ArrayList<Long>();
				for (UserExt userExt : list) {
					if(userExt!=null)ids.add(userExt.getUserId());
				}
				List<UserBasic> list2 = userBasicService.getUserBasecList(ids);
				return new MappingJacksonValue(list2);
		}catch (Exception e ){
			throw e;
		}
	}	
	/**
	 * 根据第三级行业ID获取用户列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/getUserListByThirdIndustryId" }, method = { RequestMethod.GET})
	public MappingJacksonValue getUserListByThirdIndustryId(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "thirdIndustryId",required = true) Long thirdIndustryId
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(!userLoginRegisterService.passportIsExist(passport)){
				resultMap.put("error", "passport is not exists.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(StringUtils.isEmpty(thirdIndustryId)){
				resultMap.put( "error", "provinceId is null or empty.");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if(StringUtils.isEmpty(passport)){
				resultMap.put( "error", "passport is null or empty.");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if(start<0){
				resultMap.put( "error", "start must be than zero.");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if(count<=0){
				resultMap.put( "error", "count must be than zero.");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			List<UserExt> list=userExtService.getUserListByThirdIndustryId(start, count, thirdIndustryId);
			if(list==null || list.size()==0)  return new MappingJacksonValue(list);
			List<Long> ids=new ArrayList<Long>();
			for (UserExt userExt : list) {
				if(userExt!=null)ids.add(userExt.getUserId());
			}
			List<UserBasic> list2 = userBasicService.getUserBasecList(ids);
			return new MappingJacksonValue(list2);
		}catch (Exception e ){
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
	public static void main(String[] args) {
	}
}
