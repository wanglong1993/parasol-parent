package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	/**
	 * 用户ID.
	 */
	private Long userId;
	/**
	 * 浏览我的主页.  1、仅自己可见 2、全部可见 3、部分好友可见  4 好友可见
	 */
	private Byte homePageVisible;
	/**
	 * 对我评价.   1、不可以评价 2、可评价 3、部分好友可评价  4 好友可评价
	 */
	private Byte evaluateVisible;
	/**
	 * 自动保存 .
	 */
	private Byte autosave;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;

	public UserConfig() {
	}

	@Id
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
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
	@Column(name = "ctime")
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "utime")
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

}
