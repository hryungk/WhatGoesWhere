/*
 * Filename: StudentCourseId.java
* Author: Stefanski
* 02/25/2020 
 */
package org.perscholas.whatgoeswhere.models;

import java.io.Serializable;

/**
* Key class for composite key in User_Item table
 * @author Helen Kim
 *
 */
public class UserItemID implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private int itemId;

	public UserItemID() {
	}

	public UserItemID(String userId, int itemId) {
		this.userId = userId;
		this.itemId = itemId;
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
		UserItemID other = (UserItemID) obj;
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
