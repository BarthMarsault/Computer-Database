package com.excilys.cdb.ui;

import java.util.Scanner;

import com.excilys.cdb.controller.console.ControllerCompany;

public class Root {
	
	/**
	 * Affiche la vue racine du programme.
	 */
	public static void printRoot() {
		String res = "";
		Scanner sc = new Scanner(System.in);
		
		while(!res.equals("q")) {
			System.out.println("1 : Companies / 2 : Computers / 3 : Delete  Company / q : Quit");
			
			res = sc.nextLine();
			
			switch(res) {			
				case "1":
					ControllerCompany.showAllCompanies();
					break;
				case "2":
					ComputerManagement.showRoot();
					break;
				case "3":
					ControllerCompany.deleteCompany();
					break;					
				case "q":
					break;
				default :
					System.out.println("Entrée invalide");
					break;
			
			}
		}
		sc.close();
		
		
	}
}
