package com.hyunryungkim.whatgoeswhere.services;

import java.util.List;

import com.hyunryungkim.whatgoeswhere.models.UserItem;

/**
 * A Service interface for UserItem class
 * 
 * @author Hyunryung Kim
 *
 */
public interface UserItemService {
	/**
	 * Returns all UserItem objects in the database
	 * 
	 * @return a list of UserItem objects in the database
	 */
	public List<UserItem> findAll();
	
	/**
	 * Returns a UserItem associated with the given Item id
	 * 
	 * @param itemId an integer of Item's id
	 * @return a UserItem that has the given Item id, null if not found
	 */
	public UserItem findByItemId(int itemId);
	
	/**
	 * Returns a UserItem associated with the given User id
	 * 
	 * @param userId an integer of User's id
	 * @return a list of UserItem objects that has the given User id, null if not found
	 */
	public List<UserItem> findByUserId(int userId);
	
	/**
	 * Adds a new UserItem and returns a boolean of whether the addition was successful
	 * Create method of CRUD
	 * 
	 * @param userId an integer of User's id
	 * @param itemId an integer of Item's id
	 * @return true if the addition was successful, false otherwise
	 */
	public boolean add(int userId, int itemId);
	
	/**
	 * Removes the UserItem with the given itemId and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param itemId an integer of Item's id
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean deleteByItemId(int itemId);
	/**
	 * Removes all UserItem with the given userId and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param userId an integer of User's id
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean deleteByUserId(int userId);
}
