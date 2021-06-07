package com.excilys.cdb.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;


public class CompaniesList {
	
	@Autowired
	private static CompanyService companyService;
	
	/**
	 * Affiche la liste des compagnies
	 */
	public static void showList(List<Company> companies) {
		for(Company company : companies) {
			System.out.println(company);
		}
	}
	
	public static void deleteCompany() {
		int id = UiUtils.askId("Entrez l'id de la Company à supprimer");
		if(companyService.deleteCompany(id)) {
			System.out.println("Company : " + id + " supprimée !");
		}else {
			System.out.println("Echec de la suppression de la company : " + id);
		}
	}

}
