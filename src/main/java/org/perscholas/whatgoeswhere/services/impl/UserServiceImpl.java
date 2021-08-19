package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.repositories.UserRepository;
import org.perscholas.whatgoeswhere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A Service class for User class
 * 
 * @author Hyunryung Kim
 *
 */
@Service
public class UserServiceImpl implements UserService {
	/**
	 * Repository class for User class
	 */
	private UserRepository userRepository;
	
	/**
	 * Class constructor accepting fields
	 * 
	 * @param userRepository a UserRepository object for DAO methods
	 */
	@Autowired // inject into this class from the Spring framework
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public List<User> getAll() {
		return userRepository.getAll();
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id);
	}
	
	@Override
	public User findByEmail(String email) {	
		return userRepository.findByEmail(email);
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
