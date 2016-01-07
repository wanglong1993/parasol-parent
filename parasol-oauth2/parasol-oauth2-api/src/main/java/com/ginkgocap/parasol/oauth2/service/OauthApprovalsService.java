package com.ginkgocap.parasol.oauth2.service;

import java.util.Collection;

import com.ginkgocap.parasol.oauth2.model.OauthApprovals;

/**
 * 操作用户权限信息接口定义
 * 
 */
public interface OauthApprovalsService  {
	
	public boolean addApprovals(Collection<OauthApprovals> approvals);

	public boolean revokeApprovals(Collection<OauthApprovals> approvals);

	public Collection<OauthApprovals> getApprovals(String userId, String clientId);

}
