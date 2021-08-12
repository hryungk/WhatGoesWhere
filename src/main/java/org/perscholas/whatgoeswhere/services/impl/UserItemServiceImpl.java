package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.repositories.UserItemRepository;
import org.perscholas.whatgoeswhere.services.UserItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserItemServiceImpl implements UserItemService{	
	private UserItemRepository uiRepository;
	
	@Autowired
	public UserItemServiceImpl(UserItemRepository uiRepository) {
		this.uiRepository = uiRepository;
	}
	
	@Override
	public List<UserItem> findAll() {
		return uiRepository.getAll();
	}

	@Override
	public UserItem findByItemId(int itemId) {
		return uiRepository.findByItemId(itemId);
	}

	@Override
	public List<UserItem> findByUserId(int userId) {
		return uiRepository.findByUserId(userId);
	}

	@Override
	public boolean add(int userId, int itemId) {
		return uiRepository.add(userId, itemId);
	}
	
	@Override
	public void deleteByItemId(int itemId) {
		uiRepository.deleteByItemId(itemId);
	}

	@Override
	public void deleteByUserId(int userId) {
		uiRepository.deleteByUserId(userId);
	}
}
