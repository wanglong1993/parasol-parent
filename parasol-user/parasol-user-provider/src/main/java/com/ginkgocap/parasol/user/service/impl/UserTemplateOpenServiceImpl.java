package com.ginkgocap.parasol.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.user.model.UserDefinedTemplate;
import com.ginkgocap.parasol.user.model.UserRTemplate;
import com.ginkgocap.parasol.user.model.UserTemplate;
import com.ginkgocap.parasol.user.model.UserTemplateModel;
import com.ginkgocap.parasol.user.service.UserDefinedTemplateService;
import com.ginkgocap.parasol.user.service.UserRTemplateService;
import com.ginkgocap.parasol.user.service.UserTemplateModelService;
import com.ginkgocap.parasol.user.service.UserTemplateOpenService;
import com.ginkgocap.parasol.user.service.UserTemplateService;
import com.ginkgocap.parasol.user.utils.BeanUtil;
@Service("userTemplateOpenService")
public class UserTemplateOpenServiceImpl implements UserTemplateOpenService {
	@Resource
	private UserTemplateModelService userTemplateModelService;
	@Resource
	private UserTemplateService userTemplateService;
	@Resource
	private UserDefinedTemplateService userDefinedTemplateService;
	@Resource
	private UserRTemplateService userRtemplateService;
	//用户自定义
	public final static String USERDIFINE="UD";
	//系统默认
	public final static String DEFAULT = "OS";
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

	@Override
	public void selectUserTemplate(Long userId, Long templateId)
			throws Exception {
		if(userId==null)
			throw new Exception("userId is null");
		if(templateId==null) 
			throw new Exception("templateId is null");
		UserRTemplate userRTemplate = new UserRTemplate();
		userRTemplate.setTemplateId(templateId);
		userRTemplate.setUserId(userId);
		if(userRtemplateService.getObject(userId)!=null){
			userRtemplateService.updateObject(userRTemplate);
		}else{
			userRtemplateService.createObject(userRTemplate);
		}
		
	}
	
}
