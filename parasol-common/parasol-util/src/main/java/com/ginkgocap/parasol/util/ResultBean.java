package com.ginkgocap.parasol.util;

import net.sf.json.JSONObject;

public class ResultBean {
	
	//{"responseData": {},"notification": {"notifCode": "","notifInfo": ""}}
    //错误信息
	public static String error_info() {
		return "{\"responseData\": {},\"notification\": {\"notifCode\": \"\",\"notifInfo\": \"\"}}";		
	}
	
	
	
	//完整信息
	public static JSONObject return_info(JSONObject data,JSONObject notif) {
		JSONObject result = new JSONObject();
		result.put("responseData", data);
		result.put("notifCode", notif);
		return result;
	}
	
	
	
	public static String return_info(JSONObject data) {
		 return "{\"responseData\": " + data +",\"notification\": {\"notifCode\": \"\",\"notifInfo\": \"\"}}";	
	}

}
