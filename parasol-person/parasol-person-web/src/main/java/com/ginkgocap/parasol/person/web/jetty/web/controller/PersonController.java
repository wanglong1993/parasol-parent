package com.ginkgocap.parasol.person.web.jetty.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.metadata.exception.CodeRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.person.model.ModelType;
import com.ginkgocap.parasol.person.model.PersonAttachment;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonContact;
import com.ginkgocap.parasol.person.model.PersonDefined;
import com.ginkgocap.parasol.person.model.PersonDescription;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.model.PersonFamilyMember;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.model.PersonInteresting;
import com.ginkgocap.parasol.person.model.PersonRTemplate;
import com.ginkgocap.parasol.person.model.PersonSkill;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonInfoOperateService;
import com.ginkgocap.parasol.person.service.PersonRTemplateService;
import com.ginkgocap.parasol.util.PinyinUtils;

/**
 * 人脉登录注册
 */
@RestController
public class PersonController extends BaseControl {
	private static Logger logger = Logger.getLogger(PersonController.class);
	@Resource
	private PersonInfoOperateService personInfoOperateService;
	@Resource
	private FileIndexService fileIndexService;
	@Resource
	private CodeRegionService codeRegionService;
	@Resource 
	private PersonBasicService personBasicService;
	@Resource
	private PersonRTemplateService personRTemplateService;
	
	@Value("${dfs.gintong.com}")  
	private String dfsGintongCom;//图片服务器地址
	//私密
	public final static int PRIVACY = 0;
	//好友可见
	public final static int FRIENT = 1;
	//部分好友
	public final static int PARTFRIENT = 2;
	//公开
	public final static int OPEN = 3;
	/**
	 * 插入人脉
	 * @param request
	 * @param response
	 * @param body
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = { "/person/person/savePerson" }, method = { RequestMethod.POST})
	public Map<String,Object> savePerson(HttpServletRequest request,HttpServletResponse response,@RequestBody String body){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(body);
		Map<Integer,Object> paramMap = new HashMap<Integer,Object>();
		Long userId = LoginUserContextHolder.getUserId();
		long appId = LoginUserContextHolder.getAppKey();
		String ip = this.getIpAddr(request);
		Long personId = null;
		//人脉来源，0、是自己创建，默认为0。1、是保存别人的
		int fromType = j.containsKey("fromType")?j.getInt("fromTye"):0;
		try{
			//用户基本信息
			if(j.containsKey("UB")){
				JSONObject ubJson = (JSONObject)j.get("UB");
				PersonBasic personBasic = (PersonBasic) JSONObject.toBean(ubJson, PersonBasic.class);
				personBasic.setCreateId(userId);
				String name = personBasic.getName();
				String nameAll = PinyinUtils.stringToQuanPin(name);
				String nameIndex = PinyinUtils.stringToHeads(name);
				String nameFirst = StringUtils.substring(nameIndex, 0, 1);
				personBasic.setNameIndexAll(nameAll);
				personBasic.setNameIndex(nameIndex);
				personBasic.setNameFirst(nameFirst);
				personBasic.setIp(ip);
				personBasic.setAppId(appId);
				personBasic.setFromType(fromType);
				personId = personBasicService.createObject(personBasic);
			}
			//用户 联系方式
			if(j.containsKey("UC")){
				assemblySavePersonContact(j, paramMap,ip,appId,personId);
			}
			//用户自定义
			if(j.containsKey("UD")){
				assemblySavePersonDefined(j,paramMap,ip,appId,personId);
			}
			//用户描述
			if(j.containsKey("UDN")){
				JSONObject udnJson = (JSONObject)j.get("UDN");
				PersonDescription personDescription = (PersonDescription) JSONObject.toBean(udnJson, PersonDescription.class);
				personDescription.setPersonId(personId);
				personDescription.setIp(ip);
				personDescription.setAppId(appId);
				paramMap.put(ModelType.UDN, personDescription);
			}
			//用户教育经历
			if(j.containsKey("UEH")){
				assemblySavePersonEducationHistory(j,paramMap,ip,appId,personId);
			}
			//用户家庭成员
			if(j.containsKey("UFM")){
				assemblySavePersonFamilyMember(j,paramMap,ip,appId,personId);
			}
			//用户兴趣爱好
			if(j.containsKey("UIG")){
				JSONObject uigJson = (JSONObject)j.get("UIG");
				PersonInteresting personInteresting = (PersonInteresting) JSONObject.toBean(uigJson, PersonInteresting.class);
				personInteresting.setPersonId(personId);
				personInteresting.setIp(ip);
				personInteresting.setAppId(appId);
				paramMap.put(ModelType.UIG, personInteresting);
			}
			//用户基本信息
			if(j.containsKey("UIO")){
				JSONObject uioJson = (JSONObject)j.get("UIO");
				PersonInfo personInfo = (PersonInfo) JSONObject.toBean(uioJson, PersonInfo.class);
				personInfo.setPersonId(personId);
				personInfo.setIp(ip);
				personInfo.setAppId(appId);
				paramMap.put(ModelType.UIO, personInfo);
			}
			//用户专业技能
			if(j.containsKey("US")){
				JSONObject usJson = (JSONObject)j.get("US");
				PersonSkill personSkill = (PersonSkill) JSONObject.toBean(usJson, PersonSkill.class);
				personSkill.setPersonId(personId);
				personSkill.setIp(ip);
				personSkill.setAppId(appId);
				paramMap.put(ModelType.US, personSkill);
			}
			if(j.containsKey("UWH")){
				assemblySavePersonWorkHistory(j,paramMap,ip,appId,personId);
			}
			
			//用户相关附件
			if(j.containsKey("UA")){
				JSONObject ubJson = (JSONObject)j.get("UA");
				PersonAttachment personAttachment = (PersonAttachment) JSONObject.toBean(ubJson, PersonAttachment.class);
				personAttachment.setPersonId(personId);
				personAttachment.setIp(ip);
				personAttachment.setAppId(appId);
				paramMap.put(ModelType.UA, personAttachment);
			}
			try {
				Boolean result = personInfoOperateService.saveInfo(paramMap);
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error(body);
				resultMap.put("message", e.getMessage());
				resultMap.put("status",1);
			}
			resultMap.put("personId", personId);
			resultMap.put("message", "保存成功！");
			resultMap.put("status",0);
		}catch(Exception e){
			resultMap.put("message", "保存失败！");
			resultMap.put("status",1);
		}
		return  resultMap;
	}
	
	private void assemblySavePersonContact(JSONObject j,Map<Integer, Object> paramMap, String ip, long appId,
			Long personId) {
		JSONArray ucJson = j.getJSONArray("UC");
		List<PersonContact> personContacts = (List<PersonContact>)JSONArray.toCollection(ucJson, PersonContact.class);
		for(PersonContact personContact:personContacts){
			personContact.setPersonId(personId);
			personContact.setIp(ip);
			personContact.setAppId(appId);
		}
		paramMap.put(ModelType.UC, personContacts);
	}
	
	private void assemblySavePersonEducationHistory(JSONObject j,Map<Integer, Object> paramMap, String ip, long appId,
			Long personId) {
		JSONArray uehJson = j.getJSONArray("UEH");
		List<PersonEducationHistory> personEducationHistorys = (List<PersonEducationHistory>)JSONArray.toCollection(uehJson, PersonEducationHistory.class);
		for(PersonEducationHistory personEducationHistory:personEducationHistorys){
			personEducationHistory.setPersonId(personId);
			personEducationHistory.setIp(ip);
			personEducationHistory.setAppId(appId);
		}
		paramMap.put(ModelType.UEH, personEducationHistorys);
	}
	
	private void assemblySavePersonDefined(JSONObject j,Map<Integer, Object> paramMap, String ip, long appId,
			Long personId) {
		JSONArray udJson = j.getJSONArray("UD");
		List<PersonDefined> personDefineds = (List<PersonDefined>)JSONArray.toCollection(udJson, PersonDefined.class);
		for(PersonDefined personDefined:personDefineds){
			personDefined.setPersonId(personId);
			personDefined.setIp(ip);
			personDefined.setAppId(appId);
		}
		paramMap.put(ModelType.UC, personDefineds);
	}
	
	private void assemblySavePersonFamilyMember(JSONObject j,Map<Integer, Object> paramMap, String ip, long appId,
			Long personId) {
		JSONArray ufmJson = j.getJSONArray("UFM");
		List<PersonFamilyMember> personFamilyMembers = (List<PersonFamilyMember>)JSONArray.toCollection(ufmJson, PersonFamilyMember.class);
		for(PersonFamilyMember personFamilyMember:personFamilyMembers){
			personFamilyMember.setPersonId(personId);
			personFamilyMember.setIp(ip);
			personFamilyMember.setAppId(appId);
		}
		paramMap.put(ModelType.UFM, personFamilyMembers);
	}
	
	private void assemblySavePersonWorkHistory(JSONObject j,Map<Integer, Object> paramMap, String ip, long appId,
			Long personId) {
		JSONArray uwhJson = j.getJSONArray("UWH");
		List<PersonWorkHistory> personWorkHistorys = (List<PersonWorkHistory>)JSONArray.toCollection(uwhJson, PersonWorkHistory.class);
		for(PersonWorkHistory personWorkHistory:personWorkHistorys){
			personWorkHistory.setPersonId(personId);
			personWorkHistory.setIp(ip);
			personWorkHistory.setAppId(appId);
		}
		paramMap.put(ModelType.UWH, personWorkHistorys);
	}

	@ResponseBody
	@RequestMapping(path = { "/person/person/updatePerson" }, method = { RequestMethod.POST})
	public Map<String,Object> updatePerson(HttpServletRequest request,HttpServletResponse response,@RequestBody String body){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(body);
		Map<Integer,Object> paramMap = new HashMap<Integer,Object>();
		Long userId = LoginUserContextHolder.getUserId();
		long appId = LoginUserContextHolder.getAppKey();
		String ip = this.getIpAddr(request);
		//用户基本信息
		if(j.containsKey("UB")){
			JSONObject ubJson = (JSONObject)j.get("UB");
			PersonBasic personBasic = (PersonBasic) JSONObject.toBean(ubJson, PersonBasic.class);
			personBasic.setCreateId(userId);
			String name = personBasic.getName();
			String nameAll = PinyinUtils.stringToQuanPin(name);
			String nameIndex = PinyinUtils.stringToHeads(name);
			String nameFirst = StringUtils.substring(nameIndex, 0, 1);
			personBasic.setNameIndexAll(nameAll);
			personBasic.setNameIndex(nameIndex);
			personBasic.setNameFirst(nameFirst);
			personBasic.setIp(ip);
			personBasic.setAppId(appId);
		}
		//用户 联系方式
		if(j.containsKey("UC")){
			assemblyUpdatePersonContact(j, paramMap,ip,appId);
		}
		//用户自定义
		if(j.containsKey("UD")){
			assemblyUpdatePersonDefined(j,paramMap,ip,appId);
		}
		//用户描述
		if(j.containsKey("UDN")){
			JSONObject udnJson = (JSONObject)j.get("UDN");
			PersonDescription personDescription = (PersonDescription) JSONObject.toBean(udnJson, PersonDescription.class);
			personDescription.setIp(ip);
			personDescription.setAppId(appId);
			
			paramMap.put(ModelType.UDN, personDescription);
		}
		//用户教育经历
		if(j.containsKey("UEH")){
			assemblyUpdatePersonEducationHistory(j,paramMap,ip,appId);
		}
		//用户家庭成员
		if(j.containsKey("UFM")){
			assemblyUpdatePersonFamilyMember(j,paramMap,ip,appId);
		}
		//用户兴趣爱好
		if(j.containsKey("UIG")){
			JSONObject uigJson = (JSONObject)j.get("UIG");
			PersonInteresting personInteresting = (PersonInteresting) JSONObject.toBean(uigJson, PersonInteresting.class);
			personInteresting.setIp(ip);
			personInteresting.setAppId(appId);
			paramMap.put(ModelType.UIG, personInteresting);
		}
		//用户基本信息
		if(j.containsKey("UIO")){
			JSONObject uioJson = (JSONObject)j.get("UIO");
			PersonInfo personInfo = (PersonInfo) JSONObject.toBean(uioJson, PersonInfo.class);
			personInfo.setIp(ip);
			personInfo.setAppId(appId);
			paramMap.put(ModelType.UIO, personInfo);
		}
		//用户专业技能
		if(j.containsKey("US")){
			JSONObject usJson = (JSONObject)j.get("US");
			PersonSkill personSkill = (PersonSkill) JSONObject.toBean(usJson, PersonSkill.class);
			personSkill.setIp(ip);
			personSkill.setAppId(appId);
			paramMap.put(ModelType.US, personSkill);
		}
		if(j.containsKey("UWH")){
			assemblyUpdatePersonWorkHistory(j,paramMap,ip,appId);
		}
		
		//用户相关附件
		if(j.containsKey("UA")){
			JSONObject ubJson = (JSONObject)j.get("UA");
			PersonAttachment personAttachment = (PersonAttachment) JSONObject.toBean(ubJson, PersonAttachment.class);
			personAttachment.setIp(ip);
			personAttachment.setAppId(appId);
			paramMap.put(ModelType.UA, personAttachment);
		}
		try {
			Boolean result = personInfoOperateService.updateInfo(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(body);
			resultMap.put("message", e.getMessage());
			resultMap.put("status",0);
			return  resultMap;
		}
		resultMap.put("message", "设置成功！");
		resultMap.put("status",0);
		
		return  resultMap;
	}


	/**
	 * 组装用户联系方式
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUpdatePersonContact(JSONObject j, Map<Integer, Object> paramMap,String ip,long appId) {
		Map<String,List<PersonContact>> personContactMap = new HashMap<String,List<PersonContact>>();
		JSONObject ubJson = (JSONObject)j.get("UC");
		if(ubJson.containsKey("delete")){
			JSONArray deleteJson = ubJson.getJSONArray("delete");
			List<PersonContact> personContacts = (List<PersonContact>)JSONArray.toCollection(deleteJson, PersonContact.class);
			personContactMap.put("delete", personContacts);
		}
		if(ubJson.containsKey("update")){
			JSONArray updateJson = ubJson.getJSONArray("update");
			List<PersonContact> personContacts = (List<PersonContact>)JSONArray.toCollection(updateJson, PersonContact.class);
			personContactMap.put("update", personContacts);
		}
		if(ubJson.containsKey("add")){
			JSONArray addJson = ubJson.getJSONArray("add");
			List<PersonContact> personContacts = (List<PersonContact>)JSONArray.toCollection(addJson, PersonContact.class);
			for(PersonContact personContact:personContacts){
				personContact.setIp(ip);
				personContact.setAppId(appId);
			}
			personContactMap.put("add", personContacts);
		}
		paramMap.put(ModelType.UC, personContactMap);
	}
	
	/**
	 * 组装用户自定义
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUpdatePersonDefined(JSONObject j, Map<Integer, Object> paramMap,String ip,long appId) {
		Map<String,List<PersonDefined>> personMap = new HashMap<String,List<PersonDefined>>();
		JSONObject udJson = (JSONObject)j.get("UD");
		if(udJson.containsKey("delete")){
			JSONArray deleteJson = udJson.getJSONArray("delete");
			List<PersonDefined> personDefineds = (List<PersonDefined>)JSONArray.toCollection(deleteJson, PersonDefined.class);
			personMap.put("delete", personDefineds);
		}
		if(udJson.containsKey("update")){
			JSONArray updateJson = udJson.getJSONArray("update");
			List<PersonDefined> personDefineds = (List<PersonDefined>)JSONArray.toCollection(updateJson, PersonDefined.class);
			personMap.put("update", personDefineds);
		}
		if(udJson.containsKey("add")){
			JSONArray addJson = udJson.getJSONArray("add");
			List<PersonDefined> personDefineds = (List<PersonDefined>)JSONArray.toCollection(addJson, PersonDefined.class);
			for(PersonDefined personDefined:personDefineds){
				personDefined.setIp(ip);
				personDefined.setAppId(appId);
			}
			personMap.put("add", personDefineds);
		}
		paramMap.put(ModelType.UD, personMap);
	}
	
	/**
	 * 组装用户教育经历
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUpdatePersonEducationHistory(JSONObject j, Map<Integer, Object> paramMap,String ip,long appId) {
		Map<String,List<PersonEducationHistory>> personMap = new HashMap<String,List<PersonEducationHistory>>();
		JSONObject uehJson = (JSONObject)j.get("UEH");
		if(uehJson.containsKey("delete")){
			JSONArray deleteJson = uehJson.getJSONArray("delete");
			List<PersonEducationHistory> personEducationHistorys = (List<PersonEducationHistory>)JSONArray.toCollection(deleteJson, PersonEducationHistory.class);
			personMap.put("delete", personEducationHistorys);
		}
		if(uehJson.containsKey("update")){
			JSONArray updateJson = uehJson.getJSONArray("update");
			List<PersonEducationHistory> personEducationHistorys = (List<PersonEducationHistory>)JSONArray.toCollection(updateJson, PersonEducationHistory.class);
			personMap.put("update", personEducationHistorys);
		}
		if(uehJson.containsKey("add")){
			JSONArray addJson = uehJson.getJSONArray("add");
			List<PersonEducationHistory> personEducationHistorys = (List<PersonEducationHistory>)JSONArray.toCollection(addJson, PersonEducationHistory.class);
			for(PersonEducationHistory personEducationHistory:personEducationHistorys){
				personEducationHistory.setIp(ip);
				personEducationHistory.setAppId(appId);
			}
			personMap.put("add", personEducationHistorys);
		}
		paramMap.put(ModelType.UEH, personMap);
	}
	
	
	/**
	 * 组装用户家庭成员
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUpdatePersonFamilyMember(JSONObject j, Map<Integer, Object> paramMap,String ip,Long appId) {
		Map<String,List<PersonFamilyMember>> personMap = new HashMap<String,List<PersonFamilyMember>>();
		JSONObject ufmJson = (JSONObject)j.get("UFM");
		if(ufmJson.containsKey("delete")){
			JSONArray deleteJson = ufmJson.getJSONArray("delete");
			List<PersonFamilyMember> personFamilyMembers = (List<PersonFamilyMember>)JSONArray.toCollection(deleteJson, PersonFamilyMember.class);
			personMap.put("delete", personFamilyMembers);
		}
		if(ufmJson.containsKey("update")){
			JSONArray updateJson = ufmJson.getJSONArray("update");
			List<PersonFamilyMember> personFamilyMembers = (List<PersonFamilyMember>)JSONArray.toCollection(updateJson, PersonFamilyMember.class);
			personMap.put("update", personFamilyMembers);
		}
		if(ufmJson.containsKey("add")){
			JSONArray addJson = ufmJson.getJSONArray("add");
			List<PersonFamilyMember> personFamilyMembers = (List<PersonFamilyMember>)JSONArray.toCollection(addJson, PersonFamilyMember.class);
			for(PersonFamilyMember personFamilyMember:personFamilyMembers){
				personFamilyMember.setIp(ip);
				personFamilyMember.setAppId(appId);
			}
			personMap.put("add", personFamilyMembers);
		}
		paramMap.put(ModelType.UFM, personMap);
	}
	
	/**
	 * 组装用户工作经历
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUpdatePersonWorkHistory(JSONObject j, Map<Integer, Object> paramMap,String ip,Long appId) {
		Map<String,List<PersonWorkHistory>> personMap = new HashMap<String,List<PersonWorkHistory>>();
		JSONObject uwhJson = (JSONObject)j.get("UWH");
		if(uwhJson.containsKey("delete")){
			JSONArray deleteJson = uwhJson.getJSONArray("delete");
			List<PersonWorkHistory> personWorkHistorys = (List<PersonWorkHistory>)JSONArray.toCollection(deleteJson, PersonWorkHistory.class);
			personMap.put("delete", personWorkHistorys);
		}
		if(uwhJson.containsKey("update")){
			JSONArray updateJson = uwhJson.getJSONArray("update");
			List<PersonWorkHistory> personWorkHistorys = (List<PersonWorkHistory>)JSONArray.toCollection(updateJson, PersonWorkHistory.class);
			personMap.put("update", personWorkHistorys);
		}
		if(uwhJson.containsKey("add")){
			JSONArray addJson = uwhJson.getJSONArray("add");
			List<PersonWorkHistory> personWorkHistorys = (List<PersonWorkHistory>)JSONArray.toCollection(addJson, PersonWorkHistory.class);
			for(PersonWorkHistory personWorkHistory:personWorkHistorys){
				personWorkHistory.setIp(ip);
				personWorkHistory.setAppId(appId);
			}
			personMap.put("add", personWorkHistorys);
		}
		paramMap.put(ModelType.UWH, personMap);
	}
	
	/**
	 * 获取用户资料
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path = { "/person/person/getPersonDetail" }, method = { RequestMethod.POST })
	public MappingJacksonValue getPersonDetail(@RequestBody(required = false) String body) {
		Integer[] modelTypes = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		long userId = LoginUserContextHolder.getUserId();
		//simple=1 代表全字段返回，如果simple=0过滤掉ip 创建时间 更新时间等返回
		long personId = 0l;
		boolean isSelf = true;
		if(body!=null){
			JSONObject j = JSONObject.fromObject(body);
			if(j.containsKey("personId")){
				resultMap.put("message","personId is not null");
				resultMap.put("status",1);
				return  new MappingJacksonValue(resultMap);
			}
			personId = j.getLong("personId");
			if(j.containsKey("models")){
				JSONArray modelsJson = j.getJSONArray("models");
				modelTypes = new Integer[modelsJson.size()];
				List<String> modelsList = (List<String>)modelsJson.toCollection(modelsJson, String.class);
				int size = modelsList.size();
				for(int i =0;i<size;i++){
					modelTypes[i]=ModelType.getModelType(modelsList.get(i));
				}
			}else{
				modelTypes = ModelType.MODELS;
			}
		}else{
			modelTypes = ModelType.MODELS;
		}
		try {
			Map<String,Object> info = personInfoOperateService.getInfo(personId, modelTypes);
			//添充基础信息，比如图片的id转化成path，区域转化成名称
			fillInfo(info);
			//若不是本人 则进行过滤
			resultMap.put("message",info);
			resultMap.put("status",1);
		} catch (Exception e) {
			resultMap.put("message", "获取信息失败！");
			resultMap.put("status",0);
		}
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(resultMap);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("personId","ip","utime","appId");
		filterProvider.addFilter(PersonBasic.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonInfo.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonContact.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonDescription.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonDefined.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonAttachment.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonSkill.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonEducationHistory.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonInteresting.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonWorkHistory.class.getName(), propertyFilter);
		filterProvider.addFilter(PersonFamilyMember.class.getName(), propertyFilter);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}
	/**
	 * 添充基础信息，比如图片的id转化成path，区域转化成名称
	 * @param info
	 * @throws FileIndexServiceException 
	 * @throws CodeRegionServiceException 
	 */
	private void fillInfo(Map<String, Object> info) throws FileIndexServiceException, CodeRegionServiceException {
		if(info.get("UB")!=null){
			PersonBasic personBasic = (PersonBasic)info.get("UB");
			Long picId = personBasic.getPicId();
			if(picId!=null&&picId!=0){
				FileIndex file = fileIndexService.getFileIndexById(picId);
				if(file!=null){
					String group = file.getServerHost();
					String filePath = file.getFilePath();
					personBasic.setPicPath(new StringBuilder().append(dfsGintongCom).append("/").append(group).append("/").append(filePath).toString());
				}
			}
			Long cityId = personBasic.getCityId();
			if(cityId!=null&&cityId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(cityId);
				if(codeRegion!=null)
					personBasic.setCityName(codeRegion.getCname());
			}
			Long provinceId = personBasic.getProvinceId();
			if(provinceId!=null&&provinceId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(provinceId);
				if(codeRegion!=null)	
					personBasic.setProvinceName(codeRegion.getCname());
			}
			Long countyId = personBasic.getCountyId();
			if(countyId!=null&&countyId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(countyId);
				if(codeRegion!=null)
					personBasic.setCountyName(codeRegion.getCname());
			}
		}
		if(info.get("UIO")!=null){
			PersonInfo personInfo = (PersonInfo)info.get("UIO");
			Long cityId = personInfo.getCityId();
			if(cityId!=null&&cityId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(cityId);
				if(codeRegion!=null)
					personInfo.setCityName(codeRegion.getCname());
			}
			Long provinceId = personInfo.getProvinceId();
			if(provinceId!=null&&provinceId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(provinceId);
				if(codeRegion!=null)
					personInfo.setProvinceName(codeRegion.getCname());
			}
			Long countyId = personInfo.getCountyId();
			if(countyId!=null&&countyId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(countyId);
				if(codeRegion!=null)
					personInfo.setCountyName(codeRegion.getCname());
			}
		}
	}
	/**
	 * 获取用户
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path = { "/person/person/getPersonList" }, method = { RequestMethod.POST })
	public MappingJacksonValue getPersonList(@RequestParam(name = "keyWord",required = false) String keyWord,
			@RequestParam(name = "start",required = false) int start,
			@RequestParam(name = "count",required = false) int count,
			@RequestParam(name = "orderColumn",required = false) String orderColumn) {
		long userId = LoginUserContextHolder.getUserId();
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = personBasicService.getPersonBasicListByCreateId(start, count, userId, keyWord, orderColumn);
			resultMap.put("status", 1);
		} catch (Exception e) {
			resultMap.put("message", "服务器内部错误!");
			resultMap.put("status", 0);
			return new MappingJacksonValue(resultMap);
		}
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(resultMap);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("ip","utime","appId");
		filterProvider.addFilter(PersonBasic.class.getName(), propertyFilter);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}
	
	/**
	 * 创建用户信息 选定一个模板
	 * @param templateId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = { "/user/user/choiceTemplate" }, method = { RequestMethod.GET })
	public Map<String,Object> choiceTemplate(@RequestParam(name = "templateId",required = true) long templateId,
				@RequestParam(name = "type",required = true) String type,
				@RequestParam(name = "personId",required = true) long personId) {
		Long userId = LoginUserContextHolder.getUserId();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(templateId==0){
			returnMap.put("status", 0);
			returnMap.put("message", "templateId is not null");
			return returnMap;
		}
		if(personId==0){
			returnMap.put("status", 0);
			returnMap.put("message", "personId is not null");
			return returnMap;
		}
		PersonRTemplate prt = new PersonRTemplate();
		prt.setPersonId(personId);
		prt.setTemplateId(templateId);
		prt.setType(type);
		try {
			PersonRTemplate prtOld = personRTemplateService.getObject(personId);
			if(prtOld!=null)
				personRTemplateService.updateObject(prt);
			else 
				personRTemplateService.createObject(prt);
		} catch (Exception e) {
			returnMap.put("status", 0);
			returnMap.put("message", "选定模板错误！");
			return returnMap;
		}
		returnMap.put("status", 1);
		returnMap.put("message", "选定模板成功！");
		return returnMap;
	}
}