package com.ginkgocap.parasol.oauth2.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.oauth2.model.OauthApprovals;
import com.ginkgocap.parasol.oauth2.service.OauthApprovalsService;

@Service("oauthApprovalsService")
public class OauthApprovalsServiceImpl extends BaseService<OauthApprovals> implements OauthApprovalsService {

	private static Logger logger = Logger.getLogger(OauthApprovalsServiceImpl.class);
	private static String OauthApprovals_List_Id="OauthApprovals_List_Id";
	@Override
	public boolean addApprovals(Collection<OauthApprovals> approvals) {
		List<OauthApprovals> list=new ArrayList<OauthApprovals>();
		OauthApprovals oauthApprovals=null;
		for (OauthApprovals approval : approvals) {
			if(approval!=null){
				oauthApprovals=new OauthApprovals();
				oauthApprovals.setClientId(approval.getClientId());
				oauthApprovals.setUserId(approval.getUserId());
				oauthApprovals.setScope(approval.getScope());
				oauthApprovals.setStatus_(approval.getStatus().toString());
				oauthApprovals.setExpiresAt(approval.getExpiresAt());
				oauthApprovals.setLastUpdatedAt(approval.getLastUpdatedAt());
				list.add(oauthApprovals);
			}
		}
		try {
			List<OauthApprovals> lists= saveEntitys(list);
			if(ObjectUtils.isEmpty(lists)){
				return false;
			}
			return true;
		} catch (BaseServiceException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean revokeApprovals(Collection<OauthApprovals> approvals) {
		String userId=null;
		String clientId=null;
		for (OauthApprovals approval : approvals) {
			userId=approval.getUserId();
			clientId=approval.getClientId();
			break;
		}
		try {
			List<Long> ids= getIds(OauthApprovals_List_Id,clientId,userId);
			if(ids==null) return false;
			deleteEntityByIds(ids);
			return true;
		} catch (BaseServiceException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public Collection<OauthApprovals> getApprovals(String userId, String clientId) {
		try {
			List<Long> ids=getIds(OauthApprovals_List_Id,clientId,userId);
			if(ids==null)return Collections.EMPTY_SET;
			List<OauthApprovals> list=getEntityByIds(ids);
			Collection<OauthApprovals> collection = new HashSet<OauthApprovals>();
			if(list==null) return Collections.EMPTY_SET;
			for (OauthApprovals oauthApprovals : list) {
				oauthApprovals.setExpiresAt(oauthApprovals.getExpiresAt());
				if(oauthApprovals.getStatus_().equals("APPROVED")){
					oauthApprovals.setStatus(ApprovalStatus.APPROVED);
				}else if(oauthApprovals.getStatus_().equals("DENIED")) {
					oauthApprovals.setStatus(ApprovalStatus.DENIED);
				}
				collection.add(oauthApprovals);
			}
			return collection;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return Collections.EMPTY_SET;
	}
}
