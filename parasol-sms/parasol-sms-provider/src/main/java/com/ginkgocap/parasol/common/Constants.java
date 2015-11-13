package com.ginkgocap.parasol.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public static Map<Integer, String> modelSet = new HashMap<Integer, String>();
	
	static {
		modelSet.put(1, "好友/人脉");
		modelSet.put(2, "组织/客户");
		modelSet.put(3, "需求");
		modelSet.put(4, "知识");
		modelSet.put(5, "会议");
	}
	
}
