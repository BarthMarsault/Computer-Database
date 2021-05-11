package main.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.mapper.CompanyMapper;
import main.java.model.Company;

public class CompanyDAO {
	
	static String tableName = "company";
	

	public static List<Company> getCompanies(){
		List<Company> companies = new ArrayList<>();
		ResultSet rs;
				
		try {
			Connection conn = new DB().getConnection();
			
			String req = "SELECT * FROM " + tableName;
			
			Statement stmt = conn.createStatement();
			
			
			rs = stmt.executeQuery(req);
			
			while(rs.next()) {
				Company company = new Company(rs.getInt(1), rs.getString(2));
				companies.add(CompanyMapper.resultSetToCompany(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return companies;
	}

}
