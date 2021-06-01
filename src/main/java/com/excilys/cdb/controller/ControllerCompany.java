package com.excilys.cdb.controller;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.ui.CompaniesList;

public class ControllerCompany {
	
	/**
	 * Appel l'affichage de toutes les Companies
	 */
	public static void showAllCompanies() {
		CompaniesList.showList(CompanyDAO.getInstance().getAll());
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
