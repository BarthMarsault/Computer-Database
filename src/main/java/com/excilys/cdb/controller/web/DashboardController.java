package com.excilys.cdb.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerAttribute;
import com.excilys.cdb.persistence.ComputerDAO.SortingRule;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.session.DashboardSession;


@Controller
public class DashboardController {

	private ComputerService computerService;
	private ComputerMapper mapper;
	DashboardSession session;
	

	public DashboardController(ComputerService computerService, ComputerMapper mapper, DashboardSession session) {
		super();
		this.computerService = computerService;
		this.mapper = mapper;
		this.session = session;
	}


	@GetMapping({"/","/dashboard"})
	public ModelAndView dashboard(@RequestParam(required = false) Map<String,String> allParams) {
		ModelAndView response = new ModelAndView();
		
		List<ComputerDTO> computers =  new ArrayList<>();
		ArrayList<Integer> pagePropositions = new ArrayList<>();
		int nbComputerByPage = 10;
		int currentPageNumber = 1;
		int numberOfPage;
		int nbComputer;
		String search = "";
		
		//ENUMs
		ComputerAttribute orderByAttribute = null;
		SortingRule sortingRule = null;
		
		//Init les Paramètres de la page
		updateSession(allParams);
		nbComputerByPage = session.getNbComputerByPage();		
		search = session.getSearch();
		nbComputer = computerService.getCountComputer(search);
		numberOfPage = (int) Math.ceil((double) nbComputer/nbComputerByPage);
		currentPageNumber = getCurrentPageNumber(allParams.get("pageRequest"), numberOfPage);
		
		
		orderByAttribute = session.getOrderByAttribute();
		sortingRule = session.getSortingRule();
			
		
		ArrayList<Computer> computersList = (ArrayList<Computer>) computerService.getComputersWithParamOrderedWithLimit(search,
				orderByAttribute, sortingRule, nbComputerByPage, (currentPageNumber-1)*nbComputerByPage);

		for(Computer computer : computersList) {
			computers.add(mapper.computerToComputerDTO(computer).get());
		}
		
				
		
		
		for(int i = currentPageNumber-2 ; (i <= currentPageNumber+2) && i < numberOfPage; i++) {
			if(i>1) {
				pagePropositions.add(i);
			}
		}
		
		
		response.addObject("nbComputer", nbComputer);
		response.addObject("computers", computers );
		response.addObject("pageNumber", currentPageNumber );
		response.addObject("numberOfPage", numberOfPage);
		response.addObject("pageProposition", pagePropositions);
		
		return response;
	}
	
	
	@RequestMapping(value  = "/dashboard", method = RequestMethod.POST)
	public ModelAndView postDashboard(@RequestParam(required = true) Map<String,String> allParams) {	
		if(allParams.get("selection") != null) {
			deleteComputers(allParams.get("selection"));			
		}
		return dashboard(allParams);
	}
	
	
	public void updateSession(Map<String,String> allParams) {
		int nbComputerByPage = 10;
		
		if(allParams.get("nbComputerByPage") != null) {
			nbComputerByPage = Integer.parseInt(allParams.get("nbComputerByPage"));
			session.setNbComputerByPage(nbComputerByPage);
		}
		
		if(allParams.get("search") != null) {
			session.setSearch(allParams.get("search"));
		}
		
		if(allParams.get("orderBy") != null) {
			session.setOrderByAttribute(getOrderByAttribute(allParams.get("orderBy")));
			switch (session.getSortingRule()) {
				case ASC :
					session.setSortingRule(SortingRule.DESC);
					break;
				case DESC :
					session.setSortingRule(SortingRule.NONE);
					break;
				case NONE:
				default:
					session.setSortingRule(SortingRule.ASC);
					break;
			}
		}
	}
	
	
	public void deleteComputers(String strComputerToDelete) {
		for(String idComputer : strComputerToDelete.split(",")) {
			int id = 0;
			
			try {
				id = Integer.parseInt(idComputer);
			}catch(Exception e) {
				
			}
			
			computerService.deleteComputer(id);
		}
	}
	
	public ComputerAttribute getOrderByAttribute(String orderBy) {
		ComputerAttribute attribute;
		
		switch (orderBy) {
			
			case "name" :
				attribute = ComputerAttribute.NAME;
				break;
			case "introduced" :
				attribute = ComputerAttribute.INTRODUCED;
				break;
			case "discontinued":
				attribute = ComputerAttribute.DISCONTINUED;
				break;
			case "company":
				attribute = ComputerAttribute.COMPANY;
				break;
			default:
				attribute = null;
				break;
		}
		
		return attribute;
	}
	

	
	
	public int getCurrentPageNumber(String pageRequest, int numberOfPage) {
		int currentPageNumber = 1;
		if(pageRequest != null) {
			currentPageNumber = Integer.parseInt(pageRequest);
			if(currentPageNumber > numberOfPage) { //Verification du paramètre pageRequest
				currentPageNumber = numberOfPage;
			}else if( currentPageNumber < 1) {
				currentPageNumber = 1;
			}
		}
		
		
		
		return currentPageNumber;
	}
}
