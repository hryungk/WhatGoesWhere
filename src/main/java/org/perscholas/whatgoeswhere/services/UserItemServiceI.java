package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.UserItem;

public interface UserItemServiceI {

	public List<UserItem> findAll();
	
	public UserItem findByItemId(int itemId);
	
	public List<UserItem> findByUserId(String userId);
	
	public UserItem add(UserItem useritem);
	
	public void deleteByItemId(int itemId);
	
	public void deleteByUserId(String userId);
}
