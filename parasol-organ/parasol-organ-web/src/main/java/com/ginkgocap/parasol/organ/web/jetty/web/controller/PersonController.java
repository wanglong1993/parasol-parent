package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonContactWay;
import com.ginkgocap.parasol.person.model.PersonDefined;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonContactWayService;
import com.ginkgocap.parasol.person.service.PersonDefinedService;
import com.ginkgocap.parasol.person.service.PersonEducationHistoryService;
import com.ginkgocap.parasol.person.service.PersonInfoService;
import com.ginkgocap.parasol.person.service.PersonWorkHistoryService;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;
import com.ginkgocap.parasol.user.service.UserOrgPerCusRelService;

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
	@Autowired
	private UserOrgPerCusRelService userOrgPerCusRelService;

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
	 * @param tagIds 标签ID tagIds=3933811599736848&tagIds=3933811561988102&tagIds=3933811356467203
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
			,@RequestParam(name = "personDefinedsJson", required = false) String personDefinedsJson
			,@RequestParam(name = "company",required = false) String company
			,@RequestParam(name = "position",required = false) String position
			,@RequestParam(name = "countryId",required = false) Long countryId
			,@RequestParam(name = "cityId",required = false) Long cityId
			,@RequestParam(name = "countyId",required = false) Long countyId
			,@RequestParam(name = "remark",required = false) String remark
			,@RequestParam(name = "address",required = false) String address
			//个人情况
			,@RequestParam(name = "birthday",required = false) String birthday
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
			,@RequestParam(name = "personWorkHistoryJson",required = false) String personWorkHistoryJson
			//教育经历
			,@RequestParam(name = "personEducationHistoryJson",required = false) String personEducationHistoryJson
			//标签TagSource
			,@RequestParam(name = "tagIds",required = false) Long[] tagIds
			//目录DirectorySource
			 ,@RequestParam(name = "directoryId",required = false) Long[] directoryIds
			 //关联Associate
			 ,@RequestParam(name = "associateJson",required = false) String associateJson
			)throws Exception {

        Long loginUserId = LoginUserContextHolder.getUserId();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PersonBasic personBasic= null;
		PersonInfo personInfo= null;
		PersonContactWay personContactWay= null;
		PersonWorkHistory personWorkHistory= null;
		PersonEducationHistory personEducationHistory= null;
		TagSource tagSource= null;
		DirectorySource directorySource= null;
		Associate associate=null;
		UserOrgPerCusRel userOrgPerCusRel=null;
		List<PersonWorkHistory> listPersonWorkHistory = null;
		List<PersonEducationHistory> listPersonEducationHistory = null;
		List<PersonDefined> listPersonDefined = null;
		PersonDefined personDefined=null;
		String ip=getIpAddr(request);
		Long id=0l;
		Long personId=0l;
		Long appId =0l;
		Long ctime=0l;
		Long utime=0l;
		Long userId =0l;
		try {
				if((StringUtils.isEmpty(name))){
					resultMap.put( "message", "人脉姓名不能为空！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				appId = LoginUserContextHolder.getAppKey();
				if(ObjectUtils.isEmpty(appId)){
					resultMap.put( "message", "appId不能为空！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				userId = LoginUserContextHolder.getUserId();
				if(ObjectUtils.isEmpty(userId)){
					resultMap.put( "message", "userId不能为空！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
				ctime=System.currentTimeMillis();
				utime=System.currentTimeMillis();
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
				personInfo.setBirthday(birthday);
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
				//保存自定义
				if(!StringUtils.isEmpty(personDefinedsJson)){
					listPersonDefined=new ArrayList<PersonDefined>();
					JSONObject jsonObject = JSONObject.fromObject(personDefinedsJson);
					JSONArray jsonArray=jsonObject.getJSONArray("personDefinedsJsonList");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
						personDefined= new PersonDefined();
						personDefined.setPersonId(id);
						personDefined.setIp(ip);
						personDefined.setPersonDefinedModel(jsonObject2.has("model_name")?jsonObject2.getString("model_name"):null);
						personDefined.setPersonDefinedFiled(jsonObject2.has("filed")?jsonObject2.getString("filed"):null);
						personDefined.setPersonDefinedValue(jsonObject2.has("value")?jsonObject2.getString("value"):null);
						listPersonDefined.add(personDefined);
						listPersonDefined=personDefinedService.createPersonDefinedByList(listPersonDefined, id);
					}
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
						personWorkHistory.setIncName(jsonObject2.has("inc_name")?jsonObject2.getString("inc_name"):null);
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
				//保存目录
				if(!ObjectUtils.isEmpty(directoryIds)){
					for (int i = 0; i < directoryIds.length; i++) {
						directorySource = new DirectorySource();
						directorySource.setDirectoryId(directoryIds[i]);
						directorySource.setAppId(appId);
						directorySource.setUserId(userId);
						directorySource.setSourceId(id);
						directorySource.setSourceType(1);//1为人脉
						directorySource.setCreateAt(ctime);
						directorySourceService.createDirectorySources(directorySource);
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
						associate.setSourceTypeId(1);//1为人脉
						associate.setSourceId(id);
						associate.setAssocDesc(jsonObject2.has("assoc_desc")?jsonObject2.getString("assoc_desc"):null);
						associate.setAssocTypeId(jsonObject2.has("assoc_type_id")?jsonObject2.getLong("assoc_type_id"):null);
						associate.setAssocId(jsonObject2.has("associd")?jsonObject2.getLong("associd"):null);
						associate.setAssocTitle(jsonObject2.has("assoc_title")?jsonObject2.getString("assoc_title"):null);
						associate.setCreateAt(ctime);
						associateService.createAssociate(appId, userId, associate);
					}
				}
				userOrgPerCusRel=new UserOrgPerCusRel();
				userOrgPerCusRel.setUserId(userId);
				userOrgPerCusRel.setReleationType(new Byte("6"));
				userOrgPerCusRel.setFriendId(id);
				userOrgPerCusRel.setCtime(ctime);
				userOrgPerCusRel.setUtime(utime);
				userOrgPerCusRel.setName(name);
				userOrgPerCusRelService.createUserOrgPerCusRel(userOrgPerCusRel);
				resultMap.put("id", id);
				resultMap.put("status",1);
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
	 * 修改人脉
	 * 基本信息
	 * @param id
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
	 * @param tagIds 标签ID tagIds=3933811599736848&tagIds=3933811561988102&tagIds=3933811356467203
	 * 目录DirectorySource
	 * @param directoryId 目录ID
	 * 关联Associate
	 * @param associateJson json字符串
	 * @throws Exception
	 * @return MappingJacksonValue
	 * http://www.jsjtt.com/java/Javakuangjia/67.html
	 */
	@RequestMapping(path = { "/person/person/updatePerson" }, method = { RequestMethod.POST })
	public MappingJacksonValue updatePerson(HttpServletRequest request,HttpServletResponse response
			//基本信息
			,@RequestParam(name = "id",required = true) Long id
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "gender",required = true) Byte gender
			,@RequestParam(name = "picId",required = false) Long picId
			,@RequestParam(name = "firstIndustryId",required = false) Long firstIndustryId
			,@RequestParam(name = "secondIndustryId",required = false) Long secondIndustryId
			,@RequestParam(name = "personDefinedsJson", required = false) String personDefinedsJson
			,@RequestParam(name = "company",required = false) String company
			,@RequestParam(name = "position",required = false) String position
			,@RequestParam(name = "countryId",required = false) Long countryId
			,@RequestParam(name = "cityId",required = false) Long cityId
			,@RequestParam(name = "countyId",required = false) Long countyId
			,@RequestParam(name = "remark",required = false) String remark
			,@RequestParam(name = "address",required = false) String address
			//个人情况
			,@RequestParam(name = "birthday",required = false) String birthday
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
			,@RequestParam(name = "personWorkHistoryJson",required = false) String personWorkHistoryJson
			//教育经历
			,@RequestParam(name = "personEducationHistoryJson",required = false) String personEducationHistoryJson
			//标签TagSource
			,@RequestParam(name = "tagIds",required = false) Long[] tagIds
			//目录DirectorySource
			,@RequestParam(name = "directoryId",required = false) Long[] directoryIds
			//关联Associate
			,@RequestParam(name = "associateJson",required = false) String associateJson
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
		List<PersonDefined> listPersonDefined = null;
		PersonDefined personDefined=null;
		String ip=getIpAddr(request);
		Long appId =0l;
		Long ctime=0l;
		Long utime=0l;
		Long userId =0l;
		try {
			if((StringUtils.isEmpty(name))){
				resultMap.put( "message", "人脉姓名不能为空！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			if((ObjectUtils.isEmpty(id))){
				resultMap.put( "message", "人脉id不能为空！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			appId = LoginUserContextHolder.getAppKey();
			if(ObjectUtils.isEmpty(appId)){
				resultMap.put( "message", "appId不能为空！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			userId = LoginUserContextHolder.getUserId();
			if(ObjectUtils.isEmpty(userId)){
				resultMap.put( "message", "userId不能为空！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			ctime=System.currentTimeMillis();
			utime=System.currentTimeMillis();
			personBasic=personBasicService.getPersonBasic(id);
			if(ObjectUtils.isEmpty(personBasic)){
				resultMap.put( "message", "人脉id不存在！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
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
			boolean bl=personBasicService.updatePersonBasic(personBasic);
			if(bl==false){
				resultMap.put( "message", "保存人脉基本信息出错！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			personInfo =personInfoService.getPersonInfo(id);
			if(!ObjectUtils.isEmpty(personInfo)){
				personInfo.setBirthday(birthday);
				personInfo.setCountyId(countyId2);
				personInfo.setCityId(cityId2);
				personInfo.setCtime(ctime);
				personInfo.setIp(ip);
				personInfo.setPersonId(id);
				personInfo.setProvinceId(provinceId);
				bl=personInfoService.updatePersonInfo(personInfo);
				if(bl==false){
					personBasicService.realDeletePersonBasic(id);
					resultMap.put( "message", "保存人脉个人信息出错！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
			}
			personContactWay=personContactWayService.getPersonContactWay(id);
			if(!ObjectUtils.isEmpty(personContactWay)){
				personContactWay.setPersonId(id);
				personContactWay.setCellphone(cellphone);
				personContactWay.setEmail(email);
				personContactWay.setWeixin(weixin);
				personContactWay.setQq(qq);
				personContactWay.setWeibo(weibo);
				personContactWay.setCtime(ctime);
				personContactWay.setUtime(utime);
				personContactWay.setIp(ip);
				bl=personContactWayService.updatePersonContactWay(personContactWay);
				if(bl==false){
					personBasicService.realDeletePersonBasic(id);
					personInfoService.realDeletePersonInfo(id);
					personContactWayService.realDeletePersonContactWay(id);
					resultMap.put( "message", "保存人脉联系方式出错！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}
			}
			//保存自定义
			if(!StringUtils.isEmpty(personDefinedsJson)){
				listPersonDefined=new ArrayList<PersonDefined>();
				JSONObject jsonObject = JSONObject.fromObject(personDefinedsJson);
				JSONArray jsonArray=jsonObject.getJSONArray("personDefinedsJsonList");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i); 
					personDefined= new PersonDefined();
					personDefined.setPersonId(id);
					personDefined.setIp(ip);
					personDefined.setPersonDefinedModel(jsonObject2.has("model_name")?jsonObject2.getString("model_name"):null);
					personDefined.setPersonDefinedFiled(jsonObject2.has("filed")?jsonObject2.getString("filed"):null);
					personDefined.setPersonDefinedValue(jsonObject2.has("value")?jsonObject2.getString("value"):null);
					listPersonDefined.add(personDefined);
					listPersonDefined=personDefinedService.createPersonDefinedByList(listPersonDefined, id);
				}
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
					personWorkHistory.setIncName(jsonObject2.has("inc_name")?jsonObject2.getString("inc_name"):null);
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
				//查找该人脉下的所有标签
				List<TagSource> listTagSource=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, id, 1l);
				//删除该人脉下的所有标签
				for (TagSource tagSource2 : listTagSource) {
					bl=tagSourceService.removeTagSource(appId, userId, tagSource2.getId());
				}
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
			//保存目录
			if(!ObjectUtils.isEmpty(directoryIds)){
				//删除以前的
				bl=directorySourceService.removeDirectorySourcesBySourceId(userId, appId, 1, id);
				//保存现在的
				for (int i = 0; i < directoryIds.length; i++) {
					directorySource = new DirectorySource();
					directorySource.setDirectoryId(directoryIds[i]);
					directorySource.setAppId(appId);
					directorySource.setUserId(userId);
					directorySource.setSourceId(id);
					directorySource.setSourceType(1);
					directorySource.setCreateAt(ctime);
					directorySourceService.createDirectorySources(directorySource);
				}
			}
			//保存关联
			if(!StringUtils.isEmpty(associateJson)){
				JSONObject jsonObject = JSONObject.fromObject(associateJson);
				JSONArray jsonArray=jsonObject.getJSONArray("associateList");
				//查询以前人脉id关联的数据
				Map<AssociateType, List<Associate>> map=associateService.getAssociatesBy(appId, 1l, id);
				for ( AssociateType key  : map.keySet()) {
					List<Associate> list =map.get(key);
					for (Associate associate2 : list) {
						//删除以前的数据
						associateService.removeAssociate(appId, userId, associate2.getId());
					}
				}
				//创建新的。
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
			resultMap.put("id", id);
			resultMap.put("status",1);
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
	 * 获取人脉详情
	 * 
	 * @param id 
	 * @throws Exception
	 */
	@RequestMapping(path = { "/person/person/getPersonDetail" }, method = { RequestMethod.GET })
	public MappingJacksonValue getPersonDetail(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "id",required = true) Long id
			)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PersonBasic personBasic= null;
		PersonInfo personInfo= null;
		PersonContactWay personContactWay= null;
		List<PersonWorkHistory> listPersonWorkHistory = null;
		List<PersonEducationHistory> listPersonEducationHistory = null;
		List<TagSource> listTagSource=null;
		List<DirectorySource> listDirectorySource=null;
		List<PersonDefined> listPersonDefined=null;
		Map<AssociateType, List<Associate>> map=null;
		Long appId =0l;
		Long userId=0L;
		MappingJacksonValue mappingJacksonValue = null;
		try {
			if((ObjectUtils.isEmpty(id))){
				resultMap.put( "message", "人脉id不能为空！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			userId = LoginUserContextHolder.getUserId();
			if(ObjectUtils.isEmpty(userId)){
				resultMap.put( "message", "userId不能为空！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			appId = LoginUserContextHolder.getAppKey();
			if(ObjectUtils.isEmpty(appId)){
				resultMap.put( "message", "appId不能为空！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			personBasic=personBasicService.getPersonBasic(id);
			if(ObjectUtils.isEmpty(personBasic)){
				resultMap.put( "message", "人脉id不存在！");
				resultMap.put( "status", 0);
				return new MappingJacksonValue(resultMap);
			}
			List<Long> ids=personDefinedService.getIdList(id);
			if(ids!=null && ids.size()!=0)listPersonDefined=personDefinedService.getIdList(ids);
			personInfo=personInfoService.getPersonInfo(id);
			personContactWay=personContactWayService.getPersonContactWay(id);
			listPersonWorkHistory=personWorkHistoryService.getIdList(personWorkHistoryService.getIdList(id));
			listPersonEducationHistory=personEducationHistoryService.getIdList(personEducationHistoryService.getIdList(id));
			listTagSource=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, id, 1l);
			listDirectorySource=directorySourceService.getDirectorySourcesBySourceId(userId, appId, 1, id);
			map=associateService.getAssociatesBy(appId, 1l, id);
			for ( AssociateType key  : map.keySet()) {
				resultMap.put(key.getName(), map.get(key));
			}
			resultMap.put("personBasic", personBasic);
			resultMap.put("listPersonDefined", listPersonDefined);
			if(!ObjectUtils.isEmpty(personInfo))resultMap.put("personInfo", personInfo);
			if(!ObjectUtils.isEmpty(personContactWay))resultMap.put("personContactWay", personContactWay);
			if(!ObjectUtils.isEmpty(listPersonWorkHistory))resultMap.put("listPersonWorkHistory", listPersonWorkHistory);
			if(!ObjectUtils.isEmpty(listPersonEducationHistory))resultMap.put("listPersonEducationHistory", listPersonEducationHistory);
			if(!ObjectUtils.isEmpty(listTagSource))resultMap.put("listTagSource", listTagSource);
			if(!ObjectUtils.isEmpty(listDirectorySource))resultMap.put("listDirectorySource", listDirectorySource);
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider("id,tagName");
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		}catch (Exception e ){
			logger.info("获取人脉详情失败:"+id);
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