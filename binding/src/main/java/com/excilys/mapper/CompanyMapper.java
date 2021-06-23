package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.CompanyDTO.CompanyDTOBuilder;
import com.excilys.core.Company;
import com.excilys.core.Company.CompanyBuilder;

/**
 * Classe de mapping pour la classe Company
 * @author excilys
 *
 */
@Component
@Scope
public class CompanyMapper implements RowMapper<Company>{

	
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new CompanyBuilder().withId(rs.getInt(1)).withName(rs.getString(2)).build();
	}
	
	
	public Optional<CompanyDTO> companyToCompanyDTO(Company c) {
		return Optional.ofNullable(new CompanyDTOBuilder().withId(c.getId())
				.withName(c.getName()).build());
	}
	
	
	public Optional<Company> companyDtoToCompany(CompanyDTO c) {
		return Optional.ofNullable(new CompanyBuilder().withId(c.getId())
				.withName(c.getName()).build());
	}
	

	
}
