package main.java.ui;

import main.java.model.Company;
import main.java.persistence.CompanyDAO;


public class CompaniesList {
	
	/**
	 * Affiche la liste des compagnies
	 */
	public static void showList() {
		for(Company company : CompanyDAO.getCompanies()) {
			System.out.println(company);
		}
	}

}
