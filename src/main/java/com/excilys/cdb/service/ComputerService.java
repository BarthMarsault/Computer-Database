package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
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
	
	
	public boolean addComputerToDatabase(Optional<Computer> computer) {
		if(computer.isPresent()) {
			return ComputerDAO.getInstance().create(computer.get());
		}	
			
		return false;
	}
	
	public List<Computer> getComputersWithLimit(int limit, int offset){		
		return ComputerDAO.getInstance().getWithLimit(limit, offset);
	}

	
	
	
	

}
