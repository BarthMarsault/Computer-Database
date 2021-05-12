package main.java.ui;

import java.util.Scanner;

public class UiUtils {
	
	public static int askId() {
		int id = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Id de l'ordinateur pour lequel vous souhaité des détails :");
		
		try {
			id = sc.nextInt();
		}catch(Exception e) {
			System.out.println("Votre entrée n'est pas une id valide");
		}
		return id;
	}
	
	public static boolean askYesNo(String msg) {
		Scanner sc = new Scanner(System.in);
		System.out.print(msg + " ");
		String entry = "";
		entry = sc.nextLine();
		entry = entry.trim();
		while(!entry.equals("y") && !entry.equals("n")) {
			System.out.println("Entrée invalide - " + msg);
			entry = sc.nextLine();
		}
		if(entry.equals("y"))
			return true;
		else
			return false;
		
	}

}
