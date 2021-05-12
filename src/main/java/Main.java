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
		LocalDate d = LocalDate.of(2000, 10, 20);
		Computer c = new Computer("test",d,null,0);
		
		
		
		System.out.println("FIN");
	}

}
