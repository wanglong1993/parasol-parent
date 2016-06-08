package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile.mydata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;

public class CommentApprover implements Serializable {

	private static final long serialVersionUID = -4070410790675738338L;

	private Long ueid;
	private String comment; //评价内容
	private Boolean feedback; //评价反馈
	private List<ApproverMiNi> listApproverMiNi; //赞同者对象集合

	public String getComment() {
		if(null == comment){
            comment = "";
        }
        return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isFeedback() {
        if(null == feedback){
            feedback = false;
        }
		return feedback;
	}

	public void setFeedback(boolean feedback) {
		this.feedback = feedback;
	}

	public Long getUeid() {
        if(null == ueid){
            ueid = 0l;
        }
        return ueid;
	}

	public void setUeid(Long ueid) {
		this.ueid = ueid;
	}

	public CommentApprover() {
		super();
	}

	public List<ApproverMiNi> getListApproverMiNi() {
        if(null == listApproverMiNi){
            listApproverMiNi = new ArrayList<ApproverMiNi>();
        }
        return listApproverMiNi;
	}

	public void setListApproverMiNi(List<ApproverMiNi> listApproverMiNi) {
		this.listApproverMiNi = listApproverMiNi;
	}

	public CommentApprover(String comment, boolean feedback,
			List<ApproverMiNi> listApproverMiNi,long ueid) {
		super();
		this.comment = comment;
		this.feedback = feedback;
		this.listApproverMiNi = listApproverMiNi;
		this.ueid = ueid;
	}
	
	/** 测试数据 */
	public static CommentApprover getCommentApprover(boolean feedback) {
		CommentApprover ca = new CommentApprover();
		ca.setFeedback(feedback);
		ca.setComment("测试评价内容");
		ca.setListApproverMiNi(ApproverMiNi.getApproverMiNiList());
		return ca;
	}
	
	public static List<CommentApprover> getCommentApproverList() {
		List<CommentApprover> cas = new ArrayList<CommentApprover>();
		for (int i = 0; i < 10; i++) {
			cas.add(getCommentApprover((new Random()).nextBoolean()));
		}
		return cas;
	}
	
	/** json转换方法 */
	public static CommentApprover getByJsonString(String jsonEntity) {
		if (jsonEntity.equals("{}")) {
			return null; // 无数据判断
		}
		return JSON.parseObject(jsonEntity, CommentApprover.class);
	}

	public static CommentApprover getByJsonObject(Object jsonEntity) {
		return getByJsonString(jsonEntity.toString());
	}

	public static List<CommentApprover> getCommentApproverListByJsonString(String object) {
		return JSON.parseArray(object, CommentApprover.class);
	}

	public static List<CommentApprover> getCommentApproverListByJsonObject(Object object) {
		return getCommentApproverListByJsonString(object.toString());
	}
	
}
