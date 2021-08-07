package org.perscholas.whatgoeswhere.repositories;

import org.perscholas.whatgoeswhere.models.User;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepositoryI extends CrudRepository<User, String> {

//	@Transactional
	User findByUsername(String username);
	
	User findByEmail(String email);
	
//	@Transactional	
	void delete(User user);
	
//	@Query(value="DELETE FROM USER WHERE userId = ?1", nativeQuery = true)
	void deleteByUsername(String id);
}
