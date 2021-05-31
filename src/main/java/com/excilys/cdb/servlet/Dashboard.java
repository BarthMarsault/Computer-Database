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
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.PageComputer;

@WebServlet( name="Dashboard", urlPatterns = { "/dashboard" })
public class Dashboard extends HttpServlet{
	
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		HttpSession session = request.getSession();
		ComputerMapper mapper = ComputerMapper.getInstance();
		List<ComputerDTO> computers =  new ArrayList<>();
		int nbComputerByPage = 10;
		int currentPageNumber = 1;
		int numberOfPage;
		int nbComputer;
		
		
		if(request.getParameter("pageRequest") != null){
			currentPageNumber = Integer.parseInt(request.getParameter("pageRequest"));
		}
		
		
		
		session = updateSession(request);
		nbComputerByPage = Integer.parseInt(session.getAttribute("nbComputerByPage").toString());
		
		
		
		String param = session.getAttribute("search").toString();
		nbComputer = ComputerDAO.getInstance().getCount(param);
		numberOfPage = (int) Math.ceil((double) nbComputer/nbComputerByPage);
		
		for(Computer computer : ComputerService.getInstance().getComputersWithParamWithLimit(param, nbComputerByPage, (currentPageNumber-1)*nbComputerByPage)) {
			computers.add(mapper.computerToComputerDTO(computer).get());
		}

		
		
		ArrayList<Integer> pagePropositions = new ArrayList<>();
		for(int i = currentPageNumber-2 ; (i <= currentPageNumber+2) && i < numberOfPage; i++) {
			if(i>1) {
				pagePropositions.add(i);
			}
		}
		
		
		
		request.setAttribute("nbComputer", nbComputer);
		request.setAttribute("computers", computers );
		request.setAttribute("pageNumber", currentPageNumber );
		request.setAttribute("numberOfPage", numberOfPage);
		request.setAttribute("pageProposition", pagePropositions);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		if(request.getParameter("selection") != null) {
			deleteComputers(request.getParameter("selection"));			
		}
		doGet(request, response);
	}
	
	
	public HttpSession updateSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int nbComputerByPage = 10;
		
		if(request.getParameter("nbComputerByPage") != null) {
			nbComputerByPage = Integer.parseInt(request.getParameter("nbComputerByPage"));
			session.setAttribute("nbComputerByPage", String.valueOf(nbComputerByPage));
		}else { //Initialisation de session.nbComputerByPage si vide
			if((String) session.getAttribute("nbComputerByPage") == null) {
				session.setAttribute("nbComputerByPage", "10");
			}
		}
		
		if(request.getParameter("search") != null) {
			session.setAttribute("search", request.getParameter("search"));
		}else {
			if(session.getAttribute("search") == null) {
				session.setAttribute("search", "");
			}
		}
		
		return session;
	}
	
	
	public void deleteComputers(String strComputerToDelete) {
		for(String idComputer : strComputerToDelete.split(",")) {
			int id = 0;
			
			try {
				id = Integer.parseInt(idComputer);
			}catch(Exception e) {
				
			}
			
			ComputerService.getInstance().deleteComputer(id);
		}
	}

}
