package com.excilys.cdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.cdb.ui.Root;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.persistence" ,
			"com.excilys.cdb.service", "com.excilys.cdb.controller",
			"com.excilys.cdb.mapper", "com.excilys.cdb.ui",
			"com.excilys.cdb.servlet", "com.excilys.cdb.validator"})
public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		//ApplicationContext contextDB = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

		
		//System.out.println(computerDAO.getAll().toString());
		// TODO Auto-generated method stub
		System.out.println("Hello");
		
		
		
		Root.printRoot();
		
		
		System.out.println("FIN");
	}

}
