package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Item;

public interface ItemServiceI {

	public List<Item> getAllItems();
	
	public List<Item> findItemsByName(String name);
	
	public Item findItemByNameAndState(String name, String state);
	
	public Item findItemById(int id);
	
//	public boolean addItem(Item item, String userId);
	public Item add(Item item);
	
	public void deleteById(int id);
	public void delete(Item item);
	
	public Item update(Item item);
}
