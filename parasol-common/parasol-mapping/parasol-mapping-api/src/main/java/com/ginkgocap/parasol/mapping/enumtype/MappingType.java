package com.ginkgocap.parasol.mapping.enumtype;

public enum MappingType {
	USER(1), KNOWLEDGE(2);

	private MappingType(int type) {
		this.type = type;
	}
	private int type; // 自定义数据域，private为了封装。
	
	public int value() {
		return type;
	}
}
