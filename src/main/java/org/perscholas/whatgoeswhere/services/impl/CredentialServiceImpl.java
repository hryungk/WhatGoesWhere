package org.perscholas.whatgoeswhere.services.impl;

import java.util.List;

import org.perscholas.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import org.perscholas.whatgoeswhere.exceptions.CredentialNotFoundException;
import org.perscholas.whatgoeswhere.models.Credential;
import org.perscholas.whatgoeswhere.repositories.CredentialRepository;
import org.perscholas.whatgoeswhere.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialServiceImpl implements CredentialService {
	
	private CredentialRepository credentialRepository;
	private PasswordEncoder pswdEncoder;
	
	@Autowired // inject into this class from the Spring framework
	public CredentialServiceImpl(CredentialRepository itemRepository, PasswordEncoder pswdEncoder) {
		this.credentialRepository = itemRepository;
		this.pswdEncoder = pswdEncoder;
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
		credential.setPassword(pswdEncoder.encode(credential.getPassword()));
		return credentialRepository.add(credential);
	}

	@Override
	public boolean delete(Credential credential) {
		return credentialRepository.delete(credential);
	}
	
	@Override
	public Credential update(Credential credential) {
		return credentialRepository.update(credential);
	}

	@Override
	public Credential findByUsernameAndPassword(String username, String password) throws CredentialNotFoundException {
		return credentialRepository.findByUsernameAndPassword(username, password);
	}

}
