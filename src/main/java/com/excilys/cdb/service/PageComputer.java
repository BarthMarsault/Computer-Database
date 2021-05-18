package com.excilys.cdb.service;


import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

/**
 * Classe permettant la gestion de la pagination dans l'affichage des "Computers"
 * @author excilys
 *
 */
public class PageComputer {
	private int nbLine;
	private int currentLine;
	private ComputerDAO computerDAO;
	
	private static PageComputer page = null;
	
	private PageComputer() {
		this.nbLine = 10;
		this.currentLine = 0;
		computerDAO = ComputerDAO.getInstance();
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
		return computerDAO.getComputerWithLimit(nbLine, currentLine);
	}
	
	/**
	 * Modifie les paramètres fonction de la page actuelle et retourne les ordinateurs de la page suivante.
	 * @return Une liste d'ordinateur
	 */
	public List<Computer> nextPage(){
		currentLine += nbLine;
		List<Computer> computers = computerDAO.getComputerWithLimit(nbLine, currentLine);
		
		if(computers.size() == 0) {
			currentLine -= nbLine;			
		}
		return computers;
	}
	
	/**
	 * Modifie les paramètres fonction de la page actuelle et retourne les ordinateurs de la page précédente.
	 * @return Une liste d'ordinateur
	 */
	public List<Computer> priviousPage(){
		
		
		if(currentLine - nbLine >= 0) {
			currentLine -= nbLine;			
		}else {
			currentLine = 0;
		}
		List<Computer> computers = computerDAO.getComputerWithLimit(nbLine, currentLine);
		return computers;
	}
	
	
	
}
