package com.ginkgocap.parasol.user.thirdlogin.autho;

import com.ginkgocap.parasol.user.thirdlogin.Constants;

public class Autho2 {
	
	/**
	 * 
	 * @return redirect
	 */
	public String getRedirect(){
		
		StringBuilder auto_code_parames = new StringBuilder();
		auto_code_parames.append(Constants.AUTHORIZEURL);
		auto_code_parames.append("?response_type=code");
		auto_code_parames.append("&client_id="+Constants.CLIENT_ID);
		auto_code_parames.append("&redirect_uri="+Constants.REDIRECT_URI);
		auto_code_parames.append("&scope=");
		String parames = auto_code_parames.toString();
		//String  redirect = HttpsClientUtils.httpsRequest(Constants.AUTHORIZEURL, "GET",parames);
        return parames;
	}

}
