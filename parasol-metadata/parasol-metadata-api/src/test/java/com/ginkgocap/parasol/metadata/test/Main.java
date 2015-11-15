package com.ginkgocap.parasol.metadata.test;

import com.ginkgocap.parasol.metadata.type.CodeRegionType;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = Long.toString(9999, 36).toUpperCase();
		System.out.println(a);
		
		System.out.println(CodeRegionType.TYPE_CHINAINLAND.getValue());
	}
}
