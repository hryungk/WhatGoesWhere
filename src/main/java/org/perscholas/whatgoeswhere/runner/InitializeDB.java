package org.perscholas.whatgoeswhere.runner;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;


public class InitializeDB {
public static void main(String[] args) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WhatGoesWhere");
		EntityManager manager = emfactory.createEntityManager();
		manager.getTransaction().begin();
		
		LocalDateTime now = LocalDateTime.now();
		Item item1 = new Item("Apple", "Any", "Composting","NA", "NA", now);
		manager.persist(item1);
		
		Item item2 = new Item("Aerosole Cans", "Empty", "Recycling","Must Be Empty", "NA", now);
		manager.persist(item2);
		
		List<Item> itemList = new ArrayList<>();
		itemList.add(item1);
		itemList.add(item2);
		
		User user1 = new User("HoppyCat", "1234helen", "hoppycat@email.com", "Helen Kim", itemList);
		manager.persist(user1);

		manager.getTransaction().commit();
		manager.close();
		emfactory.close();
		
	}
}
