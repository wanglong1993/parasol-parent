package com.andaily.springoauth.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.andaily.springoauth.service.OauthService;
import com.andaily.springoauth.service.dto.AuthCallbackDto;
import com.andaily.springoauth.service.dto.AuthCodeDto;
import com.andaily.springoauth.service.dto.AuthTokenDto;


@Controller
public class AuthCodeController {


    private static final Logger LOG = LoggerFactory.getLogger(AuthCodeController.class);

    
    @Value("#{properties['oauth-authorize-uri']}")
    private String oauthAuthorizeUri;
    
    
    @Value("#{properties['client-host']}")
    private String host;
    
    
    @Value("#{properties['unityUserInfoUri']}")
    private String unityUserInfoUri;

    @Value("#{properties['oauth-token-uri']}")
    private String oauthTokenUri;

    @Autowired
    private OauthService oauthService;
    
    @RequestMapping(value = "getAuthParamter", method = RequestMethod.GET)
    public String authCode(Model model,HttpServletRequest request) {
        model.addAttribute("oauthAuthorizeUri", oauthAuthorizeUri);
        model.addAttribute("host", host);
        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        String state=UUID.randomUUID().toString();
        model.addAttribute("state",state );
        WebUtils.saveState(request, state);
        return "getAuthParamter";
    }


    /*
   * Save state firstly
   * Redirect to oauth-server login page:   step-2
   * */
    @RequestMapping(value = "authorize", method = RequestMethod.POST)
    public String submitAuthorizationCode(AuthCodeDto codeDto, HttpServletRequest request) throws Exception {
        //save stats  firstly
        WebUtils.saveState(request, codeDto.getState());

        final String fullUri = codeDto.getFullUri();
        LOG.debug("Redirect to Oauth-Server URL: {}", fullUri);
        return "redirect:" + fullUri;
    }


    /*
   * Oauth callback (redirectUri):   step-3
   *
   * Handle 'code', go to 'access_token' ,validation oauth-server response data
   *
   *  authorization_code_callback
   * */
    @RequestMapping(value = "auth_code_callback")
    public String authorizationCodeCallback(AuthCallbackDto callbackDto, HttpServletRequest request, Model model) throws Exception {

        if (callbackDto.error()) {
            model.addAttribute("message", callbackDto.getError_description());
            model.addAttribute("error", callbackDto.getError());
            return "redirect:oauth_error";
        } else if (correctState(callbackDto, request)) {
            AuthTokenDto authTokenDto= new AuthTokenDto().setOauthTokenUri(oauthTokenUri).setCode(callbackDto.getCode());
            model.addAttribute("authTokenDto", authTokenDto);
            model.addAttribute("host", host);
            return "oauth_token";
        } else {
            //illegal state
            model.addAttribute("message", "Illegal \"state\": " + callbackDto.getState());
            model.addAttribute("error", "Invalid state");
            return "redirect:oauth_error";
        }

    }


    /**
     * Use HttpClient to get access_token :   step-4
     * <p/>
     * Then, 'authorization_code' flow is finished,  use 'access_token'  visit resources now
     *
     * @param tokenDto AuthAccessTokenDto
     * @param model    Model
     * @return View
     * @throws Exception
     */
    @RequestMapping(value = "oauth_token", method = RequestMethod.POST)
    @ResponseBody
    public String codeAccessToken(AuthTokenDto authTokenDto, Model model) throws Exception {
    	return getToken(authTokenDto);
//        if (authTokenDto.) {
//            model.addAttribute("message", authTokenDto.getErrorDescription());
//            model.addAttribute("error", authTokenDto.getError());
//            return "oauth_error";
//        } else {
//            model.addAttribute("accessTokenDto", accessTokenDto);
//            model.addAttribute("unityUserInfoUri", unityUserInfoUri);
//            return "access_token_result";
//        }
    }
    
    private String getToken(AuthTokenDto authTokenDto){
		CloseableHttpClient httpClient = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpEntity entity = null;
		JSONObject json = null;
		try {
			try {
				RequestConfig defaultRequestConfig = RequestConfig.custom()
						.setSocketTimeout(5000).setConnectTimeout(5000)
						.setConnectionRequestTimeout(5000)
						.setStaleConnectionCheckEnabled(true).build();
				httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
				List<NameValuePair> list = new ArrayList<NameValuePair>();  
				HttpPost httpPost = new HttpPost(authTokenDto.getOauthTokenUri());
	            Iterator iterator = authTokenDto.getAuthCodeParams().entrySet().iterator();
	            while(iterator.hasNext()){
	            	Entry<String,String> elem = (Entry<String, String>) iterator.next(); 
	            	list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
	            }
	            if(list.size() > 0){  
	                entity = new UrlEncodedFormEntity(list,"utf-8");  
	                httpPost.setEntity(entity);  
	            } 
				CloseableHttpResponse response1 = httpClient.execute(httpPost);
				try {
					resultMap.put("status", response1.getStatusLine()
							.getStatusCode());
					entity = response1.getEntity();
					String respJson = EntityUtils.toString(entity);
					return respJson;
				} finally {
					response1.close();
				}
			} finally {
				httpClient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "";
    }


    /*
     * Check the state is correct or not after redirect from Oauth Server.
     */
    private boolean correctState(AuthCallbackDto callbackDto, HttpServletRequest request) {
        final String state = callbackDto.getState();
        return WebUtils.validateState(request, state);
    }

}