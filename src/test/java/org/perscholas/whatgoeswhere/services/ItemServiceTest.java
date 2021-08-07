package org.perscholas.whatgoeswhere.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Matchers.anyString;

import java.time.LocalDateTime;
import java.util.List;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepositoryI;
import org.perscholas.whatgoeswhere.services.impl.ItemService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemServiceTest {
	private ItemService itemService;
	private ItemRepositoryI itemRepositoryI;
	private Item item1, item2, item3;
	
	
	@BeforeAll
	void setup() {
		itemRepositoryI = Mockito.mock(ItemRepositoryI.class);
		itemService = new ItemService(itemRepositoryI);
		
		LocalDateTime now = LocalDateTime.now();
		item1 = new Item("Apple", "", BestOption.Composting, "No plastic should go in your compost cart.", "", now);
		item2 = new Item("Apple", "", BestOption.Composting, "No glass should go in your compost cart.", "", now);
		item3 = new Item("Aluminum tray", "Clean", BestOption.Garbage, "", "", now);
		
	}

	@Test
	void testGetAllItems() {
		Mockito.when(itemRepositoryI.findAll()).thenReturn(List.of(item1, item2, item3));
		
		List<Item> actualList = itemService.getAllItems();
		Item[] expected = {item1, item2, item3};
		Item[] actual = new Item[actualList.size()];
		actual = actualList.toArray(actual);
		assertArrayEquals(expected, actual);		
	}
	
	@Test
	void testFindItemByName() {
		Mockito.when(itemRepositoryI.findByName(anyString())).thenReturn(List.of(item1, item2));
		Item[] expected = {item1, item2};
		
		List<Item> actualList = itemService.findItemsByName(item1.getName());		
		Item[] actual = new Item[actualList.size()];
		actual = actualList.toArray(actual);
		
		assertArrayEquals(expected, actual);		
	}
	
	@Test
	void testFindItemByNameAndState() {
		String input1 = "Apple";
		String input2 = "Core";
		LocalDateTime now = LocalDateTime.now();
		Mockito.when(itemRepositoryI.findByNameAndCondition(anyString(),anyString())).thenReturn(
				new Item(input1, input2, BestOption.Composting,
						"No plastic, glass, metal, liquids, cooking oil, or pet waste should go in your compost cart.", "", now));
		
		Item actual = itemService.findItemByNameAndState(input1, input2);
		String expected = "Food & Yard Waste";
		assertEquals(expected, actual.getBestOption().getValue());
	}	
}
