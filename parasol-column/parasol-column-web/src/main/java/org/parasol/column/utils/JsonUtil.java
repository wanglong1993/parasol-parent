package org.parasol.column.utils;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * json 工具类
 * <p>  </p>
 * @date 2015-3-26
 */
public class JsonUtil {
	public static String getNodeToString(JSONObject jsonObj,String key){
		return getNodeToString(jsonObj, key, "");
	}
	public static String getNodeToString(JSONObject jsonObj,String key,String defaultvalue){
		if(jsonObj.get(key)==null || StringUtils.isEmpty(String.valueOf(jsonObj.get(key)))){
			return defaultvalue;
		}else{
			return StringUtils.trim(String.valueOf(jsonObj.get(key)));
		}
	}
	public static int getNodeToInt(JSONObject jsonObj,String key ){
		return getNodeToInt(jsonObj, key,0);
	}
	public static int getNodeToInt(JSONObject jsonObj,String key ,int defaultvalue){
		if(StringUtils.isEmpty(getNodeToString(jsonObj, key))){
			return defaultvalue;
		}else{
			return Integer.valueOf(getNodeToString(jsonObj, key));
		}
	}
	public static Long getNodeToLong(JSONObject jsonObj,String key){
		return getNodeToLong(jsonObj, key,0L);
	}
	public static Long getNodeToLong(JSONObject jsonObj,String key,long defaultvalue){
		if(StringUtils.isEmpty(getNodeToString(jsonObj, key))){
			return defaultvalue;
		}else{
			return Long.valueOf(getNodeToString(jsonObj, key));
		}
	}
	public static  JsonNode getJsonNode(String jsonStr, String... values)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(jsonStr);

		if (values != null && values.length > 0) {
			for (String v : values) {
				node = node.path(v);
			}
		}
		return node;
	}
	/**
	 * 判断json串里有无属性key
	 * @author wfl
	 */
	public static boolean isContainkey(JSONObject jo,String key){
		boolean sign=true;
		if(jo.get(key)==null){
			sign=false;
		}
		return sign;
	}
}
