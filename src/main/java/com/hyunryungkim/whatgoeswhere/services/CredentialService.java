package com.hyunryungkim.whatgoeswhere.services;

import java.util.List;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;

/**
 * A Service interface for Credential class
 * 
 * @author Hyunryung Kim
 *
 */
public interface CredentialService {
	/**
	 * Returns all Credential objects in the database
	 * 
	 * @return a list of Credential objects in the database
	 */
	public List<Credential> getAll() ;
	
	/**
	 * Returns a Credential associated with the given Credential's id
	 * 
	 * @param id an integer of Credential's id
	 * @return a Credential that has the given id, null if not found
	 */
	public Credential findById(int id) ;	
	
	/**
	 * Returns a Credential associated with the given username
	 * 
	 * @param username a string of Credential's username
	 * @return a Credential that has the given username, null if not found
	 */
	public Credential findByUsername(String username);	
	
	/**
	 * Returns a Credential associated with given username and password
	 *  
	 * @param username a string of Credential's username
	 * @param password a string of Credential's password
	 * @return a Credential that has both given username and password, null if not found
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	public Credential findByUsernameAndPassword(String username, String password) throws CredentialNotFoundException;
	
	/**
	 * Returns a newly added Credential object
	 * Create method of CRUD
	 * 
	 * @param credential a Credential to be added to the database
	 * @return a Credential object added to the database
	 * @throws CredentialAlreadyExistsException If there already exists the same Credential in the database
	 */
	public Credential add(Credential credential) throws CredentialAlreadyExistsException;
	
	/**
	 * Removes the given Credential and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param credential a Credential to be removed from the database
	 * @return true if the removal was successful, false otherwise
	 * @throws CredentialNotFoundException 
	 */
	public boolean delete(Credential credential) throws CredentialNotFoundException;
	
	/**
	 * Updates the given Credential and returns the updated Credential
	 * Update method of CRUD
	 * 
	 * @param credential a Credential to be updated from the database
	 * @return an updated Credential object from the database
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	public Credential update(Credential credential) throws CredentialNotFoundException;
	
	/**
	 * Returns whether password1 and password2 are matching or not
	 * 
	 * @param password1 a string representing a raw password to encode and match
	 * @param password2 a string representing the encoded password from storage to compare with
	 * @return true if password1 and password2 are matching, false otherwise
	 */
	public boolean checkIfValidOldPassword(String password1, String password2);
}
