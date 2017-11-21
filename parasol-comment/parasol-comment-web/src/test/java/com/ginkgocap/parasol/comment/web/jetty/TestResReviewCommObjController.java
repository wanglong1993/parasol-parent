package com.ginkgocap.parasol.comment.web.jetty;

import java.util.Date;

import org.junit.Test;

import com.ginkgocap.parasol.comment.model.CommObjType;
import com.ginkgocap.parasol.comment.model.CommObjUpUser;
import com.ginkgocap.parasol.comment.model.ResReviewCommObj;
import com.ginkgocap.parasol.comment.model.Tipoff;
import com.ginkgocap.parasol.util.HttpUtils;
import com.ginkgocap.parasol.util.JsonUtils;

public class TestResReviewCommObjController {
	
	private static long System_AppId = 7647448850l;
	@Test
	public void testVoid(){
		
	}
	@Test
	public void createCommToRes() throws Exception{
		ResReviewCommObj obj=new ResReviewCommObj();
		obj.setCommObjType(CommObjType.res.getVal());
		obj.setCommTimes(0l);
		obj.setUpTimes(0l);
		obj.setAppId(System_AppId);
		obj.setResType(1);
		obj.setResId(2l);
		obj.setCreateUserId(1l);
		obj.setCreateUserName("小明");
		obj.setTargetUserId(2l);
		obj.setTargetUserName("张三");
		obj.setOwnerUserId(2l);
		obj.setMainContent("评论一下知识");
		obj.setCommObjType(CommObjType.res.getVal());
		obj.setCreateTime((new Date()).getTime());
		obj.setParentCommObjId(null);
		String jsonStr=JsonUtils.beanToJson(obj);
		System.out.println("输入："+jsonStr);
		String url="http://localhost:8091/commobj/commobj/createCommToRes?access_token=f007a2dd-04a9-429e-9e93-9d6bb7dc7091";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}
	
	@Test
	public void createCommToReview() throws Exception{
		ResReviewCommObj obj=new ResReviewCommObj();
		obj.setCommObjType(CommObjType.res.getVal());
		
		obj.setCreateUserId(2l);
		obj.setCreateUserName("张三");
		obj.setTargetUserId(1l);
		obj.setTargetUserName("小明");
		obj.setMainContent("回复评论一下知识");
		obj.setCommObjType(CommObjType.review.getVal());
		obj.setCreateTime((new Date()).getTime());
		obj.setParentCommObjId(null);
		obj.setParentCommObjId(146494133274300003l);
		String jsonStr=JsonUtils.beanToJson(obj);
		System.out.println("输入："+jsonStr);
		String url="http://localhost:8091/commobj/commobj/createCommToReview?access_token=f007a2dd-04a9-429e-9e93-9d6bb7dc7091";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}
	
	@Test
	public void testLoadResSubs() throws Exception{
		ResReviewCommObj obj=new ResReviewCommObj();
		obj.setAppId(1l);
		obj.setResType(1);
		obj.setResId(2l);
		String jsonStr=JsonUtils.beanToJson(obj);
		System.out.println("输入："+jsonStr);
		String url="http://localhost:8091/commobj/commobj/loadCommsForRes?access_token=f007a2dd-04a9-429e-9e93-9d6bb7dc7091";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}
	
	@Test
	public void testLoadReviewSubs() throws Exception{
		String url="http://localhost:8091/commobj/commobj/loadCommsForReview?access_token=f007a2dd-04a9-429e-9e93-9d6bb7dc7091&pid=146494133274300003";
		String resp=HttpUtils.sendGet(url);
		System.out.println(resp);
	}
	
	@Test
	public void testDelete(){
		String url="http://localhost:8091/commobj/commobj/deleteCommObj?access_token=f007a2dd-04a9-429e-9e93-9d6bb7dc7091&id=146494374283200001";
		String resp=HttpUtils.sendJsonPost(url, "");
		System.out.println(resp);
	}
	
	@Test
	public void createUp() throws Exception{
		CommObjUpUser obj=new CommObjUpUser();
		obj.setCreateUserId(1l);
		obj.setCreateUserName("小明");
		obj.setCommObjId(146494133274300003l);
		String jsonStr=JsonUtils.beanToJson(obj);
		System.out.println("输入："+jsonStr);
		String url="http://localhost:8091/commobj/commobj/createCommObjUpUser?access_token=f007a2dd-04a9-429e-9e93-9d6bb7dc7091";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}
	
	@Test
	public void createTipoff() throws Exception{
		Tipoff obj=new Tipoff();
		obj.setCreateUserId(1l);
		obj.setCreateUserName("小明");
		obj.setTipoffType(1);
		obj.setResType(2);
		obj.setResId(112l);
		obj.setReason("kkddk");
		String jsonStr=JsonUtils.beanToJson(obj);
		System.out.println("输入："+jsonStr);
		String url="http://localhost:8091/commobj/commobj/createTipoff?access_token=f007a2dd-04a9-429e-9e93-9d6bb7dc7091";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp); 
	}
}
