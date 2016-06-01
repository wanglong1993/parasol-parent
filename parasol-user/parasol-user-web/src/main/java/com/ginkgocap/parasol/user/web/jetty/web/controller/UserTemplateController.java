package com.ginkgocap.parasol.user.web.jetty.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.user.service.UserTemplateOpenService;

@Controller
public class UserTemplateController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserTemplateController.class);
	@Autowired
	private UserTemplateOpenService userTemplateOpenService;
	/**
	 * 创建用户信息 选定一个模板
	 * @param templateId
	 * @return
	 */
	@RequestMapping(path = { "/user/user/choiceTemplate" }, method = { RequestMethod.GET })
	public MappingJacksonValue choiceTemplate(@RequestParam long templateId) {
		Long userId = LoginUserContextHolder.getUserId();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(templateId==0){
			returnMap.put("status", 0);
			returnMap.put("message", "templateId is not null");
			return new MappingJacksonValue(returnMap);
		}
		try {
			userTemplateOpenService.selectUserTemplate(userId, templateId);
		} catch (Exception e) {
			returnMap.put("status", 0);
			returnMap.put("message", "选定模板错误！");
			return new MappingJacksonValue(returnMap);
		}
		returnMap.put("status", 1);
		returnMap.put("message", "选定模板成功！");
		return new MappingJacksonValue(returnMap);
	}
	
	@RequestMapping(path = { "/user/user/selectTemplate" }, method = { RequestMethod.GET })
	public MappingJacksonValue selectTemplate() {
		Long userId = LoginUserContextHolder.getUserId();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			Map<String, List<Map>> templates = userTemplateOpenService.getTemplates(userId);
			returnMap.put("data", templates);
		} catch (Exception e) {
			returnMap.put("status", 0);
			returnMap.put("message", "服务器内部错误！");
			return new MappingJacksonValue(returnMap);
		}
		returnMap.put("status", 1);
		return new MappingJacksonValue(returnMap);
	}
	
	@RequestMapping(path = { "/user/user/selectTemplateContext" }, method = { RequestMethod.GET })
	public MappingJacksonValue selectTemplateContext(@RequestParam(name = "type",required = true) String type,@RequestParam(name = "templateId",required = true) long templateId ) {
		Long userId = LoginUserContextHolder.getUserId();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			Map<String, List<Map>> templates = userTemplateOpenService.getModelByTemplateId(templateId, type);
			returnMap.put("data", templates);
		} catch (Exception e) {
			returnMap.put("status", 0);
			returnMap.put("message", "服务器内部错误！");
			return new MappingJacksonValue(returnMap);
		}
		returnMap.put("status", 1);
		return new MappingJacksonValue(returnMap);
	}
}
