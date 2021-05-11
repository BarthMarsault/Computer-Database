package main.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.mapper.CompanyMapper;
import main.java.model.Company;

/**
 * CompanyDAO est une couche de persistance permettant la liaison entre la classe Company et la base de données.
 * @author excilys
 *
 */
public class CompanyDAO {
	
	static String tableName = "company";
	

	/**
	 * Retourne la liste de toutes les "Company" présente en base de données.
	 * @return
	 */
	public static List<Company> getCompanies(){
		List<Company> companies = new ArrayList<>();
		ResultSet rs;
				
		try {
			Connection conn = new DB().getConnection();
			
			String req = "SELECT * FROM " + tableName;
			
			Statement stmt = conn.createStatement();
			
			
			rs = stmt.executeQuery(req);
			
			while(rs.next()) {
				companies.add(CompanyMapper.resultSetToCompany(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return companies;
	}

}
