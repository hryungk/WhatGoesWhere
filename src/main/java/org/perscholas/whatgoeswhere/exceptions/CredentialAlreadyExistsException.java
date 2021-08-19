package org.perscholas.whatgoeswhere.exceptions;

/**
 * Exception class thrown when a Credential already exists in the system
 *  
 * @author Hyunryung Kim
 * @see org.perscholas.whatgoeswhere.models.Credential
 * 
 */
public class CredentialAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor specifying the the error message with a username.
	 *  
	 * @param username a String containing a username
	 */
	public CredentialAlreadyExistsException(String username) {
		super("The username " + username + " is already in use. Choose a different one.");		
	}
	
}
