package com.excilys.cdb;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.ui.Root;


public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		

		
		
		/*for(Computer c : ComputerDAO.getInstance().findComputersByNameWithLimit("Mac",10,0)) {
			System.out.println(c.toString());
			
		}*/
		
		
		Root.printRoot();
		
		
		System.out.println("FIN");
	}

}
