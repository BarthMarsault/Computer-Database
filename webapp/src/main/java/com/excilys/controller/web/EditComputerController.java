package com.excilys.controller.web;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.mapper.ComputerMapper;
import com.excilys.core.Company;
import com.excilys.core.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
	
	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerMapper mapperComputer;
	private CompanyMapper mapperCompany;
	
	private static final Logger logger = LoggerFactory.getLogger(AddComputerController.class); 
	private final String ERROR_INVALID_COMPUTER = "Invalid Computer";
	private final String ERROR_STARAGE_FAIL = "Error in the process, retry later";
	
	
	public EditComputerController(CompanyService companyService, ComputerService computerService,
			ComputerMapper mapperComputer, CompanyMapper mapperCompany) {
		super();
		this.companyService = companyService;
		this.computerService = computerService;
		this.mapperComputer = mapperComputer;
		this.mapperCompany = mapperCompany;
	}
	
	
	
	@GetMapping
	public ModelAndView editComputer(@RequestParam(required = false) Integer requestId) {
		ModelAndView response = new ModelAndView();
		
	
		if(requestId == null) {
			return new ModelAndView("redirect:/dashboard");
		}
		//ComputerMapper mapperComputer = ComputerMapper.getInstance();
		Optional<ComputerDTO> computerDTO =  mapperComputer.computerToComputerDTO(computerService.getById(requestId));
		
		if(!computerDTO.isPresent()) {
			return new ModelAndView("redirect:/dashboard");
		}
		
		ArrayList<CompanyDTO> companies = new ArrayList<>();
		for(Company company : companyService.getCompanies()) {
			companies.add(mapperCompany.companyToCompanyDTO(company).get());
		}
		
		
		response.addObject("companies", companies);
		response.addObject("computer", computerDTO.get());
		
		return response;
		
	}
	
	@PostMapping
	public ModelAndView postEditComputer(@ModelAttribute("computer") ComputerDTO computerDTO) {	
		
		Optional<Computer> computer = mapperComputer.computerDtoToComputer(computerDTO);
		
		if(computer.isPresent() && computerService.updateComputer(computer.get())) {
			return new ModelAndView("redirect:/dashboard");
		}else {
			//request.setAttribute("errorMessage", ERROR_STARAGE_FAIL );
			logger.error("Fail in the storage process");
			//doGet(request, response);
		}
		return editComputer(computerDTO.getId());
			
	}
	

}
