package main.java.persistence;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import main.java.mapper.ComputerMapper;
import main.java.model.*;

/**
 * ComputerDAO est une couche de persistance permettant la liaison entre la classe Computer et la base de données.
 * @author excilys
 *
 */
public class ComputerDAO {
	
	static String tableName = "computer";
	
	/**
	 * Fonction de création d'un nouvel ordinateur en base de donnée.
	 * @param c Computer
	 * @return Boolean - true si l'orinateur est créer, false sinon.
	 */
	public static boolean createComputer(Computer c) {
		if(c.getId() > 0) {
			if(c.alreadyExistInDB()) return false;
		}
		
		try {
			Connection conn = new DB().getConnection();
			Statement stmt = conn.createStatement();
			
			//Recupération des attributs
			String name = c.getName();
			Date intr = c.getIntroduced();
			Date disc = c.getDiscontinued();
			int idCompany = c.getCompany_id();
			
			//Création de la requête
			String req = "INSERT INTO " + tableName +
						" (name, introduced, discontinued, company_id)" +
						" VALUE (" ;
			
			req +=  name != null ? "'" + name + "'" : "," + "null";
			req +=  intr != null ? ",'" +  intr + "'" : "," +  "null";
			req += disc != null ?  ",'" + disc + "'" : "," + "null";
			req += idCompany > 0 ?  "," + idCompany : "," + "null";
			
			req += ");";
			
			//Execution
			stmt.executeUpdate(req);
			
			stmt.close();
			conn.close();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Suppression d'un computer en bdd.
	 * @param computer
	 * @return
	 */
	public static boolean deleteComputer(Computer computer) {
		return deleteComputer(computer.getId());
	}
	
	/**
	 * Suppression d'un computer en bdd
	 * @param id
	 * @return
	 */
	public static boolean deleteComputer(int id) {
		if(id < 1) return false;
		
		try {
			Connection conn = new DB().getConnection();
			Statement stmt = conn.createStatement();
			
			//Préparation de la requête
			String req = "DELETE FROM " + tableName +
						" WHERE  id = " + id;
			

			//Execution
			stmt.executeUpdate(req);
			
			stmt.close();
			conn.close();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Modification d'un computer en base de donnée
	 * @param computer
	 * @return
	 */
	public static boolean updateComputer(Computer computer) {
		
		try {
			Connection conn = new DB().getConnection();
			Statement stmt = conn.createStatement();
			
			//Recupération des attributs
			String name = computer.getName();
			Date intr = computer.getIntroduced();
			Date disc = computer.getDiscontinued();
			
			//Création de la requête
			String req = "UPDATE " + tableName + 
						" SET id = " + computer.getId();
			req += name != null ? ", name = '" + computer.getName() + "'" : ", name = null";
			req += intr != null ? ", introduced = '" + computer.getIntroduced() + "'" : ", introduced = null";
			req += disc != null ? ", discontinued = '" + computer.getDiscontinued() + "'" : ", discontinued = null";			
			req += ", company_id = " + computer.getCompany_id();
			req += " WHERE id = " + computer.getId();
			
			
			//Execution
			stmt.executeUpdate(req);
			
			stmt.close();
			conn.close();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Retourne la liste de tous les ordinateurs.
	 * @return
	 */
	public static List<Computer> getComputers(){
		List<Computer> computers = new ArrayList<>();
		ResultSet rs;
				
		try {
			Connection conn = new DB().getConnection();
			
			String req = "SELECT * FROM " + tableName;
			
			Statement stmt = conn.createStatement();
			
			
			rs = stmt.executeQuery(req);
			
		
			while(rs.next()) {
				computers.add(ComputerMapper.resultSetToComputer(rs));
			}
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return computers;
	}
	
	
	/**
	 * Retourne l'ordinateur portant l'id précisée en paramètre d'appel de la fonction.
	 * @param id
	 * @return
	 */
	public static Computer findComputerById(int id) {
		
		try {
			Computer computer = null;
			Connection conn = new DB().getConnection();
			
			String req = "SELECT * FROM " + tableName +
						" WHERE id = "+id;
			
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(req);
			
			while(rs.next()) {
				computer = ComputerMapper.resultSetToComputer(rs);
			}
			
			stmt.close();
			conn.close();
			return computer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
}
