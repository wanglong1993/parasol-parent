package com.ginkgocap.parasol.oauth2.web.jetty.autoconfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ginkgocap.parasol.oauth2.service.OauthClientDetailsService;
import com.ginkgocap.parasol.oauth2.service.OauthCodeService;
import com.ginkgocap.parasol.oauth2.service.OauthTokenStoreService;
import com.ginkgocap.parasol.oauth2.web.jetty.security.CustomAuthenticationEntryPoint;
import com.ginkgocap.parasol.oauth2.web.jetty.security.CustomLogoutSuccessHandler;
import com.ginkgocap.parasol.oauth2.web.jetty.store.ParasolApprovalStore;

@Configuration
public class OAuth2Configuration {
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter implements EnvironmentAware {
		private RelaxedPropertyResolver propertyResolver;
		private static final String SECURITY_OATUTH2 = "security.oauth2.";
		private static final String SOURCE_ID = "resource.id";
		private static final String AUTHENTICATED="authenticated";

		@Autowired
		private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
		@Autowired
		private CustomLogoutSuccessHandler customLogoutSuccessHandler;
		@Autowired
		private OauthClientDetailsService oauthClientDetailsService;

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			//http.httpBasic();
			http
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint);
            
            //.and()
            //.logout()
            //.logoutUrl("/logout")
            //.logoutSuccessHandler(customLogoutSuccessHandler);
            
			http.logout().disable();
			
			
            http.csrf()
            .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
            .disable()
            .headers()
            .frameOptions().disable();
			
            http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //不创建Session
            //.and()
            //.formLogin().loginPage("/login.jhtml");
  			// @formatter:on
            
            http.securityContext().disable();
            
            //http.requestCache().disable();
            
			if (propertyResolver != null) {
				String authenticated = propertyResolver.getProperty(AUTHENTICATED); // security.oauth2.authenticated
				if (StringUtils.isNotBlank(authenticated)) {
					String[] authenticateds = StringUtils.split(authenticated,",");
					http.authorizeRequests().antMatchers(authenticateds).authenticated();
				}
			}
			http.setSharedObject(ClientDetailsService.class, oauthClientDetailsService);
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			if (propertyResolver != null) {
				String resourceId = propertyResolver.getProperty(SOURCE_ID); // security.oauth2.resource.id
				if (StringUtils.isNotBlank(resourceId)) {
					resources.resourceId(resourceId);
				}
			}
			//resources.stateless(false);
		}

		@Override
		public void setEnvironment(Environment environment) {
			this.propertyResolver = new RelaxedPropertyResolver(environment, SECURITY_OATUTH2);
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
		@Autowired
		private OauthClientDetailsService oauthClientDetailsService; // 应用信息

		@Autowired
		private OauthTokenStoreService oauthTokenStoreService; // token的存储

		@Autowired
		private ParasolApprovalStore parasolApprovalStore; // 用户同意授权的存储

		@Autowired
		private OauthCodeService oauthCodeService; // oauthCode存储

		@Bean
		public TokenStore tokenStore() {
			return oauthTokenStoreService;
		}

		@Autowired
		//@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
			endpoints.approvalStore(parasolApprovalStore);
			endpoints.authorizationCodeServices(oauthCodeService);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(oauthClientDetailsService);
		}
	}

}
