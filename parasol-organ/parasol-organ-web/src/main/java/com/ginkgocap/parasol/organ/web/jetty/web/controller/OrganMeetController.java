package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Utils;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.ywxt.organ.model.meet.CustomerMeetingDetail;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.organ.service.meet.CustomerMeetService;
import com.ginkgocap.ywxt.organ.service.meet.CustomerMeetingDetailService;
import com.ginkgocap.ywxt.util.JsonUtil;
/**
 * Created by jbqiu on 2016/6/10.
 * controller 组织会面controller
 */
@Controller
@RequestMapping("/customer/meet")
public class OrganMeetController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private CustomerService customerService;
	@Resource
	private CustomerMeetService customerMeetService;
	@Resource
	private CustomerMeetingDetailService customerMeetingDetailService;
	/**保存会面记录
	 * @author tanghh
	 */
	@ResponseBody
	@RequestMapping(value="/save.json",method=RequestMethod.POST)
	public Map<String, Object> orgSave(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			try {
		       JSONObject jo=JSONObject.fromObject(requestJson);
		       jo.remove("relevance");
		       ObjectMapper objectMapper=new ObjectMapper();
		       CustomerMeetingDetail meetDetail=objectMapper.readValue(jo.toString(),CustomerMeetingDetail.class);
			   String relevance=JsonUtil.getJsonNode(requestJson, "relevance").toString();
			   meetDetail.setRelevance(relevance);
		       List<CustomerMeetingDetail> meetList = new ArrayList();
		       meetList.add(meetDetail);
//		       CustomerMeet meet=new CustomerMeet();
//		       meet.setMeetingList(meetList);
//		       meet.setId(meetDetail.getCustomerId());
		       meetDetail =  customerMeetingDetailService.saveOrUpdate(meetDetail);
		       responseDataMap.put("id", meetDetail.getId());
//		       customerMeetService.saveOrUpdate(meet);
			} catch (Exception e) {
				setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
				logger.debug(e.getMessage().toString());
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
	/**修改会面记录
	 * @author tanghh
	 */
	@ResponseBody
	@RequestMapping(value="/update.json",method=RequestMethod.POST)
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
		       try {
				JSONObject jo=JSONObject.fromObject(requestJson);
				jo.remove("relevance");
				  ObjectMapper objectMapper=new ObjectMapper();
			       CustomerMeetingDetail meetDetail=objectMapper.readValue(jo.toString(),CustomerMeetingDetail.class);
					String relevance=JsonUtil.getJsonNode(requestJson, "relevance").toString();
					meetDetail.setRelevance(relevance);
				   List meetList = new ArrayList();
				   meetList.add(meetDetail);
//				   CustomerMeet meet=new CustomerMeet();
//				   meet.setMeetingList(meetList);
//				   meet.setId(meetDetail.getCustomerId());
				   customerMeetingDetailService.saveOrUpdate(meetDetail);
//				   customerMeetService.saveOrUpdate(meet);
				   responseDataMap.put("id", meetDetail.getId());
			} catch (Exception e) {
				setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
				logger.debug(e.getMessage().toString());
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
	 * @author tanghh
	 */
	@ResponseBody
	@RequestMapping(value="/findList.json",method=RequestMethod.POST)
	public Map<String, Object> findList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = true;
		try {
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject j = JSONObject.fromObject(requestJson);
			long creatorId = CommonUtil.getLongFromJSONObject(j, "creatorId");
			String creatortype=JsonUtil.getJsonNode(requestJson, "creatortype").toString();
			int currentPage = j.getInt("currentPage");
			int pageSize = j.getInt("pageSize");
		//	CustomerMeet cm = customerMeetService.findOne(id);
			List<Map<String,Object>> meettingList = new ArrayList<Map<String,Object>>();
			Map<String,Object> mapTemp = customerMeetingDetailService.findByCustomerId(creatorId,creatortype, currentPage, pageSize);
			List<CustomerMeetingDetail> cmd = (List<CustomerMeetingDetail>)mapTemp.get("results");
			if(cmd!=null){
				for(CustomerMeetingDetail detail:cmd){
				
						Map<String,Object> map = CommonUtil.convertBean(detail);
						 ObjectMapper objectMapper=new ObjectMapper();
						 Map<String,Object> relevanceMap = new HashMap<String, Object>();
						 if(StringUtils.isNotBlank(detail.getRelevance())){
							relevanceMap = objectMapper.readValue(detail.getRelevance(),new TypeReference<HashMap<String, Object>>(){});
						 }else{
						 }
						 map.put("relevance", relevanceMap);
						 meettingList.add(map);
					
					
				}
			}
			responseDataMap.put("success",true);
			responseDataMap.put("meetList", meettingList);
			responseDataMap.put("page", mapTemp.get("page"));
		}else{
			setSessionAndErr(request, response, "-1", "请稍后再试！");
			flag = false;
		}
		} catch (Exception e) {
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
	 * @author tanghh
	 */
	@ResponseBody
	@RequestMapping(value="/findOne.json",method=RequestMethod.POST)
	public Map<String, Object> findOne(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject j = JSONObject.fromObject(requestJson);
			long id = CommonUtil.getLongFromJSONObject(j, "id");
			CustomerMeetingDetail cmd = customerMeetingDetailService.findOne(id);
			if(cmd != null){
				Map<String,Object> map = new HashMap<String, Object>();
				try {
					map = CommonUtil.convertBean(cmd);
					 ObjectMapper objectMapper=new ObjectMapper();
					 Map<String,Object> relevanceMap = new HashMap<String, Object>();
					 if(StringUtils.isNotBlank(cmd.getRelevance())){
						relevanceMap = objectMapper.readValue(cmd.getRelevance(),new TypeReference<HashMap<String, Object>>(){});
					 }else{
					 }
					 responseDataMap.put("result", map);
					 map.put("relevance", relevanceMap);
				} catch (Exception e) {
					setSessionAndErr(request, response, "-1", "请稍后再试！");
					flag = false;
				}
			}else{
				setSessionAndErr(request, response, "-1", "请稍后再试！");
				flag = false;
			}
			
		}else{
			setSessionAndErr(request, response, "-1", "请稍后再试！");
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
	 * @author tanghh
	 */
	@ResponseBody
	@RequestMapping(value="/deleteById.json",method=RequestMethod.POST)
	public Map<String, Object> deleteById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = false;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject j = JSONObject.fromObject(requestJson);
			long id = CommonUtil.getLongFromJSONObject(j, "id");
			if(id != 0){
				flag = customerMeetingDetailService.deleteById(id);
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
