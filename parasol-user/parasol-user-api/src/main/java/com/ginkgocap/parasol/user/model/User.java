package com.ginkgocap.parasol.user.model;

import java.io.Serializable;

import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserContactWay;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.model.UserExt;
import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;
import com.ginkgocap.parasol.user.model.UserWorkHistory;

public class User implements Serializable {

	/**
	 * 用户组合信息
	 */
	private static final long serialVersionUID = 1L;
	private UserLoginRegister userLoginRegister;
	private UserBasic userBasic;
	private UserExt userExt;
	private UserInterestIndustry userInterestIndustry;
	private UserInfo userInfo;
	private UserContactWay userContactWay;
	private UserEducationHistory userEducationHistory;
	private UserWorkHistory userWorkHistory;
	private UserOrgPerCusRel userOrgPerCusRel;
	private UserDefined userDefined;
	
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
	public UserInterestIndustry getUserInterestIndustry() {
		return userInterestIndustry;
	}
	public void setUserInterestIndustry(UserInterestIndustry userInterestIndustry) {
		this.userInterestIndustry = userInterestIndustry;
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
	public UserEducationHistory getUserEducationHistory() {
		return userEducationHistory;
	}
	public void setUserEducationHistory(UserEducationHistory userEducationHistory) {
		this.userEducationHistory = userEducationHistory;
	}
	public UserWorkHistory getUserWorkHistory() {
		return userWorkHistory;
	}
	public void setUserWorkHistory(UserWorkHistory userWorkHistory) {
		this.userWorkHistory = userWorkHistory;
	}
	public UserOrgPerCusRel getUserOrgPerCusRel() {
		return userOrgPerCusRel;
	}
	public void setUserOrgPerCusRel(UserOrgPerCusRel userOrgPerCusRel) {
		this.userOrgPerCusRel = userOrgPerCusRel;
	}
	public UserDefined getUserDefined() {
		return userDefined;
	}
	public void setUserDefined(UserDefined userDefined) {
		this.userDefined = userDefined;
	}
	
	
	
}
