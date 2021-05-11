package main.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.Company;

/**
 * Classe de mapping pour la classe Company
 * @author excilys
 *
 */
public class CompanyMapper {
	
	/**
	 * Permet de transformer le contenu d'un ResultSet en objet Comapny
	 * @param rs ResultSet
	 * @return une instance de la classe Company
	 */
	public static Company resultSetToCompany(ResultSet rs) {
		try {
			return new Company(rs.getInt(1), rs.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
