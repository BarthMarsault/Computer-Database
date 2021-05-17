package main.java.controller;

import main.java.model.Computer;
import main.java.persistence.ComputerDAO;
import main.java.service.PageComputer;
import main.java.ui.ComputerManagement;

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
		ComputerDAO.getInstance().createComputer(computer);
		System.out.println(computer);
		System.out.println("Ordinateur créé");
	}
}
