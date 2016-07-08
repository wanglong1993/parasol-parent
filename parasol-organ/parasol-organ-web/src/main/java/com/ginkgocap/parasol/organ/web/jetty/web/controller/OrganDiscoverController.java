package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.ObjectUtils;
import com.ginkgocap.ywxt.organ.model.SimpleCustomer;
import com.ginkgocap.ywxt.organ.service.SimpleCustomerService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.PageUtil;
import com.gintong.common.phoenix.permission.service.PermissionRepositoryService;

/**
 * 发现组织
 * @author hdy
 * @date 2015-3-10
 */
@Controller
@RequestMapping("/organ/organ") 
public class OrganDiscoverController  extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SimpleCustomerService simpleCustomerService;
	@Autowired
	PermissionRepositoryService permissionRepositoryService;
	@Resource
	private ResourcePathExposer resourcePathExposer;
	/**
	 * 发现组织
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDiscoverList.json", method = RequestMethod.POST)
	public Map<String, Object> getOrgNoticesList(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/org/getDiscoverList.json");
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
		try{
			if (!isNullOrEmpty(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				
					int currentPage=j.optInt("index")+1; //默认是0 所以+1
					int pageSize=j.optInt("size");
					pageSize = pageSize == 0 ? 20 : pageSize;
				    String area = j.optString("area");
				    String industry =  j.optString("industry");
				    String type =  j.optString( "type");
				    //类型不限
				    type =  type.equals("0") ? "": type;
				   
					//分页列表
				   Map<String,Object>   bsn =  simpleCustomerService.findDiscorverOrg(user==null?0:user.getId(),area, industry, type,  currentPage,  pageSize);
					PageUtil pageUtil= (PageUtil) bsn.get("page");
					int total=pageUtil.getCount();
					mapPage.put("total", total);
					List<SimpleCustomer> list = (List<SimpleCustomer>) bsn.get("results");
					this.thisIsSbMethod(list);
					mapPage.put("listResults",bsn.get("results"));
					mapPage.put("index", currentPage-1);
					mapPage.put("size", pageSize);
			} else {
				setSessionAndErr(request, response, "-1", "输入参数不合法");
				return returnFailMSGNew("01", "输入参数不合法");
			}
		}catch(Exception e){
			logger.error("调用发现组织接口出错，访问参数json{}"+requestJson,e);
			return returnFailMSGNew("01", "系统异常，请稍后再试！");
		}
		responseDataMap.put("page",mapPage);
		responseDataMap.put("success",true);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	/**
	 * 转为绝对地址
	 * @param list
	 * @return
	 */
	private void thisIsSbMethod (List<SimpleCustomer> list) {
		for (int i = 0; i < list.size(); i++) {
			SimpleCustomer sc = list.get(i);
			sc.setPicLogo(resourcePathExposer.getNginxRoot()+ObjectUtils.alterImageUrl(sc.getPicLogo()));
		}
	}

}
