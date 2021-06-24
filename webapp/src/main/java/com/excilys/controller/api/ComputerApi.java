package com.excilys.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.ComputerService;

@RestController
@RequestMapping("/api")
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
		return computerService.getAll().stream()
				.map(c -> mapper.computerToComputerDTO(c).get())
				.collect(Collectors.toList());
	}
	
	@GetMapping("/computers/{id}")
	ComputerDTO getById(@PathVariable Integer id) {
		return mapper.computerToComputerDTO(computerService.getById(id)).get();
	}
	
	@DeleteMapping("/computers/{id}")
	void deleteComputer(@PathVariable Integer id) {
		computerService.deleteComputer(id);
	}
	
	@PostMapping("/computers")
	boolean newComputer(@RequestBody ComputerDTO newComputer) {
		return computerService.addComputerToDatabase(mapper.computerDtoToComputer(newComputer));		
	}
	
	@PutMapping("/computers/{id}")
	boolean replaceComputer(@RequestBody ComputerDTO newComputer, @PathVariable Integer id) {
		newComputer.setId(id);
		if(computerService.getById(id).isPresent()) {
			return computerService.updateComputer(mapper.computerDtoToComputer(newComputer).get());
		} else {
	        return computerService.addComputerToDatabase(mapper.computerDtoToComputer(newComputer));
		}
	}
	

}
