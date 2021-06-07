package com.excilys.cdb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

class TestComputerDAO {
	
	@Autowired
	ComputerDAO dao;
	
	@BeforeEach
	void setUp() throws Exception {
		//dao = ComputerDAO.getInstance();
	}

	@Test
	void test() {
		Optional<Computer> company = dao.findById(1);
		assertTrue(company.isPresent());
		assertEquals("MacBook Pro 15.4 inch", company.get().getName());
	}

}
