package main.java.controller;


import java.util.List;

import main.java.model.Computer;
import main.java.persistence.ComputerDAO;

public class PageComputer {
	private int nbLine;
	private int currentLine;
	
	private static PageComputer page = null;
	
	private PageComputer() {
		this.nbLine = 10;
		this.currentLine = 0;
	}
	
	public static PageComputer getPageComputer() {
		if(page == null) {
			page = new PageComputer();
		}
		return page;
	}
	
	public void close() {
		page = null;
	}
	
	public List<Computer> initPage(){
		return ComputerDAO.getComputerWithLimit(nbLine, currentLine);
	}
	
	public List<Computer> nextPage(){
		currentLine += nbLine;
		List<Computer> computers = ComputerDAO.getComputerWithLimit(nbLine, currentLine);
		
		if(computers.size() == 0) {
			currentLine -= nbLine;			
		}
		return computers;
	}
	
	public List<Computer> priviousPage(){
		
		
		if(currentLine - nbLine >= 0) {
			currentLine -= nbLine;			
		}else {
			currentLine = 0;
		}
		List<Computer> computers = ComputerDAO.getComputerWithLimit(nbLine, currentLine);
		return computers;
	}
	
	
	
}
