package com.ginkgocap.parasol.metadata.type;

public enum CodeRegionType {

	/**
	 * 国内
	 */
	TYPE_CHINAINLAND("国内", 0),
	/**
	 * 国内城市
	 */
	TYPE_CHINAINLAND_CITY("国内城市", 10),

	/**
	 * 台湾
	 */
	TYPE_TAIWAN("台湾", 1),

	/**
	 * 港澳台
	 */
	TYPE_GANGAOTAI("港澳台", 2),
	/**
	 * 马来西亚
	 */
	TYPE_MALAYSIA("马来西亚", 3),
	/**
	 * 国外
	 */
	TYPE_FOREIGNCOUNTRY("国外", 4);


	private String name;
	private int value;

	private CodeRegionType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	
	public String getName() {
		return this.name;
	}
	
}
