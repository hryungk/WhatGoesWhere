package com.hyunryungkim.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.hyunryungkim.whatgoeswhere.models.UserItem;
import com.hyunryungkim.whatgoeswhere.models.UserItemID;

/**
 * A DAO Repository class for UserItem model
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.UserItem
 *
 */
@Repository
public class UserItemRepository {
	/**
	 * A string of persistence-unit name for JPA to inject into entity manager factory
	 */
	private static final String PERSIST_UNIT_NAME = "WhatGoesWhere";
	
	/**
	 * Class constructor registering a database driver
	 */
	public UserItemRepository() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns all UserItem objects in the database
	 * 
	 * @return a list of UserItem objects in the database
	 */
	public List<UserItem> getAll() {
		return findUserItems("findAll");
	}
	
	/**
	 * Returns a UserItem associated with the given Item id
	 * 
	 * @param itemId an integer of Item's id
	 * @return a UserItem that has the given Item id, null if not found
	 */
	public UserItem findByItemId(int itemId) {
		List<UserItem> uiList = findUserItems("findByItemId", itemId);
		if (uiList.isEmpty())
			return null;
		return uiList.get(0); 		
	}
	
	/**
	 * Returns a UserItem associated with the given User id
	 * 
	 * @param userId an integer of User's id
	 * @return a list of UserItem objects that has the given User id, null if not found
	 */
	public List<UserItem> findByUserId(int userId) {
		return findUserItems("findByUserId",userId);
	}
	
	private List<UserItem> findUserItems(String queryName, int ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("UserItem."+queryName, UserItem.class);
		for (int i = 1; i <= columns.length; i++) {
			query.setParameter(i, columns[i-1]);
		}
		List<UserItem> users = query.getResultList();
		
		entityManager.close();
		emfactory.close();
		return users;
	}
	
	/**
	 * Adds a new UserItem and returns a boolean of whether the addition was successful
	 * Create method of CRUD
	 * 
	 * @param userId an integer of User's id
	 * @param itemId an integer of Item's id
	 * @return true if the addition was successful, false otherwise
	 */
	public boolean add(int userId, int itemId) {
		// If there already exists the same user in the system, addition fails.
		if (findByItemId(itemId) == null) {		
			UserItem ui = new UserItem(userId, itemId);
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(ui);			
			
			entityManager.getTransaction().commit();
			
			entityManager.close();
			emfactory.close();
			return true;
		} 
		return false;
	}
	
	/**
	 * Removes the given UserItem and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param userItem a UserItem to be removed from the database
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean delete(UserItem userItem) {
		// if the item doesn't exist in the database, deletion fails.
		if (findByItemId(userItem.getItemId()) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		UserItemID pk = new UserItemID(userItem.getUserId(),userItem.getItemId());
		UserItem itemToDelete = entityManager.find(UserItem.class, pk);
		entityManager.remove(itemToDelete);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
	/**
	 * Removes the UserItem with the given itemId and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param itemId an integer of Item's id
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean deleteByItemId(int itemId) {
		// if the item doesn't exist in the database, deletion fails.
		if (findByItemId(itemId) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("UserItem.findByItemId", UserItem.class);
		query.setParameter(1, itemId);		
		UserItem itemToDelete = query.getResultList().get(0);
		entityManager.remove(itemToDelete);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
	/**
	 * Removes all UserItem with the given userId and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param userId an integer of User's id
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean deleteByUserId(int userId) {
		// if the item doesn't exist in the database, deletion fails.
		if (findByUserId(userId) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("UserItem.findByUserId", UserItem.class);
		query.setParameter(1, userId);		
		List<UserItem> userItems = query.getResultList();
		for (UserItem itemToDelete : userItems) {
			entityManager.remove(itemToDelete);
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
}
