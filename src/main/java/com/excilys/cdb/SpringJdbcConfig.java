package com.excilys.cdb;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.excilys.cdb.persistence.ComputerDAO;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys.cdb")
@PropertySource("classpath:db.properties")
public class SpringJdbcConfig {
	
	@Autowired
	Environment environment;
	
	private static final Logger logger = LoggerFactory.getLogger(SpringJdbcConfig.class);
	
	private final String DATABASE_DRIVER = "driverClassName";
	private final String DATABASE_URL = "url";	
	private final String DATABASE_USER = "username";
	private final String DATABASE_PASSWORD = "password";
	
	
	@Bean
	public DataSource mysqlDataSource() {

		HikariDataSource dataSource = new HikariDataSource();       
		
		try {
			dataSource.setDriverClassName(environment.getProperty(DATABASE_DRIVER));
			dataSource.setJdbcUrl(environment.getProperty(DATABASE_URL));
			dataSource.setUsername(environment.getProperty(DATABASE_USER));
			dataSource.setPassword(environment.getProperty(DATABASE_PASSWORD));
			
			dataSource.setMinimumIdle(5);
			dataSource.setMaximumPoolSize(100);
			dataSource.setLoginTimeout(3);
		} 
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
        return dataSource;
	}

}
