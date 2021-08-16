package org.perscholas.whatgoeswhere.security;

import java.util.Collection;
import java.util.Collections;

import org.perscholas.whatgoeswhere.models.Credential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CurrentCredential implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Credential credential;
	
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
