package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository userRepository;
	
	@Autowired // inject into this class from the Spring framework
	public UserService(UserRepository itemRepository) {
		this.userRepository = itemRepository;
	}
	public List<User> getAllUsers() {
		return userRepository.getAllUsers();
	}

	public User findUserById(String id) {
		return userRepository.findUserById(id);
	}
	
	public User findUserByEmail(String email) {	
		return userRepository.findUserByEmail(email);
	}
	
	
	public boolean addUser(User user) {
		return userRepository.addUser(user);
	}

	public boolean deleteUser(User user) {
		return userRepository.deleteUser(user);
	}

	public boolean updateUser(User user) {
		return userRepository.updateUser(user);
	}
}
