package com.excilys.cdb.controller.web;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerMapper mapperComputer;
	private CompanyMapper mapperCompany;
	
	private static final Logger logger = LoggerFactory.getLogger(AddComputerController.class); 
	private final String ERROR_INVALID_COMPUTER = "Invalid Computer";
	private final String ERROR_STARAGE_FAIL = "Error in the process, retry later";
	
	
	public AddComputerController(CompanyService companyService, ComputerService computerService,
			ComputerMapper mapperComputer, CompanyMapper mapperCompany) {
		super();
		this.companyService = companyService;
		this.computerService = computerService;
		this.mapperComputer = mapperComputer;
		this.mapperCompany = mapperCompany;
	}
	
	
	@GetMapping
	public ModelAndView addComputer() {
		ModelAndView response = new ModelAndView();
		
		ArrayList<CompanyDTO> companies = new ArrayList<>();
		for(Company company : companyService.getCompanies()) {
			companies.add(mapperCompany.companyToCompanyDTO(company).get());
		}
		
		
		response.addObject("computer",new ComputerDTO());
		response.addObject("companies", companies);
		
		return response;
		
	}
	
	
	
	@PostMapping
	public ModelAndView postAddComputer(@ModelAttribute("computer") ComputerDTO computerDTO) {

			Optional<Computer> computer = mapperComputer.computerDtoToComputer(computerDTO);
			if(computerService.addComputerToDatabase(computer)) {
				return new ModelAndView("redirect:/dashboard");
			}else {
				//request.setAttribute("errorMessage", ERROR_STARAGE_FAIL );
				logger.error("Fail in the storage process");
				//doGet(request, response);
			}
			return addComputer();
			
	}
	
	
}
