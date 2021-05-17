package main.java.controller;

import java.util.List;

import main.java.model.Company;
import main.java.persistence.CompanyDAO;
import main.java.ui.CompaniesList;

public class ControllerCompany {
	
	/**
	 * Appel l'affichage de toutes les Companies
	 */
	public static void showAllCompanies() {
		CompaniesList.showList(CompanyDAO.getCompanies());
	}
	
	
	/**
	 * Appel l'affichage d'une liste de Companies
	 * @param companies
	 */
	public static void showCompanies(List<Company> companies) {
		CompaniesList.showList(companies);
	}

	

}
