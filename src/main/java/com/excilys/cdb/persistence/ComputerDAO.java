package com.excilys.cdb.persistence;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.*;

/**
 * ComputerDAO est une couche de persistance permettant la liaison entre la classe Computer et la base de données.
 * @author excilys
 *
 */
public class ComputerDAO {
	
	private DB db = null;
	static String tableName = "computer";
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private static ComputerDAO computerDAO = null;
	ComputerMapper mapper;
	public enum SortingRule { ASC, DESC };
	
	private final String sqlGetCountComputer = "SELECT COUNT(*) FROM " + tableName;
	private final String sqlGetCountComputerWithParam = "SELECT COUNT(*)" + 
			" FROM " + tableName + " C" + 
			" LEFT JOIN company Y" +
			" ON C.company_id = Y.id" +
			" WHERE C.name LIKE ?" +
			" OR C.introduced LIKE ?" +
			" OR C.discontinued LIKE ?" +
			" OR Y.name LIKE ?";
	
	private final String sqlFindComputerByIdWithLimit = "SELECT C.id, C.name, C.introduced, C.discontinued, Y.id, Y.name" +
			" FROM " + tableName + " C" +
			" LEFT JOIN company Y" +
			" ON C.company_id = Y.id" +
			" WHERE C.id = ?";
	
	
	private final String sqlFindWithParam= "SELECT C.id, C.name, C.introduced, C.discontinued, Y.id, Y.name" +
		 	" FROM " + tableName + " C" +
			" LEFT JOIN company Y" +
			" ON C.company_id = Y.id" +
			" WHERE C.name LIKE ?" +
			" OR C.introduced LIKE ?" +
			" OR C.discontinued LIKE ?" +
			" OR Y.name LIKE ?";
	
	private final String sqlUpdateComputer = "UPDATE " + tableName + 
			" SET name = ?, introduced = ?, discontinued = ?, company_id = ?" + 
			" WHERE id = ?";
	
	private final String sqlDeleteComputer = "DELETE FROM " + tableName +
			" WHERE  id = ?";
	
	private final String sqlCreateComputer = "INSERT INTO " + tableName +
			" (name, introduced, discontinued, company_id)" +
			" VALUE (?,?,?,? )" ;
	
	private ComputerDAO() {
		db = DB.getInstance();
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
	public boolean create(Computer c) {
		if(c.getId() > 0) {
			if(c.alreadyExistInDB()) return false;
		}
		
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlCreateComputer)){
			
			//Recupération des attributs
			String name = c.getName();
			LocalDate intr = c.getIntroduced();
			LocalDate disc = c.getDiscontinued();
			int idCompany = c.getCompany() != null ? c.getCompany().getId() : 0;
			

			
			ps.setString(1, c.getName());	
			
			ps.setDate(2, intr != null ? Date.valueOf(intr) : null);
			ps.setDate(3, disc != null ? Date.valueOf(disc) : null);			
			
			
			if(idCompany > 0) {
				ps.setInt(4, idCompany);
			} else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
				
			
			
			ps.executeUpdate();
		
			return true;
			
		}catch(SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();		
		}
		
		return false;
	}
	
	
	
	/**
	 * Suppression d'un computer en bdd.
	 * @param computer
	 * @return
	 */
	public boolean delete(Computer computer) {
		return delete(computer.getId());
	}
	
	/**
	 * Suppression d'un computer en bdd
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		if(id < 1) {
			//logger.trace("Tentative de suppression d'un ordinateur sans id");
			return false;
		}
		
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlDeleteComputer)){
			
			ps.setInt(1, id);
			
			//Execution
			ps.executeUpdate();

			return true;
		}catch(SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Modification d'un computer en base de donnée
	 * @param computer
	 * @return
	 */
	public boolean update(Computer computer) {
		
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlUpdateComputer)){
			
			//Recupération des attributs
			String name = computer.getName();
			LocalDate intr = computer.getIntroduced();
			LocalDate disc = computer.getDiscontinued();
			
			

			
			ps.setString(1, name);	
			
			ps.setDate(2, intr != null ? Date.valueOf(intr) : null);
			ps.setDate(3, disc != null ? Date.valueOf(disc) : null);
			
			if(computer.getCompany() != null) {
				int idCompany = computer.getCompany().getId();
				ps.setInt(4, idCompany);
			}				
			else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
				
			
			ps.setInt(5, computer.getId());
			//Execution
			ps.executeUpdate();

			return true;
		}catch(SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Retourne la liste de tous les ordinateurs.
	 * @return
	 */
	public List<Computer> getAll(){
		return getWithLimit(-1,-1);
	}
	
	
	
	public List<Computer> getWithLimit(int limit, int offset){	
		return FindWithParamOrderedWithLimit("",null,null,-1,-1);
	}
	
	
	public List<Computer> FindWithParamOrderedWithLimit(String param,ComputerAttribute attribute, SortingRule sr, int limit, int offset){
		List<Computer> computers = new ArrayList<>();
		ResultSet rs;
		String req = sqlFindWithParam;
		
		//Ajout du tri - SI NECESSAIRE !
		if(attribute != null && sr != null) {
			req += " ORDER BY " + attribute.getAttribute();
			if(sr == SortingRule.ASC || sr == null) {
				req += " ASC";
			}else {
				req += " DESC";
			}
		}
		
		//Ajout des LIMIT et OFFSET - SI NECESSAIRE !
		if(limit >= 0) { req += " LIMIT ?";}
		if(limit >= 0 && offset >= 0) { req += " OFFSET ?";}
				
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(req)){

			ps.setString(1, "%" + param + "%");
			ps.setString(2, "%" + param + "%");
			ps.setString(3, "%" + param + "%");
			ps.setString(4, "%" + param + "%");
			
			if(limit >= 0) { ps.setInt(5, limit);}
			if(limit >= 0 && offset >= 0) { ps.setInt(6, offset);}
			
			rs = ps.executeQuery();
		
			
			computers = mapper.resultSetToListComputer(rs);
			
			rs.close();			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		
		if(computers.size() == 0) {
			logger.trace("Retour d'une liste de Computer vide");
		}
		
		
		return computers;
	}
	
	
	/**
	 * Retourne l'ordinateur portant l'id précisée en paramètre d'appel de la fonction.
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id) {
		Optional<Computer>computer = Optional.empty();
		ResultSet rs;
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlFindComputerByIdWithLimit);){
			
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				computer = mapper.resultSetToComputer(rs);
			}
			
			rs.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		
		if(!computer.isPresent()) {
			logger.trace("Retour d'un Optional<Computer> vide");
		}
		return computer;
		
	}
	
	public List<Computer> findWithParam(String param){
		return findWithParamWithLimit(param,-1,-1);
	}
	
	
	public List<Computer> findWithParamWithLimit(String param, int limit, int offset){
		List<Computer> computers = new ArrayList<>();
		ResultSet rs;
		String req = sqlFindWithParam;
		//Ajout des LIMIT et OFFSET - SI NECESSAIRE !
		if(limit >= 0) { req += " LIMIT ?";}
		if(limit >= 0 && offset >= 0) { req += " OFFSET ?";}
				
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(req)){
			ps.setString(1, "%" + param + "%");
			ps.setString(2, "%" + param + "%");
			ps.setString(3, "%" + param + "%");
			ps.setString(4, "%" + param + "%");
			
			if(limit >= 0) { ps.setInt(5, limit);}
			if(limit >= 0 && offset >= 0) { ps.setInt(6, offset);}
			rs = ps.executeQuery();
		
			
			computers = mapper.resultSetToListComputer(rs);
			
			rs.close();			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		
		if(computers.size() == 0) {
			logger.trace("Retour d'une liste de Computer vide");
		}
		
		
		return computers;
	}
	

	
	
	/**
	 * Retourne le nombre de computer présent dans la table 
	 * @return int
	 */
	public int getCount() {
		int nbComputer = 0;
		
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlGetCountComputer);
				ResultSet rs = ps.executeQuery();){
			if(rs.next()) {
				nbComputer = rs.getInt(1);
			}
			
		}catch(SQLException e) {
			logger.error(e.getMessage());
		}
		return nbComputer;
	}
	
	public int getCount(String param) {
		int nbComputer = 0;
		
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlGetCountComputerWithParam)){
			
			ps.setString(1, "%" + param + "%");
			ps.setString(2, "%" + param + "%");
			ps.setString(3, "%" + param + "%");
			ps.setString(4, "%" + param + "%");
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				nbComputer = rs.getInt(1);
			}
			
		}catch(SQLException e) {
			logger.error(e.getMessage());
		}
		
		return nbComputer;
	}
	
}
