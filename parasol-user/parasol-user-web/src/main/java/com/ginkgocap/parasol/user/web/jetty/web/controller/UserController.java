package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

import com.ginkgocap.parasol.message.service.MessageRelationService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
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
import com.ginkgocap.parasol.user.web.jetty.web.utils.Prompt;

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
	@Autowired
	private MessageRelationService messageRelationService;
	@Value("${user.web.url}")  
    private String userWebUrl;  
	@Value("${oauth.web.url}")  
    private String oauthWebUrl;  
	@Value("${oauth.web.logout}")  
	private String oauthWebLogout;  
	@Value("${client_id}")  
    private String client_id;  
	@Value("${client_secret}")  
    private String client_secret; 
	@Value("${email.validate.url}")  
    private String emailValidateUrl; 	
    private static final String GRANT_TYPE="password"; 

	/**
	 * 用户注册
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
	@RequestMapping(path = { "/user/user/register" }, method = { RequestMethod.POST })
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "type",required = true) int type
			,@RequestParam(name = "code",required = true) String code
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "userType",required = true) String userType
			,@RequestParam(name = "firstIndustryIds", required = false) Long[] firstIndustryIds
			,@RequestParam(name = "name",required = false) String name
			,@RequestParam(name = "shortName",required = false) String shortName
			,@RequestParam(name = "picId",required = false) Long picId
			,@RequestParam(name = "businessLicencePicId",required = false) Long businessLicencePicId
			,@RequestParam(name = "stockCode",required = false) String stockCode
			,@RequestParam(name = "orgType",required = false) String orgType
			,@RequestParam(name = "companyContactsMobile",required = false) String companyContactsMobile
			,@RequestParam(name = "companyContacts",required = false) String companyContacts
			,@RequestParam(name = "idcardFrontPicId",required = false) Long idcardFrontPicId
			,@RequestParam(name = "idcardBackPicId",required = false) Long idcardBackPicId
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
		Long appId =0l;
		try {
				boolean exists=userLoginRegisterService.passportIsExist(passport);
				if(exists){
					if(type==1)resultMap.put( "message", Prompt.email_already_exists);
					if(type==2)resultMap.put( "message", Prompt.mobile_already_exists);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//个人用户组织用户邮箱注册
				if((type==1 && userType.equals("0")) ||(type==1 && userType.equals("1"))){
					if(!isEmail(passport)){
						resultMap.put( "message", Prompt.email_format_is_not_correct);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				if(StringUtils.isEmpty(code)){
					resultMap.put( "message", Prompt.code_cannot_be_null_or_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//个人用户手机和邮箱注册
				if((type==2 || type==1) && userType.equals("0")){
					if(!code.equals(userLoginRegisterService.getIdentifyingCode(passport))){
						resultMap.put( "message", Prompt.code_is_not_right);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				if(StringUtils.isEmpty(password)){
					resultMap.put( "message", Prompt.passowrd_cannot_be_null_or_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(password.length()<6){
					resultMap.put( "message", Prompt.password_length_must_be_greater_than_or_equal_to_6);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
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
				userLoginRegister.setSource(appId.toString());
				userLoginRegister.setCtime(System.currentTimeMillis());
				userLoginRegister.setUtime(System.currentTimeMillis());
				if(type==1)userLoginRegister.setEmail(passport);
				if(type==2)userLoginRegister.setMobile((passport));
				id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
				//个人用户邮箱注册
				if((type==1 && userType.equals("0"))){
					userBasic= new UserBasic();
					userBasic.setName(name);
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
					userLoginRegisterService.deleteIdentifyingCode(passport);
					resultMap.put( "id", id);
					resultMap.put( "status", 1);
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
					if(firstIndustryIds!=null && firstIndustryIds.length>0){
						for (Long firstIndustryId : firstIndustryIds) {
							userInterestIndustry= new UserInterestIndustry();
							userInterestIndustry.setUserId(id);
							userInterestIndustry.setFirstIndustryId(firstIndustryId);
							userInterestIndustry.setIp(ip);
							list.add(userInterestIndustry);
							list=userInterestIndustryService.createUserInterestIndustryByList(list, id);
						}
					}
					userLoginRegisterService.deleteIdentifyingCode(passport);
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
					userOrganBasic.setAuth(new Byte("1"));
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
					userLoginRegisterService.deleteIdentifyingCode(passport);
					resultMap.put( "id", id);
					resultMap.put( "status", 1);
					return new MappingJacksonValue(resultMap);
				}
				resultMap.put("message", Prompt.paramter_type_or_userType_error);
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
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 获取用户资料
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getUserDetail" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserDetail(HttpServletRequest request,HttpServletResponse response
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserOrganBasic userOrganBasic= null;
		UserOrganExt userOrganExt= null;
		UserBasic userBasic= null;
		UserExt userExt= null;
		List<UserDefined> list=null;
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(userLoginRegister.getUsetType().intValue()==1){
				userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
				userOrganExt=userOrganExtService.getUserOrganExt(userLoginRegister.getId());
				List<Long> ids=userDefinedService.getIdList(userLoginRegister.getId());
				if(ids!=null && ids.size()!=0)list=userDefinedService.getIdList(ids);
				resultMap.put("userLoginRegister", userLoginRegister);
				resultMap.put("userOrganBasic", userOrganBasic);
				resultMap.put("userOrganExt", userOrganExt);
				resultMap.put("userDefinedList", list);
				resultMap.put("status",1);
			}
			if(userLoginRegister.getUsetType().intValue()==0){
				userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
				userExt=userExtService.getUserExt(userLoginRegister.getId());
				resultMap.put("userLoginRegister", userLoginRegister);
				List<Long> ids=userDefinedService.getIdList(userLoginRegister.getId());
				if(ids!=null && ids.size()!=0)list=userDefinedService.getIdList(ids);
				resultMap.put("userBasic", userBasic);
				resultMap.put("userExt", userExt);
				resultMap.put("userDefinedList", list);
				resultMap.put("status",1);
			}
			userLoginRegister.setPassword(null);
			userLoginRegister.setSalt(null);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("获取用户资料失败:"+userId);
			logger.info(e.getStackTrace());
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
	@RequestMapping(path = { "/user/user/getOrgFriendlylList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getOrgFriendlylList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserOrgPerCusRel> list=null;
		UserLoginRegister userLoginRegister=null;
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			list= userOrgPerCusRelService.getOrgFriendlylList(start, count, userLoginRegister.getId());
			resultMap.put("list", list);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("根据userId获取用户我的里面的组织好友列表失败:"+userId);
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	
	/**
	 *获取当前登录用户的userId及appId
	 * 
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getUserIdAndAppId" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserIdAndAppId(HttpServletRequest request,HttpServletResponse response
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty_please_first_authentication_and_get_access_token);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			resultMap.put("userId", userId);
			resultMap.put("appId", LoginUserContextHolder.getAppKey());
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("根据userId获取用户我的里面的组织好友列表失败:"+userId);
			logger.info(e.getStackTrace());
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
	@RequestMapping(path = { "/user/user/getUserFriendlyList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserFriendlyList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserOrgPerCusRel> list=null;
		UserLoginRegister userLoginRegister=null;
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			list= userOrgPerCusRelService.getUserFriendlyList(start, count, userLoginRegister.getId());
			resultMap.put("list", list);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("根据userId获取用户我的里面的个人好友列表失败:"+userId);
			logger.info(e.getStackTrace());
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
	@RequestMapping(path = { "/user/user/getUserAndOrgFriendlyList" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserAndOrgFriendlyList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserOrgPerCusRel> list=null;
		UserLoginRegister userLoginRegister=null;
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			list= userOrgPerCusRelService.getUserFriendlyList(start, count, userLoginRegister.getId());
			resultMap.put("list", list);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("根据userId获取用户我的里面的个人和组织好友列表失败:"+userId);
			logger.info(e.getStackTrace());
			throw e;
		}
	}	
	/**
	 * 申请添加好友
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param content 添加用户时发送的消息.
	 * @param appId 来源应用id.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/applyToAddFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue applyToAddFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "content",required = true) String content
			,@RequestParam(name = "status",required = true) String status
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserFriendly userFriendly=null;
		Long userId=null;
		Long appId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			appId=LoginUserContextHolder.getAppKey();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(!status.equals("0")){
				resultMap.put("message", "status must be 0.");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(userLoginRegisterService.getUserLoginRegister(userId)==null){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(userLoginRegisterService.getUserLoginRegister(friendId)==null){
				resultMap.put("message", Prompt.friendId_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userFriendly=new UserFriendly();
			userFriendly.setUserId(userId);
			userFriendly.setFriendId(friendId);
			userFriendly.setStatus(new Byte(status));
			userFriendly.setContent(content);
			userFriendly.setAppId(appId);
			UserFriendly uf=userFriendlyService.getFriendly(userId ,friendId);
			if(uf!=null && uf.getStatus().intValue()==0){
				resultMap.put("message", Prompt.has_been_add_this_friendly_please_waiting_for_him_to_agree);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(uf!=null && uf.getStatus().intValue()==1){
				resultMap.put("message", Prompt.he_s_already_a_good_friend_of_yours_cannot_repeat_add_him);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userFriendlyService.createUserFriendly(userFriendly,true);
			resultMap.put("message", Prompt.apply_to_add_friendly_successed);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("添加好友"+friendId+"失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}	
	/**
	 * 同意添加好友
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param appId  应用的appId.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/auditByAddFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue auditByAddFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "status",required = true) String  status
			,@RequestParam(name = "relationId",required = true) Long  relationId
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
		Long userId=null;
		Long appId=null;
		try {
				userId = LoginUserContextHolder.getUserId();
				appId=LoginUserContextHolder.getAppKey();
				if(userId==null){
					resultMap.put("message", Prompt.userId_is_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(relationId==null){
					resultMap.put("message", Prompt.relationId_is_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(!status.equals("1")){
					resultMap.put("message", Prompt.status_must_be_1);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
				if(userLoginRegister==null){
					resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				userLoginRegisterFriend=userLoginRegisterService.getUserLoginRegister(friendId);
				if(userLoginRegisterFriend==null){
					resultMap.put("message", Prompt.friendId_is_not_exists_in_UserLoginRegister);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				//检查个人好友是否存在
				if(userLoginRegisterFriend.getUsetType().intValue()==0){
					userBasic=userBasicService.getUserBasic(userLoginRegisterFriend.getId());
					if(userBasic==null){
						resultMap.put("message", Prompt.friendId_is_not_exists_in_UserBasic);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
				}
				//检查组织好友是否存在
				if(userLoginRegisterFriend.getUsetType().intValue()==1){
					userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegisterFriend.getId());
					if(userOrganBasic==null){
						resultMap.put("message", Prompt.friendId_is_not_exists_in_UserOrganBasic);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
				}
				//更新好友关系和创建对方的好友关系
				userFriendly=new UserFriendly();
				userFriendly.setUserId(userId);
				userFriendly.setFriendId(friendId);
				userFriendly.setStatus(new Byte(status));
				userFriendly.setAppId(appId);
				bl=userFriendlyService.updateStatus(friendId, userId, new Byte(status));
				if(!bl){
					resultMap.put("message", Prompt.update_Friendly_status_friendId_failed+ friendId+",userId:"+userId);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				UserFriendly uf=userFriendlyService.getFriendly(friendId,userId);
				if(uf!=null){
					if(uf.getStatus().intValue()==1){
						resultMap.put("message", userId +Prompt.is_already_a_good_friend_of_yours_cannot_repeat_add_him);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
				}
				if(uf==null)id=userFriendlyService.createUserFriendly(userFriendly,false);
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
						if(userBasic==null){
							userFriendlyService.updateStatus(userId, friendId, new Byte("0"));
							if(id!=null || id>0l)userFriendlyService.realDeleteUserFriendly(id);
							if(userOrgPerCusRelId>0l)userOrgPerCusRelService.realDeleteUserOrgPerCusRel(userOrgPerCusRelId);
							resultMap.put("message", Prompt.friendId_is_not_exists_in_UserBasic);
							resultMap.put("status",0);
							return new MappingJacksonValue(resultMap);
						}
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setReleationType(new Byte("1"));
						userOrgPerCusRel.setName(userBasic.getName());
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					//添加对方的组织好友
					if(userLoginRegister.getUsetType().intValue()==1){
						userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
						if(userOrganBasic==null){
							userFriendlyService.updateStatus(userId, friendId, new Byte("0"));
							if(id!=null || id>0l)userFriendlyService.realDeleteUserFriendly(id);
							if(userOrgPerCusRelId>0l)userOrgPerCusRelService.realDeleteUserOrgPerCusRel(userOrgPerCusRelId);
							resultMap.put("message", Prompt.friendId_is_not_exists_in_UserOrganBasic);
							resultMap.put("status",0);
							return new MappingJacksonValue(resultMap);
						}
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setReleationType(new Byte("2"));
						userOrgPerCusRel.setName(userOrganBasic.getName());
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					//更新消息状态
					messageRelationService.updateMessageRelationStatus(relationId, new Integer(status).intValue());
					resultMap.put("message", "add friendId successed.");
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
						if(userBasic==null){
							userFriendlyService.updateStatus(userId, friendId, new Byte("0"));
							if(id!=null || id>0l)userFriendlyService.realDeleteUserFriendly(id);
							if(userOrgPerCusRelId>0l)userOrgPerCusRelService.realDeleteUserOrgPerCusRel(userOrgPerCusRelId);
							resultMap.put("message", Prompt.friendId_is_not_exists_in_UserBasic);
							resultMap.put("status",0);
							return new MappingJacksonValue(resultMap);
						}
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setName(userBasic.getName());
						userOrgPerCusRel.setReleationType(new Byte("1"));
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					//添加对方的组织好友
					if(userLoginRegister.getUsetType().intValue()==1){
						userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
						if(userOrganBasic==null){
							userFriendlyService.updateStatus(userId, friendId, new Byte("0"));
							if(id!=null || id>0l)userFriendlyService.realDeleteUserFriendly(id);
							if(userOrgPerCusRelId>0l)userOrgPerCusRelService.realDeleteUserOrgPerCusRel(userOrgPerCusRelId);
							resultMap.put("message", Prompt.friendId_is_not_exists_in_UserOrganBasic);
							resultMap.put("status",0);
							return new MappingJacksonValue(resultMap);
						}
						userOrgPerCusRel=new UserOrgPerCusRel();
						userOrgPerCusRel.setUserId(friendId);
						userOrgPerCusRel.setFriendId(userId);
						userOrgPerCusRel.setName(userOrganBasic.getName());
						userOrgPerCusRel.setReleationType(new Byte("2"));
						userOrgPerCusRelFriendlyId=userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
					}
					//更新消息状态
					messageRelationService.updateMessageRelationStatus(relationId, new Integer(status).intValue());
					if(userOrgPerCusRelFriendlyId !=null && userOrgPerCusRelFriendlyId >0l && userOrgPerCusRelId!=null && userOrgPerCusRelId>0l && id!=null && id>0l){
						resultMap.put("message", "add friendId successed.");
						resultMap.put("status",1);
					}
				}
			
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//失败回滚
			if(bl)userFriendlyService.updateStatus(userId, friendId, new Byte("0"));
			messageRelationService.updateMessageRelationStatus(relationId,0);
			if(id!=null || id>0l)userFriendlyService.realDeleteUserFriendly(id);
			if(userOrgPerCusRelId>0l)userOrgPerCusRelService.realDeleteUserOrgPerCusRel(userOrgPerCusRelId);
			if(userOrgPerCusRelFriendlyId>0l)userOrgPerCusRelService.realDeleteUserOrgPerCusRel(userOrgPerCusRelFriendlyId);
			logger.info("添加好友"+friendId+"失败");
			logger.info(e.getStackTrace());
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
	@RequestMapping(path = { "/user/user/deleteFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue deleteFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "friendId",required = true) Long friendId
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
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegisterFriend=userLoginRegisterService.getUserLoginRegister(friendId);
			if(userLoginRegisterFriend==null){
				resultMap.put("message", Prompt.friendId_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			//检查个人好友是否存在
			if(userLoginRegisterFriend.getUsetType().intValue()==0){
				userBasic=userBasicService.getUserBasic(userLoginRegisterFriend.getId());
				if(userBasic==null){
					resultMap.put("message", Prompt.friendId_is_not_exists_in_UserBasic);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
			}
			//检查组织好友是否存在
			if(userLoginRegisterFriend.getUsetType().intValue()==1){
				userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegisterFriend.getId());
				if(userOrganBasic==null){
					resultMap.put("message", Prompt.friendId_is_not_exists_in_UserOrganBasic);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
			}
			//先备份好友数据以备删除出错后恢复
			userFriendly=userFriendlyService.getFriendly(userId, friendId);
			userFriendlyTarget=userFriendlyService.getFriendly(friendId, userId);
			userOrgPerCusRel=userOrgPerCusRelService.getUserOrgPerCusRel(userId, friendId);
			userOrgPerCusRelTarget=userOrgPerCusRelService.getUserOrgPerCusRel(friendId, userId);
			//删除好友
			bl1=userOrgPerCusRelService.deleteFriendly(userId,friendId);
			bl2=userOrgPerCusRelService.deleteFriendly(friendId,userId);
			bl3=userFriendlyService.deleteFriendly(userId, friendId);
			if(bl1 && bl2 && bl3){
				resultMap.put("message",Prompt.delete_friendly_successed);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
			}else{
				//恢复
				if(bl1 && userOrgPerCusRel!=null ){
					if(userOrgPerCusRelService.getUserOrgPerCusRel(userId, friendId)==null)userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
				}
				if(bl2 && userOrgPerCusRelTarget!=null){
					if(userOrgPerCusRelService.getUserOrgPerCusRel(friendId, userId)==null)userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRelTarget);
				}
				if(bl3 && userFriendly!=null){
					if(userFriendlyService.getFriendly(userId, friendId)==null)userFriendlyService.createUserFriendly(userFriendly, false);
				}
				if(bl3 && userFriendlyTarget!=null){
					if(userFriendlyService.getFriendly(friendId, userId)==null)userFriendlyService.createUserFriendly(userFriendlyTarget, false);
				}
				if(!bl3){
					if(userFriendlyService.getFriendly(userId, friendId)==null){
						if(userFriendly!=null)userFriendlyService.createUserFriendly(userFriendly, false);
					}
					if(userFriendlyService.getFriendly(friendId, userId)==null){
						if(userFriendlyTarget!=null)userFriendlyService.createUserFriendly(userFriendlyTarget, false);
					}
				}
				resultMap.put("message",Prompt.delete_friendly_failed);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
		}catch (Exception e ){
			//失败回滚
			if(bl1 && userOrgPerCusRel!=null ){
				if(userOrgPerCusRelService.getUserOrgPerCusRel(userId, friendId)==null)userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
			}
			if(bl2 && userOrgPerCusRelTarget!=null){
				if(userOrgPerCusRelService.getUserOrgPerCusRel(friendId, userId)==null)userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRelTarget);
			}
			if(bl3 && userFriendly!=null){
				if(userFriendlyService.getFriendly(userId, friendId)==null)userFriendlyService.createUserFriendly(userFriendly, false);
			}
			if(bl3 && userFriendlyTarget!=null){
				if(userFriendlyService.getFriendly(friendId, userId)==null)userFriendlyService.createUserFriendly(userFriendlyTarget, false);
			}
			if(!bl3){
				if(userFriendlyService.getFriendly(userId, friendId)==null){
					if(userFriendly!=null)userFriendlyService.createUserFriendly(userFriendly, false);
				}
				if(userFriendlyService.getFriendly(friendId, userId)==null){
					if(userFriendlyTarget!=null)userFriendlyService.createUserFriendly(userFriendlyTarget, false);
				}
			}
			logger.info("删除好友"+friendId+"失败");
			logger.info(e.getStackTrace());
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
	@RequestMapping(path = { "/user/user/updateUser" }, method = { RequestMethod.POST})
	public MappingJacksonValue updateUser(HttpServletRequest request,HttpServletResponse response
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
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(ObjectUtils.isEmpty(userLoginRegister)){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userId=userLoginRegister.getId();
			//修改组织
			if(userLoginRegister.getUsetType().intValue()==1){
				userOrganBasic= userOrganBasicService.getUserOrganBasic(userId);
				if(userOrganBasic==null){
					resultMap.put( "message", Prompt.userId_is_not_exist_in_UserOrganBasic);
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
				resultMap.put( "message", Prompt.updateUser_success);
				resultMap.put( "userId", userId);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
			}
			//修改个人
			if(userLoginRegister.getUsetType().intValue()==0){
				userBasic= userBasicService.getUserBasic(userId);
				if(userBasic==null){
					resultMap.put( "message", Prompt.userId_is_not_exist_in_UserBasic);
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
				userExt=userExtService.getUserExt(userId);
				userExt.setProvinceId(provinceId);
				userExt.setCityId(cityId);
				userExt.setCountyId(countyId);
				userExt.setIp(ip);
				userExt.setName(name);
				userExtService.updateUserExt(userExt);
				resultMap.put( "message", Prompt.updateUser_success);
				resultMap.put( "userId", userId);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
			}			
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("登录失败:"+userId);
			logger.info(e.getStackTrace());
			throw e;
		}
	}	
	/**
	 * 用户登录
	 * 
	 * @param passport 为邮箱或者手机号
	 * @param password 用户密码
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/login" }, method = { RequestMethod.POST})
	public MappingJacksonValue login(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true) String password
			)throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserBasic userBasic=null;
		UserOrganBasic userOrganBasic=null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(userLoginRegister==null){
				resultMap.put( "message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			byte[] bt = Base64.decode(password);
			String salt=userLoginRegister.getSalt();
			String newpassword=userLoginRegisterService.setSha256Hash(salt, new String(bt));
			if(!userLoginRegister.getPassword().equals(newpassword)){
				resultMap.put("message", Prompt.incorrect_password);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			
			if(isEmail(passport)){
				if(userLoginRegister.getUsetType().intValue()==0){
					userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
					if(userBasic==null){
						resultMap.put( "message", Prompt.userId_is_not_exist_in_UserBasic);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				if(userLoginRegister.getUsetType().intValue()==1){
					userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
					if(userOrganBasic==null){
						resultMap.put( "message", Prompt.userId_is_not_exist_in_UserOrganBasic);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
			}
			JSONObject json=getAccessToken(request,passport,password,client_id,client_secret,GRANT_TYPE);
			if(json==null){
				resultMap.put( "message", Prompt.get_access_token_is_null);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if(!json.has("access_token")){
				resultMap.put( "message", Prompt.get_access_token_failed);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			resultMap.put("access_token", json.has("access_token")?json.get("access_token"):"");
			resultMap.put("token_type", json.has("token_type")?json.get("token_type"):"");
			resultMap.put("refresh_token", json.has("refresh_token")?json.get("refresh_token"):"");
			resultMap.put("expires_in", json.has("expires_in")?json.get("expires_in"):"");
			resultMap.put("scope", json.has("scope")?json.get("scope"):"");
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("登录失败:"+passport);
			logger.info("认证url:"+oauthWebUrl);
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 用户登出
	 * 
	 * @param access_token 用户认证access_token
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/logout" }, method = { RequestMethod.GET})
	public MappingJacksonValue logout(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "access_token",required = true) String access_token
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(access_token)){
				resultMap.put("message", Prompt.access_token_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			String json=logoff(request,access_token);
			if(StringUtils.isEmpty(json)){
				resultMap.put("status",1);
			}else{
				resultMap.put("message", Prompt.logout_failed);
				resultMap.put("status",0);
			}
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("注销url:"+oauthWebUrl);
			logger.info(e.getMessage());
			throw e;
		}
	}	
	@RequestMapping(path = { "/user/user/getAccessToken" }, method = { RequestMethod.POST})
	public JSONObject getAccessToken(HttpServletRequest request,
			@RequestParam(name = "username",required = true) String username
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "client_id",required = true) String client_id
			,@RequestParam(name = "client_secret",required = true) String client_secret
			,@RequestParam(name = "grant_type",required = true) String grant_type
			)throws Exception {
		CloseableHttpClient httpClient = null;  
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	HttpEntity entity =null;
    	JSONObject json = null;
    	try{
	        try{
	        	RequestConfig defaultRequestConfig = RequestConfig.custom()
	        			  .setSocketTimeout(5000)
	        			  .setConnectTimeout(5000)
	        			  .setConnectionRequestTimeout(5000)
	        			  .setStaleConnectionCheckEnabled(true)
	        			  .build();
	        	httpClient = HttpClients.custom()
	        			.setDefaultRequestConfig(defaultRequestConfig)
	        			.build();
	        	RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
        	    .build();
	            HttpPost httpPost = new HttpPost(oauthWebUrl);
	            HttpEntity reqEntity = MultipartEntityBuilder.create()  
	            .addPart("username",  new StringBody(username, ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("password", new StringBody(password, ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("scope", new StringBody("getIdentifyingCode", ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("grant_type", new StringBody(grant_type, ContentType.create("text/plain", Consts.UTF_8)))
	            .build();  
	            httpPost.setEntity(reqEntity);
	            httpPost.setConfig(requestConfig);
	            httpPost.setHeader("Authorization", getAuthorizationHeaderValue(client_id,client_secret));
	            CloseableHttpResponse response = httpClient.execute(httpPost);  
	            try {  
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						resultMap.put("status", response.getStatusLine().getStatusCode());
						entity = response.getEntity();
						String respJson = EntityUtils.toString(entity);
						json = JSONObject.fromObject(respJson);
						logger.info("json:"+respJson);
					}
	                return json;
	            } finally {  
	                response.close();  
	            }  
	        }finally{  
	            httpClient.close();  
	        }
    	}catch(Exception  e){
    		logger.info(e.getMessage());
    		throw e;
    	}
	        
	}
	@RequestMapping(path = { "/user/user/logoff" }, method = { RequestMethod.GET})
	public String logoff(HttpServletRequest request,
			@RequestParam(name = "access_token",required = true) String access_token
			)throws Exception {
		CloseableHttpClient httpClient = null;  
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	HttpEntity entity =null;
    	String json = null;
    	try{
	        try{
	        	RequestConfig defaultRequestConfig = RequestConfig.custom()
	        			  .setSocketTimeout(5000)
	        			  .setConnectTimeout(5000)
	        			  .setConnectionRequestTimeout(5000)
	        			  .setStaleConnectionCheckEnabled(true)
	        			  .build();
	        	httpClient = HttpClients.custom()
	        			.setDefaultRequestConfig(defaultRequestConfig)
	        			.build();
	        	RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
        	    .build();
	            HttpGet httpGet = new HttpGet(oauthWebLogout+"?access_token="+access_token);
	            httpGet.setConfig(requestConfig);
	            CloseableHttpResponse response = httpClient.execute(httpGet);  
	            try {  
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						resultMap.put("status", response.getStatusLine().getStatusCode());
						entity = response.getEntity();
						json  = EntityUtils.toString(entity);
						return json;
					}
	                return json;
	            } finally {  
	                response.close();  
	            }  
	        }finally{  
	            httpClient.close();  
	        }
    	}catch(Exception  e){
    		logger.info(e.getMessage());
    		throw e;
    	}
	        
	}	
	public String getAuthorizationHeaderValue(String username,String password) {
	    String result = null;
	    if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
	      String value = username + ":" + password;
	      result = "Basic " + new String(Base64.encode(value.getBytes())) ;
	    }
	    return result;
	}
	/**
	 * 验证passport是否已经存在
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/validatPassport" }, method = { RequestMethod.GET })
	public MappingJacksonValue validatPassport(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(userLoginRegisterService.passportIsExist(passport)){
				if(isEmail(passport))resultMap.put("message", Prompt.email_already_exists);
				else resultMap.put("message", Prompt.email_format_is_not_correct);
				if(isMobileNo(passport))resultMap.put("message", Prompt.mobile_already_exists);
				else resultMap.put("message", Prompt.mobile_phone_number_is_not_correct);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("失败:"+passport);
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 邮箱找回密码验证地址
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/validateEmail" }, method = { RequestMethod.GET })
	public MappingJacksonValue validateEmail(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "email",required = true) String email
			,@RequestParam(name = "code",required = true) String code
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserBasic userBasic=null;
		UserOrganBasic userOrganBasic=null;
		UserLoginRegister userLoginRegister=null;
		try {
			if(code.equals(userLoginRegisterService.getIdentifyingCode(email))){
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(email);
				if(userLoginRegister==null){
					resultMap.put("message", Prompt.email_is_not_exists_in_UserLogniRegister);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(userLoginRegister.getUsetType().intValue()==0){
					userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
					if(userBasic==null){
						resultMap.put("message", Prompt.email_is_not_exists_in_UserBasic);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
				}
				if(userLoginRegister.getUsetType().intValue()==1){
					userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
					if(userOrganBasic==null){
						resultMap.put("message", Prompt.email_is_not_exists_in_UserOrganBasic);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
				}
			}else{
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(email);
				if(userLoginRegister==null){
					resultMap.put("message", Prompt.email_is_not_exists_in_UserLogniRegister);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				resultMap.put("message", Prompt.email_has_expired);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			resultMap.put("message", Prompt.email_validate_success);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info(e.getStackTrace());
			throw e;
		}
	}	
	/**
	 * 修改密码
	 * 
	 * @param passport 为邮箱和手机号
	 * @param oldpassword 旧用户密码
	 * @param newpassword 新用户密码
	 * @param source  来源的appkey
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/updatepassword" }, method = { RequestMethod.POST })
	public MappingJacksonValue updatepassword(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "oldpassword",required = true) String oldpassword
			,@RequestParam(name = "newpassword",required = true) String newpassword
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		Long userId=null;
		try {
			userId=LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(newpassword==null){
				resultMap.put("message", Prompt.newpassword_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(oldpassword==null){
				resultMap.put("message", Prompt.oldpassword_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(newpassword.length()<6){
				resultMap.put("message", Prompt.newpassword_length_must_be_greater_than_or_equal_to_6);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(oldpassword.length()<6){
				resultMap.put("message", Prompt.oldpassword_length_must_be_greater_than_or_equal_to_6);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}			
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(ObjectUtils.isEmpty(userLoginRegister)){
				resultMap.put("message", Prompt.passport_is_not_exists);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			byte[] bt = Base64.decode(oldpassword);
			String salt=userLoginRegister.getSalt();
			oldpassword=userLoginRegisterService.setSha256Hash(salt, new String(bt));
			if(!userLoginRegister.getPassword().equals(oldpassword)){
				resultMap.put("message", Prompt.incorrect_oldpassword);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			bt = Base64.decode(newpassword);
			salt=userLoginRegister.getSalt();
			newpassword=userLoginRegisterService.setSha256Hash(salt, new String(bt));
			userLoginRegisterService.updatePassword(userLoginRegister.getId(), newpassword);
			resultMap.put("message", Prompt.update_password_successed);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("修改密码失败:"+userId);
			logger.info(e.getStackTrace());
			throw e;
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param password 密码
	 * @param confirmPassword 确认密码
	 * @param code 验证码
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/resetPassword" }, method = { RequestMethod.POST })
	public MappingJacksonValue resetPassword(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "confirmPassword",required = true) String confirmPassword
			,@RequestParam(name = "code",required = true) String code
			,@RequestParam(name = "passport",required = true) String passport
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		Long userId=null;
		try {
			if(StringUtils.isEmpty(password)){
				resultMap.put("message", Prompt.passowrd_cannot_be_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(StringUtils.isEmpty(confirmPassword)){
				resultMap.put("message", Prompt.confirmPassword_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(password.length()<6){
				resultMap.put("message", Prompt.password_length_must_be_greater_than_or_equal_to_6);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(confirmPassword.length()<6){
				resultMap.put("message", Prompt.confirmPassword_length_must_be_greater_than_or_equal_to_6);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(!password.equals(confirmPassword)){
				resultMap.put("message", Prompt.password_and_confirmPassword_do_not_match);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(!isMobileNo(passport) && !isEmail(passport)){
				resultMap.put( "message", Prompt.passport_is_not_right_phone_number_or_email);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(ObjectUtils.isEmpty(userLoginRegister)){
				resultMap.put("message", Prompt.passport_is_not_exists);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(!code.equals(userLoginRegisterService.getIdentifyingCode(passport))){
				resultMap.put( "message", Prompt.code_is_not_right);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			byte[] bt = Base64.decode(password);
			String salt=userLoginRegister.getSalt();
			String resetpassword=userLoginRegisterService.setSha256Hash(salt, new String(bt));
			if(userLoginRegisterService.updatePassword(userLoginRegister.getId(), resetpassword)){
				userLoginRegisterService.deleteIdentifyingCode(passport);
				resultMap.put("message", Prompt.reset_password_successed);
				resultMap.put("status",1);
			}else{
				resultMap.put("message", Prompt.reset_password_failed);
				resultMap.put("status",0);
			}
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("修改密码失败:"+userId);
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	
	/**
	 * 获取手机注册和邮箱注册验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getIdentifyingCode" }, method = { RequestMethod.GET})
	public MappingJacksonValue getIdentifyingCode(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "passport",required = true) String passport
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code=null;
		try {
				if(StringUtils.isEmpty(passport)){
					resultMap.put( "message", Prompt.passport_is_null_or_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!isMobileNo(passport) && !isEmail(passport)){
					resultMap.put( "message", Prompt.passport_is_not_right_phone_number_or_email);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(userLoginRegisterService.passportIsExist(passport)){
//					userLoginRegisterService.realDeleteUserLoginRegister(userLoginRegisterService.getUserLoginRegister(passport).getId());
					resultMap.put( "message", Prompt.passport_already_exists);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(isMobileNo(passport)){
					code=userLoginRegisterService.sendIdentifyingCode(passport);
					if(StringUtils.isEmpty(code)){
						resultMap.put( "message", Prompt.failed_to_get_the_mobile_verfication_code);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);	
					}else{
						resultMap.put( "code", code);
						resultMap.put( "status", 1);
					}
				}
				if(isEmail(passport)){
//					code=userLoginRegisterService.getIdentifyingCode(passport);
//					if(StringUtils.isEmpty(code)){
						code=generationIdentifyingCode();
						Map<String, Object> map = new HashMap<String, Object>();
				        map.put("acceptor",passport);
				        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
				        map.put("code", code);
				        map.put("type","0");
						if(userLoginRegisterService.sendEmail(passport, 0, map)){
							resultMap.put( "code", code);
							resultMap.put( "status", 1);
						}else{
							resultMap.put( "message", Prompt.sendmail_failed_get_the_verfication_code_failed);
							resultMap.put( "status", 0);
							return new MappingJacksonValue(resultMap);	
						}
//					}else{
//						resultMap.put( "code", code);
//						resultMap.put( "status", 1);
//					}
				}
				logger.info(new StringBuffer().append("通行证:").append(passport).append(",的验证码为:").append(code).append(",有效期为30分钟!").toString());
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info(e.getStackTrace());
			throw e;
		}
	}

	/**
	 * 手机和邮箱找回密码获取验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getBackPasswordCode" }, method = { RequestMethod.GET})
	public MappingJacksonValue getBackPasswordCode(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "passport",required = true) String passport
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code=null;
		try {
				if(StringUtils.isEmpty(passport)){
					resultMap.put( "message", Prompt.passport_is_null_or_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!isMobileNo(passport) && !isEmail(passport)){
					resultMap.put( "message", Prompt.passport_is_not_right_phone_number_or_email);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!userLoginRegisterService.passportIsExist(passport)){
					resultMap.put( "message", Prompt.passport_is_not_exists);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(isMobileNo(passport)){
					code=userLoginRegisterService.sendIdentifyingCode(passport);
					if(StringUtils.isEmpty(code)){
						resultMap.put( "message",Prompt.failed_to_get_the_mobile_verfication_code);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);	
					}else{
						resultMap.put( "code", code);
						resultMap.put( "status", 1);
					}
				}
				if(isEmail(passport)){
//					code=userLoginRegisterService.getIdentifyingCode(passport);
//					if(StringUtils.isEmpty(code)){
						code=generationIdentifyingCode();
						Map<String, Object> map = new HashMap<String, Object>();
				        map.put("email", emailValidateUrl+"?email="+passport+"&code="+code);
				        map.put("acceptor",passport);
				        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
				        map.put("code", code);
				        map.put("type","2");
						if(userLoginRegisterService.sendEmail(passport, 2, map)){
							resultMap.put( "code", code);
							resultMap.put( "status", 1);
						}else{
							resultMap.put( "message", Prompt.sendmail_failed_get_the_verfication_code_failed);
							resultMap.put( "status", 0);
							return new MappingJacksonValue(resultMap);	
						}
//					}else{
//						resultMap.put( "code", code);
//						resultMap.put( "status", 1);
//					}
				}
				logger.info(new StringBuffer().append("通行证:").append(passport).append(",找回密码验证码为:").append(code).append(",有效期为30分钟!").toString());
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 根据地区省ID获取用户列表
	 * 
	 * @param passport 用户通行证
	 * @param provinceId 省ID
	 * @param start 开始位置 0为起始位置
	 * @param count 每页多少个
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getUserListByProvinceId" }, method = { RequestMethod.GET})
	public MappingJacksonValue getUserListByProvinceId(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "provinceId",required = true) Long provinceId
		,@RequestParam(name = "start",required = true) int start
		,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long userId=null;
		try {
				userId = LoginUserContextHolder.getUserId();
				if(userId==null){
					resultMap.put("message", Prompt.userId_is_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(userLoginRegisterService.getUserLoginRegister(userId)==null){
					resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(provinceId)){
					resultMap.put( "message", Prompt.provinceId_is_null_or_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(start<0){
					resultMap.put( "message", Prompt.start_must_be_than_zero);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(count<=0){
					resultMap.put( "message", Prompt.count_must_be_than_zero);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				List<UserExt> list=userExtService.getUserExtListByProvinceId(start, count, provinceId);
				if(list==null || list.size()==0){
					resultMap.put( "status", 0);
					resultMap.put("message", Prompt.not_found_userId_list);
					return new MappingJacksonValue(resultMap);
				}
				List<Long> ids=new ArrayList<Long>();
				for (UserExt userExt : list) {
					if(userExt!=null)ids.add(userExt.getUserId());
				}
				List<UserBasic> list2 = userBasicService.getUserBasecList(ids);
				resultMap.put( "status", 1);
				resultMap.put( "list", list2);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			throw e;
		}
	}	
	/**
	 * 根据第三级行业ID获取用户列表
	 * 
	 * @param passport 用户通行证
	 * @param thirdIndustryId 三级行业ID
	 * @param start 开始位置 0为起始位置
	 * @param count 每页多少个
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getUserListByThirdIndustryId" }, method = { RequestMethod.GET})
	public MappingJacksonValue getUserListByThirdIndustryId(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "thirdIndustryId",required = true) Long thirdIndustryId
			,@RequestParam(name = "start",required = true) int start
			,@RequestParam(name = "count",required = true) int count
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long userId=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(userLoginRegisterService.getUserLoginRegister(userId)==null){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(StringUtils.isEmpty(thirdIndustryId)){
				resultMap.put( "message", Prompt.thirdIndustryId_is_null_or_empty);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if(start<0){
				resultMap.put( "message", Prompt.start_must_be_than_zero);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if(count<=0){
				resultMap.put( "message", Prompt.count_must_be_than_zero);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			List<UserExt> list=userExtService.getUserListByThirdIndustryId(start, count, thirdIndustryId);
			if(list==null || list.size()==0){
				resultMap.put( "status", 0);
				resultMap.put("message", Prompt.not_found_userId_list);
				return new MappingJacksonValue(resultMap);
			}
			List<Long> ids=new ArrayList<Long>();
			for (UserExt userExt : list) {
				if(userExt!=null)ids.add(userExt.getUserId());
			}
			List<UserBasic> list2 = userBasicService.getUserBasecList(ids);
			resultMap.put( "status", 1);
			resultMap.put( "list", list2);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info(e.getStackTrace());
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
	 * 生成验证码
	 * @return String
	 */
	private  String generationIdentifyingCode(){
		Random random = new Random();
		StringBuffer sfb=new StringBuffer();
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sfb.append(rand);
		}
		return sfb.toString();
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
