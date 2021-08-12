package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	
	private static final String PERSIST_UNIT_NAME = "WhatGoesWhere";
	private UserItemRepository uiRepository;
	
	public UserRepository() {
		
		this.uiRepository = new UserItemRepository();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<User> getAllUsers() {
		return findUsers("User.findAll");
	}

	public User findUserById(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		User user = entityManager.find(User.class, id);
		
		entityManager.close();
		emfactory.close();
		return user;
	}
	
	public User findUserByEmail(String email) {	
		List<User> users = findUsers("User.findByEmail",email);	
		if (users.isEmpty())
			return null;
		return users.get(0); 
	}
	
	private List<User> findUsers(String queryName, String ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<User> query = entityManager.createNamedQuery(queryName, User.class);
		for (int i = 1; i <= columns.length; i++) {
			query.setParameter(i, columns[i-1]);
		}
		List<User> users = query.getResultList();
		
		entityManager.close();
		emfactory.close();
		return users;
	}
	
	public User add(User user) {
		// If there already exists the same user in the system, addition fails.
		if (findUserByEmail(user.getEmail()) == null) {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(user);			
			
			entityManager.getTransaction().commit();
//			entityManager.refresh(user);
			
			entityManager.close();
			emfactory.close();
			return findUserByEmail(user.getEmail());
		} 
		return null;
	}

	public boolean delete(User user) {
		// if the user doesn't exist in the database, deletion fails.
		if (findUserById(user.getId()) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		// Delete the join table entities first
		uiRepository.deleteByUserId(user.getId());
		User userToDelete = entityManager.find(User.class, user.getId());
		entityManager.remove(userToDelete);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return findUserById(user.getId()) == null;
	}
	
	public User update(User user) {
		// if the user doesn't exist in the database, update fails.
		if (findUserById(user.getId()) == null) {
			return null;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		User userToUpdate = entityManager.find(User.class, user.getId());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setItems(user.getItems());
		
		entityManager.getTransaction().commit();
//		entityManager.refresh(user);
		entityManager.close();
		emfactory.close();
		return findUserById(user.getId());
	}
	
}
