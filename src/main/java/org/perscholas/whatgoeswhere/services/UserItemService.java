package org.perscholas.whatgoeswhere.services;

import java.util.List;
import org.perscholas.whatgoeswhere.models.UserItem;

public interface UserItemService {

	public List<UserItem> findAll();
	public UserItem findByItemId(int itemId);
	public List<UserItem> findByUserId(int userId);
	public boolean add(int userId, int itemId);
	public void deleteByItemId(int itemId);
	public void deleteByUserId(int userId);
}
