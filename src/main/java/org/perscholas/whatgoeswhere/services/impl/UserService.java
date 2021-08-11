package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.repositories.UserRepositoryI;
import org.perscholas.whatgoeswhere.services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserServiceI {
	private UserRepositoryI userRepositoryI;
	
	@Autowired // inject into this class from the Spring framework
	public UserService(UserRepositoryI userRepositoryI) {
		this.userRepositoryI = userRepositoryI;
	}
	
	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepositoryI.findAll();
	}

	@Override
	public User findUserById(String id) {
		return userRepositoryI.findByUsername(id);
	}
	
	@Override
	public User findUserByEmail(String email) {	
		return userRepositoryI.findByEmail(email);
	}
	
	@Override
	public User add(User user) {
		return userRepositoryI.save(user);
	}

	@Override
	public void delete(User user) {
		userRepositoryI.deleteUI(user.getUsername());
		userRepositoryI.delete(user);
	}
	@Override
	public void deleteById(String id) {
		userRepositoryI.deleteById(id);
	}
	
	@Override
	public User update(User user) {
		return userRepositoryI.save(user);
	}
	
}
