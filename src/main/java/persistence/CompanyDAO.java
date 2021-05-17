package main.java.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.mapper.CompanyMapper;
import main.java.mapper.ComputerMapper;
import main.java.model.Company;
import main.java.model.Computer;

/**
 * CompanyDAO est une couche de persistance permettant la liaison entre la classe Company et la base de données.
 * @author excilys
 *
 */
public class CompanyDAO {
	
	static String tableName = "company";
	
	private static CompanyDAO companyDAO = null;
	
	
	private CompanyDAO() {
		
	}
	
	public static CompanyDAO getInstance() {
		if(companyDAO == null) {
			companyDAO = new CompanyDAO();
		}
		return companyDAO;
	}

	/**
	 * Retourne la liste de toutes les "Company" présente en base de données.
	 * @return
	 */
	public List<Company> getCompanies(){
		return getCompaniesWithLimit(-1,-1);
	}
	
	
	public List<Company> getCompaniesWithLimit(int limit, int offset){
		List<Company> companies = new ArrayList<>();
		ResultSet rs;
				
		try {
			Connection conn = new DB().getConnection();
			
			String req = "SELECT * FROM " + tableName;
			
			if(limit >= 0) { req += " LIMIT ?";}
			if(limit >= 0 && offset >= 0) { req += " OFFSET ?";}
			
			PreparedStatement ps = conn.prepareStatement(req);
			
			if(limit >= 0) { ps.setInt(1, limit);}
			if(limit >= 0 && offset >= 0) { ps.setInt(2, offset);}
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				companies.add(CompanyMapper.resultSetToCompany(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return companies;
	}
	
	
	/**
	 * Retourne la Company en fonction de l'id
	 * @param id
	 * @return Company
	 */
	public Company findCompanyById(int id) {
		Company company = null;
		
		try {
			Connection conn = new DB().getConnection();
			
			String req = "SELECT * FROM " + tableName +
						" WHERE id = ?";
			
			
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				company = CompanyMapper.resultSetToCompany(rs);
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return company;
	}

}
