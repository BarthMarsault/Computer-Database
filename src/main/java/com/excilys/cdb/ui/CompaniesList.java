package com.excilys.cdb.ui;

import java.util.List;

import com.excilys.cdb.model.Company;


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
