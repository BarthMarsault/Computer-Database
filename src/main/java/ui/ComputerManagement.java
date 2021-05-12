package main.java.ui;

import java.time.*;
import java.util.Scanner;

import main.java.model.Computer;
import main.java.persistence.CompanyDAO;
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
				create();
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
			System.out.println(computer.getId() + " : " + computer.getName());
			//System.out.println(computer.toString());
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
		LocalDate date = null;
		
		System.out.print("Entrez le nom de l'ordinateur : ");
		entry = sc.nextLine();
		computer.setName(entry);
		
		
		if(UiUtils.askYesNo("Voulez vous ajouter une date d'introduction (y/n) :")) {
			System.out.print("Entrez la date d'introduction (AAAA-MM-DD) : ");
			entry = sc.nextLine();
			String[] parts = entry.split("-");
			date =  LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
			
		}
		computer.setIntroduced(date);
		date = null;
		if(UiUtils.askYesNo("Voulez vous ajouter une date de discontinuité (y/n) : ")) {
			date = null;
			System.out.print("Entrez la date de discountinuité (AAAA-MM-DD) : ");
			entry = sc.nextLine();
			String[] parts = entry.split("-");
			date =  LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
		}
		computer.setDiscontinued(date);
		
		
		if(UiUtils.askYesNo("Voulez vous ajouter constructeur (y/n) :")) {
			boolean valid = false;
			System.out.println("Entrez l'id d'un constructeur : ");
			int id;
			while(!valid) {
				
				id = UiUtils.askId();
				if(CompanyDAO.finCompanyById(id) == null) {
					System.out.println("l'id : " + id + " n'est pas valide");
				}else {
					computer.setCompany_id(id);
					valid = true;
				}
				
			}
			
		}else {
			computer.setCompany_id(0);
		}
		
		ComputerDAO.createComputer(computer);
		System.out.println(computer.toString());
		System.out.println("Ordinateur créé");
		
	}
	
	public static void update() {
		
		
	}

}
