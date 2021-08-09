package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
	private ItemRepository itemRepository;
	
	@Autowired // inject into this class from the Spring framework
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public List<Item> getAllItems() {
		return itemRepository.getAllItems();
	}
	
	public List<Item> findItemByName(String name) {
		return itemRepository.findItemByName(name);
	}
	
	public Item findItemByNameAndState(String name, String state) {
		return itemRepository.findItemByNameAndState(name, state);
	}
	
	public Item findItemById(int id) {
		return itemRepository.findItemById(id);
	}
	
	public boolean addItem(Item item, int userId) {
		return itemRepository.addItem(item, userId);
	}
	public Item add(Item item, int userId) {
		return itemRepository.add(item, userId);
	}
	
	public boolean deleteItem(int itemId) {
		return itemRepository.deleteItem(itemId);
	}
	
	public boolean updateItem(Item item) {
		return itemRepository.updateItem(item);
	}
	public Item update(Item item) {
		return itemRepository.update(item);
	}
}
