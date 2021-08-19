package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.perscholas.whatgoeswhere.config.WebAppConfig;
import org.perscholas.whatgoeswhere.controllers.HomeController;
import org.perscholas.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Item;
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
class ItemServiceIT {
	private WebApplicationContext webApplicationContext;
	private HomeController homeController;
	private MockMvc mockMvc;
	private ItemService itemService;
	private Item item1, item2, toAdd, toDelete;
	private int existingItemNum;

	@Autowired
	public ItemServiceIT(WebApplicationContext webApplicationContext, 
			HomeController homeController, ItemService itemService) {
		this.webApplicationContext = webApplicationContext;
		this.homeController = homeController;
		this.itemService = itemService;		
	}
	
	/**
	 * @see: https://www.baeldung.com/java-beforeall-afterall-non-static
	 * @see: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/TestInstance.html
	 */
	@BeforeAll
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	    existingItemNum = itemService.getAll().size();
	    // Add items to the database to use for testing if they don't already exist.
		LocalDateTime now = LocalDateTime.now();
		String itemName = "testItemName1";
		item1 = new Item(itemName, "testCondition1", BestOption.RECYCLING, "testSpecialInstrution1", "", now);
		item2 = new Item(itemName, "testCondition2", BestOption.DROPOFF, "testSpecialInstrution2", "testNote2", now);
		// Add items to the database to use for testing if they don't already exist.
		item1 = itemService.add(item1, 0);
		item2 = itemService.add(item2, 0);
				
		toAdd = new Item("testItemToAdd", "", BestOption.GARBAGE, "", "", now);
		toDelete = new Item("testItemToDelete", "testConditionToDelete", BestOption.DROPOFF, "testSpecialInstructionToDelete", "testNoteToDelete", now);
		itemService.add(toDelete, 0);
	    		
	}
	
	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}
	
	@Test
	void testGetAllItems() {
		List<Item> actual = itemService.getAll();
//		actual.forEach(System.out::println);
		assertNotNull(actual);
		assertEquals(4 + existingItemNum, actual.size());
	}
	
	@Test
	void testFindItemByName() {
		Item[] expected = {item1, item2};		
		List<Item> actualList = itemService.findByName(item1.getName());
		
		boolean result = true;
		int ii = 0;
		while (result && ii < expected.length) {
			result = actualList.remove(expected[ii]);
			ii++;
		}
		
		assertTrue(result);
	}

	@Test
	void testFindItemByNameAndState() {		
		Item expected = item1; 				
		Item actual = itemService.findByNameAndState(item1.getName(), item1.getCondition());
		
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@MethodSource("provideItemsToTestAddItem")
	void testAdd(Item item, boolean expected) throws ItemAlreadyExistsException  {
		if(!expected) {
	    	assertThrows(ItemAlreadyExistsException.class, () -> {
				itemService.add(item, 0);
			});
	    } else {
	    	itemService.add(item, 0);
	    	assertNotNull(itemService.findByNameAndState(item.getName(), item.getCondition()));
	    }
	}
	private Stream<Arguments> provideItemsToTestAddItem() {
		return Stream.of(
				Arguments.of(toAdd, true),
				Arguments.of(item1, false),
				Arguments.of(item2, false)
				);
	}

	@Test
	void testUpdateItem() throws ItemAlreadyExistsException {		
		Item expected = itemService.findById(item2.getId());
		expected.setNotes("Contact Waste Management for information on drop-off locations.");
		Item actual = itemService.update(expected);
		assertEquals(expected.getNotes(), actual.getNotes());
	}

	@Test
	void testDeleteItem() {				
		Item expected = itemService.findById(toDelete.getId());
		itemService.delete(expected.getId());
		assertNull(itemService.findById(toDelete.getId()));
	}
	
	@AfterAll
	void clearSetup() {
		itemService.delete(item1.getId());
		itemService.delete(item2.getId());
		itemService.delete(toAdd.getId());
	}
}
