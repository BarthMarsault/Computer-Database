package com.excilys.cdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.ui.Root;


public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
