package com.excilys.cdb;

import static org.junit.Assert.*;

import java.io.*;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.*;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;

import junit.framework.TestCase;



public class TestCompanyDAO {
	
	
	
	private static CompanyDAO companyDAO = CompanyDAO.getInstance();
	
	
	//@Before
	/*public void setup() throws SQLException, IOException {

		//companyDAO = CompanyDAO.getInstance();
		
    }*/
	
	
	
	@Test
	public void testFindCompanyByIdOK() {
		
		Optional<Company> company = companyDAO.findCompanyById(1);
		assertTrue(company.isPresent());
		assertEquals("Apple Inc.", company.get().getName());
		
		company = companyDAO.findCompanyById(0);
		assertFalse(company.isPresent());
	}
	
	/*@Test
	public void testFindByIdReturnOptionnalEmpty() {
		assertFalse(false);
		Optional<Company> company = companyDAO.findCompanyById(0);
		assertFalse(company.isPresent());
	}*/
	
	
	
	

}
