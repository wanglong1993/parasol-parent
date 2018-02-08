/*

 */

package com.ginkgocap.parasol.tags.web.jetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class,org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages="com.ginkgocap.parasol.tags")
@ImportResource("classpath:applicationContext.xml")
public class ParasolTagApplication{

	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(ParasolTagApplication.class, args);
	}

}
