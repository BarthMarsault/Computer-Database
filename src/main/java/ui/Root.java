package main.java.ui;

import java.util.Scanner;

public class Root {
	
	/**
	 * Affiche la vue racine du programme.
	 */
	public static void printRoot() {
		String res = "";
		Scanner sc = new Scanner(System.in);
		
		while(!res.equals("q")) {
			System.out.println("1 : Companies / 2 : Computers / q : Quit");
			
			res = sc.nextLine();
			
			switch(res) {			
				case "1":
					CompaniesList.showList();
					break;
				case "2":
					ComputerManagement.showRoot();
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
