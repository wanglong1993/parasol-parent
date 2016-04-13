package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
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

import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserExt;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserExtService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
import com.ginkgocap.parasol.user.service.UserOrganExtService;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Base64;

/**
 * 第三方注册和登录
 */
@RestController
public class UserThirdController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserThirdController.class);
	@Autowired
	private UserLoginThirdService userLoginThirdService;
	@Autowired
	private UserLoginRegisterService userLoginRegisterService;
	@Autowired
	private UserBasicService userBasicService;
	@Autowired
	private FileIndexService fileIndexService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private UserOrganExtService userOrganExtService;
	@Value("${upload.web.url}")  
    private String uploadWebUrl;  
	@Value("${file.web.url}")  
    private String fileWebUrl;  
	/**
	 * 获取第三方登录url地址
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/userThird/getLoginThirdUrl" }, method = { RequestMethod.GET })
	public MappingJacksonValue getLoginThirdUrl(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "type",required = true) int type
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
				String url= userLoginThirdService.getLoginThirdUrl(type);
				if(!StringUtils.isEmpty(url)){
					resultMap.put("url", url);
					resultMap.put("status",1);
				}
				logger.info("getLoginThirdUrl:"+url);
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
	@RequestMapping(path = { "/user/userThird/getIdentifyingCode" }, method = { RequestMethod.GET})
	public MappingJacksonValue getIdentifyingCode(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "passport",required = true) String passport
		,@RequestParam(name = "type",required = false,defaultValue ="0")int type
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
				if(StringUtils.isEmpty(passport)){
					resultMap.put( "message", "passport is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!isMobileNo(passport)){
					resultMap.put( "message", "passport is not right phone number.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(userLoginRegisterService.passportIsExist(passport)){
					resultMap.put( "message", "mobile already exists.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				String code=userLoginRegisterService.sendIdentifyingCode(passport,type);
				if(StringUtils.isEmpty(code)){
					resultMap.put( "message", "failed to get the verfication code.");
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
	 * 第三方注册登录
	 * 
	 * @param loginType 第三方类型:100为QQ,200为新浪微博
	 * @param openId 开放平台的用户ID
	 * @param name QQ或微博上的昵称
	 * @param headPic QQ或微博头像地址
	 * @param sex 性别
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/userThird/registerOrLogin" }, method = { RequestMethod.POST})
	public MappingJacksonValue registerOrLogin(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "loginType",required = true) String loginType
		,@RequestParam(name = "openId",required = true) String openId
		,@RequestParam(name = "name",required = true) String name
		,@RequestParam(name = "headPic",required = true) String headPic
		,@RequestParam(name = "sex",required = true) String sex
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= new UserLoginRegister();
		UserBasic userBasic= new UserBasic();
		UserExt userExt= null;
		String ip=getIpAddr(request);
		Long userId=0l;
		Long id=0l;
		Long userBasicId=0l;
		Long userExtId=0l;
		String suffix=null;
		String passport=null;
		JSONObject json=null;
		try { 
				if(StringUtils.isEmpty(openId)){
					resultMap.put( "message", "openId is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(loginType)){
					resultMap.put( "message", "loginType is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(loginType.equals("100") && loginType.equals("200") ){
					resultMap.put( "message", "loginType must be 100 or 200.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(name) ){
					resultMap.put( "message", "name is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(StringUtils.isEmpty(headPic) ){
					resultMap.put( "message", "headPic is null or empty.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(loginType.equals("100"))suffix="@qq";
				if(loginType.equals("200"))suffix="@sina";
				passport=openId+suffix;
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(passport);
				//不存在就用openId新注册帐号
				if(ObjectUtils.isEmpty(userLoginRegister)){
					//设置userLoginRegister开始
					userLoginRegister=new UserLoginRegister();
					userLoginRegister.setUsetType(new Byte("0"));
					userLoginRegister.setIp(ip);
					userLoginRegister.setSource(loginType.equals("100")?"qq":"sina");
					userLoginRegister.setPassport(openId+suffix);
//					userLoginRegister.setMobile(passport);
					byte[] bt = Base64.decode("123456");
					String salt=userLoginRegisterService.setSalt();
					String password=userLoginRegisterService.setSha256Hash(salt, new String(bt));
					userLoginRegister.setSalt(salt);
					userLoginRegister.setPassword(password);
					
					//设置userBasic开始
					userBasic.setName(name);
					userBasic.setPassport(passport);
					userBasic.setSex(new Byte(sex));
					userBasic.setStatus(new Byte("1"));
					userBasic.setAuth(new Byte("1"));
					//设置userExt开始
					userExt=new UserExt();
					userExt.setName(name);
					userExt.setIp(ip);
					//保存userLoginRegister开始
					id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
					//保存userBasic开始
					json=upload(request,uploadWebUrl,"3",id.toString(), headPic);
					if(json==null){
						//异常失败回滚
						if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
						resultMap.put( "message", "upload headPic failed.");
						resultMap.put("status",0);
					}
	                if(json.has("serverHost") && json.has("filePath") && !StringUtils.isEmpty(json.getString("serverHost")) && !StringUtils.isEmpty(json.getString("filePath"))){
	                	headPic=fileWebUrl+json.getString("serverHost")+"/"+json.getString("thumbnailsPath");
	                }
					if(StringUtils.isEmpty(headPic)){
						//异常失败回滚
						if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
						resultMap.put( "message", "upload headPic failed.");
						resultMap.put("status",0);
					}
					userBasic.setPicId(json.getLong("id"));
					userBasic.setUserId(id);
					userId=userBasicService.createUserBasic(userBasic);
					//保存userExt开始
					userExt.setUserId(id);
					userExtId=userExtService.createUserExt(userExt);
					
					resultMap.put("userId", id);
					resultMap.put("openId", openId);
					resultMap.put("headPic", headPic);
					resultMap.put("name", name);
					resultMap.put("sex", sex);
					resultMap.put("status",1);
					return new MappingJacksonValue(resultMap);
				}else{//openId已经存在帐号直接返回登录了
					userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
					if(userBasic!=null){
						FileIndex fileIndex=fileIndexService.getFileIndexById(userBasic.getPicId());
						if(fileIndex!=null)
						headPic=fileWebUrl+fileIndex.getServerHost()+"/"+fileIndex.getThumbnailsPath();
					}
					resultMap.put("userId", userLoginRegister.getId());
					resultMap.put("openId", openId);
					resultMap.put("headPic", headPic);
					resultMap.put("name", name);
					resultMap.put("sex", sex);
					resultMap.put("status",1);
					return new MappingJacksonValue(resultMap);
				}
		}catch (Exception e ){
			//异常失败回滚
			if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
			if(userId!=null && userId>0l)userBasicService.realDeleteUserBasic(userId);
			if(userExtId!=null && userExtId>0L)userExtService.realDeleteUserExt(id);
			if(userBasicId!=null && userBasicId>0l)userBasicService.realDeleteUserBasic(userBasicId);
			logger.info("第三方注册登录失败:"+passport);
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
	@RequestMapping(path = { "/user/userThird/bindExistsPassport" }, method = { RequestMethod.POST})
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
					resultMap.put( "message", "password is error.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//判断用户的状态是否正常
				userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
				int status=userBasic.getStatus().intValue();
				if(status==0 || status==-1 || status==2  ){
					resultMap.put( "message", "user have been logic deleted or locked or canceled");
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
				//创建userLoginThird开始
				id=userLoginThirdService.saveUserLoginThird(userLoginThird);
				resultMap.put("userId", userLoginRegister.getId());
				resultMap.put("openId", userLoginThird.getOpenId());
				resultMap.put("headPic", userLoginThird.getHeadPic());
				resultMap.put("name", userBasic.getName());
				resultMap.put("sex", userBasic.getSex());
				resultMap.put("status",1);
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
	 * @param passport 用户通行证
	 * @param openId 用户的openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/userThird/unbindExistPassport" }, method = { RequestMethod.POST})
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
					resultMap.put( "message", "openId never bind in userLoginThird.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				if(!userLoginThird.getUserId().equals(userLoginRegister.getId())){
					resultMap.put( "message", "the userId in the userLoginRegister is different from the userId in the userLoginThird.");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//删除解绑
				userLoginThirdService.realDeleteUserLoginThird(userLoginThird.getId());
				resultMap.put( "message", "unbind successed.");
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
	@RequestMapping(path = { "/user/userThird/login" }, method = { RequestMethod.POST })
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
				userBasicService.updateUserBasic(userBasic);
			}
			userLoginRegisterService.updateIpAndLoginTime(userLoginRegister.getId(), ip);
			resultMap.put("userId", userLoginRegister.getId());
			resultMap.put("openId", userLoginThird.getOpenId());
			resultMap.put("headPic", userLoginThird.getHeadPic());
			resultMap.put("name", userBasic.getName());
			resultMap.put("sex", userBasic.getSex());
			resultMap.put("status",1);
			logger.info("/user/userThird/login:success");
			return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			logger.info("/user/userThird/login:error");
			throw e;
		}
	}
	/**
	 * 第三方注册登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/userThird/registerLogin" }, method = { RequestMethod.GET})
	public MappingJacksonValue registerLogin(HttpServletRequest request,HttpServletResponse response
		,@RequestParam(name = "loginType",required = true) String loginType
		,@RequestParam(name = "accessToken",required = true) String accessToken
		,@RequestParam(name = "openId",required = true) String openId
		,@RequestParam(name = "name",required = true) String name
		,@RequestParam(name = "headPic",required = true) String headPic
		,@RequestParam(name = "source",required = true) String source
		,@RequestParam(name = "sex",required = true) String sex
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserBasic userBasic= null;
		UserExt userExt= null;
		String ip=getIpAddr(request);
		Long id=0l;
		Long userBasicId=0l;
		Long userExtId=0l;
		String suffix=null;
		String password=null;
		JSONObject json=null;
		if(loginType.equals("100"))suffix="@qq";
		if(loginType.equals("200"))suffix="@sina";
		try {   
				userLoginRegister=userLoginRegisterService.getUserLoginRegister(openId+suffix);
				if(userLoginRegister==null){
					//设置userLoginRegister开始
					userLoginRegister=new UserLoginRegister();
					userLoginRegister.setUsetType(new Byte("0"));
					userLoginRegister.setIp(ip);
					userLoginRegister.setSource(source);
					userLoginRegister.setPassport(openId+suffix);
					String salt=userLoginRegisterService.setSalt();
					password=userLoginRegisterService.setSha256Hash(salt, new String("111111"));
					userLoginRegister.setSalt(salt);
					userLoginRegister.setPassword(password);
					//保存userLoginRegister开始
					id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
					//设置userBasic开始
					userBasic=new UserBasic();
					userBasic.setName(name);
					userBasic.setSex(new Byte(sex));
					userBasic.setStatus(new Byte("1"));
					json=upload(request,uploadWebUrl,"11",id.toString(), headPic);
					if(json==null){
						//异常失败回滚
						if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
						resultMap.put( "message", "upload headPic failed.");
						resultMap.put("status",0);
					}
					if(json!=null){
		                if(json.has("serverHost") && json.has("filePath") && !StringUtils.isEmpty(json.getString("serverHost")) && !StringUtils.isEmpty(json.getString("filePath"))){
		                	headPic=fileWebUrl+json.getString("serverHost")+json.getString("filePath");
		                }
	                }
					if(StringUtils.isEmpty(headPic)) {
						//异常失败回滚
						if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
						resultMap.put( "message", "upload headPic failed.");
						resultMap.put("status",0);
					}
					userBasic.setUserId(id);
					userBasic.setPicId(json.getLong("id"));
					userBasicId=userBasicService.createUserBasic(userBasic);
					//设置userExt开始
					userExt=new UserExt();
					userExt.setUserId(id);
					userExt.setName(name);
					userExt.setIp(ip);
					userExtId=userExtService.createUserExt(userExt);
					resultMap.put("userLoginRegister",userLoginRegister);
					resultMap.put("userBasic",userBasic);
					resultMap.put("userExt",userExt);
					resultMap.put("userBasic",userBasic);
					resultMap.put("status",1);
					return new MappingJacksonValue(resultMap);
				}else{
					userBasic=userBasicService.getUserBasic(userLoginRegister.getId());
					userLoginRegisterService.updateIpAndLoginTime(userLoginRegister.getId(), ip);
					resultMap.put("userLoginRegister",userLoginRegister);
					resultMap.put("status",1);
					logger.info("/user/userThird/registerLogin:success");
					return new MappingJacksonValue(resultMap);
				}
		}catch (Exception e ){
			//异常失败回滚
			if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
			if(userExtId!=null && userExtId>0L)userExtService.realDeleteUserExt(id);
			if(userBasicId!=null && userBasicId>0l)userBasicService.realDeleteUserBasic(userBasicId);
			logger.info("第三方注册失败:"+openId);
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 上传头像
	 * 
	 * @param posturl 上传头像服务器地址
	 * @param source  来源的appkey
	 * @param userId  用户ID
	 * @param headPic 图片的http地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = { "/user/userThird/upload" }, method = { RequestMethod.POST})
	public JSONObject upload(HttpServletRequest request,
			@RequestParam(name = "posturl",required = true) String posturl
			,@RequestParam(name = "source",required = true) String source
			,@RequestParam(name = "userId",required = true) String userId
			,@RequestParam(name = "headPic",required = true) String headPic
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
//	        	RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
//	        		.setProxy(new HttpHost("192.168.130.100", 8091))
//	        	    .build();
	        	URL url = new URL(headPic);
	        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    		conn.setRequestMethod("GET");
	    		conn.setReadTimeout(6 * 10000);
	        	InputStreamBody file = new InputStreamBody(conn.getInputStream(),"1qw3e.jpg");
	            HttpPost httpPost = new HttpPost(uploadWebUrl);
	            HttpEntity reqEntity = MultipartEntityBuilder.create()  
	            .addPart("file", file)
	            .addPart("appKey",  new StringBody(source, ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("userId", new StringBody(userId, ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("fileType", new StringBody("1", ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("moduleType", new StringBody("1", ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("taskId", new StringBody(String.valueOf(System.currentTimeMillis()), ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("fileExtName", new StringBody("jpg", ContentType.create("text/plain", Consts.UTF_8)))
	            .addPart("name", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)))
	            .build();  
	            httpPost.setEntity(reqEntity);  
//	            httpPost.setConfig(requestConfig);
	            CloseableHttpResponse response = httpClient.execute(httpPost);  
	            try {  
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						resultMap.put("status", response.getStatusLine().getStatusCode());
						entity = response.getEntity();
						String respJson = EntityUtils.toString(entity);
						json = JSONObject.fromObject(respJson);
						logger.info("json:"+respJson);
					}
	                EntityUtils.consume(entity);
	                return json;
	            } finally {  
	                response.close();  
	            }  
	        }finally{  
	            httpClient.close();  
	        }
    	}catch(Exception  e){
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
