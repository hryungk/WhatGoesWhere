package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.models.User;
import org.springframework.stereotype.Repository;

/**
 * A DAO Repository class for User model
 * 
 * @author Hyunryung Kim
 * @see org.perscholas.whatgoeswhere.models.User
 *
 */
@Repository
public class UserRepository {
	/**
	 * A string of persistence-unit name for JPA to inject into entity manager factory
	 */
	private static final String PERSIST_UNIT_NAME = "WhatGoesWhere";
	/**
	 * UserItem repository object is necessary for delete method
	 */
	private UserItemRepository uiRepository;
	
	/**
	 * Class constructor registering a database driver and instantiating 
	 * the UserItem Repository object
	 */
	public UserRepository() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		this.uiRepository = new UserItemRepository();		
	}
	
	/**
	 * Returns all User objects in the database
	 *  
	 * @return a list of User objects in the database
	 */
	public List<User> getAll() {
		return findUsers("User.findAll");
	}
	
	/**
	 * Returns a User associated with the given email
	 * 
	 * @param email a string of User's email
	 * @return a User that has the given email, null if not found
	 */
	public User findByEmail(String email) {	
		List<User> users = findUsers("User.findByEmail",email);	
		if (users.isEmpty())
			return null;
		return users.get(0); 
	}
	
	/**
	 * Returns a list of User objects according to the queryName and variable columns
	 * Used by other find methods for querying named queries
	 * 
	 * @param queryName a string of named query defined in the model class
	 * @param columns an array of strings of column variables to go into the query
	 * @return a List of User objects returned by the query
	 */
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

	/**
	 * Returns a User associated with the given User's id
	 * 
	 * @param id an integer of User's id
	 * @return a User that has the given id, null if not found
	 */
	public User findById(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		User user = entityManager.find(User.class, id);
		
		entityManager.close();
		emfactory.close();
		return user;
	}
	
	/**
	 * Returns a newly added User object
	 * Create method of CRUD
	 * 
	 * @param user an Item to be added to the database
	 * @return a User object added to the database
	 */
	public User add(User user) {
		// If there already exists the same user in the system, addition fails.
		if (findByEmail(user.getEmail()) == null) {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(user);			
			
			entityManager.getTransaction().commit();
			
			entityManager.close();
			emfactory.close();
			return findByEmail(user.getEmail());
		} 
		return null;
	}

	/**
	 * Removes a User with the given user and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param user a User to be removed from the database
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean delete(User user) {
		// if the user doesn't exist in the database, deletion fails.
		if (findById(user.getId()) == null) {
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
		return findById(user.getId()) == null;
	}
	
	/**
	 * Updates the given User and returns the updated User
	 * Update method of CRUD
	 * 
	 * @param user a User to be updated from the database
	 * @return an updated User object from the database
	 */
	public User update(User user) {
		// if the user doesn't exist in the database, update fails.
		if (findById(user.getId()) == null) {
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
		return findById(user.getId());
	}
	
}
