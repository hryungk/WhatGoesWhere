package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.User;

public interface UserServiceI {
	public List<User> getAllUsers();

	public User findUserById(String id);
	
	public User findUserByEmail(String email);
	
	public User add(User user);
	
	public void delete(User user);
	
	public void deleteById(String id);

	public User update(User user);
	
}
