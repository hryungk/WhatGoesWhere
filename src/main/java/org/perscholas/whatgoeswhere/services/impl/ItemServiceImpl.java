package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepository;
import org.perscholas.whatgoeswhere.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
	private ItemRepository itemRepository;
	
	@Autowired // inject into this class from the Spring framework
	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Override
	public List<Item> getAllItems() {
		return itemRepository.getAllItems();
	}
	
	@Override
	public List<Item> findItemByName(String name) {
		return itemRepository.findItemByName(name);
	}
	
	@Override
	public Item findItemByNameAndState(String name, String state) {
		return itemRepository.findItemByNameAndState(name, state);
	}
	
	@Override
	public Item findItemById(int id) {
		return itemRepository.findItemById(id);
	}
	
	@Override
	public boolean addItem(Item item, int userId) {
		return itemRepository.addItem(item, userId);
	}
	@Override
	public Item add(Item item, int userId) {
		return itemRepository.add(item, userId);
	}
	
	@Override
	public boolean deleteItem(int itemId) {
		return itemRepository.deleteItem(itemId);
	}
	
	@Override
	public Item update(Item item) {
		return itemRepository.update(item);
	}
}
