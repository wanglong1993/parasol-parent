package com.ginkgocap.parasol.organ.web.jetty.web.model.mydata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;

public class ApproverMiNi implements Serializable {

	private static final long serialVersionUID = -1453649884629257301L;

	private Long userId; // 用户id
	private String imageUrl; // 用户头像地址
	private Boolean isonline;// 是否为线上用户 true/false

	public long getUserId() {
        if(null == userId){
            userId = 0L;
        }
        return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getImageUrl() {
        if(null == imageUrl){
            imageUrl = "";
        }
        return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean isIsonline() {
        if(null == isonline){
            isonline = false;
        }
        return isonline;
	}

	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}

	public ApproverMiNi() {
		super();
	}

	public ApproverMiNi(long userId) {
		super();
		this.userId = userId;
	}

	public ApproverMiNi(long userId, String imageUrl, boolean isonline) {
		super();
		this.userId = userId;
		this.imageUrl = imageUrl;
		this.isonline = isonline;
	}
	
	/** 测试数据 */
	public static ApproverMiNi getApproverMiNi(boolean isonline) {
		ApproverMiNi amn = new ApproverMiNi();
		amn.setImageUrl("http://ww2.sinaimg.cn/bmiddle/e5ff9289jw1eo5xcmd5n3j20k00k0abn.jpg");
		amn.setIsonline(isonline);
		amn.setUserId((int) (Math.random() * 100));
		return amn;
	}
	
	public static List<ApproverMiNi> getApproverMiNiList() {
		List<ApproverMiNi> amns = new ArrayList<ApproverMiNi>();
		for (int i = 0; i < 10; i++) {
			amns.add(getApproverMiNi((new Random()).nextBoolean()));
		}
		return amns;
	}
	
	/** json转换方法 */
	public static ApproverMiNi getByJsonString(String jsonEntity) {
		if (jsonEntity.equals("{}")) {
			return null; // 无数据判断
		}
		return JSON.parseObject(jsonEntity, ApproverMiNi.class);
	}

	public static ApproverMiNi getByJsonObject(Object jsonEntity) {
		return getByJsonString(jsonEntity.toString());
	}

	public static List<ApproverMiNi> getApproverMiNiListByJsonString(String object) {
		return JSON.parseArray(object, ApproverMiNi.class);
	}

	public static List<ApproverMiNi> getApproverMiNiListByJsonObject(Object object) {
		return getApproverMiNiListByJsonString(object.toString());
	}
}
