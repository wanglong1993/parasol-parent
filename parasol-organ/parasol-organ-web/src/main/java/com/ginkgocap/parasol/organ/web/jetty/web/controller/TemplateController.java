package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.TemplateVo;
import com.ginkgocap.ywxt.organ.model.template.Template;
import com.ginkgocap.ywxt.organ.service.template.TemplateService;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by jbqiu on 2016/6/10.
 * controller 组织模板controller
 */
@Controller
@RequestMapping("/organ")
public class TemplateController extends BaseController{

	 public final Logger logger=LoggerFactory.getLogger(getClass());
	 @Autowired
	 public   TemplateService templateService;
	
	    //根据ID 查询模板
	    @ResponseBody
		@RequestMapping(value = "/template/findTempleById.json", method = RequestMethod.POST)
		public Map<String, Object> findTempleById(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
	    	System.out.println("controller:org/template/findTempleById.json");
	    	String requestJson = "";
			requestJson = getJsonParamStr(request);
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			boolean flag = true;
				try {
					JSONObject j = JSONObject.fromObject(requestJson);
					System.out.println("进入controller");
						long templateId=j.optLong("templateId");
						if(0==templateId){
							return returnFailMSGNew("01", "传入参数值不能为空");
						}
						System.out.println("zhixingfangfaqian");
						System.out.println(templateService+"kkkkk");
					    Template template=templateService.findTemplateById(templateId);
					    
					    TemplateVo templateVo=new TemplateVo();
					    templateVo.setTemplateName(template.getName());
					    templateVo.setTemplateId(template.getId());
					    templateVo.setTemplateType(template.getType());
					    templateVo.setMoudles(template.getMoudles());
					    responseDataMap.put("template", templateVo);
					    
					
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
					return returnFailMSGNew("01", "系统异常,请稍后再试");
				}
			responseDataMap.put("success", flag);

			return responseDataMap;
	    }
	 
	    
	   
	    //根据Id 保存或修改模板
	    @ResponseBody
	    @RequestMapping(value = "/template/saveTemplate.json", method = RequestMethod.POST)
		public Map<String, Object> saveTemplate(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
	    	System.out.println("controller:/template/saveTemplate.json");
	    	String requestJson = "";
			requestJson = getJsonParamStr(request);
			Map<String, Object> model = new HashMap<String, Object>();
			
			    ObjectMapper objectMapper=new ObjectMapper();
			    Template template=JSON.parseObject(requestJson, Template.class);
			    boolean flag = true;
			    try{
			    	    templateService.saveOrUpdteTemplate(template);
			    		model.put("success", true);
				    	model.put("msg", "操作成功");
			    	
			    }catch(Exception e){
			    	
			    	e.printStackTrace();
			    	model.put("success", false);
			    	model.put("msg", "操作失败");
					return model;
			    }
			    
		    
			    
			return model;
	    }
	 
	  
	    
	    
	    
	    
	    
	    
	  
	  //根据Id 查询模板
	    @ResponseBody
		@RequestMapping(value = "/template/findUserTemplateBasiInfo.json", method = RequestMethod.POST)
		public Map<String, Object> findUserTemplateBasiInfo(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
	    	System.out.println("/template/findUserTemplateBasiInfo.json");
	    	String requestJson = "";
			requestJson = getJsonParamStr(request);
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			boolean flag=true;
				try {
					JSONObject j = JSONObject.fromObject(requestJson);
					System.out.println("进入controller");
						long userId=j.optLong("userId");
						if(0==userId){
							return returnFailMSGNew("01", "传入参数值不能为空");
						}
				
					   List<Map> templatBasicInfos=templateService.findTemplateBasicInfoByUserId(userId);
					    responseDataMap.put("templatBasicInfos", templatBasicInfos);
					
				} catch (Exception e) {
					flag=false;
					e.printStackTrace();
					return returnFailMSGNew("01", "系统异常,请稍后再试");
				}
					
				responseDataMap.put("success", flag);

			return responseDataMap;
	    }
	 
	    
	    
		    @ResponseBody
			@RequestMapping(value = "/template/deleteTemplate.json", method = RequestMethod.POST)
			public Map<String, Object> deleteTemplate(HttpServletRequest request,
					HttpServletResponse response) throws IOException {
		    	System.out.println("/template/deleteTemplate.json");
		    	String requestJson = "";
				requestJson = getJsonParamStr(request);
				Map<String, Object> model = new HashMap<String, Object>();
				boolean flag=true;
					try {
						JSONObject j = JSONObject.fromObject(requestJson);
						System.out.println("进入controller");
							long templateId=j.optLong("templateId");
							if(0==templateId){
								model.put("success", false);
								model.put("msg", "传入参数不能为空");
								return model;
							}
						Template template=	templateService.findTemplateById(templateId);
						if(template.getType()==1){
							templateService.deleteTemplate(templateId);
						}else{
							
							model.put("success", false);
							model.put("msg", "不能删除系统模板");
							return model;
						}
						
						
					} catch (Exception e) {
						
						model.put("success", false);
						model.put("msg", "删除失败");
						e.printStackTrace();
						return model;
					}
						
					model.put("success", true);
					model.put("msg", "删除成功");

				return model;
		    }
	    
		    
		    
			
			 /**
		    *
		    * 定义成功返回信息
		    *
		    * @param successResult
		    * @return
		    * @author haiyan
		    */
		   protected Map<String, Object> returnSuccessMSG(Map<String, Object> successResult) {
			   
			    successResult.put("success", true);
		        return successResult;
		   }

		   /**
		    * 定义错误返回信息
		    *
		    * @param result
		    * @param errRespCode
		    * @param errRespMsg
		    * @return
		    * @author wangfeiliang
		    */
		   protected Map<String, Object> returnFailMSGNew(String errRespCode, String errRespMsg) {
		       Map<String, Object> result = new HashMap<String, Object>();
		      

		       result.put("success", false);
		       result.put("msg", errRespMsg);
		       return result;
		   }

}
