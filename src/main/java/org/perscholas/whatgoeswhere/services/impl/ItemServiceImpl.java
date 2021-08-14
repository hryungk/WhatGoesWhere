package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.exceptions.ItemAlreadyExistsException;
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
	public Item add(Item item, int userId) throws ItemAlreadyExistsException {
		return itemRepository.add(item, userId);
	}
	
	@Override
	public boolean delete(int itemId) {
		return itemRepository.delete(itemId);
	}
	
	@Override
	public Item update(Item item) throws ItemAlreadyExistsException {
		return itemRepository.update(item);
	}
}
