package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import org.perscholas.whatgoeswhere.exceptions.CredentialNotFoundException;
import org.perscholas.whatgoeswhere.models.Credential;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepository {
	
	private static final String PERSIST_UNIT_NAME = "WhatGoesWhere";
	
	public CredentialRepository() {
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Credential> getAll() {
		return findCredentials("Credential.findAll");
	}

	public Credential findById(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		
		Credential user = entityManager.find(Credential.class, id);
		
		entityManager.close();
		emfactory.close();
		return user;
	}
	
	public Credential findByUsername(String username) {	
		List<Credential> credentials = findCredentials("Credential.findByUsername",username);	
		if (credentials.isEmpty())
			return null;
		return credentials.get(0);	
	}

	public Credential findByUsernameAndPassword(String username, String password) throws CredentialNotFoundException {	
		List<Credential> credentials = findCredentials("Credential.findByUsernameAndPassword",username, password);	
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
	
	private List<Credential> findCredentials(String queryName, String ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
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
	
	public Credential add(Credential credential) throws CredentialAlreadyExistsException {
		if (findByUsername(credential.getUsername()) != null) { // If the username already exist in the system, throw exception.
			throw new CredentialAlreadyExistsException(credential.getUsername());
		} else {
//		if (findById(credential.getId()) == null) {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(credential);			
			
			entityManager.getTransaction().commit();
			
			entityManager.close();
			emfactory.close();
			return findById(credential.getId());
		} 
//		return null;
	}

	public boolean delete(Credential credential) {
		// if the user doesn't exist in the database, deletion fails.
		if (findById(credential.getId()) == null) {			
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Credential userToDelete = entityManager.find(Credential.class, credential.getId());
		entityManager.remove(userToDelete);
		
		entityManager.getTransaction().commit();		
		entityManager.close();
		emfactory.close();
		
		return findById(credential.getId()) == null;
	}

	public Credential update(Credential credential) {
		// if the user doesn't exist in the database, update fails.
		if (findById(credential.getId()) == null) {
			return credential;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSIST_UNIT_NAME);
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
