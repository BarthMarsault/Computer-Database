package com.excilys.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import com.excilys.core.Computer;
import com.excilys.persistence.ComputerAttribute;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ComputerDAO.SortingRule;

@Service
@Scope
public class ComputerService {
	
	private ComputerDAO computerDAO;
	
	

	
	public ComputerService(ComputerDAO computerDAO) {
		super();
		this.computerDAO = computerDAO;
	}


	public boolean addComputerToDatabase(Optional<Computer> computer) {
		if(computer.isPresent() ) {
			return computerDAO.create(computer.get()) == 1;
		}	
			
		return false;
	}
	
	public List<Computer> getAll(){
		return computerDAO.getAll();
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
	
	public boolean deleteComputer(List<Integer> listIds) {
		return computerDAO.delete(listIds);
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
