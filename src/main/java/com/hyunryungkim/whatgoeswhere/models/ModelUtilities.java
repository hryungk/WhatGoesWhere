package com.hyunryungkim.whatgoeswhere.models;

public interface ModelUtilities {
	/**
	 * A string of persistence-unit name for JPA to inject into entity manager factory
	 */
	String PERSIST_UNIT_NAME = "WhatGoesWhere";
//	String ROOT_DIRECTORY = "KimHyunryung_WhatGoesWhere_CaseStudy";
	String ROOT_DIRECTORY = "WhatGoesWhere";
	String DB_DRIVER = "org.mariadb.jdbc.Driver";
	
	public final class Item {
		private Item() {}
		public static final String NAME_FIND_ALL = "Item.findAll";
		public static final String NAME_FINDBY_NAME = "Item.findByName";
		public static final String NAME_FINDBY_NAME_STATE = "Item.findByNameAndState";
		
		static final String QUERY_FIND_ALL = "SELECT i FROM Item i ORDER BY i.name";
		static final String QUERY_FINDBY_NAME = "SELECT i FROM Item i WHERE i.name LIKE ?1 ORDER BY i.name";
		static final String QUERY_FINDBY_NAME_STATE = "SELECT i FROM Item i WHERE i.name = ?1 AND i.condition = ?2";
	}
	
	public final class Credential {
		private Credential() {}
		public static final String NAME_FIND_ALL = "Credential.findAll";
		public static final String NAME_FINDBY_USERNAME = "Credential.findByUsername";
		public static final String NAME_FINDBY_USERNAME_PASSWORD = "Credential.findByUsernameAndPassword"; 
		
		public static final String QUERY_FIND_ALL = "SELECT c FROM Credential c ORDER BY c.username";
		public static final String QUERY_FINDBY_USERNAME = "SELECT c FROM Credential c WHERE c.username = ?1";
		public static final String QUERY_FINDBY_USERNAME_PASSWORD = "SELECT c FROM Credential c WHERE c.username = ?1 AND c.password = ?2";
	}
	
	public final class User {
		private User() {}
		public static final String NAME_FIND_ALL = "User.findAll";
		public static final String NAME_FINDBY_EMAIL = "User.findByEmail";
		
		static final String QUERY_FIND_ALL = "SELECT u FROM User u";
		static final String QUERY_FINDBY_EMAIL = "SELECT u FROM User u WHERE u.email = ?1";
	}
	
	public final class UserItem {
		private UserItem() {}
		public static final String NAME_FIND_ALL = "UserItem.findAll";
		public static final String NAME_FINDBY_USERID = "UserItem.findByUserId";
		public static final String NAME_FINDBY_ITEMID = "UserItem.findByItemId";
		
		static final String QUERY_FIND_ALL = "SELECT ui FROM UserItem ui";
		static final String QUERY_FINDBY_USERID = "SELECT ui FROM UserItem ui WHERE ui.userId = ?1";
		static final String QUERY_FINDBY_ITEMID = "SELECT ui FROM UserItem ui WHERE ui.itemId = ?1";
	}
}
