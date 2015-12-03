package com.ginkgocap.parasol.user.thirdlogin.user;

import net.sf.json.JSONObject;

import com.ginkgocap.parasol.user.thirdlogin.Constants;
import com.ginkgocap.parasol.user.thirdlogin.HttpsClientUtils;
import com.ginkgocap.parasol.user.thirdlogin.exception.QQException;

/**
 * @author yanweiqi
 *
 */
public class UserInfo {

	private String json;
	private String nikeName;
	private String figureurl;
	private String gender;
	
	public UserInfo(String access_token,String openid) throws QQException{
		StringBuilder get_user_info_parames = new StringBuilder();
		get_user_info_parames.append("access_token="+access_token);
		get_user_info_parames.append("&oauth_consumer_key="+Constants.APP_CLINET_ID);
		get_user_info_parames.append("&openid="+openid);
		get_user_info_parames.append("&format=json");
		json = HttpsClientUtils.httpsRequest(Constants.GET_USER_INFO, "GET", get_user_info_parames.toString());
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(0 != jsonObject.getInt("ret")){
			throw new QQException(json);
		}
		int get_user_info_status = jsonObject.getInt("ret");
		if(0 != get_user_info_status) throw new QQException(json);
		nikeName = jsonObject.getString("nickname"); 
		figureurl =  jsonObject.getString("figureurl_qq_2");
		gender = jsonObject.getString("gender");
	    
	}

	public String getJson() {
		return json;
	}

	public String getNikeName() {
		return nikeName;
	}

	public String getFigureurl() {
		return figureurl;
	}

	public String getGender() {
		return gender;
	}
	
}
