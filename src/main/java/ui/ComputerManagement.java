package main.java.ui;

import java.util.Scanner;

import main.java.model.Computer;
import main.java.persistence.ComputerDAO;

public class ComputerManagement {
	
	/**
	 * CLI racine des actions possibles sur les Computers
	 */
	public static void showRoot() {
		
		String res = "";
		Scanner sc = new Scanner(System.in);
		
		while(!res.equals("q")) {
			System.out.println("~~~~~~~~~~Computer~~~~~~~~~~");
			System.out.println("1 : Liste  / 2 : Detail by id / 3 : Create / 4 : Update / 5 : Delete / q : Quit ");
			
			res = sc.nextLine();
			
			switch(res) {
			case "1":
				showList();
				break;
			case "2":
				detail();
				break;
			case "3":
				
				break;
			case "4":
				update();
				break;
			case "5":
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
	
	/**
	 * Affiche la liste des ordinateurs "id : nom"
	 */
	public static void showList() {
		for(Computer computer : ComputerDAO.getComputers()) {
			//System.out.println(computer.getId() + " : " + computer.getName());
			System.out.println(computer.toString());
		}
	}
	
	/**
	 * Affichage de la CLI pour consulter les détails à propos d'un ordinateur
	 * Demande l'id de l'ordinateur pour lequel on souhaite les details et affiche les details s'il existe
	 */
	public static void detail() {
		//Récupération de l'id
		int id = UiUtils.askId();
		
		//Verification de l'id valide
		if(id == 0) return; 
		
		Computer computer = ComputerDAO.findComputerById(id);
		
		if(computer != null) {
			System.out.println(computer.toString());
		}else {
			System.out.println("L'ordinateur n'existe pas");
		}

	}
	
	/**
	 * Affichage de la CLI pour supprimer un ordinateur
	 */
	public static void delete() {
		//Récupération de l'id
		int id = UiUtils.askId();
		
		//Verification de l'id valide
		if(id == 0) return; 
		
		
		if(ComputerDAO.deleteComputer(id)) {
			System.out.println("Ordinateur supprimé"); //Suppression en BDD
		}else {
			System.out.println("Echec");
		}
	}
	
	public static void create() {
		String entry;
		Computer computer = new Computer();
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entrez le nom de l'ordinateur : ");
		entry = sc.nextLine();
		System.out.print("Entrez la date d'introduction (AAAA-MM-DD) : ");
		entry = sc.nextLine();
		
	}
	
	public static void update() {
		
	}

}
