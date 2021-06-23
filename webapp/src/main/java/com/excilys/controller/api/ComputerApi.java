package com.excilys.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.core.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.ComputerService;

@RestController
public class ComputerApi {
	
	ComputerService computerService;
	ComputerMapper mapper;
	
	
	
	public ComputerApi(ComputerService computerService, ComputerMapper mapper) {
		super();
		this.computerService = computerService;
		this.mapper = mapper;
	}



	@GetMapping("/computers")
	List<ComputerDTO> all(){
		List<ComputerDTO> computers =  new ArrayList<>();

		for(Computer computer : computerService.getAll()) {
			computers.add(mapper.computerToComputerDTO(computer).get());
		}
		
		return computers;
	}
	
	@GetMapping("/computer/{id}")
	ComputerDTO getById(@PathVariable Integer id) {
		return mapper.computerToComputerDTO(computerService.getById(id)).get();
	}
	
	@DeleteMapping("/computer/{id}")
	void deleteComputer(@PathVariable Integer id) {
		computerService.deleteComputer(id);
	}
	
	@PostMapping("/computer")
	boolean newComputer(@RequestBody ComputerDTO newComputer) {
		return computerService.addComputerToDatabase(mapper.computerDtoToComputer(newComputer));		
	}
	
	@PutMapping("/computer/{id}")
	boolean replaceComputer(@RequestBody ComputerDTO newComputer, @PathVariable Integer id) {
		newComputer.setId(id);
		if(computerService.getById(id).isPresent()) {
			return computerService.updateComputer(mapper.computerDtoToComputer(newComputer).get());
		} else {
	        return computerService.addComputerToDatabase(mapper.computerDtoToComputer(newComputer));
		}
	}
	

}
