package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;
import java.util.Optional;

import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.repositories.ItemRepositoryI;
import org.perscholas.whatgoeswhere.services.ItemServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService implements ItemServiceI {
	private ItemRepositoryI itemRepositoryI;
	
	@Autowired // inject into this class from the Spring framework
	public ItemService(ItemRepositoryI itemRepositoryI) {
		this.itemRepositoryI = itemRepositoryI;
	}
	
	@Override
	public List<Item> getAllItems() {
		return (List<Item>) itemRepositoryI.findAll();
	}
	
	@Override
	public List<Item> findItemsByName(String name) {
		return itemRepositoryI.findByName(name);
	}
	
	@Override
	public Item findItemByNameAndState(String name, String state) {
		return itemRepositoryI.findByNameAndCondition(name, state);
	}
	
	@Override
	public Item findItemById(int id) {
		Optional<Item> optItem = itemRepositoryI.findById(id);
		if (optItem.isPresent()) {
			return optItem.get();
		}
		return null;
	}
	
//	@Override
//	public boolean addItem(Item item, String userId) {
//		return itemRepositoryI.save(item).getId() != 0;
//	}
	
	@Override
	public Item add(Item item) {
		return itemRepositoryI.save(item);
	}
	
	@Override
	public void deleteById(int id) {
		System.out.println("delete item with id " + id);
		itemRepositoryI.deleteById(id);
	}
	@Override
	public void delete(Item item) {
		itemRepositoryI.delete(item);
	}
	
	@Override
	public Item update(Item item) {
		return itemRepositoryI.save(item);
	}
}
