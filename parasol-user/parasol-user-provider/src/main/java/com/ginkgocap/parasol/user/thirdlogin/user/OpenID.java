package com.ginkgocap.parasol.user.thirdlogin.user;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.ginkgocap.parasol.user.thirdlogin.Constants;
import com.ginkgocap.parasol.user.thirdlogin.HttpsClientUtils;
import com.ginkgocap.parasol.user.thirdlogin.exception.QQException;

/**
 * 
 * @author yanweiqi
 *
 */
public class OpenID {
	
	private String openid;
	private String clientid;
	private String json;
    
	/**
	 * 构造函数
	 * @param access_token 必填
	 */
	public OpenID(String access_token) throws QQException{
		json = HttpsClientUtils.httpsRequest(Constants.OPENID_URL, "GET", "access_token="+access_token);
		if(json.contains("error")){
		   throw new QQException(json);
		}
		String temp = StringUtils.substringBetween(json, "(", ")");
		JSONObject j =JSONObject.fromObject(temp);
		openid = j.getString("openid").toString();
		clientid = j.getString("client_id");
	}

	public String getJson() {
		return json;
	}
	
	public String getOpenid() {
		return openid;
	}

	public String getClientid() {
		return clientid;
	}
}

