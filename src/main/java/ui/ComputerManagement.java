package main.java.ui;

import java.util.Scanner;

import main.java.model.Computer;
import main.java.persistence.ComputerDAO;

public class ComputerManagement {
	
	public static void showRoot() {
		
		String res = "";
		Scanner sc = new Scanner(System.in);
		
		while(!res.equals("q")) {
			System.out.println("~~~~~~~~~~Computer~~~~~~~~~~");
			System.out.println("1 : Liste  / 2 : Detail by id  / 3 : Update / 4 : Delete / q : Quit ");
			res = sc.nextLine();
			
			switch(res) {
			case "1":
				showList();
				break;
			case "2":
				detail();
				break;
			case "3":
				update();
				break;
			case "4":
				delete();
				break;
			case "q":
				break;
			default :
				System.out.println("Entrée invalide");
				break;
			}
			
		}
	}
	
	public static void showList() {
		for(Computer computer : ComputerDAO.getComputers()) {
			System.out.println(computer.getId() + " : " + computer.getName());
		}
	}
	
	public static void detail() {
		int id = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Id de l'ordinateur pour lequel vous souhaité des détails :");
		try {
			id = sc.nextInt();
		}catch(Exception e) {
			System.out.println("Votre entrée n'est pas une id valide");
		}
		
		
		
		Computer computer = ComputerDAO.findComputerById(id);
		
		if(computer != null) {
			System.out.println(computer.toString());
		}else {
			System.out.println("L'ordinateur n'existe pas");
		}
	}
	
	public static void delete() {
		int id = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Id de l'ordinateur pour lequel vous souhaité des détails :");
		
		try {
			id = sc.nextInt();
		}catch(Exception e) {
			System.out.println("Votre entrée n'est pas une id valide");
		}
		
		
		
		if(ComputerDAO.deleteComputer(id)) {
			System.out.println("Ordinateur supprimé");
		}else {
			System.out.println("Echec");
		}
	}
	
	
	public static void update() {
		
	}

}
