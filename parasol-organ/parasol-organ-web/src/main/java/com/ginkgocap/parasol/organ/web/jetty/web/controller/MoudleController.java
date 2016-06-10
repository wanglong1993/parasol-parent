package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.ywxt.organ.model.template.Moudle;
import com.ginkgocap.ywxt.organ.model.template.OrganTemplate;
import com.ginkgocap.ywxt.organ.model.template.Template;
import com.ginkgocap.ywxt.organ.service.template.MoudleService;
import com.ginkgocap.ywxt.organ.service.template.OrganTemplateService;
import com.ginkgocap.ywxt.organ.service.template.TemplateService;

/**
 * Created by jbqiu on 2016/6/10.
 * controller 模快controller
 */
@Controller
@RequestMapping("/organ")
public class MoudleController extends BaseController{

	 public final Logger logger=LoggerFactory.getLogger(getClass());
	 
	
	 
	 @Autowired
	 public  MoudleService moudleService;
	
	    	//保存或修改  模块
	    @ResponseBody
		@RequestMapping(value = "/moudle/saveMoudle", method = RequestMethod.POST)
		public Map<String, Object> saveMoudle(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
	    	System.out.println("controller:moudle/saveMoudle.json");
	    	String requestJson = "";
			requestJson = getJsonParamStr(request);
			
			Map<String, Object> model = new HashMap<String, Object>();
		     
				try {
					
					Moudle moudle=JSON.parseObject(requestJson,Moudle.class);
					moudleService.saveOrUpdateMoudle(moudle);
					
				} catch (Exception e) {
					e.printStackTrace();
					model.put("success", false);
					model.put("msg", "系统异常,请稍后再试");
					return model;
				}
				
				model.put("success", true);
				model.put("msg", "操作成功");
				return model;
	    }
	 
	    
	    
	 	//保存或修改  模块
	    @ResponseBody
		@RequestMapping(value = "/moudle/deleteMoudle", method = RequestMethod.POST)
		public Map<String, Object> delteMoudle(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
	    	System.out.println("controller:org/moudle/delteMoudle.json");
	    	String requestJson = "";
			requestJson = getJsonParamStr(request);
			
			Map<String, Object> model = new HashMap<String, Object>();
			JSONObject j = JSONObject.fromObject(requestJson);
			System.out.println("进入controller");
				long moudleId=j.optLong("moudleId");
				if(0==moudleId){
					model.put("success", false);
					model.put("msg", "传入参数不能为空");
					return model;
				}
			
		     
				try {
					moudleService.deletMoudleById(moudleId);
				} catch (Exception e) {
					e.printStackTrace();
					model.put("success", false);
					model.put("msg", "系统异常,请稍后再试");
					return model;
				}
				model.put("success", true);
				model.put("msg", "删除成功");
				
				return model;
	    }
	   
	    
}
