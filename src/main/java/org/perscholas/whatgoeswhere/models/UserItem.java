/*
 * Filename: UserItem.java
* Author: Helen Kim
* 07/18/2021 
 */
package org.perscholas.whatgoeswhere.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Harry
 *
 */
@Entity // This should be commented out when first creating tables in the database.
@Table(name="User_Item")
@IdClass(UserItemID.class)
@NamedQuery(name="UserItem.findAll", query="SELECT ui FROM UserItem ui")
@NamedQuery(name="UserItem.findByUserId", query="SELECT ui FROM UserItem ui WHERE ui.userId = ?1")
@NamedQuery(name="UserItem.findByItemId", query="SELECT ui FROM UserItem ui WHERE ui.itemId = ?1")
public class UserItem {
	@Id
	@Column(name="user_id")
	private int userId;

	@Id
	@Column(name="item_id")
	private int itemId;

	public UserItem() {
		userId = 0;
		itemId = 0;
	}
	

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