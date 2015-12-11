package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
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
	@Autowired
	private UserInterestIndustryService userInterestIndustryService;
	/**
	 * 用户注册
	 * 
	 * @param type 1.邮箱注册,2.手机注册
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
	 */
	@RequestMapping(path = { "/userLoginRegister/register" }, method = { RequestMethod.GET })
	public MappingJacksonValue register(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "type",required = true) int type
			,@RequestParam(name = "passport",required = true) String passport
			,@RequestParam(name = "password",required = true) String password
			,@RequestParam(name = "userType",required = true) String userType
			,@RequestParam(name = "firstIndustryIds",value="firstIndustryIds[]", required = true) Long[] firstIndustryIds
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "source",required = true) String source
			,@RequestParam(name = "shortName",required = true) String shortName
			,@RequestParam(name = "businessLicencePicId",required = true) Long businessLicencePicId
			,@RequestParam(name = "stockCode",required = false) String stockCode
			,@RequestParam(name = "orgType",required = true) String orgType
			,@RequestParam(name = "companyContactsMobile",required = true) String companyContactsMobile
			,@RequestParam(name = "companyContacts",required = true) String companyContacts
			,@RequestParam(name = "idcardFrontPicId",required = true) Long idcardFrontPicId
			,@RequestParam(name = "idcardBackPicId",required = true) Long idcardBackPicId
			)throws Exception {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserOrganBasic userOrganBasic= null;
		UserBasic userBasic= null;
		UserInterestIndustry userInterestIndustry= null;
		List<UserInterestIndustry> list = null;
		String ip=getIpAddr(request);
		Long userBasicId=0l;
		Long userOrganBasicId=0l;
		Long id=0l;
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
					id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
					userBasic= new UserBasic();
					userBasic.setName(name);
					userBasic.setIp(ip);
					userBasicId=userBasicService.createUserBasic(userBasic);
					list =new ArrayList<UserInterestIndustry>();
					for (Long firstIndustryId : firstIndustryIds) {
						userInterestIndustry= new UserInterestIndustry();
						userInterestIndustry.setUserId(id);
						userInterestIndustry.setFirstIndustryId(firstIndustryId);
						userInterestIndustry.setIp(ip);
						list.add(userInterestIndustry);
						list=userInterestIndustryService.createUserInterestIndustryByList(list, id);
					}
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
					id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
					userOrganBasic= new UserOrganBasic();
					userOrganBasic.setUserId(id);
					userOrganBasic.setName(name);
					userOrganBasic.setShortName(shortName);
					userOrganBasic.setBusinessLicencePicId(businessLicencePicId);
					userOrganBasic.setIdcardFrontPicId(idcardFrontPicId);
					userOrganBasic.setIdcardBackPicId(idcardBackPicId);
					userOrganBasic.setCompanyContacts(companyContacts);
					userOrganBasic.setCompanyContactsMobile(companyContactsMobile);
					userOrganBasic.setIp(ip);
					userOrganBasicId=userOrganBasicService.createUserOrganBasic(userOrganBasic);
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
				return new MappingJacksonValue(reusltMap);
		}catch (Exception e ){
			//异常失败回滚
			if(id!=null && id>0L)userLoginRegisterService.realDeleteUserLoginRegister(id);
			if(userBasicId!=null && userBasicId>0l)userBasicService.realDeleteUserBasic(userBasicId);
			if(userOrganBasicId!=null && userOrganBasicId>0l)userBasicService.realDeleteUserBasic(userOrganBasicId);
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
