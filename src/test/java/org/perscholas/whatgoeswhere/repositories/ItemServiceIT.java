package org.perscholas.whatgoeswhere.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.perscholas.whatgoeswhere.config.WebAppConfig;
import org.perscholas.whatgoeswhere.controllers.HomeController;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.services.ItemService;
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
class ItemServiceIT {
	private WebApplicationContext webApplicationContext;
	private HomeController homeController;
	private MockMvc mockMvc;
	private ItemService itemService;
	private Item item1;
	private Item item2;

	@Autowired
	public ItemServiceIT(WebApplicationContext webApplicationContext, 
			HomeController homeController, ItemService iService) {
		this.webApplicationContext = webApplicationContext;
		this.homeController = homeController;
		itemService = iService;
		
//		// Add items to the database to use for testing if they don't already exist.
//		LocalDateTime now = LocalDateTime.now();
//		String itemName = "Aerosole cans";
//		item1 = new Item(itemName, "Empty", "Recycling", "Must be empty.", "", now);
//		item2 = new Item(itemName, "full or partially full", "Drop-off - Hazardous Waste", "", "This item is considered hazardous waste and must be disposed of safely.", now);
//		if (itemService.findItemByNameAndState(item1.getName(), item1.getCondition()) == null)
//			itemService.addItem(item1);
//		if (itemService.findItemByNameAndState(item2.getName(), item2.getCondition()) == null)
//			itemService.addItem(item2);
	}
	
	@BeforeEach
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	    
		// Add items to the database to use for testing if they don't already exist.
		LocalDateTime now = LocalDateTime.now();
		String itemName = "Aerosole cans";
		item1 = new Item(itemName, "Empty", "Recycling", "Must be empty.", "", now);
		item2 = new Item(itemName, "full or partially full", "Drop-off - Hazardous Waste", "", "This item is considered hazardous waste and must be disposed of safely.", now);
		
		if(!itemService.addItem(item1))
			item1 = itemService.findItemByNameAndState(item1.getName(), item1.getCondition());
		
		if(!itemService.addItem(item2))
			item2 = itemService.findItemByNameAndState(item2.getName(), item2.getCondition());
		
		// For testing addition, remove the item if it already exists
//		Item toRemove = new Item("Banana", "", "Food & Yard Waste",
//				"No plastic, glass, metal, liquids, cooking oil, or pet waste should go in your compost cart.", "", now);
		Item toRemove = itemService.findItemByNameAndState("Banana", "");
		if (toRemove != null) { // exists in the db
			itemService.deleteItem(toRemove);
		}
	}
	
	@Test
	void testAplicationContext() {
		assertNotNull(homeController);
		assertNotNull(mockMvc);
	}
	
	@Test
	void testGetAllItems() {
		List<Item> actual = itemService.getAllItems();
		actual.forEach(System.out::println);
		assertNotNull(actual);
	}
	
	@Test
	void testFindItemByNameAndState() {		
		Item expected = item1; 				
		Item actual = itemService.findItemByNameAndState(item1.getName(), item1.getCondition());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testFindItemByName() {
		Item[] expected = {item1, item2};		
		
		List<Item> actualList = itemService.findItemByName(item1.getName());
//		Item[] actual = new Item[actualList.size()];
//		actual = actualList.toArray(actual);
//		assertArrayEquals(expected,actual);
		
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
		LocalDateTime now = LocalDateTime.now();
		Item expected = new Item("Banana", "", "Food & Yard Waste",
				"No plastic, glass, metal, liquids, cooking oil, or pet waste should go in your compost cart.", "", now);
		assertTrue(itemService.addItem(expected));		
	}

	@Test
	void testUpdateItem() {		
		System.out.println("\n\nStarting testUpdateItem()\n\n");
		Item expected = itemService.findItemById(item2.getId());
		System.out.println(expected);
		expected.setNotes("Contact Waste Management for information on drop-off locations.");
		assertTrue(itemService.updateItem(expected));
	}

//	@Test
//	void testDeleteItem() {				
//		System.out.println("\n\nStarting testDeleteItem()\n\n");
//		Item expected = itemService.findItemById(item1.getId());
//		assertTrue(itemService.deleteItem(expected));
//	}
}
