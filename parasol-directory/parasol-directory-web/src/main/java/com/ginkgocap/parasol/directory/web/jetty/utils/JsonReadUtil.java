package com.ginkgocap.parasol.directory.web.jetty.utils;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JsonReadUtil {
	/**
	 * 获取json字符串
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getJsonIn(HttpServletRequest request) throws IOException{
        String requestJson=(String)request.getAttribute("requestJson");
        if(requestJson==null){
        	return "";
        }
		return requestJson;
	}

    public static Integer valInt(JSONObject j, String key) throws IOException {
        Object o = j.get(key);
        if (o != null) {
            return Integer.parseInt(o + "");
        }

        return null;
    }

    public static Long valLong(JSONObject j, String key) {
        Object o = j.get(key);
        if (o != null) {
            try {
                String val = o + "";
                Long l;
                l = Long.parseLong(val);
                return l;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(11);
    }
	
//	public static List<String> getArrayString(JSONArray jsonArray){
//		List<String> ret = new ArrayList<String>;
//		try{
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return ret;
//	}
}
