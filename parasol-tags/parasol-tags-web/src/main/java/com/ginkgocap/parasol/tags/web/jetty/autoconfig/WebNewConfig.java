package com.ginkgocap.parasol.tags.web.jetty.autoconfig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 
 * @author allenshen
 * @date 2015年11月19日
 * @time 下午7:42:22
 * @Copyright Copyright©2015 www.gintong.com
 */
//@Configuration
public class WebNewConfig extends WebMvcConfigurerAdapter{

	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter("tagFilter", SimpleBeanPropertyFilter.filterOutAllExcept()));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		return jsonConverter;
	}
	
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customJackson2HttpMessageConverter());
	}

}
