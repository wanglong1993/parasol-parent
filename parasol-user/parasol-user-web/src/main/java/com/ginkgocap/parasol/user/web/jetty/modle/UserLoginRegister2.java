package com.ginkgocap.parasol.user.web.jetty.modle;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 用户注册登录表
 */
@JsonFilter("com.ginkgocap.parasol.user.model.UserLoginRegister")
@Entity
@Table(name = "tb_user_login_register", catalog = "parasol_user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "passport"),
		@UniqueConstraint(columnNames = "mobile"),
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "user_name") })
public class UserLoginRegister2 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4229992793845063007L;
	private long id;
	/**
	 * 通行证：用户注册时使用的登录名.
	 */
	private String passport;
	/**
	 * 手机号.
	 */
	private String mobile;
	/**
	 * 邮箱.
	 */
	private String email;
	/**
	 * 用户名.
	 */
	private String userName;
	/**
	 * 密码.
	 */
	private String password;
	/**
	 * 1 组织用户，0.个人用户.
	 */
	private Byte userType;
	/**
	 * 密码加密码.
	 */
	private String salt;
	/**
	 * 来源于哪个APP注册的.
	 */
	private String source;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 用户IP.
	 */
	private String ip;
	/**
	 * 金桐号.
	 */
	private String gid;
	/**
	 * 用户绑定组织ID.
	 */
	private long orgId;
	/**
	 * 用户角色.0.普通用户，1.服务商
	 */
	private Byte userRole;
	/**
	 * 用户状态.0.正常，1.无效
	 */
	private Byte statu;
	/**
	 * 服务商审核标识 1.审核通过；2.审不通过；0.待审核
	 */
	private Byte auth;
	/**
	 * 用户头像id
	 */
	private long headImageId;
	/**
	 * 用户头像路径
	 */
	private String headImageUrl;
	/**
	 * 用户头像文件
	 */
	private MultipartFile file ;
	
	

	public UserLoginRegister2() {
	}

	public UserLoginRegister2(long id, String passport, String password,
			Byte userType, String salt, String source, Long ctime, Long utime,
			String ip, String gid,long orgId) {
		this.id = id;
		this.passport = passport;
		this.password = password;
		this.userType = userType;
		this.salt = salt;
		this.source = source;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
		this.gid = gid;
		this.orgId=orgId;
	}

	public UserLoginRegister2(long id, String passport, String mobile,
			String email, String userName, String password, Byte userType,
			String salt, String source, Long ctime, Long utime,
			String ip,String gid,long orgId) {
		this.id = id;
		this.passport = passport;
		this.mobile = mobile;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
		this.salt = salt;
		this.source = source;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
		this.gid = gid;
		this.orgId=orgId;
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

	@Column(name = "passport", unique = true, nullable = false, length = 50)
	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	@Column(name = "mobile", unique = true, length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "email", unique = true, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "user_name", unique = true, length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "user_type", nullable = false)
	public Byte getUsetType() {
		return this.userType;
	}

	public void setUsetType(Byte usetType) {
		this.userType = usetType;
	}

	@Column(name = "salt", nullable = false, length = 40)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "source", nullable = false, length = 20)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
	@Column(name = "ctime", nullable = false, length = 19)
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "utime", nullable = false, length = 19)
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ip", nullable = false, length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name = "gid")
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
	@Column(name = "orgId")
	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "user_role")
	public Byte getUserRole() {
		return userRole;
	}

	public void setUserRole(Byte userRole) {
		this.userRole = userRole;
	}
	@Column(name = "statu")
	public Byte getStatu() {
		return statu;
	}

	public void setStatu(Byte statu) {
		this.statu = statu;
	}
	@Column(name = "auth")
	public Byte getAuth() {
		return auth;
	}

	public void setAuth(Byte auth) {
		this.auth = auth;
	}
	@Column(name = "head_Image_Id")
	public long getHeadImageId() {
		return headImageId;
	}

	public void setHeadImageId(long headImageId) {
		this.headImageId = headImageId;
	}
	@Transient
	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
