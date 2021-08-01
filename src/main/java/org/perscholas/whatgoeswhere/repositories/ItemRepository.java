package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.models.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
	
	private UserItemRepository uiRepository;
	
	public ItemRepository() {
		
		this.uiRepository = new UserItemRepository();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Item> getAllItems() {
		List<Item> items = findItems("Item.findAll");
		return items;
	}
	
	public List<Item> findItemByName(String name) {
		List<Item> items = findItems("Item.findByName", name);
		return items;
	}

	public Item findItemByNameAndState(String name, String state) {	
		List<Item> items = findItems("Item.findByNameAndState",name,state);	
		if (items.size() == 0)
			return null;
		return items.get(0); 
	}
	
	private List<Item> findItems(String queryName, String ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<Item> query = entityManager.createNamedQuery(queryName, Item.class);
		for (int i = 1; i <= columns.length; i++) {
			query.setParameter(i, columns[i-1]);
		}
		List<Item> items = query.getResultList();
		
		entityManager.close();
		emfactory.close();
		return items;
	}
	
	public Item findItemById(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		
		Item item = entityManager.find(Item.class, id);
		
		entityManager.close();
		emfactory.close();
		return item;
	}
	
	public boolean addItem(Item item, String userId) {
		// If there already exists the same item in the system, addition fails.
		if (findItemByNameAndState(item.getName(),item.getCondition()) == null) {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(item);			
			
			entityManager.getTransaction().commit();
			entityManager.refresh(item);
			
			if (userId != null) {
				uiRepository.addUserItem(userId, item.getId());
			}
			
			entityManager.close();
			emfactory.close();
			return true;
		} 
		return false;
	}

	public boolean deleteItem(int itemId) {
		// if the item doesn't exist in the database, deletion fails.
		if (findItemById(itemId) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		// Delete the join table entities first
		uiRepository.deleteUserItemByItemId(itemId);
		
		Item itemToDelete = entityManager.find(Item.class, itemId);
		entityManager.remove(itemToDelete);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}

	public boolean updateItem(Item item) {
		// if the item doesn't exist in the database, update fails.
		if (findItemById(item.getId()) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Item itemToUpdate = entityManager.find(Item.class, item.getId());
		itemToUpdate.setName(item.getName());
		itemToUpdate.setCondition(item.getCondition());
		itemToUpdate.setBestOption(item.getBestOption());
		itemToUpdate.setSpecialInstruction(item.getSpecialInstruction());
		itemToUpdate.setNotes(item.getNotes());		
			
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
}
