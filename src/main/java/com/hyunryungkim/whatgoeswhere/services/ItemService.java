package com.hyunryungkim.whatgoeswhere.services;

import java.util.List;

import com.hyunryungkim.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.models.Item;

/**
 * A Service interface for Item class
 * 
 * @author Hyunryung Kim
 *
 */
public interface ItemService {
	/**
	 * Returns all Item objects in the database
	 * 
	 * @return a list of Item objects in the database
	 */
	public List<Item> getAll();	
	
	/**
	 * Returns an Item associated with the given name
	 * 
	 * @param name a string of Item's name
	 * @return an Item that has the given name, null if not found
	 */
	public List<Item> findByName(String name);	
	
	/**
	 * Returns an Item associated with the given name and state
	 * 
	 * @param name  a string of Item's name
	 * @param state a string of Item's condition
	 * @return an Item that has the given name and state, null if not found
	 */
	public Item findByNameAndState(String name, String state);	
	
	/**
	 * Returns an Item associated with the given Item's id
	 * 
	 * @param id an integer of Item's id
	 * @return an Item that has the given id, null if not found
	 */
	public Item findById(int id);	
	
	/**
	 * Returns a newly added Item object
	 * Create method of CRUD
	 * 
	 * @param item an Item to be added to the database
	 * @param userId an integer of the associated User's id
	 * @return an Item object added to the database
	 * @throws ItemAlreadyExistsException If there already exists the same Item in the database
	 */
	public Item add(Item item, int userId) throws ItemAlreadyExistsException;	
	
	/**
	 * Removes an Item with the given itemId and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param itemId an integer of the Item's id to be removed from the database
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean delete(int itemId);	

	/**
	 * Updates the given Item and returns the updated Item
	 * Update method of CRUD
	 * 
	 * @param item  an Item to be updated from the database
	 * @return an updated Item object from the database
	 * @throws ItemAlreadyExistsException If there already exists the same Item in the database
	 */
	public Item update(Item item) throws ItemAlreadyExistsException ;
	
}
