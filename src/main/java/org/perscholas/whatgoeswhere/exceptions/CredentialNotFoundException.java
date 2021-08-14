package org.perscholas.whatgoeswhere.exceptions;

public class CredentialNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CredentialNotFoundException() {
		super();
	}
	
	public CredentialNotFoundException(String message) {
		super(message);
	}
	
}
