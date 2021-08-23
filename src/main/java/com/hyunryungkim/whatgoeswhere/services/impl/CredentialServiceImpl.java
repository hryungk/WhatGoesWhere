package com.hyunryungkim.whatgoeswhere.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.repositories.CredentialRepository;
import com.hyunryungkim.whatgoeswhere.services.CredentialService;

/**
 * A Service class for Credential class
 * 
 * @author Hyunryung Kim
 *
 */
@Service
public class CredentialServiceImpl implements CredentialService {
	/**
	 * Repository class for Credential class
	 */
	private CredentialRepository credentialRepository;
	/**
	 * Password encoder for the Credential's password
	 */
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Class constructor accepting fields
	 * 
	 * @param credentialRepository a CredentialRepository object for DAO methods
	 * @param passwordEncoder a PasswordEncoder object to Bcrypt encode the password
	 */
	@Autowired // inject into this class from the Spring framework
	public CredentialServiceImpl(CredentialRepository credentialRepository, PasswordEncoder passwordEncoder) {
		this.credentialRepository = credentialRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public List<Credential> getAll() {
		return credentialRepository.getAll();
	}

	@Override
	public Credential findById(int id) {
		return credentialRepository.findById(id);
	}
	
	@Override
	public Credential findByUsername(String username) {	
		return credentialRepository.findByUsername(username);
	}
	
	@Override
	public Credential add(Credential credential) throws CredentialAlreadyExistsException {
		credential.setPassword(passwordEncoder.encode(credential.getPassword()));
		return credentialRepository.add(credential);
	}

	@Override
	public boolean delete(Credential credential) throws CredentialNotFoundException {
		return credentialRepository.delete(credential);
	}
	
	@Override
	public Credential update(Credential credential) throws CredentialNotFoundException {
		credential.setPassword(passwordEncoder.encode(credential.getPassword())); 
		return credentialRepository.update(credential);
	}

	@Override
	public Credential findByUsernameAndPassword(String username, String password) throws CredentialNotFoundException {
		return credentialRepository.findByUsernameAndPassword(username, password);
	}
	
	@Override
	public boolean checkIfValidOldPassword(String oldPassword, String newPassword) {
		return passwordEncoder.matches(oldPassword, newPassword);
	}
}
