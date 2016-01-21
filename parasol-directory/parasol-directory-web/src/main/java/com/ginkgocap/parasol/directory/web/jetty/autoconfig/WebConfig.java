package com.ginkgocap.parasol.directory.web.jetty.autoconfig;

import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * @author allenshen
 * @date 2015年11月19日
 * @time 下午7:42:22
 * @Copyright Copyright©2015 www.gintong.com
 */
@Configuration
public class WebConfig {
	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		return jsonConverter;
	}

	@Bean
	public ShallowEtagHeaderFilter shallowETagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

	
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
	    factory.addServerCustomizers(new JettyServerCustomizer(){
			@Override
			public void customize(Server server) {
				QueuedThreadPool queuedThreadPool = new QueuedThreadPool(1024);
				queuedThreadPool.setMaxQueued(1024);
				queuedThreadPool.setMinThreads(50);
				server.setThreadPool(queuedThreadPool);
//				server.setSessionIdManager(sessionIdManager);
			}});
	    //factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
	    return factory;
	}	
	
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customJackson2HttpMessageConverter());
		// super.addDefaultHttpMessageConverters(converters);
	}
	


}
