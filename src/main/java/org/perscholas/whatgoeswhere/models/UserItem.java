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
@NamedQuery(name="findItemsByUser", query="SELECT ui FROM UserItem ui WHERE ui.userId = :id")
@NamedQuery(name="findByItemId", query="SELECT ui FROM UserItem ui WHERE ui.itemId = :id")
public class UserItem {
	@Id
	@Column(name="user_id")
	private String userId;

	@Id
	@Column(name="item_id")
	private int itemId;

	public UserItem() {
		userId = "";
		itemId = 0;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}	

}