package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.Main;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	

	private CompanyService companyService;

	private ComputerService computerService;

	private ComputerMapper mapperComputer;

	private CompanyMapper mapperCompany;
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AddComputer.class); 
	private final String ERROR_INVALID_COMPUTER = "Invalid Computer";
	private final String ERROR_STARAGE_FAIL = "Error in the process, retry later";
       

	public void init() {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		computerService = context.getBean(ComputerService.class);
		companyService = context.getBean(CompanyService.class);
		mapperComputer = context.getBean(ComputerMapper.class);
		mapperCompany = context.getBean(CompanyMapper.class);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("requestId") == null) {
			response.sendRedirect("dashboard");
			return;
		}
		//ComputerMapper mapperComputer = ComputerMapper.getInstance();
		Optional<ComputerDTO> computerDTO =  mapperComputer.computerToComputerDTO(computerService.getById(Integer.parseInt(request.getParameter("requestId"))));
		
		if(!computerDTO.isPresent()) {
			response.sendRedirect("dashboard");
			return;
		}
		
		ArrayList<CompanyDTO> companies = new ArrayList<>();
		for(Company company : companyService.getCompanies()) {
			companies.add(mapperCompany.companyToCompanyDTO(company).get());
		}
		
		
		
		request.setAttribute("companies", companies );		
		request.setAttribute("computer", computerDTO.get() );
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("idValue"));
		String name = request.getParameter("computerNameValue");
		String intr = request.getParameter("introducedValue");
		String disc = request.getParameter("discontinuedValue");
		int idCompany = Integer.parseInt(request.getParameter("companyIdValue"));
	
		ComputerDTO computerDTO = new ComputerDTOBuilder().withId(id).withName(name).withIntroduced(intr)
				.withDiscontinued(disc).withIdCompany(idCompany).build();
		Optional<Computer> computer = Optional.empty();
		
		
		
		
		computer = mapperComputer.computerDtoToComputer(computerDTO);
		if(!computer.isPresent()) {
			request.setAttribute("errorMessage", ERROR_INVALID_COMPUTER );
			logger.error("No Computer Found");
			doGet(request, response);
			return;
		}
		
		if(computer.isPresent() && computerService.updateComputer(computer.get())) {
			response.sendRedirect("dashboard");
		}else {
			request.setAttribute("errorMessage", ERROR_STARAGE_FAIL );
			logger.error("Fail in the storage process");
			doGet(request, response);
		}
	}

}
