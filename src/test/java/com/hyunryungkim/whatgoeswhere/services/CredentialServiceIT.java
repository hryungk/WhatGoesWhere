package com.hyunryungkim.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hyunryungkim.whatgoeswhere.config.WebAppConfig;
import com.hyunryungkim.whatgoeswhere.controllers.HomeController;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.BestOption;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.models.Item;
import com.hyunryungkim.whatgoeswhere.models.User;

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
	private Credential credential1, credential2, toAdd, toDelete;//, invalidUpdate;
	private User user1, user2, userToAdd, userToDelete;
	private Item item1, item2;
	private List<Item> items;
	private int existingCredentialNum;
	
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
	    existingCredentialNum = credentialService.findAll().size();
	    // Items for user1
	    LocalDateTime now_ldt = LocalDateTime.now();
		item1 = new Item("TestItem1", "", BestOption.COMPOSTING,"", "", now_ldt);		
		item2 = new Item("TestItem2", "TestCondition2", BestOption.RECYCLING, "TestSpecialInstruction2", "", now_ldt);	
		items = List.of(item1, item2);
	    
	    // Users for corresponding credential
 		LocalDate now_ld = LocalDate.now();
 		user1 = new User("testuser1@email.com", "FirstName1", "LastName1", now_ld, items);
 		user2 = new User("testuser2@email.com", "FirstName2","LastName2", now_ld, new ArrayList<Item>());
 		userToAdd = new User("testusertoadd@email.com", "FirstNameToAdd", "LastNameToAdd", now_ld, new ArrayList<Item>());
 		userToDelete = new User("testusertodelete@email.com", "FirstNameToDelete", "LastNameToDelete", now_ld, new ArrayList<Item>());
	    
 		// Add credentials to the database to use for testing if they don't already exist.
	    credential1 = new Credential("testuser1", "testuser11234", user1); 
	    credential2 = new Credential("testuser2", "testuser21234", user2);
//	    invalidUpdate = new Credential("testUserNotExists", "pass", null);
	    
	    credential1 = credentialService.add(credential1);
	    credential2 = credentialService.add(credential2);
	    
	    toAdd = new Credential("testusertoadd", "testusertoadd1234", userToAdd);
	    toDelete = new Credential("testusertodelete", "testusertodelete1234", userToDelete);
	    credentialService.add(toDelete);
	    
	}

	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}
	
	@Test
	void testGetAllCredentials() {
		List<Credential> credentials = credentialService.findAll();
		assertNotNull(credentials);
		assertEquals(3 + existingCredentialNum, credentials.size());
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
	
	@ParameterizedTest
	@MethodSource("provideStringsForTestFindByUsernameAndPassword")
	void testFindByUsernameAndPassword(String username, String password, boolean expected) throws CredentialNotFoundException {
	    if(!expected) {
	    	assertThrows(CredentialNotFoundException.class, () -> {
				credentialService.findByUsernameAndPassword(username, password);
			});
	    } else {
	    	assertNotNull(credentialService.findByUsernameAndPassword(username, password));
	    }
	}
	private Stream<Arguments> provideStringsForTestFindByUsernameAndPassword() {
	    return Stream.of(
	      Arguments.of(credential1.getUsername(), credential1.getPassword(), true),
	      Arguments.of(credential1.getUsername(), "invalidPassword", false),
	      Arguments.of("invalidUsername", credential1.getPassword(), false)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("provideCredentialsForTestAdd")
	void testAdd(Credential credential, boolean expected) throws CredentialAlreadyExistsException {
		if(!expected) {
	    	assertThrows(CredentialAlreadyExistsException.class, () -> {
				credentialService.add(credential);
			});
	    } else {
	    	credentialService.add(credential);
	    	assertNotNull(credentialService.findByUsername(credential.getUsername()));
	    }
	}
	private Stream<Arguments> provideCredentialsForTestAdd() {
	    return Stream.of(
	      Arguments.of(toAdd, true),
	      Arguments.of(credential1, false),
	      Arguments.of(credential2, false)
	    );
	}
	
	@Test
	void testAddWithUserWithItem() throws CredentialAlreadyExistsException, CredentialNotFoundException {
//		credentialService.delete(credential1);
//		credential1.setUser(user1);
//		credential1 = credentialService.add(credential1);
		
		assertNotNull(credential1.getUser());
		assertEquals(items.size(), credential1.getUser().getItems().size());
	}
	
	@Test
	void testUpdate() throws CredentialNotFoundException {
		String expected = "newPassword";
		credential1.setPassword(expected);
		credential1 = credentialService.update(credential1);
		String actual = credential1.getPassword();
		assertTrue(credentialService.checkIfValidOldPassword(expected, actual));
//		assertEquals(expected, actual); 
	}
//	@ParameterizedTest
//	@MethodSource("provideCredentialsForTestUpdate")
//	void testUpdate(Credential credential, String expected, boolean tf) throws CredentialNotFoundException {
//		if (!tf) {
//			assertThrows(CredentialNotFoundException.class, () -> {
//				Credential updatedcredential = credentialService.update(credential);
//			});
//		} else {
//			credential.setPassword(expected);
//			credential = credentialService.update(credential);
//			String actual = credential.getPassword();
//			assertEquals(expected, actual);
//		}		
//	}
//	private Stream<Arguments> provideCredentialsForTestUpdate() {		
//	    return Stream.of(
//	      Arguments.of(credential1, "newPassword", true),
//	      Arguments.of(invalidUpdate, "newPass", false)
//	    );
//	}
	
	@Test
	void testDelete() {
		try {
			credentialService.delete(toDelete);
		} catch (CredentialNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Credential actual = credentialService.findById(toDelete.getId());
		assertNull(actual);
	}
	
	@AfterAll
	void clearSetup() {
		try {
			credentialService.delete(credential1);
			credentialService.delete(credential2);
			credentialService.delete(toAdd);
		} catch (CredentialNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		for (Item item : items)
			itemService.delete(item.getId());
	}

}
