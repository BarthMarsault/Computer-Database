package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

@WebServlet( name="Dashboard", urlPatterns = "/dashboard" )
public class Dashboard extends HttpServlet{
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		ArrayList<Computer> computers = (ArrayList<Computer>) ComputerDAO.getInstance().getComputerWithLimit(50, 0);
		System.out.println(ComputerDAO.getInstance().getComputerCount());
		
		request.setAttribute("nbComputer", ComputerDAO.getInstance().getComputerCount());
		request.setAttribute("computers", computers );
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}
	
}
