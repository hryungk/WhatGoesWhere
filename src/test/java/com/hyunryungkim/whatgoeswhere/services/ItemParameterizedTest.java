package com.hyunryungkim.whatgoeswhere.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import com.hyunryungkim.whatgoeswhere.config.WebAppConfig;
import com.hyunryungkim.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.models.BestOption;
import com.hyunryungkim.whatgoeswhere.models.Item;


@ExtendWith(SpringExtension.class)  // This doesn't really change.
@ContextConfiguration(classes = { WebAppConfig.class })
@WebAppConfiguration("WebContent") // Letting it know where web content is (folder name)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemParameterizedTest {
	private ItemService itemService;
	private List<Integer> itemIds;

	@Autowired
	public ItemParameterizedTest(ItemService itemService) {
		this.itemService = itemService;		
		itemIds = new ArrayList<>();
	}
	
	@ParameterizedTest
	@EnumSource(BestOption.class)
	void testAddItemsWithAllBestOptions(BestOption bestOption) throws ItemAlreadyExistsException {
		String name = "testName"+bestOption.getValue();
		Item item = new Item(name, "testCondition", bestOption, "testSpecialInstrution", "testNote", LocalDateTime.now());
		itemService.add(item, 0);
		Item actual = itemService.findByNameAndState(name, "testCondition");
		itemIds.add(actual.getId());
		
		assertEquals(bestOption, actual.getBestOption());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"testItemName1", "test ItemName2", "test Item Name 3"})
	void testAddItemsWithNames(String name) throws ItemAlreadyExistsException {
		Item item = new Item(name, "testCondition", BestOption.GARBAGE, "testSpecialInstrution", "testNote", LocalDateTime.now());
		itemService.add(item, 0);
		Item actual = itemService.findByNameAndState(name, "testCondition");
		itemIds.add(actual.getId());
		assertEquals(name, actual.getName());
	}
	
	@AfterAll
	void clearSetup() {
		itemIds.forEach(itemService::delete);
	}
}