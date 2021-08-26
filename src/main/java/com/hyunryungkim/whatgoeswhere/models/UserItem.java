/*
 * Filename: UserItem.java
* Author: Helen Kim
* 07/18/2021 
 */
package com.hyunryungkim.whatgoeswhere.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * A class for join table between the User and the Item table
 * Stores user id from User and item id from Item
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.User
 * @see com.hyunryungkim.whatgoeswhere.models.Item
 *
 */
@Entity
@Table(name="User_Item")
@IdClass(UserItemID.class)
@NamedQuery(name=ModelUtilities.UserItem.NAME_FIND_ALL, query=ModelUtilities.UserItem.QUERY_FIND_ALL)
@NamedQuery(name=ModelUtilities.UserItem.NAME_FINDBY_USERID, query=ModelUtilities.UserItem.QUERY_FINDBY_USERID)
@NamedQuery(name=ModelUtilities.UserItem.NAME_FINDBY_ITEMID, query=ModelUtilities.UserItem.QUERY_FINDBY_ITEMID)
public class UserItem {
	/**
	 * An integer representing the User id
	 */
	@Id
	@Column(name="User_id")
	private int userId;
	/**
	 * An integer representing the Item id
	 */
	@Id
	@Column(name="Item_id")
	private int itemId;

	/**
	 * Class constructor initializing all class fields
	 */
	public UserItem() {
		userId = 0;
		itemId = 0;
	}	
	/**
	 * Class constructor accepting fields
	 * 
	 * @param userId an integer representing a User's id
	 * @param itemId an integer representing a Item's id
	 * @see com.hyunryungkim.whatgoeswhere.models.User
	 * @see com.hyunryungkim.whatgoeswhere.models.Item
	 */
	public UserItem(int userId, int itemId) {
		super();
		this.userId = userId;
		this.itemId = itemId;
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemId;
		result = prime * result + userId;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserItem other = (UserItem) obj;
		if (itemId != other.itemId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}


}