package org.perscholas.whatgoeswhere.models;

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

@Entity
@Table(name="Users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email = ?1")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="email", length=50, nullable=false, unique=true) 
	private String email; // User's email
	@Column(name="fname", length=50)
	private String firstName; // User's first name	
	@Column(name="lname", length=50)
	private String lastName; // User's last name	
	@Column(name="joinedDate", nullable=false)
	private LocalDate joinedDate; // The date the joining	
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
		email = "";
		firstName = "";
		lastName = "";
		joinedDate = null;
		items = new ArrayList<>();
	}
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
