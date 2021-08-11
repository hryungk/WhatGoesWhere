package org.perscholas.whatgoeswhere.repositories;

import org.perscholas.whatgoeswhere.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepositoryI extends CrudRepository<User, String> {

//	@Transactional
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	@Transactional	
	void delete(User user);
	@Modifying
	@Query("delete from UserItem u where u.userId = ?1")
	void deleteUI(String userId);
	
	
//	@Query(value="DELETE FROM USER WHERE userId = ?1", nativeQuery = true)
	void deleteByUsername(String id);
	
}
