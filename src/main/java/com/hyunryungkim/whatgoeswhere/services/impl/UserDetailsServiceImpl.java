package com.hyunryungkim.whatgoeswhere.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.repositories.CredentialRepository;
import com.hyunryungkim.whatgoeswhere.security.CurrentCredential;

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
