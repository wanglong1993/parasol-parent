package com.ginkgocap.parasol.oauth2.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.oauth2.provider.approval.Approval;



/**
 * oauth2用户允许访问应用的哪些scope
 */
@Entity
@Table(name = "oauth_approvals", catalog = "parasol_oauth2")
public class OauthApprovals extends Approval implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String userId;

	private String clientId;

	private String scope;
	
	private String status_;
	
	private Date expiresAt;

	private Date lastUpdatedAt;
	

	public OauthApprovals() { }

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "userId", length = 256)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? "" : userId;
	}

	@Column(name = "clientId", length = 256)
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId == null ? "" : clientId;
	}

	@Column(name = "scope", length = 256)
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope == null ? "" : scope;
	}

	@Column(name = "expiresAt", nullable = false, length = 19)
	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		if (expiresAt == null) {
			Calendar thirtyMinFromNow = Calendar.getInstance();
			thirtyMinFromNow.add(Calendar.MINUTE, 30);
			expiresAt = thirtyMinFromNow.getTime();
		}
		this.expiresAt = expiresAt;
	}

	@Column(name = "lastModifiedAt", nullable = false, length = 19)
	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	@Transient
	public boolean isCurrentlyActive() {
		return expiresAt != null && expiresAt.after(new Date());
	}

	@Transient
	public boolean isApproved() {
		return isCurrentlyActive() && status_.equals("APPROVED");
	}

	public void setStatus_(String status) {
		this.status_ = status;
	}
	
	@Column(name = "status", length = 10)
	public String getStatus_() {
		return this.status_;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userId.hashCode();
		result = prime * result + clientId.hashCode();
		result = prime * result + scope.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof OauthApprovals)) {
			return false;
		}
		OauthApprovals other = (OauthApprovals) o;
		return userId.equals(other.userId) && clientId.equals(other.clientId) && scope.equals(other.scope) && status_ == other.status_;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s, %s, %s, %s]", userId, scope, clientId, expiresAt, status_.toString(), lastUpdatedAt);
	}	
}
