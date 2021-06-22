package com.excilys.persistence;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.core.User;

@Repository
@Scope
public class UserDAO {
	
	private SessionFactory sessionFactory;
	
	
	public UserDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	public User findByUsername(String username) {
		Session session = sessionFactory.openSession();
		EntityManager entityManager = sessionFactory.createEntityManager();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		
		Root<User> root = cr.from(User.class);
		
		cr.select(root).where(cb.equal(root.get("username"), username));	
		return entityManager.createQuery(cr).getSingleResult();
	}
	
	
	
	
}
