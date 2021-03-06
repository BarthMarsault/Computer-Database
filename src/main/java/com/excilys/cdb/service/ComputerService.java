package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerAttribute;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.persistence.ComputerDAO.SortingRule;

@Service
@Scope
public class ComputerService {
	
	@Autowired
	private ComputerDAO computerDAO;
	

	
	public boolean addComputerToDatabase(Optional<Computer> computer) {
		if(computer.isPresent()) {
			return computerDAO.create(computer.get()) == 1;
		}	
			
		return false;
	}
	
	
	public List<Computer> getComputersWithParamOrderedWithLimit(String param,
			ComputerAttribute attribute, SortingRule sr, int limit, int offset) {
		return computerDAO.FindWithParamOrderedWithLimit(param, attribute, sr, limit, offset);
	}
	
	public Optional<Computer> getById(int id){
		return computerDAO.findById(id);
	}
	
	public boolean updateComputer(Computer computer) {
		return computerDAO.update(computer) == 1;
	}
	
	public boolean deleteComputer(int id) {
		return computerDAO.delete(id) == 1;
	}
	
	public int getCountComputer() {
		return computerDAO.getCount();
	}
	
	public int getCountComputer(String param) {
		if(param == null || param.equals("")) {
			return getCountComputer();
		}
		return computerDAO.getCount(param);
	}

	
		

}
