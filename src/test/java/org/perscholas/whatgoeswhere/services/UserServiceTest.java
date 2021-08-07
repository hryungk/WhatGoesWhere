package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Matchers.anyString;

import java.util.List;
import java.util.Optional;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.repositories.UserRepositoryI;
import org.perscholas.whatgoeswhere.services.impl.UserService;

class UserServiceTest {
	private static UserService userService;
	private static UserRepositoryI userRepositoryI;
	
	@BeforeAll
	static void setup() {
		userRepositoryI = Mockito.mock(UserRepositoryI.class);
		userService = new UserService(userRepositoryI);
	}

	@Test
	void testGetAllUsers() {
		User user1 = new User("pusheen", "pusheenthecat", "pusheen@email.com", "Pusheen","Cat", null);
		User user2 = new User("stormy", "stormythesister", "stormy@email.com", "Stormy","Cat", null);
		User user3 = new User("pip", "pipthebrother", "pip@email.com", "Pip","Cat", null);
		Mockito.when(userRepositoryI.findAll()).thenReturn(List.of(user1, user2, user3));
		
		List<User> actualList = userService.getAllUsers();
		User[] expected = {user1, user2, user3};
		User[] actual = new User[actualList.size()];
		actual = actualList.toArray(actual);
		assertArrayEquals(expected, actual);		
	}
	
	@Test
	void testFindUserById() {
		String input1 = "pusheen";
		Optional<User> user1 = Optional.of(new User(input1, "pusheenthecat", "pusheen@email.com", "Pusheen","Cat", null));
		Mockito.when(userRepositoryI.findById(anyString())).thenReturn(user1);
		String expected = "pusheen@email.com";
		
		User actual = userService.findUserById(input1);		
		
		assertEquals(expected, actual.getEmail());		
	}
	
	@Test
	void testFindUserByEmail() {
		String input1 = "pusheen@email.com";
		User user1 = new User("pusheen", "pusheenthecat", "pusheen@email.com", "Pusheen","Cat", null);
		Mockito.when(userRepositoryI.findByEmail(anyString())).thenReturn(user1);
		
		User actual = userService.findUserByEmail(input1);
		String expected = "pusheen";
		assertEquals(expected, actual.getUsername());
	}	
	
}
