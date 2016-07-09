package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ginkgocap.ywxt.organ.model.CustomerCollect;
import com.ginkgocap.ywxt.organ.model.CustomerInform;
import com.ginkgocap.ywxt.organ.model.SimpleCustomer;
import com.ginkgocap.ywxt.organ.service.CustomerCollectService;
import com.ginkgocap.ywxt.organ.service.CustomerCountService;
import com.ginkgocap.ywxt.organ.service.CustomerInformService;
import com.ginkgocap.ywxt.organ.service.SimpleCustomerService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.JsonUtil;
/**
* <p>Title: OrganCollectController.java<／p> 
* <p>Description: 组织收藏和举报接口<／p> 
* @author wfl
* @date 2015-6-9 
* @version 1.0
 */
@Controller
@RequestMapping("/customer")
public class OrganCollectController extends BaseController {

	  private final Logger logger=LoggerFactory.getLogger(getClass());
      @Autowired
      private CustomerCollectService customerCollectService;
      @Autowired
      private SimpleCustomerService simpleCustomerService;
      @Autowired
      private CustomerInformService customerInformService;
      @Autowired
      private CustomerCountService customerCountService;
	    /**
	     * 收藏或取消收藏组织客户
	     * type 1:收藏 2:取消
	     * @author wfl
	     */
	    @ResponseBody
		@RequestMapping(value = "/collect/operate.json", method = RequestMethod.POST)
		public Map<String, Object> customerCollectSave(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String requestJson = "";
			requestJson = getJsonParamStr(request);
			Map<String, Object> model = new HashMap<String, Object>();
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			Map<String, Object> notificationMap = new HashMap<String, Object>();
			if (requestJson != null && !"".equals(requestJson)) {
				try {
					JSONObject jo = JSONObject.fromObject(requestJson);
					String type=jo.getString("type");
				    User user=getUser(request);
				    List<Object> ids = JsonUtil.getList(jo, "customerIds", List.class);
				    if(!isNullOrEmpty(ids)){
				    		 if("1".equals(type)){//收藏
				    			  for(int i=0;i<ids.size();i++){
				    				  SimpleCustomer sc=simpleCustomerService.findByCustomerId(Long.parseLong(ids.get(i).toString()));
				    				  if(sc!=null){
				    					  CustomerCollect cc=new CustomerCollect();
					    				  cc.setCustomerId(sc.getCustomerId());
					    				  cc.setType(sc.getVirtual()==0?0:1);
					    				  cc.setUserId(user.getId());
					    				  customerCollectService.saveCustomerCollect(cc);
					    				    try{
					    		    			customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.collect.getType(), sc.getCustomerId());
					    		    		}catch(Exception e){
					    		    			logger.error("插入组织数据统计功能报错,请求参数json: ",e);
					    		    		}
				    				  }
				    			  }
				    		  }else if("2".equals(type)){//取消收藏
				    			  List<Long> idLs=new ArrayList<Long>();
				    			  for(int i=0;i<ids.size();i++){
				    				  idLs.add(Long.parseLong(ids.get(i).toString()));
				    			  }
						    	 customerCollectService.deleteCustomerCollect(idLs);
							 }
				    }
					responseDataMap.put("success", true);
					responseDataMap.put("msg", "操作成功");
					notificationMap.put("notifCode", "0001");
					notificationMap.put("notifInfo", "hello mobile app!");
				} catch (Exception e) {
					setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
					logger.error("收藏组织客户报错,请求参数:{}"+requestJson,e);
				}
			} else {
				setSessionAndErr(request, response, "-1", "非法操作！");
			}
			model.put("responseData", responseDataMap);
			model.put("notification", notificationMap);

			return model;
		}
	    /**
	     * 举报组织客户
	     * @author wfl
	     */
	    @ResponseBody
		@RequestMapping(value = "/inform/save.json", method = RequestMethod.POST)
		public Map<String, Object> customerInformSave(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String requestJson = "";
			requestJson = getJsonParamStr(request);
			Map<String, Object> model = new HashMap<String, Object>();
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			Map<String, Object> notificationMap = new HashMap<String, Object>();
			if (requestJson != null && !"".equals(requestJson)) {
				try {
					JSONObject jo = JSONObject.fromObject(requestJson);
				    String reason=jo.getString("reason");
				    String content=jo.getString("content");
				    long customerId=jo.getLong("customerId");
				    User user=getUser(request);
				    SimpleCustomer sc=simpleCustomerService.findByCustomerId(customerId);
				    if(sc!=null){
				    	CustomerInform ci=new CustomerInform();
				    	ci.setReason(reason);
				    	ci.setContent(content);
				    	ci.setCustomerId(customerId);
				    	ci.setType(sc.getVirtual()==0?0:1);
				    	ci.setUserId(user.getId());
				    	ci.setName(user.getName());
				    	customerInformService.saveCustomerInform(ci);
				    }
					responseDataMap.put("success", true);
					responseDataMap.put("msg", "操作成功");
					notificationMap.put("notifCode", "0001");
					notificationMap.put("notifInfo", "hello mobile app!");
				} catch (Exception e) {
					setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
					logger.error("举报组织客户报错,请求参数:{}"+requestJson,e);
				}
			} else {
				setSessionAndErr(request, response, "-1", "非法操作！");
			}
			model.put("responseData", responseDataMap);
			model.put("notification", notificationMap);

			return model;
		}
	    
}
