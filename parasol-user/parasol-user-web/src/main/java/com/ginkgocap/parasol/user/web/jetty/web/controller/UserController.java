package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ginkgocap.parasol.user.model.UserContactWay;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.model.UserExt;
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
import com.ginkgocap.parasol.user.service.UserContactWayService;
import com.ginkgocap.parasol.user.service.UserDefinedService;
import com.ginkgocap.parasol.user.service.UserEducationHistoryService;
import com.ginkgocap.parasol.user.service.UserExtService;
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
	@Autowired
	private UserContactWayService userContactWayService;
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
	@Value("${email.validate.url.coopert}")  
	private String emailValidateUrlCoopert; 	
	@Value("${email.findpwd.url.coopert}")  
	private String emailFindpwdUrlCoopert; 	
	@Value("${dfs.gintong.com}")  
	private String dfsGintongCom; 	
    private static final String GRANT_TYPE="password"; 
    private static final String CLASS_NAME = UserController.class.getName();

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
		User user=null;
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
					user.setUserExt(userExt);
					user.setListUserInterestIndustry(list);
					defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.USER_SAVE, GsonUtils.objectToString(user));
					resultMap.put( "id", id);
					resultMap.put( "status", 1);
					return new MappingJacksonValue(resultMap);
				}
				//个人用手机注册
				if(type==2 && userType.equals("0")){
					userBasic= new UserBasic();
					userBasic.setName(name);
					userBasic.setPassport(passport);
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
					user.setUserExt(userExt);
					user.setListUserInterestIndustry(list);
					defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.USER_SAVE, GsonUtils.objectToString(user));
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
		UserInfo userInfo= null;
		UserContactWay userContactWay= null;
		List<UserWorkHistory> listUserWorkHistory = null;
		List<UserEducationHistory> listUserEducationHistory = null;
		List<UserInterestIndustry> listUserInterestIndustry = null;
		List<TagSource> listTagSource=null;
		List<DirectorySource> listDirectorySource=null;
		Map<AssociateType, List<Associate>> map=null;
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
				resultMap.put( "message", "appId不能为空！");
				resultMap.put( "status", 0);
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
				if(!ObjectUtils.isEmpty(userOrganBasic)){
					if(!StringUtils.isEmpty(userOrganBasic.getPicPath())){
						userOrganBasic.setPicPath(dfsGintongCom+userOrganBasic.getPicPath());
					}
				}
				if(!ObjectUtils.isEmpty(userOrganExt)){
					if(!StringUtils.isEmpty(userOrganExt.getBusinessLicencePicPath())){
						userOrganExt.setBusinessLicencePicPath(dfsGintongCom+userOrganExt.getBusinessLicencePicPath());
					}
					if(!StringUtils.isEmpty(userOrganExt.getIdcardFrontPicPath())){
						userOrganExt.setIdcardFrontPicPath(dfsGintongCom+userOrganExt.getIdcardFrontPicPath());
					}
					if(!StringUtils.isEmpty(userOrganExt.getIdcardBackPicPath())){
						userOrganExt.setIdcardBackPicPath(dfsGintongCom+userOrganExt.getIdcardBackPicPath());
					}
				}
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
				if(!ObjectUtils.isEmpty(userBasic)){
					if(!StringUtils.isEmpty(userBasic.getPicPath())){
						userBasic.setPicPath(dfsGintongCom+userBasic.getPicPath());
					}
				}
				listUserInterestIndustry=userInterestIndustryService.getInterestIndustryListBy(userInterestIndustryService.getIdList(userId));
				userInfo=userInfoService.getUserInfo(userId);
				userContactWay=userContactWayService.getUserContactWay(userId);
				listUserWorkHistory=userWorkHistoryService.getIdList(userWorkHistoryService.getIdList(userId));
				listUserEducationHistory=userEducationHistoryService.getIdList(userEducationHistoryService.getIdList(userId));
				listTagSource=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, userId, 1l);
				listDirectorySource=directorySourceService.getDirectorySourcesBySourceId(userId, appId, 1, userId);
				map=associateService.getAssociatesBy(appId, 1l, userId);
				for ( AssociateType key  : map.keySet()) {
					resultMap.put(key.getName(), map.get(key));
				}
				resultMap.put("userBasic", userBasic);
				if(!ObjectUtils.isEmpty(userInfo))resultMap.put("userInfo", userInfo);
				if(!ObjectUtils.isEmpty(listUserInterestIndustry))resultMap.put("listUserInterestIndustry", listUserInterestIndustry);
				if(!ObjectUtils.isEmpty(userContactWay))resultMap.put("userContactWay", userContactWay);
				if(!ObjectUtils.isEmpty(listUserWorkHistory))resultMap.put("listUserWorkHistory", listUserWorkHistory);
				if(!ObjectUtils.isEmpty(listUserEducationHistory))resultMap.put("listUserEducationHistory", listUserEducationHistory);
				if(!ObjectUtils.isEmpty(listTagSource))resultMap.put("listTagSource", listTagSource);
				if(!ObjectUtils.isEmpty(listDirectorySource))resultMap.put("listDirectorySource", listDirectorySource);
				resultMap.put("userBasic", userBasic);
				resultMap.put("userExt", userExt);
				resultMap.put("userDefinedList", list);
				resultMap.put("status",1);
				mappingJacksonValue = new MappingJacksonValue(resultMap);
				SimpleFilterProvider filterProvider = builderSimpleFilterProvider("id,tagName");
				mappingJacksonValue.setFilters(filterProvider);
			}
			userLoginRegister.setPassword(null);
			userLoginRegister.setSalt(null);
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
	 * 个人情况
	 * @param birthday 出生日期
	 * @param provinceId 籍贯省ID
	 * @param cityId 籍贯市ID
	 * @param countyId 籍贯县ID
	 * @param interests 兴趣爱好
	 * @param skills   擅长技能
	 * 联系方式
	 * @param cellphone  手机
	 * @param email  邮箱
	 * @param weixin  微信
	 * @param qq  QQ
	 * @param weibo  微博
	 * 工作经历
	 * @param userWorkHistoryJson  json字符串
	 * 教育经历
	 * @param userEducationHistoryJson  json字符串 
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
			,@RequestParam(name = "userDefinedsJson", required = false) String userDefinedsJson
			,@RequestParam(name = "email",required = false) String email
			,@RequestParam(name = "phone",required = false) String phone
			,@RequestParam(name = "brief",required = false) String brief
			,@RequestParam(name = "companyName",required = false) String companyName
			,@RequestParam(name = "companyJob",required = false) String companyJob
			,@RequestParam(name = "mobile",required = false) String mobile
			,@RequestParam(name = "provinceId",required = false) Long provinceId
			,@RequestParam(name = "cityId",required = false) Long cityId
			,@RequestParam(name = "countyId",required = false) Long countyId
			//个人情况
			,@RequestParam(name = "birthday",required = false) String birthday
			,@RequestParam(name = "provinceId",required = false) Long provinceId2
			,@RequestParam(name = "cityId",required = false) Long cityId2
			,@RequestParam(name = "countyId",required = false) Long countyId2
			,@RequestParam(name = "interests",required = false) String interests
			,@RequestParam(name = "skills",required = false) String skills
			//联系方式
			,@RequestParam(name = "cellphone",required = false) String cellphone
			,@RequestParam(name = "email",required = false) String email2
			,@RequestParam(name = "weixin",required = false) String weixin
			,@RequestParam(name = "qq",required = false) String qq
			,@RequestParam(name = "weibo",required = false) String weibo
			,@RequestParam(name = "contactName",required = false) String contactName
			//工作经历
			,@RequestParam(name = "userWorkHistoryJson",required = false) String userWorkHistoryJson
			//教育经历
			,@RequestParam(name = "userEducationHistoryJson",required = false) String userEducationHistoryJson
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
		UserInfo userInfo= null;
		UserContactWay userContactWay= null;
		UserWorkHistory userWorkHistory= null;
		UserEducationHistory userEducationHistory= null;
		CodeRegion codeRegion=null;
		List<UserWorkHistory> listUserWorkHistory = null;
		List<UserEducationHistory> listUserEducationHistory = null;
		String ip=getIpAddr(request);
		Long userId=null;
		Long appId =0l;
		Long ctime=0l;
		Long utime=0l;
		Long id=0l;
		User user= null;
		boolean bl=false;
		boolean bl2=false;
		try {
			userId = LoginUserContextHolder.getUserId();
			appId =LoginUserContextHolder.getAppKey();
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
				if(!StringUtils.isEmpty(userDefinedsJson)){
					listUserDefined=new ArrayList<UserDefined>();
					JSONObject jsonObject = JSONObject.fromObject(userDefinedsJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userDefinedsJsonList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userDefined= new UserDefined();
						userDefined.setUserId(userId);
						userDefined.setIp(ip);
						userDefined.setUserDefinedModel(jsonObject2.has("model_name")?jsonObject2.getString("model_name"):null);
						userDefined.setUserDefinedFiled(jsonObject2.has("filed")?jsonObject2.getString("filed"):null);
						userDefined.setUserDefinedValue(jsonObject2.has("value")?jsonObject2.getString("value"):null);
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
				//联系方式
				userContactWay=userContactWayService.getUserContactWay(userId);
				if(ObjectUtils.isEmpty(userContactWay)){
					userContactWay=new UserContactWay();
					bl2=true;
				}
				userContactWay.setUserId(userId);
				userContactWay.setCellphone(cellphone);
				userContactWay.setName(contactName);
				userContactWay.setEmail(email);
				userContactWay.setWeibo(weibo);
				userContactWay.setCtime(ctime);
				userContactWay.setUtime(utime);
				userContactWay.setIp(ip);
				if(bl2){
					id=userContactWayService.createUserContactWay(userContactWay);
				}else{
					bl=userContactWayService.updateUserContactWay(userContactWay);
				}
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
				userBasic.setPassport(phone);
				userBasic.setName(name);
				userBasic.setSex(new Byte("1"));
				userBasicService.updateUserBasic(userBasic);
				if(!StringUtils.isEmpty(userDefinedsJson)){
					listUserDefined=new ArrayList<UserDefined>();
					JSONObject jsonObject = JSONObject.fromObject(userDefinedsJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userDefinedsJsonList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userDefined= new UserDefined();
						userDefined.setUserId(userId);
						userDefined.setIp(ip);
						userDefined.setUserDefinedModel(jsonObject2.has("model_name")?jsonObject2.getString("model_name"):null);
						userDefined.setUserDefinedFiled(jsonObject2.has("filed")?jsonObject2.getString("filed"):null);
						userDefined.setUserDefinedValue(jsonObject2.has("value")?jsonObject2.getString("value"):null);
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
				//个人信息
				userInfo =userInfoService.getUserInfo(userId);
				if(ObjectUtils.isEmpty(userInfo)){
					userInfo= new UserInfo();
					bl2=true;
				}
				userInfo.setBirthday(birthday);
				userInfo.setCountyId(countyId2);
				if(countyId2!=null)codeRegion=codeRegionService.getCodeRegionById(countyId2);
				if(codeRegion!=null)userInfo.setCountyName(codeRegion.getCname());
				userInfo.setCityId(cityId2);
				if(cityId2!=null)codeRegion=codeRegionService.getCodeRegionById(cityId2);
				if(codeRegion!=null)userInfo.setCityName(codeRegion.getCname());
				userInfo.setCtime(ctime);
				userInfo.setIp(ip);
				userInfo.setUserId(userId);
				userInfo.setProvinceId(provinceId);
				if(provinceId!=null)codeRegion=codeRegionService.getCodeRegionById(provinceId);
				if(codeRegion!=null)userInfo.setProvinceName(codeRegion.getCname());
				if(bl2){
					id=userInfoService.createUserInfo(userInfo);
					bl2=false;
				}else{
					bl=userInfoService.updateUserInfo(userInfo);
				}
//					if(bl==false){
//						userBasicService.realDeleteUserBasic(userId);
//						resultMap.put( "message", "保存用户个人信息出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				//联系方式
				userContactWay=userContactWayService.getUserContactWay(userId);
				if(ObjectUtils.isEmpty(userContactWay)){
					userContactWay=new UserContactWay();
					bl2=true;
				}
				userContactWay.setUserId(userId);
				userContactWay.setCellphone(cellphone);
				userContactWay.setEmail(email);
				userContactWay.setWeixin(weixin);
				userContactWay.setQq(qq);
				userContactWay.setWeibo(weibo);
				userContactWay.setCtime(ctime);
				userContactWay.setUtime(utime);
				userContactWay.setIp(ip);
				if(bl2){
					id=userContactWayService.createUserContactWay(userContactWay);
				}else{
					bl=userContactWayService.updateUserContactWay(userContactWay);
				}
//					if(bl==false){
//						userBasicService.realDeleteUserBasic(userId);
//						userInfoService.realDeleteUserInfo(userId);
//						userContactWayService.realDeleteUserContactWay(userId);
//						resultMap.put( "message", "保存用户联系方式出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				//保存工作经历
				if(!StringUtils.isEmpty(userWorkHistoryJson)){
					listUserWorkHistory =new ArrayList<UserWorkHistory>();
					JSONObject jsonObject = JSONObject.fromObject(userWorkHistoryJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userWorkHistoryList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userWorkHistory=new UserWorkHistory();
						userWorkHistory.setUserId(userId);
						userWorkHistory.setIncName(jsonObject2.has("inc_name")?jsonObject2.getString("inc_name"):null);
						userWorkHistory.setPosition(jsonObject2.has("position")?jsonObject2.getString("position"):null);
						userWorkHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
						userWorkHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
						userWorkHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
						userWorkHistory.setCtime(ctime);
						userWorkHistory.setUtime(utime);
						userWorkHistory.setIp(ip);
						listUserWorkHistory.add(userWorkHistory);
					}
					if(listUserWorkHistory.size()>0)
						listUserWorkHistory=userWorkHistoryService.createUserWorkHistoryByList(listUserWorkHistory, userId);
//					if(listUserWorkHistory==null|| listUserWorkHistory.size()<=0){
//						userBasicService.realDeleteUserBasic(userId);
//						userInfoService.realDeleteUserInfo(userId);
//						userContactWayService.realDeleteUserContactWay(userId);
//						userWorkHistoryService.realDeleteUserWorkHistoryList(userWorkHistoryService.getIdList(userId));
//						resultMap.put( "message", "保存用户工作经历出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				}
				//保存教育经历
				if(!StringUtils.isEmpty(userEducationHistoryJson)){
					listUserEducationHistory =new ArrayList<UserEducationHistory>();
					JSONObject jsonObject = JSONObject.fromObject(userEducationHistoryJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userEducationHistoryList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userEducationHistory=new UserEducationHistory();
						userEducationHistory.setUserId(userId);
						userEducationHistory.setSchool(jsonObject2.has("school")?jsonObject2.getString("school"):null);
						userEducationHistory.setMajor(jsonObject2.has("major")?jsonObject2.getString("major"):null);
						userEducationHistory.setDegree(jsonObject2.has("degree")?jsonObject2.getString("degree"):null);
						userEducationHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
						userEducationHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
						userEducationHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
						userEducationHistory.setCtime(ctime);
						userEducationHistory.setUtime(utime);
						userEducationHistory.setIp(ip);
						listUserEducationHistory.add(userEducationHistory);
					}
					if(listUserEducationHistory.size()>0)
						listUserEducationHistory=userEducationHistoryService.createUserEducationHistoryByList(listUserEducationHistory, userId);
//					if(listUserEducationHistory==null|| listUserEducationHistory.size()<=0){
//						userBasicService.realDeleteUserBasic(userId);
//						userInfoService.realDeleteUserInfo(userId);
//						userContactWayService.realDeleteUserContactWay(userId);
//						userWorkHistoryService.realDeleteUserWorkHistoryList(userWorkHistoryService.getIdList(userId));
//						userEducationHistoryService.realDeleteUserEducationHistoryList(userEducationHistoryService.getIdList(userId));
//						resultMap.put( "message", "保存用户教育经历出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				}
				//向万能插座发送消息
				user = new User();
				user.setUserLoginRegister(userLoginRegister);
				userBasic.setPicPath(dfsGintongCom+userBasic.getPicPath());
				user.setUserBasic(userBasic);
				user.setUserExt(userExt);
				user.setUserContactWay(userContactWay);
				user.setUserInfo(userInfo);
				user.setListUserInterestIndustry(list);
				user.setListUserDefined(listUserDefined);
				user.setListUserWorkHistory(listUserWorkHistory);
				user.setListUserEducationHistory(listUserEducationHistory);
				RocketSendResult rocketSendResult=defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.USER_UPDATE, GsonUtils.objectToString(user));
				rocketSendResult.getSendResult().getSendStatusCode();
				rocketSendResult.getSendResult().getMsgId();
				resultMap.put( "message", Prompt.updateUser_success);
				resultMap.put( "userId", userId);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
			}			
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("修改失败:"+userId);
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
	 * 个人情况
	 * @param birthday 出生日期
	 * @param provinceId 籍贯省ID
	 * @param cityId 籍贯市ID
	 * @param countyId 籍贯县ID
	 * @param interests 兴趣爱好
	 * @param skills   擅长技能
	 * 联系方式
	 * @param cellphone  手机
	 * @param email  邮箱
	 * @param weixin  微信
	 * @param qq  QQ
	 * @param weibo  微博
	 * 工作经历
	 * @param userWorkHistoryJson  json字符串
	 * 教育经历
	 * @param userEducationHistoryJson  json字符串 
	 * @throws Exception
	 * @return MappingJacksonValue
	 */
	@RequestMapping(path = { "/userext/user/updateUser" }, method = { RequestMethod.POST})
	public MappingJacksonValue extUpdateUser(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = false) String passport
			,@RequestParam(name = "type",required = false) int type
			,@RequestParam(name = "userType",required = false) String userType
			,@RequestParam(name = "mobile",required = false) String mobile
			,@RequestParam(name = "email",required = false) String email
			,@RequestParam(name = "password",required = false) String password
			,@RequestParam(name = "salt",required = false) String salt
			,@RequestParam(name = "userId",required = false) Long userId
			,@RequestParam(name = "appId",required = false) Long appId
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "sex",required = false) String sex
			,@RequestParam(name = "picId",required = true) Long picId
			,@RequestParam(name = "shortName",required = false) String shortName
			,@RequestParam(name = "firstIndustryIds", required = false) Long[] firstIndustryIds
			,@RequestParam(name = "userDefinedsJson", required = false) String userDefinedsJson
			,@RequestParam(name = "phone",required = false) String phone
			,@RequestParam(name = "brief",required = false) String brief
			,@RequestParam(name = "companyName",required = false) String companyName
			,@RequestParam(name = "companyJob",required = false) String companyJob
			,@RequestParam(name = "provinceId",required = false) Long provinceId
			,@RequestParam(name = "cityId",required = false) Long cityId
			,@RequestParam(name = "countyId",required = false) Long countyId
			//个人情况
			,@RequestParam(name = "birthday",required = false) String birthday
			,@RequestParam(name = "provinceId",required = false) Long provinceId2
			,@RequestParam(name = "cityId",required = false) Long cityId2
			,@RequestParam(name = "countyId",required = false) Long countyId2
			,@RequestParam(name = "interests",required = false) String interests
			,@RequestParam(name = "skills",required = false) String skills
			//联系方式
			,@RequestParam(name = "cellphone",required = false) String cellphone
			,@RequestParam(name = "email",required = false) String email2
			,@RequestParam(name = "weixin",required = false) String weixin
			,@RequestParam(name = "qq",required = false) String qq
			,@RequestParam(name = "weibo",required = false) String weibo
			,@RequestParam(name = "contactName",required = false) String contactName
			//工作经历
			,@RequestParam(name = "userWorkHistoryJson",required = false) String userWorkHistoryJson
			//教育经历
			,@RequestParam(name = "userEducationHistoryJson",required = false) String userEducationHistoryJson
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
		UserInfo userInfo= null;
		UserContactWay userContactWay= null;
		UserWorkHistory userWorkHistory= null;
		UserEducationHistory userEducationHistory= null;
		List<UserWorkHistory> listUserWorkHistory = null;
		List<UserEducationHistory> listUserEducationHistory = null;
		String ip=getIpAddr(request);
		Long ctime=0l;
		Long utime=0l;
		Long id=0l;
		User user= null;
		boolean bl=false;
		boolean bl2=false;
		Long userBasicId=0l;
		Long userExtId=0l;
		Long userOrganBasicId=0l;
		Long userOrganExtId=0l;
		try {
			if(userId==null){
				//新增个人
				if(userType.equals("0")){
					userLoginRegister= new UserLoginRegister();
					userLoginRegister.setPassport(mobile);
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
					//自定义
					if(!StringUtils.isEmpty(userDefinedsJson)){
						listUserDefined=new ArrayList<UserDefined>();
						JSONObject jsonObject = JSONObject.fromObject(userDefinedsJson);
						JSONArray jsonArray=jsonObject.getJSONArray("userDefinedsJsonList");
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
							userDefined= new UserDefined();
							userDefined.setUserId(userId);
							userDefined.setIp(ip);
							userDefined.setUserDefinedModel(jsonObject2.has("model_name")?jsonObject2.getString("model_name"):null);
							userDefined.setUserDefinedFiled(jsonObject2.has("filed")?jsonObject2.getString("filed"):null);
							userDefined.setUserDefinedValue(jsonObject2.has("value")?jsonObject2.getString("value"):null);
							listUserDefined.add(userDefined);
							listUserDefined=userDefinedService.createUserDefinedByList(listUserDefined, userId);
						}
					}
					//个人信息
					userInfo= new UserInfo();
					userInfo.setBirthday(birthday);
					userInfo.setCountyId(countyId2);
					userInfo.setCityId(cityId2);
					userInfo.setCtime(ctime);
					userInfo.setIp(ip);
					userInfo.setUserId(userId);
					userInfo.setProvinceId(provinceId);
					id=userInfoService.createUserInfo(userInfo);
					//联系方式
					userContactWay=new UserContactWay();
					userContactWay.setUserId(userId);
					userContactWay.setCellphone(cellphone);
					userContactWay.setEmail(email);
					userContactWay.setWeixin(weixin);
					userContactWay.setQq(qq);
					userContactWay.setWeibo(weibo);
					userContactWay.setCtime(ctime);
					userContactWay.setUtime(utime);
					userContactWay.setIp(ip);
					id=userContactWayService.createUserContactWay(userContactWay);
					//保存工作经历
					if(!StringUtils.isEmpty(userWorkHistoryJson)){
						listUserWorkHistory =new ArrayList<UserWorkHistory>();
						JSONObject jsonObject = JSONObject.fromObject(userWorkHistoryJson);
						JSONArray jsonArray=jsonObject.getJSONArray("userWorkHistoryList");
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
							userWorkHistory=new UserWorkHistory();
							userWorkHistory.setUserId(userId);
							userWorkHistory.setIncName(jsonObject2.has("inc_name")?jsonObject2.getString("inc_name"):null);
							userWorkHistory.setPosition(jsonObject2.has("position")?jsonObject2.getString("position"):null);
							userWorkHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
							userWorkHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
							userWorkHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
							userWorkHistory.setCtime(ctime);
							userWorkHistory.setUtime(utime);
							userWorkHistory.setIp(ip);
							listUserWorkHistory.add(userWorkHistory);
						}
						if(listUserWorkHistory.size()>0)
							listUserWorkHistory=userWorkHistoryService.createUserWorkHistoryByList(listUserWorkHistory, userId);
					}
					//保存教育经历
					if(!StringUtils.isEmpty(userEducationHistoryJson)){
						listUserEducationHistory =new ArrayList<UserEducationHistory>();
						JSONObject jsonObject = JSONObject.fromObject(userEducationHistoryJson);
						JSONArray jsonArray=jsonObject.getJSONArray("userEducationHistoryList");
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
							userEducationHistory=new UserEducationHistory();
							userEducationHistory.setUserId(userId);
							userEducationHistory.setSchool(jsonObject2.has("school")?jsonObject2.getString("school"):null);
							userEducationHistory.setMajor(jsonObject2.has("major")?jsonObject2.getString("major"):null);
							userEducationHistory.setDegree(jsonObject2.has("degree")?jsonObject2.getString("degree"):null);
							userEducationHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
							userEducationHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
							userEducationHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
							userEducationHistory.setCtime(ctime);
							userEducationHistory.setUtime(utime);
							userEducationHistory.setIp(ip);
							listUserEducationHistory.add(userEducationHistory);
						}
						if(listUserEducationHistory.size()>0)
							listUserEducationHistory=userEducationHistoryService.createUserEducationHistoryByList(listUserEducationHistory, userId);
					}
					//用户设置
					UserConfig userConfig =new UserConfig();
					userConfig.setUserId(id);
					userConfig.setHomePageVisible(new Byte("2"));
					userConfig.setEvaluateVisible(new Byte("2"));
					userConfig.setAutosave(new Byte("0"));
					userConfigerService.createUserConfig(userConfig);
					userLoginRegisterService.deleteIdentifyingCode(passport);
					
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
					userBasic.setPicPath(dfsGintongCom+userBasic.getPicPath());
					user.setUserBasic(userBasic);
					user.setUserExt(userExt);
					user.setUserContactWay(userContactWay);
					user.setUserInfo(userInfo);
					user.setListUserInterestIndustry(list);
					user.setListUserDefined(listUserDefined);
					user.setListUserWorkHistory(listUserWorkHistory);
					user.setListUserEducationHistory(listUserEducationHistory);
					RocketSendResult rocketSendResult=defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.USER_UPDATE, GsonUtils.objectToString(user));
					rocketSendResult.getSendResult().getSendStatusCode();
					rocketSendResult.getSendResult().getMsgId();
					resultMap.put( "message", Prompt.updateUser_success);
					resultMap.put( "userId", userId);
					resultMap.put("status",1);
					return new MappingJacksonValue(resultMap);
				}				
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
				if(!StringUtils.isEmpty(userDefinedsJson)){
					listUserDefined=new ArrayList<UserDefined>();
					JSONObject jsonObject = JSONObject.fromObject(userDefinedsJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userDefinedsJsonList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userDefined= new UserDefined();
						userDefined.setUserId(userId);
						userDefined.setIp(ip);
						userDefined.setUserDefinedModel(jsonObject2.has("model_name")?jsonObject2.getString("model_name"):null);
						userDefined.setUserDefinedFiled(jsonObject2.has("filed")?jsonObject2.getString("filed"):null);
						userDefined.setUserDefinedValue(jsonObject2.has("value")?jsonObject2.getString("value"):null);
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
				//联系方式
				userContactWay=userContactWayService.getUserContactWay(userId);
				if(ObjectUtils.isEmpty(userContactWay)){
					userContactWay=new UserContactWay();
					bl2=true;
				}
				userContactWay.setUserId(userId);
				userContactWay.setCellphone(cellphone);
				userContactWay.setName(contactName);
				userContactWay.setEmail(email);
				userContactWay.setWeibo(weibo);
				userContactWay.setCtime(ctime);
				userContactWay.setUtime(utime);
				userContactWay.setIp(ip);
				if(bl2){
					id=userContactWayService.createUserContactWay(userContactWay);
				}else{
					bl=userContactWayService.updateUserContactWay(userContactWay);
				}
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
				userBasic.setPassport(phone);
				userBasic.setName(name);
				userBasic.setSex(new Byte("1"));
				userBasicService.updateUserBasic(userBasic);
				//自定义
				if(!StringUtils.isEmpty(userDefinedsJson)){
					listUserDefined=new ArrayList<UserDefined>();
					JSONObject jsonObject = JSONObject.fromObject(userDefinedsJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userDefinedsJsonList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userDefined= new UserDefined();
						userDefined.setUserId(userId);
						userDefined.setIp(ip);
						userDefined.setUserDefinedModel(jsonObject2.has("model_name")?jsonObject2.getString("model_name"):null);
						userDefined.setUserDefinedFiled(jsonObject2.has("filed")?jsonObject2.getString("filed"):null);
						userDefined.setUserDefinedValue(jsonObject2.has("value")?jsonObject2.getString("value"):null);
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
				//个人信息
				userInfo =userInfoService.getUserInfo(userId);
				if(ObjectUtils.isEmpty(userInfo)){
					userInfo= new UserInfo();
					bl2=true;
				}
				userInfo.setBirthday(birthday);
				userInfo.setCountyId(countyId2);
				userInfo.setCityId(cityId2);
				userInfo.setCtime(ctime);
				userInfo.setIp(ip);
				userInfo.setUserId(userId);
				userInfo.setProvinceId(provinceId);
				if(bl2){
					id=userInfoService.createUserInfo(userInfo);
					bl2=false;
				}else{
					bl=userInfoService.updateUserInfo(userInfo);
				}
//					if(bl==false){
//						userBasicService.realDeleteUserBasic(userId);
//						resultMap.put( "message", "保存用户个人信息出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				//联系方式
				userContactWay=userContactWayService.getUserContactWay(userId);
				if(ObjectUtils.isEmpty(userContactWay)){
					userContactWay=new UserContactWay();
					bl2=true;
				}
				userContactWay.setUserId(userId);
				userContactWay.setCellphone(cellphone);
				userContactWay.setEmail(email);
				userContactWay.setWeixin(weixin);
				userContactWay.setQq(qq);
				userContactWay.setWeibo(weibo);
				userContactWay.setCtime(ctime);
				userContactWay.setUtime(utime);
				userContactWay.setIp(ip);
				if(bl2){
					id=userContactWayService.createUserContactWay(userContactWay);
				}else{
					bl=userContactWayService.updateUserContactWay(userContactWay);
				}
//					if(bl==false){
//						userBasicService.realDeleteUserBasic(userId);
//						userInfoService.realDeleteUserInfo(userId);
//						userContactWayService.realDeleteUserContactWay(userId);
//						resultMap.put( "message", "保存用户联系方式出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				//保存工作经历
				if(!StringUtils.isEmpty(userWorkHistoryJson)){
					listUserWorkHistory =new ArrayList<UserWorkHistory>();
					JSONObject jsonObject = JSONObject.fromObject(userWorkHistoryJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userWorkHistoryList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userWorkHistory=new UserWorkHistory();
						userWorkHistory.setUserId(userId);
						userWorkHistory.setIncName(jsonObject2.has("inc_name")?jsonObject2.getString("inc_name"):null);
						userWorkHistory.setPosition(jsonObject2.has("position")?jsonObject2.getString("position"):null);
						userWorkHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
						userWorkHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
						userWorkHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
						userWorkHistory.setCtime(ctime);
						userWorkHistory.setUtime(utime);
						userWorkHistory.setIp(ip);
						listUserWorkHistory.add(userWorkHistory);
					}
					if(listUserWorkHistory.size()>0)
						listUserWorkHistory=userWorkHistoryService.createUserWorkHistoryByList(listUserWorkHistory, userId);
//					if(listUserWorkHistory==null|| listUserWorkHistory.size()<=0){
//						userBasicService.realDeleteUserBasic(userId);
//						userInfoService.realDeleteUserInfo(userId);
//						userContactWayService.realDeleteUserContactWay(userId);
//						userWorkHistoryService.realDeleteUserWorkHistoryList(userWorkHistoryService.getIdList(userId));
//						resultMap.put( "message", "保存用户工作经历出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				}
				//保存教育经历
				if(!StringUtils.isEmpty(userEducationHistoryJson)){
					listUserEducationHistory =new ArrayList<UserEducationHistory>();
					JSONObject jsonObject = JSONObject.fromObject(userEducationHistoryJson);
					JSONArray jsonArray=jsonObject.getJSONArray("userEducationHistoryList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						userEducationHistory=new UserEducationHistory();
						userEducationHistory.setUserId(userId);
						userEducationHistory.setSchool(jsonObject2.has("school")?jsonObject2.getString("school"):null);
						userEducationHistory.setMajor(jsonObject2.has("major")?jsonObject2.getString("major"):null);
						userEducationHistory.setDegree(jsonObject2.has("degree")?jsonObject2.getString("degree"):null);
						userEducationHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
						userEducationHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
						userEducationHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
						userEducationHistory.setCtime(ctime);
						userEducationHistory.setUtime(utime);
						userEducationHistory.setIp(ip);
						listUserEducationHistory.add(userEducationHistory);
					}
					if(listUserEducationHistory.size()>0)
						listUserEducationHistory=userEducationHistoryService.createUserEducationHistoryByList(listUserEducationHistory, userId);
//					if(listUserEducationHistory==null|| listUserEducationHistory.size()<=0){
//						userBasicService.realDeleteUserBasic(userId);
//						userInfoService.realDeleteUserInfo(userId);
//						userContactWayService.realDeleteUserContactWay(userId);
//						userWorkHistoryService.realDeleteUserWorkHistoryList(userWorkHistoryService.getIdList(userId));
//						userEducationHistoryService.realDeleteUserEducationHistoryList(userEducationHistoryService.getIdList(userId));
//						resultMap.put( "message", "保存用户教育经历出错！");
//						resultMap.put( "status", 0);
//						return new MappingJacksonValue(resultMap);
//					}
				}
				//向万能插座发送消息
				user = new User();
				user.setUserLoginRegister(userLoginRegister);
				userBasic.setPicPath(dfsGintongCom+userBasic.getPicPath());
				user.setUserBasic(userBasic);
				user.setUserExt(userExt);
				user.setUserContactWay(userContactWay);
				user.setUserInfo(userInfo);
				user.setListUserInterestIndustry(list);
				user.setListUserDefined(listUserDefined);
				user.setListUserWorkHistory(listUserWorkHistory);
				user.setListUserEducationHistory(listUserEducationHistory);
				RocketSendResult rocketSendResult=defaultMessageService.sendMessage(TopicType.OPEN_USER_TOPIC, FlagType.USER_UPDATE, GsonUtils.objectToString(user));
				rocketSendResult.getSendResult().getSendStatusCode();
				rocketSendResult.getSendResult().getMsgId();
				resultMap.put( "message", Prompt.updateUser_success);
				resultMap.put( "userId", userId);
				resultMap.put("status",1);
				return new MappingJacksonValue(resultMap);
			}			
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("修改失败:"+userId);
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
//					userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
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
					if(emailtype!=0 && emailtype!=1){
						resultMap.put( "message", Prompt.register_email_type_is_not_correcct);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
						code=generationIdentifyingCode();
						Map<String, Object> map = new HashMap<String, Object>();
						if(emailtype==1)map.put("email", emailValidateUrlCoopert+"?email="+passport+"&code="+code);
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
	 * 手机和邮箱找回密码获取验证码
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
					if(emailtype!=2 && emailtype!=4){
						resultMap.put( "message", Prompt.findpwd_email_type_is_not_correcct);
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
						code=generationIdentifyingCode();
						Map<String, Object> map = new HashMap<String, Object>();
				        if(emailtype==4)map.put("email", emailFindpwdUrlCoopert+"?email="+passport+"&code="+code);
				        if(emailtype==2)map.put("email", emailValidateUrl+"?email="+passport+"&code="+code);
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
	 * 设置用户浏览我的主页权限
	 * @param homePageVisible 浏览我的主页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/userSetHomePageVisible" }, method = { RequestMethod.POST})
	public MappingJacksonValue userSetHomePageVisible(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "homePageVisible",required = true) String homePageVisible
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
			if(StringUtils.isEmpty(homePageVisible)){
				resultMap.put("message", "浏览我的主页设置不能为空");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			UserConfig userConfig=userConfigerService.getUserConfig(userId);
			if(ObjectUtils.isEmpty(userConfig)){
				resultMap.put("message", "用户设置不存在");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userConfig.setHomePageVisible(new Byte(homePageVisible));
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
	 * 设置用户对我评价权限
	 * 
	 * @param evaluateVisible 对我评价
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/user/userSetEvaluateVisible" }, method = { RequestMethod.POST})
	public MappingJacksonValue userSetEvaluateVisible(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "evaluateVisible",required = true) String evaluateVisible
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
			if(StringUtils.isEmpty(evaluateVisible)){
				resultMap.put("message", "对我评价设置不能为空");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			UserConfig userConfig=userConfigerService.getUserConfig(userId);
			if(ObjectUtils.isEmpty(userConfig)){
				resultMap.put("message", "用户设置不存在");
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
			}
			userConfig.setHomePageVisible(new Byte(evaluateVisible));
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
