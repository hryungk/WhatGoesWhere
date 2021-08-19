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
	private User user1, user2, user3;
	private Item item1, item2, toDelete1, toDelete2, toDelete3;
	private List<Item> items, items2delete;
	

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

		LocalDateTime now = LocalDateTime.now();
		item1 = new Item("Banana", "", BestOption.COMPOSTING,"", "", now);		
		item2 = new Item("Aerosole cans", "Empty", BestOption.RECYCLING,"Must be empty.", "", now);	
		toDelete1 = new Item("Aerosole cans", "full or partially full", BestOption.DROPOFF,"If not empty, bring to facility", "Hazardous", now);
		toDelete2 = new Item("Bread bags", "Empty", BestOption.DROPOFF,"Must be clean", "Drop off at a local grocery", now);
		toDelete3 = new Item("Chip bags", "", BestOption.GARBAGE,"", "", now);
		items = List.of(item1, item2);
		items2delete = List.of(toDelete2, toDelete3);
	    		
		// Add users to the database to use for testing if they don't already exist.
		LocalDate now_ld = LocalDate.now();
		user1 = new User("hoppycat@email.com", "Helen", "Kim", now_ld, items);
		user1 = userService.add(user1);
		items = user1.getItems();
		item1 = items.get(0);
		item2 = items.get(1);
		
		user2 = new User("davidchi@email.com", "David","Chi", now_ld, List.of(toDelete1));
		user2 = userService.add(user2);
		toDelete1 = user2.getItems().get(0);
		
		user3 = new User("pusheen@email.com", "Pusheen","Cat", now_ld, items2delete);
		user3 = userService.add(user3);
		items2delete = user3.getItems();
		toDelete2 = items2delete.get(0);
		toDelete3 = items2delete.get(1);
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
		assertEquals(items.size(), actual.size());
		assertEquals(item1.getId(), actual.get(0).getItemId());
		assertEquals(item2.getId(), actual.get(1).getItemId());
	}
	
	@Test
	void testFindByItemId() {
		UserItem actual = uiService.findByItemId(item1.getId());
		assertEquals(item1.getId(), actual.getItemId());
	}
	
	@Test
	void testFindByUserId() {
		List<UserItem> actual = uiService.findByUserId(user1.getId());
		assertEquals(items.size(), actual.size());
		assertEquals(item1.getId(), actual.get(0).getItemId());
		assertEquals(item2.getId(), actual.get(1).getItemId());
	}
	
	@Test
	void testDeleteByItemId() {
		uiService.deleteByItemId(toDelete1.getId());
		UserItem actual = uiService.findByItemId(toDelete1.getId());
		assertNull(actual);
		
		userService.delete(user2);
		assertNull(userService.findById(user2.getId()));
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
		for (Item item : items) {
			itemService.delete(item.getId());
		}
		itemService.delete(toDelete1.getId());
		itemService.delete(toDelete2.getId());
		itemService.delete(toDelete3.getId());
	}
}