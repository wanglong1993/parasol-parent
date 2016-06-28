package org.parasol.column;

import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class,org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages = "org.parasol.column")
@ImportResource("classpath:applicationContext.xml")
public class ColumnApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext context = SpringApplication.run(new Object[]{"classpath:/applicationContext.xml"}, args);  
//				SpringApplication app=new SpringApplication(PermissionApplication.class);
//				app.setWebEnvironment(true);
//				Set<Object> set = new HashSet<Object>();  
//		        set.add("classpath:applicationContext.xml");  
//		        app.setSources(set);  
		        ConfigurableApplicationContext context=SpringApplication.run(ColumnApplication.class,args);
		        /*System.out.println("hohoho"); 
		        String[] names = context.getBeanDefinitionNames();  
		        Arrays.sort(names);  
		        for (String string : names) {  
		        System.err.println(string);  
		        }*/
	}
	
}
