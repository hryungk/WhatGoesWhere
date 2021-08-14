package org.perscholas.whatgoeswhere.exceptions;

public class CredentialAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CredentialAlreadyExistsException() {
		super();
	}
		
	public CredentialAlreadyExistsException(String username) {
		super("The username " + username + " is already in use. Choose a different one.");		
	}
	
}
