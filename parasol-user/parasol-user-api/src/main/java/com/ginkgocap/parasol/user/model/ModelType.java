package com.ginkgocap.parasol.user.model;

public enum ModelType {
	UB(1),//用户基本信息
	UC(2),//用户联系方式
	UD(3),//用户自定义
	UDN(4),//用户描述
	UEH(5),//用户教育经历
	UFM(6),//用户家庭成员
	UIO(7),//用户基本情况
	UIG(8),//用户兴趣爱好
	US(9),//用户专业技能
	UWH(10);//用户工作经历
	private int value;
	ModelType(int value){
		this.value=value;
	}
	public int getValue(){
		return value;
	}
}
