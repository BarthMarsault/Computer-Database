package main.java.persistence;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
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
	
	private static ComputerDAO computerDAO = null;
	ComputerMapper mapper;
	
	private ComputerDAO() {
		mapper = ComputerMapper.getInstance();
	}
	
	public static ComputerDAO getInstance() {
		if(computerDAO == null) {
			computerDAO = new ComputerDAO();
		}
		return computerDAO;
	}
	
	
	/**
	 * Fonction de création d'un nouvel ordinateur en base de donnée.
	 * @param c Computer
	 * @return Boolean - true si l'orinateur est créer, false sinon.
	 */
	public boolean createComputer(Computer c) {
		if(c.getId() > 0) {
			if(c.alreadyExistInDB()) return false;
		}
		
		try {
			Connection conn =  DB.getInstance().getConnection();
			//Recupération des attributs
			String name = c.getName();
			LocalDate intr = c.getIntroduced();
			LocalDate disc = c.getDiscontinued();
			int idCompany = c.getCompany().getId();
			
			
			String req = "INSERT INTO " + tableName +
						" (name, introduced, discontinued, company_id)" +
						" VALUE (?,?,?,? )" ;
			
			PreparedStatement ps = conn.prepareStatement(req);
			
			ps.setString(1, c.getName());	
			
			if(intr != null)
				ps.setDate(2, Date.valueOf(intr));
			else
				ps.setDate(2, null);
			
			if(disc != null)
				ps.setDate(3, Date.valueOf(disc));
			else
				ps.setDate(3, null);
			
			
			if(idCompany > 0)
				ps.setInt(4, idCompany);
			else
				ps.setNull(4, java.sql.Types.INTEGER);
			
			
			ps.executeUpdate();
		
			ps.close();
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
	public boolean deleteComputer(Computer computer) {
		return deleteComputer(computer.getId());
	}
	
	/**
	 * Suppression d'un computer en bdd
	 * @param id
	 * @return
	 */
	public boolean deleteComputer(int id) {
		if(id < 1) return false;
		
		try {
			Connection conn = DB.getInstance().getConnection();
			
			
			//Préparation de la requête
			String req = "DELETE FROM " + tableName +
						" WHERE  id = ?";
			
			PreparedStatement ps = conn.prepareStatement(req);
			
			ps.setInt(1, id);
			
			//Execution
			ps.executeUpdate();
			
			ps.close();
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
	public boolean updateComputer(Computer computer) {
		
		try {
			Connection conn = DB.getInstance().getConnection();
			
			//Recupération des attributs
			String name = computer.getName();
			LocalDate intr = computer.getIntroduced();
			LocalDate disc = computer.getDiscontinued();
			int idCompany = computer.getCompany().getId();
			
			//Création de la requête
			String req = "UPDATE " + tableName + 
						" SET name = ?, introduced = ?, discontinued = ?, company_id = ?" + 
						" WHERE id = ?";

			PreparedStatement ps = conn.prepareStatement(req);
			
			ps.setString(1, name);	
			
			if(intr != null) {
				ps.setDate(2, Date.valueOf(intr));
			}
			else {
				ps.setDate(2, null);
			}
			
			if(disc != null) {
				ps.setDate(3, Date.valueOf(disc));
			}
			else {
				ps.setDate(3, null);
			}
			
			
			if(idCompany > 0) {
				ps.setInt(4, idCompany);
			}				
			else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
				
			
			ps.setInt(5, computer.getId());
			
			//Execution
			ps.executeUpdate();
			
			ps.close();
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
	public List<Computer> getComputers(){
		return getComputerWithLimit(-1,-1);
	}
	
	
	
	public List<Computer> getComputerWithLimit(int limit, int offset){
		List<Computer> computers = new ArrayList<>();
		ResultSet rs;
				
		try {
			Connection conn = DB.getInstance().getConnection();
			
			String req = "SELECT C.id, C.name, C.introduced, C.discontinued, Y.id, Y.name" +
					 	" FROM " + tableName + " C" +
						" LEFT JOIN company Y" +
						" ON C.company_id = Y.id";
			
			if(limit >= 0) { req += " LIMIT ?";}
			if(limit >= 0 && offset >= 0) { req += " OFFSET ?";}
			

			PreparedStatement ps = conn.prepareStatement(req);
			
			if(limit >= 0) { ps.setInt(1, limit);}
			if(limit >= 0 && offset >= 0) { ps.setInt(2, offset);}
			
			rs = ps.executeQuery();
		
			
			computers = mapper.resultSetToListComputer(rs);
			
			rs.close();
			ps.close();
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
	public Optional<Computer> findComputerById(int id) {
		Optional<Computer>computer = Optional.empty();
		try {
			
			Connection conn = DB.getInstance().getConnection();
			
			String req = "SELECT C.id, C.name, C.introduced, C.discontinued, Y.id, Y.name" +
						" FROM " + tableName + " C" +
						" LEFT JOIN company Y" +
						" ON C.company_id = Y.id" +
						" WHERE C.id = ?";
			
			
			
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				computer = mapper.resultSetToComputer(rs);
			}
			
			
			
			
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computer;
		
	}
}
