package org.perscholas.whatgoeswhere.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Credentials")
@NamedQuery(name="Credential.findAll", query="SELECT c FROM Credential c")
@NamedQuery(name="Credential.findByUsername", query="SELECT c FROM Credential c WHERE c.username = ?1")
@NamedQuery(name="Credential.findByUsernameAndPassword", query="SELECT c FROM Credential c WHERE c.username = ?1 AND c.password = ?2")
public class Credential {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="username", length=50, nullable=false, unique=true)
	private String username;
	@Column(name="password", length=50, nullable=false) 
	private String password;
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=false)
	private User user;
	
	public Credential() {
		super();
		username = "";
		password = "";
		user = new User();
	}

	public Credential(String username, String password, User user) {
		super();
		this.username = username;
		this.password = password;
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
		return "Credential [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
}
