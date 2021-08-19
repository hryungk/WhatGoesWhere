package com.hyunryungkim.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hyunryungkim.whatgoeswhere.models.Item;
import com.hyunryungkim.whatgoeswhere.models.User;
import com.hyunryungkim.whatgoeswhere.repositories.UserRepository;
import com.hyunryungkim.whatgoeswhere.services.impl.UserServiceImpl;

class UserServiceMockitoTest {
	private static UserService userService;
	private static UserRepository userRepository;
	
	@BeforeAll
	static void setup() {
		userRepository = Mockito.mock(UserRepository.class);
		userService = new UserServiceImpl(userRepository);
	}

	@Test
	void testGetAllUsers() {
		LocalDate now = LocalDate.now();
		User user1 = new User("pusheen@email.com", "Pusheen","Cat", now, new ArrayList<Item>());
		User user2 = new User("stormy@email.com", "Stormy","Cat", now, new ArrayList<Item>());
		User user3 = new User("pip@email.com", "Pip","Cat", now, new ArrayList<Item>());
		Mockito.when(userRepository.getAll()).thenReturn(List.of(user1, user2, user3));
		
		List<User> actualList = userService.getAll();
		User[] expected = {user1, user2, user3};
		User[] actual = new User[actualList.size()];
		actual = actualList.toArray(actual);
		assertArrayEquals(expected, actual);		
	}
	
	@Test
	void testFindUserById() {
		User user1 = new User("pusheen@email.com", "Pusheen","Cat", LocalDate.now(), new ArrayList<Item>());
		Mockito.when(userRepository.findById(anyInt())).thenReturn(user1);
		String expected = "pusheen@email.com";
		
		User actual = userService.findById(user1.getId());		
		
		assertEquals(expected, actual.getEmail());		
	}
	
	@Test
	void testFindUserByEmail() {
		String expected = "pusheen@email.com";
		User user1 = new User("pusheen@email.com", "Pusheen","Cat", LocalDate.now(), new ArrayList<Item>());
		Mockito.when(userRepository.findByEmail(anyString())).thenReturn(user1);
		
		User actual = userService.findByEmail(expected);
		
		assertEquals(expected, actual.getEmail());
	}	
	
}
