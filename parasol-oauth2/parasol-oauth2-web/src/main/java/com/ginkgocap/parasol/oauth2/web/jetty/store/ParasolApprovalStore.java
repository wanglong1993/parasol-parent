package com.ginkgocap.parasol.oauth2.web.jetty.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.oauth2.model.OauthApprovals;
import com.ginkgocap.parasol.oauth2.service.OauthApprovalsService;

@Service("parasolApprovalStore")
public class ParasolApprovalStore implements ApprovalStore {
	private static Logger logger = Logger.getLogger(ParasolApprovalStore.class);
	@Resource
	private OauthApprovalsService oauthApprovalsService;

	@Override
	public boolean addApprovals(Collection<Approval> approvals) {
		List<OauthApprovals> list = new ArrayList<OauthApprovals>();
		OauthApprovals oauthApprovals = null;
		for (Approval approval : approvals) {
			if (approval != null) {
				oauthApprovals = new OauthApprovals();
				oauthApprovals.setClientId(approval.getClientId());
				oauthApprovals.setUserId(approval.getUserId());
				oauthApprovals.setScope(approval.getScope());
				oauthApprovals.setStatus(approval.getStatus());
				oauthApprovals.setExpiresAt(approval.getExpiresAt());
				oauthApprovals.setLastUpdatedAt(approval.getLastUpdatedAt());
				list.add(oauthApprovals);
			}
		}
		return oauthApprovalsService.addApprovals(list);
	}

	@Override
	public boolean revokeApprovals(Collection<Approval> approvals) {
		List<OauthApprovals> list = new ArrayList<OauthApprovals>();
		OauthApprovals oauthApprovals = null;
		for (Approval approval : approvals) {
			if (approval != null) {
				oauthApprovals = new OauthApprovals();
				oauthApprovals.setClientId(approval.getClientId());
				oauthApprovals.setExpiresAt(approval.getExpiresAt());
				oauthApprovals.setScope(approval.getScope());
				oauthApprovals.setLastUpdatedAt(approval.getLastUpdatedAt());
				oauthApprovals.setStatus_(approval.getStatus().toString());
				oauthApprovals.setUserId(approval.getUserId());
				list.add(oauthApprovals);
			}
		}
		return oauthApprovalsService.revokeApprovals(list);
	}

	@Override
	public Collection<Approval> getApprovals(String userId, String clientId) {
		Collection<OauthApprovals> collection = oauthApprovalsService.getApprovals(userId, clientId);
		Collection<Approval> collection2 = new HashSet<Approval>();
		Approval approval = null;
		for (OauthApprovals oauthApprovals : collection) {
			if (oauthApprovals != null) {
				approval = new Approval(oauthApprovals.getUserId(), oauthApprovals.getClientId(), oauthApprovals.getScope(), oauthApprovals.getExpiresAt(),
						oauthApprovals.getStatus(), oauthApprovals.getLastUpdatedAt());
				collection2.add(approval);
			}
		}
		return collection2;
	}
}