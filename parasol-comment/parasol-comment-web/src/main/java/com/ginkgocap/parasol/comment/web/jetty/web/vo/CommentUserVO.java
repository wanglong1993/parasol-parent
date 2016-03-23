package com.ginkgocap.parasol.comment.web.jetty.web.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.ginkgocap.parasol.comment.model.Comment;
import com.ginkgocap.parasol.user.model.UserBasic;

/**
 * Value Object
 * @author allenshen
 * @date 2016年3月22日
 * @time 下午2:09:06
 * @Copyright Copyright©2015 www.gintong.com
 * @see Comment
 */
@JsonFilter("com.ginkgocap.parasol.comment.web.jetty.web.vo.CommentUserVO")
public class CommentUserVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id; // id标识
	private long userId;// 创建这个记录的人用户（OwerID）
	private long toUserId = -1l;// 评论回复给谁
	private long appId; // 应用Id
	private long sourceTypeId; // 表示AppId中的类型
	private long sourceId; // 知识ID, 人脉ID,组织ID，等资源ID
	private String content; // 评论内容
	private long createAt; // 记录创建的时间
	private boolean visable; // 评论是否可见（审核的时候使用）

	private Map<String, Object> user = new HashMap<String, Object>();
	private Map<String, Object> toUser = new HashMap<String, Object>();

	/**
	 * 
	 * @param comment 评论信息
	 * @param fUser   谁评论的
	 * @param tUser	  回复给谁
	 */
	public CommentUserVO(Comment comment, UserBasic fUser, UserBasic tUser) {
		// this.user = new HashMap<String, Object>();
		this.id = comment.getId();
		this.userId = comment.getUserId();
		this.toUserId = comment.getToUserId();
		this.appId = comment.getAppId();
		this.sourceTypeId = comment.getSourceTypeId();
		this.sourceId = comment.getSourceId();
		this.content = comment.getContent();
		this.createAt = comment.getCreateAt();
		this.visable = comment.isVisable();

		if (fUser != null) {
			user.put("picPath", fUser.getPicPath());
			user.put("picId", fUser.getPicId());

			user.put("sex", fUser.getSex());
			user.put("name", fUser.getName());
			user.put("userId", fUser.getUserId());
		}
		
		if (tUser != null) { //回复给谁
			toUser.put("picPath", tUser.getPicPath());
			toUser.put("picId", tUser.getPicId());

			toUser.put("sex", tUser.getSex());
			toUser.put("name", tUser.getName());
			toUser.put("userId", tUser.getUserId());
		} else {
			toUser.put("userId", -1);
		}
	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}

	public long getToUserId() {
		return toUserId;
	}

	public long getAppId() {
		return appId;
	}

	public long getSourceTypeId() {
		return sourceTypeId;
	}

	public long getSourceId() {
		return sourceId;
	}

	public String getContent() {
		return content;
	}

	public long getCreateAt() {
		return createAt;
	}

	public boolean isVisable() {
		return visable;
	}

	public Map<String, Object> getUser() {
		return user;
	}


	

}
