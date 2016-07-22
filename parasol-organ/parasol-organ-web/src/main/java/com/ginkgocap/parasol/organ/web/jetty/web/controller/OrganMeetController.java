package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.ywxt.organ.model.meet.CustomerMeetingDetail;
import com.ginkgocap.ywxt.organ.model.meet.MeetAssociate;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.organ.service.meet.CustomerMeetingDetailService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.util.JsonUtil;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by jbqiu on 2016/6/10.
 * controller 组织会面controller
 */
@Controller
@RequestMapping("/organ/meet")
public class OrganMeetController extends BaseController {

	@Resource
	private CustomerMeetingDetailService customerMeetingDetailService;
	@Resource
	private AssociateService associateService;
    @Autowired
    private UserService userService;
	@Resource
	private CustomerService customerService;
	
	
	/**保存会面记录
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/save.json",method=RequestMethod.POST)
	public Map<String, Object> orgSave(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String requestJson = getJsonParamStr(request);
		User userBasic = getUser(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		Long appId = 1l;
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			if(userBasic==null){
				setSessionAndErr(request, response, "-1", "请登录！");
			}else{
				try {
			       JSONObject jo=JSONObject.fromObject(requestJson);
			       ObjectMapper objectMapper=new ObjectMapper();
			       CustomerMeetingDetail meetDetail=objectMapper.readValue(jo.toString(),CustomerMeetingDetail.class);
			       meetDetail.setCreatorId(userBasic.getId());
				   meetDetail =  customerMeetingDetailService.saveOrUpdate(meetDetail);
			       responseDataMap.put("id", meetDetail.getId());
				} catch (Exception e) {
					e.printStackTrace();
					return returnFailMSGNew("-1", "系统异常,请稍后再试");
				}
			}
		}else{
			setSessionAndErr(request, response, "-1", "请完善信息！");
			 flag = false;
		}
		   responseDataMap.put("success", flag);
	       notificationMap.put("notifCode", "0001");
		   notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	/**查询会面情况列表
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/findList.json",method=RequestMethod.POST)
	public Map<String, Object> findList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		 User userBasic = getUser(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			if(userBasic==null){
				return returnFailMSGNew("-1", "请登录后再操作！");
			}else{
			JSONObject j = JSONObject.fromObject(requestJson);
			Long creatorId = userBasic.getId();
			Long creatorTagId=j.optLong("creatorTagId");
			int creatortype = j.getInt("creatortype");
			int currentPage = j.getInt("currentPage");
			int pageSize = j.getInt("pageSize");
			Map<String,Object> mapTemp = customerMeetingDetailService.findByCustomerId(creatorId,creatortype,creatorTagId,currentPage,pageSize);
			responseDataMap.put("success",true);
			responseDataMap.put("meetList", mapTemp.get("results"));
			responseDataMap.put("page", mapTemp.get("page"));
			}
		}else{
			setSessionAndErr(request, response, "-1", "请稍后再试！");
			flag = false;
		}
		responseDataMap.put("success", flag);
        notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**查询会面情况
	 * @author zbb
	 * @throws AssociateServiceException 
	 */
	@ResponseBody
	@RequestMapping(value="/findOne.json",method=RequestMethod.POST)
	public Map<String, Object> findOne(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AssociateServiceException {
		 String requestJson = getJsonParamStr(request);
			User userBasic = null;
	        userBasic=getUser(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		//Long appId=1l;
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			if(userBasic==null){
				return returnFailMSGNew("-1", "请登录后再操作！");
			}else{
				JSONObject j = JSONObject.fromObject(requestJson);
				long id = CommonUtil.getLongFromJSONObject(j, "id");
				CustomerMeetingDetail cmd = customerMeetingDetailService.findOne(id);
				responseDataMap.put("customerMeetingDetail", cmd);
			}
		}else{
			setSessionAndErr(request, response, "-1", "请稍后再试");
			flag = false;
		}
		responseDataMap.put("success",flag);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**删除会面情况
	 * @author zbb
	 * @throws AssociateServiceException 
	 */
	@ResponseBody
	@RequestMapping(value="/deleteById.json",method=RequestMethod.POST)
	public Map<String, Object> deleteById(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AssociateServiceException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = false;
		Long appId = 1l;
		User userBasic = null;
        userBasic=getUser(request);
		if (requestJson != null && !"".equals(requestJson)){
			if(userBasic==null){
				setSessionAndErr(request, response, "-1", "请登录！");
			}else{
				JSONObject j = JSONObject.fromObject(requestJson);
				List<Long> id = JsonUtil.getList(j, "id", Long.class);
				if(id.isEmpty()){
					setSessionAndErr(request, response, "-1", "请选择删除对象！");
				}else{
					for (Long long1 : id) {
						flag = customerMeetingDetailService.deleteById(long1);
					}
				}
			}
		}else{  
			flag = false;
			setSessionAndErr(request, response, "-1", "请稍后再试！");
		}
		responseDataMap.put("success",flag);
		notificationMap.put("notifCode", "0001");
		notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
}
