package org.perscholas.whatgoeswhere.exceptions;

/**
 * Exception class thrown when a Credential is not found in the system
 * 
 * @author Hyunryung Kim
 * @see org.perscholas.whatgoeswhere.models.Credential
 *
 */
public class CredentialNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor with predefined exception message
	 */
	public CredentialNotFoundException() {
		super("You must log in");
	}
	/**
	 * Class constructor specifying the exception message
	 * 
	 * @param message a string containing the error message
	 */
	public CredentialNotFoundException(String message) {
		super(message);
	}
	
}
