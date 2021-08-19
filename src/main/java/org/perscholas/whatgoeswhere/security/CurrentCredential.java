package org.perscholas.whatgoeswhere.security;

import java.util.Collection;
import java.util.Collections;

import org.perscholas.whatgoeswhere.models.Credential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementing UserDetails to make use of the project's own user definition
 * Credential is the class that stores username and password so we inject the
 * Credential for the Spring Security user.
 * 
 * @author Hyunryung Kim
 *
 */
public class CurrentCredential implements UserDetails {

	private static final long serialVersionUID = 1L;
	/**
	 * A user Credential to be used for Spring Security
	 */
	private Credential credential;
	
	/**
	 * Class constructor accepting a credential field
	 * 
	 * @param credential a Credential object to be used as a user for Spring Security
	 */
	public CurrentCredential(Credential credential) {
		this.credential = credential;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(credential.getUserRole()));
	}

	@Override
	public String getPassword() {
		return credential.getPassword();
	}

	@Override
	public String getUsername() {
		return  credential.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
