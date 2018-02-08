package com.ginkgocap.parasol.tags.web.jetty;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ginkgocap.parasol.util.HttpUtils;



public class GetUserTest {
	
	@Test
	public void getUser(){
		String jsonStr="{\"pid\":\"0\"}";
		String url="http://localhost:8088/tags/test";
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("sessionID", "009055c9-670a-4faf-a29d-284b1fdeb975");
		headers.put("s", "web");
		String resp=HttpUtils.sendPost(url, jsonStr,"application/json",headers); 
		System.out.println(resp);
	}

}
