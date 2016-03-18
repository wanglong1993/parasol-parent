package com.ginkgocap.parasol.person.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonContactWay;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonContactWayService;
import com.ginkgocap.parasol.person.service.PersonDefinedService;
import com.ginkgocap.parasol.person.service.PersonEducationHistoryService;
import com.ginkgocap.parasol.person.service.PersonInfoService;
import com.ginkgocap.parasol.person.service.PersonWorkHistoryService;
import com.ginkgocap.parasol.person.web.jetty.web.utils.Prompt;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

/**
 * 人脉登录注册
 */
@RestController
public class PersonController extends BaseControl {
	private static Logger logger = Logger.getLogger(PersonController.class);
	@Autowired
	private PersonBasicService personBasicService;
	@Autowired
	private PersonContactWayService personContactWayService;
	@Autowired
	private PersonDefinedService personDefinedService;
	@Autowired
	private PersonEducationHistoryService personEducationHistoryService;
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private PersonWorkHistoryService personWorkHistoryService;
	@Autowired
	private TagSourceService tagSourceService;
	@Autowired
	private AssociateService associateService;
	@Autowired
	private DirectorySourceService directorySourceService;

	/**
	 * 创建人脉
	 * 基本信息
	 * @param name 姓名
	 * @param gender 性别
	 * @param picId 人脉头像ID
	 * @param firstIndustryId 一级职能ID
	 * @param secondIndustryId 二级职能ID
	 * @param company 公司
	 * @param position 职位
	 * @param countryId 国外的洲或国内的省id
	 * @param cityId  国外的国家或国内的城市id
	 * @param countyId 县id
	 * @param remark 个人描述
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
	 * @param personWorkHistoryJson  json字符串
	 * 教育经历
	 * @param personEducationHistoryJson  json字符串
	 * 标签TagSource
	 * @param tagId 标签ID
	 * 目录DirectorySource
	 * @param directoryId 目录ID
	 * 关联Associate
	 * @param associateJson json字符串
	 * @throws Exception
	 * @return MappingJacksonValue
	 * http://www.jsjtt.com/java/Javakuangjia/67.html
	 */
	@RequestMapping(path = { "/person/person/createPerson" }, method = { RequestMethod.POST })
	public MappingJacksonValue createPerson(HttpServletRequest request,HttpServletResponse response
			//基本信息
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "gender",required = true) Byte gender
			,@RequestParam(name = "picId",required = false) Long picId
			,@RequestParam(name = "firstIndustryId",required = false) Long firstIndustryId
			,@RequestParam(name = "secondIndustryId",required = false) Long secondIndustryId
			,@RequestParam(name = "company",required = false) String company
			,@RequestParam(name = "position",required = false) String position
			,@RequestParam(name = "countryId",required = false) Long countryId
			,@RequestParam(name = "cityId",required = false) Long cityId
			,@RequestParam(name = "countyId",required = false) Long countyId
			,@RequestParam(name = "remark",required = false) String remark
			,@RequestParam(name = "address",required = false) String address
			//个人情况
			,@RequestParam(name = "birthday",required = false) Date birthday
			,@RequestParam(name = "provinceId",required = false) Long provinceId
			,@RequestParam(name = "cityId",required = false) Long cityId2
			,@RequestParam(name = "countyId",required = false) Long countyId2
			,@RequestParam(name = "interests",required = false) String interests
			,@RequestParam(name = "skills",required = false) String skills
			//联系方式
			,@RequestParam(name = "cellphone",required = false) String cellphone
			,@RequestParam(name = "email",required = false) String email
			,@RequestParam(name = "weixin",required = false) String weixin
			,@RequestParam(name = "qq",required = false) String qq
			,@RequestParam(name = "weibo",required = false) String weibo
			//工作经历
			,@RequestParam(name = "personWorkHistorys",required = false) String personWorkHistoryJson
			//教育经历
			,@RequestParam(name = "personEducationHistorys",required = false) String personEducationHistoryJson
			//标签TagSource
			,@RequestParam(name = "tagIds",required = false) Long[] tagIds
			//目录DirectorySource
			 ,@RequestParam(name = "directoryId",required = false) Long[] directoryIds
			 //关联Associate
			 ,@RequestParam(name = "associates",required = false) String associateJson
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PersonBasic personBasic= null;
		PersonInfo personInfo= null;
		PersonContactWay personContactWay= null;
		PersonWorkHistory personWorkHistory= null;
		PersonEducationHistory personEducationHistory= null;
		TagSource tagSource= null;
		DirectorySource directorySource= null;
		Associate associate=null;
		List<PersonWorkHistory> listPersonWorkHistory = null;
		List<PersonEducationHistory> listPersonEducationHistory = null;
		String ip=getIpAddr(request);
		Long id=0l;
		Long personId=0l;
		Long appId =0l;
		Long ctime=0l;
		Long utime=0l;
		try {
				if((StringUtils.isEmpty(name))){
					resultMap.put( "message", "人脉姓名不能为空！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				ctime=System.currentTimeMillis();
				utime=System.currentTimeMillis();
				appId = LoginUserContextHolder.getAppKey();
				Long userId = LoginUserContextHolder.getUserId();
				personBasic=new PersonBasic();
				personBasic.setUserId(userId);
				personBasic.setPersonType(new Byte("1"));
				personBasic.setName(name);
				personBasic.setGender(gender);
				personBasic.setCompany(company);
				personBasic.setPosition(position);
				personBasic.setPicId(picId);
				personBasic.setCountryId(countryId);
				personBasic.setCityId(cityId);
				personBasic.setCountyId(countyId);
				personBasic.setAddress(address);
				personBasic.setRemark(remark);
				personBasic.setFirstIndustryId(firstIndustryId);
				personBasic.setSecondIndustryId(secondIndustryId);
				personBasic.setCtime(ctime);
				personBasic.setUtime(utime);
				personBasic.setIp(ip);
				id=personBasicService.createPersonBasic(personBasic);
				if(id<=0l || id==null){
					resultMap.put( "message", "保存人脉基本信息出错！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				personInfo = new PersonInfo();
				personInfo.setBirthday(birthday.getTime());
				personInfo.setCountyId(countyId2);
				personInfo.setCityId(cityId2);
				personInfo.setCtime(ctime);
				personInfo.setIp(ip);
				personInfo.setPersonId(id);
				personInfo.setProvinceId(provinceId);
				personId=personInfoService.createPersonInfo(personInfo);
				if(!personId.equals(id)){
					personBasicService.realDeletePersonBasic(id);
					resultMap.put( "message", "保存人脉个人信息出错！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				personContactWay=new PersonContactWay();
				personContactWay.setPersonId(id);
				personContactWay.setCellphone(cellphone);
				personContactWay.setEmail(email);
				personContactWay.setWeixin(weixin);
				personContactWay.setQq(qq);
				personContactWay.setWeibo(weibo);
				personContactWay.setCtime(ctime);
				personContactWay.setUtime(utime);
				personContactWay.setIp(ip);
				personId=personContactWayService.createPersonContactWay(personContactWay);
				if(!personId.equals(id)){
					personBasicService.realDeletePersonBasic(id);
					personInfoService.realDeletePersonInfo(id);
					personContactWayService.realDeletePersonContactWay(id);
					resultMap.put( "message", "保存人脉联系方式出错！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				//保存工作经历
				if(!StringUtils.isEmpty(personWorkHistoryJson)){
					listPersonWorkHistory =new ArrayList<PersonWorkHistory>();
					JSONObject jsonObject = JSONObject.fromObject(personWorkHistoryJson);
					JSONArray jsonArray=jsonObject.getJSONArray("personWorkHistoryList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						personWorkHistory=new PersonWorkHistory();
						personWorkHistory.setPersonId(id);
						personWorkHistory.setIncName(jsonObject2.has("inc_name")?jsonObject2.getString("incname"):null);
						personWorkHistory.setPosition(jsonObject2.has("position")?jsonObject2.getString("position"):null);
						personWorkHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
						personWorkHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
						personWorkHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
						personWorkHistory.setCtime(ctime);
						personWorkHistory.setUtime(utime);
						personWorkHistory.setIp(ip);
						listPersonWorkHistory.add(personWorkHistory);
					}
					if(listPersonWorkHistory.size()>0)
					listPersonWorkHistory=personWorkHistoryService.createPersonWorkHistoryByList(listPersonWorkHistory, id);
					if(listPersonWorkHistory==null|| listPersonWorkHistory.size()<=0){
						personBasicService.realDeletePersonBasic(id);
						personInfoService.realDeletePersonInfo(id);
						personContactWayService.realDeletePersonContactWay(id);
						personWorkHistoryService.realDeletePersonWorkHistoryList(personWorkHistoryService.getIdList(id));
						resultMap.put( "message", "保存人脉工作经历出错！");
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				//保存教育经历
				if(!StringUtils.isEmpty(personEducationHistoryJson)){
					listPersonEducationHistory =new ArrayList<PersonEducationHistory>();
					JSONObject jsonObject = JSONObject.fromObject(personEducationHistoryJson);
					JSONArray jsonArray=jsonObject.getJSONArray("personEducationHistoryList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						personEducationHistory=new PersonEducationHistory();
						personEducationHistory.setPersonId(id);
						personEducationHistory.setSchool(jsonObject2.has("school")?jsonObject2.getString("school"):null);
						personEducationHistory.setMajor(jsonObject2.has("major")?jsonObject2.getString("major"):null);
						personEducationHistory.setDegree(jsonObject2.has("degree")?jsonObject2.getString("degree"):null);
						personEducationHistory.setBeginTime(jsonObject2.has("begin_time")?jsonObject2.getString("begin_time"):null);
						personEducationHistory.setEndTime(jsonObject2.has("end_time")?jsonObject2.getString("end_time"):null);
						personEducationHistory.setDescription(jsonObject2.has("description")?jsonObject2.getString("description"):null);
						personEducationHistory.setCtime(ctime);
						personEducationHistory.setUtime(utime);
						personEducationHistory.setIp(ip);
						listPersonEducationHistory.add(personEducationHistory);
					}
					if(listPersonEducationHistory.size()>0)
						listPersonEducationHistory=personEducationHistoryService.createPersonEducationHistoryByList(listPersonEducationHistory, id);
					if(listPersonEducationHistory==null|| listPersonEducationHistory.size()<=0){
						personBasicService.realDeletePersonBasic(id);
						personInfoService.realDeletePersonInfo(id);
						personContactWayService.realDeletePersonContactWay(id);
						personWorkHistoryService.realDeletePersonWorkHistoryList(personWorkHistoryService.getIdList(id));
						personEducationHistoryService.realDeletePersonEducationHistoryList(personEducationHistoryService.getIdList(id));
						resultMap.put( "message", "保存人脉教育经历出错！");
						resultMap.put( "status", 0);
						return new MappingJacksonValue(resultMap);
					}
				}
				//保存标签
				if(!ObjectUtils.isEmpty(tagIds)){
					boolean bl=false;
					Long tagId=0L;
					List<Long> m_tagIds=new ArrayList<Long>();
					List<TagSource> listTagSource=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, id, 1l);
					if(listTagSource!=null && listTagSource.size()>0){
						for (int i = 0; i < tagIds.length; i++){
							tagId=tagIds[i];
							for (TagSource tagSource2 : listTagSource) {
								if(tagSource2.getTagId()==tagId){
									bl=true;
								}
							}
							if(bl==false)m_tagIds.add(tagId);
							else bl=false;
						}
					}
					if(m_tagIds.size()>0){
						for(Long tagId_2 : m_tagIds){
							tagSource = new TagSource();
							tagSource.setTagId(tagId_2);
							tagSource.setAppId(appId);
							tagSource.setUserId(userId);
							tagSource.setSourceId(id);
							tagSource.setSourceType(1);
							tagSource.setCreateAt(ctime);
							tagSourceService.createTagSource(tagSource);
						}
					}else{
						for (int i = 0; i < tagIds.length; i++) {
							tagSource = new TagSource();
							tagSource.setTagId(tagIds[i]);
							tagSource.setAppId(appId);
							tagSource.setUserId(userId);
							tagSource.setSourceId(id);
							tagSource.setSourceType(1);//1为人脉
							tagSource.setCreateAt(ctime);
							tagSourceService.createTagSource(tagSource);
						}
					}
				}
				//保存目录
				if(!ObjectUtils.isEmpty(directoryIds)){
					boolean bl=false;
					Long directoryId=0L;
					List<Long> m_directoryIds=new ArrayList<Long>();
					List<DirectorySource> listDirectorySource=directorySourceService.getDirectorySourcesBySourceId(userId, appId, 2, id);
					if(listDirectorySource!=null && listDirectorySource.size()>0){
						for (int i = 0; i < directoryIds.length; i++){
							directoryId=directoryIds[i];
							for (DirectorySource directorySource2 : listDirectorySource) {
								if(directorySource2.getDirectoryId()==directoryId){
									bl=true;
								}
							}
							if(bl==false)m_directoryIds.add(directoryId);
							else bl=false;
						}
					}
					if(m_directoryIds.size()>0){
						for(Long directoryId_2 : m_directoryIds){
							directorySource = new DirectorySource();
							directorySource.setDirectoryId(directoryId_2);
							directorySource.setAppId(appId);
							directorySource.setUserId(userId);
							directorySource.setSourceId(id);
							directorySource.setSourceType(2);
							directorySource.setCreateAt(ctime);
							directorySourceService.createDirectorySources(directorySource);
						}
					}else{
						for (int i = 0; i < directoryIds.length; i++) {
							directorySource = new DirectorySource();
							directorySource.setDirectoryId(directoryIds[i]);
							directorySource.setAppId(appId);
							directorySource.setUserId(userId);
							directorySource.setSourceId(id);
							directorySource.setSourceType(2);
							directorySource.setCreateAt(ctime);
							directorySourceService.createDirectorySources(directorySource);
						}
					}
				}
				//保存关联
				if(!StringUtils.isEmpty(associateJson)){
					JSONObject jsonObject = JSONObject.fromObject(associateJson);
					JSONArray jsonArray=jsonObject.getJSONArray("associateList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						associate=new Associate();
						associate.setUserId(userId);
						associate.setAppId(appId);
						associate.setSourceTypeId(1);
						associate.setSourceId(id);
						associate.setAssocDesc(jsonObject2.has("assoc_desc")?jsonObject2.getString("assoc_desc"):null);
						associate.setAssocTypeId(jsonObject2.has("assoc_type_id")?jsonObject2.getLong("assoc_type_id"):null);
						associate.setAssocId(jsonObject2.has("associd")?jsonObject2.getLong("associd"):null);
						associate.setAssocTitle(jsonObject2.has("assoc_title")?jsonObject2.getString("assoc_title"):null);
						associate.setCreateAt(ctime);
						associateService.createAssociate(appId, userId, associate);
					}
				}
				resultMap.put("message", Prompt.paramter_type_or_userType_error);
				resultMap.put("status",0);
				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//异常失败回滚
			personBasicService.realDeletePersonBasic(id);
			personInfoService.realDeletePersonInfo(id);
			personContactWayService.realDeletePersonContactWay(id);
			personWorkHistoryService.realDeletePersonWorkHistoryList(personWorkHistoryService.getIdList(id));
			personEducationHistoryService.realDeletePersonEducationHistoryList(personEducationHistoryService.getIdList(id));
			resultMap.put( "message", "保存人脉教育经历出错！");
			resultMap.put( "status", 0);
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
