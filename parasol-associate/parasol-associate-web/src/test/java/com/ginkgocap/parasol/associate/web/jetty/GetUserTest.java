package com.ginkgocap.parasol.associate.web.jetty;

import java.util.HashMap;
import java.util.Map;

import com.ginkgocap.parasol.util.HttpUtils;
import org.junit.Test;

public class GetUserTest {
	
	@Test
	public void getUser(){
		String jsonStr="{\"pid\":\"0\"}";
		String url="http://localhost:8091/associate/associate/getAssociateDetail.json?id=1";
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("sessionID", "009055c9-670a-4faf-a29d-284b1fdeb975");
		headers.put("s", "web");
		String resp= HttpUtils.sendPost(url, jsonStr,"application/json",headers);
		System.out.println(resp);
	}

}
