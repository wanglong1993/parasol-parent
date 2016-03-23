package com.ginkgocap.parasol.comment.test;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class Main {
	public static void main(String[] args) {
		String[] parentIds = StringUtils.split("1-2-3-4-5-6-7-8","-");
		parentIds = Arrays.copyOfRange(parentIds, parentIds.length - 3, parentIds.length);
		parentIds=new String[]{};
		System.out.println(StringUtils.join(parentIds, "-"));
		String cc = new String();
		
		System.out.println(cc.getClass().getPackage().getName());
	}
}
