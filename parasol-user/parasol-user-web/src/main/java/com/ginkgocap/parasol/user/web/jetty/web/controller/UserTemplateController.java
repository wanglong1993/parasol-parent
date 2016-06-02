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
	@ResponseBody
	@RequestMapping(path = { "/user/user/choiceTemplate" }, method = { RequestMethod.GET })
	public Map<String,Object> choiceTemplate(@RequestParam long templateId) {
		Long userId = LoginUserContextHolder.getUserId();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(templateId==0){
			returnMap.put("status", 0);
			returnMap.put("message", "templateId is not null");
			return returnMap;
		}
		try {
			userTemplateOpenService.selectUserTemplate(userId, templateId);
		} catch (Exception e) {
			returnMap.put("status", 0);
			returnMap.put("message", "选定模板错误！");
			return returnMap;
		}
		returnMap.put("status", 1);
		returnMap.put("message", "选定模板成功！");
		return returnMap;
	}
	/**
	 *查询用户所有模板
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = { "/user/user/selectTemplate" }, method = { RequestMethod.GET })
	public Map<String,Object> selectTemplate() {
		Long userId = LoginUserContextHolder.getUserId();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			Map<String, List<Map>> templates = userTemplateOpenService.getTemplates(userId);
			returnMap.put("data", templates);
		} catch (Exception e) {
			returnMap.put("status", 0);
			returnMap.put("message", "服务器内部错误！");
			return returnMap;
		}
		returnMap.put("status", 1);
		return returnMap;
	}
	/**
	 * 根据模板的id获取模板内容，系统模板只获取木块信息，自定义模板获取字段和模板信息
	 * @param type OS为心痛默认模板，UD为用户自定义模板
	 * @param templateId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = { "/user/user/selectTemplateContext" }, method = { RequestMethod.GET })
	public Map<String,Object> selectTemplateContext(@RequestParam(name = "type",required = true) String type,@RequestParam(name = "templateId",required = true) long templateId ) {
		Long userId = LoginUserContextHolder.getUserId();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			Map<String, List<Map>> templates = userTemplateOpenService.getModelByTemplateId(templateId, type);
			returnMap.put("data", templates);
		} catch (Exception e) {
			returnMap.put("status", 0);
			returnMap.put("message", "服务器内部错误！");
			return returnMap;
		}
		returnMap.put("status", 1);
		return returnMap;
	}
}
