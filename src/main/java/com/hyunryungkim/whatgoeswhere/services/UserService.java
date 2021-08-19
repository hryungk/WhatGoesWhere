package com.hyunryungkim.whatgoeswhere.services;

import java.util.List;

import com.hyunryungkim.whatgoeswhere.models.User;

/**
 * A Service interface for User class
 * 
 * @author Hyunryung Kim
 *
 */
public interface UserService {
	/**
	 * Returns all User objects in the database
	 *  
	 * @return a list of User objects in the database
	 */
	public List<User> getAll();
	
	/**
	 * Returns a User associated with the given User's id
	 * 
	 * @param id an integer of User's id
	 * @return a User that has the given id, null if not found
	 */
	public User findById(int id);	

	/**
	 * Returns a User associated with the given email
	 * 
	 * @param email a string of User's email
	 * @return a User that has the given email, null if not found
	 */
	public User findByEmail(String email);	
	
	/**
	 * Returns a newly added User object
	 * Create method of CRUD
	 * 
	 * @param user an Item to be added to the database
	 * @return a User object added to the database
	 */
	public User add(User user) ;	
	
	/**
	 * Removes a User with the given user and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param user a User to be removed from the database
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean delete(User user);
	
	/**
	 * Updates the given User and returns the updated User
	 * Update method of CRUD
	 * 
	 * @param user a User to be updated from the database
	 * @return an updated User object from the database
	 */
	public User update(User user);
}
