package com.ginkgocap.parasol.oauth2.web.jetty.client;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

/**
 * 
 * @author allenshen
 * @date 2015年12月7日
 * @time 下午4:54:10
 * @Copyright Copyright©2015 www.gintong.com
 */
public class ParasolClientDetailsService implements ClientDetailsService {
	private static Logger logger = Logger.getLogger(ParasolClientDetailsService.class);

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		if (StringUtils.isNotBlank(clientId)) {
			logger.info("load ClientDetails by clientId: " + clientId);
			System.out.println("load ClientDetails by clientId: " + clientId);
			String resourceIds = "directory,res_2,res_3,res_4,oauth2-resource";
			String scopes = "read,write";
			String grantTypes = "password,refresh_token,authorization_code,implicit";
			String authorities = "aaaa,bbb";
			BaseClientDetails clientDetails = new BaseClientDetails(clientId, resourceIds, scopes, grantTypes, authorities);
			clientDetails.setClientSecret("bar");
			clientDetails.setAccessTokenValiditySeconds(1000);
			
			Set<String> regSet = new HashSet<String>();
			regSet.add("http://www.sohu.com");
			clientDetails.setRegisteredRedirectUri(regSet);
			return clientDetails;
		} else {
			return null;
		}
	}

}
