package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Item;

public interface ItemService {
	
	public List<Item> getAllItems();	
	public List<Item> findItemByName(String name);	
	public Item findItemByNameAndState(String name, String state);	
	public Item findItemById(int id);	
	
	public boolean addItem(Item item, int userId);
	public Item add(Item item, int userId);	
	
	public boolean deleteItem(int itemId);	
	public Item update(Item item) ;
	
}
