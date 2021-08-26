package com.hyunryungkim.whatgoeswhere.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;

class ModelsTest {
	
	@Test
	void testAddItem() {		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager manager = emfactory.createEntityManager();
		manager.getTransaction().begin();
		
		LocalDateTime now = LocalDateTime.now();
		String name = "Apple";		
		Item expected = new Item(name, "", BestOption.COMPOSTING,
				"No plastic, glass, metal, liquids, cooking oil, or pet waste should go in your compost cart.", "", now);
		manager.persist(expected);		
		manager.getTransaction().commit();
		
		Item actual = manager.find(Item.class, expected.getId());		
		manager.close();
		emfactory.close();
		
		assertEquals(expected, actual);
	}	
	
	@Test
	void testAddUser() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager manager = emfactory.createEntityManager();
		manager.getTransaction().begin();
		
		String userId = "HoppyCat";		
		User expected = new User("hoppycat@email.com", "Helen", "Kim", LocalDate.now(), new ArrayList<Item>());
		manager.persist(expected);		
		manager.getTransaction().commit();
		
		User actual = manager.find(User.class, userId);		
		manager.close();
		emfactory.close();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testAddUserWithItems() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(ModelUtilities.PERSIST_UNIT_NAME);
		EntityManager manager = emfactory.createEntityManager();
		manager.getTransaction().begin();
		
		LocalDateTime now = LocalDateTime.now();
		Item item1 = new Item("Banana", "", BestOption.COMPOSTING,"", "", now);
		manager.persist(item1);
		
		Item item2 = new Item("Aerosole cans", "Empty", BestOption.RECYCLING,"Must be empty.", "", now);
		manager.persist(item2);
		
		List<Item> items = List.of(item1, item2);
		User user1 = new User("davidchi@email.com", "David","Chi", LocalDate.now(), items);
		manager.persist(user1);

		manager.getTransaction().commit();
		
		// A list of item IDs added with the user1
		List<Integer> expected = new ArrayList<>();
		items.forEach(item -> expected.add(item.getId()));
		
		// Query the user1's items from the join table in the database
		TypedQuery<UserItem> query = manager.createNamedQuery("UserName.findByUserId", UserItem.class);
		query.setParameter(1, user1.getId());		
		List<UserItem> userItems = query.getResultList();

		manager.close();
		emfactory.close();
		
		// A list of item IDs added by user1 queried from the join table in the database
		List<Integer> actual = new ArrayList<>();
		userItems.forEach(ui -> actual.add(ui.getItemId()));		
				
		boolean result = true;
		int ii = 0;
		while (result && ii < expected.size()) {
			result = actual.remove(expected.get(ii));
			ii++;
		}
		
		assertTrue(result);
	}
}
