package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Credential;
import org.perscholas.whatgoeswhere.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
	private CredentialRepository credentialRepository;
	
	@Autowired // inject into this class from the Spring framework
	public CredentialService(CredentialRepository itemRepository) {
		this.credentialRepository = itemRepository;
	}
	public List<Credential> getAll() {
		return credentialRepository.getAll();
	}

	public Credential findById(int id) {
		return credentialRepository.findById(id);
	}
	
	public Credential findByUsername(String username) {	
		return credentialRepository.findByUsername(username);
	}
	
	public boolean addCredential(Credential credential) {
		return credentialRepository.addCredential(credential);
	}
	public Credential add(Credential credential) {
		return credentialRepository.add(credential);
	}

	public boolean delete(Credential credential) {
		return credentialRepository.delete(credential);
	}

	public boolean updateCredential(Credential credential) {
		return credentialRepository.updateCredential(credential);
	}
	public Credential update(Credential credential) {
		return credentialRepository.update(credential);
	}
}
