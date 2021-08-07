package org.perscholas.whatgoeswhere.repositories;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ItemRepositoryI extends CrudRepository<Item, Integer> {

	List<Item> findByName(String name);
	
	Item findByNameAndCondition(String name, String condition);
	
	@Transactional
	void deleteById(int id);
}
