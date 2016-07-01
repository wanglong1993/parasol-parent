package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.ywxt.organ.model.meet.CustomerMeetingDetail;
import com.ginkgocap.ywxt.organ.model.meet.MeetAssociate;
import com.ginkgocap.ywxt.organ.service.meet.CustomerMeetingDetailService;
import com.ginkgocap.ywxt.user.model.User;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private CustomerMeetingDetailService customerMeetingDetailService;
	@Resource
	private AssociateService associateService;
	
	
	
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
				   System.out.println("SourceId====222="+meetDetail.getId());
					for (int i = 0; i < meetDetail.getAssociateList().size(); i++) {
						MeetAssociate jsonObject2 = meetDetail.getAssociateList().get(i); 
						Associate associate = new Associate();
						associate.setUserId(userBasic.getId());
						associate.setAppId(appId);
						associate.setSourceTypeId(3);
						associate.setSourceId(meetDetail.getId());
						associate.setAssocDesc(jsonObject2.getAssoc_desc());
						associate.setAssocTypeId(jsonObject2.getAssoc_type_id());
						associate.setAssocId(jsonObject2.getAssocid());
						associate.setAssocTitle(jsonObject2.getAssoc_title());
						associate.setAssocMetadata(jsonObject2.getAssocMetadata());
						associateService.createAssociate(appId, userBasic.getId(), associate);
					}
					System.out.println("----------------------------------------");
			       responseDataMap.put("id", 2222222);
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
	
	
	
	/**修改会面记录
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/update.json",method=RequestMethod.POST)
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		 User userBasic = getUser(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		boolean flag = true;
		Long appId = 1l;
		Long ctime = 1l;
		if (requestJson != null && !"".equals(requestJson)){
			if(userBasic==null){
				setSessionAndErr(request, response, "-1", "请登录！");
			}else{
			       try {
					JSONObject jo=JSONObject.fromObject(requestJson);
					  ObjectMapper objectMapper=new ObjectMapper();
				       CustomerMeetingDetail meetDetail=objectMapper.readValue(jo.toString(),CustomerMeetingDetail.class);
				       meetDetail.setCreatorId(userBasic.getId());
					   customerMeetingDetailService.saveOrUpdate(meetDetail);
					   //查询当前会面在关联表中的信息
	                   List<Associate> associatlist = associateService.getAssociatesBySourceId(appId, userBasic.getId(), meetDetail.getId());
					   //删除当前会面在关联表中的信息
	                   for (Associate associate : associatlist) {
						   associateService.removeAssociate(appId,userBasic.getId(), associate.getId());
					   }
	   				   for (int i = 0; i < meetDetail.getAssociateList().size(); i++) {
	   					MeetAssociate jsonObject2 = meetDetail.getAssociateList().get(i); 
	   					Associate associate = new Associate();
	   					associate.setUserId(userBasic.getId());
	   					associate.setAppId(appId);
	   					associate.setSourceTypeId(1);
	   					associate.setSourceId(meetDetail.getId());
						associate.setAssocDesc(jsonObject2.getAssoc_desc());
						associate.setAssocTypeId(jsonObject2.getAssoc_type_id());
						associate.setAssocId(jsonObject2.getAssocid());
						associate.setAssocTitle(jsonObject2.getAssoc_title());
	   					associate.setCreateAt(ctime);
	   					associate.setAssocMetadata(jsonObject2.getAssocMetadata());
	   					associateService.createAssociate(appId, userBasic.getId(), associate);
	   				   }
	                   responseDataMap.put("id", meetDetail.getId());
				} catch (Exception e) {
					setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
					logger.debug(e.getMessage().toString());
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
			JSONObject j = JSONObject.fromObject(requestJson);
			Long creatorId = j.optLong("creatorId");
			String creatortype = j.optString("creatortype");
			int currentPage = j.getInt("currentPage");
			int pageSize = j.getInt("pageSize");
			Map<String,Object> mapTemp = customerMeetingDetailService.findByCustomerId(creatorId,creatortype, currentPage, pageSize);
			responseDataMap.put("success",true);
			responseDataMap.put("meetList", mapTemp.get("results"));
			responseDataMap.put("page", mapTemp.get("page"));
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
			responseDataMap.put("customerMeetingDetail", cmd);
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
				long id = CommonUtil.getLongFromJSONObject(j, "id");
				if(id != 0){
					flag = customerMeetingDetailService.deleteById(id);
					   //查询当前会面在关联表中的信息
	                List<Associate> associatlist = associateService.getAssociatesBySourceId(appId, userBasic.getId(), id);
					   //删除当前会面在关联表中的信息
	                for (Associate associate : associatlist) {
						   associateService.removeAssociate(appId,  userBasic.getId(), associate.getId());
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
