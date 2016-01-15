package com.ginkgocap.parasol.oauth2.web.jetty;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.ginkgocap.parasol.oauth2.model.SecurityUserDetails;
import com.ginkgocap.parasol.user.model.UserLoginRegister;

/**
 * 
 * @author allenshen
 * @date 2016年1月15日
 * @time 下午1:18:39
 * @Copyright Copyright©2015 www.gintong.com
 */
public final class LoginUserContextHolder {
	public static Authentication getAuthentication() {
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		return au;
	}
	
	
	public static String getPassport() {
		Authentication au = getAuthentication();
		return au.getName();
	}
	
	public static Long getUserId() {
		UserLoginRegister userLoginRegister = getUserLoginRegister();
		return userLoginRegister.getId();
	}
	
	
	public static UserLoginRegister getUserLoginRegister() {
		SecurityUserDetails p = getSecurityUserDetails();;
		return p.user();
	}

	public static SecurityUserDetails getSecurityUserDetails() {
		Authentication au = getAuthentication();
		return (SecurityUserDetails) au.getPrincipal();
	}
	
	public static Long getAppKey() {
		OAuth2Authentication au = (OAuth2Authentication) getAuthentication();
		String appKey = au.getOAuth2Request().getClientId();
		return Long.valueOf(appKey);
	}
	
}
