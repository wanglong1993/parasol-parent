package com.ginkgocap.parasol.organ.web.jetty.web.utils;

public class ValidateTagUtil {

	/**
	 * 校验标签
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public static void  validateTag(String name) throws Exception {
		if (name!= null && name.length() > 20) {
			throw new Exception("标签"+name+"长度过长:"+name.length());
		}
		
	}
	public static void main(String[] args) {
		try {
			validateTag("123456789我0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

}
