package com.excilys.cdb;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Fields;

import com.excilys.cdb.persistence.DB;



public class TestCompanyDAO {
	
	@InjectMocks private DB db;
	@Mock private Connection conn;
	@Mock private Statement mockStatement;
	
	
	@Before
	public void init() throws SQLException, IOException {
		
	    /*conn = DriverManager.getConnection("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1","sa","");
	    
	    Statement stmt = conn.createStatement();
	    
	    try {
	    	stmt.execute("CREATE TABLE test(id int, nom varchar(255))");
	    	stmt.execute("INSERT INTO test VALUES(1,'NomDeTest')");
	    }catch(Exception e){
	    	System.out.println(e.getMessage());
	    }
	    
		MockitoAnnotations.initMocks(this);
	    */
	    
	    
    }
	
	@Test
	public void testFindCompanyById() {
		assertTrue(true);
	
	}

}
