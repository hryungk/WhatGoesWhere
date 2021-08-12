package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.repositories.UserRepository;
import org.perscholas.whatgoeswhere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	
	@Autowired // inject into this class from the Spring framework
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.getAllUsers();
	}

	@Override
	public User findUserById(int id) {
		return userRepository.findUserById(id);
	}
	
	@Override
	public User findUserByEmail(String email) {	
		return userRepository.findUserByEmail(email);
	}
	
	@Override
	public User add(User user) {
		return userRepository.add(user);
	}
	
	@Override
	public boolean delete(User user) {
		return userRepository.delete(user);
	}

	@Override
	public User update(User user) {
		return userRepository.update(user);
	}
}
