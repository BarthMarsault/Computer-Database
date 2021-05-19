package com.excilys.cdb;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.excilys.cdb.persistence.DB;



public class TestCompanyDAO {
	
	@InjectMocks private DB db;
	@Mock private Connection conn;
	@Mock private Statement mockStatement;
	
	
	@Before
	public void init() throws SQLException, IOException {
		
		String reqCreateDatabase = "";
		String reqEntries = "";
		String line;
		BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/1-SCHEMA.sql"));
		
		while ((line = reader.readLine()) != null)
			reqCreateDatabase += line;
		reader.close();
		
		line = "";
		reader = new BufferedReader(new FileReader("src/test/resources/3-ENTRIES.sql"));
		while ((line = reader.readLine()) != null)
			reqEntries += line;
		
		
		
	    conn = DriverManager.getConnection("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1","sa","");
	    
	    Statement stmt = conn.createStatement();
	    
	    try {
	    	stmt.execute(reqCreateDatabase);
	    	stmt.execute(reqEntries);
	    }catch(Exception e){
	    	System.out.println(e.getMessage());
	    }
	    
		//MockitoAnnotations.initMocks(this);
	    
		
    }
	
	@After
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}
	
	@Test
	public void testFindCompanyById() {
		assertTrue(true);
	
	}

}
