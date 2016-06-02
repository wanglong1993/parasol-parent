package com.ginkgocap.parasol.user.model;

import java.util.HashMap;
import java.util.Map;

public class ModelType {
	public final static int UB=1;//用户基本信息
	public final static int UC=2;//用户联系方式
	public final static int UD=3;//用户自定义
	public final static int UDN=4;//用户描述
	public final static int UEH =5;//用户教育经历
	public final static int UFM =6;//用户家庭成员
	public final static int UIO =7;//用户基本情况
	public final static int UIG =8;//用户兴趣爱好
	public final static int US =9;//用户专业技能
	public final static int UWH =10;//用户工作经历
	public final static int UA = 11;//用户相关附件
	public static Map<String,Integer> ModelTypeMap = new HashMap<String,Integer>();
	public static Integer[] MODELS = new Integer[11];
	static{
		ModelTypeMap.put("UB", UB);
		ModelTypeMap.put("UC", UC);
		ModelTypeMap.put("UD", UD);
		ModelTypeMap.put("UDN", UDN);
		ModelTypeMap.put("UEH", UEH);
		ModelTypeMap.put("UFM", UFM);
		ModelTypeMap.put("UIO", UIO);
		ModelTypeMap.put("UIG", UIG);
		ModelTypeMap.put("US", US);
		ModelTypeMap.put("UWH", UWH);
		ModelTypeMap.put("UA", UA);
		MODELS[0]=UB;
		MODELS[1]=UC;
		MODELS[2]=UD;
		MODELS[3]=UDN;
		MODELS[4]=UEH;
		MODELS[5]=UFM;
		MODELS[6]=UIO;
		MODELS[7]=UIG;
		MODELS[8]=US;
		MODELS[9]=UWH;
		MODELS[10]=UA;
	}
	public static int getModelType(String modelKey){
		return ModelTypeMap.get(modelKey);
	}
	public static Integer[] getModelType(){
		return MODELS;
	}
}
