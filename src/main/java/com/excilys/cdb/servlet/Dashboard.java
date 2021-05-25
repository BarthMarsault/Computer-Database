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
	
	private int nbComputerByPage = 10;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		ArrayList<Computer> computers = (ArrayList<Computer>) ComputerDAO.getInstance().getComputersWithLimit(10, 0);
		System.out.println(ComputerDAO.getInstance().getComputerCount());
		
		request.setAttribute("nbComputer", ComputerDAO.getInstance().getComputerCount());
		request.setAttribute("computers", computers );
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		//int nbComputerByPage = Integer.parseInt(request.getParameter("nbComputerByPage"));
		ArrayList<Computer> computers = (ArrayList<Computer>) ComputerDAO.getInstance().getComputersWithLimit(nbComputerByPage, 0);
		System.out.println("PASSAGE POST : " + request.getParameter("nbComputerByPage"));
		
		request.setAttribute("nbComputer", ComputerDAO.getInstance().getComputerCount());
		request.setAttribute("computers", computers );
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}
	
	public void doOptions(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		String tmp = request.getParameter("nbComputerByPage");
		
		nbComputerByPage = Integer.parseInt(tmp);
		
	}
}