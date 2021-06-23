package com.excilys.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.service.CompanyService;

@RestController
public class CompanyAPI {
	
	CompanyService companyService;
	CompanyMapper mapper;
	
	public CompanyAPI(CompanyService companyService, CompanyMapper mapper) {
		super();
		this.companyService = companyService;
		this.mapper = mapper;
	}
	
	
	@GetMapping("/companies")
	List<CompanyDTO> all(){
		return companyService.getCompanies().stream()
				.map(c -> mapper.companyToCompanyDTO(c).get())
				.collect(Collectors.toList());
	}
	
	@GetMapping("/company/{id}")
	CompanyDTO getByUd(@PathVariable int id) {
		return mapper.companyToCompanyDTO(companyService.getById(id).get()).get();
	}
	
	@DeleteMapping("/company/{id}")
	boolean deleteCompany(@PathVariable int id) {
		return companyService.deleteCompany(id);
	}
	
	
	
	
}
