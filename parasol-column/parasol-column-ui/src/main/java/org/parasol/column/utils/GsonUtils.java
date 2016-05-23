package org.parasol.column.utils;

import com.google.gson.Gson;
/**
 * 用与json和对象之间的转换
 * @author kaihu
 */
public final class GsonUtils {
	private static final Gson GSON = CommonUtil.createGson();

	public static String objectToString(Object targetObj) {
		return GSON.toJson(targetObj);
	}

	public static <T> T StringToObject(Class<T> classOfT, String targetJson) {
		return GSON.fromJson(targetJson, classOfT);
	}

	private GsonUtils() {
	}
}
