package com.ginkgocap.parasol.file.web.jetty.autoconfig;

import java.net.InetSocketAddress;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
    public MultipartConfigElement multipartConfigElement() {
	   MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("20480KB");
        factory.setMaxRequestSize("20480KB");
        return factory.createMultipartConfig();
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
			}});
	    //factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
	    return factory;
	}	
	
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customJackson2HttpMessageConverter());
		// super.addDefaultHttpMessageConverters(converters);
	}
	
	@Bean
	public FastdfsClientInit initFastdfsClient() {
		return new FastdfsClientInit();
	}
	
	public static class FastdfsClientInit implements EnvironmentAware {
		private RelaxedPropertyResolver propertyResolver;
		
		@Override
		public void setEnvironment(Environment environment) {
			this.propertyResolver = new RelaxedPropertyResolver(environment, "fastdfs.");

			int connect_timeout = Integer.valueOf(propertyResolver.getProperty("connect_timeout"));
			if (connect_timeout<0) connect_timeout = ClientGlobal.DEFAULT_CONNECT_TIMEOUT;
			ClientGlobal.g_connect_timeout = connect_timeout * 1000;

			int network_timeout = Integer.valueOf(propertyResolver.getProperty("network_timeout"));
			if (network_timeout<0) network_timeout = ClientGlobal.DEFAULT_NETWORK_TIMEOUT;
			ClientGlobal.g_network_timeout = network_timeout * 1000;
			
			ClientGlobal.g_charset = propertyResolver.getProperty("charset");	
			ClientGlobal.g_tracker_http_port = 80;
			ClientGlobal.g_anti_steal_token = false;
			
			String servers = propertyResolver.getProperty("tracker_server");
			
			String[] szTrackerServers = StringUtils.split(servers,",");
			
	  		String[] parts;
	  		InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
	  		for (int i=0; i<szTrackerServers.length; i++)
	  		{
	  			parts = szTrackerServers[i].split("\\:", 2);
	  			
	  			tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
	  		}
	  		ClientGlobal.g_tracker_group = new TrackerGroup(tracker_servers);
	  		
	  		if (ClientGlobal.g_anti_steal_token)
	  		{
	  			ClientGlobal.g_secret_key = propertyResolver.getProperty("http.secret_key");
	  		}
		}
		
	}
}
