package com.ginkgocap.parasol.user.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	/**
	 * 用户组合信息
	 */
	private static final long serialVersionUID = 1L;
	private UserLoginRegister userLoginRegister;
	private UserBasic userBasic;
	private UserExt userExt;
	private List<UserInterestIndustry> listUserInterestIndustry;
	private UserInfo userInfo;
	private UserContactWay userContactWay;
	private List<UserEducationHistory> listUserEducationHistory;
	private List<UserWorkHistory> listUserWorkHistory;
	private UserOrgPerCusRel userOrgPerCusRel;
	private List<UserDefined> listUserDefined;
	
	public UserLoginRegister getUserLoginRegister() {
		return userLoginRegister;
	}
	public void setUserLoginRegister(UserLoginRegister userLoginRegister) {
		this.userLoginRegister = userLoginRegister;
	}
	public UserBasic getUserBasic() {
		return userBasic;
	}
	public void setUserBasic(UserBasic userBasic) {
		this.userBasic = userBasic;
	}
	public UserExt getUserExt() {
		return userExt;
	}
	public void setUserExt(UserExt userExt) {
		this.userExt = userExt;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public UserContactWay getUserContactWay() {
		return userContactWay;
	}
	public void setUserContactWay(UserContactWay userContactWay) {
		this.userContactWay = userContactWay;
	}
	public UserOrgPerCusRel getUserOrgPerCusRel() {
		return userOrgPerCusRel;
	}
	public void setUserOrgPerCusRel(UserOrgPerCusRel userOrgPerCusRel) {
		this.userOrgPerCusRel = userOrgPerCusRel;
	}
	public List<UserInterestIndustry> getListUserInterestIndustry() {
		return listUserInterestIndustry;
	}
	public void setListUserInterestIndustry(
			List<UserInterestIndustry> listUserInterestIndustry) {
		this.listUserInterestIndustry = listUserInterestIndustry;
	}
	public List<UserEducationHistory> getListUserEducationHistory() {
		return listUserEducationHistory;
	}
	public void setListUserEducationHistory(
			List<UserEducationHistory> listUserEducationHistory) {
		this.listUserEducationHistory = listUserEducationHistory;
	}
	public List<UserWorkHistory> getListUserWorkHistory() {
		return listUserWorkHistory;
	}
	public void setListUserWorkHistory(List<UserWorkHistory> listUserWorkHistory) {
		this.listUserWorkHistory = listUserWorkHistory;
	}
	public List<UserDefined> getListUserDefined() {
		return listUserDefined;
	}
	public void setListUserDefined(List<UserDefined> listUserDefined) {
		this.listUserDefined = listUserDefined;
	}
	
}
