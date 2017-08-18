package org.parasol.column.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @Title: ParamsUtils.java
 * @Package com.ginkgocap.ywxt.utils
 * @Description:
 * @author haiyan
 * @date 2015-5-28 下午2:57:57
 */
public class ParamsUtils {
	private ObjectMapper objectMapper = null;

	private JsonNode jsonNode;

	public ParamsUtils(String jsonStr) {
		objectMapper = new ObjectMapper();
		init(jsonStr);
	}

	/**
	 * @param jsonStrl
	 * @author haiyan
	 */
	private void init(String jsonStr) {
		try {
			jsonNode = objectMapper.readTree(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStringValue(String key) {
		if (jsonNode != null) {
			JsonNode jn = jsonNode.path(key);
			return jn == null ? null : jn.asText();
		}
		return null;
	}
}
