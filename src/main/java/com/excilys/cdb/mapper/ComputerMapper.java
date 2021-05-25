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

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

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

}
