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

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.CompanyDAO;

/**
 * Classe de Mapping de la classe Computer
 * @author excilys
 *
 */
public class ComputerMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	private static ComputerMapper computerMapper = null;
	
	private ComputerMapper() {
		
	}
	
	public static ComputerMapper getInstance() {
		if(computerMapper == null) {
			computerMapper = new ComputerMapper();
		}
		return computerMapper;
	}
	
	/**
	 * Retourne un objet Computer obtenu à partir d'un ResultSet
	 * @param rs ResultSet
	 * @return Une instance de la classe Computer
	 */
	public Optional<Computer> resultSetToComputer(ResultSet rs) {
		try {
			
			Date dIntr = rs.getDate(3);
			Date dDisc = rs.getDate(4);
			
			LocalDate ldIntr =  dIntr != null ? dIntr.toLocalDate() : null;
			LocalDate ldDisc = dDisc != null ? dDisc.toLocalDate() : null;
			
			Company company = new Company(rs.getInt(5), rs.getString(6));
			
			return Optional.ofNullable(new ComputerBuilder().withId(rs.getInt(1)).withName(rs.getString(2))
					.withIntroduced(ldIntr).withDiscontinued(ldDisc).withCompany(company).build());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Optional.empty();
	}
	
	public List<Computer> resultSetToListComputer(ResultSet rs) {
		ArrayList<Computer> computers = new ArrayList<>();
		
		try {
			while(rs.next()) {
				Optional<Computer> computer = resultSetToComputer(rs);
				if(computer.isPresent()) {
					computers.add(computer.get());
				}
				
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return computers;
	}
	
	
	public Optional<ComputerDTO> resultSetToComputerDTO(ResultSet rs){
		Optional<ComputerDTO> computerDTO = Optional.empty();
		try {
			Date dIntr = rs.getDate(3);
			Date dDisc = rs.getDate(4);
			
			String intr =  dIntr != null ? dIntr.toString() : null;
			String disc = dDisc != null ? dDisc.toString() : null;
					
			computerDTO = Optional.ofNullable(new ComputerDTOBuilder().withId(rs.getInt(1)).withName(rs.getString(2))
					.withIntroduced(intr).withDiscontinued(disc).withIdCompany(rs.getInt(5)).withNameCompany(rs.getString(6)).build());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		
		return computerDTO;
	}
	
	public List<ComputerDTO> resultSetToListComputerDTO(ResultSet rs) {
		ArrayList<ComputerDTO> computers = new ArrayList<>();
		
		try {
			while(rs.next()) {
				Optional<ComputerDTO> computer = resultSetToComputerDTO(rs);
				if(computer.isPresent()) {
					computers.add(computer.get());
				}
				
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return computers;
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
		
		LocalDate ldIntr = intr != null ? LocalDate.parse(intr) : null;
		LocalDate ldDisc = disc != null ? LocalDate.parse(disc) : null;
		
		
		//WIP A faire comme ça ????
		if(dto.getIdCompany() > 0) {
			company = CompanyDAO.getInstance().findById(dto.getIdCompany());
		}
		
		
		computer = Optional.ofNullable(new ComputerBuilder().withId(dto.getId()).withName(dto.getName())
				.withIntroduced(ldIntr).withDiscontinued(ldDisc)
				.withCompany(company.isPresent() ? company.get() : null).build());
		
		
		return computer;
	}

}
