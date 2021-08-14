package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import org.perscholas.whatgoeswhere.exceptions.CredentialNotFoundException;
import org.perscholas.whatgoeswhere.models.Credential;

public interface CredentialService {
	
	public List<Credential> getAll() ;
	public Credential findById(int id) ;	
	public Credential findByUsername(String username);	
	public Credential findByUsernameAndPassword(String username, String password) throws CredentialNotFoundException;	
	public Credential add(Credential credential) throws CredentialAlreadyExistsException;
	public boolean delete(Credential credential);
	public Credential update(Credential credential);
}
