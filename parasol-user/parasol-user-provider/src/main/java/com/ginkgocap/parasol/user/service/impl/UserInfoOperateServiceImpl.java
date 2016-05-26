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
import com.ginkgocap.parasol.user.model.UserDefinedTemplate;
import com.ginkgocap.parasol.user.model.UserTemplate;
import com.ginkgocap.parasol.user.model.UserTemplateModel;
import com.ginkgocap.parasol.user.service.UserAttachmentService;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserDefinedService;
import com.ginkgocap.parasol.user.service.UserDefinedTemplateService;
import com.ginkgocap.parasol.user.service.UserDescriptionService;
import com.ginkgocap.parasol.user.service.UserEducationHistoryService;
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
	private UserDefinedTemplateService userDefinedTemplateService;
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
	private UserTemplateModelService userTemplateModelService;
	@Resource
	private UserTemplateService userTemplateService;
	@Resource
	private UserWorkHistoryService userWorkHistoryService;
	//用户自定义
	public final static String USERDIFINE="UD";
	//系统默认
	public final static String DEFAULT = "OS";
	
	
	@Override
	public boolean saveInfo(Map<Integer, Map<String, Object>> param)
			throws Exception {
		if(param==null) throw new Exception("请求模块参数不能为空！");
		Set<Integer> keys = param.keySet();
//		Map<String, Object> entitys =
		for(Integer modelType : keys){
			switch(modelType){
			   case ModelType.UB :
			   		Map<String, Object> entitys =  param.get(modelType);
			   		UserBasic ub= (UserBasic)BeanUtil.map2bean(entitys, UserBasic.class);
			   		userBasicService.createObject(ub);
			   		break;
			   case ModelType.UC :
			   		entitys =  param.get(modelType);
//			   		UserBasic ub= (UserBasic)BeanUtil.map2bean(entitys, UserBasic.class);
//			   		userBasicService.createObject(ub);
			   		break;
			
			}
		}
		return true;
	}

	@Override
	public Map<ModelType, Map<String, Object>> getInfo(Long userId,
			ModelType[] types) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateInfo(Map<ModelType, Map<String, Object>> param)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteInfo(Long userId, ModelType[] types) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Map>> getTemplates(Long userId)
			throws Exception {
		Map<String, List<Map>> returnMap = new HashMap<String, List<Map>>();
		//获取系统
		List<UserTemplate> templates = userTemplateService.getObjectsByUserId(0l);
		//获取用户自定义
		List<UserDefinedTemplate> definedTemplates = userDefinedTemplateService.getObjectsByUserId(userId);
		List<String> fields = new ArrayList<String>();
		fields.add("modelText");
		returnMap.put(USERDIFINE, BeanUtil.getMaps(definedTemplates,fields));
		returnMap.put(DEFAULT, BeanUtil.getMaps(templates));
		return returnMap;
	}

	@Override
	public Map<String, List<Map>> getModelByTemplateId(Long templateId, String type)
			throws Exception {
		Map<String, List<Map>> returnMap = new HashMap<String, List<Map>>();
		if(USERDIFINE.equals(type)){
			UserDefinedTemplate userDefinedTemplate = userDefinedTemplateService.getObject(templateId);
			if(userDefinedTemplate==null) throw new Exception("自定义模板不存在！");
			Long template_id = userDefinedTemplate.getTemplate_id();
			List<UserTemplateModel>  models = userTemplateModelService.getObjectsByTemplateId(template_id);
			returnMap.put(DEFAULT,BeanUtil.getMaps(models));
			List<Map> list = new ArrayList<Map>();
			list.add(BeanUtil.bean2map(userDefinedTemplate));
			returnMap.put(USERDIFINE, list);
		}else if(DEFAULT.equals(type)){
			List<UserTemplateModel>  models = userTemplateModelService.getObjectsByTemplateId(templateId);
			returnMap.put(DEFAULT,BeanUtil.getMaps(models));
		}
		return returnMap;
	}
	
}
