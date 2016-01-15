package com.ginkgocap.parasol.oauth2.web.jetty.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login.jhtml").setViewName("login");
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/css/**")) {
			registry.addResourceHandler("/css/**").addResourceLocations(
					"classpath:/static/css/");
		}
		if (!registry.hasMappingForPattern("/fonts/**")) {
			registry.addResourceHandler("/fonts/**").addResourceLocations(
					"classpath:/static/fonts/");
		}
		
		if (!registry.hasMappingForPattern("/i/**")) {
			registry.addResourceHandler("/i/**").addResourceLocations(
					"classpath:/static/i/");
		}
		if (!registry.hasMappingForPattern("/js/**")) {
			registry.addResourceHandler("/js/**").addResourceLocations(
					"classpath:/static/js/");
		}
	}
}
