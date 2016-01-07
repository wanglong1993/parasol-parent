package com.ginkgocap.parasol.oauth2.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.oauth2.provider.approval.Approval;
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
	public boolean addApprovals(Collection<Approval> approvals) {
		List<OauthApprovals> list=new ArrayList<OauthApprovals>();
		OauthApprovals oauthApprovals=null;
		for (Approval approval : approvals) {
			oauthApprovals=new OauthApprovals();
			oauthApprovals.setClientId(approval.getClientId());
			oauthApprovals.setUserId(approval.getUserId());
			oauthApprovals.setScope(approval.getScope());
			oauthApprovals.setStatus_(approval.getStatus().toString());
			oauthApprovals.setExpiresAt(approval.getExpiresAt());
			oauthApprovals.setLastUpdatedAt(approval.getLastUpdatedAt());
			list.add(oauthApprovals);
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
	public boolean revokeApprovals(Collection<Approval> approvals) {
		List<Long> ids=new ArrayList<Long>();
		OauthApprovals oauthApprovals=null;
		for (Approval approval : approvals) {
			oauthApprovals=(OauthApprovals) approval;
			ids.add(oauthApprovals.getId());
		}
		try {
			deleteEntityByIds(ids);
			return true;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Collection<Approval> getApprovals(String userId, String clientId) {
		try {
			List<Long> ids=getIds(OauthApprovals_List_Id,clientId,userId);
			List<OauthApprovals> list=getEntityByIds(ids);
			Collection<Approval> collection = new HashSet<Approval>();
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
		return null;
	}
}
