package com.excilys.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.service.CompanyService;

@RestController
@RequestMapping("/api")
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
	
	@GetMapping("/companies/{id}")
	CompanyDTO getByUd(@PathVariable int id) {
		return mapper.companyToCompanyDTO(companyService.getById(id).get()).get();
	}
	
	@DeleteMapping("/companies/{id}")
	boolean deleteCompany(@PathVariable int id) {
		return companyService.deleteCompany(id);
	}
	
	@PostMapping("/companies")
	boolean newComputer(@RequestBody CompanyDTO newCompany) {
		return companyService.addCompanyToDatabase(mapper.companyDtoToCompany(newCompany).get());
	}
	
	
	
}
