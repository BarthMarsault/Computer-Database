package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

@Repository
public class DB {
	
	private static final Logger logger = LoggerFactory.getLogger(DB.class);
	
	//private static DB database = null;
	private static HikariDataSource datasource;
	private Connection conn = null;
	
	private final String CONFIG_FILE = "db.properties";
	private final String DATABASE_DRIVER = "driverClassName";
	private final String DATABASE_URL = "url";	
	private final String DATABASE_USER = "username";
	private final String DATABASE_PASSWORD = "password";

	
	
	public DB() {
		super();
		Properties properties = getProperties();
		datasource = new HikariDataSource();
		
		try {
			datasource.setDriverClassName(properties.getProperty(DATABASE_DRIVER));
			datasource.setJdbcUrl(properties.getProperty(DATABASE_URL));
			datasource.setUsername(properties.getProperty(DATABASE_USER));
			datasource.setPassword(properties.getProperty(DATABASE_PASSWORD));
			
			datasource.setMinimumIdle(5);
			datasource.setMaximumPoolSize(100);
			datasource.setLoginTimeout(3);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/*public static DB getInstance() {
		if(database == null) {
			database = new DB();
		}
		return database;
	}*/
	
	public Connection getConnection() {
		
		try {
			return datasource.getConnection();
			/*if(conn == null || conn.isClosed()) { 
			    //conn =  DriverManager.getConnection(database.url, database.user, database.pass);
			    conn = datasource.getConnection();
			}*/
					    
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return conn;
	}
	
	private Properties getProperties() {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try (InputStream configFile = classLoader.getResourceAsStream(CONFIG_FILE)) {
			properties.load(configFile);
		} catch (IOException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return properties;
	}
	
	public boolean close() {
		try {
			if(conn != null || !conn.isClosed()) {
				conn.close();
			}			
			conn = null;
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	
}
