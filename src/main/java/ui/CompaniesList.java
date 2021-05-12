package main.java.ui;

import java.util.List;

import main.java.model.Company;
import main.java.persistence.CompanyDAO;


public class CompaniesList {
	
	/**
	 * Affiche la liste des compagnies
	 */
	public static void showList(List<Company> companies) {
		for(Company company : companies) {
			System.out.println(company);
		}
	}

}
