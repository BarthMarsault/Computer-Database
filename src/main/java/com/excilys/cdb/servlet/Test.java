package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

@WebServlet( name="Test", urlPatterns = "/test" )
public class Test extends HttpServlet {



	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		List<Computer> computers = (List<Computer>) ComputerDAO.getInstance().getComputerWithLimit(50,0);


		
		String message = "Transmission de variables : OK !";
		request.setAttribute( "test", message );
		request.setAttribute( "computers", computers );
		
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
	}
	
}
