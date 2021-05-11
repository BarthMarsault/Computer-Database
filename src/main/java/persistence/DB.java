package main.java.persistence;

import java.sql.*;

public class DB {
	
	
	Connection conn = null;
	
	private String protocole = "jdbc:mysql:";
	private String ip =  "localhost" ; 
    private String port =  "3306" ;
	private String dbName = "computer-database-db";
	private String user = "admincdb";
	private String password = "qwerty1234";
	
	
	public Connection getConnection() {
		try {
				        
	        String connString = protocole +  "//" + ip +  ":" + port +  "/" + dbName ;
			
		    conn =
		       DriverManager.getConnection(connString, user, password);
		    
		   return conn;
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return null;
	}
	
	/*
	public boolean closeConnection() {
		try {
			conn.close();
			conn = null;
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	*/
	
	
	
	
	
	
	
}
