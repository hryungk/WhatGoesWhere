package com.hyunryungkim.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hyunryungkim.whatgoeswhere.config.WebAppConfig;
import com.hyunryungkim.whatgoeswhere.controllers.HomeController;
import com.hyunryungkim.whatgoeswhere.models.BestOption;
import com.hyunryungkim.whatgoeswhere.models.Item;
import com.hyunryungkim.whatgoeswhere.models.User;
import com.hyunryungkim.whatgoeswhere.models.UserItem;

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
class UserItemServiceIT {
	private WebApplicationContext webApplicationContext;
	private HomeController homeController;
	private MockMvc mockMvc;
	private UserService userService;
	private ItemService itemService;
	private UserItemService uiService;
	private User user1, userToDelete, user3;
	private Item item1, item2, itemToDelete, itemToDelete31, itemToDelete32;
	private List<Item> itemsForUser1, itemsForUser3;
	private int existingUserNum;

	@Autowired
	public UserItemServiceIT(WebApplicationContext webApplicationContext, 
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
	    existingUserNum = uiService.findAll().size();
		LocalDateTime now = LocalDateTime.now();
		item1 = new Item("testItemName1", "testCondition1", BestOption.COMPOSTING,"testSpecialInstrution1", "", now);		
		item2 = new Item("testItemName2", "testCondition2", BestOption.RECYCLING,"testSpecialInstrution2", "testNote2", now);	
		itemToDelete = new Item("testItemToDelete1", "testConditionToDelete1", BestOption.DROPOFF,"testSpecialInstrutionToDelete1", "testNoteToDelete1", now);
		itemToDelete31 = new Item("testItemToDelete2", "testConditionToDelete2", BestOption.DROPOFF,"testSpecialInstrutionToDelete2", "testNoteToDelete2", now);
		itemToDelete32 = new Item("testItemToDelete3", "testConditionToDelete3", BestOption.GARBAGE,"testSpecialInstrutionToDelete3", "", now);
		itemsForUser1 = List.of(item1, item2);
		itemsForUser3 = List.of(itemToDelete31, itemToDelete32);
	    		
		// Add users to the database to use for testing if they don't already exist.
		LocalDate now_ld = LocalDate.now();
		user1 = new User("testuser1@email.com", "FirstName1", "LastName1", now_ld, itemsForUser1);
		user1 = userService.add(user1);
		itemsForUser1 = user1.getItems();
		item1 = itemsForUser1.get(0);
		item2 = itemsForUser1.get(1);
		
		userToDelete = new User("testuser2@email.com", "FirstName2","LastName2", now_ld, List.of(itemToDelete));
		userToDelete = userService.add(userToDelete);
		itemToDelete = userToDelete.getItems().get(0);
		
		user3 = new User("testuser3@email.com", "FirstName3","LastName3", now_ld, itemsForUser3);
		user3 = userService.add(user3);
		itemsForUser3 = user3.getItems();
		itemToDelete31 = itemsForUser3.get(0);
		itemToDelete32 = itemsForUser3.get(1);
	}
	
	
	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}

	@Test
	void testFindAll() {
		List<UserItem> actual = uiService.findAll();
		assertNotNull(actual);
		assertEquals(existingUserNum + itemsForUser1.size(), actual.size());
	}
	
	@Test
	void testFindByItemId() {
		UserItem actual = uiService.findByItemId(item1.getId());
		assertEquals(item1.getId(), actual.getItemId());
	}
	
	@Test
	void testFindByUserId() {
		List<UserItem> actual = uiService.findByUserId(user1.getId());
		assertEquals(itemsForUser1.size(), actual.size());
		assertEquals(item1.getId(), actual.get(0).getItemId());
		assertEquals(item2.getId(), actual.get(1).getItemId());
	}
	
	@Test
	void testDeleteByItemId() {
		uiService.deleteByItemId(itemToDelete.getId());
		UserItem actual = uiService.findByItemId(itemToDelete.getId());
		assertNull(actual);
		
		userService.delete(userToDelete);
		assertNull(userService.findById(userToDelete.getId()));
	}
	
	@Test
//	@Disabled("Testing single delete first")
	void testDeleteByUserId() {
		uiService.deleteByUserId(user3.getId());
		List<UserItem> actual = uiService.findByUserId(user3.getId());
		assertEquals(0, actual.size());
		
		userService.delete(user3);
		assertNull(userService.findById(user3.getId()));
	}

	@AfterAll
	void clearSetup() {
		userService.delete(user1);
		for (Item item : itemsForUser1) {
			itemService.delete(item.getId());
		}
		itemService.delete(itemToDelete.getId());
		itemService.delete(itemToDelete31.getId());
		itemService.delete(itemToDelete32.getId());
	}
}