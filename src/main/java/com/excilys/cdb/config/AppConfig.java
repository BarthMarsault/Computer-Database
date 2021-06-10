package com.excilys.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.persistence" ,
			"com.excilys.cdb.service", "com.excilys.cdb.controller",
			"com.excilys.cdb.mapper", "com.excilys.cdb.ui",
			"com.excilys.cdb.servlet", "com.excilys.cdb.validator",
			"com.excilys.cdb.config", "com.excilys.cdb.session"})
public class AppConfig implements WebMvcConfigurer {

	@Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");

        return bean;
    }
	
}
