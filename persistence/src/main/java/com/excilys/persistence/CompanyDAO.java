package com.excilys.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.Company;
import com.excilys.core.Computer;

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

	
	
	private SessionFactory sessionFactory;
	
	
	
	public CompanyDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	
	public boolean create(Company c) {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.save(c);
			
			session.getTransaction().commit();
		} catch(Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		
		
		return true;
		
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
		Session session = sessionFactory.openSession();
		EntityManager entityManager = sessionFactory.createEntityManager();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Company> cr = cb.createQuery(Company.class);
		
		Root<Company> root = cr.from(Company.class);
		
		cr.select(root).where(cb.equal(root.get("id"), id));
		return Optional.ofNullable(entityManager.createQuery(cr).getSingleResult());
	}
	
	
	@Transactional
	public boolean delete(int id) {
		
		if(id < 1) {
			logger.trace("Tentative de suppression d'une company sans id");
			return false;
		}

		
		Company companyTmp = new Company();
		companyTmp.setId(id);
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		
		try {
			
			
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaDelete<Computer> crComputer = cb.createCriteriaDelete(Computer.class);
			Root<Computer> rootComputer = crComputer.from(Computer.class);
			crComputer.where(cb.equal(rootComputer.get("company"), companyTmp));
			session.createQuery(crComputer).executeUpdate();
			
			CriteriaDelete<Company> crCompany = cb.createCriteriaDelete(Company.class);
			Root<Company> rootCompany = crCompany.from(Company.class);
			crCompany.where(cb.equal(rootCompany.get("id"), id));
			session.createQuery(crCompany).executeUpdate();
			
			transaction.commit();
		} catch(Exception e) {
			logger.error(e.getMessage());
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		
		return true;
		
	}

}
