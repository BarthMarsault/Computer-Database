package com.excilys.cdb.persistence;


import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private SessionFactory sessionFactory;
	
	


	public ComputerDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	public enum SortingRule { ASC, DESC , NONE };

	/**
	 * Fonction de création d'un nouvel ordinateur en base de donnée.
	 * @param c Computer
	 * @return int
	 */
	public int create(Computer c) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(c);
		
		session.getTransaction().commit();
		
		return 1;
		
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
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaDelete<Computer> crComputer = cb.createCriteriaDelete(Computer.class);
		Root<Computer> rootComputer = crComputer.from(Computer.class);
		crComputer.where(cb.equal(rootComputer.get("id"), id));
		session.createQuery(crComputer).executeUpdate();
		
		transaction.commit();
		
		return 1;
	}
	
	/**
	 * Modification d'un computer en base de donnée
	 * @param computer
	 * @return
	 */
	public int update(Computer computer) {
		Session session = sessionFactory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = cb.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);
		criteriaUpdate.set("name", computer.getName())
					.set("introduced", computer.getIntroduced())
					.set("discontinued", computer.getDiscontinued())
					.set("company", computer.getCompany())
					.where(cb.equal(root.get("id"), computer.getId()));
		
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.createQuery(criteriaUpdate).executeUpdate();
		transaction.commit();
		
		
		
		return 1;
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
		param = "%" + param + "%";
		
		Session session = sessionFactory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		
		CriteriaQuery<Computer> cr = cb.createQuery(Computer.class);
		Root<Computer> rootComputer = cr.from(Computer.class);
		rootComputer.fetch("company",JoinType.LEFT);
		
		
		if(attribute != null && sr != SortingRule.NONE && !attribute.equals(ComputerAttribute.COMPANY)) {
			if(sr.equals(SortingRule.ASC)) {
				cr.orderBy(cb.asc(rootComputer.get(attribute.getAttribute())));
			}else {
				cr.orderBy(cb.desc(rootComputer.get(attribute.getAttribute())));
			}
		}else if(attribute != null && attribute.equals(ComputerAttribute.COMPANY) && sr != SortingRule.NONE) {
			if(sr.equals(SortingRule.ASC)) {
				cr.orderBy(cb.asc(rootComputer.get("company").get(attribute.getAttribute())));
			}else {
				cr.orderBy(cb.desc(rootComputer.get("company").get(attribute.getAttribute())));
			}
		}
		
		Predicate preName = cb.like(rootComputer.<String>get("name"), param);
		Predicate preCompany = cb.like(rootComputer.<Company>get("company").<String>get("name"), param);
		Predicate preForAll = cb.or(preName, preCompany);
		
		
		cr.select(rootComputer).where(preForAll).distinct(true);
		
		Query query;
		
		if(limit >= 0 && offset >= 0) {
			query = session.createQuery(cr).setFirstResult(offset).setMaxResults(limit);
		}
		else if(limit >= 0) {
			query = session.createQuery(cr).setMaxResults(limit);
		}
		else {
			query = session.createQuery(cr);
		}

		List<Computer> results = query.getResultList();

		return results;

	}
	
	
	/**
	 * Retourne l'ordinateur portant l'id précisée en paramètre d'appel de la fonction.
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id) {		
		Session session = sessionFactory.openSession();
		EntityManager entityManager = sessionFactory.createEntityManager();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Computer> cr = cb.createQuery(Computer.class);
		
		Root<Computer> root = cr.from(Computer.class);
		
		cr.select(root).where(cb.equal(root.get("id"), id));
		return Optional.ofNullable(entityManager.createQuery(cr).getSingleResult());
	}
	
	
	

	
	
	/**
	 * Retourne le nombre de computer présent dans la table 
	 * @return int
	 */
	public int getCount() {
		return getCount("");		
	}
	
	public int getCount(String param) {			
		param = "%"+param+"%";
		
		Session session = sessionFactory.openSession();
		EntityManager entityManager = sessionFactory.createEntityManager();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		
		Root<Computer> root = cr.from(Computer.class);
		
		cr.select(cb.count(root)).where(cb.like(root.<String>get("name"), param));
		
		return Integer.parseInt(entityManager.createQuery(cr).getSingleResult().toString());
	}


	


	
}
