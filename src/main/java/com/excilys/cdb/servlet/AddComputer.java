package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet( name="AddComputer", urlPatterns = "/addComputer")
public class AddComputer extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		ArrayList<Company> companies = (ArrayList<Company>) CompanyDAO.getInstance().getCompanies();
		
		request.setAttribute("companies", companies );
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String name = request.getParameter("computerNameValue");
		String intr = request.getParameter("introducedValue");
		String disc = request.getParameter("discontinuedValue");
		int idCompany = Integer.parseInt(request.getParameter("companyIdValue"));
		
		System.out.println("JUI LA");

		
		ComputerDTO computerDTO = new ComputerDTOBuilder().withName(name).withIntroduced(intr)
				.withDiscontinued(disc).withIdCompany(idCompany).build();
		
		
		
		if(ComputerService.getInstance().addComputerToDatabase(computerDTO)) {
			//WIP - Redirection ?
			System.out.println("Ordi créé");
			//response.sendRedirect("dashboard");
			
			//request.setAttribute(PAGE_REQUEST, "last");                          
			//request.getRequestDispatcher("/dashboard/p-1").forward(request,response);
		}
		
		
		doGet(request, response);
	}

}
