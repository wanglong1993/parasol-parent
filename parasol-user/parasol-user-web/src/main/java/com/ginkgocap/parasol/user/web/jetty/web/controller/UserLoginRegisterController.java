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
import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.model.UserOrganExt;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserDefinedService;
import com.ginkgocap.parasol.user.service.UserExtService;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;
import com.ginkgocap.parasol.user.service.UserOrganExtService;
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
	private UserExtService userExtService;
	@Autowired
	private UserOrganExtService userOrganExtService;
	@Autowired
	private UserOrganBasicService userOrganBasicService;
	@Autowired
	private UserInterestIndustryService userInterestIndustryService;
	@Autowired
	private UserDefinedService userDefinedService;

	/**
	 * 用户注册
	 * 
	 * @param type 1.邮箱注册,2.手机注册
	 * @param code 手机验证码
	 * @param passport 为邮箱和手机号
	 * @param password 用户密码
	 * @param userType 1.个人用户,2.组织用户
	 * @param firstIndustryIds 一级行业ID数组
	 * @param name 昵称,企业全称
	 * @param shortName 企业简称
	 * @param businessLicencePicId 企业执照图片ID
	 * @param stockCode 股票代码
	 * @param orgType 组织类型
	 * @param companyContactsMobile 组织联系人电话
	 * @param companyContacts   组织联系人
	 * @param idcardFrontPicId   组织联系人身份证正面照片id
	 * @param idcardBackPicId  联系人身份证背面照片id
	 * @param source  来源的appkey
	 * @throws Exception
	 * @return MappingJacksonValue
	 * http://www.jsjtt.com/java/Javakuangjia/67.html
	 */
	@RequestMapping(path = { "/userLoginRegister/register" }, method = { RequestMethod.GET })
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "type",required = true) int type
			,@RequestParam(name = "code",required = true) String code
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "userType",required = true) String userType
			,@RequestParam(name = "firstIndustryIds", required = true) Long[] firstIndustryIds
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "source",required = true) String source
			,@RequestParam(name = "shortName",required = true) String shortName
			,@RequestParam(name = "picId",required = true) Long picId
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
				if((type==1 && userType=="1") ||(type==1 && userType=="2")){
					if(!isEmail(passport)){
						resultMap.put( "error", "email format is not correct.");
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				//个人用户手机注册
				if(type==2 && userType=="1"){
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
				if(type==1)userLoginRegister.setEmail(passport);
				if(type==2)userLoginRegister.setMobile((passport));
				id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
				//个人用户邮箱注册
				if((type==1 && userType=="1")){
			        Map<String, Object> map = new HashMap<String, Object>();
			        map.put("email", "http://www.gintong.com/userLoginRegister/verification?eamil="+Base64.decode(passport.getBytes()));
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
				if(type==2 && userType=="1"){
					userBasic= new UserBasic();
					userBasic.setName(name);
					userBasic.setPicId(picId);
					userBasic.setMobile(passport);
					userBasic.setPicId(picId);
					userBasic.setStatus(new Byte("1"));
					userBasic.setSex(new Byte("1"));
					userBasicId=userBasicService.createUserBasic(userBasic);
					userExt=new UserExt();
					userExt.setName(name);
					userExt.setShortName(name);
					userExt.setIp(ip);
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
					resultMap.put( "userLoginRegister", userLoginRegister);
					resultMap.put( "status", 1);
					return new MappingJacksonValue(resultMap);
				}
				//组织用户邮箱注册
				if(type==1 && userType=="2"){
					userOrganBasic= new UserOrganBasic();
					userOrganBasic.setPicId(picId);
					userOrganBasic.setUserId(id);
					userOrganBasic.setName(name);
					userOrganBasic.setAuth(new Byte("0"));
					userOrganBasic.setStatus(new Byte("1"));
					userOrganBasicId=userOrganBasicService.createUserOrganBasic(userOrganBasic);
					userOrganExt= new UserOrganExt();
					userOrganExt.setShortName(shortName);
					userOrganExt.setStockCode(stockCode);
					userOrganExt.setOrgType(orgType);
					userOrganExt.setBusinessLicencePicId(businessLicencePicId);
					userOrganExt.setIdcardFrontPicId(idcardFrontPicId);
					userOrganExt.setIdcardBackPicId(idcardBackPicId);
					userOrganExt.setCompanyContacts(companyContacts);
					userOrganExt.setCompanyContactsMobile(companyContactsMobile);
					userOrganExt.setIp(ip);
					userOrganExtId=userOrganExtService.createUserOrganExt(userOrganExt);
					//邮箱验证地址
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("email", "http://www.gintong.com/userLoginRegister/verification?eamil="+Base64.decode(passport.getBytes()));
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
	 * 获取好友
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginRegister/getFriendly" }, method = { RequestMethod.GET })
	public MappingJacksonValue getFriendly(HttpServletRequest request,HttpServletResponse response
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
	 * 修改资料
	 * 
	 * @param passport 为邮箱和手机号
	 * @param name 昵称,企业全称
	 * @param sex 性别
	 * @param picId 个人或组织LOGOID
	 * @param password 用户密码
	 * @param shortName 组织简称
	 * @param firstIndustryIds 一级行业ID数组
	 * @param userDefineds 用户自定义数组 userDefineds=1,自定义字段12,3&userDefineds=4,自定义字段子,好的&userDefineds=7,8,9
	 * @param email 邮箱
	 * @param phone 企业电话
	 * @param brief 企业简介
	 * @param companyName 公司名称
	 * @param companyJob 职业
	 * @param mobile 个人手机号
	 * @param stockCode 股票代码
	 * @param orgType 组织类型
	 * @param provinceId 省ID
	 * @param cityId   市ID
	 * @param countyId   县ID
	 * @throws Exception
	 * @return MappingJacksonValue
	 */
	@RequestMapping(path = { "/userLoginRegister/updateUser" }, method = { RequestMethod.GET })
	public MappingJacksonValue updateUser(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "sex",required = true) String sex
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
	@RequestMapping(path = { "/userLoginRegister/login" }, method = { RequestMethod.GET })
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
			String salt=userLoginRegisterService.setSalt();
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
	@RequestMapping(path = { "/userLoginRegister/validatPassport" }, method = { RequestMethod.GET })
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
	 * 修改密码
	 * 
	 * @param passport 为邮箱和手机号
	 * @param oldpassword 用户密码
	 * @param newpassword 用户密码
	 * @param source  来源的appkey
	 * @throws Exception
	 */
	@RequestMapping(path = { "/userLoginRegister/updatepassword" }, method = { RequestMethod.GET })
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
	@RequestMapping(path = { "/userLoginRegister/getIdentifyingCode" }, method = { RequestMethod.GET})
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
}
