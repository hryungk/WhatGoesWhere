package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.models.UserItemID;

public class UserItemRepository {
	
	public UserItemRepository() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	public UserItem findByItemId(int itemId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("findByItemId", UserItem.class);
		query.setParameter("id", itemId);
		List<UserItem> userItems = query.getResultList();
		UserItem userItem = null;
		if (userItems.size() != 0) {
			userItem= query.getResultList().get(0);
		}
		
		entityManager.close();
		emfactory.close();
		return userItem;
	}
	
	public List<UserItem> findItemsByUser(String userId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("findItemsByUser", UserItem.class);
		query.setParameter("id", userId);		
		List<UserItem> userItems = query.getResultList();
		
		entityManager.close();
		emfactory.close();
		return userItems;
	}
	
	public boolean addUserItem(String userId, int itemId) {
		// If there already exists the same user in the system, addition fails.
		if (findByItemId(itemId) == null) {		
			UserItem ui = new UserItem(userId, itemId);
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(ui);			
			
			entityManager.getTransaction().commit();
//			entityManager.refresh(user);
			
			entityManager.close();
			emfactory.close();
			return true;
		} 
		return false;
	}
	
	public boolean deleteUserItem(UserItem item) {
		// if the item doesn't exist in the database, deletion fails.
		if (findByItemId(item.getItemId()) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		UserItemID pk = new UserItemID(item.getUserId(),item.getItemId());
		UserItem itemToDelete = entityManager.find(UserItem.class, pk);
		entityManager.remove(itemToDelete);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
	
	public boolean deleteUserItemByItemId(int itemId) {
		// if the item doesn't exist in the database, deletion fails.
		if (findByItemId(itemId) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("findByItemId", UserItem.class);
		query.setParameter("id", itemId);		
		UserItem itemToDelete = query.getResultList().get(0);
//		UserItem itemToDelete = findByItemId(itemId);
		entityManager.remove(itemToDelete);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
	public boolean deleteUserItemByUserId(String userId) {
		// if the item doesn't exist in the database, deletion fails.
		if (findItemsByUser(userId) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("findItemsByUser", UserItem.class);
		query.setParameter("id", userId);		
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
