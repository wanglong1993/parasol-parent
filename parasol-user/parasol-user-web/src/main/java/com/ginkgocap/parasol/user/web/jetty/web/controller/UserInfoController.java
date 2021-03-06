package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.metadata.exception.CodeRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.user.model.ModelType;
import com.ginkgocap.parasol.user.model.UserAttachment;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserContact;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.model.UserDescription;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.model.UserFamilyMember;
import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.model.UserInteresting;
import com.ginkgocap.parasol.user.model.UserSkill;
import com.ginkgocap.parasol.user.model.UserWorkHistory;
import com.ginkgocap.parasol.user.service.UserInfoOperateService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Controller
public class UserInfoController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserInfoController.class);
	@Resource
	private UserInfoOperateService userInfoOperateService;
	@Resource
	private FileIndexService fileIndexService;
	@Resource
	private CodeRegionService codeRegionService;
	
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
	
	@ResponseBody
	@RequestMapping(path = { "/user/user/updateUser" }, method = { RequestMethod.POST})
	public Map<String,Object> updateUser(HttpServletRequest request,HttpServletResponse response,@RequestBody String body){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(body);
		Map<Integer,Object> paramMap = new HashMap<Integer,Object>();
		Long userId = LoginUserContextHolder.getUserId();
		long appId = LoginUserContextHolder.getAppKey();
		String ip = this.getIpAddr(request);
		//用户基本信息
		if(j.containsKey("UB")){
			JSONObject ubJson = (JSONObject)j.get("UB");
			UserBasic userBasic = (UserBasic) JSONObject.toBean(ubJson, UserBasic.class);
			userBasic.setUserId(userId);
			String name = userBasic.getName();
			String nameAll = PinyinUtils.stringToQuanPin(name);
			String nameIndex = PinyinUtils.stringToHeads(name);
			String nameFirst = StringUtils.substring(nameIndex, 0, 1);
			userBasic.setNameIndexAll(nameAll);
			userBasic.setNameIndex(nameIndex);
			userBasic.setNameFirst(nameFirst);
			userBasic.setIp(ip);
			userBasic.setAppId(appId);
			paramMap.put(ModelType.UB, userBasic);
		}
		//用户 联系方式
		if(j.containsKey("UC")){
			assemblyUserContact(j, paramMap,ip,appId,userId);
		}
		//用户自定义
		if(j.containsKey("UD")){
			assemblyUserDefined(j,paramMap,ip,appId,userId);
		}
		//用户描述
		if(j.containsKey("UDN")){
			JSONObject udnJson = (JSONObject)j.get("UDN");
			UserDescription userDescription = (UserDescription) JSONObject.toBean(udnJson, UserDescription.class);
			userDescription.setUserId(userId);
			userDescription.setIp(ip);
			userDescription.setAppId(appId);
			
			paramMap.put(ModelType.UDN, userDescription);
		}
		//用户教育经历
		if(j.containsKey("UEH")){
			assemblyUserEducationHistory(j,paramMap,ip,appId,userId);
		}
		//用户家庭成员
		if(j.containsKey("UFM")){
			assemblyUserFamilyMember(j,paramMap,ip,appId,userId);
		}
		//用户兴趣爱好
		if(j.containsKey("UIG")){
			JSONObject uigJson = (JSONObject)j.get("UIG");
			UserInteresting userInteresting = (UserInteresting) JSONObject.toBean(uigJson, UserInteresting.class);
			userInteresting.setUserId(userId);
			userInteresting.setIp(ip);
			userInteresting.setAppId(appId);
			paramMap.put(ModelType.UIG, userInteresting);
		}
		//用户基本信息
		if(j.containsKey("UIO")){
			JSONObject uioJson = (JSONObject)j.get("UIO");
			UserInfo userInfo = (UserInfo) JSONObject.toBean(uioJson, UserInfo.class);
			userInfo.setUserId(userId);
			userInfo.setIp(ip);
			userInfo.setAppId(appId);
			paramMap.put(ModelType.UIO, userInfo);
		}
		//用户专业技能
		if(j.containsKey("US")){
			JSONObject usJson = (JSONObject)j.get("US");
			UserSkill userSkill = (UserSkill) JSONObject.toBean(usJson, UserSkill.class);
			userSkill.setUserId(userId);
			userSkill.setIp(ip);
			userSkill.setAppId(appId);
			paramMap.put(ModelType.US, userSkill);
		}
		if(j.containsKey("UWH")){
			assemblyUserWorkHistory(j,paramMap,ip,appId,userId);
		}
		
		//用户相关附件
		if(j.containsKey("UA")){
			JSONObject ubJson = (JSONObject)j.get("UA");
			UserAttachment userAttachment = (UserAttachment) JSONObject.toBean(ubJson, UserAttachment.class);
			userAttachment.setUserId(userId);
			userAttachment.setIp(ip);
			userAttachment.setAppId(appId);
			paramMap.put(ModelType.UA, userAttachment);
		}
		try {
			Boolean result = userInfoOperateService.updateInfo(paramMap);
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
	private void assemblyUserContact(JSONObject j, Map<Integer, Object> paramMap,String ip,long appId,long userId) {
		Map<String,List<UserContact>> userContactMap = new HashMap<String,List<UserContact>>();
		JSONObject ubJson = (JSONObject)j.get("UC");
		if(ubJson.containsKey("delete")){
			JSONArray deleteJson = ubJson.getJSONArray("delete");
			List<UserContact> userContacts = (List<UserContact>)JSONArray.toCollection(deleteJson, UserContact.class);
			userContactMap.put("delete", userContacts);
		}
		if(ubJson.containsKey("update")){
			JSONArray updateJson = ubJson.getJSONArray("update");
			List<UserContact> userContacts = (List<UserContact>)JSONArray.toCollection(updateJson, UserContact.class);
			userContactMap.put("update", userContacts);
		}
		if(ubJson.containsKey("add")){
			JSONArray addJson = ubJson.getJSONArray("add");
			List<UserContact> userContacts = (List<UserContact>)JSONArray.toCollection(addJson, UserContact.class);
			for(UserContact userContact:userContacts){
				userContact.setUserId(userId);
				userContact.setIp(ip);
				userContact.setAppId(appId);
			}
			userContactMap.put("add", userContacts);
		}
		paramMap.put(ModelType.UC, userContactMap);
	}
	
	/**
	 * 组装用户自定义
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserDefined(JSONObject j, Map<Integer, Object> paramMap,String ip,long appId,long userId) {
		Map<String,List<UserDefined>> userMap = new HashMap<String,List<UserDefined>>();
		JSONObject udJson = (JSONObject)j.get("UD");
		if(udJson.containsKey("delete")){
			JSONArray deleteJson = udJson.getJSONArray("delete");
			List<UserDefined> userDefineds = (List<UserDefined>)JSONArray.toCollection(deleteJson, UserDefined.class);
			userMap.put("delete", userDefineds);
		}
		if(udJson.containsKey("update")){
			JSONArray updateJson = udJson.getJSONArray("update");
			List<UserDefined> userDefineds = (List<UserDefined>)JSONArray.toCollection(updateJson, UserDefined.class);
			userMap.put("update", userDefineds);
		}
		if(udJson.containsKey("add")){
			JSONArray addJson = udJson.getJSONArray("add");
			List<UserDefined> userDefineds = (List<UserDefined>)JSONArray.toCollection(addJson, UserDefined.class);
			for(UserDefined userDefined:userDefineds){
				userDefined.setUserId(userId);
				userDefined.setIp(ip);
				userDefined.setAppId(appId);
			}
			userMap.put("add", userDefineds);
		}
		paramMap.put(ModelType.UD, userMap);
	}
	
	/**
	 * 组装用户教育经历
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserEducationHistory(JSONObject j, Map<Integer, Object> paramMap,String ip,long appId,long userId) {
		Map<String,List<UserEducationHistory>> userMap = new HashMap<String,List<UserEducationHistory>>();
		JSONObject uehJson = (JSONObject)j.get("UEH");
		if(uehJson.containsKey("delete")){
			JSONArray deleteJson = uehJson.getJSONArray("delete");
			List<UserEducationHistory> userEducationHistorys = (List<UserEducationHistory>)JSONArray.toCollection(deleteJson, UserEducationHistory.class);
			userMap.put("delete", userEducationHistorys);
		}
		if(uehJson.containsKey("update")){
			JSONArray updateJson = uehJson.getJSONArray("update");
			List<UserEducationHistory> userEducationHistorys = (List<UserEducationHistory>)JSONArray.toCollection(updateJson, UserEducationHistory.class);
			userMap.put("update", userEducationHistorys);
		}
		if(uehJson.containsKey("add")){
			JSONArray addJson = uehJson.getJSONArray("add");
			List<UserEducationHistory> userEducationHistorys = (List<UserEducationHistory>)JSONArray.toCollection(addJson, UserEducationHistory.class);
			for(UserEducationHistory userEducationHistory:userEducationHistorys){
				userEducationHistory.setUserId(userId);
				userEducationHistory.setIp(ip);
				userEducationHistory.setAppId(appId);
			}
			userMap.put("add", userEducationHistorys);
		}
		paramMap.put(ModelType.UEH, userMap);
	}
	
	
	/**
	 * 组装用户家庭成员
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserFamilyMember(JSONObject j, Map<Integer, Object> paramMap,String ip,Long appId,long userId) {
		Map<String,List<UserFamilyMember>> userMap = new HashMap<String,List<UserFamilyMember>>();
		JSONObject ufmJson = (JSONObject)j.get("UFM");
		if(ufmJson.containsKey("delete")){
			JSONArray deleteJson = ufmJson.getJSONArray("delete");
			List<UserFamilyMember> userFamilyMembers = (List<UserFamilyMember>)JSONArray.toCollection(deleteJson, UserFamilyMember.class);
			userMap.put("delete", userFamilyMembers);
		}
		if(ufmJson.containsKey("update")){
			JSONArray updateJson = ufmJson.getJSONArray("update");
			List<UserFamilyMember> userFamilyMembers = (List<UserFamilyMember>)JSONArray.toCollection(updateJson, UserFamilyMember.class);
			userMap.put("update", userFamilyMembers);
		}
		if(ufmJson.containsKey("add")){
			JSONArray addJson = ufmJson.getJSONArray("add");
			List<UserFamilyMember> userFamilyMembers = (List<UserFamilyMember>)JSONArray.toCollection(addJson, UserFamilyMember.class);
			for(UserFamilyMember userFamilyMember:userFamilyMembers){
				userFamilyMember.setUserId(userId);
				userFamilyMember.setIp(ip);
				userFamilyMember.setAppId(appId);
			}
			userMap.put("add", userFamilyMembers);
		}
		paramMap.put(ModelType.UFM, userMap);
	}
	
	/**
	 * 组装用户工作经历
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserWorkHistory(JSONObject j, Map<Integer, Object> paramMap,String ip,Long appId,long userId) {
		Map<String,List<UserWorkHistory>> userMap = new HashMap<String,List<UserWorkHistory>>();
		JSONObject uwhJson = (JSONObject)j.get("UWH");
		if(uwhJson.containsKey("delete")){
			JSONArray deleteJson = uwhJson.getJSONArray("delete");
			List<UserWorkHistory> userWorkHistorys = (List<UserWorkHistory>)JSONArray.toCollection(deleteJson, UserWorkHistory.class);
			userMap.put("delete", userWorkHistorys);
		}
		if(uwhJson.containsKey("update")){
			JSONArray updateJson = uwhJson.getJSONArray("update");
			List<UserWorkHistory> userWorkHistorys = (List<UserWorkHistory>)JSONArray.toCollection(updateJson, UserWorkHistory.class);
			userMap.put("update", userWorkHistorys);
		}
		if(uwhJson.containsKey("add")){
			JSONArray addJson = uwhJson.getJSONArray("add");
			List<UserWorkHistory> userWorkHistorys = (List<UserWorkHistory>)JSONArray.toCollection(addJson, UserWorkHistory.class);
			for(UserWorkHistory userWorkHistory:userWorkHistorys){
				userWorkHistory.setUserId(userId);
				userWorkHistory.setIp(ip);
				userWorkHistory.setAppId(appId);
			}
			userMap.put("add", userWorkHistorys);
		}
		paramMap.put(ModelType.UWH, userMap);
	}
	
	/**
	 * 获取用户资料
	 * 
	 * @param passport 为邮箱和手机号
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path = { "/user/user/getUserDetail" }, method = { RequestMethod.POST })
	public MappingJacksonValue getUserDetail(@RequestBody(required = false) String body) {
		Integer[] modelTypes = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		long userId = LoginUserContextHolder.getUserId();
		//simple=1 代表全字段返回，如果simple=0过滤掉ip 创建时间 更新时间等返回
		long readUserId = userId;
		boolean isSelf = true;
		int simple = 0;
		if(body!=null){
			JSONObject j = JSONObject.fromObject(body);
			readUserId = j.containsKey("userId")?j.getLong("userId"):userId;
			isSelf=(userId==readUserId);
			simple = j.containsKey("simple")?j.getInt("simple"):simple;
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
			Map<String,Object> info = userInfoOperateService.getInfo(readUserId, modelTypes);
			//添充基础信息，比如图片的id转化成path，区域转化成名称
			fillInfo(info);
			//若不是本人 则进行过滤
			if(!isSelf)
				filterPermission(info,userId,isFriend(userId,readUserId));
			resultMap.put("message",info);
			resultMap.put("status",1);
		} catch (Exception e) {
			resultMap.put("message", "获取信息失败！");
			resultMap.put("status",0);
		}
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(resultMap);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("userId","ip","utime","appId");
		filterProvider.addFilter(UserBasic.class.getName(), propertyFilter);
		filterProvider.addFilter(UserInfo.class.getName(), propertyFilter);
		filterProvider.addFilter(UserContact.class.getName(), propertyFilter);
		filterProvider.addFilter(UserDescription.class.getName(), propertyFilter);
		filterProvider.addFilter(UserDefined.class.getName(), propertyFilter);
		filterProvider.addFilter(UserAttachment.class.getName(), propertyFilter);
		filterProvider.addFilter(UserSkill.class.getName(), propertyFilter);
		filterProvider.addFilter(UserEducationHistory.class.getName(), propertyFilter);
		filterProvider.addFilter(UserInteresting.class.getName(), propertyFilter);
		filterProvider.addFilter(UserWorkHistory.class.getName(), propertyFilter);
		filterProvider.addFilter(UserFamilyMember.class.getName(), propertyFilter);
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
			UserBasic userBasic = (UserBasic)info.get("UB");
			Long picId = userBasic.getPicId();
			if(picId!=null&&picId!=0){
				FileIndex file = fileIndexService.getFileIndexById(picId);
				if(file!=null){
					String group = file.getServerHost();
					String filePath = file.getFilePath();
					userBasic.setPicPath(new StringBuilder().append(dfsGintongCom).append("/").append(group).append("/").append(filePath).toString());
				}
			}
			Long cityId = userBasic.getCityId();
			if(cityId!=null&&cityId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(cityId);
				if(codeRegion!=null)
					userBasic.setCityName(codeRegion.getCname());
			}
			Long provinceId = userBasic.getProvinceId();
			if(provinceId!=null&&provinceId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(provinceId);
				if(codeRegion!=null)	
					userBasic.setProvinceName(codeRegion.getCname());
			}
			Long countyId = userBasic.getCountyId();
			if(countyId!=null&&countyId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(countyId);
				if(codeRegion!=null)
					userBasic.setCountyName(codeRegion.getCname());
			}
		}
		if(info.get("UIO")!=null){
			UserInfo userInfo = (UserInfo)info.get("UIO");
			Long cityId = userInfo.getCityId();
			if(cityId!=null&&cityId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(cityId);
				if(codeRegion!=null)
					userInfo.setCityName(codeRegion.getCname());
			}
			Long provinceId = userInfo.getProvinceId();
			if(provinceId!=null&&provinceId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(provinceId);
				if(codeRegion!=null)
					userInfo.setProvinceName(codeRegion.getCname());
			}
			Long countyId = userInfo.getCountyId();
			if(countyId!=null&&countyId!=0){
				CodeRegion codeRegion = codeRegionService.getCodeRegionById(countyId);
				if(codeRegion!=null)
					userInfo.setCountyName(codeRegion.getCname());
			}
		}
		
	}


	/**
	 * 
	 * 字段权限过滤
	 * @param models
	 */
	public void filterPermission(Map<String,Object> models,Long userId,boolean isFriend){
		Set<String> keys = models.keySet();
		for(String key : keys){
			if("UC".equals(key)){
				filterUC(models, userId, isFriend, key);
			}
			if("UD".equals(key)){
				filterUD(models, userId, isFriend, key);
			}
			if("UDN".equals(key)){
				filterUDN(models, userId, isFriend, key);
			}
			if("UEH".equals(key)){
				filterUEH(models, userId, isFriend, key);
			}
			if("UFM".equals(key)){
				filterUFM(models, userId, isFriend, key);
			}
			if("UIO".equals(key)){
				filterUIO(models, userId, isFriend, key);
			}
			if("UIG".equals(key)){
				filterUIG(models, userId, isFriend, key);
			}
			if("US".equals(key)){
				filterUS(models, userId, isFriend, key);
			}
			if("UWH".equals(key)){
				filterUWH(models, userId, isFriend, key);
			}
		}
	}
	
	private void filterUWH(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		List<UserWorkHistory> userWorkHistorys = (List<UserWorkHistory>)models.get(key);
		for(UserWorkHistory userWorkHistory:userWorkHistorys){
			int permission = userWorkHistory.getPermission();
			switch(permission){
			   case PRIVACY:
				   userWorkHistorys.remove(userWorkHistory);
			   case FRIENT:
				   if(!isFriend) userWorkHistorys.remove(userWorkHistory);
			   case PARTFRIENT:
				   String friendIdsStr = userWorkHistory.getFriendIds();
				   if(StringUtils.isEmpty(friendIdsStr)){
					   String[] friendIds = friendIdsStr.split(",");
					   int index = Arrays.binarySearch(friendIds, userId.toString());
					   if(index<0){
						   userWorkHistorys.remove(userWorkHistory);
					   }
				   }
			   case OPEN:
				   continue;
			}
		}
	}
	
	
	private void filterUS(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		UserSkill userSkill = (UserSkill)models.get(key);
		int permission = userSkill.getPermission();
		switch(permission){
		case PRIVACY:
			models.remove(key);
		case FRIENT:
			if(!isFriend) models.remove(key);
		case PARTFRIENT:
			 String friendIdsStr = userSkill.getFriendIds();
			   if(StringUtils.isEmpty(friendIdsStr)){
				   String[] friendIds = friendIdsStr.split(",");
				   int index = Arrays.binarySearch(friendIds, userId.toString());
				   if(index<0){
					   models.remove(key);
				   }
			   }
		case OPEN:
			   break;
		}
	}

	private void filterUIG(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		UserInteresting userInteresting = (UserInteresting)models.get(key);
		int permission = userInteresting.getPermission();
		switch(permission){
		case PRIVACY:
			models.remove(key);
		case FRIENT:
			if(!isFriend) models.remove(key);
		case PARTFRIENT:
			 String friendIdsStr = userInteresting.getFriendIds();
			   if(StringUtils.isEmpty(friendIdsStr)){
				   String[] friendIds = friendIdsStr.split(",");
				   int index = Arrays.binarySearch(friendIds, userId.toString());
				   if(index<0){
					   models.remove(key);
				   }
			   }
		case OPEN:
			   break;
		}
	}
	private void filterUIO(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		UserInfo userInfo = (UserInfo)models.get(key);
		int permission = userInfo.getPermission();
		switch(permission){
		case PRIVACY:
			models.remove(key);
		case FRIENT:
			if(!isFriend) models.remove(key);
		case PARTFRIENT:
			 String friendIdsStr = userInfo.getFriendIds();
			   if(StringUtils.isEmpty(friendIdsStr)){
				   String[] friendIds = friendIdsStr.split(",");
				   int index = Arrays.binarySearch(friendIds, userId.toString());
				   if(index<0){
					   models.remove(key);
				   }
			   }
		case OPEN:
			   break;
		}
	}
	
	private void filterUFM(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		List<UserFamilyMember> userFamilyMembers = (List<UserFamilyMember>)models.get(key);
		for(UserFamilyMember userEducationHistory:userFamilyMembers){
			int permission = userEducationHistory.getPermission();
			switch(permission){
			   case PRIVACY:
				   userFamilyMembers.remove(userEducationHistory);
			   case FRIENT:
				   if(!isFriend) userFamilyMembers.remove(userEducationHistory);
			   case PARTFRIENT:
				   String friendIdsStr = userEducationHistory.getFriendIds();
				   if(StringUtils.isEmpty(friendIdsStr)){
					   String[] friendIds = friendIdsStr.split(",");
					   int index = Arrays.binarySearch(friendIds, userId.toString());
					   if(index<0){
						   userFamilyMembers.remove(userEducationHistory);
					   }
				   }
			   case OPEN:
				   continue;
			}
		}
	}
	/**
	 * 过滤教育经历
	 * @param models
	 * @param userId
	 * @param isFriend
	 * @param key
	 */
	private void filterUEH(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		List<UserEducationHistory> userEducationHistorys = (List<UserEducationHistory>)models.get(key);
		for(UserEducationHistory userEducationHistory:userEducationHistorys){
			int permission = userEducationHistory.getPermission();
			switch(permission){
			   case PRIVACY:
				   userEducationHistorys.remove(userEducationHistory);
			   case FRIENT:
				   if(!isFriend) userEducationHistorys.remove(userEducationHistory);
			   case PARTFRIENT:
				   String friendIdsStr = userEducationHistory.getFriendIds();
				   if(StringUtils.isEmpty(friendIdsStr)){
					   String[] friendIds = friendIdsStr.split(",");
					   int index = Arrays.binarySearch(friendIds, userId.toString());
					   if(index<0){
						   userEducationHistorys.remove(userEducationHistory);
					   }
				   }
			   case OPEN:
				   continue;
			}
		}
	}
	/**
	 * 过滤用户描述
	 * @param models
	 * @param userId
	 * @param isFriend
	 * @param key
	 */
	private void filterUDN(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		UserDescription userDescription = (UserDescription)models.get(key);
		int permission = userDescription.getPermission();
		switch(permission){
		case PRIVACY:
			models.remove(key);
		case FRIENT:
			if(!isFriend) models.remove(key);
		case PARTFRIENT:
			 String friendIdsStr = userDescription.getFriendIds();
			   if(StringUtils.isEmpty(friendIdsStr)){
				   String[] friendIds = friendIdsStr.split(",");
				   int index = Arrays.binarySearch(friendIds, userId.toString());
				   if(index<0){
					   models.remove(key);
				   }
			   }
		case OPEN:
			   break;
		}
	}

	/**
	 * 过滤用户联系方式
	 * @param models
	 * @param userId
	 * @param isFriend
	 * @param key
	 */
	private void filterUC(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		List<UserContact> userContacts = (List<UserContact>)models.get(key);
		for(UserContact userContact:userContacts){
			int permission = userContact.getPermission();
			switch(permission){
			   case PRIVACY:
				   userContacts.remove(userContact);
			   case FRIENT:
				   if(!isFriend) userContacts.remove(userContact);
			   case PARTFRIENT:
				   String friendIdsStr = userContact.getFriendIds();
				   if(StringUtils.isEmpty(friendIdsStr)){
					   String[] friendIds = friendIdsStr.split(",");
					   int index = Arrays.binarySearch(friendIds, userId.toString());
					   if(index<0){
						   userContacts.remove(userContact);
					   }
				   }
			   case OPEN:
				   continue;
			}
		}
	}

	/**
	 * 过滤用户自定义
	 * @param models
	 * @param userId
	 * @param isFriend
	 * @param key
	 */
	private void filterUD(Map<String, Object> models, Long userId,
			boolean isFriend, String key) {
		List<UserDefined> userDefineds = (List<UserDefined>)models.get(key);
		for(UserDefined userDefined:userDefineds){
			int permission = userDefined.getPermission();
			switch(permission){
			   case PRIVACY:
				   userDefineds.remove(userDefined);
			   case FRIENT:
				   if(!isFriend) userDefineds.remove(userDefined);
			   case PARTFRIENT:
				   String friendIdsStr = userDefined.getFriendIds();
				   if(StringUtils.isEmpty(friendIdsStr)){
					   String[] friendIds = friendIdsStr.split(",");
					   int index = Arrays.binarySearch(friendIds, userId.toString());
					   if(index<0){
						   userDefineds.remove(userDefined);
					   }
				   }
			   case OPEN:
				   continue;
			}
		}
	}
	public boolean isFriend(long userId,long friendId){
		return true;
	} 
	
}
