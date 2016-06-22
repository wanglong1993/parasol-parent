package com.ginkgocap.parasol.organ.web.jetty.web.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.user.model.UserBasic;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagService;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.ywxt.organ.model.Constants;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.CustomerCollect;
import com.ginkgocap.ywxt.organ.model.CustomerTag;
import com.ginkgocap.ywxt.organ.model.SimpleCustomer;
import com.ginkgocap.ywxt.organ.service.CustomerCollectService;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.organ.service.SimpleCustomerService;
import com.ginkgocap.ywxt.organ.service.tag.RCustomerTagService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.JsonUtil;
import com.ginkgocap.ywxt.util.PageUtil;
import com.gintong.common.phoenix.permission.entity.PermissionQuery;


/**
 * 我的组织客户
 * @author hdy
 * @date 2015-3-10
 */
@Controller
@RequestMapping("/organ")
public class OrgAndCustomerController  extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SimpleCustomerService simpleCustomerService;
	@Resource
	private RCustomerTagService rCustomerTagService;
	@Resource
	private CustomerService customerService;
	@Resource
	private ResourcePathExposer resourcePathExposer;
	@Resource
	private CustomerCollectService customerCollectService;
	@Autowired
	private TagSourceService tagSourceService;
	@Autowired
	private TagService tagService;
	@Autowired
	private DirectorySourceService directorySourceService;

	/**
	 * 我的组织客户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/getCustomer", method = RequestMethod.POST)
	public Map<String, Object> getCustomer(HttpServletRequest request,
			HttpServletResponse response) {
		UserBasic user = getUser(request);
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
				int currentPage=j.optInt("index"); //默认currentPage为1
				int pageSize=j.optInt("size");
				pageSize = pageSize == 0 ? 20 : pageSize;
			    String type=j.optString("type");//1创建的客户，2收藏的客户
			    String keyword=j.optString("keyword");
			    String ordername=j.optString("ordername");//排序字段
			    String ordertype=j.optString("ordertype");//排序方式
			    if(type=="" || type==null){
			    	type="1";
			    }
			    if(ordername=="" || ordername==null){
			    	ordername="ctime";
			    }
			    if(ordertype=="" || ordertype==null){
			    	ordertype="desc";
			    }
                //按条件查询
			    Map<String,Object>   map=new HashMap<String,Object>();
		    	map.put("start", (currentPage-1)*pageSize);
		    	map.put("end", pageSize);
		    	map.put("keyword", keyword);
		    	map.put("ordername", ordername);
		    	map.put("ordertype", ordertype);
			    List<SimpleCustomer> simpleCustomerList = null;
				if("1".equals(type)){
			    	 map.put("userid", user.getUserId());
			    	 simpleCustomerList=simpleCustomerService.selectUserid(map);
			    	 int count = simpleCustomerService.selectUseridCount(map);
			    	 PageUtil page = new PageUtil((int)count, currentPage, pageSize);
			    	 responseDataMap.put("simpleCustomerList", simpleCustomerList);
			    	 responseDataMap.put("page", page);	
			    }else if("2".equals(type)){
			    	 List<Long> customerIds=customerCollectService.getCustomerIdsByParam(user.getUserId(), 0);
			    	 if(customerIds==null || customerIds.isEmpty()){ 
			    		 responseDataMap.put("simpleCustomerList", simpleCustomerList);
			    	 }else{
			    	 map.put("list", customerIds);
			    	 simpleCustomerList=simpleCustomerService.selectId(map);
			    	 int count = simpleCustomerService.selectIdCount(map);
			    	 PageUtil page = new PageUtil((int)count, currentPage, pageSize);
			    	 responseDataMap.put("page", page);	
			    	 responseDataMap.put("simpleCustomerList", simpleCustomerList);
			    	 } 
			    }else{
			    	setSessionAndErr(request, response, "-1", "输入参数不合法");
			    }
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**
	 * 修改组织客户的目录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/updateCustomerDirectory", method = RequestMethod.POST)
	public Map<String, Object> updateCustomerTags(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserBasic user = getUser(request);
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
			    String custormId=j.optString("custormId");//需要修改添加标签目录的客户id
			    List<Long> directoryIds = JsonUtil.getList(j, "directoryIds", Long.class);
			    Long appId = 1l;
			    Long ctime = System.currentTimeMillis();
			    //删除以前的
			    directorySourceService.removeDirectorySourcesBySourceId(user.getUserId(), appId, 1, Long.valueOf(custormId));
			    if(!directoryIds.isEmpty()){
			    	for (Long directoryId : directoryIds) {
			    		DirectorySource directorySource = new DirectorySource();
						directorySource.setDirectoryId(directoryId);
						directorySource.setAppId(appId);
						directorySource.setUserId(user.getUserId());
						directorySource.setSourceId(Long.valueOf(custormId));
						directorySource.setSourceType(1);//
						directorySource.setCreateAt(ctime);
						directorySourceService.createDirectorySources(directorySource);
					}
			    }
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	
	/**
	 * 修改组织客户的标签
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/updateCustomerTags", method = RequestMethod.POST)
	public Map<String, Object> updateCustomerDirectory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserBasic user = getUser(request);
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
		Long appId = 1l; //还需做修改
		Long ctime = System.currentTimeMillis();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				List<Long> taglist = JsonUtil.getList(j, "taglist", Long.class);
			    String custormId=j.optString("custormId");//需要修改添加标签目录的客户id
			    
				//查找该人脉下的所有标签
				List<TagSource> listTagSource=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, Long.valueOf(custormId), 1l);
				//删除该人脉下的所有标签
				for (TagSource tagSource2 : listTagSource) {
					tagSourceService.removeTagSource(appId, user.getUserId(), tagSource2.getId());
				}
                if(!taglist.isEmpty()){
                	for (Long tagId : taglist) {
                		TagSource tagSource = new TagSource();
						tagSource.setTagId(tagId);
						tagSource.setAppId(appId);
						tagSource.setUserId(user.getUserId());
						tagSource.setSourceId(Long.valueOf(custormId));
						tagSource.setSourceType(1);//
						tagSource.setCreateAt(ctime);
                        tagSourceService.createTagSource(tagSource);
					}
                }				   
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**
	 * 查询组织在某个目录下的资源
	 * @param request
	 * @param response
	 * @return
	 * @throws DirectorySourceServiceException 
	 * @throws NumberFormatException 
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/findDirectoryIdsCustomer", method = RequestMethod.POST)
	public Map<String, Object> findDirectoryIdsCustomer(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, DirectorySourceServiceException {
		UserBasic user = getUser(request);
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
		Long appId = 1l;
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				  String directoryId=j.optString("directoryId"); //目录id
			      String type=j.optString("type");//1创建的客户，2收藏的客户
			      int currentPage=j.optInt("index"); //默认currentPage为1
				  int pageSize=j.optInt("size");
			      List<DirectorySource> dirctoryList = directorySourceService.getDirectorySourcesByDirectoryId(appId, user.getUserId(), Long.valueOf(directoryId));
			      if(!dirctoryList.isEmpty()){
			    	  for (DirectorySource directorySource : dirctoryList) {
			    		  Map map=new HashMap();
		            	  map.put("list", directorySource.getSourceId());
		            	  map.put("userId", user.getUserId());
		            	  map.put("start", (currentPage-1)*pageSize);
		            	  map.put("end", pageSize);
		            	  map.put("currentPage", currentPage);
		            	  Map<String, Object> simpleCustormerMap = simpleCustomerService.selectByIds(map,type);
		                  responseDataMap.put("simpleCustormerList", simpleCustormerMap);
					}
			      }
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**
	 * 查询组织在某个标签下的资源
	 * @param request
	 * @param response
	 * @return
	 * @throws TagSourceServiceException 
	 * @throws NumberFormatException 
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/findTagIdCustomer", method = RequestMethod.POST)
	public Map<String, Object> findTagIdCustomer(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, TagSourceServiceException {
		UserBasic user = getUser(request);
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
		Long appId = 1l;
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				  String tagId=j.optString("tagId"); //标签id
			      String type=j.optString("type");//1创建的客户，2收藏的客户
			      int currentPage=j.optInt("index"); //默认currentPage为1
				  int pageSize=j.optInt("size");
				  List<TagSource> tagsourceList = tagSourceService.getTagSourcesByAppIdTagId(appId, Long.valueOf(tagId), (currentPage-1)*pageSize, pageSize);
				  int count=tagSourceService.countTagSourcesByAppIdTagId(appId, Long.valueOf(tagId));
				  pageSize=pageSize>Constants.max_select_count?Constants.max_select_count:pageSize;
				  PageUtil page = new PageUtil((int)count, currentPage, pageSize);
				  if(!tagsourceList.isEmpty()){
			    	  for (TagSource tagSource : tagsourceList) {
			    		  Map map=new HashMap();
		            	  map.put("list", tagSource.getSourceId());
		            	  map.put("userId", user.getUserId());
		            	  map.put("start", (currentPage-1)*pageSize);
		            	  map.put("end", pageSize);
		            	  map.put("currentPage", currentPage);
		            	  Map<String, Object> simpleCustormerMap = simpleCustomerService.selectByIds(map,type);
		                  responseDataMap.put("simpleCustormerList", simpleCustormerMap);
		                  responseDataMap.put("page", page);
					}
			      }
			    }	
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**
	 * 删除客户
	 * @param request
	 * @param response
	 * @return
	 * @throws TagSourceServiceException 
	 * @throws DirectorySourceServiceException 
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/deleteResourcesCustomer", method = RequestMethod.POST)
	public Map<String, Object> deleteResourcesCustomer(HttpServletRequest request,
			HttpServletResponse response) throws TagSourceServiceException, DirectorySourceServiceException {
		UserBasic user = getUser(request);
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
					List<Long> custermIds = JsonUtil.getList(j, "custermIds", Long.class);
				    String type=j.optString("type");//1创建的客户，2收藏的客户
				    Long appId = 1l;  
	                if(!custermIds.isEmpty()){
	                  for (Long custermId : custermIds) {
						  PermissionQuery p=new PermissionQuery();
						  p.setResId(custermId);
						  p.setUserId(user.getUserId());
						  p.setResType((short)5);//  5 组织
	                       if("1".equals(type)){
								   simpleCustomerService.deleteByIds(custermIds);
								   customerService.deleteCustomerByCustomerId(custermId,p);
							}else if("2".equals(type)){
								 Map map = new HashMap();
								 map.put("custermId", custermId);
								 map.put("userId", user.getUserId());
								 customerCollectService.deleteUserCustomerCollect(map);
							}
                    	    tagSourceService.removeTagSource(appId, user.getUserId(), custermId);
                    	    directorySourceService.removeDirectorySourcesBySourceId(user.getUserId(), appId, 1, custermId);
				        }
	                }
			    }	
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**
	 * 创建标签目录（测试用）
	 * @param request
	 * @param response
	 * @return
	 * @throws TagSourceServiceException 
	 * @throws DirectorySourceServiceException 
	 * @throws TagServiceException 
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/createTagDirecty", method = RequestMethod.POST)
	public Map<String, Object> createTagDirecty(HttpServletRequest request,
			HttpServletResponse response) throws TagSourceServiceException, DirectorySourceServiceException, TagServiceException {
		UserBasic user = getUser(request);
		// 获取json参数串
		Tag tag = new Tag();
		tag.setAppId(1l);
		tag.setTagName("zbb");
		tag.setTagType(3);
		tag.setUserId(user.getUserId());
		Long aa = tagService.createTag(user.getUserId(), tag);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tagId", aa);
		return model;
	}
	
}

