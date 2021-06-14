package com.excilys.cdb.persistence;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.*;

/**
 * ComputerDAO est une couche de persistance permettant la liaison entre la classe Computer et la base de données.
 * @author excilys
 * @param <JdbcTemplate>
 *
 */
@Repository
@Scope
public class ComputerDAO {
	
	
	static String tableName = "computer";
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);


	private ComputerMapper mapper;
	
	private JdbcTemplate jdbcTemplate;
	
	
	
	
	public ComputerDAO(ComputerMapper mapper, JdbcTemplate jdbcTemplate) {
		super();
		this.mapper = mapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	

	public enum SortingRule { ASC, DESC , NONE };
	
	private final String sqlGetCountComputer = "SELECT COUNT(*) FROM " + tableName;
	private final String sqlGetCountComputerWithParam = "SELECT COUNT(*)" + 
			" FROM " + tableName + " C" + 
			" LEFT JOIN company Y" +
			" ON C.company_id = Y.id" +
			" WHERE C.name LIKE ?" +
			" OR C.introduced LIKE ?" +
			" OR C.discontinued LIKE ?" +
			" OR Y.name LIKE ?";
	
	private final String sqlFindComputerById = "SELECT C.id, C.name, C.introduced, C.discontinued, Y.id, Y.name" +
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
	

	
	
	/**
	 * Fonction de création d'un nouvel ordinateur en base de donnée.
	 * @param c Computer
	 * @return Boolean - true si l'orinateur est créer, false sinon.
	 */
	public int create(Computer c) {
		
		return jdbcTemplate.update(sqlCreateComputer,c.getName(),
				c.getIntroduced(), c.getDiscontinued(),
				c.getCompany() != null && c.getCompany().getId() > 0 ? c.getCompany().getId() : null);
		
		
	}
	
	
	
	/**
	 * Suppression d'un computer en bdd.
	 * @param computer
	 * @return
	 */
	public int delete(Computer computer) {
		return delete(computer.getId());
	}
	
	/**
	 * Suppression d'un computer en bdd
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		return jdbcTemplate.update(sqlDeleteComputer, id);
	}
	
	/**
	 * Modification d'un computer en base de donnée
	 * @param computer
	 * @return
	 */
	public int update(Computer computer) {
		return jdbcTemplate.update(sqlUpdateComputer, computer.getName(),
				computer.getIntroduced(), computer.getDiscontinued(),
				computer.getCompany() != null && computer.getCompany().getId() > 0 ? computer.getCompany().getId() : null,
				computer.getId());
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
		if(attribute != null && sr != SortingRule.NONE) {
			req += " ORDER BY " + attribute.getAttribute();
			if(sr == SortingRule.ASC || sr == null || sr == SortingRule.NONE) {
				req += " ASC";
			}else {
				req += " DESC";
			}
		}
		
		//Ajout des LIMIT et OFFSET - SI NECESSAIRE !
		if(limit >= 0) { req += " LIMIT ?";}
		if(limit >= 0 && offset >= 0) { req += " OFFSET ?";}
		
	
		
		param = "%" + param + "%";
		if(limit >= 0 && offset >= 0) {
			computers = jdbcTemplate.query(req, mapper, param, param, param, param, limit, offset);
		}
		else if(limit >= 0) {
			computers = jdbcTemplate.query(req, mapper, param, param, param, param, limit);
		}
		else {
			computers = jdbcTemplate.query(req, mapper, param, param, param, param);
		}
		
		
		if(computers.size() == 0) {
			logger.trace("Retour d'un liste de Computer vide");
		}
		
		return computers;
	}
	
	
	/**
	 * Retourne l'ordinateur portant l'id précisée en paramètre d'appel de la fonction.
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id) {		
		return Optional.ofNullable(jdbcTemplate.queryForObject(sqlFindComputerById, new Object[] { id }, mapper));
	}
	
	
	

	
	
	/**
	 * Retourne le nombre de computer présent dans la table 
	 * @return int
	 */
	public int getCount() {
		return jdbcTemplate.queryForObject(sqlGetCountComputer, Integer.class);
	}
	
	public int getCount(String param) {			
		param = "%"+param+"%";
		return jdbcTemplate.queryForObject(sqlGetCountComputerWithParam, Integer.class, param,param,param,param);
	}


	


	
}
