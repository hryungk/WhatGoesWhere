package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.perscholas.whatgoeswhere.config.WebAppConfig;
import org.perscholas.whatgoeswhere.controllers.HomeController;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.models.UserItem;
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
	private int existingUserNum;

	@Autowired
	public UserServiceIT(WebApplicationContext webApplicationContext, 
			HomeController homeController, UserService userService,ItemService itemService,UserItemService uiService) {
		this.webApplicationContext = webApplicationContext;
		this.homeController = homeController;
		this.userService = userService;	
		this.itemService = itemService;
		this.uiService = uiService;
	}
	
	/**
	 * @see: https://www.baeldung.com/java-beforeall-afterall-non-static
	 * @see: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/TestInstance.html
	 */
	@BeforeAll
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	    existingUserNum = userService.getAll().size();
		LocalDateTime now_ldt = LocalDateTime.now();
		item1 = new Item("Banana", "", BestOption.COMPOSTING,"", "", now_ldt);		
		item2 = new Item("Aerosole cans", "Empty", BestOption.RECYCLING,"Must be empty.", "", now_ldt);	
		items = List.of(item1, item2);
		item3 = new Item("Muffin liner", "Food soiled", BestOption.COMPOSTING,"", "", now_ldt);	
		itemToDelete = new Item("Parchment paper", "Food soiled", BestOption.COMPOSTING,"", "", now_ldt);	
		
		// Add users to the database to use for testing if they don't already exist.
		LocalDate now_ld = LocalDate.now();
		user1 = new User("hoppycat@email.com", "Helen", "Kim", now_ld, new ArrayList<Item>());
		user2 = new User("davidchi@email.com", "David","Chi", now_ld, new ArrayList<Item>());
		
		user1 = userService.add(user1);				
		user2 = userService.add(user2);
				
	    toAdd = new User("pusheen@email.com", "Pusheen","Cat", now_ld, new ArrayList<Item>());
	    toDelete = new User("stormy@email.com", "Stormy","Sister", now_ld, List.of(itemToDelete));
		toDelete = userService.add(toDelete);
	}
	
	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}
	
	@Test
//	@Disabled
	void testGetAllUsers() {
		List<User> actual = userService.getAll();
		actual.forEach(System.out::println);
		assertNotNull(actual);
		assertEquals(4 + existingUserNum, actual.size());
	}
	
	@Test
//	@Disabled
	void testFindUserById() {		
		User expected = user1; 				
		User actual = userService.findById(user1.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
//	@Disabled
	void testFindUserByEmail() {
		User expected = user2;
		User actual = userService.findByEmail(user2.getEmail());

		assertEquals(expected, actual);
	}
	
	@Test
//	@Disabled
	void testAddUser() {
		// For testing addition, remove the user if it already exists
		User toRemove = userService.findByEmail(toAdd.getEmail());
		if (toRemove != null) { // exists in the db
			userService.delete(toRemove);
		}
		toAdd = userService.add(toAdd);
		assertNotNull(toAdd);		
	}
	
	@Test
//	@Disabled
	void testAddUserWithItems() {	
		// Delete the user before adding again.
		userService.delete(user1);
		assertNull(userService.findByEmail(user1.getEmail()));
		
		user1.setItems(items);
		user1 = userService.add(user1);
		items = user1.getItems();
		item1 = items.get(0);
		item2 = items.get(1);
		
		List<UserItem> actual = uiService.findByUserId(user1.getId());
		assertEquals(items.size(), actual.size());
		
		List<Integer> itemIds = new ArrayList<>();
		for (UserItem ui : actual) {
			itemIds.add(ui.getItemId());
		}
		for (Item item : items) {
			assertTrue(itemIds.remove((Integer)item.getId()));
		}
		assertEquals(0, itemIds.size());
		
	}

	@Test
//	@Disabled
	void testUpdateUser() {		
		System.out.println("\n\nStarting testUpdateUser()\n\n");
		String newFirstName = "NewFirstName";
		user2.setFirstName(newFirstName);
		userService.update(user2);
		User actual = userService.findById(user2.getId());
		assertEquals(newFirstName, actual.getFirstName());
		
	}

	@Test
//	@Disabled
	void testUpdateUserWithNewItem() {		
		user2.getItems().add(item3);
		user2 = userService.update(user2);
		assertTrue(user2.getItems().contains(item3));
	}

	@Test
//	@Disabled
	void testDeleteUser() {	
		userService.delete(toDelete);
		User actual = userService.findById(toDelete.getId());
		assertNull(actual);
		assertEquals(0, uiService.findByUserId(toDelete.getId()).size());
	}
	
	@AfterAll
	void clearSetup() {
		userService.delete(user1);
		userService.delete(user2);
		userService.delete(toAdd);
		
		for (Item item : items) {			
			itemService.delete(item.getId());
		}
		itemService.delete(item3.getId());
		itemService.delete(itemToDelete.getId());
	}
}
