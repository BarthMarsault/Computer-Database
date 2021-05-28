package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.PageComputer;

@WebServlet( name="Dashboard", urlPatterns = { "/dashboard" })
public class Dashboard extends HttpServlet{
	
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		HttpSession session = request.getSession();
		
		int nbComputerByPage = 10;
		int currentPageNumber = 1;
		int numberOfPage;
		int nbComputer;
		
		
		if(request.getParameter("pageRequest") != null){
			currentPageNumber = Integer.parseInt(request.getParameter("pageRequest"));
		}
		
		if(request.getParameter("nbComputerByPage") != null) {
			nbComputerByPage = Integer.parseInt(request.getParameter("nbComputerByPage"));
			session.setAttribute("nbComputerByPage", String.valueOf(nbComputerByPage));
		}else { //Initialisation de session.nbComputerByPage si vide
			String tmp = (String) session.getAttribute("nbComputerByPage");
			if((String) session.getAttribute("nbComputerByPage") == null) {
				session.setAttribute("nbComputerByPage", "10");
			}
			nbComputerByPage = Integer.parseInt(session.getAttribute("nbComputerByPage").toString());
		}

		
		
		
		
		
		nbComputer = ComputerDAO.getInstance().getComputerCount();
		numberOfPage = (int) Math.ceil((double) nbComputer/nbComputerByPage);

		
		
		ArrayList<Integer> pagePropositions = new ArrayList<>();
		for(int i = currentPageNumber-2 ; (i <= currentPageNumber+2) && i < numberOfPage; i++) {
			if(i>1) {
				pagePropositions.add(i);
			}
		}
		
		ArrayList<ComputerDTO> computers = (ArrayList<ComputerDTO>) ComputerService.getInstance().getComputerDTOWithLimit(nbComputerByPage, (currentPageNumber-1)*nbComputerByPage);
		
		request.setAttribute("nbComputer", nbComputer);
		request.setAttribute("computers", computers );
		request.setAttribute("pageNumber", currentPageNumber );
		request.setAttribute("numberOfPage", numberOfPage);
		request.setAttribute("pageProposition", pagePropositions);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		//int nbComputerByPage = Integer.parseInt(request.getParameter("nbComputerByPage"));
		/*ArrayList<Computer> computers = (ArrayList<Computer>) ComputerDAO.getInstance().getComputersWithLimit(10, 0);
		System.out.println("PASSAGE POST : " + request.getParameter("nbComputerByPage"));
		
		request.setAttribute("nbComputer", ComputerDAO.getInstance().getComputerCount());
		request.setAttribute("computers", computers );
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);*/
		doGet(request, response);
	}

}
