package org.perscholas.whatgoeswhere.services.impl;

import org.perscholas.whatgoeswhere.models.Credential;
import org.perscholas.whatgoeswhere.repositories.CredentialRepository;
import org.perscholas.whatgoeswhere.security.CurrentCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A Service class for Spring Security's UserDetails class
 * 
 * @author Hyunryung Kim
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	/**
	 * Repository class for Credential class
	 * Used to implement UserDetailsService's DAO methods
	 */		
	private CredentialRepository credentialRepository;
	
	/**
	 * Class constructor accepting fields
	 * 
	 * @param credentialRepository a CredentialRepository object for DAO methods
	 */
	@Autowired
	public UserDetailsServiceImpl(CredentialRepository credentialRepository) {
		this.credentialRepository = credentialRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Credential credential = credentialRepository.findByUsername(username);
		if (credential == null) {
			throw new UsernameNotFoundException("User not found.");
		}
		return new CurrentCredential(credential);
	}

}
