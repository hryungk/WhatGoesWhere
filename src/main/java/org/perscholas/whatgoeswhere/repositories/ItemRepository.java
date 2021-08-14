package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.exceptions.ItemAlreadyExistsException;
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
	
	public Item add(Item item, int userId) throws ItemAlreadyExistsException {
		// If there already exists the same item in the system, addition fails.
		if (findItemByNameAndState(item.getName(),item.getCondition()) != null) {
			throw new ItemAlreadyExistsException(item.getName(),item.getCondition());
		} else {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(item);			
			
			entityManager.getTransaction().commit();
			
			if (userId != 0) {
				uiRepository.add(userId, item.getId());
			}
			
			entityManager.close();
			emfactory.close();
			return findItemById(item.getId());
		} 
//		return null;
	}
	public boolean delete(int itemId) {
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

	public Item update(Item item) throws ItemAlreadyExistsException {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Item itemById = entityManager.find(Item.class, item.getId());
		Item itemByNS = findItemByNameAndState(item.getName(), item.getCondition());
		// If user changed neither name nor condition OR user changed it but an matching item doesn't exist in the db
		if (itemById.equals(itemByNS) || !itemById.equals(itemByNS) && itemByNS==null) {
			itemById.setName(item.getName());
			itemById.setCondition(item.getCondition());
			itemById.setBestOption(item.getBestOption());
			itemById.setSpecialInstruction(item.getSpecialInstruction());
			itemById.setNotes(item.getNotes());
			
			entityManager.getTransaction().commit();
			entityManager.close();
			emfactory.close();
			return findItemById(item.getId());
		} else { // user changed either name or condition or both such that there is a duplicate
			throw new ItemAlreadyExistsException(item.getName(),item.getCondition());		
		}		
	}
}
