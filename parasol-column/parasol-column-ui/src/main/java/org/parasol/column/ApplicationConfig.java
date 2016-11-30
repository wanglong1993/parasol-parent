package org.parasol.column;

import javax.servlet.Filter;

import org.parasol.column.filter.AppFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	
	 @Bean
	    public FilterRegistrationBean appFilterRegistration() {

	        FilterRegistrationBean registration = new FilterRegistrationBean();
	        registration.setFilter(appFilter());
	        registration.addUrlPatterns("/*");
	        registration.addInitParameter("excludedUrl", "/paramValue");
	        registration.setName("appFilter");
	        return registration;
	    }
	 
	@Bean(name = "appFilter")
    public Filter appFilter() {
        return new AppFilter();
    }
}
