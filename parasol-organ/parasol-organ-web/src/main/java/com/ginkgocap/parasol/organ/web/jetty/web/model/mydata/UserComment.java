package com.ginkgocap.parasol.organ.web.jetty.web.model.mydata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;

public class UserComment implements Serializable {

	private static final long serialVersionUID = 4019623433084062295L;

	private long id; // 主键
	private long count; // 赞同数量
	private String comment; // 评价内容
	private boolean feekback; // 当前用户是否赞同

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isFeekback() {
		return feekback;
	}

	public void setFeekback(boolean feekback) {
		this.feekback = feekback;
	}

	public UserComment() {
		super();
	}

	public UserComment(long id) {
		super();
		this.id = id;
	}

	public UserComment(long id, long count, String comment, boolean feekback) {
		super();
		this.id = id;
		this.count = count;
		this.comment = comment;
		this.feekback = feekback;
	}

	/** 测试数据方法 */
	public static UserComment getUserComment(boolean feekback) {
		UserComment uc = new UserComment();
		uc.setComment("测试评价");
		uc.setFeekback(feekback);
		uc.setCount(5);
		uc.setId((int) (Math.random() * 100));
		return uc;
	}

	/** 测试数据集合方法 */
	public static List<UserComment> getUserCommentList() {
		List<UserComment> ucs = new ArrayList<UserComment>();
		for (int i = 0; i < 10; i++) {
			ucs.add(getUserComment((new Random()).nextBoolean()));
		}
		return ucs;
	}

	/** json转换方法 */
	public static UserComment getByJsonString(String jsonEntity) {
		if (jsonEntity.equals("{}")) {
			return null; // 无数据判断
		}
		return JSON.parseObject(jsonEntity, UserComment.class);
	}

	public static UserComment getByJsonObject(Object jsonEntity) {
		return getByJsonString(jsonEntity.toString());
	}

	public static List<UserComment> getUserCommentListByJsonString(String object) {
		return JSON.parseArray(object, UserComment.class);
	}

	public static List<UserComment> getUserCommentListByJsonObject(Object object) {
		return getUserCommentListByJsonString(object.toString());
	}
}
