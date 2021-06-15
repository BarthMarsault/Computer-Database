package com.excilys.cdb.config;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan
@PropertySource("classpath:db.properties")
public class SpringJdbcConfig {


	Environment environment;

	HikariDataSource dataSource;

	private static final Logger logger = LoggerFactory.getLogger(SpringJdbcConfig.class);

	private final String DATABASE_DRIVER = "driverClassName";
	private final String DATABASE_URL = "url";	
	private final String DATABASE_USER = "user_database";
	private final String DATABASE_PASSWORD = "password";




	public SpringJdbcConfig(Environment environment) {
		super();
		this.environment = environment;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(mysqlDataSource());
	}


	@Bean
	public DataSource mysqlDataSource() {

		if(dataSource == null) {
			dataSource = new HikariDataSource();

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
		}

		return dataSource;
	}

}
