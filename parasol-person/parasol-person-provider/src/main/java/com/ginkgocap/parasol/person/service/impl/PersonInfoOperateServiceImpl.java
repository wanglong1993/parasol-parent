package com.ginkgocap.parasol.person.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.person.model.PersonAttachment;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonContact;
import com.ginkgocap.parasol.person.model.PersonDefined;
import com.ginkgocap.parasol.person.model.PersonDescription;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.model.PersonFamilyMember;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.model.PersonSkill;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonAttachmentService;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonDefinedService;
import com.ginkgocap.parasol.person.service.PersonDescriptionService;
import com.ginkgocap.parasol.person.service.PersonEducationHistoryService;
import com.ginkgocap.parasol.person.service.PersonFamilyMemberService;
import com.ginkgocap.parasol.person.service.PersonInfoOperateService;
import com.ginkgocap.parasol.person.service.PersonInfoService;
import com.ginkgocap.parasol.person.service.PersonInterestingService;
import com.ginkgocap.parasol.person.service.PersonSkillService;
import com.ginkgocap.parasol.person.service.PersonWorkHistoryService;
import com.ginkgocap.parasol.user.model.ModelType;
@Service("personInfoOperateService")
public class PersonInfoOperateServiceImpl implements PersonInfoOperateService {
	@Resource
	private PersonBasicService personBasicService;
	@Resource
	private PersonAttachmentService personAttachmentService;
	@Resource
	private PersonContactServiceImpl personContactService;
	@Resource 
	private PersonDefinedService personDefinedService;
	@Resource
	private PersonFamilyMemberService personFamilyMemberService;
	@Resource 
	private PersonDescriptionService personDescriptionService;
	@Resource
	private PersonEducationHistoryService personEducationHistoryService;
	@Resource
	private PersonInfoService personInfoService;
	@Resource
	private PersonInterestingService personInterestingService;
	@Resource
	private PersonSkillService personSkillService;
	@Resource
	private PersonWorkHistoryService personWorkHistoryService;

	@Override
	public boolean saveInfo(Map<Integer, Object> param)
			throws Exception {
		if(param==null) throw new Exception("请求模块参数不能为空！");
		Set<Integer> keys = param.keySet();
		Object entitys = null;
		for(Integer modelType : keys){
			switch(modelType){
			   case ModelType.UA :
		   		entitys =  param.get(modelType);
		   		PersonAttachment personAttachment = (PersonAttachment)entitys;
		   		personAttachmentService.createObject(personAttachment);
		   		continue;
			   case ModelType.UC :
			   		entitys =  param.get(modelType);
			   		List<PersonContact> personContacts = (List<PersonContact>)entitys;
			   		personContactService.createObjects(personContacts);
			   		continue;
			   case ModelType.UD :
			   		entitys =  param.get(modelType);
			   		List<PersonDefined> personDefineds = (List<PersonDefined>)entitys;
			   		personDefinedService.createObjects(personDefineds);
			   		continue;
			   case ModelType.UDN :
			   		entitys =  param.get(modelType);
			   		PersonDescription personDescription = (PersonDescription)entitys;
			   		personDescriptionService.createObject(personDescription);
			   		continue;
			   case ModelType.UEH :
			   		entitys =  param.get(modelType);
			   		List<PersonEducationHistory> personEducationHistorys = (List<PersonEducationHistory>)entitys;
			   		personEducationHistoryService.createObjects(personEducationHistorys);
			   		continue;
			   case ModelType.UFM :
			   		entitys =  param.get(modelType);
			   		List<PersonFamilyMember>  personFamilyMembers= (List<PersonFamilyMember>)entitys;
			   		personFamilyMemberService.createObjects(personFamilyMembers);
			   		continue;
			   case ModelType.UIO :
			   		entitys =  param.get(modelType);
			   		PersonInfo  personInfo= (PersonInfo)entitys;
			   		personInfoService.createObject(personInfo);
			   		continue;
			   case ModelType.US :
			   		entitys =  param.get(modelType);
			   		PersonSkill  personSkill= (PersonSkill)entitys;
			   		personSkillService.createObject(personSkill);
			   		continue;
			   case ModelType.UWH :
			   		entitys =  param.get(modelType);
			   		List<PersonWorkHistory> personWorkHistorys= (List<PersonWorkHistory>)entitys;
			   		personWorkHistoryService.createObjects(personWorkHistorys);
			   		continue;
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> getInfo(Long personId,
			Integer[] models) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(personId==null) throw new Exception("用户ID不能为空！");
		for(Integer modelType:models){
			switch(modelType){
			   case ModelType.UA :
		   			 returnMap.put("UA", personAttachmentService.getObject(personId));
		   			 continue;
			   case ModelType.UB :
			   		returnMap.put("UB", personBasicService.getObject(personId));
			   		continue;
			   case ModelType.UC :
				   	returnMap.put("UC",personContactService.getObjectsByPersonId(personId));
			   		continue;
			   case ModelType.UD :
					returnMap.put("UD",personDefinedService.getObjectsByPersonId(personId));
			   		continue;
			   case ModelType.UDN :
				   	returnMap.put("UDN", personDescriptionService.getObject(personId));
			   		continue;
			   case ModelType.UEH :
				   	returnMap.put("UEH",personEducationHistoryService.getObjectsByPersonId(personId));
			   		continue;
			   case ModelType.UFM :
				   	returnMap.put("UFM",personFamilyMemberService.getObjectsByPersonId(personId));
			   		continue;
			   case ModelType.UIO :
				   	returnMap.put("UIO", personInfoService.getObject(personId));
			   		continue;
			   case ModelType.US :
				   	returnMap.put("US", personSkillService.getObject(personId));
			   		continue;
			   case ModelType.UWH :
				   	returnMap.put("UWH",personWorkHistoryService.getObjectsByPersonId(personId));
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
			  case ModelType.UA :
			   		entitys =  param.get(modelType);
			   		PersonAttachment personAttachment = (PersonAttachment)entitys;
			   		if(personAttachmentService.getObject(personAttachment.getPersonId())!=null){
			   			personAttachmentService.updateObject(personAttachment);
			   		}else{
			   			personAttachmentService.createObject(personAttachment);
			   		}
			   		continue;
			   case ModelType.UB :
			   		entitys =  param.get(modelType);
			   		PersonBasic personBasic = (PersonBasic)entitys;
			   		if(personBasicService.getObject(personBasic.getId())!=null){
			   			personBasicService.updateObject(personBasic);
			   		}else{
			   			personBasicService.createObject(personBasic);
			   		}
			   		continue;
			   case ModelType.UC :
			   		entitys =  param.get(modelType);
			   		updatePersonContact(entitys);
			   		continue;
			   case ModelType.UD :
			   		entitys =  param.get(modelType);
			   		updateDefined(entitys);
			   		continue;
			   case ModelType.UDN :
			   		entitys =  param.get(modelType);
			   		PersonDescription personDescription = (PersonDescription)entitys;
			   		if(personDescriptionService.getObject(personDescription.getPersonId())!=null){
			   			personDescriptionService.updateObject(personDescription);
			   		}else{
			   			personDescriptionService.createObject(personDescription);
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
			   		PersonInfo  personInfo= (PersonInfo)entitys;
			   		if(personInfoService.getObject(personInfo.getPersonId())!=null){
			   			personInfoService.updateObject(personInfo);
			   		}else{
			   			personInfoService.createObject(personInfo);
			   		}
			   		continue;
			   case ModelType.US :
			   		entitys =  param.get(modelType);
			   		PersonSkill  personSkill= (PersonSkill)entitys;
			   		if(personSkillService.getObject(personSkill.getPersonId())!=null){
			   			personSkillService.updateObject(personSkill);
			   		}else{
			   			personSkillService.createObject(personSkill);
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
	private void updatePersonContact(Object entitys) throws Exception {
		Map<String,List<PersonContact>> personContactMap = (Map<String,List<PersonContact>>)entitys;
		List<PersonContact> deletePersonContact = personContactMap.get("delete");
		if(deletePersonContact!=null&&deletePersonContact.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(PersonContact personContact:deletePersonContact){
				ids.add(personContact.getId());
			}
			personContactService.deleteObjects(ids);
		}
		List<PersonContact> updatePersonContact = personContactMap.get("update");
		personContactService.updateObjects(updatePersonContact);
		List<PersonContact> addPersonContact = personContactMap.get("add");
		personContactService.createObjects(addPersonContact);
	}
	/**
	 * 更新用户自定义
	 * @param entitys
	 * @throws Exception
	 */
	private void updateDefined(Object entitys) throws Exception {
		Map<String,List<PersonDefined>> personDefinedMap = (Map<String,List<PersonDefined>>)entitys;
		List<PersonDefined> deletePersonDefined = personDefinedMap.get("delete");
		if(deletePersonDefined!=null&&deletePersonDefined.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(PersonDefined personDefined:deletePersonDefined){
				ids.add(personDefined.getId());
			}
			personDefinedService.deleteObjects(ids);
		}
		List<PersonDefined> updatePersonDefined = personDefinedMap.get("update");
		personDefinedService.updateObjects(updatePersonDefined);
		List<PersonDefined> addPersonDefined = personDefinedMap.get("add");
		personDefinedService.createObjects(addPersonDefined);
	}
	
	/**
	 * 更新家庭成员
	 * @param entitys
	 * @throws Exception
	 */
	private void updateFamilyMember(Object entitys) throws Exception {
		Map<String,List<PersonFamilyMember>> personFamilyMemberMap = (Map<String,List<PersonFamilyMember>>)entitys;
		List<PersonFamilyMember> deletePersonFamilyMember = personFamilyMemberMap.get("delete");
		if(deletePersonFamilyMember!=null&&deletePersonFamilyMember.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(PersonFamilyMember personFamilyMember:deletePersonFamilyMember){
				ids.add(personFamilyMember.getId());
			}
			personFamilyMemberService.deleteObjects(ids);
		}
		List<PersonFamilyMember> updatePersonFamilyMember = personFamilyMemberMap.get("update");
		personFamilyMemberService.updateObjects(updatePersonFamilyMember);
		List<PersonFamilyMember> addPersonFamilyMember = personFamilyMemberMap.get("add");
		personFamilyMemberService.createObjects(addPersonFamilyMember);
	}
	
	/**
	 * 更新家庭教育经历
	 * @param entitys
	 * @throws Exception
	 */
	private void updateEducationHistory(Object entitys) throws Exception {
		Map<String,List<PersonEducationHistory>> personEducationHistoryMap = (Map<String,List<PersonEducationHistory>>)entitys;
		List<PersonEducationHistory> deletePersonEducationHistory = personEducationHistoryMap.get("delete");
		if(deletePersonEducationHistory!=null&&deletePersonEducationHistory.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(PersonEducationHistory personEducationHistory:deletePersonEducationHistory){
				ids.add(personEducationHistory.getId());
			}
			personEducationHistoryService.deleteObjects(ids);
		}
		List<PersonEducationHistory> updatePersonEducationHistory = personEducationHistoryMap.get("update");
		personEducationHistoryService.updateObjects(updatePersonEducationHistory);
		List<PersonEducationHistory> addPersonEducationHistory = personEducationHistoryMap.get("add");
		personEducationHistoryService.createObjects(addPersonEducationHistory);
	}
	
	/**
	 * 更新家庭教育经历
	 * @param entitys
	 * @throws Exception
	 */
	private void updateWorkHistory(Object entitys) throws Exception {
		Map<String,List<PersonWorkHistory>> personWorkHistoryMap = (Map<String,List<PersonWorkHistory>>)entitys;
		List<PersonWorkHistory> deletePersonWorkHistory = personWorkHistoryMap.get("delete");
		if(deletePersonWorkHistory!=null&&deletePersonWorkHistory.size()>0){
			List<Long> ids = new ArrayList<Long>();
			for(PersonWorkHistory personWorkHistory:deletePersonWorkHistory){
				ids.add(personWorkHistory.getId());
			}
			personWorkHistoryService.deleteObjects(ids);
		}
		List<PersonWorkHistory> updatePersonWorkHistory = personWorkHistoryMap.get("update");
		personWorkHistoryService.updateObjects(updatePersonWorkHistory);
		List<PersonWorkHistory> addPersonPersonWorkHistory = personWorkHistoryMap.get("add");
		personWorkHistoryService.createObjects(addPersonPersonWorkHistory);
	}

	@Override
	public Boolean deleteInfo(Long personId, List<Integer> modelTypes) throws Exception {
		if(modelTypes==null) return true;
		for(Integer modelType : modelTypes){
			switch(modelType){
			   case ModelType.UB :
			   		personBasicService.deleteObject(personId);
			   		continue;
			   case ModelType.UC :
				   	personContactService.deleteObjectsByPersonId(personId);
			   		continue;
			   case ModelType.UD :
				   	personDefinedService.deleteObjectsByPersonId(personId);
			   		continue;
			   case ModelType.UDN :
			   		personDescriptionService.deleteObject(personId);
			   		continue;
			   case ModelType.UEH :
				    personEducationHistoryService.deleteObjectsByPersonId(personId);
			   		continue;
			   case ModelType.UFM :
				   	personFamilyMemberService.deleteObjectsByPersonId(personId);
			   		continue;
			   case ModelType.UIO :
			   		personInfoService.deleteObject(personId);
			   		continue;
			   case ModelType.US :
			   		personSkillService.deleteObject(personId);
			   		continue;
			   case ModelType.UWH :
				   	personWorkHistoryService.deleteObjectsByPersonId(personId);
			   		continue;
			}
		}
		return true;
	}
}
