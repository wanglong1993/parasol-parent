package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ginkgocap.parasol.user.model.ModelType;
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
@Controller
public class UserInfoController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserInfoController.class);
	@Resource
	private UserInfoOperateService userInfoOperateService;
	
	@RequestMapping(path = { "/user/user/updateUser1" }, method = { RequestMethod.POST})
	public MappingJacksonValue updateUser(HttpServletRequest request,HttpServletResponse response,@RequestBody String body){
		JSONObject j = JSONObject.fromObject(body);
		Map<Integer,Object> paramMap = new HashMap<Integer,Object>();
		//用户基本信息
		if(j.containsKey("UB")){
			JSONObject ubJson = (JSONObject)j.get("UB");
			UserBasic userBasic = (UserBasic) JSONObject.toBean(ubJson, UserBasic.class);
			paramMap.put(ModelType.UB, userBasic);
		}
		//用户 联系方式
		if(j.containsKey("UC")){
			assemblyUserContact(j, paramMap);
		}
		//用户自定义
		if(j.containsKey("UD")){
			assemblyUserDefined(j,paramMap);
		}
		//用户描述
		if(j.containsKey("UDN")){
			JSONObject udnJson = (JSONObject)j.get("UDN");
			UserDescription userDescription = (UserDescription) JSONObject.toBean(udnJson, UserDescription.class);
			paramMap.put(ModelType.UDN, userDescription);
		}
		//用户教育经历
		if(j.containsKey("UEH")){
			assemblyUserEducationHistory(j,paramMap);
		}
		//用户家庭成员
		if(j.containsKey("UFM")){
			assemblyUserFamilyMember(j,paramMap);
		}
		//用户兴趣爱好
		if(j.containsKey("UIG")){
			JSONObject uigJson = (JSONObject)j.get("UIG");
			UserInteresting userInteresting = (UserInteresting) JSONObject.toBean(uigJson, UserInteresting.class);
			paramMap.put(ModelType.UIG, userInteresting);
		}
		//用户基本信息
		if(j.containsKey("UIO")){
			JSONObject uioJson = (JSONObject)j.get("UIO");
			UserInfo userInfo = (UserInfo) JSONObject.toBean(uioJson, UserInfo.class);
			paramMap.put(ModelType.UIO, userInfo);
		}
		//用户专业技能
		if(j.containsKey("US")){
			JSONObject usJson = (JSONObject)j.get("US");
			UserSkill userSkill = (UserSkill) JSONObject.toBean(usJson, UserSkill.class);
			paramMap.put(ModelType.UIO, userSkill);
		}
		if(j.containsKey("UWH")){
			assemblyUserWorkHistory(j,paramMap);
		}
		try {
			Boolean result = userInfoOperateService.updateInfo(paramMap);
		} catch (Exception e) {
			
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		return new MappingJacksonValue(resultMap);
	}


	/**
	 * 组装用户联系方式
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserContact(JSONObject j, Map<Integer, Object> paramMap) {
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
			userContactMap.put("add", userContacts);
		}
		paramMap.put(ModelType.UC, userContactMap);
	}
	
	/**
	 * 组装用户自定义
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserDefined(JSONObject j, Map<Integer, Object> paramMap) {
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
			userMap.put("add", userDefineds);
		}
		paramMap.put(ModelType.UD, userMap);
	}
	
	/**
	 * 组装用户教育经历
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserEducationHistory(JSONObject j, Map<Integer, Object> paramMap) {
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
			userMap.put("add", userEducationHistorys);
		}
		paramMap.put(ModelType.UEH, userMap);
	}
	
	
	/**
	 * 组装用户家庭成员
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserFamilyMember(JSONObject j, Map<Integer, Object> paramMap) {
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
			userMap.put("add", userFamilyMembers);
		}
		paramMap.put(ModelType.UFM, userMap);
	}
	
	/**
	 * 组装用户工作经历
	 * @param j
	 * @param paramMap
	 */
	private void assemblyUserWorkHistory(JSONObject j, Map<Integer, Object> paramMap) {
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
	@RequestMapping(path = { "/user/user/getUserDetail1" }, method = { RequestMethod.GET })
	public MappingJacksonValue getUserDetail(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(name = "userId",required = true) long userId)throws Exception {
		/*Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginRegister userLoginRegister= null;
		UserOrganBasic userOrganBasic= null;
		UserOrganExt userOrganExt= null;
		UserBasic userBasic= null;
//		UserExt userExt= null;
		List<UserDefined> list=null;
		UserInfo userInfo= null;
//		UserContactWay userContactWay= null;
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
//				List<Long> ids=userDefinedService.getIdList(userLoginRegister.getId());
//				if(ids!=null && ids.size()!=0)list=userDefinedService.getIdList(ids);
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
				mappingJacksonValue = new MappingJacksonValue(resultMap);
//				SimpleFilterProvider filterProvider = builderSimpleFilterProvider("id,tagName");
//				mappingJacksonValue.setFilters(filterProvider);
			}
			if(userLoginRegister.getUsetType().intValue()==0){
				userBasic=userBasicService.getObject(userLoginRegister.getId());
//				userExt=userExtService.getUserExt(userLoginRegister.getId());
				resultMap.put("userLoginRegister", userLoginRegister);
//				List<Long> ids=userDefinedService.getIdList(userLoginRegister.getId());
//				if(ids!=null && ids.size()!=0)list=userDefinedService.getIdList(ids);
				if(!ObjectUtils.isEmpty(userBasic)){
					if(!StringUtils.isEmpty(userBasic.getPicPath())){
						userBasic.setPicPath(dfsGintongCom+userBasic.getPicPath());
					}
				}
				listUserInterestIndustry=userInterestIndustryService.getInterestIndustryListBy(userInterestIndustryService.getIdList(userId));
//				userInfo=userInfoService.getUserInfo(userId);
//				userContactWay=userContactWayService.getUserContactWay(userId);
//				listUserWorkHistory=userWorkHistoryService.getIdList(userWorkHistoryService.getIdList(userId));
//				listUserEducationHistory=userEducationHistoryService.getIdList(userEducationHistoryService.getIdList(userId));
				listTagSource=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, userId, 1l);
				listDirectorySource=directorySourceService.getDirectorySourcesBySourceId(userId, appId, 1, userId);
				map=associateService.getAssociatesBy(appId, 1l, userId);
				for ( AssociateType key  : map.keySet()) {
					resultMap.put(key.getName(), map.get(key));
				}
				resultMap.put("userBasic", userBasic);
				if(!ObjectUtils.isEmpty(userInfo))resultMap.put("userInfo", userInfo);
				if(!ObjectUtils.isEmpty(listUserInterestIndustry))resultMap.put("listUserInterestIndustry", listUserInterestIndustry);
//				if(!ObjectUtils.isEmpty(userContactWay))resultMap.put("userContactWay", userContactWay);
				if(!ObjectUtils.isEmpty(listUserWorkHistory))resultMap.put("listUserWorkHistory", listUserWorkHistory);
				if(!ObjectUtils.isEmpty(listUserEducationHistory))resultMap.put("listUserEducationHistory", listUserEducationHistory);
				if(!ObjectUtils.isEmpty(listTagSource))resultMap.put("listTagSource", listTagSource);
				if(!ObjectUtils.isEmpty(listDirectorySource))resultMap.put("listDirectorySource", listDirectorySource);
				resultMap.put("userBasic", userBasic);
//				resultMap.put("userExt", userExt);
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
		}*/
		return null;
	}
	
	
}
