package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepository;
import org.perscholas.whatgoeswhere.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A Service class for Item class
 * 
 * @author Hyunryung Kim
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	/**
	 * Repository class for Item class
	 */
	private ItemRepository itemRepository;
	
	/**
	 * Class constructor accepting fields
	 * 
	 * @param itemRepository an ItemRepository object for DAO methods
	 */
	@Autowired // inject into this class from the Spring framework
	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Override
	public List<Item> getAll() {
		return itemRepository.getAll();
	}
	
	@Override
	public List<Item> findByName(String name) {
		return itemRepository.findByName(name);
	}
	
	@Override
	public Item findByNameAndState(String name, String state) {
		return itemRepository.findByNameAndState(name, state);
	}
	
	@Override
	public Item findById(int id) {
		return itemRepository.findById(id);
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
