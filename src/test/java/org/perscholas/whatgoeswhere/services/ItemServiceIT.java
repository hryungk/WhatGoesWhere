package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
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
import org.perscholas.whatgoeswhere.services.impl.ItemService;
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
	    
		// Add items to the database to use for testing if they don't already exist.
		LocalDateTime now = LocalDateTime.now();
		String itemName = "Aerosole cans";
		item1 = new Item(itemName, "Empty", BestOption.Recycling, "Must be empty.", "", now);
		item2 = new Item(itemName, "full or partially full", BestOption.DropOff, "Hazardous waste", "This item is considered hazardous waste and must be disposed of safely.", now);
		
		itemService.add(item1);
		itemService.add(item2);
				
		toAdd = new Item("Chip bags", "", BestOption.Garbage, "", "", now);
		toDelete = new Item("Bread bags", "Clean", BestOption.DropOff, "Must be clean and dry", "Drop off at local grocery stores", now);
		itemService.add(toDelete);
	}
	
	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}
	
	@Test
	void testGetAllItems() {
		List<Item> actual = itemService.getAllItems();
//		actual.forEach(System.out::println);
		assertNotNull(actual);
		assertEquals(4, actual.size());
	}
	
	@Test
	void testFindItemByNameAndState() {		
		Item expected = item1; 				
		Item actual = itemService.findItemByNameAndState(item1.getName(), item1.getCondition());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testFindItemsByName() {
		Item[] expected = {item1, item2};		
		List<Item> actualList = itemService.findItemsByName(item1.getName());
		
		boolean result = true;
		int ii = 0;
		while (result && ii < expected.length) {
			result = actualList.remove(expected[ii]);
			ii++;
		}
		
		assertTrue(result);
	}
	
	@Test
	void testAddItem() {
		// For testing addition, remove the item if it already exists
		Item toRemove = itemService.findItemByNameAndState(toAdd.getName(), toAdd.getCondition());
		if (toRemove != null) { // exists in the db
			itemService.deleteById(toRemove.getId());
		}
		
		Item expected = toAdd;
		Item actual = itemService.add(toAdd);
		assertEquals(expected, actual);		
	}

	@Test
	void testUpdateItem() {		
		Item expected = itemService.findItemById(item2.getId());
		expected.setNotes("Contact Waste Management for information on drop-off locations.");
		Item actual = itemService.update(expected);
		assertEquals(expected.getNotes(), actual.getNotes());
	}

	@Test
	void testDeleteById() {				
		Item expected = itemService.findItemById(toDelete.getId());
		itemService.deleteById(expected.getId());
		assertNull(itemService.findItemById(toDelete.getId()));
	}

	@AfterAll
	void cleanUp() {
		itemService.delete(item1);
		itemService.delete(item2);
		itemService.delete(toAdd);
	}
}
