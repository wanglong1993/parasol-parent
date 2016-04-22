package org.parasol.column.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
private static ObjectMapper mapper;
	
	/**
	 * 获取ObjectMapper实例
	 * @param createNew 方式：true，新实例；false,存在的mapper实例
	 * @return
	 */
	public static synchronized ObjectMapper getMapper() {   
		if (mapper == null) {   
            mapper = new ObjectMapper();   
        }    
        return mapper;   
    } 
	
	/**
	 * 将java对象转换成json字符串
	 * @param obj 准备转换的对象
	 * @return json字符串
	 * @throws Exception 
	 */
	public static String beanToJson(Object obj) throws Exception {
		try {
			ObjectMapper objectMapper = getMapper();
			String json =objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}		
	}
	
	 public  static <T> List<T>  jsonToList(String in, Class<T> clsT) throws IOException {

		 ObjectMapper objectMapper = getMapper();
	       JsonParser parser = objectMapper.getJsonFactory().createJsonParser(in);   

	        JsonNode nodes = parser.readValueAsTree();
	        List<T> list  = new ArrayList<T>(nodes.size());
	        for(JsonNode node : nodes){
	            list.add(objectMapper.readValue(node, clsT));
	        }
	        return list;
	}
	
	
	/**
	 * 将json字符串转换成java对象
	 * @param json 准备转换的json字符串
	 * @param cls  准备转换的类
	 * @return 
	 * @throws Exception 
	 */
	public static Object jsonToBean(String json, Class<?> cls) throws Exception {
		try {
		ObjectMapper objectMapper = getMapper();
		Object vo = objectMapper.readValue(json, cls);
		return vo;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}	
	}

}
