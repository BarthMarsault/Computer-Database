package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;

/**
 * Classe de mapping pour la classe Company
 * @author excilys
 *
 */
public class CompanyMapper {
	
	private static CompanyMapper companyMapper = null;
	
	private CompanyMapper() {
		
	}
	
	public static CompanyMapper getInstance() {
		if(companyMapper == null) {
			companyMapper = new CompanyMapper();
		}
		return companyMapper;
	}
	
	/**
	 * Permet de transformer le contenu d'un ResultSet en objet Comapny
	 * @param rs ResultSet
	 * @return une instance de la classe Company
	 */
	public Company resultSetToCompany(ResultSet rs) {
		try {
			return new Company(rs.getInt(1), rs.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Company> resultSetToListCompany(ResultSet rs){
		ArrayList<Company> companies = new ArrayList<>();
		
		try {
			while(rs.next()) {
				companies.add(resultSetToCompany(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companies;
	}
}