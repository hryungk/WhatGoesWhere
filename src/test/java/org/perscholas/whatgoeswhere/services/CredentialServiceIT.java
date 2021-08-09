package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.perscholas.whatgoeswhere.config.WebAppConfig;
import org.perscholas.whatgoeswhere.controllers.HomeController;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Credential;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CredentialServiceIT {
	private WebApplicationContext webApplicationContext;
	private HomeController homeController;
	private MockMvc mockMvc;
	private CredentialService credentialService;
	private ItemService itemService;
	private Credential credential1, credential2, toAdd, toDelete;
	private User user1, user2, userToAdd, userToDelete;
	private Item item1, item2;
	private List<Item> items;
	
	@Autowired
	public CredentialServiceIT(WebApplicationContext webApplicationContext, HomeController homeController,
			CredentialService credentialService, ItemService itemService) {
		super();
		this.webApplicationContext = webApplicationContext;
		this.homeController = homeController;
		this.credentialService = credentialService;
		this.itemService = itemService;
	}
	
	/**
	 * @see: https://www.baeldung.com/java-beforeall-afterall-non-static
	 * @see: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/TestInstance.html
	 */
	@BeforeAll
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	    // Items for user1
	    LocalDateTime now_ldt = LocalDateTime.now();
		item1 = new Item("Banana", "", BestOption.Composting,"", "", now_ldt);		
		item2 = new Item("Aerosole cans", "Empty", BestOption.Recycling,"Must be empty.", "", now_ldt);	
		items = List.of(item1, item2);
	    
	    // Users for corresponding credential
 		LocalDate now_ld = LocalDate.now();
 		user1 = new User("hoppycat@email.com", "Helen", "Kim", now_ld, items);
 		user2 = new User("davidchi@email.com", "David","Chi", now_ld, new ArrayList<Item>());
 		userToAdd = new User("pusheen@email.com", "Pusheen", "Cat", now_ld, new ArrayList<Item>());
 		userToDelete = new User("stormy@email.com", "Stormy", "Sister", now_ld, new ArrayList<Item>());
	    
 		// Add credentials to the database to use for testing if they don't already exist.
	    credential1 = new Credential("hoppycat", "hoppycat1234", null); 
	    credential2 = new Credential("doomgeek", "doomgeek1234", user2);
	    
	    credential1 = credentialService.add(credential1);
	    credential2 = credentialService.add(credential2);
	    
	    toAdd = new Credential("pusheenthecat", "pusheen1234", userToAdd);
	    toDelete = new Credential("stormythesister", "stormy1234", userToDelete);
	    credentialService.add(toDelete);
	    
	}

	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}
	
	@Test
	void testFindAllCredentials() {
		List<Credential> credentials = credentialService.getAll();
		assertNotNull(credentials);
		assertEquals(4, credentials.size());
	}
	
	@Test
	void testFindById() {
		Credential expected = credential1;
		Credential actual = credentialService.findById(credential1.getId());
		assertEquals(expected, actual);
	}
	
	@Test
	void testFindByUsername() {
		Credential expected = credential1;
		Credential actual = credentialService.findByUsername(credential1.getUsername());
		assertEquals(expected, actual);
	}
	
	@Test
	void testAdd() {
		// For testing addition, remove the user if it already exists
		Credential toRemove = credentialService.findByUsername(toAdd.getUsername());
		if (toRemove != null) { // exists in the db
			credentialService.delete(toRemove);
		}
		toAdd = credentialService.add(toAdd);
		assertNotNull(toAdd);	
	}
	
	@Test
	void testAddWithItem() {
		credentialService.delete(credential1);
		credential1.setUser(user1);
		credential1 = credentialService.add(credential1);
		
		assertNotNull(credential1.getUser());
		assertEquals(items.size(), credential1.getUser().getItems().size());
	}
	
	@Test
	void testUpdate() {
		String expected = "newPassword";
		credential1.setPassword(expected);
		credential1 = credentialService.update(credential1);
		String actual = credential1.getPassword();
		assertEquals(expected, actual); 
	}
	
	@Test
	void testDelete() {
		credentialService.delete(toDelete);
		Credential actual = credentialService.findById(toDelete.getId());
		assertNull(actual);
	}
	
	@AfterAll
	void clearSetup() {
		credentialService.delete(credential1);
		credentialService.delete(credential2);
		credentialService.delete(toAdd);
		
		for (Item item : items)
			itemService.deleteItem(item.getId());
	}

}
