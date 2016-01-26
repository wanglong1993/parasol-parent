package com.ginkgocap.parasol.oauth2.web.jetty.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.ginkgocap.parasol.oauth2.service.OauthTokenStoreService;

@Component
public class CustomLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {
	private static final String BEARER_AUTHENTICATION = "Bearer ";
	private static final String HEADER_AUTHORIZATION = "authorization";
	@Autowired(required = false)
	private OauthTokenStoreService tokenStore;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (authentication != null && authentication instanceof OAuth2Authentication) {
			OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken((OAuth2Authentication) authentication);
			if (existingAccessToken != null) {
				tokenStore.removeAccessToken(existingAccessToken);
			}
		} else {
			String token = request.getHeader(HEADER_AUTHORIZATION);
			if (token != null && token.startsWith(BEARER_AUTHENTICATION)) {
				OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(" ")[0]);
				if (oAuth2AccessToken != null) {
					tokenStore.removeAccessToken(oAuth2AccessToken);
				}
			}else {
				token = request.getParameter("access_token");
				OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
				if (oAuth2AccessToken != null) {
					tokenStore.removeAccessToken(oAuth2AccessToken);
				}
			}
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
