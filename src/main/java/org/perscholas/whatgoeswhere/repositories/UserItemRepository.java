package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.models.UserItemID;
import org.springframework.stereotype.Repository;

@Repository
public class UserItemRepository {
	
	private static final String PERSIST_UNIT_NAME = "WhatGoesWhere";
	
	public UserItemRepository() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<UserItem> getAll() {
		return findUserItems("findAll");
	}
	
	public UserItem findByItemId(int itemId) {
		List<UserItem> uiList = findUserItems("findByItemId", itemId);
		if (uiList.isEmpty())
			return null;
		return uiList.get(0); 		
	}
	
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
