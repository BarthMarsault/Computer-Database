package com.excilys.cdb;


import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.*;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;





public class TestCompanyDAO {
	
	
	
	private CompanyDAO companyDAO;
	
	
	@BeforeEach
	public void setup() throws SQLException, IOException {

		companyDAO = CompanyDAO.getInstance();
		
    }
	
	
	
	@Test
	public void testFindCompanyByIdOK() {
		
		Optional<Company> company = companyDAO.findById(1);
		assertTrue(company.isPresent());
		assertEquals("Apple Inc.", company.get().getName());
		
		company = companyDAO.findById(0);
		assertFalse(company.isPresent());
	}
	
	
	/*@Test
	public void testFindByIdReturnOptionnalEmpty() {
		assertFalse(false);
		Optional<Company> company = companyDAO.findCompanyById(0);
		assertFalse(company.isPresent());
	}*/
	
	
	
	

}
