package com.excilys.cdb.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * CompanyDAO est une couche de persistance permettant la liaison entre la classe Company et la base de données.
 * @author excilys
 *
 */
@Repository
@Scope
public class CompanyDAO {

	static String tableName = "company";
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);


	private CompanyMapper mapper;
	
	private JdbcTemplate jdbcTemplate;
	
	private final String sqlFindCompanyById = "SELECT * FROM " + tableName + " WHERE id = ?";
	
	private final String sqlDeleteCompany = "DELETE FROM " + tableName + " WHERE  id = ?";
	
	private final String sqlDeleteComputer = "DELETE FROM computer WHERE company_id = ?";
	
	
	private SessionFactory sessionFactory;
	
	
	
	public CompanyDAO(CompanyMapper mapper, JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
		super();
		this.mapper = mapper;
		this.jdbcTemplate = jdbcTemplate;
		this.sessionFactory = sessionFactory;
	}
	
	
	


	/**
	 * Retourne la liste de toutes les "Company" présente en base de données.
	 * @return
	 */

	public List<Company> getAll(){
		//return getWithLimit(-1,-1);
		Session session = sessionFactory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Company> cr = cb.createQuery(Company.class);
		Root<Company> root = cr.from(Company.class);
		cr.select(root);
		
		Query<Company> query = session.createQuery(cr);
		List<Company> results = query.getResultList();
		
		return results;
	}

	
	
	/**
	 * Retourne la Company en fonction de l'id
	 * @param id
	 * @return Company
	 */
	public Optional<Company> findById(int id) {
		return Optional.ofNullable(jdbcTemplate.queryForObject(sqlFindCompanyById, new Object[] { id }, mapper));
	}
	
	
	@Transactional
	public boolean delete(int id) {
		
		if(id < 1) {
			logger.trace("Tentative de suppression d'une company sans id");
			return false;
		}
		
//		jdbcTemplate.update(sqlDeleteComputer,id);
//		jdbcTemplate.update(sqlDeleteCompany,id);
//		return true;
		
		Session session = sessionFactory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaDelete<Computer> crComputer = cb.createCriteriaDelete(Computer.class);
		Root<Computer> rootComputer = crComputer.from(Computer.class);
		crComputer.where(cb.equal(rootComputer.get("company_id"), id));
		session.createQuery(crComputer);
		
		CriteriaDelete<Company> crCompany = cb.createCriteriaDelete(Company.class);
		Root<Company> rootCompany = crCompany.from(Company.class);
		crCompany.where(cb.equal(rootCompany.get("id"), id));
		session.createQuery(crCompany).executeUpdate();
		
		return true;
		
	}

}
