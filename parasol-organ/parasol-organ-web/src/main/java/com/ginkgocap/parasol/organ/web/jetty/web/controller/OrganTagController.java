package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.organ.web.jetty.web.service.OrganTagService;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.ValidateTagUtil;
import com.ginkgocap.ywxt.metadata.model.UserTag;
import com.ginkgocap.ywxt.metadata.service.userTag.UserTagService;
import com.ginkgocap.ywxt.organ.service.tag.RCustomerTagService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.PageUtil;

/**
 * 客户标签controller
 * @author hdy
 * @date 2015-3-12
 */
@Controller
@RequestMapping("/customer")
public class OrganTagController extends BaseController{
	@Resource
	private RCustomerTagService rCustomerTagService;
	@Resource
    private UserTagService userTagService;
	@Resource
	OrganTagService organTagService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
	/**
	 * 获得客户标签分组列表 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tag/group.json")
	@ResponseBody
	public Map<String,Object> group(HttpServletRequest request , HttpServletResponse response) {
		
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
				pageSize = pageSize == 0 ? 20 : pageSize;
				Map<String, Object> bsn =rCustomerTagService.getAllTagGroupByPage(user.getId(), currentPage, pageSize);
				PageUtil pageUtil= (PageUtil) bsn.get("page");
				int total=pageUtil.getCount();
			    mapPage.put("index",j.optInt("index"));  
			    mapPage.put("size",pageSize);
			    mapPage.put("total",total);
				mapPage.put("list", bsn.get("results"));
				responseDataMap.put("success",true);
				responseDataMap.put("page", mapPage);			
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
	 * 获得客户标签添加
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tag/add.json")
	@ResponseBody
	public Map<String,Object> add(HttpServletRequest request , HttpServletResponse response) {
		
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
			    String tagName =  j.getString( "tag");
			    
			    //标签后台校验
			    try {
			    	ValidateTagUtil.validateTag(tagName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					responseDataMap.put("success",false);
					responseDataMap.put("msg", e.getMessage());
					notificationMap.put("notifCode", "0001");
					notificationMap.put("notifInfo", "hello mobile app!");
					model.put("responseData", responseDataMap);
					model.put("notification", notificationMap);
					return model;	
				}
				UserTag userTag = organTagService.save(tagName, user.getId());
				responseDataMap.put("success",true);
				Map<String,Object> tag  = new HashMap<String,Object>();
				tag.put("id", userTag.getTagId());
				tag.put("name", userTag.getTagName());
				responseDataMap.put("obj", tag);
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
	 * 标签删除(只删除关系)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tag/delete.json")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request , HttpServletResponse response) {
		
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
			    String tagId =  j.getString( "id");
				 rCustomerTagService.deleteByTagId(Long.valueOf(tagId),user.getId());
				responseDataMap.put("success",true);
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
	 * 标签批量删除(只删除关系)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tag/batchDelete.json")
	@ResponseBody
	public Map<String,Object> batchDelete(HttpServletRequest request , HttpServletResponse response) {
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		String requestJson = "";
		try {
			User user = getUser(request);
			requestJson = getJsonParamStr(request);
			if (!isNullOrEmpty(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				     JSONArray ja=j.getJSONArray("tagIds");
				     if(ja!=null&&ja.size()>0){
				    	 for(int i=0;i<ja.size();i++){
				    		 String tagId=ja.getString(i);
				    		 rCustomerTagService.deleteByTagId(Long.valueOf(tagId),user.getId());
				    	 }
				     }
					responseDataMap.put("success",true);
			} else {
				setSessionAndErr(request, response, "-1", "输入参数不合法");
			}
		} catch (IOException e) {
			logger.error("请求批量删除标签失败，请求参数为json{}==="+requestJson,e);
			return returnFailMSGNew("01", "请求数据异常，请稍后再试");
		}
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}


}
