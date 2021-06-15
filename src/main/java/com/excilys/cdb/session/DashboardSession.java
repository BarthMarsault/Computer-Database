package com.excilys.cdb.session;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.excilys.cdb.persistence.ComputerAttribute;
import com.excilys.cdb.persistence.ComputerDAO.SortingRule;

@Component
@SessionScope
public class DashboardSession {
	private int nbComputerByPage = 10;
	private int currentPageNumber = 1;
	private String search = "";
	
	//ENUMs
	ComputerAttribute orderByAttribute = null;
	SortingRule sortingRule = SortingRule.NONE;
	
	
	
	
	
	public int getNbComputerByPage() {
		return nbComputerByPage;
	}
	public void setNbComputerByPage(int nbComputerByPage) {
		this.nbComputerByPage = nbComputerByPage;
	}
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	public ComputerAttribute getOrderByAttribute() {
		return orderByAttribute;
	}
	public void setOrderByAttribute(ComputerAttribute orderByAttribute) {
		this.orderByAttribute = orderByAttribute;
	}
	public SortingRule getSortingRule() {
		return sortingRule;
	}
	public void setSortingRule(SortingRule sortingRule) {
		this.sortingRule = sortingRule;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	
	
	
	
}
