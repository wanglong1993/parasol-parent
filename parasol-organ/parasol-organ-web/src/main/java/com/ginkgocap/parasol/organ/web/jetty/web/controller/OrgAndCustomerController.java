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
import com.ginkgocap.parasol.organ.web.jetty.web.utils.ObjectUtils;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectoryService;
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
import com.ginkgocap.ywxt.user.service.FriendsRelationService;
import com.ginkgocap.ywxt.util.JsonUtil;
import com.ginkgocap.ywxt.util.PageUtil;
import com.gintong.common.phoenix.permission.entity.PermissionQuery;


/**
 * 我的组织客户
 * @author hdy
 * @date 2015-3-10
 */
@Controller
@RequestMapping("/org")
public class OrgAndCustomerController  extends BaseController {
	
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SimpleCustomerService simpleCustomerService;
	@Autowired
	FriendsRelationService friendsRelationService;
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
	@Autowired
	private DirectoryService directoryService;
	
	/**
	 * 我的客户组织列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrgAndCustomer.json", method = RequestMethod.POST)
	public Map<String, Object> getOrgNoticesList(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/org/getOrgAndCustomer.json");
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
			    String groupId = j.optString( "groupId");
			    String tagId =  j.optString( "tagId");
			    String type=j.optString("type");
			    String name="";
			    if(j.has("name")){
			    	name =j.optString("name");
			    }
			    if("0".equals(groupId)) {
			    	groupId ="";
			    }
			    
			    //接照分组查询
			    if(!StringUtils.isBlank(groupId)) {
			    	Map<String, Object> bsn = customerService.findByParam(user.getId(), groupId, "", "", currentPage, pageSize);
			    	PageUtil pageUtil= (PageUtil) bsn.get("page");
					int total=pageUtil.getCount();
					mapPage.put("total", total);
					List<SimpleCustomer> list = (List<SimpleCustomer>) bsn.get("results");
					this.thisIsSbMethod(user.getId(),list);
					mapPage.put("listResults",bsn.get("results"));
					mapPage.put("index", currentPage-1);
					mapPage.put("size", pageSize);
			    	
			    } else if(!StringUtils.isBlank(tagId) ){ //接照标签查询
			    	Map<String,Object> bsn =rCustomerTagService.getListByTagId(Long.valueOf(tagId), user.getId(), "nameIndex", currentPage, pageSize);
			    	PageUtil pageUtil= (PageUtil) bsn.get("page");
					int total=pageUtil.getCount();
					mapPage.put("total", total);
					List<SimpleCustomer> list = (List<SimpleCustomer>) bsn.get("results");
					this.thisIsSbMethod(user.getId(),list);
					mapPage.put("listResults",bsn.get("results"));
					mapPage.put("index", currentPage-1);
					mapPage.put("size", pageSize);
			    } else {
			           //查询我的组织好友id
					   List<Long> ids = friendsRelationService.findFirendsIdByPram(user.getId(), 2);
					   //查询我的收藏的组织客户id
					   List<Long> customerIds=customerCollectService.getCustomerIdsByParam(user.getId(), 0);
						//分页列表
					   Map<String,Object>   bsn=new HashMap<String,Object>();
					   if("".equals(StringUtils.trimToEmpty(type))||"-2".equals(StringUtils.trimToEmpty(type))){//全部客户和我的组织好友
						   bsn=simpleCustomerService.findByOrgAndCustmer(user.getId(),ids, customerIds,StringUtils.trimToEmpty(name),currentPage, pageSize);
					   }else if("-1".equals(StringUtils.trimToEmpty(type))){//全部客户
						   bsn=simpleCustomerService.findByOrgAndCustmer(user.getId(),null, customerIds,StringUtils.trimToEmpty(name),currentPage, pageSize);
					   }else if("1".equals(StringUtils.trimToEmpty(type))){//我创建的
						   bsn=simpleCustomerService.findByOrgAndCustmer(user.getId(),null, null,StringUtils.trimToEmpty(name),currentPage, pageSize);
					   }else if("2".equals(StringUtils.trimToEmpty(type))){//我收藏的
							if(customerIds!=null&&customerIds.size()>0){
								 bsn=simpleCustomerService.findByOrgAndCustmer(-2,null, customerIds,StringUtils.trimToEmpty(name),currentPage, pageSize);
							}else{//如果没有收藏，直接返回
								PageUtil page = new PageUtil(0, 1, 20);
								bsn.put("page", page);
								bsn.put("results", new ArrayList<SimpleCustomer>());
							}
					   }else if("3".equals(StringUtils.trimToEmpty(type))){//其他
						   if(ids!=null&&ids.size()>0){
							    bsn=simpleCustomerService.findByOrgAndCustmer(-2,ids, null,StringUtils.trimToEmpty(name),currentPage, pageSize);
						   }else{
							    PageUtil page = new PageUtil(0, 1, 20);
								bsn.put("page", page);
								bsn.put("results", new ArrayList<SimpleCustomer>());
						   }
					   }else if("4".equals(StringUtils.trimToEmpty(type))){//好友和我创建的客户
						   bsn=simpleCustomerService.findByOrgAndCustmer(user.getId(),ids, null,StringUtils.trimToEmpty(name),currentPage, pageSize);
					   }
					   
						PageUtil pageUtil= (PageUtil) bsn.get("page");
						int total=pageUtil.getCount();
						mapPage.put("total", total);
						List<SimpleCustomer> list = (List<SimpleCustomer>) bsn.get("results");
						this.thisIsSbMethod(user.getId(),list);
						mapPage.put("listResults",bsn.get("results"));
						mapPage.put("index", currentPage-1);
						mapPage.put("size", pageSize);
			    }
			
			}

		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",true);
		responseDataMap.put("page",mapPage);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**
	 * 删除组织好友或者客户
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteOrgAndCustomer.json", method = RequestMethod.POST)
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/org/deleteOrgAndCustomer.json");
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
			
			    String customerId = j.optString( "customerId");
			    SimpleCustomer simpleCustomer =  simpleCustomerService.findByCustomerId(Long.valueOf(customerId));
			 
			    if(simpleCustomer == null  ||  simpleCustomer.getVirtual() == 0) {//删除客户
			    	   if(simpleCustomer != null && simpleCustomer.getCreateById()!=user.getId()) {
							  responseDataMap.put("success", false);
							  responseDataMap.put("msg", "操作成功");
							  notificationMap.put("notifCode", "0001");
							  notificationMap.put("notifInfo", "操作失败,不能删除别人的客户");
					    } else {
					    	  customerService.deleteById(String.valueOf(customerId));
							  responseDataMap.put("success", true);
							  responseDataMap.put("msg", "操作成功");
							  notificationMap.put("notifCode", "0001");
							 notificationMap.put("notifInfo", "hello mobile app!");
					    }
			    } else {
			    	//删除组织好友(列表中不允许删除)
			    	responseDataMap.put("success", false);
					responseDataMap.put("msg", "操作成功");
					notificationMap.put("notifCode", "0001");
					notificationMap.put("notifInfo", "列表中不允许删除好友");
			    }
			}

		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	/**
	 * 转为绝对地址
	 * @param list
	 * @return
	 */
	private void thisIsSbMethod (long userId,List<SimpleCustomer> list) {
		for (int i = 0; i < list.size(); i++) {
			SimpleCustomer sc = list.get(i);
			sc.setPicLogo(resourcePathExposer.getNginxRoot()+ObjectUtils.alterImageUrl(sc.getPicLogo()));
			sc.setSaveType(1);
			CustomerCollect cc=customerCollectService.findByUserIdAndCustomerId(userId,sc.getCustomerId());
			if(cc!=null){
				sc.setSaveType(2);
			}
			boolean sign=friendsRelationService.isExistFriends(userId, sc.getCreateById());
			if(sign) sc.setSaveType(3);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/resourceManageCount.json")
	public Map<String, Object> resourceManageCount(HttpServletRequest request,HttpServletResponse response) {
		String requestJson = "";
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		try {
			User user = getUser(request);
			requestJson = getJsonParamStr(request);
			 //查询我的组织好友id
			 List<Long> ids = friendsRelationService.findFirendsIdByPram(user.getId(), 2);
			 //查询我的收藏的组织客户id
			 List<Long> customerIds=customerCollectService.getCustomerIdsByParam(user.getId(), 0);
			long count=simpleCustomerService.countByOrgAndCustomer(user.getId(), ids, customerIds, "");
			responseDataMap.put("count", count);
			responseDataMap.put("success", true);
			notificationMap.put("notifCode", "0001");
			notificationMap.put("notifInfo", "hello mobile app!");
		} catch (Exception e) {
			logger.error("访问组织资源管理数量报错，请求参数json{}=="+requestJson,e);
			return returnFailMSGNew("01", "数据请求失败，请稍后再试");
		}
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	/**
	 * 我的组织客户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/getCustomer.json", method = RequestMethod.POST)
	public Map<String, Object> getCustomer(HttpServletRequest request,
			HttpServletResponse response) {
		User user = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		boolean flag=true;
		// 封装 response
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				flag=false;
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
			    	 map.put("userid", user.getId());
			    	 simpleCustomerList=simpleCustomerService.selectUserid(map);
			    	 int count = simpleCustomerService.selectUseridCount(map);
			    	 PageUtil page = new PageUtil((int)count, currentPage, pageSize);
			    	 responseDataMap.put("simpleCustomerList", simpleCustomerList);
			    	 responseDataMap.put("page", page);	
			    }else if("2".equals(type)){
			    	 List<Long> customerIds=customerCollectService.getCustomerIdsByParam(user.getId(), 0);
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
			flag=false;
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",flag);
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
	@RequestMapping(value = "/organ/updateCustomerDirectory.json", method = RequestMethod.POST)
	public Map<String, Object> updateCustomerTags(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUser(request);
		boolean flag=true; 
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
			    long custormId = j.optLong("custormId");//需要修改添加标签目录的客户id
			    List<Long> directoryIds = JsonUtil.getList(j, "directoryIds", Long.class);
			    String sourceUrl=j.optString("sourceUrl");
			    String sourceTitle=j.optString("sourceTitle");
			    String sourceData=j.optString("sourceData");
			    String invokeMethod=j.optString("invokeMethod");
			    Long appId = 1l;
			    //删除以前的
			    directorySourceService.removeDirectorySourcesBySourceId(user.getId(), appId, 1,custormId);
			    if(!directoryIds.isEmpty()){
			    	for (Long directoryId : directoryIds) {
			    		DirectorySource directorySource = new DirectorySource();
						directorySource.setDirectoryId(directoryId);
						directorySource.setAppId(appId);
						directorySource.setUserId(user.getId());
						directorySource.setSourceId(Long.valueOf(custormId));
						directorySource.setSourceType(3);//
						directorySource.setSourceUrl(sourceUrl);
						directorySource.setSourceTitle(sourceTitle);
						directorySource.setSourceData(sourceData);
						directorySource.setInvokeMethod(invokeMethod);
						directorySourceService.createDirectorySources(directorySource);
					}
			    }
			}
		} else {
			flag=false;
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",flag);
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
	@RequestMapping(value = "/organ/updateCustomerTags.json", method = RequestMethod.POST)
	public Map<String, Object> updateCustomerDirectory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUser(request);
		boolean flag=true;
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
			flag=false; 
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
			    Long custormId=j.optLong("custormId");//需要修改添加标签目录的客户id
			    String sourceTitle=j.optString("sourceTitle");
				//查找该人脉下的所有标签
				List<TagSource> listTagSource=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, Long.valueOf(custormId), 1l);
				//删除该人脉下的所有标签
				for (TagSource tagSource2 : listTagSource) {
					tagSourceService.removeTagSource(appId, user.getId(), tagSource2.getId());
				}
                if(!taglist.isEmpty()){
                	for (Long tagId : taglist) {
                		TagSource tagSource = new TagSource();
						tagSource.setTagId(tagId);
						tagSource.setAppId(appId);
						tagSource.setUserId(user.getId());
						tagSource.setSourceId(custormId);
						tagSource.setSourceType(3);//
						tagSource.setCreateAt(ctime);
						tagSource.setSourceTitle(sourceTitle);
                        tagSourceService.createTagSource(tagSource);
					}
                }				   
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
			flag=false; 
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
	@RequestMapping(value = "/organ/findDirectoryIdsCustomer.json", method = RequestMethod.POST)
	public Map<String, Object> findDirectoryIdsCustomer(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, DirectorySourceServiceException {
		User user = getUser(request);
		boolean flag=true;
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			flag=false;
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
				  Long directoryId=j.optLong("directoryId"); //目录id
			      String type=j.optString("type");//1创建的客户，2收藏的客户
			      int currentPage=j.optInt("index"); //默认currentPage为1
				  int pageSize=j.optInt("size");
				  Object[] obj = new Object[]{appId,user.getId(),directoryId};
			      List<DirectorySource> dirctoryList = directorySourceService.getSourcesByDirectoryIdAndSourceType((currentPage-1)*pageSize,pageSize,user.getId(),appId,1l,directoryId);
			      int count = directorySourceService.countDirectorySourcesByDirectoryId(appId, user.getId(), directoryId);
				  pageSize=pageSize>Constants.max_select_count?Constants.max_select_count:pageSize;
				  PageUtil page = new PageUtil((int)count, currentPage, pageSize);
			      List<Long> list = new ArrayList<Long>();
			      if(!dirctoryList.isEmpty()){
			    	  for (DirectorySource directorySource : dirctoryList) {
			    		  list.add(directorySource.getSourceId());
					}
			      }
			      List<SimpleCustomer> simpleCustormerMap=null;
			      if(list.isEmpty()){
			    	  responseDataMap.put("simpleCustormerList", simpleCustormerMap);
			      }else{
		    		  Map map=new HashMap();
	            	  map.put("list", list);
	            	  map.put("userId", user.getId());
	            	  simpleCustormerMap = simpleCustomerService.selectByIds(map,type);
	                  responseDataMap.put("simpleCustormerList", simpleCustormerMap);
			      }
			      responseDataMap.put("page", page);
			}
		} else {
			flag=false;
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		responseDataMap.put("success",flag);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	private int appId(long id) {
		// TODO Auto-generated method stub
		return 0;
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
	@RequestMapping(value = "/organ/findTagIdCustomer.json", method = RequestMethod.POST)
	public Map<String, Object> findTagIdCustomer(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, TagSourceServiceException {
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
		Long appId = 1l;
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				  long tagId = j.optLong("tagId"); //标签id
			      String type=j.optString("type");//1创建的客户，2收藏的客户
			      int currentPage=j.optInt("index"); //默认currentPage为1
				  int pageSize=j.optInt("size");
				  List<TagSource> tagsourceList = tagSourceService.getTagSourcesByAppIdTagIdAndType(appId,tagId,3l,(currentPage-1)*pageSize,pageSize);
				  int count=tagSourceService.countTagSourcesByAppIdTagId(appId, tagId);
				  pageSize=pageSize>Constants.max_select_count?Constants.max_select_count:pageSize;
				  PageUtil page = new PageUtil((int)count, currentPage, pageSize);
				  List<Long> list = new ArrayList<Long>();
				  if(!tagsourceList.isEmpty()){
			    	  for (TagSource tagSource : tagsourceList) {
			    		  long customerId = tagSource.getSourceId();
			    		  list.add(customerId);
					}
			      }
				  List<SimpleCustomer> simpleCustormerMap=null;
				  if(list.isEmpty()){
					  responseDataMap.put("simpleCustormerList", simpleCustormerMap);
				  }else{
		    		  Map map=new HashMap();
	            	  map.put("list", list);
	            	  map.put("userId", user.getId());
	            	  simpleCustormerMap = simpleCustomerService.selectByIds(map,type);
	                  responseDataMap.put("simpleCustormerList", simpleCustormerMap);
				  }
                  responseDataMap.put("page", page);
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
	@RequestMapping(value = "/organ/deleteResourcesCustomer.json", method = RequestMethod.POST)
	public Map<String, Object> deleteResourcesCustomer(HttpServletRequest request,
			HttpServletResponse response) throws TagSourceServiceException, DirectorySourceServiceException {
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
					List<Long> custermIds = JsonUtil.getList(j, "custermIds", Long.class);
				    String type=j.optString("type");//1创建的客户，2收藏的客户
				    Long appId = 1l;  
	                if(!custermIds.isEmpty()){
	                  for (Long custermId : custermIds) {
						  PermissionQuery p=new PermissionQuery();
						  p.setResId(custermId);
						  p.setUserId(user.getId());
						  p.setResType((short)5);//  5 组织
	                       if("1".equals(type)){
								   simpleCustomerService.deleteByIds(custermIds);
								   customerService.deleteCustomerByCustomerId(custermId,p);
							}else if("2".equals(type)){
								 Map map = new HashMap();
								 map.put("custermId", custermId);
								 map.put("userId", user.getId());
								 customerCollectService.deleteUserCustomerCollect(map);
							}
                    	    tagSourceService.removeTagSource(appId, user.getId(), custermId);
                    	    directorySourceService.removeDirectorySourcesBySourceId(user.getId(), appId, 1, custermId);
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
	 * @throws DirectoryServiceException 
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/createTagDirecty.json", method = RequestMethod.POST)
	public Map<String, Object> createTagDirecty(HttpServletRequest request,
			HttpServletResponse response) throws TagSourceServiceException, DirectorySourceServiceException, TagServiceException, DirectoryServiceException {
		User user = getUser(request);
		String requestJson = null;
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject j = JSONObject.fromObject(requestJson);
		String name = j.optString("name");
		long time = j.optLong("time");
		// 获取json参数串
//		Tag tag = new Tag();
//		tag.setAppId(1l);
//		tag.setTagName("zbb");
//		tag.setTagType(3);
//		tag.setUserId(user.getId());
//		Long aa = tagService.createTag(user.getId(), tag);
		Map<String, Object> model = new HashMap<String, Object>();
		Directory directory = new Directory();
		directory.setAppId(1l);
		directory.setName(name);
		directory.setNameIndex("");
		directory.setNameIndexAll("");
		directory.setNumberCode("");
		directory.setOrderNo(0);
		directory.setPid(0);
		directory.setRemark("");
		directory.setTypeId(3);
		directory.setUpdateAt(time);
		directory.setUserId(user.getId());
		Long aa = directoryService.createDirectoryForRoot(3l, directory);
		System.out.println(directory.getId());
		model.put("tagId", aa);
		return model;
	}
	
}

