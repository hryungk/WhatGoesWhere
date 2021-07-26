package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.perscholas.whatgoeswhere.config.WebAppConfig;
import org.perscholas.whatgoeswhere.controllers.HomeController;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Spring MVC Integration Test 
 * (It is a convention to name integration test IT because 
 * it's not a unit test and we're relying on Spring.)
 *
 */

@ExtendWith(SpringExtension.class)  // This doesn't really change.
@ContextConfiguration(classes = { WebAppConfig.class })
@WebAppConfiguration("WebContent") // Letting it know where web content is (folder name)
class UserServiceIT {
	private WebApplicationContext webApplicationContext;
	private HomeController homeController;
	private MockMvc mockMvc;
	private UserService userService;
	private User user1;
	private User user2;

	@Autowired
	public UserServiceIT(WebApplicationContext webApplicationContext, 
			HomeController homeController, UserService userService) {
		this.webApplicationContext = webApplicationContext;
		this.homeController = homeController;
		this.userService = userService;		
	}
	
	@BeforeEach
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	    
		// Add users to the database to use for testing if they don't already exist.
		user1 = new User("HoppyCat", "1234helen", "hoppycat@email.com", "Helen", "Kim", null);
		user2 = new User("doomgeek", "1234dpc", "davidchi@email.com", "David","Chi", null);
		
		if(!userService.addUser(user1))
			user1 = userService.findUserById(user1.getId());
		
		if(!userService.addUser(user2))
			user2 = userService.findUserById(user2.getId());		
	}
	
	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}
	
	@Test
	void testGetAllUsers() {
		List<User> actual = userService.getAllUsers();
		actual.forEach(System.out::println);
		assertNotNull(actual);
	}
	
	@Test
	void testFindUserById() {		
		User expected = user1; 				
		User actual = userService.findUserById(user1.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testFindUserByEmail() {
		User expected = user2;
		User actual = userService.findUserByEmail(user2.getEmail());

		assertEquals(expected, actual);
	}
	
	@Test
	void testAddUser() {
		// For testing addition, remove the user if it already exists
		String id = "pusheen";
		User toRemove = userService.findUserById(id);
		if (toRemove != null) { // exists in the db
			userService.deleteUser(toRemove);
		}
		
		User expected = new User(id, "pusheenthecat", "pusheen@email.com", "Pusheen","Cat", null);
		assertTrue(userService.addUser(expected));		
	}
	
	@Test
	void testAddUserWithItems() {	
		// For testing addition, remove the user if it already exists
		String user1Name = "doomgeek";
		User toRemove = userService.findUserById(user1Name);
		if (toRemove != null) { // exists in the db
			userService.deleteUser(toRemove);
		}
		LocalDateTime now = LocalDateTime.now();
		Item item1 = new Item("Banana", "", "Composting","", "", now);		
		Item item2 = new Item("Aerosole cans", "Empty", "Recycling","Must be empty.", "", now);	
		List<Item> items = List.of(item1, item2);
		User user1 = new User("doomgeek", "1234dpc", "davidchi@email.com", "David","Chi", items);		
		userService.addUser(user1);
		
		// A list of item IDs added with the user1
		List<Integer> expected = new ArrayList<>();
		items.forEach(item -> expected.add(item.getId()));
		
		List<Item> itemsFromUser= userService.getItems(user1.getId());		
		
		// A list of item IDs added by user1 queried from the join table in the database
		List<Integer> actual = new ArrayList<>();
		itemsFromUser.forEach(item -> actual.add(item.getId()));		
				
		boolean result = true;
		int ii = 0;
		while (result && ii < expected.size()) {
			result = actual.remove(expected.get(ii));
			ii++;
		}
		
		assertTrue(result);
	}

	@Test
	void testUpdateUser() {		
		System.out.println("\n\nStarting testUpdateUser()\n\n");
		User expected = userService.findUserById(user2.getId());
		System.out.println(expected);
		expected.setPassword("newpassword");
		assertTrue(userService.updateUser(expected));
	}

	@Test
	void testDeleteUser() {	
		
		System.out.println("\n\nStarting testDeleteUser()\n\n");
		User expected = userService.findUserById(user1.getId());
		assertTrue(userService.deleteUser(expected));
	}
}
