package com.hyunryungkim.whatgoeswhere.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A class representing a user
 * Stores user id, email, first and last name, date the account is created, and a list of Item objects
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.Item
 *
 */
@Entity
@Table(name="Users")
@NamedQuery(name=ModelUtilities.User.NAME_FIND_ALL, query=ModelUtilities.User.QUERY_FIND_ALL)
@NamedQuery(name=ModelUtilities.User.NAME_FINDBY_EMAIL, query=ModelUtilities.User.QUERY_FINDBY_EMAIL)
public class User {
	/**
	 * An integer representing the User id, auto-generated
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	/**
	 * A string containing this user's email
	 */
	@Column(name="email", length=50, nullable=false, unique=true) 
	private String email; // User's email
	/**
	 * A string containing this user's first  name 
	 */
	@Column(name="first_name", length=50, nullable=false)
	private String firstName; // User's first name
	/**
	 * A string containing this user's last name 
	 */
	@Column(name="last_name", length=50, nullable=false)
	private String lastName; // User's last name
	/**
	 * A date containing this user's date of joining the service 
	 */
	@Column(name="joined_date", nullable=false)
	private LocalDate joinedDate; // The date the joining	
	/**
	 * A collection of Items this user had added to the system 
	 */
	@OneToMany(targetEntity = Item.class, cascade=CascadeType.PERSIST, orphanRemoval=false)
	@JoinTable(
			name = "User_Item",
			joinColumns = 
				{ @JoinColumn(name = "User_id", referencedColumnName = "id")}, 
			inverseJoinColumns = 
				{ @JoinColumn(name = "Item_id", referencedColumnName = "id")})
	private List<Item> items; // A list of items the user has added to the system
	
	/**
	 * Class constructor initializing all class fields
	 */
	public User() {
		super();
		email = "";
		firstName = "";
		lastName = "";
		joinedDate = null;
		items = new ArrayList<>();
	}
	/**
	 * Class constructor accepting fields
	 * 
	 * @param email a string of this user's name
	 * @param firstName a string of this user's first name
	 * @param lastName a string of this user's last name
	 * @param joinedDate a date which this user has created the account 
	 * @param items a List of Items this user had added to the database
	 */
	public User(String email, String firstName, String lastName, LocalDate joinedDate, List<Item> items) {
		super();
		this.email = email;
		this.firstName = firstName;		
		this.lastName = lastName;
		this.joinedDate = joinedDate;
		this.items = items;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(LocalDate joinedDate) {
		this.joinedDate = joinedDate;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", joinedDate=" + joinedDate + "]";
	}
	
	
}
