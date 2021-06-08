package com.excilys.cdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.ui.CompaniesList;

@Controller
@Scope
public class ControllerCompany {
	
	private static CompanyDAO companyDAO;
	
	@Autowired
	public void initDAO(CompanyDAO companyDAO) {
		ControllerCompany.companyDAO = companyDAO;
	}
	
	/**
	 * Appel l'affichage de toutes les Companies
	 */
	public static void showAllCompanies() {
		CompaniesList.showList(companyDAO.getAll());
	}
	
	
	/**
	 * Appel l'affichage d'une liste de Companies
	 * @param companies
	 */
	public static void showCompanies(List<Company> companies) {
		CompaniesList.showList(companies);
	}
	
	
	public static void deleteCompany() {
		CompaniesList.deleteCompany();
	}
	

}
