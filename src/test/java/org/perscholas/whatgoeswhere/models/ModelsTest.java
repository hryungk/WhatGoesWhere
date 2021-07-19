package org.perscholas.whatgoeswhere.models;

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
	void testItem() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager manager = emfactory.createEntityManager();
		manager.getTransaction().begin();
		
		LocalDateTime now = LocalDateTime.now();
		String name = "Apple";		
		Item expected = new Item(name, "", "Food & Yard Waste",
				"No plastic, glass, metal, liquids, cooking oil, or pet waste should go in your compost cart.", "", now);
		manager.persist(expected);		
		manager.getTransaction().commit();
		
		Item actual = manager.find(Item.class, expected.getId());		
		manager.close();
		emfactory.close();
		
		assertEquals(expected, actual);
	}	
	
	@Test
	void testUser() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager manager = emfactory.createEntityManager();
		manager.getTransaction().begin();
		
		String userId = "HoppyCat";		
		User expected = new User("HoppyCat", "1234helen", "hoppycat@email.com", "Helen Kim", null);
		manager.persist(expected);		
		manager.getTransaction().commit();
		
		User actual = manager.find(User.class, userId);		
		manager.close();
		emfactory.close();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testUserWithItems() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager manager = emfactory.createEntityManager();
		manager.getTransaction().begin();
		
		LocalDateTime now = LocalDateTime.now();
		Item item1 = new Item("Apple", "", "Composting","", "", now);
		manager.persist(item1);
		
		Item item2 = new Item("Aerosole cans", "Empty", "Recycling","Must be empty.", "", now);
		manager.persist(item2);
		
		List<Item> items = List.of(item1, item2);
		User user1 = new User("doomgeek", "1234dpc", "davidchi@email.com", "David Chi", items);
		manager.persist(user1);

		manager.getTransaction().commit();
		
		// A list of item IDs added with the user1
		List<Integer> expected = new ArrayList<>();
		items.forEach(item -> expected.add(item.getId()));
		
		// Query the user1's items from the join table in the database
		TypedQuery<UserItem> query = manager.createNamedQuery("ItemsByUser", UserItem.class);
		query.setParameter("id", user1.getId());		
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
