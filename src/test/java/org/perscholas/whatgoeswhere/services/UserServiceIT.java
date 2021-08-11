package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.perscholas.whatgoeswhere.config.WebAppConfig;
import org.perscholas.whatgoeswhere.controllers.HomeController;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.services.impl.ItemService;
import org.perscholas.whatgoeswhere.services.impl.UserItemService;
import org.perscholas.whatgoeswhere.services.impl.UserService;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceIT {
	private WebApplicationContext webApplicationContext;
	private HomeController homeController;
	private MockMvc mockMvc;
	private UserService userService;
	private UserItemService uiService;
	private ItemService itemService;
	private User user1, user2, toAdd, toDelete;
	private Item item1, item2, item3, itemToDelete;
	private List<Item> items;
	

	@Autowired
	public UserServiceIT(WebApplicationContext webApplicationContext, 
			HomeController homeController, UserService userService,UserItemService uiService,ItemService itemService) {
		this.webApplicationContext = webApplicationContext;
		this.homeController = homeController;
		this.userService = userService;
		this.uiService = uiService;
		this.itemService = itemService;
	}
	
	/**
	 * @see: https://www.baeldung.com/java-beforeall-afterall-non-static
	 * @see: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/TestInstance.html
	 */
	@BeforeAll
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

		LocalDateTime now = LocalDateTime.now();
		item1 = new Item("Banana", "", BestOption.Composting,"", "", now);		
		item2 = new Item("Aerosole cans", "Empty", BestOption.Recycling,"Must be empty.", "", now);	
		items = List.of(item1, item2);
		item3 = new Item("Muffin liner", "Food soiled", BestOption.Composting,"", "", now);	
		itemToDelete = new Item("Parchment paper", "Food soiled", BestOption.Composting,"", "", now);	
		
		// Add users to the database to use for testing if they don't already exist.
		user1 = new User("HoppyCat", "1234helen", "hoppycat@email.com", "Helen", "Kim", new ArrayList<Item>());
		user2 = new User("doomgeek", "1234dpc", "davidchi@email.com", "David","Chi", new ArrayList<Item>());
		
		userService.add(user1);				
		userService.add(user2);
				
	    toAdd = new User("Pusheen", "pusheenthecat", "pusheen@email.com", "Pusheen","Cat", new ArrayList<Item>());
	    List<Item> itemList = new ArrayList<>();
	    itemList.add(itemToDelete);
	    toDelete = new User("Stormy", "stormythesister", "stormy@email.com", "Stormy","Sister",itemList);
		toDelete = userService.add(toDelete);
	}	
	
	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}

	@Test
	void testGetAllUsers() {
		List<User> actual = userService.getAllUsers();
		assertNotNull(actual);
		assertEquals(4, actual.size());
		
	}
	
	@Test
	void testFindUserById() {		
		User expected = user1; 				
		User actual = userService.findUserById(user1.getUsername());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testFindUserByEmail() {
		User expected = user2;
		User actual = userService.findUserByEmail(user2.getEmail());
		assertEquals(expected.getUsername(), actual.getUsername());
	}
	
	@Test
	void testAddUser() {
		// For testing addition, remove the user if it already exists
		User toRemove = userService.findUserById(toAdd.getUsername());
		if (toRemove != null) { // exists in the db
			userService.delete(toRemove);
		}
		
		User expected = toAdd;
		User actual = userService.add(toAdd);
		assertEquals(expected.getUsername(), actual.getUsername());		
	}
	
	@Test
	void testAddUserWithItems() {			
		// Delete the user before adding again.
		userService.delete(user1);
		assertNull(userService.findUserById(user1.getUsername()));
		
		user1.setItems(items);
		User temp = userService.add(user1);
		items = temp.getItems();
		item1 = items.get(0);
		item2 = items.get(1);
				
		List<UserItem> actual = uiService.findByUserId(user1.getUsername());
		assertEquals(items.size(), actual.size());
		assertEquals(item1.getId(), actual.get(0).getItemId());
		assertEquals(item2.getId(), actual.get(1).getItemId());
		
		// For some reason, I can't delete user1 in clean up so I delete here
		uiService.deleteByUserId(user1.getUsername());
		List<UserItem> userItem = uiService.findByUserId(user1.getUsername());
		assertEquals(0, userItem.size());
		
		userService.deleteById(user1.getUsername());
		
	}

	@Test
	void testUpdateUser() {		
		String newPassword = "newpassword";
		user2.setPassword(newPassword);
		user2 = userService.update(user2);
		User actual = userService.findUserById(user2.getUsername());
		assertEquals(newPassword, actual.getPassword());
	}

	@Test
	void testUpdateUserWithNewItem() {		
		user2.getItems().add(item3);
		user2 = userService.update(user2);
		assertTrue(user2.getItems().contains(item3));
	}
	
	@Test
	void testDeleteUser() {	
		uiService.deleteByUserId(toDelete.getUsername());
		userService.delete(toDelete);
		User actual = userService.findUserById(toDelete.getUsername());
		assertNull(actual); 
	}

	@AfterAll
	void clearSetup() {
		userService.delete(user2);
		userService.delete(toAdd);
		
		for (Item item : items) {			
			itemService.delete(item);
		}
	}
}
