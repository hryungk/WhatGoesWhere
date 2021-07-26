package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.perscholas.whatgoeswhere.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	
	private UserItemRepository uiRepository;
	
	public UserRepository() {
		
		this.uiRepository = new UserItemRepository();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<User> getAllUsers() {
		List<User> users = findUsers("User.findAll");
		return users;
	}

	public User findUserById(String id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		
		User user = entityManager.find(User.class, id);
		
		entityManager.close();
		emfactory.close();
		return user;
	}
	
	public User findUserByEmail(String email) {	
		List<User> users = findUsers("User.findByEmail",email);	
		if (users.size() == 0)
			return null;
		return users.get(0); 
	}
	
	private List<User> findUsers(String queryName, String ... columns) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		
		TypedQuery<User> query = entityManager.createNamedQuery(queryName, User.class);
		for (int i = 1; i <= columns.length; i++) {
			query.setParameter(i, columns[i-1]);
		}
		List<User> users = query.getResultList();
		
		entityManager.close();
		emfactory.close();
		return users;
	}
	
	public boolean addUser(User user) {
		// If there already exists the same user in the system, addition fails.
		if (findUserById(user.getId()) == null) {		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
			EntityManager entityManager = emfactory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.persist(user);			
			
			entityManager.getTransaction().commit();
			entityManager.refresh(user);
			
			entityManager.close();
			emfactory.close();
			return true;
		} 
		return false;
	}

	public boolean deleteUser(User user) {
		// if the user doesn't exist in the database, deletion fails.
		if (findUserById(user.getId()) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		// Delete the join table entities first
		boolean isUIDeleteSuccessful = uiRepository.deleteUserItemByUserId(user.getId());
		if (isUIDeleteSuccessful) {
			User userToDelete = entityManager.find(User.class, user.getId());
			entityManager.remove(userToDelete);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}

	public boolean updateUser(User user) {
		// if the user doesn't exist in the database, update fails.
		if (findUserById(user.getId()) == null) {
			return false;
		}
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager entityManager = emfactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		User userToUpdate = entityManager.find(User.class, user.getId());
		userToUpdate.setId(user.getId());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLasttName(user.getLastName());
		
		entityManager.getTransaction().commit();
		entityManager.close();
		emfactory.close();
		return true;
	}
}
