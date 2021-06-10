package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Company.CompanyBuilder;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.validator.ComputerValidator;

/**
 * Classe de Mapping de la classe Computer
 * @author excilys
 *
 */
@Component
@Scope
public class ComputerMapper implements RowMapper<Computer>{
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	

	private ComputerValidator validator;
	
	

	public ComputerMapper(ComputerValidator validator) {
		super();
		this.validator = validator;
	}



	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Date dIntr = rs.getDate(3);
		Date dDisc = rs.getDate(4);
		
		LocalDate ldIntr =  dIntr != null ? dIntr.toLocalDate() : null;
		LocalDate ldDisc = dDisc != null ? dDisc.toLocalDate() : null;
		
		Company company = new Company(rs.getInt(5), rs.getString(6));
		
	
		
		return new ComputerBuilder().withId(rs.getInt(1)).withName(rs.getString(2))
				.withIntroduced(ldIntr).withDiscontinued(ldDisc).withCompany(company).build();
	}
	
	
	
	public Optional<ComputerDTO> computerToComputerDTO(Optional<Computer> c) {
		if(c.isPresent()) {
			return computerToComputerDTO(c.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	
	public Optional<ComputerDTO> computerToComputerDTO(Computer c) {
		Optional<ComputerDTO> computerDTO = Optional.empty();
		
		LocalDate dIntr = c.getIntroduced();
		LocalDate dDisc = c.getDiscontinued();
		
		String intr =  dIntr != null ? dIntr.toString() : null;
		String disc = dDisc != null ? dDisc.toString() : null;
		
		computerDTO = Optional.ofNullable(new ComputerDTOBuilder().withId(c.getId()).withName(c.getName())
				.withIntroduced(intr).withDiscontinued(disc)
				.withIdCompany(c.getCompany().getId()).withNameCompany(c.getCompany().getName()).build());
		
		return computerDTO;
	}
	
	public Optional<Computer> computerDtoToComputer(ComputerDTO dto){
		Optional<Computer> computer = Optional.empty();
		Optional<Company> company = Optional.empty();
		String intr = dto.getIntroduced();
		String disc = dto.getDiscontinued();
		
		if(validator.isValid(dto)) {
			LocalDate ldIntr = intr != null ? LocalDate.parse(intr) : null;
			LocalDate ldDisc = disc != null ? LocalDate.parse(disc) : null;
			
			
			//TODO A faire comme ça ????
			if(dto.getIdCompany() > 0) {
				company = Optional.ofNullable(new CompanyBuilder().withId(dto.getIdCompany()).build());
			}
			
			
			computer = Optional.ofNullable(new ComputerBuilder().withId(dto.getId()).withName(dto.getName())
					.withIntroduced(ldIntr).withDiscontinued(ldDisc)
					.withCompany(company.isPresent() ? company.get() : null).build());
					
		}else {
			logger.error("Invalid ComputerDTO");
		}
		
		
		
		
		return computer;
	}

	

}
