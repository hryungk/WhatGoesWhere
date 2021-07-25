package org.perscholas.whatgoeswhere.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User {

	@Id
	@Column(name="id", length=50)
	private String id; // User ID
	@Column(name="password", length=50, nullable=false)
	private String password; // User password
	@Column(name="email", length=50, nullable=false) 
	private String email; // User's email
	@Column(name="name", length=50)
	private String name; // User's full name	
	@OneToMany(targetEntity = Item.class, cascade=CascadeType.ALL)
	@JoinTable(
			name = "User_Item",
			joinColumns = 
				{ @JoinColumn(name = "user_id", referencedColumnName = "id")}, 
			inverseJoinColumns = 
				{ @JoinColumn(name = "item_id", referencedColumnName = "id")})
	private List<Item> items; // A list of items the user has added to the system
	
	public User() {
		super();
		id = "";		
		password = "";		
		email = "";
		name = "";
		items = new ArrayList<>();
	}
	public User(String id, String password, String email, String name, List<Item> items) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.name = name;		
		this.items = items;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
	
}
