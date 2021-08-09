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
	
	private static final String PERSIST_UNIT_NAME = "WhatGoesWhere";
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
		return findItems("findAll");
	}
	
	public List<Item> findItemByName(String name) {
		return findItems("findByName", name);
	}

	public Item findItemByNameAndState(String name, String state) {	
		List<Item> items = findItems("findByNameAndState",name,state);	
		if (items.isEmpty())
			return null;
		return items.get(0); 
	}
	
	private List<Item> findItems(String queryName, String ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<Item> query = entityManager.createNamedQuery("Item."+queryName, Item.class);
		for (int i = 1; i <= columns.length; i++) {
			query.setParameter(i, columns[i-1]);
		}
		List<Item> items = query.getResultList();
		
		entityManager.close();
		emfactory.close();
		return items;
	}
	
	public Item findItemById(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		Item item = entityManager.find(Item.class, id);
		
		entityManager.close();
		emfactory.close();
		return item;
	}
	
	public boolean addItem(Item item, int userId) {
		// If there already exists the same item in the system, addition fails.
		if (findItemByNameAndState(item.getName(),item.getCondition()) == null) {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(item);			
			
			entityManager.getTransaction().commit();
			entityManager.refresh(item);
			
			if (userId != 0) {
				uiRepository.add(userId, item.getId());
			}
			
			entityManager.close();
			emfactory.close();
			return true;
		} 
		return false;
	}
	public Item add(Item item, int userId) {
		// If there already exists the same item in the system, addition fails.
		if (findItemByNameAndState(item.getName(),item.getCondition()) == null) {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(item);			
			
			entityManager.getTransaction().commit();
//			entityManager.refresh(item);
			
			if (userId != 0) {
				uiRepository.add(userId, item.getId());
			}
			
			entityManager.close();
			emfactory.close();
			return item;
		} 
		return null;
	}
	public boolean deleteItem(int itemId) {
		// if the item doesn't exist in the database, deletion fails.
		if (findItemById(itemId) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		// Delete the join table entities first
		uiRepository.deleteByItemId(itemId);
		
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
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
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
	public Item update(Item item) {
		// if the item doesn't exist in the database, update fails.
		if (findItemById(item.getId()) == null) {
			return null;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
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
		return item;
	}
}
