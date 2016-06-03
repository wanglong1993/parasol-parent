package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.sf.json.JSONArray;
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

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.message.service.MessageRelationService;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.parasol.user.model.User;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserConfig;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.model.UserFriendly;
import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;
import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.model.UserOrganExt;
import com.ginkgocap.parasol.user.model.UserWorkHistory;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserConfigerService;
import com.ginkgocap.parasol.user.service.UserDefinedService;
import com.ginkgocap.parasol.user.service.UserEducationHistoryService;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.parasol.user.service.UserInfoService;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
import com.ginkgocap.parasol.user.service.UserOrgPerCusRelService;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;
import com.ginkgocap.parasol.user.service.UserOrganExtService;
import com.ginkgocap.parasol.user.service.UserWorkHistoryService;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Base64;
import com.ginkgocap.parasol.user.web.jetty.web.utils.HuanxinUtils;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Prompt;
import com.ginkgocap.parasol.user.web.jetty.web.utils.ThreadPoolUtils;
import com.gintong.easemob.server.comm.GsonUtils;
import com.gintong.rocketmq.api.DefaultMessageService;
import com.gintong.rocketmq.api.enums.FlagType;
import com.gintong.rocketmq.api.enums.TopicType;
import com.gintong.rocketmq.api.model.RocketSendResult;

/**
 * 用户登录注册
 */
@RestController
public class UserController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserController.class);
	private static long sourceType  = 2l;
	private static int sourceType2  = 2;
	@Autowired
	private UserLoginThirdService userLoginThirdService;
	@Autowired
	private UserLoginRegisterService userLoginRegisterService;
	@Autowired
	private UserBasicService userBasicService;
//	@Autowired
//	private UserExtService userExtService;
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
//	@Autowired
//	private UserContactWayService userContactWayService;
	@Autowired
	private UserEducationHistoryService userEducationHistoryService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserWorkHistoryService userWorkHistoryService;
	@Autowired
	private TagSourceService tagSourceService;
	@Autowired
	private AssociateService associateService;
	@Autowired
	private DirectorySourceService directorySourceService;	
	@Autowired
	private UserConfigerService userConfigerService;	
	@Autowired
	private DefaultMessageService defaultMessageService;	
	@Autowired
	private CodeRegionService codeRegionService;	
	
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
	@Value("${email.validate.url.gintong}")  
	private String emailValidateUrlgintong;
	@Value("${email.bind.url.gintong}")  
	private String emailBindUrlgintong;	
	@Value("${email.validate.url.coopert}")  
	private String emailValidateUrlCoopert; 	
	@Value("${email.findpwd.url.coopert}")  
	private String emailFindpwdUrlCoopert; 	
	@Value("${dfs.gintong.com}")  
	private String dfsGintongCom; 	
    private static final String GRANT_TYPE="password"; 
    private static final String CLASS_NAME = UserController.class.getName();


	/**
	 * 完善个人用户信息
	 * @picId 个人或组织LOGOID
	 * @param name 昵称,企业全称
	 * @param phone 手机号
	 * @param email 邮箱
	 * @param companyName 所在公司
	 * @throws Exception
	 * @return MappingJacksonValue
	 */
	@RequestMapping(path = { "/user/user/createPerfectionInfo" }, method = { RequestMethod.POST })
	public MappingJacksonValue createPerfectionInfo(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "picId",required = false) Long picId
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "email",required = false) String email
			,@RequestParam(name = "mobile",required = false) String mobile
			,@RequestParam(name = "companyName",required = false) String companyName
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserBasic userBasic= null;
		Long appId =0l;
		Long userId=0L;
		Long userBasicId=0L;
		boolean bl=false;
		try {
				userId = LoginUserContextHolder.getUserId();
				if(userId==null){
					resultMap.put("message", Prompt.userId_is_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				appId = LoginUserContextHolder.getAppKey();
				if(ObjectUtils.isEmpty(appId)){
					resultMap.put( "message", Prompt.appId_is_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(name)){
					resultMap.put( "message", "用户昵称不能为空！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
				if(ObjectUtils.isEmpty(userLoginRegister)){
					resultMap.put( "message", Prompt.user_is_not_exists_in_UserLoginRegister);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!StringUtils.isEmpty(email))userLoginRegister.setEmail(email);
				if(!StringUtils.isEmpty(mobile))userLoginRegister.setMobile(mobile);
				bl=userLoginRegisterService.updataUserLoginRegister(userLoginRegister);
				if(bl==false){
					resultMap.put("message", Prompt.user_perfectionInfo_is_failed);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				userBasic=userBasicService.getObject(userId);
				if(ObjectUtils.isEmpty(userBasic)){
					userBasic=new UserBasic();
					userBasic.setName(name);
					userBasic.setCompanyName(companyName);
					userBasic.setPicId(picId);
					userBasic.setSex(new Byte("1"));
					userBasic.setUserId(userId);
					userBasicId=userBasicService.createObject(userBasic);
					if(userBasicId==null && userBasicId<=0l){
						resultMap.put("message", Prompt.user_perfectionInfo_is_failed);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
					resultMap.put( "status", 1);
					return new MappingJacksonValue(resultMap);
				}else{
					userBasic.setName(name);
					userBasic.setCompanyName(companyName);
					userBasic.setPicId(picId);
					userBasic.setUserId(userId);
					if(userBasic.getSex().intValue()!=1 && userBasic.getSex().intValue()!=2 && userBasic.getSex().intValue()!=0)userBasic.setSex(new Byte("1"));
					bl=userBasicService.updateObject(userBasic);
					if(bl==false){
						resultMap.put("message", Prompt.user_perfectionInfo_is_failed);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);
					}
					resultMap.put("status", 1);
					return new MappingJacksonValue(resultMap);
				}

		}catch (Exception e ){
			//异常失败回滚
			if(userId!=null && userId>0L)userLoginRegisterService.realDeleteUserLoginRegister(userId);
			if(userBasicId!=null && userBasicId>0l)userBasicService.deleteObject(userBasicId);
			logger.info("用户Id"+userId+"完善信息失败:");
			logger.info(e.getStackTrace());
			throw e;
		}
	}    
	/**
	 * 修改和创建感兴趣的标签
	 * @param tagIds 标签ID字符串，以逗号分隔
	 * @throws Exception
	 * @return MappingJacksonValue
	 */
	@RequestMapping(path = { "/user/user/createIterestedTag" }, method = { RequestMethod.POST })
	public MappingJacksonValue createInterestedTag(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "tagIds",required = true) String tagIds
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserInterestIndustry userInterestIndustry=null;
		Long appId =0l;
		Long userId=0L;
		String[] careTagIds=null;
		String ip=getIpAddr(request);
		List<UserInterestIndustry> list =null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			appId = LoginUserContextHolder.getAppKey();
			if(ObjectUtils.isEmpty(appId)){
				resultMap.put( "message", Prompt.appId_is_empty);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(ObjectUtils.isEmpty(userLoginRegister)){
				resultMap.put( "message", Prompt.user_is_not_exists_in_UserLoginRegister);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if(!StringUtils.isEmpty(tagIds))careTagIds=tagIds.split(",");
			list=new ArrayList<UserInterestIndustry>();
			if(careTagIds!=null && careTagIds.length>0){
				for (String tagId : careTagIds) {
					userInterestIndustry= new UserInterestIndustry();
					userInterestIndustry.setUserId(userId);
					userInterestIndustry.setFirstIndustryId(new Long(tagId));
					userInterestIndustry.setIp(ip);
					list.add(userInterestIndustry);
					list=userInterestIndustryService.createUserInterestIndustryByList(list, userId);
				}
			}
			resultMap.put( "message", Prompt.Operation_succeeded);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
			
		}catch (Exception e ){
			logger.info("创建用户："+userId+",感兴趣的标签失败:");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	
 
	/**
	 * 生成登录二维码和绑定组织二维码
	 * @param id 组织id
	 * @param type 二维码尺寸类型
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getQrcode" }, method = { RequestMethod.GET })
	public void getQrcode(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "id",required =true) String id
			,@RequestParam(name = "type",required = false ,defaultValue ="3") int type
			)throws Exception{
		int width=0;
		int height=0;
		ByteArrayOutputStream out=null;
			try {
				if(type==1)width=height=90;
				if(type==2)width=height=140;
				if(type==3)width=height=250;
				if(type==4)width=height=300;
				if(type==4)width=height=400;
				out =QRCode.from(id).to(ImageType.PNG).withSize(width,height).stream();
	            response.setContentType("image/png");  
	            response.setContentLength(out.size());  
	            OutputStream outStream = response.getOutputStream();
	            outStream.write(out.toByteArray());  
	            outStream.flush();  
	            outStream.close();
	            System.out.println("cache="+userLoginRegisterService.setCache(id, "1", 1 * 30 * 1)+",,id="+id);
		}catch (Exception e ){
			logger.info("生成登录二维码和绑定组织二级码失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 获取二维码扫描登录ID
	 * @param id 组织id
	 * @param type 二维码尺寸类型
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getQrcodeId" }, method = { RequestMethod.GET })
	public MappingJacksonValue getQrcodeId(HttpServletRequest request,HttpServletResponse response
			)throws Exception{
		String uuid=null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			uuid=UUID.randomUUID().toString();
			resultMap.put( "status", 1);
			resultMap.put( "qrid", uuid);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("获取二维码扫描登录ID失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 获取缓存的二维码扫描登录ID
	 * @param id 二维码id
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getQrId" }, method = { RequestMethod.GET })
	public MappingJacksonValue getQrId(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "id",required =true) String id
			)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put( "qrid", userLoginRegisterService.getCache(id));
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("获取二维码扫描登录ID失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 获取二维码对应的用户名密码
	 * @param id id
	 * @param passport 邮箱或者手机号
	 * @param password 密码
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getLoginByQrcode" }, method = { RequestMethod.GET })
	public MappingJacksonValue getLoginByQrcode(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "id",required = true) String id
			,@RequestParam(name = "timeout",required = true ,defaultValue ="30000") int timeout
			)throws Exception{
		String passport=null;
		String password=null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
			try {
				Object value=null;
				long afterTime = System.currentTimeMillis()+timeout;
				System.out.println("afterTime="+afterTime);
				long currentTime=0l;
				while(true){
					Thread.sleep(5000); 
					currentTime=System.currentTimeMillis();
					System.out.println("currentTime="+currentTime);
					long t=currentTime-afterTime;
					System.out.println("ttttt="+t+",,,threadname="+Thread.currentThread().getName());
					if(t>=0){
						resultMap.put( "status", 0);
						resultMap.put( "message", "请求超时！");
						value=userLoginRegisterService.getCache(id);
						if(!ObjectUtils.isEmpty(value)){
							if(!value.toString().equals("1")){
								resultMap.put( "status", 1);
								resultMap.put( "message", "请求成功");
								System.out.println("value="+value+",id="+id);
								passport=value.toString().split(",")[0];
								password=value.toString().split(",")[1];
								resultMap.put( "passport", passport);
								resultMap.put( "password", password);
								break;
							}
						}
						break;
					}
					value=userLoginRegisterService.getCache(id);
					if(!ObjectUtils.isEmpty(value)){
						if(!value.toString().equals("1")){
							resultMap.put( "status", 1);
							resultMap.put( "message", "请求成功");
							System.out.println("value="+value+",id="+id);
							passport=value.toString().split(",")[0];
							password=value.toString().split(",")[1];
							resultMap.put( "passport", passport);
							resultMap.put( "password", password);
							break;
						}
					}else{
						resultMap.put( "status", 0);
						resultMap.put( "message", "二维码已经过期！或者不存在");
					}
				}
				System.out.println("over="+111111111);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("获取二维码对应的用户名密码失败！");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 组织绑定一个用户
	 * @param id 组织ID
	 * @param passport 邮箱或者手机号
	 * @param password 密码
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/orgBindUser" }, method = { RequestMethod.POST })
	public MappingJacksonValue orgBindUser(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "orgId",required = true) long orgId
			,@RequestParam(name = "passport",required = true ) String passport
			,@RequestParam(name = "password",required = true ) String password
			)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long userId=null;
		Long appId=null;
		UserLoginRegister userLoginRegister=null;
		UserLoginRegister userLoginRegister2=null;
			try {
				userId = LoginUserContextHolder.getUserId();
				if(userId==null){
					resultMap.put("message", Prompt.userId_is_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				appId = LoginUserContextHolder.getAppKey();
				if(ObjectUtils.isEmpty(appId)){
					resultMap.put( "message", Prompt.appId_is_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				userLoginRegister2=userLoginRegisterService.getUserLoginRegister(orgId);
				if(ObjectUtils.isEmpty(userLoginRegister2)){
					resultMap.put("message", Prompt.orgId_is_not_exists);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(passport)){
					resultMap.put("message", Prompt.passport_is_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(password)){
					resultMap.put("message", Prompt.passowrd_cannot_be_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
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
				userLoginRegister.setOrgId(orgId);
				userLoginRegisterService.updataUserLoginRegister(userLoginRegister2);
				resultMap.put("message", Prompt.Operation_succeeded);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("获取二维码对应的用户名密码失败！");
			logger.info(e.getStackTrace());
			throw e;
		}
	}	
	/**
	 * 设置登录二维码对应的用户名密码
	 * @param id id
	 * @param passport 邮箱或者手机号
	 * @param password 密码
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/setLoginForQrcode" }, method = { RequestMethod.POST })
	public MappingJacksonValue setLoginForQrcode(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "id",required = true) String id
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true ) String password
			)throws Exception{
		Long appId =0l;
		Long userId=0L;
		Map<String, Object> resultMap = new HashMap<String, Object>();
			try {
//				userId = LoginUserContextHolder.getUserId();
//				if(userId==null){
//					resultMap.put("message", Prompt.userId_is_null_or_empty);
//					resultMap.put("status",0);
//					return new MappingJacksonValue(resultMap);
//				}
//				appId = LoginUserContextHolder.getAppKey();
//				if(ObjectUtils.isEmpty(appId)){
//					resultMap.put( "message", Prompt.appId_is_empty);
//					resultMap.put( "status", 0);
//					return new MappingJacksonValue(resultMap);
//				}
//				if(ObjectUtils.isEmpty(passport)){
//					resultMap.put( "message", "通行证不能为空！");
//					resultMap.put( "status", 0);
//					return new MappingJacksonValue(resultMap);
//				}
//				if(ObjectUtils.isEmpty(password)){
//					resultMap.put( "message", "密码不能为空！");
//					resultMap.put( "status", 0);
//					return new MappingJacksonValue(resultMap);
//				}
				Object value=userLoginRegisterService.getCache(id);
				if(ObjectUtils.isEmpty(value)){
					resultMap.put( "message", "二维码已经过期！或者不存在");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(ObjectUtils.isEmpty(appId)){
					resultMap.put( "message", Prompt.appId_is_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!userLoginRegisterService.setCache(id, passport+","+password, 1 * 30 * 1)){
					resultMap.put( "message", Prompt.Operation_failed);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				resultMap.put( "status", 1);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("设置登录二维码对应的用户名密码失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}	
	/**
	 * 用户注册
	 * @param type 1.邮箱注册,2.手机注册
	 * @param code 手机验证码
	 * @param passport 为邮箱和手机号
	 * @param password 用户密码
	 * @param userType 0.个人用户,1.组织用户
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
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserBasic userBasic= null;
//		UserExt userExt= null;//有问题
		List<UserInterestIndustry> list = null;
		User user=null;
		String ip=getIpAddr(request);
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
				//验证code
//				if((type==2 || type==1) && userType.equals("0")){
					if(!code.equals(userLoginRegisterService.getIdentifyingCode(passport))){
						resultMap.put( "message", Prompt.code_is_not_right);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
//				}
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
					/**
					 * 添加集成环信注册用户
					 */
					if (id > 1) {
						final String huanxinParam = String.valueOf(id);
						ThreadPoolUtils.getExecutorService().execute(new Runnable() {
							@Override
							public void run() {
								HuanxinUtils.addUser(huanxinParam, huanxinParam,CLASS_NAME);
							}
						});
					}
					//向万能插座发送消息
					user = new User();
					user.setUserLoginRegister(userLoginRegister);
					user.setUserBasic(userBasic);
//					user.setUserExt(userExt);
					user.setListUserInterestIndustry(list);
					defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.USER_SAVE, GsonUtils.objectToString(user));
					resultMap.put( "id", id);
					resultMap.put( "status", 1);
					return new MappingJacksonValue(resultMap);
				}
				//个人用手机注册
				if(type==2 && userType.equals("0")){
					/**
					 * 添加集成环信注册用户
					 */
					if (id > 1) {
						final String huanxinParam = String.valueOf(id);
						ThreadPoolUtils.getExecutorService().execute(new Runnable() {
							@Override
							public void run() {
								HuanxinUtils.addUser(huanxinParam, huanxinParam,CLASS_NAME);
							}
						});
					}
					//用户设置
					UserConfig userConfig =new UserConfig();
					userConfig.setUserId(id);
					userConfig.setHomePageVisible(new Byte("2"));
					userConfig.setEvaluateVisible(new Byte("2"));
					userConfig.setAutosave(new Byte("0"));
					userConfigerService.createUserConfig(userConfig);
					userLoginRegisterService.deleteIdentifyingCode(passport);
					//向万能插座发送消息
					user = new User();
					user.setUserLoginRegister(userLoginRegister);
					user.setUserBasic(userBasic);
//					user.setUserExt(userExt);
					user.setListUserInterestIndustry(list);
					defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.USER_SAVE, GsonUtils.objectToString(user));
					resultMap.put( "id", id);
					resultMap.put( "status", 1);
					return new MappingJacksonValue(resultMap);
				}
				//组织用户邮箱注册
				if(type==1 && userType.equals("1")){
					/**
					 * 添加集成环信注册用户
					 */
					if (id > 1) {
						final String huanxinParam = String.valueOf(id);
						ThreadPoolUtils.getExecutorService().execute(new Runnable() {
							@Override
							public void run() {
								HuanxinUtils.addUser(huanxinParam, huanxinParam,CLASS_NAME);
							}
						});
					}
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
			logger.info("注册失败:"+passport);
			logger.info(e.getStackTrace());
			throw e;
		}
	}

	/**
	 * 获取用户资料
	 * 
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getUserBasic" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserBasic(HttpServletRequest request,HttpServletResponse response
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserBasic userBasic= null;
		Long userId=null;
		Long appId =0l;
		MappingJacksonValue mappingJacksonValue = null;
		try {
			userId = LoginUserContextHolder.getUserId();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			appId = LoginUserContextHolder.getAppKey();
			if(ObjectUtils.isEmpty(appId)){
				resultMap.put( "message", Prompt.appId_is_empty);
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}			
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userBasic=userBasicService.getObject(userId);
				resultMap.put("userBasic", userBasic);
				resultMap.put("userLoginRegister", userLoginRegister);
				resultMap.put("status",1);
				mappingJacksonValue = new MappingJacksonValue(resultMap);
//				SimpleFilterProvider filterProvider = builderSimpleFilterProvider("id,tagName");
				mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter(UserBasic.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept("name")));
			return mappingJacksonValue;
		}catch (Exception e ){
			logger.info("获取用户资料失败:"+userId);
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	
	
	/**
	 * 指定显示那些字段
	 * 
	 * @param fileds
	 * @return
	 */
	private SimpleFilterProvider builderSimpleFilterProvider(String fileds) {
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		// 请求指定字段
		String[] filedNames = StringUtils.split(fileds, ",");
		Set<String> filter = new HashSet<String>();
		if (filedNames != null && filedNames.length > 0) {
			for (int i = 0; i < filedNames.length; i++) {
				String filedName = filedNames[i];
				if (!StringUtils.isEmpty(filedName)) {
					filter.add(filedName);
				}
			}
		} else {
			filter.add("id"); // id',
			filter.add("sourceId"); // 资源ID
			filter.add("sourceType"); // 资源类型
			filter.add("tagName"); // 标签名称
		}

		filterProvider.addFilter(TagSource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		filterProvider.addFilter(DirectorySource.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept("id","directoryId","sourceTitle","sourceUrl"));
		filterProvider.addFilter(Associate.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept("id","assocTitle"));
		return filterProvider;
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
	 * @param friendIds 要添加的好友的用户ID,ID是以逗号分隔的字符串
	 * @param content 添加用户时发送的消息.
	 * @param appId 来源应用id.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/applyToAddFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue applyToAddFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "friendIds",required = true) String friendIds
			,@RequestParam(name = "content",required = false) String content
			,@RequestParam(name = "status",required = true) String status
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserFriendly userFriendly=null;
		Long userId=null;
		Long appId=null;
		List<Long> listFriendsid=new ArrayList<Long>();
		String[] tempsarray=null;
		Long id=null;
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
			if(StringUtils.isEmpty(friendIds)){
				resultMap.put("message", Prompt.friendIds_is_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			tempsarray=friendIds.split(",");
			for (int i = 0; i < tempsarray.length; i++) {
				id=new Long(tempsarray[i]);
				if(userLoginRegisterService.getUserLoginRegister(id)!=null){
					listFriendsid.add(id);
				}
			}
			for (Long friendId : listFriendsid) {
				userFriendly=new UserFriendly();
				userFriendly.setUserId(userId);
				userFriendly.setFriendId(friendId);
				userFriendly.setStatus(new Byte(status));
				userFriendly.setContent(StringUtils.isEmpty(content)==true?"你好":content);
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
			}
			resultMap.put("message", Prompt.apply_to_add_friendly_successed);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("添加好友"+friendIds+"失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 获取申请添加当前用户为好友的申请列表
	 * @param appId 来源应用id.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getApplyToAddFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue getApplyToAddFriendly(HttpServletRequest request,HttpServletResponse response
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long userId=null;
		Long appId=null;
		List<UserFriendly> listFriends=null;
		List<UserBasic> listUserBasic=null;
		Long id=null;
		UserBasic userBasic=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			appId=LoginUserContextHolder.getAppKey();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			listFriends=new ArrayList<UserFriendly>();
			listFriends=userFriendlyService.getApplyFriendlyList(userId);
			listUserBasic=new ArrayList<UserBasic>();
			for (UserFriendly userFriendly : listFriends) {
				userBasic=userBasicService.getObject(userFriendly.getFriendId()); //有问题
				if(!ObjectUtils.isEmpty(userBasic)){
					userBasic.setPicPath(dfsGintongCom+userBasic.getPicPath());
					listUserBasic.add(userBasic);
				}
			}
			resultMap.put("status",1);
			resultMap.put("listUserBasic",listUserBasic);
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(resultMap);
			mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter(UserBasic.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept("userId","name","picId","companyName")));
			return mappingJacksonValue;
		}catch (Exception e ){
			logger.info("获取申请添加当前用户为好友的申请列表:"+userId+"失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 设置金桐号
	 * @param gid 金桐号.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/setGid" }, method = { RequestMethod.POST })
	public MappingJacksonValue setGid(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "gid",required = true) String gid
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long userId=null;
		Long appId=null;
		UserLoginRegister userLoginRegister=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			appId=LoginUserContextHolder.getAppKey();
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(gid.length()<5 || gid.length()>50){
				resultMap.put("message", Prompt.gId_length_error);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegisterByGid(gid);
			if(userLoginRegister!=null){
				resultMap.put("message", Prompt.gid_is_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(userId);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists_in_UserLoginRegister);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userLoginRegister.setGid(gid);
			userLoginRegisterService.updataUserLoginRegister(userLoginRegister);
			resultMap.put("status",1);
			resultMap.put("message", Prompt.update_passport_is_successed);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("获取申请添加当前用户为好友的申请列表:"+userId+"失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 添加我的好友到目录或标签
	 * @param userId  当前用户ID
	 * @param friendIds 要添加的好友的用户ID,ID是以逗号分隔的字符串
	 * @param appId 来源应用id.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/addFriendlyToDirectoryOrTag" }, method = { RequestMethod.POST })
	public MappingJacksonValue addFriendlyToDirectoryOrTag(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "friendIds",required = true) String friendIds
			,@RequestParam(name = "directoryId",required = false) Long directoryId
			,@RequestParam(name = "tagId",required = false) Long tagId
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserOrgPerCusRel userOrgPerCusRel=null;
		Long userId=null;
		Long appId=null;
		List<Long> listFriendsid=new ArrayList<Long>();
		List<UserOrgPerCusRel> userOrgPerCusRelList=null;
		String[] tempsarray=null;
		Long id=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			appId=LoginUserContextHolder.getAppKey();
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
			if(StringUtils.isEmpty(directoryId)){
				resultMap.put("message", Prompt.directoryId_is_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(StringUtils.isEmpty(tagId)){
				resultMap.put("message", Prompt.tagId_is_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(ObjectUtils.isEmpty(friendIds)){
				resultMap.put("message", Prompt.friendIds_is_empty);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			tempsarray=friendIds.split(",");
			for (int i = 0; i < tempsarray.length; i++) {
				id=new Long(tempsarray[i]);
				if(userLoginRegisterService.getUserLoginRegister(id)!=null){
					listFriendsid.add(id);
				}
			}
			userOrgPerCusRelList=new ArrayList<UserOrgPerCusRel>();
			for (Long friendId : listFriendsid) {
				userOrgPerCusRel=userOrgPerCusRelService.getUserOrgPerCusRel(userId, friendId);
				if(!ObjectUtils.isEmpty(userOrgPerCusRel!=null)){
					userOrgPerCusRel.setDirectoryId(directoryId);
					userOrgPerCusRel.setTagId(tagId);
					userOrgPerCusRelList.add(userOrgPerCusRel);
				}
				userOrgPerCusRelService.updateUserOrgPerCusRelList(userOrgPerCusRelList);
			}
			resultMap.put("message", Prompt.apply_to_add_friendly_successed);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("添加我的好友到目录或标签"+friendIds+"失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 修改用户备注名
	 * @param userId  当前用户ID
	 * @param friendId 好友的ID
	 * @param appId 来源应用id.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/updateFriendlyName" }, method = { RequestMethod.POST })
	public MappingJacksonValue updateFriendlyName(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "name",required = false) String name
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		Long userId=null;
		Long appId=null;
		Long id=null;
		try {
			userId = LoginUserContextHolder.getUserId();
			appId=LoginUserContextHolder.getAppKey();
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
			userOrgPerCusRelService.updateName(userId, friendId, name);
			resultMap.put("message", Prompt.apply_to_add_friendly_successed);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("修改用户备注名"+friendId+"失败");
			logger.info(e.getStackTrace());
			throw e;
		}
	}	
	/**
	 * 申请添加好友：临时使用此方法
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param content 添加用户时发送的消息.
	 * @param appId 来源应用id.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userext/user/applyToAddFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue extApplyToAddFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "userId",required = true) Long userId
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "status",required = true) String status
			,@RequestParam(name = "appId",required = true) Long appId
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserFriendly userFriendly=null;
		try {
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
					userBasic=userBasicService.getObject(userLoginRegisterFriend.getId());
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
				bl=userFriendlyService.updateStatus(userId,friendId, new Byte(status));
				if(!bl){
					resultMap.put("message", Prompt.update_Friendly_status_friendId_failed+ friendId+",userId:"+userId);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				UserFriendly uf=userFriendlyService.getFriendly(friendId,userId);
				if(uf!=null){
					if(uf.getStatus().intValue()==1){
						resultMap.put("message", friendId +Prompt.is_already_a_good_friend_of_yours_cannot_repeat_add_him);
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
						userBasic=userBasicService.getObject(userLoginRegister.getId());
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
					//向万能插座发送消息
					defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.ADD_FRIEND,"{\"friendId\":"+friendId+",\"userId\":"+userId+"}");
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
						userBasic=userBasicService.getObject(userLoginRegister.getId());
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
					//向万能插座发送消息
					defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.ADD_FRIEND,"{\"friendId\":"+friendId+",\"userId\":"+userId+"}");
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
	 * 同意添加好友:临时使用些方法
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param appId  应用的appId.
	 * @param status 申请状态.
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userext/user/auditByAddFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue extAuditByAddFriendly(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "friendId",required = true) Long friendId
			,@RequestParam(name = "userId",required = true) Long userId
			,@RequestParam(name = "appId",required = true) Long appId
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
			if(userId==null){
				resultMap.put("message", Prompt.userId_is_null_or_empty);
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
				userBasic=userBasicService.getObject(userLoginRegisterFriend.getId());
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
					userBasic=userBasicService.getObject(userLoginRegister.getId());
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
					userBasic=userBasicService.getObject(userLoginRegister.getId());
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
				if(userOrgPerCusRelFriendlyId !=null && userOrgPerCusRelFriendlyId >0l && userOrgPerCusRelId!=null && userOrgPerCusRelId>0l && id!=null && id>0l){
					resultMap.put("message", "add friendId successed.");
					resultMap.put("status",1);
				}
			}
			
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//失败回滚
			if(bl)userFriendlyService.updateStatus(userId, friendId, new Byte("0"));
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
				userBasic=userBasicService.getObject(userLoginRegisterFriend.getId());
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
				//向万能插座发送消息
				defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.DELETE_FRIEND,"{\"friendId\":"+friendId+",\"userId\":"+userId+"}");
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
	 * 删除好友:临时使用此方法
	 * @param userId  当前用户ID
	 * @param friendId 要添加的好友的用户ID
	 * @param appId 应用的appId
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userext/user/deleteFriendly" }, method = { RequestMethod.POST })
	public MappingJacksonValue extDeleteFriendly(HttpServletRequest request,HttpServletResponse response
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
				userBasic=userBasicService.getObject(userLoginRegisterFriend.getId());
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
			,@RequestParam(name = "appid",required = false) String appid
			,@RequestParam(name = "appsecret",required = false) String appsecret
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
//				if(userLoginRegister.getUsetType().intValue()==0){
//					userBasic=userBasicService.getObject(userLoginRegister.getId());
//					if(userBasic==null){
//						resultMap.put( "message", Prompt.userId_is_not_exist_in_UserBasic);
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
//				}
//				if(userLoginRegister.getUsetType().intValue()==1){
//					userOrganBasic=userOrganBasicService.getUserOrganBasic(userLoginRegister.getId());
//					if(userOrganBasic==null){
//						resultMap.put( "message", Prompt.userId_is_not_exist_in_UserOrganBasic);
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
//				}
			}
			userLoginRegister.setUtime(System.currentTimeMillis());
			userLoginRegisterService.updataUserLoginRegister(userLoginRegister);
			JSONObject json=getAccessToken(request,passport,password,appid==null?client_id:appid,appsecret==null?client_secret:appsecret,GRANT_TYPE);
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
					userBasic=userBasicService.getObject(userLoginRegister.getId());
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
		,@RequestParam(name = "mobiletype",required = false,defaultValue ="1")int mobiletype
		,@RequestParam(name = "emailtype",required = false,defaultValue ="0")int emailtype
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
					if(mobiletype!=1 && mobiletype!=2){
						resultMap.put( "message", Prompt.mobile_type_is_not_correcct);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
					code=userLoginRegisterService.sendIdentifyingCode(passport,mobiletype);
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
					if(emailtype!=0 && emailtype!=1 && emailtype!=5){
						resultMap.put( "message", Prompt.register_email_type_is_not_correcct);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
						code=generationIdentifyingCode();
						Map<String, Object> map = new HashMap<String, Object>();
						if(emailtype==1)map.put("email", emailValidateUrlCoopert+"&email="+passport+"&code="+code);
						if(emailtype==5)map.put("email", emailValidateUrlgintong+"&email="+passport+"&code="+code);
				        map.put("acceptor",passport);
				        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
				        map.put("code", code);
				        map.put("type",String.valueOf(emailtype));
						if(userLoginRegisterService.sendEmail(passport, emailtype, map)){
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
	 *手机和邮箱找回密码及更改绑定手机和邮箱获取验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/getBackPasswordCode" }, method = { RequestMethod.GET})
	public MappingJacksonValue getBackPasswordCode(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "passport",required = true) String passport
		,@RequestParam(name = "mobiletype",required = false,defaultValue ="1")int mobiletype
		,@RequestParam(name = "emailtype",required = false,defaultValue ="2")int emailtype
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
					if(mobiletype!=1 && mobiletype!=2){
						resultMap.put( "message", Prompt.mobile_type_is_not_correcct);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
					code=userLoginRegisterService.sendIdentifyingCode(passport,mobiletype);
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
					if(emailtype!=2 && emailtype!=3 && emailtype!=4 && emailtype!=6 && emailtype!=7){
						resultMap.put( "message", Prompt.findpwd_email_type_is_not_correcct);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
						code=generationIdentifyingCode();
						Map<String, Object> map = new HashMap<String, Object>();
//				        if(emailtype==4)map.put("email", emailFindpwdUrlCoopert+"?email="+passport+"&code="+code);
				        if(emailtype==2){
				        	map.put("operatorname",Prompt.findpwd);
				        	map.put("email", emailValidateUrl+"?email="+passport+"&code="+code);
				        }
				        if(emailtype==3){
				        	map.put("operatorname",Prompt.bindEmail);
				        	map.put("email", emailBindUrlgintong+"?email="+passport+"&code="+code);
				        }
				        if(emailtype==6){
				        	map.put("operatorname",Prompt.findpwd);
				        	map.put("email", emailBindUrlgintong+"?email="+passport+"&code="+code);
				        }
				        if(emailtype==7){
				        	map.put("operatorname",Prompt.changeOldTitle);
				        	map.put("email", emailBindUrlgintong+"?email="+passport+"&code="+code);
				        }
				        map.put("acceptor",passport);
				        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
				        map.put("code", code);
				        map.put("type",String.valueOf(emailtype));
						if(userLoginRegisterService.sendEmail(passport, emailtype, map)){
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
	 * 验证绑定邮箱和手机验证码
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/validateBindCode" }, method = { RequestMethod.GET })
	public MappingJacksonValue validateBindCode(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "code",required = true) String code
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister=null;
		try {
			userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
			if(userLoginRegister==null){
				resultMap.put("message", Prompt.passport_is_not_exists);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			if(!code.equals(userLoginRegisterService.getIdentifyingCode(passport))){
				resultMap.put("message", Prompt.code_is_not_right);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			resultMap.put("message", Prompt.Operation_succeeded);
			resultMap.put("status",1);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info(e.getStackTrace());
			throw e;
		}
	}
	/**
	 * 修改绑定的邮箱或手机
	 * @param passport 为邮箱和手机号
	 * @param code 验证码
	 * @param source  来源的appkey
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/updatePassport" }, method = { RequestMethod.POST })
	public MappingJacksonValue updatePassport(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "code",required = true) String code
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		Long appId =0l;
		Long userId=0L;
		boolean bl=false;
		String code2=null;
			try {
				userId = LoginUserContextHolder.getUserId();
				if(userId==null){
					resultMap.put("message", Prompt.userId_is_null_or_empty);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				appId = LoginUserContextHolder.getAppKey();
				if(ObjectUtils.isEmpty(appId)){
					resultMap.put( "message", Prompt.appId_is_empty);
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
				if(userLoginRegister==null){
					resultMap.put("message", Prompt.passport_is_not_exists);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				code2=userLoginRegisterService.getIdentifyingCode(passport);
				if(StringUtils.isEmpty(code2)){
					resultMap.put("message", Prompt.identifying_code_has_experied_);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				if(code.equals(code2)){
					if(isMobileNo(passport)){
						userLoginRegister.setMobile(passport);
						if(isMobileNo(userLoginRegister.getPassport())){
							userLoginRegister.setPassport(passport);
						}
					}
					if(isEmail(passport)){
						userLoginRegister.setEmail(passport);
						if(isEmail(userLoginRegister.getPassport())){
							userLoginRegister.setPassport(passport);
						}
					}
					bl=userLoginRegisterService.updataUserLoginRegister(userLoginRegister);
					if(bl==false){
						resultMap.put("message", Prompt.update_passport_is_failed);
						resultMap.put("status",0);
						return new MappingJacksonValue(resultMap);	
					}
				}else{
					resultMap.put("message", Prompt.code_is_not_right);
					resultMap.put("status",0);
					return new MappingJacksonValue(resultMap);
				}
				resultMap.put("message", Prompt.update_passport_is_successed);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("修改邮箱或手机失败:"+userId);
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
//				List<UserExt> list=userExtService.getUserExtListByProvinceId(start, count, provinceId);//有问题
//				if(list==null || list.size()==0){
//					resultMap.put( "status", 0);
//					resultMap.put("message", Prompt.not_found_userId_list);
//					return new MappingJacksonValue(resultMap);
//				}
				List<Long> ids=new ArrayList<Long>();
//				for (UserExt userExt : list) {
//					if(userExt!=null)ids.add(userExt.getUserId());
//				}
				List<UserBasic> list2 = userBasicService.getObjects(ids);
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
//			List<UserExt> list=userExtService.getUserListByThirdIndustryId(start, count, thirdIndustryId);//有问题
//			if(list==null || list.size()==0){
//				resultMap.put( "status", 0);
//				resultMap.put("message", Prompt.not_found_userId_list);
//				return new MappingJacksonValue(resultMap);
//			}
//			List<Long> ids=new ArrayList<Long>();
//			for (UserExt userExt : list) {
//				if(userExt!=null)ids.add(userExt.getUserId());
//			}
//			List<UserBasic> list2 = userBasicService.getObjects(ids);//有问题
//			resultMap.put( "status", 1);
//			resultMap.put( "list", list2);
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info(e.getStackTrace());
			throw e;
		}
	}	

	/**
	 * 设置用户自动保存权限
	 * 
	 * @param autosave 自动保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/userSetAutosave" }, method = { RequestMethod.POST})
	public MappingJacksonValue userSetAutosave(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "autosave",required = true) String autosave
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
			if(StringUtils.isEmpty(autosave)){
				resultMap.put("message", "自动保存设置不能为空");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			UserConfig userConfig=userConfigerService.getUserConfig(userId);
			if(ObjectUtils.isEmpty(userConfig)){
				resultMap.put("message", "用户设置不存在");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userConfig.setHomePageVisible(new Byte(autosave));
			userConfigerService.updateUserConfig(userConfig);
			resultMap.put( "status", 1);
			resultMap.put("message", "设置成功!");
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
