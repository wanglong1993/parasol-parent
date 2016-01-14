package com.ginkgocap.parasol.oauth2.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_session_ids", catalog = "parasol_oauth2")
public class OauthSessionIds implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	public OauthSessionIds() {
	}

	public OauthSessionIds(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 120)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
