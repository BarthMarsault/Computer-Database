package main.java.controller;

import java.util.List;

import main.java.model.Company;
import main.java.persistence.CompanyDAO;
import main.java.ui.CompaniesList;

public class ControllerCompany {
	
	public static void showAllCompanies() {
		CompaniesList.showList(CompanyDAO.getCompanies());
	}
	
	public static void showCompanies(List<Company> companies) {
		CompaniesList.showList(companies);
	}

	

}
