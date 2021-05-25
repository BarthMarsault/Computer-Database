package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB {
	
	private static final Logger logger = LoggerFactory.getLogger(DB.class);
	
	private static DB database = null;
	private Connection conn = null;
	
	private final String CONFIG_FILE = "db.properties";
	private final String DATABASE_DRIVER = "driverClassName";
	private final String DATABASE_URL = "url";	
	private final String DATABASE_USER = "username";
	private final String DATABASE_PASSWORD = "password";
	
	private String driver;
	private String url;
	private String user;
	private String pass;
	
	
	private DB() {
		super();
		Properties properties = getProperties();
		driver = properties.getProperty(DATABASE_DRIVER);
		url = properties.getProperty(DATABASE_URL);
		user = properties.getProperty(DATABASE_USER);
		pass = properties.getProperty(DATABASE_PASSWORD);
	}
	
	public static DB getInstance() {
		if(database == null) {
			database = new DB();
		}
		return database;
	}
	
	public Connection getConnection() {
		try {
			Class.forName(driver);
			if(conn == null || conn.isClosed()) { 
			    conn =  DriverManager.getConnection(database.url, database.user, database.pass);
			}
					    
		} catch (SQLException | ClassNotFoundException e) {
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
	
	public boolean closeConnection() {
		try {
			conn.close();
			conn = null;
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	
}
