package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.models.UserItemID;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserItemRepositoryI extends CrudRepository<UserItem, UserItemID> {
	
//	@Query("select u from UserItem u where u.itemId = ?1")
	UserItem findByItemId(int itemId);
	
	List<UserItem> findByUserId(String userId);
	
//	@Modifying
//	@Query("delete from UserItem u where u.itemId = ?1")
	@Transactional
	void deleteByItemId(int itemId);
	
//	@Modifying
//	@Query("delete from UserItem u where u.userId = ?1")
	@Transactional
	void deleteByUserId(String userId);
}
