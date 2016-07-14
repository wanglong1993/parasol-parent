package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.ywxt.metadata.service.SensitiveWordService;
import com.ginkgocap.ywxt.organ.model.CustomerGroup;
import com.ginkgocap.ywxt.organ.service.CustomerGroupService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.Function;
import com.ginkgocap.ywxt.util.LoggerHelper;
import com.ginkgocap.ywxt.util.Module;

/**
 * 客户分组controller
 * @author hdy
 * @date 2015-3-11
 */
@Controller
@RequestMapping("/customer")
public class OrganGroupController extends BaseController{
	@Resource
	private CustomerGroupService customerGroupService;
	// 敏感词过滤
	@Autowired
	private SensitiveWordService sensitiveWordService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
	/**
	 * 添加客户分组(一级目录父id 0 ,未分组id　-1)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/add.json")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,HttpServletResponse response) {
		
		User user = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			String name = j.getString("name");
                // 敏感词判断
				List<String> listword = sensitiveWordService
						.sensitiveWord(StringUtils.trimToEmpty(name));
				if (listword != null && listword.size() > 0) {
					setSessionAndErr(request, response, "-1", "您输入的内容含有敏感词");
					return returnFailMSGNew("01", "您输入的内容含有敏感词");
				}
			 String parenIdStr = j.getString("parentId");
			 int parentId = isNullOrEmpty(parenIdStr) ==true ? 0 : Integer.valueOf(parenIdStr);
			 boolean sign=customerGroupService.getGroupByNameCreatorIdParentId(StringUtils.trimToEmpty(name), user.getId(), parentId);
			 if(sign){
					setSessionAndErr(request, response, "-1", "目录名称已存在");
					return returnFailMSGNew("01", "目录名称已存在");
				}
			CustomerGroup customerGroup = new CustomerGroup();
			customerGroup.setCreatorId(user.getId());
			customerGroup.setCtime(DateFunc.getDate());
			customerGroup.setName(name);
			customerGroup.setParentId(parentId);
			CustomerGroup inserted = customerGroupService.saveOrUpdate(customerGroup);
			responseDataMap.put("success",true);
			responseDataMap.put("result", inserted);
			Map<String,Object> bsn = customerGroupService.getAllGroup(user.getId());
			responseDataMap.put("categorys", bsn.get("results"));
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		return returnSuccessMSG(responseDataMap);
	}

	/**
	 * 按照id删除分组
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/delete.json")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,HttpServletResponse response)   {
		User user = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				int id=j.optInt("id"); 
				boolean i = customerGroupService.deleteById(id);
				responseDataMap.put("id",id);
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		Map<String,Object> bsn = customerGroupService.getAllGroup(user==null?0:user.getId());
		responseDataMap.put("categorys", bsn.get("results"));
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		logger.info(LoggerHelper.buildInfoMessage(Module.CUSTOMER_GROUP, user==null?0:user.getId(),
				Function.DELETE, model));
		return model;
	}

	/**
	 * 更新客户分组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/update.json")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request,HttpServletResponse response) {
		User user = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
				long id= CommonUtil.optLongFromJSONObject(j, "id");
			    String name = j.optString("name");
				CustomerGroup peopleGroup = customerGroupService.findOne(id);
				boolean sign=customerGroupService.getGroupByNameCreatorIdParentId(StringUtils.trimToEmpty(name), user.getId(), peopleGroup.getParentId());
				if(sign&&!name.equals(peopleGroup.getName())){//与原来的名称不相同
					setSessionAndErr(request, response, "-1", "目录名称已存在");
					return returnFailMSGNew("01", "目录名称已存在");
				}
				peopleGroup.setName(name);
				customerGroupService.saveOrUpdate(peopleGroup);
				responseDataMap.put("success",true);
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		Map<String,Object> bsn = customerGroupService.getAllGroup(user==null?0:user.getId());
		responseDataMap.put("categorys", bsn.get("results"));
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		logger.info(LoggerHelper.buildInfoMessage(Module.CUSTOMER_GROUP, user==null?0:user.getId(),
				Function.UPDATE, model));
		return model;
		
	}

	/**
	 * 获得客户分组列表 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/list.json")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request , HttpServletResponse response)  {
		User user = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				Map<String,Object> bsn = customerGroupService.getAllGroup(user.getId());
				responseDataMap.put("success",true);
				responseDataMap.put("results", bsn.get("results"));
				responseDataMap.put("count", bsn.get("count"));
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**
	 *测试 FIXME
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/test.json")
	@ResponseBody
	public Map<String, Object> test(HttpServletRequest request,HttpServletResponse response) {
		
		User user = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		Map<String,Object> mapPage=new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				int currentPage=j.optInt("index")+1; //默认是0 所以+1
				int pageSize=j.optInt("size");
			    String groupId =  j.getString("groupId");
				responseDataMap.put("success",true);
				
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		
		responseDataMap.put("page",mapPage);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	/**
	 * ajax异步获得客户分组列表 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/web/list.json")
	@ResponseBody
	public Map<String,Object> weblist(HttpServletRequest request , HttpServletResponse response)  {
		User user = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		try{
			if (!isNullOrEmpty(requestJson)) {
			    JSONObject j = JSONObject.fromObject(requestJson);
			    long pid=0;
		        int index=j.optInt("index");
		        int size=j.optInt("size");
		        pid=j.optLong("pid");
				Map<String,Object> map = customerGroupService.findListByParentId(user.getId(), pid, index, size);
				responseDataMap.put("index", index);
				responseDataMap.put("size", size);
				responseDataMap.put("total", map.get("count"));
				responseDataMap.put("list", map.get("bsn"));
				responseDataMap.put("success",true);
		  } else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		  }
		}catch(Exception e){
			logger.error("请求我的目录列表出错，请求参数为json{}=="+requestJson,e);
			return returnFailMSGNew("01", "数据请求异常，请稍后再试");
		}
		
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	/**
	 * 按照ids批量删除分组
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/group/batchDelete.json")
	@ResponseBody
	public Map<String, Object> batchDelete(HttpServletRequest request,HttpServletResponse response)   {
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		String requestJson = "";
		try{
			User user = getUser(request);
			// 获取json参数串
		    requestJson = getJsonParamStr(request);
			if (!isNullOrEmpty(requestJson)) {
			    JSONObject j = JSONObject.fromObject(requestJson);
				JSONArray ja=j.getJSONArray("ids");
				if(ja!=null&&ja.size()>0){
					 for(int i=0;i<ja.size();i++){
						 String id=ja.getString(i);
						 customerGroupService.deleteById(Long.parseLong(id));
					 }
				}
		  } else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		  }
		}catch(Exception e){
			logger.error("请求批量删除目录失败，请求参数json{}=="+requestJson,e);
			return returnFailMSGNew("01", "数据请求失败,请稍后再试");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
}
