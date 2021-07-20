package org.perscholas.whatgoeswhere.runner;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepository;
import org.perscholas.whatgoeswhere.services.ItemService;


public class MainRunner {

	public static void main(String[] args) {
		ItemRepository itemRepository = new ItemRepository();
		ItemService itemService = new ItemService(itemRepository);
		List<Item> items = itemService.getAllItems();
		items.forEach(System.out::println);
	}
}
