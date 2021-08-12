package org.perscholas.whatgoeswhere.runner;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepository;
import org.perscholas.whatgoeswhere.services.impl.ItemServiceImpl;


public class MainRunner {

	public static void main(String[] args) {
		ItemRepository itemRepository = new ItemRepository();
		ItemServiceImpl itemService = new ItemServiceImpl(itemRepository);
		List<Item> items = itemService.getAllItems();
		items.forEach(System.out::println);
	}
}
