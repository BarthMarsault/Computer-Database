package com.excilys.cdb.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

/**
 * CompanyDAO est une couche de persistance permettant la liaison entre la classe Company et la base de données.
 * @author excilys
 *
 */
@Repository
@Scope
public class CompanyDAO {

	static String tableName = "company";
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);


	private CompanyMapper mapper;
	
	private JdbcTemplate jdbcTemplate;
	
	private final String sqlFindCompanyById = "SELECT * FROM " + tableName + " WHERE id = ?";
	
	private final String sqlGetCompanies = "SELECT * FROM " + tableName;
	
	private final String sqlDeleteCompany = "DELETE FROM " + tableName + " WHERE  id = ?";
	
	private final String sqlDeleteComputer = "DELETE FROM computer WHERE company_id = ?";
	

	
	
	
	public CompanyDAO(CompanyMapper mapper, DataSource dataSource) {
		super();
		this.mapper = mapper;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	/**
	 * Retourne la liste de toutes les "Company" présente en base de données.
	 * @return
	 */
	public List<Company> getAll(){
		return getWithLimit(-1,-1);
	}
	
	
	public List<Company> getWithLimit(int limit, int offset){
		List<Company> companies = new ArrayList<>();
		
		
		String req = sqlGetCompanies;
		
		if(limit >= 0) { req += " LIMIT ?";}
		if(limit >= 0 && offset >= 0) { req += " OFFSET ?";}
				

		
		if(limit >= 0 && offset >= 0) {
			companies = jdbcTemplate.query(req, mapper, limit, offset);
		}
		else if(limit >= 0) {
			companies = jdbcTemplate.query(req, mapper, limit);
		}
		else {
			companies = jdbcTemplate.query(req, mapper);
		}
		
		if(companies.size() == 0) {
			logger.trace("Retour d'un liste de Company vide");
		}
		
		return companies;
	}
	
	
	/**
	 * Retourne la Company en fonction de l'id
	 * @param id
	 * @return Company
	 */
	public Optional<Company> findById(int id) {
		return Optional.ofNullable(jdbcTemplate.queryForObject(sqlFindCompanyById, new Object[] { id }, mapper));
	}
	
	
	@Transactional
	public boolean delete(int id) {
		
		if(id < 1) {
			logger.trace("Tentative de suppression d'une company sans id");
			return false;
		}
		
		jdbcTemplate.update(sqlDeleteComputer,id);
		jdbcTemplate.update(sqlDeleteCompany,id);
		return true;
		
		
	}

}
