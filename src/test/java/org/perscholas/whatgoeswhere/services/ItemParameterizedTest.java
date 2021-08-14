package org.perscholas.whatgoeswhere.services;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Item;


class ItemParameterizedTest {
	@ParameterizedTest
	@EnumSource(BestOption.class)
	void testAddItemsWithAllBestOptions(BestOption bestOption) {
		Item item = new Item("", "", bestOption, "", "", LocalDateTime.now());
		assertEquals(bestOption, item.getBestOption());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Apple", "Milk jar", "Plastic drinking straw"})
	void testAddItemsWithNames(String name) {
		Item item = new Item(name, "", null, "", "", LocalDateTime.now());
		assertEquals(name, item.getName());
	}
}