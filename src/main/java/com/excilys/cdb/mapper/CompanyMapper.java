package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.CompanyDTO.CompanyDTOBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Company.CompanyBuilder;
import com.excilys.cdb.model.Computer;

/**
 * Classe de mapping pour la classe Company
 * @author excilys
 *
 */
@Component
@Scope
public class CompanyMapper implements RowMapper<Company>{
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new CompanyBuilder().withId(rs.getInt(1)).withName(rs.getString(2)).build();
	}
	
	/**
	 * Permet de transformer le contenu d'un ResultSet en objet Comapny
	 * @param rs ResultSet
	 * @return une instance de la classe Company
	 */
	public Optional<Company> resultSetToCompany(ResultSet rs) {
		Optional<Company> company = Optional.empty();
		try {
			company = Optional.ofNullable(new Company(rs.getInt(1), rs.getString(2)));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return company;
	}
	
	public List<Company> resultSetToListCompany(ResultSet rs){
		ArrayList<Company> companies = new ArrayList<>();
		
		try {
			while(rs.next()) {
				Optional<Company> company = resultSetToCompany(rs);
				if(company.isPresent()) {
					companies.add(company.get());
				}
				
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		
		return companies;
	}
	
	
	public Optional<CompanyDTO> companyToCompayDTO(Company c) {
		return Optional.ofNullable(new CompanyDTOBuilder().withId(c.getId())
				.withName(c.getName()).build());
	}

	
}
