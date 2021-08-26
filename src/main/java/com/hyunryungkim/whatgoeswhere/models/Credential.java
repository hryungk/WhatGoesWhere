package com.hyunryungkim.whatgoeswhere.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A class representing a user credential
 * Stores credential id, username, password(bcrypt), user's role, and associated User
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.User
 */
@Entity
@Table(name="Credentials")
@NamedQuery(name=ModelUtilities.Credential.NAME_FIND_ALL, query=ModelUtilities.Credential.QUERY_FIND_ALL)
@NamedQuery(name=ModelUtilities.Credential.NAME_FINDBY_USERNAME, query=ModelUtilities.Credential.QUERY_FINDBY_USERNAME)
@NamedQuery(name=ModelUtilities.Credential.NAME_FINDBY_USERNAME_PASSWORD, query=ModelUtilities.Credential.QUERY_FINDBY_USERNAME_PASSWORD)
public class Credential {
	/**
	 * An integer representing the credential id, auto-generated
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;	
	/**
	 * A string containing the username
	 */
	@Column(name="username", length=25, nullable=false, unique=true)
	@Size(min=5, max=25, message="Username must be between 5 and 25 characters.")
	@NotNull
	private String username;
	/**
	 * A string containing the password
	 */
	@Column(name="password", length=100, nullable=false)
	@Size(min=10, max=50, message="Password must be between 10 and 50 characters.")
	private String password;
	/**
	 * A string containing the user's role (ROLE_UESR or ROLE_ADMIN)
	 */
	@Column(name="user_role", length=10, nullable=false)
	private String userRole;
	/**
	 * User associated with this credential
	 */
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=false)
	@JoinColumn(name="User_id", nullable=false)
	private User user;
	
	/**
	 * Class constructor initializing all class fields
	 */
	public Credential() {
		super();
		username = "";
		password = "";
		userRole = "ROLE_USER";
		user = new User();
	}

	/**
	 * Class constructor accepting fields
	 * 
	 * @param username a string of this user's username
	 * @param password a string of this user's password
	 * @param user a User associated with this Crednetial
	 * @see com.hyunryungkim.whatgoeswhere.models.User
	 */
	public Credential(String username, String password, User user) {
		super();
		this.username = username;
		this.password = password;
		userRole = "ROLE_USER";
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Credential other = (Credential) obj;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Credential [id=" + id + ", username=" + username + ", password=" + password + ", " + userRole +"]";
	}
	
}
