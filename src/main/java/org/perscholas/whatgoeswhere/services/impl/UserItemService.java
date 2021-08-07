package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.repositories.UserItemRepositoryI;
import org.perscholas.whatgoeswhere.services.UserItemServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserItemService implements UserItemServiceI {

	private UserItemRepositoryI uiRepositoryI;
	
	@Autowired
	public UserItemService(UserItemRepositoryI uiRepositoryI) {
		this.uiRepositoryI = uiRepositoryI;
	}
	
	@Override
	public List<UserItem> findAll() {
		return (List<UserItem>) uiRepositoryI.findAll();
	}
	@Override
	public UserItem findByItemId(int itemId) {
		return uiRepositoryI.findByItemId(itemId);
	}

	@Override
	public List<UserItem> findByUserId(String userId) {
		return uiRepositoryI.findByUserId(userId);
	}

	@Override
	public UserItem add(UserItem useritem) {
		return uiRepositoryI.save(useritem);
	}
	
	@Override
	public void deleteByItemId(int itemId) {
		uiRepositoryI.deleteByItemId(itemId);
	}

	@Override
	public void deleteByUserId(String userId) {
		uiRepositoryI.deleteByUserId(userId);
	}

}
