package com.excilys.cdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.PageComputer;
import com.excilys.cdb.ui.ComputerManagement;

@Controller
public class ControllerComputer {
	
	static ComputerDAO computerDAO;
	
	@Autowired
	public void initDAO(ComputerDAO computerDAO) {
		ControllerComputer.computerDAO = computerDAO;
	}
	
	private static PageComputer page = PageComputer.getPageComputer();
	
	public static void init() {
		ComputerManagement.showList(page.initPage());
	}
	
	public static void showComputerNextPage() {
		ComputerManagement.showList(page.nextPage());
	}
	
	public static void showComputerPriviousPage() {
		ComputerManagement.showList(page.priviousPage());
	}
	
	public static void createComputer() {
		Computer computer = ComputerManagement.create();
		computerDAO.create(computer);
		System.out.println(computer);
		System.out.println("Ordinateur créé");
	}
}
