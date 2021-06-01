package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class CompanyService {
	private static CompanyService companyService = null;
	
	private CompanyService() {
		
	}
	
	
	public static CompanyService getInstance() {
		if(companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}
	
	
	public List<Company> getCompanies(){
		return CompanyDAO.getInstance().getAll();
	}
	
	public boolean deleteCompany(int id) {		
		return CompanyDAO.getInstance().delete(id);
	}
	
}
