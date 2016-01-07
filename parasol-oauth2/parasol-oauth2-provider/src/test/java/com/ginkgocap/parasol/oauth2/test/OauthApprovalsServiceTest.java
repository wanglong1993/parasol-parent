package com.ginkgocap.parasol.oauth2.test;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;

import com.ginkgocap.parasol.oauth2.model.OauthApprovals;
import com.ginkgocap.parasol.oauth2.service.OauthApprovalsService;

public class OauthApprovalsServiceTest  extends TestBase implements Test  {

	@Resource
	private OauthApprovalsService oauthApprovalsService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建用户权限
	 */
	@org.junit.Test
	public void testAddApprovals(){
		try {
			Collection<Approval> collection=setOauthApprovalsList(1l,"1111");
//			boolean bl=oauthApprovalsService.addApprovals(collection);
//			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除用户权限
	 */
	@org.junit.Test
	public void testRevokeApprovals(){
		try {
//			Collection<Approval> collection=oauthApprovalsService.getApprovals("1212121", "111212321");
//			boolean bl=oauthApprovalsService.revokeApprovals(collection);
//			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询用户权限列表
	 */
	@org.junit.Test
	public void testGetApprovals(){
		try {
//			Collection<Approval> collection=oauthApprovalsService.getApprovals("1212121", "111212321");
//			for (Approval approval : collection) {
//				Assert.assertTrue(approval.getClientId()!=null);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Collection<Approval> setOauthApprovalsList(Long userId,String clientId){
		Collection<Approval> collection = new HashSet<Approval>();
		String[] scopes =new String[]{"getUserInfo","getUserFriendly"};
		for (int i = 0; i < scopes.length; i++) {
			collection.add(setOauthApprovals(userId,clientId,scopes[i]));
		}
		return collection;
	}	
	/**
	 * 初始化登录注册用户对象
	 * @return oauthClientDetails
	 */
	public Approval setOauthApprovals(Long userId,String clientId,String scope ){
		try {
//			Approval oauthApprovals = new OauthApprovals();
//			oauthApprovals.setClientId("111212321");
//			oauthApprovals.setUserId("1212121");
//			oauthApprovals.setStatus(ApprovalStatus.DENIED);
//			oauthApprovals.setExpiresAt(new Date());
//			oauthApprovals.setLastUpdatedAt(new Date());
//			oauthApprovals.setScope(scope);
//			return oauthApprovals;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
