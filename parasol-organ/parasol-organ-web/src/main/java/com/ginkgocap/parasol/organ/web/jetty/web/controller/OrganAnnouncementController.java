package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginkgocap.parasol.user.model.UserBasic;
import jersey.repackaged.com.google.common.collect.Maps;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gintong.ywxt.organization.model.CustomerAnnouncement;
import com.gintong.ywxt.organization.model.OrganRegister;
import com.gintong.ywxt.organization.model.OrganUserLog;
import com.gintong.ywxt.organization.service.CustomerAnnouncementService;
import com.gintong.ywxt.organization.service.OrganRegisterService;
import com.gintong.ywxt.organization.service.OrganUserLogService;

/**
 * Created by jbqiu on 2016/6/10.
 * controller 组织公告controller
 */
@Controller
@RequestMapping(value="/organ")
public class OrganAnnouncementController extends BaseController {

	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	private CustomerAnnouncementService customerAnnouncementService;
	
	@Resource
	private OrganRegisterService organRegisterService;
	
	@Resource
	private OrganUserLogService organUserLogService;
	
	/**
	* @Title:		AnnouncementSave 
	* @Description: 添加组织公告
	* @param:		@param request
	* @param:		@param response
	* @param:		@return
	* @param:		@throws IOException
	* @return:		Map<String,Object>
	* @author:		xutianlong
	 */
	@ResponseBody
	@RequestMapping(value="/announcement/save")
	public Map<String,Object> AnnouncementSave(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
				String requestJson = "";
		requestJson = getJsonParamStr(request);
		Map<String, Object> responseDataMap = Maps.newHashMap();

        UserBasic userBasic=null;
        userBasic=getUser(request);
		if(requestJson != null && !"".equals(requestJson)){
			try{
                userBasic=getUser(request);
				JSONObject jo = JSONObject.fromObject(requestJson);
				CustomerAnnouncement ca = new CustomerAnnouncement();
				ca.setCreateId(userBasic.getUserId());
				ca.setTitle(jo.optString("title"));
				ca.setContent(jo.optString("content"));
				ca.setType(0);
				long id = customerAnnouncementService.saveCustomerAnnouncement(ca);
				if( id > 0){
					responseDataMap.put("id", id);
				}
				// 插入操作日志
				//insertLog(request,"发布公告");
			}catch(Exception e){
				setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
				logger.error("添加组织公告报错,请求参数:{}"+requestJson,e);
				return returnFailMSGNew("01", "系统异常,请稍后再试");
			}
		}else {
			setSessionAndErr(request, response, "-1", "非法操作！");
			return returnFailMSGNew("01", "非法操作！");
		}
		return returnSuccessMSG(responseDataMap);
	}
	
	/**
	* @Title:		findByCreateId 
	* @Description: 根据组织ID查询公告列表 
	* @param:		@param request
	* @param:		@param response
	* @param:		@return
	* @param:		@throws IOException&nbsp;&nbsp;&nbsp; 
	* @return:		Map<String,Object>&nbsp;&nbsp;&nbsp; 
	* @throws:
	* @author:		xutianlong
	 */
	@ResponseBody
	@RequestMapping(value="/announcement/list")
	public Map<String,Object> findByCreateId(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		logger.info("查询组织公告列表");
		Map<String, Object> result = Maps.newHashMap();
		try {
			String requestJson = getJsonParamStr(request);
			if (StringUtils.isNotBlank(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				Long createId = j.optLong("organId");
				int currentPage=j.optInt("index");
				int pageSize=j.optInt("size");
				pageSize = pageSize == 0 ? 6 : pageSize;
				// 返回的公告list和total在service接口中提供
				result = customerAnnouncementService.fingByParam(createId, currentPage, pageSize);
				result.put("success", true); 
				result.put("index", currentPage);
				result.put("size", pageSize);
				// 插入操作日志
				//insertLog(request,"查询公告列表");
			}
		} catch (Exception e) {
			return returnFailMSGNew("01", "系统异常,请稍后再试");
		}

		return genRespBody(result, null);
	}
	
	
	//获取公告内容
	@ResponseBody
	@RequestMapping(value="/announcement/getOne")
	public Map<String,Object> findAnnouncementById(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Map<String,Object> responseDataMap = Maps.newHashMap();
		CustomerAnnouncement ca = new CustomerAnnouncement();
		JSONObject j = null;
		try{
			j = JSONObject.fromObject(getJsonParamStr(request));
			long id = j.optLong("announcementId");
			if(0 != id) {
				ca = customerAnnouncementService.findById(id);
				responseDataMap.put("ca", ca);
				if(null == ca){
					return returnFailMSGNew("01", "查询的公告不存在");
				}
			}
			
			String organAllName = "";
			if(null != ca && null != ca.getCreateId() && !"".equals(ca.getCreateId())) {
				OrganRegister organ = organRegisterService.getOrganRegisterById(ca.getCreateId());
				if(null != organ) {
					organAllName = organ.getOrganAllName();
				}
			}
			responseDataMap.put("organAllName", organAllName);
			// 插入操作日志
			//insertLog(request,"获取公告详情");
		}catch(Exception e){
			logger.error("根据组织公告Id获取公告详情失败 ");
			return returnFailMSGNew("01", "系统异常,请稍后再试");
		}
		return returnSuccessMSG(responseDataMap);
	}
	
	/**
	* @Title:		updateAnnouncement 
	* @Description: 修改组织公告 
	* @param:		@param ca
	* @param:		@return&nbsp;&nbsp;&nbsp; 
	* @return:		Map<String,Object>&nbsp;&nbsp;&nbsp; 
	* @author:		xutianlong
	*/
	
	//修改公告内容
	@ResponseBody
	@RequestMapping(value="/announcement/update")
	public Map<String,Object> updateAnnouncement(HttpServletRequest request
			, HttpServletResponse response){
		Map<String,Object> responseDateMap = Maps.newHashMap();
		JSONObject j = null;
		try{
			j = JSONObject.fromObject(getJsonParamStr(request));
			long id = j.getLong("id");
			String title = j.getString("title");
			String content = j.getString("content");
			CustomerAnnouncement ca = new CustomerAnnouncement();
			ca.setId(id);
			ca.setTitle(title);
			ca.setContent(content);
			customerAnnouncementService.updateCustomerAnnouncement(ca);
			responseDateMap.put("id", id);
			// 插入操作日志
			//insertLog(request,"修改公告");
		}catch(Exception e){
			logger.error("修改组织公告失败");
			return returnFailMSGNew("01", "系统异常，请联系管理员");
		}
		return returnSuccessMSG(responseDateMap);
	}
	
	/**
	* @Title:		deleteAnnouncement 
	* @Description: 根据ID删除组织公告
	* @param:		@param request
	* @param:		@param response
	* @param:		@return
	* @return:		Map<String,Object>&nbsp;&nbsp;&nbsp; 
	* @throws:
	* @author:		xutianlong
	 */
	
	//根据公告id删除公告
	@ResponseBody
	@RequestMapping(value="/announcement/delete")
	public Map<String,Object> deleteAnnouncement(HttpServletRequest request
			, HttpServletResponse response){
		Map<String,Object> responseDataMap = Maps.newHashMap();
		JSONObject j = null;
		try{
			j = JSONObject.fromObject(getJsonParamStr(request));
			long id = j.getLong("id");
			customerAnnouncementService.deleteCustomerAnnouncement(id);
			responseDataMap.put("id",id);
			// 插入操作日志
			//insertLog(request,"删除公告:" + id);
		}catch(Exception e){
			logger.error("删除组织公告时,服务器报错");
			return returnFailMSGNew("01", "删除组织公告时系统异常，请联系管理员");
		}
		return returnSuccessMSG(responseDataMap);
	}

}
