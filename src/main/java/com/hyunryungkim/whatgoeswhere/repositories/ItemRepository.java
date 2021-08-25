package com.hyunryungkim.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.hyunryungkim.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.models.Item;
import com.hyunryungkim.whatgoeswhere.models.ModelUtilities;

/**
 * A DAO Repository class for Item model
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.Item
 * 
 */
@Repository
public class ItemRepository {
	/**
	 * UserItem repository object is necessary for add and delete methods
	 */
	private UserItemRepository uiRepository;
	
	/**
	 * Class constructor registering a database driver and instantiating 
	 * the UserItem Repository object
	 */
	public ItemRepository() {			
		try {
			Class.forName(ModelUtilities.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		this.uiRepository = new UserItemRepository();
	}
	
	/**
	 * Returns all Item objects in the database
	 * 
	 * @return a list of Item objects in the database
	 */
	public List<Item> getAll() {
		return findItems(ModelUtilities.Item.NAME_FIND_ALL);
	}
	
	/**
	 * Returns an Item associated with the given name
	 * 
	 * @param name a string of Item's name
	 * @return an Item that has the given name, null if not found
	 */
	public List<Item> findByName(String name) {
		return findItems(ModelUtilities.Item.NAME_FINDBY_NAME, "%"+name+"%");
	}

	/**
	 * Returns an Item associated with the given name and state
	 * 
	 * @param name  a string of Item's name
	 * @param state a string of Item's condition
	 * @return an Item that has the given name and state, null if not found
	 */
	public Item findByNameAndState(String name, String state) {	
		List<Item> items = findItems(ModelUtilities.Item.NAME_FINDBY_NAME_STATE,name,state);	
		if (items.isEmpty())
			return null;
		return items.get(0); 
	}
	
	/**
	 * Returns a list of Item objects according to the queryName and variable columns
	 * Used by other find methods for querying named queries
	 * 
	 * @param queryName a string of named query defined in the model class
	 * @param columns an array of strings of column variables to go into the query
	 * @return a List of Item objects returned by the query
	 */
	private List<Item> findItems(String queryName, String ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
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
	
	/**
	 * Returns an Item associated with the given Item's id
	 * 
	 * @param id an integer of Item's id
	 * @return an Item that has the given id, null if not found
	 */
	public Item findById(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		Item item = entityManager.find(Item.class, id);
		
		entityManager.close();
		emfactory.close();
		return item;
	}
	
	/**
	 * Returns a newly added Item object
	 * Create method of CRUD
	 * 
	 * @param item an Item to be added to the database
	 * @param userId an integer of the associated User's id
	 * @return an Item object added to the database
	 * @throws ItemAlreadyExistsException If there already exists the same Item in the database
	 */
	public Item add(Item item, int userId) throws ItemAlreadyExistsException {
		// If there already exists the same item in the system, addition fails.
		if (findByNameAndState(item.getName(),item.getCondition()) != null) {
			throw new ItemAlreadyExistsException(item.getName(),item.getCondition());
		} else {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(item);			
			
			entityManager.getTransaction().commit();
			
			if (userId != 0) {
				uiRepository.add(userId, item.getId());
			}
			
			entityManager.close();
			emfactory.close();
			return findById(item.getId());
		} 
	}
	
	/**
	 * Removes an Item with the given itemId and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param itemId an integer of the Item's id to be removed from the database
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean delete(int itemId) {
		// if the item doesn't exist in the database, deletion fails.
		if (findById(itemId) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
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

	/**
	 * Updates the given Item and returns the updated Item
	 * Update method of CRUD
	 * 
	 * @param item  an Item to be updated from the database
	 * @return an updated Item object from the database
	 * @throws ItemAlreadyExistsException If there already exists the same Item in the database
	 */
	public Item update(Item item) throws ItemAlreadyExistsException {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Item itemById = entityManager.find(Item.class, item.getId());
		Item itemByNS = findByNameAndState(item.getName(), item.getCondition());
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
			return findById(item.getId());
		} else { // user changed either name or condition or both such that there is a duplicate
			throw new ItemAlreadyExistsException(item.getName(),item.getCondition());		
		}		
	}
}
