package com.excilys.cdb.service;

import java.util.Optional;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {
	
	private static ComputerService computerService = null;
	
	private ComputerService() {
		
	}
	
	public static ComputerService getInstance() {
		if(computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}
	
	
	public boolean addComputerToDatabase(ComputerDTO dto) {
		if(!dto.isValid()) {
			return false;
		}
		
		
		Optional<Computer>computer = ComputerMapper.getInstance().computerDtoToComputer(dto);

		if(computer.isPresent()) {
			return ComputerDAO.getInstance().createComputer(computer.get());
		}	
			
		return false;
	}
	

	
	
	
	

}
