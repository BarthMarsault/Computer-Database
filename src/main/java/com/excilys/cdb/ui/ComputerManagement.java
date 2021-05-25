package com.excilys.cdb.ui;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.cdb.controller.ControllerComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerManagement {

	/**
	 * CLI racine des actions possibles sur les Computers
	 */
	public static void showRoot() {

		String res = "";
		Scanner sc = new Scanner(System.in);

		while (!res.equals("q")) {
			System.out.println("~~~~~~~~~~Computer~~~~~~~~~~");
			System.out.println("1 : Liste  / 2 : Detail by id / 3 : Create / 4 : Update / 5 : Delete / q : Quit ");

			res = sc.nextLine();

			switch (res) {
			case "1":
				useList();
				break;
			case "2":
				detail();
				break;
			case "3":
				ControllerComputer.createComputer();
				break;
			case "4":
				update();
				break;
			case "5":
				delete();
				break;
			case "q":
				break;
			default:
				System.out.println("Entrée invalide");
				break;
			}

		}
	}

	/**
	 * Affiche la liste de tous les ordinateurs "id : nom"
	 */
	public static void showList(List<Computer> computers) {
		for (Computer computer : computers) {
			System.out.println(computer.getId() + " : " + computer.getName());
			// System.out.println(computer.toString());
		}
	}


	/**
	 * Affiche la liste des ordinateurs par page 
	 */
	public static void useList() {
		String entry = "";
		Scanner sc = new Scanner(System.in);
		ControllerComputer.init();
		while (!entry.trim().toLowerCase().equals("q")) {
			System.out.println("~~~ N : next / P : privious / Q : Quit");
			entry = sc.nextLine();
			switch (entry.trim().toLowerCase()) {
			case "n": //Page Suivante
				ControllerComputer.showComputerNextPage();
				break;
			case "p": //Page Précédente
				ControllerComputer.showComputerPriviousPage();
				break;
			default:
				break;
			}
		}

	}

	/**
	 * Affichage de la CLI pour consulter les détails à propos d'un ordinateur
	 * Demande l'id de l'ordinateur pour lequel on souhaite les details et affiche
	 * les details s'il existe
	 */
	public static void detail() {
		// Récupération de l'id
		int id = UiUtils.askId("Id de l'ordinateur pour lequel vous souhaité des détails :");

		// Verification de l'id valide
		if (id == 0)
			return;

		Optional<Computer> computer = ComputerDAO.getInstance().findComputerById(id);

		if (computer.isPresent()) {
			System.out.println(computer.get().toString());
		} else {
			System.out.println("L'ordinateur n'existe pas");
		}

	}

	/**
	 * Affichage de la CLI pour supprimer un ordinateur
	 */
	public static void delete() {
		// Récupération de l'id
		int id = UiUtils.askId("Id de l'ordinateur que vous souhaitez supprimer :");

		// Verification de l'id valide
		if (id == 0)
			return;

		if (ComputerDAO.getInstance().deleteComputer(id)) {
			System.out.println("Ordinateur supprimé"); // Suppression en BDD
		} else {
			System.out.println("Echec");
		}
	}

	/**
	 * CLI de création d'un ordinateur
	 */
	public static Computer create() {
		String entry;
		ComputerBuilder computer = new ComputerBuilder();
		Scanner sc = new Scanner(System.in);
		LocalDate date = null;

		//Nom
		System.out.print("Entrez le nom de l'ordinateur : ");
		entry = sc.nextLine();
		computer.withName(entry);

		//Date d'introduction
		if (UiUtils.askYesNo("Voulez vous ajouter une date d'introduction :")) { // Yes/No
			System.out.print("Entrez la date d'introduction (AAAA-MM-DD) : ");
			date = UiUtils.askDate("Entrez la date d'introduction (AAAA-MM-DD) :");
		}
		computer.withIntroduced(date);
		date = null;
		//Date de discontinuité
		if (UiUtils.askYesNo("Voulez vous ajouter une date de discontinuité : ")) { // Yes/No
			date = null;
			date = UiUtils.askDate("Entrez la date de discontinuité (AAAA-MM-DD) :");
		}
		computer.withDiscontinued(date);
		
		//Constructeur
		if (UiUtils.askYesNo("Voulez vous ajouter constructeur :")) { // Yes/No
			boolean valid = false;
			System.out.println("Entrez l'id d'un constructeur : ");
			int id;
			while (!valid) {

				id = UiUtils.askId("Id du fabricant :");
				Optional<Company> company = CompanyDAO.getInstance().findCompanyById(id);
				if (!company.isPresent()) {
					System.out.println("l'id : " + id + " n'est pas valide");
				} else {					
					computer.withCompany(company.get());
					valid = true;
				}

			}

		}
		
		return computer.build();

	}

	/**
	 * Mise à jour d'un ou plusieurs attribut d'un ordinateur
	 */
	public static void update() {
		int idComputer = UiUtils.askId("Id de l'ordinateur pour lequel vous souhaité modifier:");
		Optional<Computer> computer = ComputerDAO.getInstance().findComputerById(idComputer);
		if (!computer.isPresent()) {
			System.out.println("L'ordinateur avec l'id " + idComputer + " n'éxiste pas");
		} else {
			System.out.println(computer.toString());
			Scanner sc = new Scanner(System.in);

			// Update du nom
			if (UiUtils.askYesNo("Voulez vous modifier le nom de l'ordinateur ? :")) {
				System.out.print("Entrez le nouveau nom : ");
				computer.get().setName(sc.nextLine());
			}

			// Update de la date d'introduction
			if (UiUtils.askYesNo("Voulez vous modifier la date d'introduction ? :")) {
				LocalDate dateIntr = UiUtils.askDate("Entrez la date d'introduction (AAAA-MM-DD) :");
				computer.get().setIntroduced(dateIntr);
			}

			// Update de la date de discontinuité
			if (UiUtils.askYesNo("Voulez vous modifier la date de discontinuité ? :")) {
				LocalDate dateDisc = UiUtils.askDate("Entrez la date de discontinuité (AAAA-MM-DD) :");
				computer.get().setDiscontinued(dateDisc);
			}

			// Update du fabricant
			if (UiUtils.askYesNo("Voulez vous modifier le fabricant :")) {
				int idCompany = UiUtils.askId("Entrez l'id du fabricant :");
				Optional<Company> company = CompanyDAO.getInstance().findCompanyById(idCompany);
				if (company.isPresent()) {
					computer.get().setCompany(company.get());
				} else {
					System.out.println("Ce Fabricant n'existe pas");
				}
			}

			// Validation
			if (UiUtils.askYesNo("Voulez vous enregistrer les changements sur : " + computer.toString())) {
				ComputerDAO.getInstance().updateComputer(computer.get());
				System.out.println("Changements enregistrer");
			} else {
				System.out.println("Annulation");
			}

		}

	}

}
