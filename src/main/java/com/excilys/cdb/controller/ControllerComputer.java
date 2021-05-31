package com.excilys.cdb.controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.PageComputer;
import com.excilys.cdb.ui.ComputerManagement;

public class ControllerComputer {
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
		ComputerDAO.getInstance().create(computer);
		System.out.println(computer);
		System.out.println("Ordinateur créé");
	}
}
