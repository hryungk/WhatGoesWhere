package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.repositories.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserItemService {
	
	private UserItemRepository uiRepository;
	
	@Autowired
	public UserItemService(UserItemRepository uiRepository) {
		this.uiRepository = uiRepository;
	}
	

	public List<UserItem> findAll() {
		return (List<UserItem>) uiRepository.getAll();
	}

	public UserItem findByItemId(int itemId) {
		return uiRepository.findByItemId(itemId);
	}


	public List<UserItem> findByUserId(int userId) {
		return uiRepository.findByUserId(userId);
	}


	public boolean add(int userId, int itemId) {
		return uiRepository.add(userId, itemId);
	}
	

	public void deleteByItemId(int itemId) {
		uiRepository.deleteByItemId(itemId);
	}

	public void deleteByUserId(int userId) {
		uiRepository.deleteByUserId(userId);
	}
}
