package com.ginkgocap.parasol.comment.test;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ginkgocap.parasol.comment.exception.CommObjServiceException;
import com.ginkgocap.parasol.comment.exception.ResReviewCommObjServiceException;
import com.ginkgocap.parasol.comment.model.CommObj;
import com.ginkgocap.parasol.comment.model.CommObjType;
import com.ginkgocap.parasol.comment.model.ResReviewCommObj;
import com.ginkgocap.parasol.comment.service.CommObjService;
import com.ginkgocap.parasol.comment.service.ResReviewCommObjService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CommObjServiceTest {
	
	private static long System_AppId = 7647448850l;
	
	@Autowired
	private CommObjService commObjService;

	@Autowired
	private ResReviewCommObjService ResReviewCommObjService;
	
	@Test
	public void testSaveCommObj() throws CommObjServiceException{
		CommObj obj=new CommObj();
		obj.setCommObjType(CommObjType.res.getVal());
		obj.setCommTimes(0l); 
		obj.setUpTimes(0l); 
		obj.setAppId(System_AppId);
		this.commObjService.createCommObj(obj);
	}
	
	@Test
	public void testSaveResReviewCommObj() throws ResReviewCommObjServiceException{
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
		this.ResReviewCommObjService.createResReviewCommObj(obj);
	}

}
