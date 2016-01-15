package com.ginkgocap.parasol.oauth2.web.jetty.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ginkgocap.parasol.oauth2.utils.Sha256PasswordEncoder;

/**
 * 
 * @author allenshen
 * @date 2015年12月4日
 * @time 上午9:50:00
 * @Copyright Copyright©2015 www.gintong.com
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
	   //http.httpBasic().and()
		http
	   .authorizeRequests()
	       .anyRequest().authenticated()
	       .antMatchers("/login.jhtml","/").permitAll()
	       .and()
	   .exceptionHandling()
	       .accessDeniedPage("/login.jhtml?authorization_error=true")
	       .and()
	   // TODO: put CSRF protection back into this endpoint
	   .csrf()
	       .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
	       .disable()
	   .logout()
	   	.logoutUrl("/logout")
	       .logoutSuccessUrl("/login.jhtml")
	       .and()
	   .formLogin()
	   		//.loginProcessingUrl("/login")
	        .failureUrl("/login.jhtml?authentication_error=true")
	        .loginPage("/login.jhtml");
		
		
		// @formatter:on
	}

	@Autowired
	private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Sha256PasswordEncoder();
	}

	public SaltSource saltSource() {
		ReflectionSaltSource saltSource = new ReflectionSaltSource();
		saltSource.setUserPropertyToUse("salt");
		return  saltSource;
		
	}
	public ObjectPostProcessor<DaoAuthenticationProvider> daoObjectPostProcessor() {
		return new ObjectPostProcessor<DaoAuthenticationProvider>(){
			private SaltSource saltSource = saltSource();
			@Override
			public <O extends DaoAuthenticationProvider> O postProcess(O object) {
				object.setSaltSource(saltSource);
				return object;
			}
		};
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// AuthenticationProvider provider = new DaoAuthenticationProvider();
		// auth.authenticationProvider(provider);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()).addObjectPostProcessor(daoObjectPostProcessor());
	}

	/**
	 * 那些地址不用认证跳过去
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// @formatter:off
		// TODO: 可以写一个配置程序
		
//		web.ignoring()
//		.antMatchers("/css/bootstrap.min.css");
//		.antMatchers("/api/activate")
//		.antMatchers("/api/lostpassword")
//		.antMatchers("/api/resetpassword");
	   // @formatter:on
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * 没有搞清楚
	 * 
	 * @author allenshen
	 * @date 2015年12月4日
	 * @time 上午10:31:32
	 * @Copyright Copyright©2015 www.gintong.com
	 */
	@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
	public static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
		@Override
		protected MethodSecurityExpressionHandler createExpressionHandler() {
			return new OAuth2MethodSecurityExpressionHandler();
		}
	}

}
