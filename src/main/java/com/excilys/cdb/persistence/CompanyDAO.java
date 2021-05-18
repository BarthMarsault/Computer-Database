package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

/**
 * CompanyDAO est une couche de persistance permettant la liaison entre la classe Company et la base de données.
 * @author excilys
 *
 */
public class CompanyDAO {
	
	static String tableName = "company";
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static CompanyDAO companyDAO = null;
	CompanyMapper mapper;
	
	
	private CompanyDAO() {
		mapper = CompanyMapper.getInstance();
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
			Connection conn = DB.getInstance().getConnection();
			
			String req = "SELECT * FROM " + tableName;
			
			if(limit >= 0) { req += " LIMIT ?";}
			if(limit >= 0 && offset >= 0) { req += " OFFSET ?";}
			
			PreparedStatement ps = conn.prepareStatement(req);
			
			if(limit >= 0) { ps.setInt(1, limit);}
			if(limit >= 0 && offset >= 0) { ps.setInt(2, offset);}
			
			rs = ps.executeQuery();
						
			companies = mapper.resultSetToListCompany(rs);
			
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		
		
		return companies;
	}
	
	
	/**
	 * Retourne la Company en fonction de l'id
	 * @param id
	 * @return Company
	 */
	public Optional<Company> findCompanyById(int id) {
		Optional<Company> company = Optional.empty();
		
		try {
			Connection conn = DB.getInstance().getConnection();
			
			String req = "SELECT * FROM " + tableName +
						" WHERE id = ?";
			
			
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				company = Optional.ofNullable(mapper.resultSetToCompany(rs));
			}
			
			
									
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		
		return company;
	}

}
