package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet( name="AddComputer", urlPatterns = "/addComputer")
public class AddComputer extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(AddComputer.class); 
	private final String ERROR_INVALID_COMPUTER = "Invalid Computer";
	private final String ERROR_STARAGE_FAIL = "Error in the process, retry later";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		ArrayList<CompanyDTO> companies = new ArrayList<>();
		CompanyMapper mapper = CompanyMapper.getInstance();
		for(Company company : CompanyService.getInstance().getCompanies()) {
			companies.add(mapper.companyToCompayDTO(company).get());
		}
		
		
		
		request.setAttribute("companies", companies );
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String name = request.getParameter("computerNameValue");
		String intr = request.getParameter("introducedValue");
		String disc = request.getParameter("discontinuedValue");
		int idCompany = Integer.parseInt(request.getParameter("companyIdValue"));
	
		ComputerDTO computerDTO = new ComputerDTOBuilder().withName(name).withIntroduced(intr)
				.withDiscontinued(disc).withIdCompany(idCompany).build();
		Optional<Computer> computer = Optional.empty();
		
		
		if(computerDTO.isValid()) {
			computer = ComputerMapper.getInstance().computerDtoToComputer(computerDTO);
		}else {
			request.setAttribute("errorMessage", ERROR_INVALID_COMPUTER );
			logger.error("Invalid Computer");
			doGet(request, response);
			return;
		}
		
		if(ComputerService.getInstance().addComputerToDatabase(computer)) {
			response.sendRedirect("dashboard");
		}else {
			request.setAttribute("errorMessage", ERROR_STARAGE_FAIL );
			logger.error("Fail in the storage process");
			doGet(request, response);
		}
		
	}

}
