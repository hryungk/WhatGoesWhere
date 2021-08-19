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
import org.mockito.Mockito;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepository;
import org.perscholas.whatgoeswhere.services.impl.ItemServiceImpl;

class ItemServiceTest {
	private static ItemService itemService;
	private static ItemRepository itemRepository;
	
	@BeforeAll
	static void setup() {
		itemRepository = Mockito.mock(ItemRepository.class);
		itemService = new ItemServiceImpl(itemRepository);
	}

	@Test
	void testGetAllItems() {
		String input1 = "Apple";
		LocalDateTime now = LocalDateTime.now();
		Item item1 = new Item(input1, "", BestOption.COMPOSTING, "No plastic should go in your compost cart.", "", now);
		Item item2 = new Item(input1, "", BestOption.COMPOSTING, "No glass should go in your compost cart.", "", now);
		Item item3 = new Item("Aluminum tray", "Clean", BestOption.GARBAGE, "", "", now);
		Mockito.when(itemRepository.getAll()).thenReturn(List.of(item1, item2, item3));
		
		List<Item> actualList = itemService.getAll();
		Item[] expected = {item1, item2, item3};
		Item[] actual = new Item[actualList.size()];
		actual = actualList.toArray(actual);
		assertArrayEquals(expected, actual);		
	}
	
	@Test
	void testFindItemByName() {
		String input1 = "Apple";
		LocalDateTime now = LocalDateTime.now();
		Item item1 = new Item(input1, "", BestOption.COMPOSTING, "No plastic should go in your compost cart.", "", now);
		Item item2 = new Item(input1, "", BestOption.COMPOSTING, "No glass should go in your compost cart.", "", now);
		Mockito.when(itemRepository.findByName(anyString())).thenReturn(List.of(item1, item2));
		Item[] expected = {item1, item2};
		
		List<Item> actualList = itemService.findByName(input1);		
		Item[] actual = new Item[actualList.size()];
		actual = actualList.toArray(actual);
		
		assertArrayEquals(expected, actual);		
	}
	
	@Test
	void testFindItemByNameAndState() {
		String input1 = "Apple";
		String input2 = "Core";
		LocalDateTime now = LocalDateTime.now();
		Mockito.when(itemRepository.findByNameAndState(anyString(),anyString())).thenReturn(
				new Item(input1, input2, BestOption.COMPOSTING,
						"No plastic, glass, metal, liquids, cooking oil, or pet waste should go in your compost cart.", "", now));
		
		Item actual = itemService.findByNameAndState(input1, input2);
		String expected = "Food & Yard Waste";
		assertEquals(expected, actual.getBestOption().getValue());
	}	
	
}
