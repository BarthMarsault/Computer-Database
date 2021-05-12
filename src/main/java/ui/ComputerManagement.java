package main.java.ui;

import java.time.*;
import java.util.Scanner;

import main.java.model.Company;
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
		int id = UiUtils.askId("Id de l'ordinateur pour lequel vous souhaité des détails :");
		
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
		int id = UiUtils.askId("Id de l'ordinateur que vous souhaitez supprimer :");
		
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
		
		
		if(UiUtils.askYesNo("Voulez vous ajouter une date d'introduction :")) { //Yes/No
			System.out.print("Entrez la date d'introduction (AAAA-MM-DD) : ");
			date = UiUtils.askDate("Entrez la date d'introduction (AAAA-MM-DD) :");	
		}
		computer.setIntroduced(date);
		date = null;
		if(UiUtils.askYesNo("Voulez vous ajouter une date de discontinuité : ")) { //Yes/No
			date = null;
			date = UiUtils.askDate("Entrez la date de discontinuité (AAAA-MM-DD) :");
		}
		computer.setDiscontinued(date);
		
		
		if(UiUtils.askYesNo("Voulez vous ajouter constructeur :")) { //Yes/No
			boolean valid = false;
			System.out.println("Entrez l'id d'un constructeur : ");
			int id;
			while(!valid) {
				
				id = UiUtils.askId("Id du fabricant :");
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
		int idComputer = UiUtils.askId("Id de l'ordinateur pour lequel vous souhaité modifier:");
		Computer computer = ComputerDAO.findComputerById(idComputer);
		if(computer == null) {
			System.out.println("L'ordinateur avec l'id " + idComputer + " n'éxiste pas");
		}else {
			System.out.println(computer.toString());
			Scanner sc = new Scanner(System.in);
			
			
			//Update du nom
			if(UiUtils.askYesNo("Voulez vous modifier le nom de l'ordinateur ? :")) {
				System.out.print("Entrez le nouveau nom : ");
				computer.setName(sc.nextLine());
			}
			
			//Update de la date d'introduction
			if(UiUtils.askYesNo("Voulez vous modifier la date d'introduction ? :")) {
				LocalDate dateIntr = UiUtils.askDate("Entrez la date d'introduction (AAAA-MM-DD) :");
				computer.setIntroduced(dateIntr);
			}
			
			//Update de la date de discontinuité
			if(UiUtils.askYesNo("Voulez vous modifier la date de discontinuité ? :")) {
				LocalDate dateDisc = UiUtils.askDate("Entrez la date de discontinuité (AAAA-MM-DD) :");
				computer.setDiscontinued(dateDisc);
			}
			
			//Update du fabricant
			if(UiUtils.askYesNo("Voulez vous modifier le fabricant :")) {
				int idCompany = UiUtils.askId("Entrez l'id du fabricant :");
				Company company = CompanyDAO.finCompanyById(idCompany);
				if(company != null) {
					computer.setCompany_id(idCompany);
				}else{
					System.out.println("Ce Fabricant n'existe pas");
				}
			}
			
			//Validation
			if(UiUtils.askYesNo("Voulez vous enregistrer les changements sur : " + computer.toString())) {
				ComputerDAO.updateComputer(computer);
				System.out.println("Changements enregistrer");
			}else {
				System.out.println("Annulation");
			}
			
		}
		
		
	}

}
