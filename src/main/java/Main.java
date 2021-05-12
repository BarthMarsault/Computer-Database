package main.java;

import java.time.LocalDate;

import main.java.model.Computer;
import main.java.persistence.ComputerDAO;
import main.java.ui.Root;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		
		
		/*
		for(Computer c : ComputerDAO.getComputers()) {
			System.out.println(c.toString());
			
		}
		*/
		
		Root.printRoot();
		
		
		
		
		System.out.println("FIN");
	}

}
