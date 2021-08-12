package org.perscholas.whatgoeswhere.services;

import java.util.List;

import org.perscholas.whatgoeswhere.models.Credential;

public interface CredentialService {
	
	public List<Credential> getAll() ;
	public Credential findById(int id) ;	
	public Credential findByUsername(String username);	
	public Credential add(Credential credential);
	public boolean delete(Credential credential);
	public Credential update(Credential credential);
}
