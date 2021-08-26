package com.hyunryungkim.whatgoeswhere.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunryungkim.whatgoeswhere.models.UserItem;
import com.hyunryungkim.whatgoeswhere.repositories.UserItemRepository;
import com.hyunryungkim.whatgoeswhere.services.UserItemService;

/**
 * A Service class for UserItem class
 * 
 * @author Hyunryung Kim
 *
 */
@Service
public class UserItemServiceImpl implements UserItemService{	
	/**
	 * Repository class for UserItem class
	 */
	private UserItemRepository uiRepository;
	
	/**
	 * Class constructor accepting fields
	 * 
	 * @param uiRepository a UserItemRepository object for DAO methods 
	 */
	@Autowired
	public UserItemServiceImpl(UserItemRepository uiRepository) {
		this.uiRepository = uiRepository;
	}
	
	@Override
	public List<UserItem> findAll() {
		return uiRepository.findAll();
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
	public boolean deleteByItemId(int itemId) {
		return uiRepository.deleteByItemId(itemId);
	}

	@Override
	public boolean deleteByUserId(int userId) {
		return uiRepository.deleteByUserId(userId);
	}
}
