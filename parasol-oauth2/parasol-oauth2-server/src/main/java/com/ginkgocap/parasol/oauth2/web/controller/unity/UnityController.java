package com.ginkgocap.parasol.oauth2.web.controller.unity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ginkgocap.parasol.oauth2.model.SecurityUserDetails;
import com.ginkgocap.parasol.oauth2.service.UserDetailService;
import com.ginkgocap.parasol.oauth2.service.UserService;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/openapi/")
public class UnityController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService userDetailService;
    
    @Value("#{properties['getIdentifyingCode']}")
    private String getIdentifyingCode;

    @RequestMapping("/user/login")
    @ResponseBody
    public JSONObject login(HttpServletRequest request) {
    	JSONObject json = new JSONObject();
        return json.accumulate("id", 1212112212).accumulate("passport", "esofmdsa@126.com");
    }

//    @RequestMapping("/user/getIdentifyingCode")
//    @ResponseBody
//    	public JSONObject getIdentifyingCode(HttpServletRequest request) throws Exception {
//    	CloseableHttpClient httpClient = null;  
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//    	HttpEntity entity =null;
//    	JSONObject json = null;
//    	String passport=request.getParameter("passport");
//    	try{
//	        try{
//	        	RequestConfig defaultRequestConfig = RequestConfig.custom()
//	        			  .setSocketTimeout(5000)
//	        			  .setConnectTimeout(5000)
//	        			  .setConnectionRequestTimeout(5000)
//	        			  .setStaleConnectionCheckEnabled(true)
//	        			  .build();
//	        	httpClient = HttpClients.custom()
//	        			.setDefaultRequestConfig(defaultRequestConfig)
//	        			.build();
//	        	RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
//	        		.setProxy(new HttpHost("localhost", 8088))
//	        	    .build();
//	            HttpPost httpPost = new HttpPost("http://localhost:8088/user/getIdentifyingCode");
//	            HttpEntity reqEntity = MultipartEntityBuilder.create()  
//	            .addPart("passport",  new StringBody(passport, ContentType.create("text/plain", Consts.UTF_8)))
//	            .build();  
//	            httpPost.setEntity(reqEntity);  
//	            httpPost.setConfig(requestConfig);
//	            CloseableHttpResponse response = httpClient.execute(httpPost);  
//	            try {  
//					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//						resultMap.put("status", response.getStatusLine().getStatusCode());
//						entity = response.getEntity();
//						String respJson = EntityUtils.toString(entity);
//						json = JSONObject.fromObject(respJson);
//					}
//	                EntityUtils.consume(entity);
//	                return json;
//	            } finally {  
//	                response.close();  
//	            }  
//	        }finally{  
//	            httpClient.close();  
//	        }
//    	}catch(Exception  e){
//    		throw e;
//    	}
//        }
	@RequestMapping("/user/getIdentifyingCode")
	@ResponseBody
	public void getIdentifyingCodePost(HttpServletRequest request,HttpServletResponse response, RedirectAttributesModelMap modelMap)throws Exception {
		
		modelMap.addAttribute("passport", request.getParameter("passport"));
		final Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		final Object principal = authentication.getPrincipal();
		SecurityUserDetails ocd=(SecurityUserDetails)principal;
		if(!ocd.user().getPassport().equals(modelMap.get("passport"))){
			doBgPostReq(response,getIdentifyingCode,modelMap); 
		}
	}

	/**
	 * @Description: 后台进行POST请求(请写在代码执行结尾)
	 * @return void 返回类型
	 */
	public static void doBgPostReq(HttpServletResponse response,
			String postUrl, Map<String, ?> paramMap) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html>");
		out.println("<form name='postSubmit' method='GET' action='" + postUrl
				+ "' >");
		for (String key : paramMap.keySet()) {
			out.println("<input type='hidden' name='" + key + "' value='"
					+ paramMap.get(key) + "'>");
		}
		out.println("</form>");
		out.println("<script>");
		out.println("  document.postSubmit.submit()");
		out.println("</script>");
		out.println("</html>");
	}
}