package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;

@Service
public class CompanyService {
	@Autowired
	CompanyDAO companyDAO;
	

	
	
	public List<Company> getCompanies(){
		return companyDAO.getAll();
	}
	
	public boolean deleteCompany(int id) {		
		return companyDAO.delete(id);
	}
	
	
}
