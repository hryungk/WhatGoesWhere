package org.perscholas.whatgoeswhere.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@NamedQuery(name="User.findByName", query="SELECT u FROM User u WHERE u.username = ?1")
@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email = ?1")
public class User {

	@Id
	@Column(name="username", length=50)
	private String username; // User name (unique ID)
	@Column(name="password", length=50, nullable=false)
	private String password; // User password
	@Column(name="email", length=50, nullable=false, unique=true) 
	private String email; // User's email
	@Column(name="fname", length=50)
	private String firstName; // User's first name	
	@Column(name="lname", length=50)
	private String lastName; // User's last name	
	@OneToMany(targetEntity = Item.class, cascade=CascadeType.ALL)
	@JoinTable(
			name = "User_Item",
			joinColumns = 
				{ @JoinColumn(name = "user_id", referencedColumnName = "username")}, 
			inverseJoinColumns = 
				{ @JoinColumn(name = "item_id", referencedColumnName = "id")})
	private List<Item> items; // A list of items the user has added to the system
	
	public User() {
		super();
		username = "";		
		password = "";		
		email = "";
		firstName = "";
		lastName = "";
		items = new ArrayList<>();
	}
	public User(String id, String password, String email, String firstName, String lastName, List<Item> items) {
		super();
		this.username = id;
		this.password = password;
		this.email = email;
		this.firstName = firstName;		
		this.lastName = lastName;
		this.items = items;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) { 
		this.username = username;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLasttName(String lastName) {
		this.lastName = lastName;
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
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}	
	
}
