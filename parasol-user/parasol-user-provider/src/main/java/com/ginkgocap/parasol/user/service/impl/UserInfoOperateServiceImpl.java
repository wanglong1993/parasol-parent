package com.ginkgocap.parasol.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.user.model.ModelType;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserContact;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.model.UserDefinedTemplate;
import com.ginkgocap.parasol.user.model.UserDescription;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.model.UserFamilyMember;
import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.model.UserSkill;
import com.ginkgocap.parasol.user.model.UserTemplate;
import com.ginkgocap.parasol.user.model.UserTemplateModel;
import com.ginkgocap.parasol.user.model.UserWorkHistory;
import com.ginkgocap.parasol.user.service.UserAttachmentService;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserDefinedService;
import com.ginkgocap.parasol.user.service.UserDefinedTemplateService;
import com.ginkgocap.parasol.user.service.UserDescriptionService;
import com.ginkgocap.parasol.user.service.UserEducationHistoryService;
import com.ginkgocap.parasol.user.service.UserFamilyMemberService;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.parasol.user.service.UserInfoOperateService;
import com.ginkgocap.parasol.user.service.UserInfoService;
import com.ginkgocap.parasol.user.service.UserInterestingService;
import com.ginkgocap.parasol.user.service.UserSkillService;
import com.ginkgocap.parasol.user.service.UserTemplateModelService;
import com.ginkgocap.parasol.user.service.UserTemplateService;
import com.ginkgocap.parasol.user.service.UserWorkHistoryService;
import com.ginkgocap.parasol.user.utils.BeanUtil;
@Service("userInfoOperateService")
public class UserInfoOperateServiceImpl implements UserInfoOperateService {
	@Resource
	private UserBasicService userBasicService;
	@Resource
	private UserAttachmentService userAttachmentService;
	@Resource
	private UserContactServiceImpl userContactService;
	@Resource 
	private UserDefinedService userDefinedService;
	@Resource
	private UserFamilyMemberService userFamilyMemberService;
	@Resource 
	private UserDescriptionService userDescriptionService;
	@Resource
	private UserEducationHistoryService userEducationHistoryService;
	@Resource
	private UserFriendlyService userFriendlyService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserInterestingService userInterestingService;
	@Resource
	private UserSkillService userSkillService;
	@Resource
	private UserWorkHistoryService userWorkHistoryService;

	@Override
	public boolean saveInfo(Map<Integer, Object> param)
			throws Exception {
		if(param==null) throw new Exception("请求模块参数不能为空！");
		Set<Integer> keys = param.keySet();
		Object entitys = null;
		for(Integer modelType : keys){
			switch(modelType){
			   case ModelType.UB :
			   		entitys =  param.get(modelType);
			   		UserBasic userBasic = (UserBasic)entitys;
			   		userBasicService.createObject(userBasic);
			   		continue;
			   case ModelType.UC :
			   		entitys =  param.get(modelType);
			   		List<UserContact> userContacts = (List<UserContact>)entitys;
			   		userContactService.createObjects(userContacts);
			   		continue;
			   case ModelType.UD :
			   		entitys =  param.get(modelType);
			   		List<UserDefined> userDefineds = (List<UserDefined>)entitys;
			   		userDefinedService.createObjects(userDefineds);
			   		continue;
			   case ModelType.UDN :
			   		entitys =  param.get(modelType);
			   		UserDescription userDescription = (UserDescription)entitys;
			   		userDescriptionService.createObject(userDescription);
			   		continue;
			   case ModelType.UEH :
			   		entitys =  param.get(modelType);
			   		List<UserEducationHistory> userEducationHistorys = (List<UserEducationHistory>)entitys;
			   		userEducationHistoryService.createObjects(userEducationHistorys);
			   		continue;
			   case ModelType.UFM :
			   		entitys =  param.get(modelType);
			   		List<UserFamilyMember>  userFamilyMembers= (List<UserFamilyMember>)entitys;
			   		userFamilyMemberService.createObjects(userFamilyMembers);
			   		continue;
			   case ModelType.UIO :
			   		entitys =  param.get(modelType);
			   		UserInfo  userInfo= (UserInfo)entitys;
			   		userInfoService.createObject(userInfo);
			   		continue;
			   case ModelType.US :
			   		entitys =  param.get(modelType);
			   		UserSkill  userSkill= (UserSkill)entitys;
			   		userSkillService.createObject(userSkill);
			   		continue;
			   case ModelType.UWH :
			   		entitys =  param.get(modelType);
			   		List<UserWorkHistory> userWorkHistorys= (List<UserWorkHistory>)entitys;
			   		userWorkHistoryService.createObjects(userWorkHistorys);
			   		continue;
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> getInfo(Long userId,
			Integer[] models) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(userId==null) throw new Exception("用户ID不能为空！");
		for(Integer modelType:models){
			switch(modelType){
			 case ModelType.UB :
			   		returnMap.put("UB", userBasicService.getObject(userId));
			   		continue;
			   case ModelType.UC :
				   	returnMap.put("UC",userContactService.getObjectsByUserId(userId));
			   		continue;
			   case ModelType.UD :
					returnMap.put("UD",userDefinedService.getObjectsByUserId(userId));
			   		continue;
			   case ModelType.UDN :
				   	returnMap.put("UDN", userDescriptionService.getObject(userId));
			   		continue;
			   case ModelType.UEH :
				   	returnMap.put("UEH",userEducationHistoryService.getObjectsByUserId(userId));
			   		continue;
			   case ModelType.UFM :
				   	returnMap.put("UFM",userFamilyMemberService.getObjectsByUserId(userId));
			   		continue;
			   case ModelType.UIO :
				   	returnMap.put("UIO", userInfoService.getObject(userId));
			   		continue;
			   case ModelType.US :
				   	returnMap.put("US", userSkillService.getObject(userId));
			   		continue;
			   case ModelType.UWH :
				   	returnMap.put("UWH",userWorkHistoryService.getObjectsByUserId(userId));
			   		continue;
			}
		}
		return returnMap;
	}
	/**
	 * 用户信息修改的时候判断哪些是新增的，哪些是删除的，哪些是修改的
	 */
	@Override
	public Boolean updateInfo(Map<Integer, Object> param)
			throws Exception {
		if(param==null) throw new Exception("请求模块参数不能为空！");
		Set<Integer> keys = param.keySet();
		Object entitys = null;
		for(Integer modelType : keys){
			switch(modelType){
			   case ModelType.UB :
			   		entitys =  param.get(modelType);
			   		UserBasic userBasic = (UserBasic)entitys;
			   		if(userBasicService.getObject(userBasic.getUserId())!=null){
			   			userBasicService.updateObject(userBasic);
			   		}else{
			   			userBasicService.createObject(userBasic);
			   		}
			   		continue;
			   case ModelType.UC :
			   		entitys =  param.get(modelType);
			   		updateUserContact(entitys);
			   		continue;
			   case ModelType.UD :
			   		entitys =  param.get(modelType);
			   		updateDefined(entitys);
			   		continue;
			   case ModelType.UDN :
			   		entitys =  param.get(modelType);
			   		UserDescription userDescription = (UserDescription)entitys;
			   		if(userDescriptionService.getObject(userDescription.getUserId())!=null){
			   			userDescriptionService.updateObject(userDescription);
			   		}else{
			   			userDescriptionService.createObject(userDescription);
			   		}
			   		continue;
			   case ModelType.UEH :
			   		entitys =  param.get(modelType);
			   		updateEducationHistory(entitys);
			   		continue;
			   case ModelType.UFM :
			   		entitys =  param.get(modelType);
			   		updateFamilyMember(entitys);
			   		continue;
			   case ModelType.UIO :
			   		entitys =  param.get(modelType);
			   		UserInfo  userInfo= (UserInfo)entitys;
			   		if(userInfoService.getObject(userInfo.getUserId())!=null){
			   			userInfoService.updateObject(userInfo);
			   		}else{
			   			userInfoService.createObject(userInfo);
			   		}
			   		continue;
			   case ModelType.US :
			   		entitys =  param.get(modelType);
			   		UserSkill  userSkill= (UserSkill)entitys;
			   		if(userSkillService.getObject(userSkill.getUserId())!=null){
			   			userSkillService.updateObject(userSkill);
			   		}else{
			   			userSkillService.createObject(userSkill);
			   		}
			   		continue;
			   case ModelType.UWH :
			   		entitys =  param.get(modelType);
			   		updateWorkHistory(entitys);
			   		continue;
			}
		}
		return true;
	}
	//更新用户联系方式
	private void updateUserContact(Object entitys) throws Exception {
		Map<String,List<UserContact>> userContactMap = (Map<String,List<UserContact>>)entitys;
		List<UserContact> deleteUserContact = userContactMap.get("delete");
		if(deleteUserContact!=null&&deleteUserContact.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(UserContact userContact:deleteUserContact){
				ids.add(userContact.getId());
			}
			userContactService.deleteObjects(ids);
		}
		List<UserContact> updateUserContact = userContactMap.get("update");
		userContactService.updateObjects(updateUserContact);
		List<UserContact> addUserContact = userContactMap.get("add");
		userContactService.createObjects(addUserContact);
	}
	/**
	 * 更新用户自定义
	 * @param entitys
	 * @throws Exception
	 */
	private void updateDefined(Object entitys) throws Exception {
		Map<String,List<UserDefined>> userDefinedMap = (Map<String,List<UserDefined>>)entitys;
		List<UserDefined> deleteUserDefined = userDefinedMap.get("delete");
		if(deleteUserDefined!=null&&deleteUserDefined.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(UserDefined userDefined:deleteUserDefined){
				ids.add(userDefined.getId());
			}
			userDefinedService.deleteObjects(ids);
		}
		List<UserDefined> updateUserDefined = userDefinedMap.get("update");
		userDefinedService.updateObjects(updateUserDefined);
		List<UserDefined> addUserDefined = userDefinedMap.get("add");
		userDefinedService.createObjects(updateUserDefined);
	}
	
	/**
	 * 更新家庭成员
	 * @param entitys
	 * @throws Exception
	 */
	private void updateFamilyMember(Object entitys) throws Exception {
		Map<String,List<UserFamilyMember>> userFamilyMemberMap = (Map<String,List<UserFamilyMember>>)entitys;
		List<UserFamilyMember> deleteUserFamilyMember = userFamilyMemberMap.get("delete");
		if(deleteUserFamilyMember!=null&&deleteUserFamilyMember.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(UserFamilyMember userFamilyMember:deleteUserFamilyMember){
				ids.add(userFamilyMember.getId());
			}
			userFamilyMemberService.deleteObjects(ids);
		}
		List<UserFamilyMember> updateUserFamilyMember = userFamilyMemberMap.get("update");
		userFamilyMemberService.updateObjects(updateUserFamilyMember);
		List<UserFamilyMember> addUserFamilyMember = userFamilyMemberMap.get("add");
		userFamilyMemberService.createObjects(addUserFamilyMember);
	}
	
	/**
	 * 更新家庭教育经历
	 * @param entitys
	 * @throws Exception
	 */
	private void updateEducationHistory(Object entitys) throws Exception {
		Map<String,List<UserEducationHistory>> userEducationHistoryMap = (Map<String,List<UserEducationHistory>>)entitys;
		List<UserEducationHistory> deleteUserEducationHistory = userEducationHistoryMap.get("delete");
		if(deleteUserEducationHistory!=null&&deleteUserEducationHistory.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(UserEducationHistory userEducationHistory:deleteUserEducationHistory){
				ids.add(userEducationHistory.getId());
			}
			userEducationHistoryService.deleteObjects(ids);
		}
		List<UserEducationHistory> updateUserEducationHistory = userEducationHistoryMap.get("update");
		userEducationHistoryService.updateObjects(updateUserEducationHistory);
		List<UserEducationHistory> addUserEducationHistory = userEducationHistoryMap.get("add");
		userEducationHistoryService.createObjects(addUserEducationHistory);
	}
	
	/**
	 * 更新家庭教育经历
	 * @param entitys
	 * @throws Exception
	 */
	private void updateWorkHistory(Object entitys) throws Exception {
		Map<String,List<UserWorkHistory>> userWorkHistoryMap = (Map<String,List<UserWorkHistory>>)entitys;
		List<UserWorkHistory> deleteUserWorkHistory = userWorkHistoryMap.get("delete");
		if(deleteUserWorkHistory!=null&&deleteUserWorkHistory.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(UserWorkHistory userWorkHistory:deleteUserWorkHistory){
				ids.add(userWorkHistory.getId());
			}
			userWorkHistoryService.deleteObjects(ids);
		}
		List<UserWorkHistory> updateUserWorkHistory = userWorkHistoryMap.get("update");
		userWorkHistoryService.updateObjects(updateUserWorkHistory);
		List<UserWorkHistory> addUserUserWorkHistory = userWorkHistoryMap.get("add");
		userWorkHistoryService.createObjects(addUserUserWorkHistory);
	}

	@Override
	public Boolean deleteInfo(Long userId, List<Integer> modelTypes) throws Exception {
		if(modelTypes==null) return true;
		for(Integer modelType : modelTypes){
			switch(modelType){
			   case ModelType.UB :
			   		userBasicService.deleteObject(userId);
			   		continue;
			   case ModelType.UC :
				   	userContactService.deleteObjectsByUserId(userId);
			   		continue;
			   case ModelType.UD :
				   	userDefinedService.deleteObjectsByUserId(userId);
			   		continue;
			   case ModelType.UDN :
			   		userDescriptionService.deleteObject(userId);
			   		continue;
			   case ModelType.UEH :
				    userEducationHistoryService.deleteObjectsByUserId(userId);
			   		continue;
			   case ModelType.UFM :
				   	userFamilyMemberService.deleteObjectsByUserId(userId);
			   		continue;
			   case ModelType.UIO :
			   		userInfoService.deleteObject(userId);
			   		continue;
			   case ModelType.US :
			   		userSkillService.deleteObject(userId);
			   		continue;
			   case ModelType.UWH :
				   	userWorkHistoryService.deleteObjectsByUserId(userId);
			   		continue;
			}
		}
		return true;
	}
}
