package com.ginkgocap.parasol.user.model;

public enum TemplateType {
	DEFAULT(0),//默认模板
	DOCTOR(1),//医生
	LAYWER(2),//律师
	TEACHER(3),//教师
	HISTORY(4);//历史人物
	private int value;
	
	TemplateType(int value){
		this.value=value;
	}
	public int getValue(){
		return value;
	}
}
