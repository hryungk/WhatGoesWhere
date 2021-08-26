package com.hyunryungkim.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.models.ModelUtilities;

/**
 * A DAO Repository class for Credential model
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.Credential
 * 
 */
@Repository
public class CredentialRepository {	
	/**
	 * Class constructor registering a database driver
	 */
	public CredentialRepository() {		
		try {
			Class.forName(ModelUtilities.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns all Credential objects in the database
	 * 
	 * @return a list of Credential objects in the database
	 */
	public List<Credential> findAll() {
		return findCredentials(ModelUtilities.Credential.NAME_FIND_ALL);
	}
	
	/**
	 * Returns a Credential associated with the given username
	 * 
	 * @param username a string of Credential's username
	 * @return a Credential that has the given username, null if not found
	 */
	public Credential findByUsername(String username) {	
		List<Credential> credentials = findCredentials(ModelUtilities.Credential.NAME_FINDBY_USERNAME,username);	
		if (credentials.isEmpty())
			return null;
		return credentials.get(0);	
	}

	/**
	 * Returns a Credential associated with given username and password
	 *  
	 * @param username a string of Credential's username
	 * @param password a string of Credential's password
	 * @return a Credential that has both given username and password, null if not found
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	public Credential findByUsernameAndPassword(String username, String password) throws CredentialNotFoundException {	
		List<Credential> credentials = findCredentials(ModelUtilities.Credential.NAME_FINDBY_USERNAME_PASSWORD,username, password);	
		if (credentials.isEmpty()) {
			Credential credentialByUsername = findByUsername(username);
			String message = "";
			if (credentialByUsername == null) // User name is not found
				message = "You haven't registered yet. Please click the link below the form to create a new account.";
			else // User name is found but password doesn't match.
				message = "Credentials not correct. Please try again";
			throw new CredentialNotFoundException(message);			
		} else {
			return credentials.get(0);
		}
	}
	
	/**
	 * Returns a list of Credential objects according to the queryName and variable columns
	 * Used by other find methods for querying named queries
	 * 
	 * @param queryName a string of named query defined in the model class
	 * @param columns an array of strings of column variables to go into the query
	 * @return a List of Credential objects returned by the query
	 */
	private List<Credential> findCredentials(String queryName, String ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<Credential> query = entityManager.createNamedQuery(queryName, Credential.class);
		for (int i = 1; i <= columns.length; i++) {
			query.setParameter(i, columns[i-1]);
		}
		List<Credential> users = query.getResultList();
		
		entityManager.close();
		emfactory.close();
		return users;
	}

	/**
	 * Returns a Credential associated with the given Credential's id
	 * 
	 * @param id an integer of Credential's id
	 * @return a Credential that has the given id, null if not found
	 */
	public Credential findById(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		Credential user = entityManager.find(Credential.class, id);
		
		entityManager.close();
		emfactory.close();
		return user;
	}
	
	/**
	 * Returns a newly added Credential object
	 * Create method of CRUD
	 * 
	 * @param credential a Credential to be added to the database
	 * @return a Credential object added to the database
	 * @throws CredentialAlreadyExistsException If there already exists the same Credential in the database
	 */
	public Credential add(Credential credential) throws CredentialAlreadyExistsException {
		if (findByUsername(credential.getUsername()) != null) { // If the username already exist in the system, throw exception.
			throw new CredentialAlreadyExistsException(credential.getUsername());
		} else {	
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(credential);			
			
			entityManager.getTransaction().commit();
			
			entityManager.close();
			emfactory.close();
			return findById(credential.getId());
		} 
	}

	/**
	 * Removes the given Credential and returns a boolean of the result
	 * Delete method of CRUD
	 * 
	 * @param credential a Credential to be removed from the database
	 * @return true if the removal was successful, false otherwise
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	public boolean delete(Credential credential) throws CredentialNotFoundException {
		// if the user doesn't exist in the database, deletion fails.
		if (findById(credential.getId()) == null) {			
			throw new CredentialNotFoundException();
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Credential userToDelete = entityManager.find(Credential.class, credential.getId());
		entityManager.remove(userToDelete);
		
		entityManager.getTransaction().commit();		
		entityManager.close();
		emfactory.close();
		
		return findById(credential.getId()) == null;
	}

	/**
	 * Updates the given Credential and returns the updated Credential
	 * Update method of CRUD
	 * 
	 * @param credential a Credential to be updated from the database
	 * @return an updated Credential object from the database
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	public Credential update(Credential credential) throws CredentialNotFoundException {
		// if the user doesn't exist in the database, update fails.
		if (findById(credential.getId()) == null) {
			throw new CredentialNotFoundException();
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Credential credentialToUpdate = entityManager.find(Credential.class, credential.getId());
		credentialToUpdate.setUsername(credential.getUsername());
		credentialToUpdate.setPassword(credential.getPassword());
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return findById(credential.getId());
	}
	
}
