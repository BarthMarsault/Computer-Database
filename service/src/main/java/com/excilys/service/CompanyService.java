package com.excilys.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.core.Company;
import com.excilys.persistence.CompanyDAO;

@Service
@Scope
public class CompanyService {
	
	CompanyDAO companyDAO;
	

	
	
	
	public CompanyService(CompanyDAO companyDAO) {
		super();
		this.companyDAO = companyDAO;
	}

	public List<Company> getCompanies(){
		return companyDAO.getAll();
	}
	
	public boolean deleteCompany(int id) {		
		return companyDAO.delete(id);
	}
	
	
}
