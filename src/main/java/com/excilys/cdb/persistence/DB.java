package com.excilys.cdb.persistence;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB {
	
	private static final Logger logger = LoggerFactory.getLogger(DB.class);
	
	private static DB db = null;
	private Connection conn = null;
	
	private String protocole = "jdbc:mysql:";
	private String ip =  "localhost" ; 
    private String port =  "3306" ;
	private String dbName = "computer-database-db";
	private String user = "admincdb";
	private String password = "qwerty1234";
	
	
	public static DB getInstance() {
		if(db == null) {
			db = new DB();
		}
		return db;
	}
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if(conn == null || conn.isClosed()) {       
		        String connString = protocole +  "//" + ip +  ":" + port +  "/" + dbName ;
			    conn = DriverManager.getConnection(connString, user, password);
			}
					    
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		
		
		return conn;
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
