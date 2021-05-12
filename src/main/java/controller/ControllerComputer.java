package main.java.controller;

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
}
