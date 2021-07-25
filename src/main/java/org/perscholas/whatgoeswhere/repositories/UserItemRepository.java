package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.models.UserItem;

public class UserItemRepository {
		
	public UserItem findByItemId(int itemId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<UserItem> query = entityManager.createNamedQuery("findByItemId", UserItem.class);
		query.setParameter("id", itemId);
		UserItem userItem= query.getResultList().get(0);
		
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
	public boolean deleteUserItem(UserItem item) {
		// if the item doesn't exist in the database, deletion fails.
		if (findByItemId(item.getItemId()) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		UserItem itemToDelete = entityManager.find(UserItem.class, item.getItemId());
		entityManager.remove(itemToDelete);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
}
