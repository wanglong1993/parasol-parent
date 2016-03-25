package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户设置
 */
@Entity
@Table(name = "tb_user_config", catalog = "parasol_user")
public class UserConfig implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	/**
	 * 用户ID.
	 */
	private Byte userId;
	/**
	 * 浏览我的主页.
	 */
	private Byte homePageVisible;
	/**
	 * 对我评价.
	 */
	private Byte evaluateVisible;
	/**
	 * 自动保存 .
	 */
	private Byte autosave;

	public UserConfig() {
	}

	public UserConfig(long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public Byte getUserId() {
		return this.userId;
	}

	public void setUserId(Byte userId) {
		this.userId = userId;
	}

	@Column(name = "home_page_visible")
	public Byte getHomePageVisible() {
		return this.homePageVisible;
	}

	public void setHomePageVisible(Byte homePageVisible) {
		this.homePageVisible = homePageVisible;
	}

	@Column(name = "evaluate_visible")
	public Byte getEvaluateVisible() {
		return this.evaluateVisible;
	}

	public void setEvaluateVisible(Byte evaluateVisible) {
		this.evaluateVisible = evaluateVisible;
	}

	@Column(name = "autosave")
	public Byte getAutosave() {
		return this.autosave;
	}

	public void setAutosave(Byte autosave) {
		this.autosave = autosave;
	}

}
