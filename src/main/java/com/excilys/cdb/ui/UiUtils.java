package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.util.Scanner;

public class UiUtils {
	
	public static int askId(String msg) {
		int id = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println(msg + " ");
		
		try {
			id = sc.nextInt();
		}catch(Exception e) {
			System.out.println("Votre entrée n'est pas une id valide");
		}
		return id;
	}
	
	public static boolean askYesNo(String msg) {
		Scanner sc = new Scanner(System.in);
		System.out.print(msg + " (y/n) ");
		String entry = "";
		entry = sc.nextLine();
		entry = entry.trim().toLowerCase();
		while(!entry.equals("y") && !entry.equals("n")) {
			System.out.println("Entrée invalide - " + msg);
			entry = sc.nextLine();
		}
		if(entry.equals("y"))
			return true;
		else
			return false;
		
	}
	
	public static LocalDate askDate(String msg) {
		Scanner sc = new Scanner(System.in);
		String entry;
		
		System.out.print(msg + " ");
		entry = sc.nextLine();
		String[] parts = entry.split("-");
		
		
		LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
		
		if(!date.isAfter(LocalDate.of(1969,12,31))) {
			date = null;
		}
		
		return date;
	}

}
