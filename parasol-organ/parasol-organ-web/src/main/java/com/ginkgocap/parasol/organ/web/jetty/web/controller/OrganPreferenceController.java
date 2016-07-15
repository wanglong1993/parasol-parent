package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.ywxt.user.model.User;
import com.gintong.ywxt.organization.model.CustomerPreference;
import com.gintong.ywxt.organization.service.CustomerPreferenceService;

/**
* <p>Title: OrganPreferenceController.java<／p> 
* <p>Description:组织偏好设置controller <／p> 
* @author wfl
* @date 2015-12-5 
* @version 1.0
 */
@Controller
@RequestMapping("/organ")
public class OrganPreferenceController extends BaseController {

	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	 @Resource
    private CustomerPreferenceService  customerPreferenceService;
	 
	    /**
	     * 添加自定义偏好标签
	     * @author wfl
	     */
	    @ResponseBody
		@RequestMapping(value = "/preference/save.json", method = RequestMethod.POST)
		public Map<String, Object> preferenceSave(HttpServletRequest request,HttpServletResponse response) throws IOException {
			String requestJson = "";
			requestJson = getJsonParamStr(request);
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			if (requestJson != null && !"".equals(requestJson)) {
				try {
					JSONObject jo = JSONObject.fromObject(requestJson);
					User user=getUser(request);
					long userId = user.getId();
				    String name=jo.optString("name");
				    if(customerPreferenceService.isExist(name, userId)) {
				    	return returnFailMSGNew("-1",  "您的自定义标签已经存在");
				    }
				    CustomerPreference cp=new CustomerPreference();
				    cp.setType(2);
				    cp.setName(name);
				    cp.setCreateId(userId);
				    long preferenceId = customerPreferenceService.saveCustomerPreference(cp);
				    responseDataMap.put("id", preferenceId);
				} catch (Exception e) {
					setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
					logger.error("添加自定义偏好标签报错,请求参数:{}"+requestJson,e);
					return returnFailMSGNew("01", "系统异常,请稍后再试");
				}
			} else {
				setSessionAndErr(request, response, "-1", "非法操作！");
				return returnFailMSGNew("01", "非法操作！");
			}
			return returnSuccessMSG(responseDataMap);
		}
	    
		@ResponseBody
		@RequestMapping(value = "/preference/delete.json", method = RequestMethod.POST)
		public Map<String, Object> preferenceDelete(HttpServletRequest request, HttpServletResponse response) {

			logger.info("into /preference/delete.json");
			
			/** 获取json参数串 */
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			JSONObject j = null;
			boolean result = false;
			try {
				j = JSONObject.fromObject(getJsonParamStr(request));
				long preferenceId = j.optLong("preferenceId");
				if (preferenceId != 0) {
					result = customerPreferenceService.deleteCustomerPreference(preferenceId);
				}
				if(!result) {
					returnFailMSGNew("01", "非法操作！");
				}
			} catch (Exception e) {
				logger.error("/preference/delete.json 参数读取异常");
				returnFailMSGNew("01", "非法操作！");
			}
			return returnSuccessMSG(responseDataMap);
		}
		
		@ResponseBody
		@RequestMapping(value = "/preference/find.json", method = RequestMethod.POST)
		public Map<String, Object> preferenceFind(HttpServletRequest request, HttpServletResponse response) {

			logger.info("into /preference/find.json");
			
			/** 获取json参数串 */
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			JSONObject j = null;
			Map<String, Object> customerPreferences = null;
			try {
				
				j = JSONObject.fromObject(getJsonParamStr(request));
				
				int currentPage = j.optInt("index",1);
				int pageSize = j.optInt("page",20);
				currentPage = currentPage < 1000 ? currentPage : 1000;
				pageSize = pageSize < 100 ? pageSize : 100;
				
				User user = this.getUser(request);
				
				customerPreferences = customerPreferenceService.fingByParam(user.getId(), currentPage, pageSize);
				
			} catch (Exception e) {
				logger.error("/preference/find.json 参数读取异常");
			}
			responseDataMap.putAll(customerPreferences);
			return genRespBody(responseDataMap, null);
		}
}
