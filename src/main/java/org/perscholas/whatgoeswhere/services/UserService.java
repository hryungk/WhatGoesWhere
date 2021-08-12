package org.perscholas.whatgoeswhere.services;

import java.util.List;
import org.perscholas.whatgoeswhere.models.User;

public interface UserService {
	public List<User> getAllUsers();
	public User findUserById(int id);	
	public User findUserByEmail(String email);	
	public User add(User user) ;	
	public boolean delete(User user);
	public User update(User user);
}
