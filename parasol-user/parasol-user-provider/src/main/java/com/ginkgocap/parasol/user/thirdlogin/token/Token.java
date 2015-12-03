package com.ginkgocap.parasol.user.thirdlogin.token;

import org.apache.log4j.Logger;

import com.ginkgocap.parasol.user.thirdlogin.Constants;
import com.ginkgocap.parasol.user.thirdlogin.HttpsClientUtils;

public class Token {
	private static Logger logger = Logger.getLogger(Token.class);

	public String getAccessToken(String code,String state){
		
    	StringBuilder parames = new StringBuilder();
		parames.append("grant_type=authorization_code");
		parames.append("&client_id="+Constants.CLIENT_ID);
		parames.append("&client_secret="+Constants.CLIENT_SECRET);
		parames.append("&code="+code);
		parames.append("&state="+state);
		parames.append("&redirect_uri="+Constants.REDIRECT_URI);
    	
		String access_token = HttpsClientUtils.httpsRequest(Constants.TOKEN_URL, "GET", parames.toString());
		logger.info(access_token);
		return access_token;
	}
}
