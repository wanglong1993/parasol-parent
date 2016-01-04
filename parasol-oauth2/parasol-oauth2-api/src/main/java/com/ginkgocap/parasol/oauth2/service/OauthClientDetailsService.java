package com.ginkgocap.parasol.oauth2.service;

import java.util.List;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;

import com.ginkgocap.parasol.oauth2.exception.OauthClientDetailsServiceException;
import com.ginkgocap.parasol.oauth2.model.OauthClientDetails;


/**
 * 操作客户端基本信息接口定义
 * 
 */
public interface OauthClientDetailsService  extends ClientDetailsService, ClientRegistrationService {
	
	public List<OauthClientDetails> listClientDetails(int start,int count) throws OauthClientDetailsServiceException;

}
